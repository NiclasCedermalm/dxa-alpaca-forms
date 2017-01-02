using Sdl.Web.Common.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace SDL.DXA.Modules.Forms.Models
{
    /// <summary>
    /// Form Action
    /// </summary>
    [SemanticEntity(EntityName = "FormAction", Vocab = CoreVocabulary, Prefix = "e")]
    public class FormAction : EntityModel
    {
        const string HTTP_POST_ACTION_TYPE = "HTTP Post";
        const string AJAX_POST_ACTION_TYPE = "AJAX HTTP Post";

        [SemanticProperty("e:actionType")]
        public string ActionType { get; set; }

        [SemanticProperty("e:label")]
        public string Label { get; set; }

        [SemanticProperty("e:target")]
        public string Target { get; set; }

        [SemanticProperty("e:formHandler")]
        public string FormHandler { get; set; }

        [SemanticProperty("e:resultLinks")]
        public List<FormResultLink> ResultLinks { get; set; }

        [SemanticProperty("e:actionJavascript")]
        public string ActionJavascript { get; set; }

        // TODO: Add ActionSettings

        public string GetResultLink(String status)
        {
            foreach (var resultLink in this.ResultLinks)
            {
                if (resultLink.Status.Equals(status))
                {
                    return resultLink.Link.Url;
                }
            }
            return "error-404";
        }

        public string ToJson(string formId)
        {

            if (ActionType.Equals(HTTP_POST_ACTION_TYPE, StringComparison.OrdinalIgnoreCase))
            {
                return GenerateHttpPostConfig(formId);
            }
            else if (ActionType.Equals(AJAX_POST_ACTION_TYPE, StringComparison.OrdinalIgnoreCase))
            {
                // TO IMPLEMENT
                //return generateAjaxPostConfig(formId);
                return "";
            }
            else
            {
                return "";
            }
        }

        private string GenerateHttpPostConfig(String formId)
        {

            string postUrl;
            if (this.FormHandler != null)
            {
                postUrl = "/form/submit/" + formId;
            }
            else
            {
                postUrl = this.Target;
            }

            return
                    "\"form\":{\n" +
                            "        \"attributes\":{\n" +
                            "            \"action\":\"" + postUrl + "\",\n" +
                            "            \"method\":\"post\"\n" +
                            "        },\n" +
                            "        \"buttons\":{\n" +
                            "            \"submit\":{\n" +
                            "                \"title\": \"" + this.Label + "\",\n" +
                            "                \"styles\": \"btn btn-primary\"\n" +
                                             GenerateActionJavascript() +
                            "            }\n" +
                            "       }\n" +
                            "},";

        }

        private string GenerateActionJavascript()
        {
            if (this.ActionJavascript != null)
            {
                return ",\"click\": " + this.ActionJavascript;
            }
            else
            {
                return "";
            }
        }

    }
}