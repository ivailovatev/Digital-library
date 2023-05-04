package com.finalProject.digitalLibrary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    private String userName;
    private String userPassword;
    private String passwordConfirmation;
    private String roleName;
}
