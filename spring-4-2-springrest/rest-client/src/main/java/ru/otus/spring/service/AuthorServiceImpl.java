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
import ru.otus.spring.domain.Author;

import java.util.*;

/**
 * AuthorServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String API_AUTHOR = "/api/author";

    @Value("${gateway.url}")
    private String url;

    private final RestTemplate rest = new RestTemplate();

    @Override
    public Author save(Author author) {
        return rest.postForObject(url.concat(API_AUTHOR), author, Author.class);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Long> uriParam = new HashMap<>();
        uriParam.put("id", id);
        rest.delete(url.concat(API_AUTHOR).concat("/{id}"), uriParam);
    }

    @Override
    public Optional<Author> getById(long id) {
        Map<String, Long> uriParam = new HashMap<>();
        uriParam.put("id", id);
        ResponseEntity<Optional<Author>> response = rest.exchange(url.concat(API_AUTHOR).concat("/{id}"),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {}, uriParam);
        return response.getBody();
    }

    @Override
    public Author getByBrief(String brief) {
        UriComponents builder = UriComponentsBuilder
                .fromHttpUrl(url.concat(API_AUTHOR))
                .queryParam("brief", brief)
                .build();
        ResponseEntity<Author> response = rest.exchange(builder.toUriString(), HttpMethod.GET, null,
                Author.class);
        return response.getBody();
    }

    @Override
    public List<Author> getAll() {
        ResponseEntity<List<Author>> response =
                rest.exchange(url.concat(API_AUTHOR), HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

}
