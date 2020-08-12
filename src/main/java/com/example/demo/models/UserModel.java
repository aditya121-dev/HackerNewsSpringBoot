package com.example.demo.models;

import com.example.demo.services.utilServices.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
