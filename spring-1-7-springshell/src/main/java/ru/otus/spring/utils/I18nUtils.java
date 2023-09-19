package ru.otus.spring.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import ru.otus.spring.config.AppConfig;

import java.util.Locale;

/**
 * I18nUtils
 **/
@Component
public class I18nUtils {

    private final MessageSource messageSource;
    private final Locale locale;

    public I18nUtils(MessageSource messageSource, AppConfig config) {
        this.messageSource = messageSource;
        locale = config.getLocale() != null
                ? config.getLocale()
                : LocaleContextHolder.getLocale();
    }

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, locale);
    }

    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, locale);
    }

    public Locale getLocale() {
        return locale;
    }

}
