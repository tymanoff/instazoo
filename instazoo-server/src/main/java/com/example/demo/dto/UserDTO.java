package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {

    private Long id;
    private String username;
    @NotEmpty
    private String lastname;
    @NotEmpty
    private String firstname;
    private String bio;
}
