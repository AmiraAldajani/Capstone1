package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    @NotEmpty(message = "Enter an ID")
    private String id;
    @NotEmpty(message ="Fill a name")
    @Size(min=4, message = "Name has to be bigger than 3")
    private String name;

}
