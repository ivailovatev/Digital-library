package com.finalProject.digitalLibrary.repositoryInterfaces;

import java.util.Optional;

public interface AdministratorInterface {

    public String updateUserStatus(boolean isActive, int userId);
    public Optional<String> userStatusValue(int userId);
    public Optional<String> checkIfUserIdExist(int userId);

}
