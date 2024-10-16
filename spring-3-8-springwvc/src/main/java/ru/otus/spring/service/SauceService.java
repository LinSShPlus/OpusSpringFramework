package ru.otus.spring.service;

import ru.otus.spring.domain.Sauce;

import java.util.List;
import java.util.Optional;

/**
 * SauceService
 **/
public interface SauceService {

    Sauce save(Sauce sauce);

    void deleteById(long id);

    Optional<Sauce> getById(long id);

    Sauce getByBrief(String brief);

    List<Sauce> getAll();

}
