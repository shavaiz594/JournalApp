package org.example.repository;

import org.bson.types.ObjectId;
import org.example.Service.journalservice;
import org.example.entity.Journalentry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface journalrepo extends MongoRepository<Journalentry, ObjectId> {

}
