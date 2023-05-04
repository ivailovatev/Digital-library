package com.finalProject.digitalLibrary.repository;

import com.finalProject.digitalLibrary.models.Book;
import com.finalProject.digitalLibrary.repositoryInterfaces.AuthorInterface;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

import static com.finalProject.digitalLibrary.messages.OutputMessages.*;

@Repository
public class AuthorRepository implements AuthorInterface {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AuthorRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Double showRateForBookByBookId(int bookId) {
        String sql = "                            " +
                "select trunc(avg(rates5.rate),2) " +
                "from rates5                      " +
                "inner join books5                " +
                "on rates5.bookId=books5.bookId   " +
                "where books5.bookId =:bookId     ";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("bookId", bookId);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, Double.class);
        } catch (EmptyResultDataAccessException ex) {
            return 0.0;
        }
    }


    @Override
    public Integer countHowMuchBookIsRead(int bookId) {

        String sql = "                              " +
                "SELECT count(*) total_reading      " +
                "FROM books5                        " +
                "inner join libraries5              " +
                "on books5.bookId=libraries5.bookid " +
                "where books5.bookId =:bookId       ";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("bookId", bookId);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, Integer.class);
        } catch (EmptyResultDataAccessException ex) {
            return 0;
        }
    }

    @Override
    public String insertFile(String fileName) {

        String sql = "                         " +
                "insert into files5            " +
                "(filename)                    " +
                "values (:filename)            ";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("filename", fileName);

        try {
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            return SUCCESSFUL_CREATED_FILE;
        } catch (EmptyResultDataAccessException ex) {
            return "";
        }
    }

    @Override
    public Integer insertBook(Book book) {

        String sql = "                           " +
                "insert into books5              " +
                "(title,userId,fileId)           " +
                "values (:title,:userId,:fileId) ";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("userId", book.getUserId())
                .addValue("fileId", book.getFileId());

        try {
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource, keyHolder, new String[]{"bookId"});
            return Objects.requireNonNull(keyHolder.getKey()).intValue();
        } catch (EmptyResultDataAccessException ex) {
            return 0;
        }
    }

    @Override
    public String updateBookStatus(boolean isActive, int bookId) {
        int status = 1;
        if (!isActive) status = 0;

        String sql = "                       " +
                "update books5               " +
                "set status=:status          " +
                "where books5.bookId=:bookId ";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("status", status)
                .addValue("bookId", bookId);

        try {
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            return SUCCESSFUL_CHANGED_BOOK_STATUS;
        } catch (EmptyResultDataAccessException ex) {
            return "";
        }
    }

    @Override
    public Optional<String> checkIfBookIdExist(int bookId) {

        String sql = "                       " +
                "select bookId from          " +
                "books5 where bookId=:bookId ";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("bookId", bookId);

        try {
            return Optional
                    .ofNullable(namedParameterJdbcTemplate
                            .queryForObject(sql, mapSqlParameterSource, String.class));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> bookStatusValue(int bookId) {
        String sql = "                " +
                "select status        " +
                "from books5          " +
                "where bookId=:bookId ";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("bookId", bookId);

        try {
            return Optional
                    .ofNullable(namedParameterJdbcTemplate
                            .queryForObject(sql, mapSqlParameterSource, String.class));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }
    @Override
    public Optional<String> getAuthorNameByBookId(int bookId){
        String sql="select users5.username \n" +
                "from users5\n" +
                "inner join books5\n" +
                "on users5.userId=books5.userId\n" +
                "where books5.bookId=:bookId";
        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("bookId",bookId);
        try {
            return Optional
                    .ofNullable(namedParameterJdbcTemplate
                            .queryForObject(sql, mapSqlParameterSource, String.class));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void insertGenre(int bookId,String genreName){
        String sql ="insert into genres5(bookId,genreName) values(:bookId,:genreName)";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("bookId", bookId)
                .addValue("genreName",genreName);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }

}