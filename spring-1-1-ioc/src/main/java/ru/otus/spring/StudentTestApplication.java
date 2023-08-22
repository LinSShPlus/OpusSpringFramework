package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.StudentTestService;

/**
 * StudentTestApplication
 **/
public class StudentTestApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        StudentTestService service = context.getBean(StudentTestService.class);
        service.printTest();
    }
}
