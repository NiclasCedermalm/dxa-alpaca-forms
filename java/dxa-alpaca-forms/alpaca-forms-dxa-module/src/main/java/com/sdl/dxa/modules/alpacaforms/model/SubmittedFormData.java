package com.sdl.dxa.modules.alpacaforms.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SubmittedFormData
 *
 * @author nic
 */
public class SubmittedFormData {

    private int id;

    private Date date;

    private List<SubmittedFormField> fields = new ArrayList<>();

    public SubmittedFormData(int id, Date date) {
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public List<SubmittedFormField> getFields() {
        return fields;
    }

}
