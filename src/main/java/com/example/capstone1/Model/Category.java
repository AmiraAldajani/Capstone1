package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    @NotEmpty(message = "Enter ID")
    private String id;
    @NotEmpty(message ="Enter a name")
    @Size(min=4, message = "Name has to be more than 3")
    private String name;

}
