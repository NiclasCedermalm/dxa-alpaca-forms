package com.sdl.dxa.modules.alpacaforms;

import com.sdl.webapp.common.api.mapping.views.AbstractInitializer;
import com.sdl.webapp.common.api.mapping.views.RegisteredViewModel;
import com.sdl.webapp.common.api.mapping.views.RegisteredViewModels;
import com.sdl.webapp.common.markup.html.builders.HtmlBuilders;
import com.sdl.dxa.modules.alpacaforms.model.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Forms Module Initializer
 *
 * @author nic
 */
@Component
@RegisteredViewModels({

        // Entity Views
        //
        @RegisteredViewModel(viewName = "Form", modelClass = Form.class)
})
public class FormsModuleInitializer extends AbstractInitializer {

    @Override
    protected String getAreaName() {
        return "Forms";
    }

}
