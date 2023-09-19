package ru.otus.spring.reader;

import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * InputReaderImpl
 **/
@Component
public class InputReaderImpl implements BaseReader<String> {

    @Override
    public String read() {
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

}
