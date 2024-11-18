package com.JournalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.JournalApp.entity.JournalEntry;
import com.JournalApp.entity.User;
import com.JournalApp.repository.JournalEntryRepository;

@Service
public class JournalEntryService {

    @Autowired 
    JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry myEntry, String username){
        try {
            User user = userService.getUserByUserName(username);
            myEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(myEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
           System.out.println(e);
           throw new RuntimeException("An error occurred while saving the entry in Traction annotation");
        }
    }

    // method overloading for saveEntry ==> kyuki uperwala method JournalEntryController me createJournalEntry me use ho raha hai ( PostMapping )
    // niche wala method JournalEntryController me updateJournalEntry me use ho raha hai ( PutMapping ) kyuki update ke liye username ki jaroorat hi hai
    // --->   i.e., method overloading  ---> same name different parameter

    public void saveEntry(JournalEntry myEntry){
         journalEntryRepository.save(myEntry);
    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getSingleEntry(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteEntry(String username, ObjectId id){
        boolean removed = false;
       try {
        User user = userService.getUserByUserName(username);
         removed = user.getJournalEntries().removeIf(x -> x.getId() == id);
        // removeIf  -> boolean return kr raha hai then .,
        if(removed){
            userService.saveUser(user);
            journalEntryRepository.deleteById(id);
        }
       } catch (Exception e) {
        System.out.println(e);
        throw new RuntimeException("An error occurred while deleting the entry");
       }
       return removed;
    }

    


}
