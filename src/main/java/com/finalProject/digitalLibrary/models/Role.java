package com.finalProject.digitalLibrary.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {
    private int roleId;
    private String roleName;
    private int userId;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
