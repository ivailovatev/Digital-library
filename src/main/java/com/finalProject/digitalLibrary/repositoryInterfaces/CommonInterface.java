package com.finalProject.digitalLibrary.repositoryInterfaces;

import com.finalProject.digitalLibrary.models.ReaderView;
import com.finalProject.digitalLibrary.models.User;

import java.util.List;
import java.util.Optional;

public interface CommonInterface {
    public String getUsername(int userId);
    public List<ReaderView> showTitleAndAuthorsOfTheBooks();
    public Integer insertAccount(String userName, String userPassword);
    public String updateUsername(String newUsername,String oldUsername);
    public String updatePassword(String newPassword,String username);
    public Optional<User> findUserByUsername(String username);
    public Optional<String> checkIfUserIdExist(int userId);

}
