using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using SDL.DXA.Modules.Forms;

namespace SDL.DXA.AlpacaForms.DBHandler.Controllers
{
    /// <summary>
    /// Database Form Admin Controller
    /// </summary>
    public class DatabaseFormAdminController : Controller
    {
        public ActionResult ListAllForms()
        {
            return AdminView("ListForms", FormHandler.GetAllForms());
        }

        public ActionResult ListSubmittedForms(string formId)
        {

            return AdminView("ListSubmittedForms", FormHandler.GetForm(formId));
        }

        public ActionResult DisplaySubmittedForm(string formId, int sequenceId)
        {
            return AdminView("DisplaySubmittedForm", FormHandler.GetSubmittedForm(sequenceId));
        }

        protected ActionResult AdminView(string viewName, object model)
        {
            return View("~/Areas/DBForms/Views/Admin/" + viewName + ".cshtml", model);
        }

        protected DatabaseFormHandler FormHandler
        {
            get
            {
                return (DatabaseFormHandler)FormHandlerRegistry.Get(typeof(DatabaseFormHandler));
            }
           
        }
    }
}