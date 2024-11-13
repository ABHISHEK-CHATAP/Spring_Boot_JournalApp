package com.JournalApp.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JournalApp.entity.JournalEntry;

@RestController
@RequestMapping("/test-journal")
public class TestJournalController {

    private Map<Long, JournalEntry> entries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getJournalEntries(){
        return new ArrayList<>(entries.values());
    }

    @PostMapping
    public boolean  createJournalEntries(@RequestBody JournalEntry entry){
        // entries.put(entry.getId(), entry);
        return true;
    }

    // above line comment kia kyuki error degi,
    // becoz -> id hum ab na hi String and Long le rahe hai
    // ab id ObjectId le rahe hai jo Bson yani MongoDb ki Bson id hai
    // that;s why getting here error

    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable Long myId){
        return entries.get(myId);
    }

    @DeleteMapping("/id/{myId}")
    public JournalEntry deleteJournalEntryById(@PathVariable Long myId){
        return entries.remove(myId);
    }

    @PutMapping("/id/{myId}")
    public JournalEntry updateJournalEntryById(@PathVariable Long myId, @RequestBody JournalEntry myEntry){
        return entries.put(myId, myEntry);
    }

}
