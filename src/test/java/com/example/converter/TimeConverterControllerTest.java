package com.example.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TimeConverterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private byte[] fromFile(String path) throws IOException {
        return new ClassPathResource(path).getInputStream().readAllBytes();
    }

    @Test
    public void defaultGet() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    public void convertTimeToMatchTime() throws Exception {
        this.mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"time\": \"[H1] 0:15.025\" }"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("00:15 - FIRST_HALF")));
    }

    @Async
    @Test
    public void convertTimeListToMatchTime() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        var result = this.mockMvc.perform(post("/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fromFile("timeList.json")))
                .andExpect(status().isOk()).andReturn();

        var resultContent = result.getResponse().getContentAsString();
        var apiResultList = objectMapper.readValue(resultContent, ArrayList.class);

        ArrayList<String> jsonFile = objectMapper.readValue(fromFile("responseTimeList.json"), ArrayList.class);
        Assert.assertEquals(apiResultList, jsonFile);
    }
}
