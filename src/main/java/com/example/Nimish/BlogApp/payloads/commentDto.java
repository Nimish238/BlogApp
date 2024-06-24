package com.example.Nimish.BlogApp.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class commentDto {

    private int id;

    private String content;

    private int user_id;

    public void setUserId(Integer id) {
        this.user_id=id;
    }
}
