package com.HarshG.journalApp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("JournalEntry")
@Data
@NoArgsConstructor
public class JournalEntry {

  @Id
  private String id;
  private String title;
  private String content;


}
