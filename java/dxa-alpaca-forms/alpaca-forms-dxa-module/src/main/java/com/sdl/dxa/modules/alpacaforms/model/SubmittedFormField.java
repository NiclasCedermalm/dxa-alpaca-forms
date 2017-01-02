package com.sdl.dxa.modules.alpacaforms.model;

/**
 * SubmittedFormField
 *
 * @author nic
 */
public class SubmittedFormField {

    private String name;
    private String value;

    // TODO: Add type here as well

    public SubmittedFormField(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
