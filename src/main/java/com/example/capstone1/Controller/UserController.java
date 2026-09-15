package com.example.capstone1.Controller;
import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.status(200).body(userService.getUsers());
    }
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        if ( !userService.addUser(user))
            return ResponseEntity.status(400).body(new ApiResponse( "ID already exists!"));
        return ResponseEntity.status(200).body(new ApiResponse( "Added Successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        if ( !userService.updateUser(user))
            return ResponseEntity.status(400).body(new ApiResponse( "Did not find a user with that ID"));
        return ResponseEntity.status(200).body(new ApiResponse("Updated Successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        if ( !userService.deleteUser(id))
            return ResponseEntity.status(400).body(new ApiResponse("Did not find a user with same ID. deletion Failed"));
        return ResponseEntity.status(200).body(new ApiResponse("Deleted Successfully"));
    }

    @PutMapping("/transfer/{senderID}/{receiverID}/{amount}")
    public ResponseEntity<?> transferBalance(@PathVariable String senderID, @PathVariable String receiverID, @PathVariable double amount){
        String message = userService.transferBalance(senderID, receiverID, amount);
        if(message.equals("case1"))
            return ResponseEntity.status(400).body(new ApiResponse("Can't transfer a negative amount."));
        if(message.equals("case2"))
            return ResponseEntity.status(400).body(new ApiResponse("Not enough balance. Failed to transfer."));
        if(message.equals("case3"))
            return ResponseEntity.status(400).body(new ApiResponse("ReceiverID not Found..."));
        if(message.equals("case5"))
            return ResponseEntity.status(400).body(new ApiResponse("SenderID not found"));
        return ResponseEntity.status(200).body(message); //case3
    }
}
