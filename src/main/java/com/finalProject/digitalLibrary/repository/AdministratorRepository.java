package com.finalProject.digitalLibrary.repository;

import com.finalProject.digitalLibrary.repositoryInterfaces.AdministratorInterface;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.finalProject.digitalLibrary.messages.OutputMessages.SUCCESSFUL_CHANGED_BOOK_STATUS;

@Repository
public class AdministratorRepository implements AdministratorInterface {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AdministratorRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public String updateUserStatus(boolean isActive, int userId) {
        int status = 1;
        if (!isActive) status = 0;

        String sql = "                " +
                "update users5        " +
                "set isActive=:status " +
                "where userId=:userId ";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("status", status)
                .addValue("userId", userId);

        try {
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            return SUCCESSFUL_CHANGED_BOOK_STATUS;
        } catch (EmptyResultDataAccessException ex) {
            return "";
        }
    }

    @Override
    public Optional<String> userStatusValue(int userId) {

        String sql = "                " +
                "select isActive      " +
                "from users5          " +
                "where userId=:userId ";

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

    @Override
    public Optional<String> checkIfUserIdExist(int userId) {

        String sql = "                " +
                "select userId        " +
                "from users5          " +
                "where userId=:userId ";

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