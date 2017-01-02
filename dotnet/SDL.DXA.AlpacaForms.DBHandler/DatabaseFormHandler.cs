using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Configuration;
using System.Web.Mvc;
using SDL.DXA.Modules.Forms.Models;
using Sdl.Web.Common.Logging;
using SDL.DXA.Modules.Forms;
using SDL.DXA.AlpacaForms.DBHandler.Models;

namespace SDL.DXA.AlpacaForms.DBHandler
{
    /// <summary>
    /// Database Form Handler
    /// </summary>
    public class DatabaseFormHandler : IFormHandler
    {
        // Database Repository for submitted forms
        //
        private DBFormsRepository dbFormsRepository = new DBFormsRepository(WebConfigurationManager.AppSettings["alpacaforms-db-connection"]);

        public IList<DBForm> GetAllForms()
        {
            return this.dbFormsRepository.GetAllForms();
        }

        public DBForm GetForm(string formId)
        {
            return this.dbFormsRepository.GetForm(formId);
        }

        public SubmittedForm GetSubmittedForm(int sequenceId)
        {
            return this.dbFormsRepository.GetSubmittedForm(sequenceId);
        }

        public string Name
        {
            get
            {
                return "DB-Form";
            }
        }

        public string ProcessForm(Form form, FormCollection formData)
        {
            Log.Debug("Processing form: " + form.Id);
          
            this.dbFormsRepository.StoreSubmittedForm(form, formData);

            return form.FormAction.GetResultLink("SUCCESS");
        }

    }

}