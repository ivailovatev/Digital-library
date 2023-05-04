package com.finalProject.digitalLibrary.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LibraryEntry {
    private String title;
    private List<String> genres;
}