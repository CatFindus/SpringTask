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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ITController
class TaskControllerTestIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }

    @Test
    void myTasksPage() throws Exception {
        mvc.perform(get("/tasks/my")
                        .sessionAttr("authification",
                                new Authification(1L, true, 1)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("name"))
                .andExpect(model().attributeExists("addr"))
                .andExpect(model().attributeExists("addr_name"))
                .andExpect(model().attributeExists("tasks"));
    }

    @Test
    void otherTasksPage() throws Exception {
        mvc.perform(get("/tasks/other")
                        .sessionAttr("authification",
                                new Authification(1L, true, 1)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tag"))
                .andExpect(model().attributeExists("types"));
    }

    @Test
    void detailsPage() throws Exception {
        mvc.perform(get("/tasks/1")
                        .sessionAttr("authification",
                                new Authification(1L, true, 1)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(model().attributeExists("lastPage"))
                .andExpect(model().attributeExists("comment"));
    }

    @Test
    void newTaskPage() throws Exception {
        mvc.perform(get("/tasks/new")
                        .sessionAttr("authification",
                                new Authification(1L, true, 1)))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(status().isOk())
                .andExpect(view().name("tasks/new"));
    }

    @Test
    void addTask() throws Exception {
        mvc.perform(post("/tasks")
                        .sessionAttr("authification",
                                new Authification(1L, true, 1))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("description", "some_description")
                        .param("content", "some_content")
                        .param("userId", "1")
                        .param("tagId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/tasks/other"));
    }

    @Test
    void editTaskPage() throws Exception {
        mvc.perform(get("/tasks/new")
                        .sessionAttr("authification",
                                new Authification(1L, true, 1)))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(status().isOk())
                .andExpect(view().name("tasks/new"));
    }

    @Test
    void editTask() throws Exception {
        mvc.perform(put("/tasks/1")
                        .sessionAttr("authification",
                                new Authification(1L, true, 1))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "1")
                        .param("description", "some_description")
                        .param("content", "some_content")
                        .param("userId", "1")
                        .param("tagId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/tasks/other"));
    }

    @Test
    void deleteTask() throws Exception {
        mvc.perform(delete("/tasks/2")
                        .sessionAttr("authification",
                                new Authification(1L, true, 1)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/tasks/other"));

    }
}