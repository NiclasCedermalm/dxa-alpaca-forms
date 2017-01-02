using Sdl.Web.Common.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace SDL.DXA.Modules.Forms.Models
{
    /// <summary>
    /// Form Result Link
    /// </summary>
    [SemanticEntity(EntityName = "FormResultLink", Vocab = CoreVocabulary, Prefix = "e")]
    public class FormResultLink : EntityModel
    {
        [SemanticProperty("e:status")]
        public string Status { get; set; }

        [SemanticProperty("e:link")]
        public Link Link { get; set; }
    }
}