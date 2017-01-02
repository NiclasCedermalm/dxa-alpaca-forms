using Sdl.Web.Common.Configuration;
using Sdl.Web.Common.Logging;
using Sdl.Web.Mvc.Configuration;
using Sdl.Web.Mvc.Controllers;
using SDL.DXA.Modules.Forms.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace SDL.DXA.Modules.Forms.Controllers
{
    public class FormsController : BaseController
    {
        [HttpPost]
        public ActionResult SubmitForm(String formId, FormCollection formData)
        {
            // Remove CSRF token
            //
            formData.Remove("__RequestVerificationToken");

            // Get form entity
            //
            Form form = FormRepository.GetForm(formId);
            Log.Debug("Form data for form '" + form.Id + "' : ");
            foreach ( var param in formData.Keys )
            {
                Log.Debug("  " + param + " = " + formData.Get(param.ToString()));
            }

            var formHandler = FormHandlerRegistry.Get(form.FormAction.FormHandler);
            string resultLink;
            if ( formHandler != null )
            {
                resultLink = formHandler.ProcessForm(form, formData); 
            }
            else
            {
                resultLink = WebRequestContext.Localization.Path + "/error-404"; 
            }

            return Redirect(resultLink);
        }
    }
}