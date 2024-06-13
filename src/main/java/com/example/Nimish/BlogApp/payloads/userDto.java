package com.example.Nimish.BlogApp.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class userDto {

    private int id;

    @NotEmpty(message = "Name is required!!")
    @Size(min =4, message = "Username must be min of 4 characters!!")
    private String name;

    @Email(message = "Email message is not valid!!")
    @NotEmpty(message = "Email is required!!")
    private String email;

    @NotEmpty(message = "Password is required!!")
    @Size(min=3,max=10,message = "Password must be min of 3 characters and max of 10 characters!!")
    private String password;



    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public void setEmail(String email) {
        this.email=email;
    }


}
