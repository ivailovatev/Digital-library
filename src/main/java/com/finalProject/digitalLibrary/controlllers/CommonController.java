package com.finalProject.digitalLibrary.controlllers;


import com.finalProject.digitalLibrary.dtos.AccountRequest;
import com.finalProject.digitalLibrary.models.ReaderView;
import com.finalProject.digitalLibrary.services.CommonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/info")
public class CommonController {

    private final CommonService commonService;

    public CommonController(CommonService commonService) {
        this.commonService = commonService;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/authors")
    public List<ReaderView> authorsInfo(){
        return commonService.getAuthorsInfo();
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public Integer createAccount(@RequestBody AccountRequest accountRequest){
        int userId=commonService.createAccount(accountRequest.getUserName(),accountRequest.getUserPassword(),accountRequest.getPasswordConfirmation());
        commonService.createRoles(userId,accountRequest.getRoleName());
        return userId;
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/change/username/")
    public String changeUsername(@RequestParam String newUsername,@RequestParam String oldUsername){
        return commonService.changeUsername(newUsername,oldUsername);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/change/password/")
    public String changePassword(@RequestParam String newPassword,@RequestParam String repeatPassword,@RequestParam String username){
        return commonService.changePassword(newPassword,repeatPassword,username);
    }

}