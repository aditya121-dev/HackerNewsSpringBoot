package com.example.demo.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JacksonXmlRootElement(localName = "response")
public class ApiResponseSuccess extends ApiResponse {
    private Object body;

    public ApiResponseSuccess() {
        this.status = "success";
    }

    public ApiResponseSuccess(Object body) {
        this.status = "success";
        this.body = body;
    }
}
