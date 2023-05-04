package com.finalProject.digitalLibrary.repositoryInterfaces;

import com.finalProject.digitalLibrary.models.Book;

import java.util.Optional;

public interface AuthorInterface {
    public Double showRateForBookByBookId(int bookId);
    public Integer countHowMuchBookIsRead(int bookId);
    public String insertFile(String fileName);
    public Integer insertBook(Book book);
    public String updateBookStatus(boolean isActive, int bookId);
    public Optional<String> checkIfBookIdExist(int bookId);
    public Optional<String> bookStatusValue(int bookId);
    public Optional<String> getAuthorNameByBookId(int bookId);
    public void insertGenre(int bookId,String genreName);
}
