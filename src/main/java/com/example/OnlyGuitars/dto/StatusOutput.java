package com.example.OnlyGuitars.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

public class StatusOutput {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public ArrayList<String> errorList = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String succededMessage;
    public boolean isSucceded;

    public List<String> getErrorList() {
        return errorList;
    }

    public void setSuccededMessage(String succededMessage) {
        this.succededMessage = succededMessage;
    }

    public void setSucceded(boolean succeded) {
        isSucceded = succeded;
    }
}
