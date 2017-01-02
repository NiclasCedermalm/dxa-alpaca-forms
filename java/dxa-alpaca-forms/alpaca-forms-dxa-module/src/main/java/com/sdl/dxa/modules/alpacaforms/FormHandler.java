package com.sdl.dxa.modules.alpacaforms;

import com.sdl.dxa.modules.alpacaforms.model.Form;

import java.util.Map;

/**
 * FormHandler
 *
 * @author nic
 */
public interface FormHandler {

    String getName();

    String processForm(String formId, Form form, Map<String,String> formData);

}
