package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.service.ExamService;

@SpringBootApplication
public class StudentTestApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(StudentTestApplication.class, args);
		ExamService examService = context.getBean(ExamService.class);
		examService.runExam();
	}

}
