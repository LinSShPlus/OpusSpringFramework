package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * GenreDaoJdbc
 **/
@RequiredArgsConstructor
@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long insert(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("brief", genre.getBrief())
                .addValue("name", genre.getName());

        jdbc.update("insert into Genre (brief, name)\n" +
                "values (:brief, :name)", params, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public int update(Genre genre) {
        return jdbc.update("""
                        update Genre
                           set brief = :brief, name = :name
                         where id = :id""",
                Map.of("id", genre.getId(),
                        "brief", genre.getBrief(),
                        "name", genre.getName()));
    }

    @Override
    public int deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.update("delete from Genre where id = :id", params);
    }

    @Override
    public Genre getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.queryForObject("select * from Genre where id = :id", params, new GenreMapper());
    }

    @Override
    public Genre getByBrief(String brief) {
        Map<String, Object> params = Collections.singletonMap("brief", brief);
        return jdbc.queryForObject("select * from Genre where brief = :brief", params, new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from Genre", new GenreMapper());
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            return Genre
                    .builder()
                    .id(resultSet.getLong("id"))
                    .brief(resultSet.getString("brief"))
                    .name(resultSet.getString("name"))
                    .build();
        }
    }

}
