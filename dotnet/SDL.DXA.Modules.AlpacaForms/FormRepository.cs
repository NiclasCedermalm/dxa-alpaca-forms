using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Sdl.Web.Common.Configuration;
using Sdl.Web.Mvc.Configuration;
using SDL.DXA.Modules.Forms.Models;

namespace SDL.DXA.Modules.Forms
{
    /// <summary>
    /// Form Repository
    /// </summary>
    public class FormRepository
    {
        /// <summary>
        /// Get Form from the content repository. The form itself is a dynamic SDL Web component stored in the content store.
        /// </summary>
        /// <param name="formId"></param>
        /// <returns></returns>
        public static Form GetForm(string formId)
        {
            Form form = (Form) SiteConfiguration.ContentProvider.GetEntityModel(formId, WebRequestContext.Localization);
            return form;
        }
    }
}