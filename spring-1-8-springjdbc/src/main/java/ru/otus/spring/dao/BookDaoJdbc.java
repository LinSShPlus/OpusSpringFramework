package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * BookDaoJdbc
 **/
@RequiredArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("brief", book.getBrief())
                .addValue("title", book.getTitle())
                .addValue("text", book.getText())
                .addValue("authorId", book.getAuthorId())
                .addValue("genreId", book.getGenreId());

        jdbc.update("insert into Book (brief, title, text, authorId, genreId)\n" +
                        "values (:brief, :title, :text, :authorId, :genreId)", params, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public int update(Book book) {
        return jdbc.update("""
                        update Book
                           set brief = :brief, title = :title, text = :text, authorId = :authorId, genreId = :genreId
                         where id = :id""",
                Map.of("id", book.getId(),
                        "brief", book.getBrief(),
                        "title", book.getTitle(),
                        "text", book.getText(),
                        "authorId", book.getAuthorId(),
                        "genreId", book.getGenreId()));
    }

    @Override
    public int deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.update("delete from Book where id = :id", params);
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.queryForObject("select * from Book where id = :id", params, new BookMapper());
    }

    @Override
    public Book getByBrief(String brief) {
        Map<String, Object> params = Collections.singletonMap("brief", brief);
        return jdbc.queryForObject("select * from Book where brief = :brief", params, new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select * from Book", new BookMapper());
    }

    @Override
    public int count() {
        Integer count = jdbc.queryForObject("select count(*) from Book", Map.of(), Integer.class);
        return count == null ? 0 : count;
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            return Book
                    .builder()
                    .id(resultSet.getLong("id"))
                    .brief(resultSet.getString("brief"))
                    .title(resultSet.getString("title"))
                    .text(resultSet.getString("text"))
                    .authorId(resultSet.getLong("authorId"))
                    .genreId(resultSet.getLong("genreId"))
                    .build();
        }
    }

}
