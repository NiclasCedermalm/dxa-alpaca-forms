using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace SDL.DXA.Modules.Forms
{
    /// <summary>
    /// Form Handler Registry
    /// </summary>
    public abstract class FormHandlerRegistry
    {
        private static IDictionary<string, IFormHandler> formHandlers = new Dictionary<string, IFormHandler>();

        /// <summary>
        /// Register form handler
        /// </summary>
        /// <param name="formHandler"></param>
        public static void RegisterFormHandler(IFormHandler formHandler)
        {
            formHandlers.Add(formHandler.Name, formHandler);
        }

        /// <summary>
        /// Get form handler with specific name
        /// </summary>
        /// <param name="name"></param>
        /// <returns></returns>
        public static IFormHandler Get(string name)
        {
            IFormHandler formHandler = null;
            formHandlers.TryGetValue(name, out formHandler);
            return formHandler;
        }

        /// <summary>
        /// Get form handler of a specific type
        /// </summary>
        /// <param name="formHandlerType"></param>
        /// <returns></returns>
        public static IFormHandler Get(Type formHandlerType)
        {
            foreach ( var formHandler in formHandlers.Values )
            {
                if ( formHandler.GetType() == formHandlerType )
                {
                    return formHandler;
                }
            }
            return null;
        }

    }
}