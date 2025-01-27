package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.service.MainService;

@SpringBootApplication
public class LibraryApplicationClient {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(LibraryApplicationClient.class, args);
		MainService service = context.getBean(MainService.class);
		service.start();
	}

}
