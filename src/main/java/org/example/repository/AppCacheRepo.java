package org.example.repository;

import org.bson.types.ObjectId;
import org.example.Cache.AppCache;
import org.example.entity.AppCacheEntity;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;


public interface AppCacheRepo extends MongoRepository<AppCacheEntity, ObjectId> {

}
