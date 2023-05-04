package com.finalProject.digitalLibrary.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    private String title;
    private int authorId;
    private int fileId;
    private List<String> genreNames;
}
