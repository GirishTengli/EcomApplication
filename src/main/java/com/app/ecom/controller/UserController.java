package com.app.ecom.controller;

import com.app.ecom.Entity.Users;
import com.app.ecom.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/api/users")
    public ResponseEntity<List<Users>> getAllUsers(){
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
        // or
        // return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<Users> getUser(@PathVariable Long id){
        Users user = userService.fetchUser(id);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody Users users){
        userService.addUser(users);
        return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
    }

//    @DeleteMapping("/api/{id}}")
//    public Users deleteUser(@PathVariable Long id){
//        usersList.re remove(id);
//        return ;
//    }
}
