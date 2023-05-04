package com.finalProject.digitalLibrary.services;

import com.finalProject.digitalLibrary.models.User;
import com.finalProject.digitalLibrary.repository.AdministratorRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.finalProject.digitalLibrary.messages.OutputMessages.SUCCESSFUL_CHANGED_USER_STATUS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class AdministratorServiceTest {

    @Mock
    private AdministratorRepository administratorRepository;
    @Autowired
    private AdministratorService administratorService;


    @Test
    public void testChangeUserStatus(){
     User user = new User(2,"Ivailo Vatev", "222",true);

     when(administratorRepository.updateUserStatus(user.isActive(),user.getUserId()))
             .thenReturn(SUCCESSFUL_CHANGED_USER_STATUS);

     assertEquals(administratorRepository.updateUserStatus(user.isActive(),user.getUserId())
             ,administratorService.changeUserStatus(false,user.getUserId()));

     verify(administratorRepository,times(1)).updateUserStatus(user.isActive(),user.getUserId());
    }

    @Test
    public void negativeTestChangeUserStatus(){
        User user = new User(1,"Anton Almishev", "111",true);

       when(administratorRepository.updateUserStatus(user.isActive(),user.getUserId()))
                .thenReturn(SUCCESSFUL_CHANGED_USER_STATUS);

        assertNotEquals(administratorRepository.updateUserStatus(user.isActive(),user.getUserId()),
                administratorService.changeUserStatus(true,user.getUserId()));

        verify(administratorRepository,times(1)).updateUserStatus(user.isActive(),user.getUserId());
    }

}