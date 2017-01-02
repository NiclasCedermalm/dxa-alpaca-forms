using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Xml.Linq;
using Sdl.Web.Common;
using Sdl.Web.Common.Logging;
using SDL.DXA.AlpacaForms.DBHandler.Models;
using SDL.DXA.Modules.Forms;
using SDL.DXA.Modules.Forms.Models;

namespace SDL.DXA.AlpacaForms.DBHandler
{
    /// <summary>
    /// Simple DB repository for forms.
    /// </summary>
    public class DBFormsRepository
    {

        private string connectionString;

        const string GET_NEXT_FORM_ID = "SELECT NEXT VALUE FOR FORM_ID_SEQUENCE";
        const string INSERT_FORM_DATA_SQL = "INSERT INTO SUBMITTED_FORM(ID, FORM_ID, SUBMIT_DATE) VALUES(@Id,@FormId,@SubmitDate);";
        const string INSERT_FORM_FIELD_SQL = "INSERT INTO SUBMITTED_FORM_FIELD(ID, NAME, VALUE) VALUES(@Id,@Name,@Value)";
        const string SELECT_ALL_FORMS = "SELECT FORM_ID, COUNT(FORM_ID) AS SUBMITTED_COUNT FROM SUBMITTED_FORM GROUP BY FORM_ID";
        const string SELECT_SUBMITTED_FORMS = "SELECT ID, SUBMIT_DATE FROM SUBMITTED_FORM WHERE FORM_ID=@FormId ORDER BY ID";
        const string SELECT_SUBMITTED_FORM_FIELDS = "SELECT NAME,VALUE FROM SUBMITTED_FORM_FIELD WHERE ID=@Id";

        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="connectionString"></param>
        public DBFormsRepository(string connectionString)
        {
            this.connectionString = connectionString;
        }

        /// <summary>
        /// Store submitted form
        /// </summary>
        /// <param name="form"></param>
        /// <param name="formData"></param>
        public void StoreSubmittedForm(Form form, FormCollection formData)
        {

            using ( SqlConnection sqlConnection = new SqlConnection(connectionString) )
            {
                try
                {
                    sqlConnection.Open();
                    var getFormSequenceIdSQL = new SqlCommand(GET_NEXT_FORM_ID, sqlConnection);
                    SqlDataReader reader = getFormSequenceIdSQL.ExecuteReader();
                    using (reader)
                    {
                        if (reader.Read())
                        {
                            int formSequenceId = reader.GetInt32(0);
                            reader.Close();
                            var insertFormSQL = new SqlCommand(INSERT_FORM_DATA_SQL, sqlConnection);
                            insertFormSQL.Parameters.Add("@Id", SqlDbType.Int).Value = formSequenceId;
                            insertFormSQL.Parameters.Add("@FormId", SqlDbType.NVarChar).Value = form.FormId;
                            insertFormSQL.Parameters.Add("@SubmitDate", SqlDbType.Date).Value = DateTime.Now;
                            insertFormSQL.ExecuteNonQuery();
                            foreach (var formFieldName in formData.Keys.Cast<string>())
                            {
                                var insertFormFieldSQL = new SqlCommand(INSERT_FORM_FIELD_SQL, sqlConnection);
                                insertFormFieldSQL.Parameters.Add("@Id", SqlDbType.Int).Value = formSequenceId;
                                insertFormFieldSQL.Parameters.Add("@Name", SqlDbType.NVarChar).Value = formFieldName;
                                insertFormFieldSQL.Parameters.Add("@Value", SqlDbType.NVarChar).Value = formData[formFieldName];
                                insertFormFieldSQL.ExecuteNonQuery();
                            }
                        }

                    }

                }
                catch (Exception e)
                {
                    throw new DxaException("Could not store submitted form.", e);
                }
            }

        }

        /// <summary>
        /// Get all defined forms that have submitted form data
        /// </summary>
        /// <returns></returns>
        public IList<DBForm> GetAllForms()
        {
            using (SqlConnection sqlConnection = new SqlConnection(connectionString))
            {
                try
                {
                    sqlConnection.Open();
                    var getAllFormsSQL = new SqlCommand(SELECT_ALL_FORMS, sqlConnection);
                    SqlDataReader reader = getAllFormsSQL.ExecuteReader();
                    IList<DBForm> allForms = new List<DBForm>();
                    while (reader.Read())
                    {
                        string formId = reader.GetString(0);
                        int count = reader.GetInt32(1);
                        Form form = FormRepository.GetForm(formId);
                        if (form != null)
                        {
                            DBForm dbForm = new DBForm(form);
                            dbForm.Count = count;
                            allForms.Add(dbForm);
                        }
                    }
                    return allForms;
                }
                catch (Exception e)
                {
                    throw new DxaException("Could not get all forms.", e);
                }
            }
        }

        /// <summary>
        /// Get a specific form and its submitted data
        /// </summary>
        /// <param name="formId"></param>
        /// <returns></returns>
        public DBForm GetForm(string formId)
        {
            Form form = FormRepository.GetForm(formId);
            if (form == null)
            {
                Log.Warn("No form found in the Broker DB with ID: " + formId);
                return null;
            }
            DBForm dbForm = new DBForm(form);
            using (SqlConnection sqlConnection = new SqlConnection(connectionString))
            {
                try
                {
                    sqlConnection.Open();
                    var getSubmittedFormsSQL = new SqlCommand(SELECT_SUBMITTED_FORMS, sqlConnection);
                    getSubmittedFormsSQL.Parameters.Add("@FormId", SqlDbType.NVarChar).Value = form.FormId;
                    SqlDataReader reader = getSubmittedFormsSQL.ExecuteReader();
                    IList<SubmittedForm> submittedForms = new List<SubmittedForm>();
                    dbForm.SubmittedForms = submittedForms;
                    while (reader.Read())
                    {
                        int  id = reader.GetInt32(0);
                        DateTime date = reader.GetDateTime(1);
                        SubmittedForm submittedForm = new SubmittedForm { SequenceId = id, SubmitDate = date};
                        submittedForms.Add(submittedForm);
                    }
                    dbForm.Count = submittedForms.Count;
                    return dbForm;
                }
                catch (Exception e)
                {
                    throw new DxaException("Could not get all forms.", e);
                }
            }
        }

        /// <summary>
        /// Get a specific submitted form with form fields
        /// </summary>
        /// <param name="sequenceId"></param>
        /// <returns></returns>
        public SubmittedForm GetSubmittedForm(int sequenceId)
        {
            SubmittedForm form = new SubmittedForm();
            form.SequenceId = sequenceId;
            using (SqlConnection sqlConnection = new SqlConnection(connectionString))
            {
                try
                {
                    sqlConnection.Open();
                    var getSubmittedFormSQL = new SqlCommand(SELECT_SUBMITTED_FORM_FIELDS, sqlConnection);            
                    getSubmittedFormSQL.Parameters.Add("@Id", SqlDbType.Int).Value = sequenceId;
                    SqlDataReader reader = getSubmittedFormSQL.ExecuteReader();
                    form.FormData = new FormCollection();
                    while (reader.Read())
                    {
                        string name = reader.GetString(0);
                        string value = reader.GetString(1);
                        Log.Info("Form " + name + " = " + value);
                        form.FormData.Add(name, value);
                    }
                    return form;
                }
                catch (Exception e)
                {
                    throw new DxaException("Could not get all forms.", e);
                }
            }

        }
    }
}