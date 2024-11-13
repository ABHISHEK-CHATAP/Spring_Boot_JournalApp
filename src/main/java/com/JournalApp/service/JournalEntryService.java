package com.JournalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JournalApp.entity.JournalEntry;
import com.JournalApp.repository.JournalEntryRepository;

@Service
public class JournalEntryService {

    @Autowired 
    JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(myEntry);
    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getSingleEntry(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteEntry(ObjectId id){
        journalEntryRepository.deleteById(id);
    }


}
