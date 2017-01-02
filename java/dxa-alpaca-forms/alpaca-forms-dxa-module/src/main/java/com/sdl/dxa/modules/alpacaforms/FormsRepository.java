package com.sdl.dxa.modules.alpacaforms;

import com.sdl.webapp.common.api.WebRequestContext;
import com.sdl.webapp.common.api.content.ContentProvider;
import com.sdl.webapp.common.api.content.ContentProviderException;
import com.sdl.webapp.common.api.localization.Localization;
import com.sdl.webapp.common.exceptions.DxaException;
import com.sdl.dxa.modules.alpacaforms.model.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FormsRepository
 *
 * @author nic
 */
@Component
public class FormsRepository {

    @Autowired
    private WebRequestContext webRequestContext;

    @Autowired
    private ContentProvider contentProvider;

    public Form getForm(String formId) throws ContentProviderException {

        final Localization localization = webRequestContext.getLocalization();

        try {
            return (Form) this.contentProvider.getEntityModel(formId, localization);
        }
        catch ( DxaException e ) {
            throw new ContentProviderException("Could not get form data for form ID: " + formId, e);
        }
    }
}
