package com.finalProject.digitalLibrary.repository;

import com.finalProject.digitalLibrary.models.ReaderView;
import com.finalProject.digitalLibrary.models.User;
import com.finalProject.digitalLibrary.repositoryInterfaces.CommonInterface;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.finalProject.digitalLibrary.messages.OutputMessages.*;

@Repository
public class CommonRepository implements CommonInterface {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CommonRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public String getUsername(int userId) {
        String sql = "                 " +
                "select username   " +
                "from users5           " +
                "where userId =:userId ";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("userId", userId);
        return namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, String.class);
    }

    @Override
    public List<ReaderView> showTitleAndAuthorsOfTheBooks() {

        String sql = "                                " +
                "select users5.userName, books5.title " +
                "from users5                          " +
                "inner join books5                    " +
                "on users5.userId=books5.userId       ";

        try {
            return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
                ReaderView readerView = new ReaderView();
                readerView.setAuthor(rs.getString("userName"));
                readerView.setTitle(rs.getString("title"));
                return readerView;
            });
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<ReaderView>();
        }
    }
    @Override
    public Integer insertAccount(String userName, String userPassword) {
        String sql = "                            " +
                "insert into users5               " +
                "(userName,userPassword)          " +
                "values (:userName,:userPassword) ";
        KeyHolder keyHolder=new GeneratedKeyHolder();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("userName", userName)
                .addValue("userPassword", userPassword);

        try {
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource,keyHolder, new String[]{"userId"});
            return Objects.requireNonNull(keyHolder.getKey()).intValue();
        }
        catch (EmptyResultDataAccessException ex){
            return 0;
        }
    }
    public void insertRoles(int userId,String roleName){
        String sql = "insert into roles5(roleName,userId) values(:roleName,:userId)";
        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("roleName",roleName)
                .addValue("userId",userId);
        namedParameterJdbcTemplate.update(sql,mapSqlParameterSource);
    }
    @Override
    public String updateUsername(String newUsername,String oldUsername){

        String sql="                          " +
                "update users5                " +
                "set username=:newUsername    " +
                "where username =:oldUsername ";

        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("newUsername",newUsername)
                .addValue("oldUsername",oldUsername);

        try {
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            return USERNAME_CHANGE_SUCCESSFUL;
        }
        catch (EmptyResultDataAccessException ex){
            return "";
        }
    }

    @Override
    public String updatePassword(String newPassword,String username){

        String sql="                           " +
                "update users5                 " +
                "set userPassword=:newPassword " +
                "where username =:username     ";

        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("newPassword",newPassword)
                .addValue("username",username);

        try {
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            return PASSWORD_CHANGE_SUCCESSFUL;
        }
        catch (EmptyResultDataAccessException ex){
            return "";
        }
    }
    @Override
    public Optional<User> findUserByUsername(String username){
        String sql="                                          " +
                "select userId,username,userPassword,isActive " +
                "from users5                                  " +
                "where username=:username                     ";

        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("username",username);

        try{
            return namedParameterJdbcTemplate.queryForObject(sql,mapSqlParameterSource,
                    (rs,rowNum)->Optional.of(
                            new User(
                                    rs.getInt("userId"),
                                    rs.getString("userName"),
                                    rs.getString("userPassword"),
                                    rs.getBoolean("isActive")
                            )
                    ));
        }
        catch (EmptyResultDataAccessException ex){
            return Optional.empty();
        }
    }
    @Override
    public Optional<String> checkIfUserIdExist(int userId) {
        String sql = "                        " +
                "select users5.userId         " +
                "from users5                  " +
                "where users5.userId =:userId ";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("userId", userId);

        try {
            return Optional
                    .ofNullable(namedParameterJdbcTemplate
                            .queryForObject(sql, mapSqlParameterSource, String.class));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

}