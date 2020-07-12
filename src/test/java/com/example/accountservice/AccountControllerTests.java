package com.example.accountservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.accountservice.account.Account;
import com.example.accountservice.account.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository repository;

    @Test
    public void shouldGetAnAccount() throws Exception {
        Account account = repository.save(new Account("TestAccount", false));

        this.mockMvc.perform(get("/accounts/" + account.getId())).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(account.getName())));
    }

    @Test
    public void shouldCreateAnAccount() throws Exception {
        this.mockMvc
                .perform(post("/accounts").content(asJsonString(new Account("TestAccount", false)))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void shouldNotSetTreasuryOnAnAlreadyCreatedAccount() throws Exception {
        Account account = repository.save(new Account("TestAccount", true));

        Account modification = new Account("TestChangeName", false);

        this.mockMvc
                .perform(put("/accounts/{id}", account.getId().toString()).content(asJsonString(modification))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("TestChangeName"))
                .andExpect(jsonPath("$.id").value(account.getId())).andExpect(jsonPath("$.treasury").value(true));
    }

    @Test
    public void shouldDeleteAccount() throws Exception {
        Account account = repository.save(new Account("TestAccount", true));

        this.mockMvc.perform(delete("/accounts/{id}", account.getId())).andExpect(status().isAccepted());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}