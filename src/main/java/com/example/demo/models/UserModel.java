package com.example.demo.models;

import com.example.demo.services.utilServices.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/***
 * @author Aditya Soni( adityasoni182@gmail.com )
 * @version v1
 * @since 12 August 2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private String created;

    public String getCreated() {
        return DateUtils.convertUnixTimeToLocalDateTime(this.created);
    }
}
