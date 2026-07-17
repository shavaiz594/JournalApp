package org.example.repository;

import org.bson.types.ObjectId;
import org.example.entity.Journalentry;
import org.example.entity.UserEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Userrepo extends MongoRepository<UserEntry, ObjectId> {
    UserEntry findByUsername(String username);
    UserEntry findByEmail(String email);
    void deleteByUsername(String username);
}
