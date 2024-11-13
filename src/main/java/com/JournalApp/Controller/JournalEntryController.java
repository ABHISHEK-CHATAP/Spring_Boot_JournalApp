package com.JournalApp.Controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JournalApp.entity.JournalEntry;
import com.JournalApp.service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping
    public List<JournalEntry> getJournalEntries(){
        return journalEntryService.getAllEntries();
    }

    @PostMapping
    public boolean createJournalEntries(@RequestBody JournalEntry entry){
        journalEntryService.saveEntry(entry);
        return true;
    }

    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId){
        return journalEntryService.getSingleEntry(myId).orElse(null);
    }

    @DeleteMapping("/id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId myId){
         journalEntryService.deleteEntry(myId);
         return true;
    }

    @PutMapping("/id/{myId}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        JournalEntry oldEntry = journalEntryService.getSingleEntry(myId).orElse(null);
        if(oldEntry != null){
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
        }
        journalEntryService.saveEntry(oldEntry);
        return oldEntry;
    }



}
