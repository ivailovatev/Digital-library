package com.finalProject.digitalLibrary.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookView {
    private int bookId;
    private String title;
    private String userName;
    private boolean isActive;
    private double avgRate;

}
