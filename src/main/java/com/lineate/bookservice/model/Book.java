package com.lineate.bookservice.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Book {
    private String id;
    private String title;
    private String author;
}