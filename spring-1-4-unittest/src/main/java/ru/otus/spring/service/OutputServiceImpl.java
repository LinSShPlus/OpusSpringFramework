package ru.otus.spring.service;

import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * OutputServiceImpl
 **/
@Service
public class OutputServiceImpl implements OutputService {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void output(Object object) {
        logger.info(object::toString);
    }

}
