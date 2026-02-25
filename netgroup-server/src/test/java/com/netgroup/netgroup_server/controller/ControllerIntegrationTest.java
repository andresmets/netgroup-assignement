package com.netgroup.netgroup_server.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@AutoConfigureMockMvc
class ControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void addAndBookAnEvent() throws Exception {
        JSONObject json = new JSONObject();
        json.put("eventName", "Ümber Ülemistejärve jooks");
        json.put("eventDate", new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date()));
        json.put("amount", 100);
        RequestBuilder rb = MockMvcRequestBuilders.post("/add/event").content(json.toString()).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(rb).andReturn();
        assertEquals(result.getResponse().getStatus(), 201);
        rb = MockMvcRequestBuilders.get("/get/events").contentType(MediaType.APPLICATION_JSON);
        result = mockMvc.perform(rb).andReturn();
        String responseContent = result.getResponse().getContentAsString();
        JSONArray array = new JSONArray(responseContent);
        assertEquals(1, array.length());
        JSONObject object = array.getJSONObject(0);
        assertEquals(1, object.get("id"));
        JSONObject bookingRequest = new JSONObject();
        bookingRequest.put("id", 1L);
        bookingRequest.put("firstName", "Andres");
        bookingRequest.put("lastName", "Mets");
        bookingRequest.put("personalCode", "1");
        rb =  MockMvcRequestBuilders.post("/book").content(bookingRequest.toString()).contentType(MediaType.APPLICATION_JSON);
        result = mockMvc.perform(rb).andReturn();
        assertEquals(201, result.getResponse().getStatus());
        rb =  MockMvcRequestBuilders.post("/book").content(bookingRequest.toString()).contentType(MediaType.APPLICATION_JSON);
        result = mockMvc.perform(rb).andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }
}