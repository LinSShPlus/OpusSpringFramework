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
import ru.otus.spring.domain.BookComment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * BookCommentServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class BookCommentServiceImpl implements BookCommentService {

    private static final String API_BOOKCOMMENT = "/api/bookcomment";

    @Value("${gateway.url}")
    private String url;

    private final RestTemplate rest = new RestTemplate();

    @Override
    public BookComment save(BookComment bookComment) {
        return rest.postForObject(url.concat(API_BOOKCOMMENT), bookComment, BookComment.class);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Long> uriParam = new HashMap<>();
        uriParam.put("id", id);
        rest.delete(url.concat(API_BOOKCOMMENT).concat("/{id}"), uriParam);
    }

    @Override
    public Optional<BookComment> getById(long id) {
        Map<String, Long> uriParam = new HashMap<>();
        uriParam.put("id", id);
        ResponseEntity<Optional<BookComment>> response = rest.exchange(url.concat(API_BOOKCOMMENT).concat("/{id}"),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {}, uriParam);
        return response.getBody();
    }

    @Override
    public List<BookComment> getByBookId(long bookId) {
        UriComponents builder = UriComponentsBuilder
                .fromHttpUrl(url.concat(API_BOOKCOMMENT))
                .queryParam("bookId", bookId)
                .build();
        ResponseEntity<List<BookComment>> response = rest.exchange(builder.toUriString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

}
