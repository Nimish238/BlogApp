package com.example.Nimish.BlogApp.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class apiResponse  {
    private String message;
    private boolean success;

}
