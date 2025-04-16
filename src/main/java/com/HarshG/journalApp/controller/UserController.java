package com.HarshG.journalApp.controller;

import com.HarshG.journalApp.entity.User;
import com.HarshG.journalApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserService userService;

    @GetMapping
    public ResponseEntity<?> getUsers(){
        return userService.getUsers();
    }
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        if(user==null ){
            return new ResponseEntity<>("Invalid Request", HttpStatus.BAD_REQUEST);
        }


            return userService.createUser(user);

    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id){

        LOGGER.info("Getting element with id:" + id);
        return userService.getElementById(id);
    }
    @PutMapping("")
    public ResponseEntity<?> editUserById(@RequestBody User user){

        if(user==null){
            return new ResponseEntity<>("Bad request",HttpStatus.BAD_REQUEST);
        }
        return userService.editUserByUserName(user);
    }
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id){
        return userService.deleteUserById(id);
    }
}
