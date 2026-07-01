package org.example.entity;

import lombok.Data;
import lombok.NoArgsConstructor;;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Appcache")
@Data
@NoArgsConstructor
public class AppCacheEntity {
    private String key;
    private String value;
}
