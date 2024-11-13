package com.JournalApp.repository;

//  Controller ----> Service ----> Repository

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.JournalApp.entity.JournalEntry;

    // ObjectId => imp hai ab hus id ko na ( String and Long ) denge , directly every entry mongodb khud se id generate karega woh ObjectId use karnge as id
    // ab iss project me id ka type => ObjectId



@Repository
public interface JournalEntryRepository extends  MongoRepository<JournalEntry, ObjectId>{



}
