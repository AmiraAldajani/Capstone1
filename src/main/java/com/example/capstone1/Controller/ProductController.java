package com.example.capstone1.Controller;
import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.MerchantService;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity<?> getProducts(){
        return ResponseEntity.status(200).body(productService.getProducts());
    }
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        String message = productService.addProduct(product);
        if (message.equals("Product ID already exists"))
            return ResponseEntity.status(400).body(new ApiResponse(message));
        if (message.equals("Category does not exist"))
            return ResponseEntity.status(400).body(new ApiResponse(message));
        return ResponseEntity.status(200).body(new ApiResponse(message));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMerchant(@RequestBody @Valid Product product, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        if ( !productService.updateProduct(product))
            return ResponseEntity.status(400).body(new ApiResponse( "Did not find a product with that ID"));
        return ResponseEntity.status(200).body(new ApiResponse("Updated Successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        if ( !productService.deleteProduct(id))
            return ResponseEntity.status(400).body(new ApiResponse("Did not find a product with same ID. deletion Failed"));
        return ResponseEntity.status(200).body(new ApiResponse("Deleted Successfully"));
    }

    @PutMapping("/applyDiscount/{adminID}/{categoryID}/{percentage}")
    public ResponseEntity<?> applyDiscount(@PathVariable String adminID, @PathVariable String categoryID, @PathVariable double percentage){
        String message = productService.applyDiscount(adminID, categoryID, percentage);
        if (message.equals("You don't have admin access"))
            return ResponseEntity.status(400).body(new ApiResponse(message));
        if (message.equals("Category does not exist"))
            return ResponseEntity.status(400).body(new ApiResponse(message));
        if (message.equals("Your ID was not found in the system"))
            return ResponseEntity.status(400).body(new ApiResponse(message));
        return ResponseEntity.status(200).body(new ApiResponse(message));
    }
}
