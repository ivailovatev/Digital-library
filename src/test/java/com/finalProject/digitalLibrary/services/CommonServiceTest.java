package com.finalProject.digitalLibrary.services;

import com.finalProject.digitalLibrary.models.ReaderView;
import com.finalProject.digitalLibrary.repository.CommonRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.finalProject.digitalLibrary.messages.OutputMessages.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class CommonServiceTest {
    @Mock
    private CommonRepository commonRepository;


    @Autowired
    private CommonService commonService;

    @Test
    public void negativeTestAllAuthorsWithTheirBookInfo(){

        ReaderView readerView1 = new ReaderView("Under Igo","Ivan Vazov");
        ReaderView readerView2 = new ReaderView("Not me","Frank Iso");
        List<ReaderView> readerViewList = new ArrayList<>();
        readerViewList.add(readerView1);
        readerViewList.add(readerView2);
        when(commonRepository.showTitleAndAuthorsOfTheBooks()).thenReturn(readerViewList);
        assertNotEquals(commonRepository.showTitleAndAuthorsOfTheBooks(),commonService.getAuthorsInfo());
        verify(commonRepository,times(1)).showTitleAndAuthorsOfTheBooks();
    }

    @Test
    public void testChangeUserName(){
        String username="Mariq Almisheva";
        String oldUsername="Mariq Ivanova";
        when(commonRepository
                .updateUsername(username,oldUsername))
                .thenReturn(USERNAME_CHANGE_SUCCESSFUL);
        assertEquals(commonRepository.updateUsername(username,oldUsername),
                commonService.changeUsername(username,oldUsername));
        verify(commonRepository,times(1)).updateUsername(username,oldUsername);
    }

}