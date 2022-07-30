package com.example.demo.payload.request;

import com.example.demo.annotations.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignupRequest {

    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;
    @NotBlank(message = "Please enter your name")
    private String firstname;
    @NotBlank(message = "Please enter your lastname")
    private String lastname;
    @NotBlank(message = "Please enter your username")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    private String confirmPassword;

}
