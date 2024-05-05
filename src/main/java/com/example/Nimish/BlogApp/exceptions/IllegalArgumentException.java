package com.example.Nimish.BlogApp.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IllegalArgumentException extends RuntimeException{
    long pId;
    long uId;

    public IllegalArgumentException (long pId,long uId){
        super(String.format("The comment on post %s you are trying to access is in-accessible for user %s",pId,uId));
        this.pId=pId;
        this.uId=uId;
    }
}
