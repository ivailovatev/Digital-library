package com.finalProject.digitalLibrary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusRequest {
    private Boolean isActive;
    private int userId;
}
