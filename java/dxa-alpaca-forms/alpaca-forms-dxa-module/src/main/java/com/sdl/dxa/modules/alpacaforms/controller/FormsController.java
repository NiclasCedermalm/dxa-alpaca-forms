package com.sdl.dxa.modules.alpacaforms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdl.dxa.modules.alpacaforms.FormHandler;
import com.sdl.webapp.common.api.WebRequestContext;
import com.sdl.webapp.common.api.content.ContentProvider;
import com.sdl.webapp.common.api.content.ContentProviderException;
import com.sdl.webapp.common.api.localization.Localization;
import com.sdl.webapp.common.controller.BaseController;
import com.sdl.webapp.common.exceptions.DxaException;
import com.sdl.dxa.modules.alpacaforms.FormsRepository;
import com.sdl.dxa.modules.alpacaforms.model.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Forms Controller
 *
 * @author nic
 */
@Controller
@RequestMapping(FormsController.FORMS_CONTROLLER_PATH)
public class FormsController {

    public static final String FORMS_CONTROLLER_PATH = "/api/form";

    @Autowired
    private WebRequestContext webRequestContext;

    @Autowired
    private ContentProvider contentProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private List<FormHandler> formHandlers;

    @Autowired
    private FormsRepository formsRepository;

    @RequestMapping(method = RequestMethod.POST, value = "{formId}")
    public AbstractView formPost(HttpServletRequest request, @PathVariable String formId, @RequestParam Map<String,String> formData) throws ContentProviderException {

        // TODO: Add a conversation ID to be used when having multi-step forms
        //

        final Localization localization = webRequestContext.getLocalization();

        Form form = this.formsRepository.getForm(formId);

        // Forward the request to the form handler
        //
        FormHandler formHandler = this.getFormHandler(form.getFormAction().getFormHandler());
        String resultLink;
        if ( formHandler != null ) {
            resultLink = formHandler.processForm(formId, form, formData);
        }
        else {
            resultLink = "/error-404";
        }

        // TODO: Transfer form object + form data to result page -> store that also in a conversation

        return new RedirectView(resultLink);
    }

    protected FormHandler getFormHandler(String name) {
        for ( FormHandler handler : this.formHandlers ) {
            if ( handler.getName().equals(name) ) {
                return handler;
            }
        }
        return null;
    }

}
