using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Sdl.Web.Mvc.Configuration;
using SDL.DXA.Modules.Forms;

namespace SDL.DXA.AlpacaForms.DBHandler
{
    /// <summary>
    /// DB Handler Registration.
    /// Register DB Form handler into the form registry and the admin controller routes.
    /// </summary>
    public class DBHandlerRegistration : BaseAreaRegistration
    {

        const string CONTROLLER_NAMESPACE = "SDL.DXA.AlpacaForms.DBHandler.Controller";

        public override string AreaName
        {
            get
            {
                return "DBForms";
            }
        }

        public override void RegisterArea(AreaRegistrationContext context)
        {
            base.RegisterArea(context);

            RouteHelper.MapRoute(CONTROLLER_NAMESPACE, context.Routes, "Admin_ListAllForms", "form/admin/list",
               new { controller = "DatabaseFormAdmin", action = "ListAllForms" });

            RouteHelper.MapRoute(CONTROLLER_NAMESPACE, context.Routes, "Admin_ListSubmittedForms", "form/admin/listSubmitted/{formId}",
               new { controller = "DatabaseFormAdmin", action = "ListSubmittedForms" });

            RouteHelper.MapRoute(CONTROLLER_NAMESPACE, context.Routes, "Admin_DisplaySubmittedForm", "form/admin/get/{formId}/{sequenceId}",
               new { controller = "DatabaseFormAdmin", action = "DisplaySubmittedForm" });

            // Register Form Handlers
            //
            FormHandlerRegistry.RegisterFormHandler(new DatabaseFormHandler());
        }
    }

}