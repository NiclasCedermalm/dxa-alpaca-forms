package com.sdl.dxa.modules.alpacaforms.db;

import com.sdl.webapp.common.api.content.ContentProviderException;
import com.sdl.dxa.modules.alpacaforms.FormHandler;
import com.sdl.dxa.modules.alpacaforms.FormsRepository;
import com.sdl.dxa.modules.alpacaforms.model.Form;
import com.sdl.dxa.modules.alpacaforms.model.SubmittedFormData;
import com.sdl.dxa.modules.alpacaforms.model.SubmittedFormField;
import com.sdl.webapp.common.impl.interceptor.csrf.CsrfUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * DB Forms Controller
 *
 * @author nic
 */
@Component
@Slf4j
public class DatabaseFormHandler implements FormHandler {

    @Autowired(required = false)
    private DataSource formDs;

    @Value("${form.db.jdbcUrl:}")
    private String jdbcUrl;

    @Value("${form.db.jdbcDriverClassName:}")
    private String jdbdDriverClassName;

    @Autowired
    private FormsRepository formsRepository;

    private JdbcTemplate jdbcTemplate;

    /*

    DATABASE ARTIFACT NEEDED:

        * FORM_ID_SEQUENCE (SEQUENCE)
        * SUBMITTED_FORM (TABLE)
            - ID (INTEGER)
            - FORM_ID (VARCHAR)
            - SUBMIT_DATE (TIMESTAMP)

        * SUBMITTED_FORM_FIELD (TABLE)
            - ID (INTEGER)
            - NAME (VARCHAR)
            - VALUE (VARCHAR)

     */

    static final String GET_NEXT_FORM_ID_HSQL = "CALL NEXT VALUE FOR FORM_ID_SEQUENCE";
    static final String GET_NEXT_FORM_ID = "SELECT NEXT VALUE FOR FORM_ID_SEQUENCE";
    static final String INSERT_FORM_DATA_SQL = "INSERT INTO SUBMITTED_FORM(ID, FORM_ID, SUBMIT_DATE) VALUES(?,?,?);";
    static final String INSERT_FORM_FIELD_SQL = "INSERT INTO SUBMITTED_FORM_FIELD(ID, NAME, VALUE) VALUES(?,?,?)";
    static final String SELECT_ALL_FORMS = "SELECT FORM_ID, COUNT(FORM_ID) AS SUBMITTED_COUNT FROM SUBMITTED_FORM GROUP BY FORM_ID";
    static final String SELECT_SUBMITTED_FORMS = "SELECT ID, SUBMIT_DATE FROM SUBMITTED_FORM WHERE FORM_ID=? ORDER BY ID";
    static final String SELECT_SUBMITTED_FORM_FIELDS = "SELECT ID,NAME,VALUE FROM SUBMITTED_FORM_FIELD WHERE ID=?";

    class FormRowMapper implements RowMapper<Form> {

        @Override
        public Form mapRow(ResultSet rs, int rowNum) throws SQLException {
            String formId = rs.getString("FORM_ID");
            int submittedCount = rs.getInt("SUBMITTED_COUNT");
            try {
                Form form = formsRepository.getForm(formId);
                form.setFormId(formId);
                form.setSubmittedCount(submittedCount);
                return form;
            }
            catch ( ContentProviderException e ) {
                throw new SQLException("Could not get form data for form ID: " + formId, e);
            }
        }
    }

    static class SubmittedFormDataRowMapper implements RowMapper<SubmittedFormData> {

        @Override
        public SubmittedFormData mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new SubmittedFormData(
                    rs.getInt("ID"),
                    rs.getTimestamp("SUBMIT_DATE"));
        }
    }

    static class SubmittedFormFieldRowMapper implements RowMapper<SubmittedFormField> {

        @Override
        public SubmittedFormField mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new SubmittedFormField(rs.getString("NAME"), rs.getString("VALUE"));
        }
    }

    @PostConstruct
    public void initialize() throws Exception {

        log.debug("Initializing DB-Forms handler...");

        if ( formDs == null ) {
            // Fallback on simple JBDC driver DS
            //
            Driver jdbcDriver = (Driver) ClassUtils.forName(this.jdbdDriverClassName, ClassUtils.getDefaultClassLoader()).newInstance();
            this.formDs = new SimpleDriverDataSource(jdbcDriver, this.jdbcUrl);
        }
        this.jdbcTemplate = new JdbcTemplate(this.formDs);
    }

    @Override
    public String getName() {
        return "DB-Form";
    }

    @Override
    public String processForm(String formId, Form form, Map<String, String> formData) {

        log.debug("Processing form: " + formData);

        int formSubmitId = this.getNextFormSequenceId();

        this.jdbcTemplate.update(INSERT_FORM_DATA_SQL, formSubmitId, formId, new Date());
        for ( String formField : formData.keySet() ) {
            if ( !formField.equals(CsrfUtils.CSRF_TOKEN_NAME) ) {
                this.jdbcTemplate.update(INSERT_FORM_FIELD_SQL, formSubmitId, formField, formData.get(formField));
            }
        }

        log.debug("Form stored in DB using submit ID: " + formSubmitId);

        return form.getFormAction().getResultLink("SUCCESS");
    }

    private int getNextFormSequenceId() {

        int formSequenceId;
        if ( jdbdDriverClassName.contains("hsql") ) {
            formSequenceId = this.jdbcTemplate.queryForInt(GET_NEXT_FORM_ID_HSQL);
        }
        else {
            formSequenceId = this.jdbcTemplate.queryForInt(GET_NEXT_FORM_ID);
        }
        return formSequenceId;
    }

    public List<Form> getAllForms() throws ContentProviderException {
        return this.jdbcTemplate.query(SELECT_ALL_FORMS, new FormRowMapper());
    }


    public List<SubmittedFormData> getSubmittedForms(String formId) {

        // TODO: Add support for pagination here...

        return this.jdbcTemplate.query(SELECT_SUBMITTED_FORMS, new SubmittedFormDataRowMapper(), formId);
    }

    public List<SubmittedFormField> getSubmittedFormFields(int submitId) {
        return this.jdbcTemplate.query(SELECT_SUBMITTED_FORM_FIELDS, new SubmittedFormFieldRowMapper(), submitId);
    }

}
