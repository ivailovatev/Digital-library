package com.finalProject.digitalLibrary.repository;


import com.finalProject.digitalLibrary.models.*;
import com.finalProject.digitalLibrary.exeptions.InvalidException;
import com.finalProject.digitalLibrary.repositoryInterfaces.ReaderInterface;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.finalProject.digitalLibrary.messages.ExceptionMessages.THE_RATE_SHOULD_BE_BETWEEN_ONE_AND_TЕN;
import static com.finalProject.digitalLibrary.messages.OutputMessages.*;

@Repository
public class ReaderRepository implements ReaderInterface {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ReaderRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    @Override
    public BookView showBookByBookId(int bookId) {
        String sql = "" +
                " SELECT books5.bookId, users5.userName, books5.title,books5.status                      " +
                ", trunc(avg(rate),2) as avgRate                                                         " +
                "                FROM rates5                                                             " +
                "                RIGHT OUTER JOIN books5                                                 " +
                "                on rates5.bookId=books5.bookId                                          " +
                "                left outer join users5                                                  " +
                "                on books5.userId=users5.userId                                          " +
                "                where books5.bookId=:bookId                                             " +
                "                GROUP BY books5.bookId,users5.userName, books5.title ,books5.status     ";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("bookId", bookId);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, (rs, rowNum) -> {
                BookView view = new BookView();
                view.setBookId(rs.getInt("bookId"));
                view.setTitle(rs.getString("title"));
                view.setUserName(rs.getString("userName"));
                view.setActive(rs.getBoolean("status"));
                view.setAvgRate(rs.getDouble("avgRate"));
                return view;
            });
        }
        catch (EmptyResultDataAccessException ex) {
            return new BookView();
        }
    }

    @Override
    public List<BookView> showBooksByAuthorId(int authorId) {
        String sql = "                                                                                                      " +
                "select books5.bookId, users5.userName, books5.title,books5.status , trunc(avg(rate),2) as avgRate          " +
                "                from rates5                                                                                " +
                "                full outer join books5                                                                     " +
                "                on rates5.bookId=books5.bookId                                                             " +
                "                right outer join users5                                                                    " +
                "                on books5.userId=users5.userId                                                             " +
                "                where users5.userId =:userId                                                               " +
                "                GROUP BY books5.bookId,users5.userName, books5.title ,books5.status                        ";


        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("userId", authorId);
        try {
            return namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, (rs, rowNum) -> {
                BookView view = new BookView();
                view.setBookId(rs.getInt("bookId"));
                view.setTitle(rs.getString("title"));
                view.setUserName(rs.getString("userName"));
                view.setActive(rs.getBoolean("status"));
                view.setAvgRate(rs.getDouble("avgRate"));
                return view;
            });
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<BookView>();
        }
    }
    @Override
    public String showLastReadBookForReader(int userId) {

        String sql = " select books5.title                                                  " +
                "      from libraries5 inner join books5 on libraries5.bookId =books5.bookId" +
                "      where libraries5.libraryid=                                          " +
                "      ( select max(libraryId) from libraries5 where userId=:userId)          " ;
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, String.class);
        } catch (EmptyResultDataAccessException ex) {
            return "";
        }
    }

    @Override
    public List<LibraryEntry> showListOfReadBookForAReaderByReaderId(int readerId) {

        String sql = "                                      " +
                "select  books5.title, genres5.genreName    " +
                "from genres5                               " +
                "inner join books5                          " +
                "on genres5.bookId = books5.bookId          " +
                "inner join libraries5                      " +
                "on books5.bookId = libraries5.BookId       " +
                "where libraries5.userId=:userId            ";


        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("userId", readerId);

        try {
            return namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, (rs, rowNum) -> {
                LibraryEntry libraryView=new LibraryEntry();
                libraryView.setTitle(rs.getString("title"));
                return libraryView;
            });
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<LibraryEntry>();
        }

    }
    @Override
    public String insertBookRate(int userId, int bookId, int rate) {
        if(rate>0&&rate<11) {
            String sql = "insert into rates5 (userId,bookId,rate) values (:userId,:bookId,:rate)";
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("userId", userId);
            mapSqlParameterSource.addValue("bookId", bookId);
            mapSqlParameterSource.addValue("rate", rate);
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            return SUCCESSFUL_CREATED_RATE_FOR_BOOK;
        }
        throw  new InvalidException(THE_RATE_SHOULD_BE_BETWEEN_ONE_AND_TЕN );
    }
    @Override
    public String insertBookToLibrary(int userId, int bookId)  {
        if(checkBookStatus(bookId)) {
            String sql = "insert into libraries5 (userId,bookId) values(:userId,:bookId)";

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                    .addValue("userId", userId)
                    .addValue("bookId", bookId);
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            return SUCCESFULL_INSERTED_BOOK_TO_LIBRARY;
        }
         throw new InvalidException(BOOK_HAS_INACTIVE_STATUS);
    }

    private Boolean checkBookStatus(int bookId){
        String sql ="select status from books5 where bookId =:bookId";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
        .addValue("bookId", bookId);
         return namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource,Boolean.class);

    }
    @Override
    public List<LibraryView> showSortedLibraryByReaderName(String readerName) {

        String sql = "                                                                                         " +
                " SELECT                                                                                       " +
                "books5.title                                                                                  " +
                ",(select users5.userName from users5 where users5.userId=books5.userId) as author             " +
                "FROM                                                                                          " +
                "GENRES5 right OUTER join books5 on genres5.bookid = books5.bookId full outer join libraries5  " +
                "on books5.bookid = libraries5.bookid                                                          " +
                "RIGHT outer join users5 on libraries5.userId=users5.userId                                    " +
                "where users5.username like :userName                                                          " +
                "order by books5.title";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userName", readerName);
        try {
            return namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, (rs, rowNum) -> {
                LibraryView view = new LibraryView();
                view.setTitle(rs.getString("title"));
                view.setUserName(rs.getString("author"));
                return view;
            });
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<LibraryView>();
        }
    }
    @Override
    public List<LibraryView> showBooksByGenreName(String genreName) {
        String sql = " " +
                " SELECT                                                                                 " +
                " distinct(books5.title)                                                                 " +
                " ,(select users5.userName from users5 where users5.userId=books5.userId) as author      " +
                "  FROM                                                                                  " +
                "  GENRES5 inner  join books5 on genres5.bookId = books5.bookId inner join libraries5    " +
                "  on books5.bookId = libraries5.bookId                                                  " +
                "  inner join users5 on libraries5.userId=users5.userId                                  " +
                "  where genres5.genreName like :genreName"                                                ;
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("genreName", genreName);
        try {
            return namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, (rs, rowNum) -> {
                LibraryView view = new LibraryView();
                view.setTitle(rs.getString("title"));
                view.setUserName(rs.getString("author"));
                return view;
            });
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<LibraryView>();
        }
    }
    @Override
    public Optional<String> checkIfBookIdExist(int bookId){
        String sql="select bookId from books5 where bookId=:bookId";
        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("bookId",bookId);
        try {
            return Optional
                    .ofNullable(namedParameterJdbcTemplate
                            .queryForObject(sql,mapSqlParameterSource,String.class));
        }
        catch (EmptyResultDataAccessException ex){
            return Optional.empty();
        }
    }



    @Override
    public Optional<String> checkIfAuthorIdExist(int authorId){
        String sql="" +
                "select users5.userId              " +
                "from users5                       " +
                "inner join roles5                 " +
                "on users5.userId=roles5.userID    " +
                "where roles5.roleName='author'    " +
                "and roles5.userId=:authorId";
        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("authorId",authorId);
        try{
            return Optional
                    .ofNullable(namedParameterJdbcTemplate
                            .queryForObject(sql,mapSqlParameterSource,String.class));
        }
        catch (EmptyResultDataAccessException ex){
            return Optional.empty();
        }
    }
    @Override
    public Optional<String> checkIfReaderIdExist(int readerId){
        String sql="" +
                "select users5.userId           " +
                "from users5                    " +
                "inner join roles5              " +
                "on users5.userId=roles5.userID " +
                "where roles5.roleName='reader' " +
                "and roles5.userId=:authorId"     ;
        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("authorId",readerId);
        try{
            return Optional
                    .ofNullable(namedParameterJdbcTemplate
                            .queryForObject(sql,mapSqlParameterSource,String.class));
        }
        catch (EmptyResultDataAccessException ex){
            return Optional.empty();
        }
    }
    @Override
    public Optional<String> checkIfReaderNameExist(String readerName){
        String sql="select distinct users5.username\n" +
                "from users5\n" +
                "inner join roles5\n" +
                "on users5.userId=roles5.userID\n" +
                "where roles5.rolename='reader'\n" +
                "and users5.username=:readerName";
        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("readerName",readerName);
        try {
            return Optional
                    .ofNullable(namedParameterJdbcTemplate
                            .queryForObject(sql,mapSqlParameterSource,String.class));
        }
        catch (EmptyResultDataAccessException ex){
            return Optional.empty();
        }
    }
    @Override
    public Optional<String> checkIfGenreNameExist(String genreName){
        String sql="select distinct genreName from genres5 where genreName=:genreName";
        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("genreName",genreName);
        try {
            return Optional
                    .ofNullable(namedParameterJdbcTemplate
                            .queryForObject(sql,mapSqlParameterSource,String.class));
        }
        catch (EmptyResultDataAccessException ex){
            return Optional.empty();
        }
    }

}
