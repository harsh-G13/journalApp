package com.HarshG.journalApp.service;

import com.HarshG.journalApp.entity.JournalEntry;
import com.HarshG.journalApp.entity.User;
import com.HarshG.journalApp.repository.JournalRepository;
import com.HarshG.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class JournalService {

    @Autowired
    public JournalRepository journalRepository;
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> getJournals(String userName) {
        User user = userRepository.findByUsername(userName);
        if (user != null && user.getJournalsEntries() != null) {
            return new ResponseEntity<>(user.getJournalsEntries(), HttpStatus.OK);
        } else return new ResponseEntity<>("No Data found", HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> createJournal(String userName, JournalEntry journalEntry) {
        try {
            User user = userRepository.findByUsername(userName);
            if (user == null) {
                throw new IllegalArgumentException("Invalid username");
            }

            JournalEntry savedEntry = journalRepository.save(journalEntry);
            List<JournalEntry> entries = user.getJournalsEntries();
            entries.add(savedEntry);
            user.setJournalsEntries(entries);
            userRepository.save(user);

            return new ResponseEntity<>(savedEntry, HttpStatus.CREATED);

        }catch (IllegalArgumentException ex){
            throw new IllegalArgumentException(ex.getMessage());
        }
        catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the entry", e);
        }
    }

    public ResponseEntity<?> createMultipleJournal(List<JournalEntry> journalEntries) {
        try {
            List<JournalEntry> savedEntries = journalRepository.saveAll(journalEntries);

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
            Optional<JournalEntry> journalEntry = journalRepository.findById(id);

            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Journal entry not found for ID: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching journal entry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> editJournalById(String id, JournalEntry journalEntry) {
        try {
            if (!journalRepository.existsById(id)) {
                return new ResponseEntity<>("Journal entry not found for ID: " + id, HttpStatus.NOT_FOUND);
            }

            journalEntry.setId(id); // Ensure the ID remains the same
            JournalEntry updatedEntry = journalRepository.save(journalEntry);
            return new ResponseEntity<>(updatedEntry, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating journal entry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteJournalById(String id,String userName) {
        try {
            if (!journalRepository.existsById(id)) {
                return new ResponseEntity<>("Journal entry not found for ID: " + id, HttpStatus.NOT_FOUND);
            }
            User user  = userRepository.findByUsername(userName);
            user.getJournalsEntries().removeIf(x-> x.getId().equals(id));
            journalRepository.deleteById(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting journal entry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
