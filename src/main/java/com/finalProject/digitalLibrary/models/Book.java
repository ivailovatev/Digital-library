package com.finalProject.digitalLibrary.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private int bookId;
    private String title;
    private int userId;
    private int fileId;
    private boolean isActive;
}
