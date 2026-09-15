package com.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "Fill an ID")
    private String id;
    @NotEmpty(message ="Fill a name")
    @Size(min=4, message = "Name has to be bigger than 3")
    private String name;
    @NotNull(message = "Enter a price")
    @Min(value = 0, message = "Has to be a positive number")
    private Double price;
    @NotEmpty(message = "Enter the category's ID, Can't be empty.")
    private String categoryID; //validate if category ID exists in categories in Category class


    // نشيك إذا المنتج موجود بالمرشنت ستوك. بعدين نعرض لسته لليوزر من المنتجات المتاحة
}
