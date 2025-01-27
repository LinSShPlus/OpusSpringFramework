package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.otus.spring.domain.Genre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * GenreServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private static final String API_GENRE = "/api/genre";

    @Value("${gateway.url}")
    private String url;

    private final RestTemplate rest = new RestTemplate();

    @Override
    public Genre save(Genre genre) {
        return rest.postForObject(url.concat(API_GENRE), genre, Genre.class);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Long> uriParam = new HashMap<>();
        uriParam.put("id", id);
        rest.delete(url.concat(API_GENRE).concat("/{id}"), uriParam);
    }

    @Override
    public Optional<Genre> getById(long id) {
        Map<String, Long> uriParam = new HashMap<>();
        uriParam.put("id", id);
        ResponseEntity<Optional<Genre>> response = rest.exchange(url.concat(API_GENRE).concat("/{id}"),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {}, uriParam);
        return response.getBody();
    }

    @Override
    public Genre getByBrief(String brief) {
        UriComponents builder = UriComponentsBuilder
                .fromHttpUrl(url.concat(API_GENRE))
                .queryParam("brief", brief)
                .build();
        ResponseEntity<Genre> response = rest.exchange(builder.toUriString(), HttpMethod.GET, null,
                Genre.class);
        return response.getBody();
    }

    @Override
    public List<Genre> getAll() {
        ResponseEntity<List<Genre>> response =
                rest.exchange(url.concat(API_GENRE), HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

}
