package ru.otus.spring.reader;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.otus.spring.config.AppConfig;
import ru.otus.spring.exception.StudentTestRuntimeException;
import ru.otus.spring.utils.I18nUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ResourceDataReaderImpl
 **/
@RequiredArgsConstructor
@Component
public class ResourceDataReaderImpl implements BaseReader<List<String[]>> {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final AppConfig appConfig;
    private final I18nUtils i18nUtils;

    @Override
    public List<String[]> read() {
        List<String[]> dataList = new ArrayList<>();
        final String fileName = appConfig.getFileName().getOrDefault(i18nUtils.getLocale(), "");
        final String delimiter = appConfig.getDelimiter();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream(fileName)));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(delimiter);
                dataList.add(values);
            }
            reader.close();
        } catch (StudentTestRuntimeException e) {
            logger.severe(e.getMessage());
        } catch (IOException e) {
            logger.log(Level.SEVERE, i18nUtils.getMessage("error.readFile", fileName), e);
        }

        return dataList;
    }

    private InputStream getInputStream(String fileName) {
        if (StringUtils.isEmpty(fileName))
            throw new StudentTestRuntimeException(i18nUtils.getMessage("error.testFileNameNotDefined"));
        InputStream stream = getClass().getResourceAsStream(fileName);
        if (stream == null)
            throw new StudentTestRuntimeException(i18nUtils.getMessage("error.readFile", fileName));
        return stream;
    }

}
