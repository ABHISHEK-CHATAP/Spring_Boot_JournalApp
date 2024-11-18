package com.JournalApp.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

// @Data ye lombok se => using single annotation use use ( Getter , Setter, AllArgsConstructor ) but ye ( NoArgsConstructor ) nhi deta jo ki required hai covert to JSON to POJO

@Document(collection = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private ObjectId id; 

    //solution --> application.properties for @Indexed
    @Indexed(unique = true)
    @NonNull
    private String username;

    @NonNull
    private String password;

    private List<String> roles;

    // har ek user ki bahut sari journalEntries hogi, I want to link that entries only for perticular user loggedIn
    // means jo user logged hai ussi ki sari journal entries ek Array me entries ki id stored karenge
    // By using @DBRef annotation

    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();

    // = new ArrayList<>() -> means jaise he user logged In hoga then,  journalEntries = []   ( journalEntries is equal to empty Array hoga , null nhi hoga)



}
