package com.JournalApp.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection= "journal_entries")
public class JournalEntry {

    // ObjectId => imp hai ab hus id ko na ( String and Long ) denge , directly every entry mongodb khud se id generate karega woh ObjectId use karnge as id
    // ab iss project me id ka type => ObjectId

    @Id
    private ObjectId id;

    private String title;

    private String content;

    private LocalDateTime date;

}


//
// import java.util.Date; -> use nhi kareng  -> LocalDateTime -> use karenge for saving time and date
// LocalDateTime -> ye JAVA 8 me aayi this