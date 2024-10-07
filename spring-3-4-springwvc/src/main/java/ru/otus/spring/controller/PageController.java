package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * PageController
 **/
@Controller
public class PageController {

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/authenticated")
    public String authenticatedPage() {
        return "authenticated";
    }

    @GetMapping("/success")
    public String successPage() {
        return "success";
    }

}
