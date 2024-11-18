package com.JournalApp.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JournalApp.entity.JournalEntry;
import com.JournalApp.entity.User;
import com.JournalApp.service.JournalEntryService;
import com.JournalApp.service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        String username = authenticatedUser.getName();
        User user = userService.getUserByUserName(username);
         List<JournalEntry> allEntries = user.getJournalEntries();
         if(allEntries != null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
         }
         return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createJournalEntries(@RequestBody JournalEntry entry){
       try {
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        String username = authenticatedUser.getName();
        journalEntryService.saveEntry(entry, username);
        return new ResponseEntity<>(entry, HttpStatus.CREATED);
       } catch (Exception e) {
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(  @PathVariable ObjectId myId){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        String username = authenticatedUser.getName();
        User user = userService.getUserByUserName(username);
        List<JournalEntry> gotJournalEntryById = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!gotJournalEntryById.isEmpty()){
            
            Optional<JournalEntry> journalEntry = journalEntryService.getSingleEntry( myId);
            if(journalEntry.isPresent()){
               return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
         return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        String username = authenticatedUser.getName();
         boolean removed = journalEntryService.deleteEntry(username, myId);
         if (removed){
             return new ResponseEntity<>( HttpStatus.NO_CONTENT);
         }else{
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
         }
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        String username = authenticatedUser.getName();
        User user = userService.getUserByUserName(username);
        List<JournalEntry> gotJournalEntryById = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!gotJournalEntryById.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.getSingleEntry( myId);
            if(journalEntry.isPresent()){
                // uper mili hui journalEntry Optional hai -> journalEntry.get() krna padega ;
                JournalEntry oldEntry = journalEntry.get();
                oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
                journalEntryService.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }

        //means null hai entry
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }



}
