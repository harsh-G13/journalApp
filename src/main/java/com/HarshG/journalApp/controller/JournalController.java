package com.HarshG.journalApp.controller;


import com.HarshG.journalApp.entity.JournalEntry;
import com.HarshG.journalApp.entity.User;
import com.HarshG.journalApp.service.JournalService;
import com.HarshG.journalApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import static javax.swing.UIManager.put;


@RestController
@RequestMapping("/journal")
class JournalController {
    Logger LOGGER = LoggerFactory.getLogger(JournalController.class);

    @Autowired
    public JournalService journalService;
    @Autowired
    public UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getJournalsEntriesOfUser(@PathVariable String userName) {
        return journalService.getJournals(userName);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<?> createJournalForUser(@RequestBody JournalEntry journalEntry, @PathVariable String userName) {
        if (journalEntry == null) {
            return new ResponseEntity<>("Invalid Request", HttpStatus.BAD_REQUEST);
        }
        if (userName != null) {
            return journalService.createJournal(userName, journalEntry);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getJournalById(@PathVariable String id) {

        LOGGER.info("Getting element with id:" + id);
        return journalService.getElementById(id);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> editJournalById(@PathVariable String id, @RequestBody JournalEntry journalEntry) {
        return journalService.editJournalById(id, journalEntry);
    }

    @DeleteMapping("/{userName}/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable String id,@PathVariable String userName) {
        return journalService.deleteJournalById(id,userName);
    }
}
