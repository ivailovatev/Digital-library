package com.finalProject.digitalLibrary.services;

import com.finalProject.digitalLibrary.models.*;
import com.finalProject.digitalLibrary.repository.ReaderRepository;
import com.finalProject.digitalLibrary.exeptions.InvalidException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.finalProject.digitalLibrary.messages.ExceptionMessages.*;

@Service
public class ReaderService {

    private final ReaderRepository readerRepository;

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public BookView getBookById(int bookId) {
        readerRepository.checkIfBookIdExist(bookId)
                .orElseThrow(()->new InvalidException(INVALID_BOOK_ID));
        return readerRepository.showBookByBookId(bookId);
    }

    public List<BookView> getBooksByAuthorId(int authorId) {
        readerRepository.checkIfAuthorIdExist(authorId)
                .orElseThrow(()->new InvalidException(INVALID_AUTHOR_ID));
        return readerRepository.showBooksByAuthorId(authorId);
    }

    public String getLastReadBookForReader(int userId) {
       return readerRepository.showLastReadBookForReader(userId);
    }

    public List<LibraryEntry> getReaderBooksByReaderId(int readerId){
        readerRepository.checkIfReaderIdExist(readerId)
                .orElseThrow(()->new InvalidException(INVALID_READER_ID));
        return readerRepository.showListOfReadBookForAReaderByReaderId(readerId);
    }
    public String createBookRate(int userId, int bookId, int rate) {
        readerRepository.checkIfBookIdExist(bookId)
                        .orElseThrow(()->new InvalidException(INVALID_BOOK_ID));
        readerRepository.checkIfReaderIdExist(userId)
                        .orElseThrow(()->new InvalidException(INVALID_READER_ID));
        return readerRepository.insertBookRate(userId,bookId,rate);
    }

    public String addBookToLibrary(int userId, int bookId){
        readerRepository.checkIfBookIdExist(bookId)
                        .orElseThrow(()->new InvalidException(INVALID_BOOK_ID));
        readerRepository.checkIfReaderIdExist(userId)
                        .orElseThrow(()->new InvalidException(INVALID_READER_ID));
        return readerRepository.insertBookToLibrary(userId,bookId);
    }

    public List<LibraryView> getSortedLibraryByReaderName(String readerName){
        readerRepository.checkIfReaderNameExist(readerName)
                .orElseThrow(()->new InvalidException(INVALID_READER_NAME));
        return readerRepository.showSortedLibraryByReaderName(readerName);
    }

    public List<LibraryView> getBooksByGenreName(String genreName){
        readerRepository.checkIfGenreNameExist(genreName)
                .orElseThrow(()->new InvalidException(INVALID_GENRE_NAME));
        return readerRepository.showBooksByGenreName(genreName);
    }

}