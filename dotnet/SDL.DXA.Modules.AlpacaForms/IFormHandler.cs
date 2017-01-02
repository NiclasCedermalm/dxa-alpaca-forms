using SDL.DXA.Modules.Forms.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace SDL.DXA.Modules.Forms
{
    /// <summary>
    /// Form Handler interface
    /// </summary>
    public interface IFormHandler
    {
        /// <summary>
        /// Handler name
        /// </summary>
        string Name { get; }

        /// <summary>
        /// Process form using the provided form data
        /// </summary>
        /// <param name="form"></param>
        /// <param name="formData"></param>
        /// <returns></returns>
        string ProcessForm(Form form, FormCollection formData);
    }
}