package com.sdl.dxa.modules.alpacaforms.model;

import com.sdl.dxa.modules.alpacaforms.controller.FormsController;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticEntity;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticProperty;
import com.sdl.webapp.common.api.model.entity.AbstractEntityModel;
import com.sdl.webapp.common.api.model.entity.NameValuePair;
import com.sdl.webapp.common.impl.interceptor.csrf.CsrfUtils;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import static com.sdl.webapp.common.api.mapping.semantic.config.SemanticVocabulary.SDL_CORE;

/**
 * Form Action
 *
 * @author nic
 */
@SemanticEntity(entityName = "FormAction", vocabulary = SDL_CORE, prefix = "e", public_ = false)
public class FormAction extends AbstractEntityModel {

    @SemanticProperty("e:actionType")
    private String actionType;

    @SemanticProperty("e:label")
    private String label;

    @SemanticProperty("e:target")
    private String target;

    @SemanticProperty("e:formHandler")
    private String formHandler;

    @SemanticProperty("e:resultLinks")
    private List<FormResultLink> resultLinks;

    @SemanticProperty("e:actionJavascript")
    private String actionJavascript;

    @SemanticProperty("e:actionSettings")
    private Collection<NameValuePair> actionSettings;

    public Collection<NameValuePair> getActionSettings() {
        return actionSettings;
    }

    public String getActionType() {
        return actionType;
    }

    public String getLabel() {
        return label;
    }

    public String getTarget() {
        return target;
    }

    public String getFormHandler() {
        return formHandler;
    }

    public List<FormResultLink> getResultLinks() {
        return resultLinks;
    }

    public String getResultLink(String status) {
        for ( FormResultLink resultLink : this.resultLinks ) {
            if ( resultLink.getStatus().equals(status) ) {
                return resultLink.getLink().getUrl();
            }
        }
        return "error-404";
    }

    public String toJson(String formId) {

        if ( actionType.equalsIgnoreCase(HTTP_POST_ACTION_TYPE) ) {
             return generateHttpPostConfig(formId);
        }
        else if ( actionType.equalsIgnoreCase(AJAX_POST_ACTION_TYPE) ) {
             return generateAjaxPostConfig(formId);
        }
        else {
            return "";
        }
    }

    private String generateHttpPostConfig(String formId) {

        String postUrl;
        if ( this.getFormHandler() != null ) {
            postUrl = FormsController.FORMS_CONTROLLER_PATH + "/" + formId;
        }
        else {
            postUrl = this.target;
        }

        /*
        TODO: Enable CSRF here

        .withAttribute("name", CsrfUtils.CSRF_TOKEN_NAME)
                .withAttribute("value", CsrfUtils.setToken(pageContext.getSession()))
                .withAttribute("type", "hidden")

         */

        /*
        if ( true ) {
            postUrl + "?" + CsrfUtils.CSRF_TOKEN_NAME + "=" + CsrfUtils.setToken()
        }
        */

        return
                "\"form\":{\n" +
                        "        \"attributes\":{\n" +
                        "            \"action\":\"" + postUrl + "\",\n" +
                        "            \"method\":\"post\"\n" +
                        "        },\n" +
                        "        \"buttons\":{\n" +
                        "            \"submit\":{\n" +
                        "                \"title\": \"" + this.label + "\",\n" +
                        "                \"styles\": \"btn btn-primary\"\n" +
                                         generateActionJavascript() +
                        "            }\n" +
                        "       }\n" +
                        "},";

    }

    private String generateActionJavascript() {
         if ( this.actionJavascript != null ) {
             return  ",\"click\": " + this.actionJavascript;
         }
         else {
             return "";
         }
    }

    private String generateAjaxPostConfig(String formId) {
        // TODO: TO BE IMPLEMENTED!!!
        return "";
    }

    static String HTTP_POST_ACTION_TYPE = "HTTP Post";
    static String AJAX_POST_ACTION_TYPE = "AJAX HTTP Post";


    // REMOVE THESE TEMPLATES????
    static String HTTP_POST_JSON_TEMPLATE =
            "\"form\":{\n" +
                    "        \"attributes\":{\n" +
                    "            \"action\":\"{0}\",\n" +
                    "            \"method\":\"post\"\n" +
                    "        },\n" +
                    "        \"buttons\":{\n" +
                    "            \"submit\":{\n" +
                    "                \"title\": \"{1}\"\n" +
                    "                \"styles\": \"btn btn-primary\"\n" +
                    "            }\n" +
                    "       }\n" +
                    "},";

    static String HTTP_POST_JSON_WITH_CLICK_ACTION_TEMPLATE =
            "\"form\":{\n" +
                    "        \"attributes\":{\n" +
                    "            \"action\":\"{0}\",\n" +
                    "            \"method\":\"post\"\n" +
                    "        },\n" +
                    "        \"buttons\":{\n" +
                    "            \"submit\":{\n" +
                    "                \"title\": \"{1}\"\n," +
                    "                \"styles\": \"btn btn-primary\"\n" +
                    "                \"click\": {2}\n" +
                    "            }\n" +
                    "       }\n" +
                    "},";

}
