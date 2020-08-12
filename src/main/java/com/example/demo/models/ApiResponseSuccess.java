package com.example.demo.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;
/***
 * @author Aditya Soni( adityasoni182@gmail.com )
 * @version v1
 * @since 12 August 2020
 */
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
