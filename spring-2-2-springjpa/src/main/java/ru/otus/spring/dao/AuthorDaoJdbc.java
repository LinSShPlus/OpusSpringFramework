package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * AuthorDaoJdbc
 **/
@RequiredArgsConstructor
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long insert(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("brief", author.getBrief())
                .addValue("lastName", author.getLastName())
                .addValue("firstName", author.getFirstName());

        jdbc.update("insert into Author (brief, lastName, firstName)\n" +
                "values (:brief, :lastName, :firstName)", params, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public int update(Author author) {
        return jdbc.update("""
                        update Author
                           set brief = :brief, lastName = :lastName, firstName = :firstName
                         where id = :id""",
                Map.of("id", author.getId(),
                        "brief", author.getBrief(),
                        "lastName", author.getLastName(),
                        "firstName", author.getFirstName()));
    }

    @Override
    public int deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.update("delete from Author where id = :id", params);
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.queryForObject("select * from Author where id = :id", params, new AuthorMapper());
    }

    @Override
    public Author getByBrief(String brief) {
        Map<String, Object> params = Collections.singletonMap("brief", brief);
        return jdbc.queryForObject("select * from Author where brief = :brief", params, new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select * from Author", new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return Author
                    .builder()
                    .id(resultSet.getLong("id"))
                    .brief(resultSet.getString("brief"))
                    .lastName(resultSet.getString("lastName"))
                    .firstName(resultSet.getString("firstName"))
                    .build();
        }
    }

}
