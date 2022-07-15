package com.stackroute.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CarnivryUserId_generator")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdGenerator {
    @Id
    private String  id;
    private int seq;
}
