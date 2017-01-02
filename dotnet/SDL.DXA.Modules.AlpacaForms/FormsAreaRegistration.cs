using Sdl.Web.Mvc.Configuration;
using SDL.DXA.Modules.Forms.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;

namespace SDL.DXA.Modules.Forms
{
    /// <summary>
    /// Forms Area Registration
    /// </summary>
    public class FormsAreaRegistration : BaseAreaRegistration
    {
        const string CONTROLLER_NAMESPACE = "SDL.DXA.Modules.Forms.Controller";

        public override string AreaName
        {
            get
            {
                return "Forms";
            }
        }

        protected override void RegisterAllViewModels()
        {
            // Entity Views
            //
            RegisterViewModel("Form", typeof(Form));
        }

        public override void RegisterArea(AreaRegistrationContext context)
        {
            base.RegisterArea(context);

            // Register the Forms controller
            //
            RouteHelper.MapRoute(CONTROLLER_NAMESPACE, context.Routes, "Form_Submit", "form/submit/{formId}",
                new { controller = "Forms", action = "SubmitForm" });
        }

    }
}