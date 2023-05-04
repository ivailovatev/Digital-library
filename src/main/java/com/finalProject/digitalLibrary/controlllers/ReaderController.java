package com.finalProject.digitalLibrary.controlllers;

import com.finalProject.digitalLibrary.dtos.LibraryRequest;
import com.finalProject.digitalLibrary.dtos.RateRequest;
import com.finalProject.digitalLibrary.models.*;
import com.finalProject.digitalLibrary.services.CommonService;
import com.finalProject.digitalLibrary.services.ReaderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.finalProject.digitalLibrary.messages.ExceptionMessages.NOT_RIGHT_PERSON;

@RestController
@RequestMapping("/books")
@PreAuthorize("hasRole('READER')")
public class ReaderController {

    private final ReaderService readerService;
    private final CommonService commonService;

    public ReaderController(ReaderService readerService, CommonService commonService) {
        this.readerService = readerService;
        this.commonService = commonService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{bookId}")
    public BookView getBookById(@PathVariable("bookId") Integer bookId) {
        return readerService.getBookById(bookId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/author/{authorId}")
    public List<BookView> viewCreativity(@PathVariable("authorId") int authorId) {
        return readerService.getBooksByAuthorId(authorId);

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me/last/{userId}")
    public String getLastReadBook(@PathVariable int userId, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        String dataBaseUsername = commonService.getUsernameByUserId(userId);
        if (username.equals(dataBaseUsername)) {
            return readerService.getLastReadBookForReader(userId);
        }
        return NOT_RIGHT_PERSON;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me/all/{readerId}")
    public List<LibraryEntry> getAllBooksFromReader(@PathVariable("readerId") int readerId, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        String dataBaseUsername = commonService.getUsernameByUserId(readerId);
        if (username.equals(dataBaseUsername)) {
            return readerService.getReaderBooksByReaderId(readerId);
        }
        return new ArrayList<LibraryEntry>();

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/rate")
    public String createNewRate(@RequestBody RateRequest rateRequest, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        String dataBaseUsername = commonService.getUsernameByUserId(rateRequest.getUserId());
        if (username.equals(dataBaseUsername)) {
            return readerService.createBookRate(rateRequest.getUserId(), rateRequest.getBookId(), rateRequest.getRate());
        }
        return NOT_RIGHT_PERSON;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public String insertBookToLibrary(@RequestBody LibraryRequest libraryRequest, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        String dataBaseUsername = commonService.getUsernameByUserId(libraryRequest.getUserId());
        if (username.equals(dataBaseUsername)) {
            return readerService.addBookToLibrary(libraryRequest.getUserId(), libraryRequest.getBookId());
        }
        return NOT_RIGHT_PERSON;

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/sort/library/{readerName}")
    public List<LibraryView> getSortedLibrary(@PathVariable String readerName, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        if (username.equals(readerName)) {
            return readerService.getSortedLibraryByReaderName(readerName);
        }
        return new ArrayList<LibraryView>();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/genre/{genreName}")
    public List<LibraryView> getBooksByGenreName(@PathVariable String genreName) {
        return readerService.getBooksByGenreName(genreName);
    }

}