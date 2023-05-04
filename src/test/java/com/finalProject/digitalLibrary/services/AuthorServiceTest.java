package com.finalProject.digitalLibrary.services;

import com.finalProject.digitalLibrary.models.Book;
import com.finalProject.digitalLibrary.models.File;
import com.finalProject.digitalLibrary.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.finalProject.digitalLibrary.messages.OutputMessages.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorService authorService;

    @Test
    public void negativeTestAddBook() {
        Book book1 = new Book(0, "Evil island", 3, 4, false);
        when(authorRepository.insertBook(book1)).thenReturn(33);
        assertNotEquals(authorRepository.insertBook(book1), authorService.createBook(book1));
        verify(authorRepository,times(1)).insertBook(book1);
    }

    @Test
    public void testInsertFile() {
        File file = new File(0, "File9.pdf");
        when(authorRepository.insertFile(file.getFileName())).thenReturn(SUCCESSFUL_CREATED_FILE);
        assertEquals(authorRepository.insertFile(file.getFileName()), authorService.createFile(file.getFileName()));
        verify(authorRepository,times(1)).insertFile(file.getFileName());
    }

    @Test
    public void testGetRateForBookWithId() {
        Double rate = 6.66;
        when(authorRepository.showRateForBookByBookId(1)).thenReturn(rate);
        assertEquals(authorRepository.showRateForBookByBookId(1),authorService.getRateForBookById(1));
        verify(authorRepository,times(1)).showRateForBookByBookId(1);
    }


    @Test
    public void testGetBookNumberOfTimesReadById() {
        int result = 2;
        Mockito.when(authorRepository.countHowMuchBookIsRead(1)).thenReturn(result);
        assertEquals(authorRepository.countHowMuchBookIsRead(1),authorService.getBookNumberOfTimesReadById(1));
        verify(authorRepository,times(1)).countHowMuchBookIsRead(1);
    }

    @Test
    public void negativeTestGetBookNumberOfTimesReadById() {
        int result = 4;
        Mockito.when(authorRepository.countHowMuchBookIsRead(1)).thenReturn(result);
        assertNotEquals(authorRepository.countHowMuchBookIsRead(1),authorService.getBookNumberOfTimesReadById(1));
        verify(authorRepository,times(1)).countHowMuchBookIsRead(1);
    }


    @Test
    public void testChangeBookStatus() {
        when(authorRepository.updateBookStatus(true, 1))
                .thenReturn(SUCCESSFUL_CHANGED_BOOK_STATUS);
        assertEquals(authorRepository.updateBookStatus(true,1),
                authorService.changeBookStatus(true, 1));
        verify(authorRepository,times(1)).updateBookStatus(true,1);
    }

    @Test
    public void negativeTestChangeBookStatus() {
        when(authorRepository.updateBookStatus(true, 1))
                .thenReturn(SUCCESSFUL_CHANGED_BOOK_STATUS);
        assertNotEquals(authorRepository.updateBookStatus(true,1),
                authorService.changeBookStatus(false, 1));
        verify(authorRepository,times(1)).updateBookStatus(true,1);
    }

}