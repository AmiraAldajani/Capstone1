package com.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "Enter an ID")
    private String id;
    @NotEmpty(message = "Enter the products ID")
    private String productID;
    @NotEmpty(message = "Enter the merchances ID")
    private String merchantID;
    @NotNull(message = "Enter Stock Number")
    @Min(value = 10, message = "Stock can't be less than 10 at the start.")
    private Integer stock;

}
