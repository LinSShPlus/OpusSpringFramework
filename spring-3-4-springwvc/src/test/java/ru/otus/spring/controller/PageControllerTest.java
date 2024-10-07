package ru.otus.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * PageControllerTest
 **/
@DisplayName("Класс PageController")
@WebMvcTest(PageController.class)
class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(username = "admin")
    @Test
    void positive_indexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "admin")
    @Test
    void positive_authenticatedPage() throws Exception {
        mockMvc.perform(get("/authenticated"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "admin")
    @Test
    void positive_successPage() throws Exception {
        mockMvc.perform(get("/success"))
                .andExpect(status().isOk());
    }

    @Test
    void negative_indexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void negative_authenticatedPage() throws Exception {
        mockMvc.perform(get("/authenticated"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void negative_successPage() throws Exception {
        mockMvc.perform(get("/success"))
                .andExpect(status().isUnauthorized());
    }

}
