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
import ru.otus.spring.domain.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * BookServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private static final String API_BOOK = "/api/book";

    @Value("${gateway.url}")
    private String url;

    private final RestTemplate rest = new RestTemplate();

    @Override
    public Book save(Book book) {
        return rest.postForObject(url.concat(API_BOOK), book, Book.class);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Long> uriParam = new HashMap<>();
        uriParam.put("id", id);
        rest.delete(url.concat(API_BOOK).concat("/{id}"), uriParam);
    }

    @Override
    public Optional<Book> getById(long id) {
        Map<String, Long> uriParam = new HashMap<>();
        uriParam.put("id", id);
        ResponseEntity<Optional<Book>> response = rest.exchange(url.concat(API_BOOK).concat("/{id}"),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {}, uriParam);
        return response.getBody();
    }

    @Override
    public Book getByBrief(String brief) {
        UriComponents builder = UriComponentsBuilder
                .fromHttpUrl(url.concat(API_BOOK))
                .queryParam("brief", brief)
                .build();
        ResponseEntity<Book> response = rest.exchange(builder.toUriString(), HttpMethod.GET, null,
                Book.class);
        return response.getBody();
    }

    @Override
    public List<Book> getAll() {
        ResponseEntity<List<Book>> response =
                rest.exchange(url.concat(API_BOOK), HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

}
