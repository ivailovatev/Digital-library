package com.finalProject.digitalLibrary.controlllers;

import com.finalProject.digitalLibrary.dtos.BookRequest;
import com.finalProject.digitalLibrary.models.Book;
import com.finalProject.digitalLibrary.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.finalProject.digitalLibrary.messages.ExceptionMessages.NOT_RIGHT_AUTHOR;

@RestController
@RequestMapping("/author")
@PreAuthorize("hasRole('AUTHOR')")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/rate/{bookId}")
    public String getRateForBook(@PathVariable("bookId") int bookId,
                                 @AuthenticationPrincipal UserDetails userDetails ) {

        String username = userDetails.getUsername();
        String dataBaseUsername = authorService.getBookAuthorName(bookId);
        if (username.equals(dataBaseUsername)) {
            return authorService.getRateForBookById(bookId).toString();
        }
        return NOT_RIGHT_AUTHOR;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/count/{bookId}")
    public String countBookReadIsRead(@PathVariable("bookId") int bookId,
                                      @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        String dataBaseUsername = authorService.getBookAuthorName(bookId);
        if (username.equals(dataBaseUsername)) {
            return authorService.getBookNumberOfTimesReadById(bookId).toString();
        }
        return NOT_RIGHT_AUTHOR;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/file/{fileName}")
    public String createNewFile(@PathVariable("fileName") String fileName) {
        return authorService.createFile(fileName);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post")
    public Integer createNewBook(@RequestBody BookRequest bookRequest) {
        Book newBook = new Book();

        newBook.setTitle(bookRequest.getTitle());
        newBook.setUserId(bookRequest.getAuthorId());
        newBook.setFileId(bookRequest.getFileId());
        int bookId = authorService.createBook(newBook);
        for (String genreName : bookRequest.getGenreNames()) {
            authorService.addGenre(bookId,genreName);
        }

        return bookId;
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/status/{bookId}/{isActive}")
    public String setStatus(@PathVariable("bookId") int bookId, @PathVariable("isActive") boolean isActive,
                            @AuthenticationPrincipal UserDetails userDetails ) {

        String username = userDetails.getUsername();
        String dataBaseUsername = authorService.getBookAuthorName(bookId);
        if (username.equals(dataBaseUsername)) {
            return  authorService.changeBookStatus(isActive, bookId);
        }
        return NOT_RIGHT_AUTHOR;
    }

}