package com.sdl.dxa.modules.alpacaforms.model;

import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticEntity;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticProperty;
import com.sdl.webapp.common.api.model.entity.AbstractEntityModel;

import java.util.StringTokenizer;

import static com.sdl.webapp.common.api.mapping.semantic.config.SemanticVocabulary.SDL_CORE;

/**
 * Form
 *
 * @author nic
 */
@SemanticEntity(entityName = "Form", vocabulary = SDL_CORE, prefix = "e", public_ = false)
public class Form extends AbstractEntityModel {

    @SemanticProperty("e:title")
    private String title;

    @SemanticProperty("e:description")
    private String description;

    @SemanticProperty("e:formDefinition")
    private String formDefinition;

    @SemanticProperty("e:formAction")
    private FormAction formAction;

    // Non-content fields
    //
    private String formId;
    private int submittedCount;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public FormAction getFormAction() {
        return formAction;
    }

    public String getFormDefinition() {
        return formDefinition;
    }

    public String getAggregatedDefinition() {

        // TODO: Store forms in session here??? Otherwise we need to find out template ID...

        // Insert the form action into the form options
        //
        int optionIndex = this.formDefinition.indexOf(OPTIONS_JSON_TAG);
        if ( optionIndex != -1 ) {
            int insertIndex = optionIndex + OPTIONS_JSON_TAG.length();

            String aggregatedDefinition = this.formDefinition.substring(0, insertIndex) + this.formAction.toJson(this.getFormId()) + this.formDefinition.substring(insertIndex);
            return aggregatedDefinition;
        }
        else {
            return this.formDefinition;
        }
    }

    public String getFormId() {
        if ( formId == null ) {
            String templateTcmUri = (String) this.getXpmMetadata().get("ComponentTemplateID");
            StringTokenizer tokenizer = new StringTokenizer(templateTcmUri, ":-");
            tokenizer.nextToken();
            tokenizer.nextToken();
            this.formId = this.getId() + "-" + tokenizer.nextToken();
        }
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public int getSubmittedCount() {
        return submittedCount;
    }

    public void setSubmittedCount(int submittedCount) {
        this.submittedCount = submittedCount;
    }

    static String OPTIONS_JSON_TAG = "\"options\":{";
}
