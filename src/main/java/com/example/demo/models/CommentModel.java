package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/***
 * @author Aditya Soni( adityasoni182@gmail.com )
 * @version v1
 * @since 12 August 2020
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class CommentModel implements Serializable {
    String text;
    @JsonProperty("by")
    String userName;
    String userSince;

    public String getText() {
        return this.text;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserSince() {
        return this.userSince;
    }

    public void setUserSince(String userSince) {
        this.userSince = userSince;
    }
}
