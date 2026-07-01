package org.example.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.example.Enums.Sentiment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Document(collection = "journalEntry")
@Data
@NoArgsConstructor
public class Journalentry {
    @Id
    private ObjectId id;

    private String name;
    private String type;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;
}
