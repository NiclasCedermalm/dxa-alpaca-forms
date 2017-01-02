package com.sdl.dxa.modules.alpacaforms.db;

import com.sdl.webapp.common.api.content.ContentProviderException;
import com.sdl.webapp.common.markup.Markup;
import com.sdl.dxa.modules.alpacaforms.FormsRepository;
import com.sdl.dxa.modules.alpacaforms.model.Form;
import com.sdl.dxa.modules.alpacaforms.model.SubmittedFormData;
import com.sdl.dxa.modules.alpacaforms.model.SubmittedFormField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * DatabaseFormAdminController
 *
 * @author nic
 */
@Controller
public class DatabaseFormAdminController {

    @Autowired
    private Markup markup;

    @Autowired
    private DatabaseFormHandler databaseFormHandler;

    @Autowired
    private FormsRepository formsRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/admin/form/list")
    public String listAllForms(HttpServletRequest request) throws ContentProviderException {

        // List all forms with submit forms + count
        //
        List<Form> allForms = this.databaseFormHandler.getAllForms();

        request.setAttribute("markup", this.markup);
        request.setAttribute("allForms", allForms);

        return "/Forms/Admin/ListForms";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/form/listSubmitted/{formId}")
    public String listSubmittedForms(HttpServletRequest request, @PathVariable String formId) throws ContentProviderException {

        Form form = this.formsRepository.getForm(formId);
        List<SubmittedFormData> submittedForms = this.databaseFormHandler.getSubmittedForms(formId);

        // TODO: Build up a table with all results here instead

        request.setAttribute("markup", this.markup);
        request.setAttribute("form", form);
        request.setAttribute("submittedForms", submittedForms);

        return "/Forms/Admin/ListSubmittedForms";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/form/get/{submitId}")
    public String displaySubmittedForm(HttpServletRequest request, @PathVariable int submitId) {

        List<SubmittedFormField> submittedFormFields = this.databaseFormHandler.getSubmittedFormFields(submitId);
        request.setAttribute("markup", this.markup);
        request.setAttribute("submittedFormFields", submittedFormFields);

        return "/Forms/Admin/DisplaySubmittedForm";
    }
}
