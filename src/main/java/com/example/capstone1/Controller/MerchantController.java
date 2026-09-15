package com.example.capstone1.Controller;
import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.MerchantService;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchants(){
        return ResponseEntity.status(200).body(merchantService.getMerchants());
    }
    @PostMapping("/add")
    public ResponseEntity<?> addMerchants(@RequestBody @Valid Merchant merchant, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        if ( !merchantService.addMerchants(merchant))
            return ResponseEntity.status(400).body(new ApiResponse( "ID already exists!"));
        return ResponseEntity.status(200).body(new ApiResponse( "Added Successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMerchant(@RequestBody @Valid Merchant merchant, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        if ( !merchantService.updateMerchant(merchant))
            return ResponseEntity.status(400).body(new ApiResponse( "Did not find a merchant with that ID"));
        return ResponseEntity.status(200).body(new ApiResponse("Updated Successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id){
        if ( !merchantService.deleteMerchants(id))
            return ResponseEntity.status(400).body(new ApiResponse("Did not find a merchant with same ID. deletion Failed"));
        return ResponseEntity.status(200).body(new ApiResponse("Deleted Successfully"));
    }

}
