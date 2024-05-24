package com.example.Nimish.BlogApp.payloads;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class responseDto {

    private Object data;
    private String message;
    private boolean hasError;


}
