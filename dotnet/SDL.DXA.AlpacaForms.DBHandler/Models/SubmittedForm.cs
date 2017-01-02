using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace SDL.DXA.AlpacaForms.DBHandler.Models
{
    /// <summary>
    /// Submitted Form - Represents a submitted form
    /// </summary>
    public class SubmittedForm
    {
        public int SequenceId { get; set; }
        public FormCollection FormData { get; set; }
        public DateTime SubmitDate { get; set; }
    }
}