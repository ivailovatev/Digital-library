package com.finalProject.digitalLibrary.services;

import com.finalProject.digitalLibrary.models.ReaderView;
import com.finalProject.digitalLibrary.repository.CommonRepository;
import com.finalProject.digitalLibrary.validations.Validation;
import com.finalProject.digitalLibrary.exeptions.InvalidException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.finalProject.digitalLibrary.messages.ExceptionMessages.INVALID_USER_ID;

@Service
public class CommonService {
    private final CommonRepository commonRepository;
    private final Validation validation;

    public CommonService(CommonRepository commonRepository, Validation validation) {
        this.commonRepository = commonRepository;
        this.validation = validation;
    }

    public List<ReaderView> getAuthorsInfo(){
        return commonRepository.showTitleAndAuthorsOfTheBooks();
    }

    public Integer createAccount(String userName, String userPassword, String passwordConformation){
        validation.checkIfUsernameExists(userName);
        validation.passwordValidation(userPassword,passwordConformation);
        return commonRepository.insertAccount(userName,userPassword);
    }
    public void createRoles(int userId,String roleName){
        if(roleName.equals("reader") || roleName.equals("author")) {
            commonRepository.insertRoles(userId,roleName);
        }
    }

    public String changeUsername(String newUsername,String oldUsername){
        validation.checkIfUsernameExists(newUsername);
        validation.changeUsernameWithTheSameValidation(newUsername,oldUsername);
        return commonRepository.updateUsername(newUsername,oldUsername);
    }

    public String changePassword(String newPassword,String repeatPassword,String username){
        validation.passwordValidation(newPassword,repeatPassword);
        return commonRepository.updatePassword(newPassword,username);
    }

    public String getUsernameByUserId(int userId){
        commonRepository.checkIfUserIdExist(userId)
                .orElseThrow(()->new InvalidException(INVALID_USER_ID));
        return commonRepository.getUsername(userId);
    }

}