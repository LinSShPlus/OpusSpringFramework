package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.service.ProducerService;

@SpringBootApplication
public class LibraryApplicationProducer {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(LibraryApplicationProducer.class, args);
		ProducerService service = context.getBean(ProducerService.class);
		service.start();
	}

}
