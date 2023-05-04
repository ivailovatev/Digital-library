package com.finalProject.digitalLibrary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateRequest {
    private int userId;
    private int rate;
    private int bookId;
}
