package com.HarshG.journalApp.service;

import com.HarshG.journalApp.entity.JournalEntry;
import com.HarshG.journalApp.entity.User;
import com.HarshG.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;

    public ResponseEntity<?> getUsers() {
        List<User> allEntries = userRepository.findAll();
        if (allEntries.size() > 0) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        } else return new ResponseEntity<>("No Data found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> createUser(User userEntry) {
        try {
            User savedEntry = userRepository.save(userEntry);
            return new ResponseEntity<>(savedEntry, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> createMultipleUser(List<User> userEntries) {
        try {
            List<User> savedEntries = userRepository.saveAll(userEntries);

            if (savedEntries.isEmpty()) {
                return new ResponseEntity<>("No entries were saved", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(savedEntries, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getElementById(String id) {
        try {
            Optional<User> userEntry = userRepository.findById(id);

            if (userEntry.isPresent()) {
                return new ResponseEntity<>(userEntry.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User entry not found for ID: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching user entry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public  ResponseEntity<?> getUserByUserName(String userName){
        try{
            User user = userRepository.findByUsername(userName);
            if(user!=null){
                return new ResponseEntity<>(user,HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No User Found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Error fetching user" + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> editUserByUserName(User user) {
        try {
            User _user = userRepository.findByUsername(user.getUsername());
            System.out.println(_user.getUsername());
            if(_user==null){
                return new ResponseEntity<>("No user exists",HttpStatus.BAD_REQUEST);
            }
                userRepository.save(user);
                return new ResponseEntity<>(user,HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>("Error updating user entry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteUserById(String id) {
        try {
            if (!userRepository.existsById(id)) {
                return new ResponseEntity<>("User entry not found for ID: " + id, HttpStatus.NOT_FOUND);
            }

            userRepository.deleteById(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting user entry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
