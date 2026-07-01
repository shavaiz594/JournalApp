package org.example.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.example.Enums.Sentiment;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Document(collection =  "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntry {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String username;
    @NonNull
    private String password;
    private String email;
    private boolean sentimentAnalysis;
    @DBRef
    private List<Journalentry> Journalentries=new ArrayList<Journalentry>();
    private List<String> roles;

}
