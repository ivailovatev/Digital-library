package com.finalProject.digitalLibrary.repositoryInterfaces;

import com.finalProject.digitalLibrary.models.BookView;
import com.finalProject.digitalLibrary.models.LibraryEntry;
import com.finalProject.digitalLibrary.models.LibraryView;

import java.util.List;
import java.util.Optional;

public interface ReaderInterface {
    public BookView showBookByBookId(int bookId);
    public List<BookView> showBooksByAuthorId(int authorId);
    public String showLastReadBookForReader(int userId);
    public List<LibraryEntry> showListOfReadBookForAReaderByReaderId(int readerId);
    public String insertBookRate(int userId, int bookId, int rate);
    public String insertBookToLibrary(int userId, int bookId);
    public List<LibraryView> showSortedLibraryByReaderName(String readerName);
    public List<LibraryView> showBooksByGenreName(String genreName);
    public Optional<String> checkIfBookIdExist(int bookId);
    public Optional<String> checkIfAuthorIdExist(int authorId);
    public Optional<String> checkIfReaderIdExist(int readerId);
    public Optional<String> checkIfReaderNameExist(String readerName);
    public Optional<String> checkIfGenreNameExist(String genreName);

}
