package com.sdl.dxa.modules.alpacaforms.model;

import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticEntity;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticProperty;
import com.sdl.webapp.common.api.model.entity.AbstractEntityModel;
import com.sdl.webapp.common.api.model.entity.Link;

import static com.sdl.webapp.common.api.mapping.semantic.config.SemanticVocabulary.SDL_CORE;

/**
 * Form Result Link
 *
 * @author nic
 */
@SemanticEntity(entityName = "FormResultLink", vocabulary = SDL_CORE, prefix = "e", public_ = false)
public class FormResultLink extends AbstractEntityModel {

    @SemanticProperty("e:status")
    private String status;

    @SemanticProperty("e:link")
    private Link link;

    public Link getLink() {
        return link;
    }

    public String getStatus() {
        return status;
    }
}
