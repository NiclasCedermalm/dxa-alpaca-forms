using Sdl.Web.Common.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace SDL.DXA.Modules.Forms.Models
{
    /// <summary>
    /// Form model
    /// </summary>
    [SemanticEntity(EntityName = "Form", Vocab = CoreVocabulary, Prefix = "e")]
    public class Form : EntityModel
    {
        const string OPTIONS_JSON_TAG = "\"options\":{";

        [SemanticProperty("e:title")]
        public string Title { get; set; }

        [SemanticProperty("e:description")]
        public string Description { get; set; }

        [SemanticProperty("e:formDefinition")]
        public string FormDefinition { get; set; }

        [SemanticProperty("e:formAction")]
        public FormAction FormAction { get; set; }

        public string FormId
        {
            get
            {
                return this.Id;
            }
        }

        public string AggregatedDefinition
        {
            get
            {
                // Insert the form action into the form options
                //
                var optionIndex = this.FormDefinition.IndexOf(OPTIONS_JSON_TAG);
                if (optionIndex != -1)
                {
                    int insertIndex = optionIndex + OPTIONS_JSON_TAG.Length;

                    var aggregatedDefinition = this.FormDefinition.Substring(0, insertIndex) + this.FormAction.ToJson(this.Id) + this.FormDefinition.Substring(insertIndex);
                    return aggregatedDefinition;
                }
                else
                {
                    return this.FormDefinition;
                }
            }
        }

        public int SubmittedCount { get; set; }

        

    }
}