package ru.otus.spring.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;

/**
 * AppConfig
 **/
@ConfigurationProperties(prefix = "application")
@Getter
@Setter
@NoArgsConstructor
@Component
public class AppConfig {

    private Locale locale;
    private Map<Locale, String> fileName;
    private String delimiter;
    private int allAnswersCount;
    private int minAnswersCount;

}
