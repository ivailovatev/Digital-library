package com.finalProject.digitalLibrary.services;

import com.finalProject.digitalLibrary.models.*;
import com.finalProject.digitalLibrary.repository.ReaderRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.finalProject.digitalLibrary.messages.OutputMessages.SUCCESFULL_INSERTED_BOOK_TO_LIBRARY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest

class ReaderServiceTest {
    @Autowired
    private ReaderService readerService;
    @Mock
    private ReaderRepository readerRepository;

    @Test
    public void testGetInfoBookById() {
        BookView bookView = new BookView(1, "The Notebook", "Nicholas Sparks", false, 6.66);
        when(readerRepository.showBookByBookId(1)).thenReturn(bookView);
        assertEquals(readerService.getBookById(1), readerRepository.showBookByBookId(1));
        verify(readerRepository,times(1)).showBookByBookId(1);
    }

    @Test
    public void negativeTestGetInfoBookById() {
        BookView bookView = new BookView(1, "Under Igo", "Nicholas Sparks", false, 6.66);
        when(readerRepository.showBookByBookId(1)).thenReturn(bookView);
        assertNotEquals(readerRepository.showBookByBookId(1),readerService.getBookById(1));
        verify(readerRepository,times(1)).showBookByBookId(1);
    }



    @Test
    public void negativeTestSortHisOwnLibraryForAReaderByReaderName(){
        LibraryView libraryView1 = new LibraryView("For country,for lows","Mark\tCiceron");
        LibraryView libraryView2 = new LibraryView("Leters to Atticus","Mark\tCiceron");
        LibraryView libraryView3 = new LibraryView("The Notebook","Nicalas\tSparks");
        LibraryView libraryView4 = new LibraryView("The happy prince","Oscar\tWilde");
        LibraryView libraryView5 = new LibraryView("The happy prince","Oscar\tWilde");
        LibraryView libraryView6 = new LibraryView("The happy prince","Oscar\tWilde");
        LibraryView libraryView7 = new LibraryView("The wish","Nicholas Sparks");

        List<LibraryView> libraryViewList = new ArrayList<>();
        libraryViewList.add(libraryView1);
        libraryViewList.add(libraryView2);
        libraryViewList.add(libraryView3);
        libraryViewList.add(libraryView4);
        libraryViewList.add(libraryView5);
        libraryViewList.add(libraryView6);
        libraryViewList.add(libraryView7);

        when(readerRepository.showSortedLibraryByReaderName("Anton Almishev")).thenReturn(libraryViewList);
        assertNotEquals(readerRepository.showSortedLibraryByReaderName("Anton Almishev"),readerService.getSortedLibraryByReaderName("Ivailo Vatev"));
        verify(readerRepository,times(1)).showSortedLibraryByReaderName("Anton Almishev");

    }



    @Test
    public void negativeTestGetBooksInfoByAuthorIdtBy(){
        BookView bookView1 = new BookView(5,"The happy prince","Oscar\tWilde",true,5.66);
        BookView bookView2 = new BookView(7,"Fishman and his soul","Oscar\tWilde",true,0.0);
        BookView bookView3 = new BookView(6,"The bitrh day of Infanta","Oscar\tWilde",true,0.0);
        List<BookView> bookViewList = new ArrayList<>();
        bookViewList.add(bookView1);
        bookViewList.add(bookView2);
        bookViewList.add(bookView3);



        when(readerRepository.showBooksByAuthorId(3))
                .thenReturn(bookViewList);
        assertNotEquals(readerRepository.showBooksByAuthorId(3),readerService.getBooksByAuthorId(4));
        verify(readerRepository,times(1)).showBooksByAuthorId(3);
    }




    @Test
    public void testGetLastReadBookForReader(){
       String  lastReadBook = "The happy prince";
       when(readerRepository.showLastReadBookForReader(1)).thenReturn(lastReadBook);
       assertThat(readerService.getLastReadBookForReader(1)).isEqualTo(readerRepository.showLastReadBookForReader(1));
       verify(readerRepository,times(1)).showLastReadBookForReader(1);
    }

    @Test
    public void negativeTestGetLastReadBookForReader(){
        String  lastReadBook = "QUE VADIS";
        when(readerRepository.showLastReadBookForReader(1)).thenReturn(lastReadBook);
        assertThat(readerService.getLastReadBookForReader(1)).isNotEqualTo(readerRepository.showLastReadBookForReader(1));
        verify(readerRepository,times(1)).showLastReadBookForReader(1);
    }



    @Test
    public void testInsertToLibrary(){
        Library library = new Library(0,2,3);
        when(readerRepository.insertBookToLibrary(library.getUserId(),library.getBookId()))
                .thenReturn(SUCCESFULL_INSERTED_BOOK_TO_LIBRARY);
        assertEquals(readerRepository.insertBookToLibrary(2,3),readerService
                .addBookToLibrary(library.getUserId(),library.getBookId()));
        verify(readerRepository,times(1)).insertBookToLibrary(2,3);
    }


}