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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ITController
class TagControllerTestIT {

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
    void tagsPage() throws Exception {
        mvc.perform(get("/tags")
                        .sessionAttr("authification",
                                new Authification(1L, true, 1)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tags"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("newTag"))
                .andExpect(view().name("tags/tags"));
    }

    @Test
    void addTag() throws Exception {
        mvc.perform(post("/tags")
                        .sessionAttr("authification",
                                new Authification(1L, true, 1))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "some_name2")
                        .param("color", "some_color")
                        .param("type", TagType.BUGS.getName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/tags"));
    }

    @Test
    void tagEditPage() throws Exception {
        mvc.perform(get("/tags/1/edit")
                        .sessionAttr("authification",
                                new Authification(1L, true, 1)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tag"))
                .andExpect(model().attributeExists("types"))
                .andExpect(view().name("tags/edit"));
    }

    @Test
    void tagUpdate() throws Exception {
        mvc.perform(put("/tags/1")
                        .sessionAttr("authification",
                                new Authification(1L, true, 1))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "some_name")
                        .param("color", "some_color")
                        .param("type", TagType.BUGS.getName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/tags"));
    }

    @Test
    void tagDelete() throws Exception {
        mvc.perform(delete("/tags/3")
                        .sessionAttr("authification",
                                new Authification(1L, true, 1)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/tags"));
    }
}