package com.blog.controller;

import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid  @RequestBody UserDto userDto){
        UserDto createdUserDto = userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto ,HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto , @PathVariable("userId") Integer uId ){
        UserDto updatedUser = userService.updateUser(userDto,uId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId){
        userService.deleteUser(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully" , true),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public  ResponseEntity<UserDto> getUserById(@PathVariable Integer userId){
        UserDto  user = userService.getUserById(userId);

        if (user == null){
            throw new ResourceNotFoundException("User not", "found", userId);
        }
        return ResponseEntity.ok(user);
    }
}
