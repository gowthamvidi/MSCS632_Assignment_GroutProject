package com.system.catalogbook.dto;

import lombok.Data;

@Data
public class BookRequest {
    private String title;
    private String author;
    private String genre;
    private Integer publicationYear;
}
