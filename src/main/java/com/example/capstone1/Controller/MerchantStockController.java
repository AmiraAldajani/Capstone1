package com.example.capstone1.Controller;
import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Service.MerchantService;
import com.example.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchantStock")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;


    @GetMapping("/get")
    public ResponseEntity<?> getMerchantStocks(){
        return ResponseEntity.status(200).body(merchantStockService.getMerchantStocks());
    }
    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStocks(@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        String message = merchantStockService.addMerchantStock(merchantStock);
        return switch (message) {
            case "case1" -> ResponseEntity.status(400).body(new ApiResponse("Merchant Stock ID already taken"));
            case "case2" ->
                    ResponseEntity.status(400).body(new ApiResponse("Product and Merchant were not added as merchant stock"));
            case "case3" -> ResponseEntity.status(400).body(new ApiResponse("Product does not exist"));
            case "case4" -> ResponseEntity.status(400).body(new ApiResponse("Merchant does not exist"));
            default -> ResponseEntity.status(200).body(new ApiResponse(message));
        };

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        if ( !merchantStockService.updateMerchantStock(merchantStock))
            return ResponseEntity.status(400).body(new ApiResponse( "Did not find a merchant stock with that ID"));
        return ResponseEntity.status(200).body(new ApiResponse("Updated Successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String id){
        if ( !merchantStockService.deleteMerchantStock(id))
            return ResponseEntity.status(400).body(new ApiResponse("Did not find a merchant with same ID. deletion Failed"));
        return ResponseEntity.status(200).body(new ApiResponse("Deleted Successfully"));
    }

    @PutMapping("/updateStock/{merchantID}/{productID}/{stockNumber}")
    public ResponseEntity<?> updateStock(@PathVariable String merchantID,@PathVariable String productID,@PathVariable Integer stockNumber){
        if ( !merchantStockService.addStocks(merchantID, productID, stockNumber))
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't update due to an incorrect ID OR because stock number is less than 1."));
        return ResponseEntity.status(200).body(new ApiResponse("Updated Successfully\n"));
    }

    @PutMapping("/buyProduct/{userID}/{productID}/{merchantID}")
    public ResponseEntity<?> buyProduct(@PathVariable String userID, @PathVariable String merchantID,@PathVariable String productID){
        String message = merchantStockService.buyProduct(userID,merchantID,productID);
        if(message.equals( "case1"))
            return ResponseEntity.status(400).body(new ApiResponse("User with that ID was not found.."));
        if(message.equals( "case2"))
            return ResponseEntity.status(400).body(new ApiResponse("Product with that ID does not exist"));
        if(message.equals( "case3"))
            return ResponseEntity.status(400).body(new ApiResponse("Product either is out of stock or you're out of money"));
        return ResponseEntity.status(200).body(new ApiResponse(message));
    }

    @GetMapping("/listAvailable/{categoryID}")
    public ResponseEntity<?> listAvailable(@PathVariable String categoryID){
        if (merchantStockService.availableProductsBasedOnCategory(categoryID).isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("The category you provided might not exist in the system"));
        return ResponseEntity.status(200).body(merchantStockService.availableProductsBasedOnCategory(categoryID));
    }

    @GetMapping("/inventoryValue/{merchantID}")
    public ResponseEntity<?> merchantInventoryValue(@PathVariable String merchantID){
        String message= merchantStockService.merchantInventoryValue(merchantID);
        if (message.equals("case1"))
            return ResponseEntity.status(400).body(new ApiResponse("Didn't find a merchant with this ID in the system"));
        return ResponseEntity.status(200).body(new ApiResponse( message));
    }

    @PutMapping("returnProduct/{userID}/{merchantID}/{productID}")
    public ResponseEntity<?> returnProduct(@PathVariable String userID, @PathVariable String merchantID,@PathVariable String productID){
        if(!merchantStockService.returnProduct(userID,merchantID,productID))
            return ResponseEntity.status(400).body(new ApiResponse("No matching purchase found. Wrong IDs or the product was already returned."));
        return ResponseEntity.status(200).body(new ApiResponse("Return Complete!"));
    }


}
