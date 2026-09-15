package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "Enter user ID")
    private String id;
    @NotEmpty(message ="Fill a name")
    @Size(min=6, message = "Name has to be longer than 5")
    private String username;
    @NotEmpty(message ="Fill a name")
    @Size(min=7, message = "Password has to be 7 or more")
    private String password;
    @Email(message = "Enter correct email format")
    @NotEmpty(message = "Fill email")
    private String email;
    @NotEmpty(message = "Enter role")
    @Pattern(regexp = "^(admin|customer)$",message = "Role must be either admin or customer.")
    private String role;
    @NotNull(message = "Enter Balance. can't be empty")
    @Min(value = 0, message = "Balance has to be positive")
    private Double balance;
}
