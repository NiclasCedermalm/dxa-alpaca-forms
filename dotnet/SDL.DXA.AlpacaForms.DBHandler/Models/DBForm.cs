using SDL.DXA.Modules.Forms.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace SDL.DXA.AlpacaForms.DBHandler.Models
{
    /// <summary>
    /// DB Form model - represent a form stored in the database
    /// </summary>
    public class DBForm
    {
        public DBForm(Form form)
        {
            this.Id = form.FormId;
            this.Title = form.Title;
        }

        public string Id { get; set; }

        public string Title { get; set; }

        public int Count { get; set; }

        public IList<SubmittedForm> SubmittedForms { get; internal set; }
    }
}