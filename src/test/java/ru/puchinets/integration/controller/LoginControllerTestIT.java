package ru.puchinets.integration.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.puchinets.annotation.ITController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ITController
class LoginControllerTestIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc= MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }

    @Test
    void loginPage() throws Exception {
        mvc.perform(get(""))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void login_successful() throws Exception {
        mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("userName=user1&password=pass_user1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/tasks/my"));
    }

    @Test
    void login_unsuccessful() throws Exception {
        mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("userName=user1&password=badPass"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));

    }

    @Test
    void register() throws Exception {
        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("userName=testUser&firstname=testFN&lastname=testLN&birthDate=2024-05-30&password=qwerty&companyId=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/tasks/my"));
    }


    @Test
    void registerPage() throws Exception {
        mvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }
}