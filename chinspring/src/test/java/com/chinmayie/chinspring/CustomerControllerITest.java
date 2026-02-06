package com.chinmayie.chinspring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn400WhenHeaderIsMissing() throws Exception {
        // We try to GET customers without the X-User-Email header
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn200WithValidHeader() throws Exception {
        // Now we provide the header, it should work!
        mockMvc.perform(get("/api/v1/customers")
                        .header("X-User-Email", "test@test.com"))
                .andExpect(status().isOk());
    }
}