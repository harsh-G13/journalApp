package com.HarshG.journalApp.repository;

import com.HarshG.journalApp.entity.JournalEntry;
import com.HarshG.journalApp.entity.User;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalRepository extends MongoRepository<JournalEntry,String> {

}
