package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.SauceRepository;
import ru.otus.spring.domain.Sauce;

import java.util.List;
import java.util.Optional;

/**
 * SauceServiceImpl
 **/
@RequiredArgsConstructor
@Service
public class SauceServiceImpl implements SauceService {

    private final SauceRepository sauceRepository;

    @Transactional
    @Override
    public Sauce save(Sauce sauce) {
        return sauceRepository.save(sauce);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        sauceRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Sauce> getById(long id) {
        return sauceRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Sauce getByBrief(String brief) {
        return sauceRepository.findByBrief(brief);
    }

    @Override
    public List<Sauce> getAll() {
        return sauceRepository.findAll();
    }

}
