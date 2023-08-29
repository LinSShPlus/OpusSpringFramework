package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.service.ExamService;

/**
 * StudentTestApplication
 **/
@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan
public class StudentTestApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(StudentTestApplication.class);
        ExamService examService = context.getBean(ExamService.class);
        examService.runExam();
    }

}
