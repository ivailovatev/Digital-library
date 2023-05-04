package com.finalProject.digitalLibrary.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rate {
    private int rateId;
    private int userId;
    private int bookId;
    private int rate;
}
