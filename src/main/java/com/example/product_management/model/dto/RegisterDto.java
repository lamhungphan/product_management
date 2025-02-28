package com.example.product_management.model.dto;

import com.example.product_management.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegisterDto {
    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    private String phoneNumber;

    private String address;

    @NotNull(message = "Role must be selected")
    private Role role;

    @Size(min = 6, max = 20, message = "Minimum Password length is 6 characters")
    private String password;

    private String confirmPassword;
}
