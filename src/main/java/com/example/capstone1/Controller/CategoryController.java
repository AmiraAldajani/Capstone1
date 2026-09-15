package com.example.capstone1.Controller;
import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.CategoryService;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity<?> getCategories(){
        return ResponseEntity.status(200).body(categoryService.getCategories());
    }
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody @Valid Category category, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        if ( !categoryService.addCategory(category))
            return ResponseEntity.status(400).body(new ApiResponse( "ID already exists!"));
        return ResponseEntity.status(200).body(new ApiResponse( "Added Successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestBody @Valid Category category, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        if ( !categoryService.updateCategory(category))
            return ResponseEntity.status(400).body(new ApiResponse( "Did not find a category with that ID"));
        return ResponseEntity.status(200).body(new ApiResponse("Updated Successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){
        if ( !categoryService.deleteCategory(id))
            return ResponseEntity.status(400).body(new ApiResponse("Did not find a category with same ID. deletion Failed"));
        return ResponseEntity.status(200).body(new ApiResponse("Deleted Successfully"));
    }

}
