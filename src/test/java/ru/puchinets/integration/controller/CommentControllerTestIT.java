package ru.puchinets.integration.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.puchinets.annotation.ITController;
import ru.puchinets.dto.authorization.Authification;
import ru.puchinets.enums.TagType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ITController
class CommentControllerTestIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }

    @Test
    void addComment() throws Exception {
        mvc.perform(post("/comments")
                        .sessionAttr("authification",
                                new Authification(1L, true, 1))
                        .sessionAttr("lastPage", "redirect:/tasks/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("subject", "some_subject")
                        .param("content", "some_content")
                        .param("userId", "1")
                        .param("taskId","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/tasks/1"));
    }
}