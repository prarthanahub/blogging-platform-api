package com.example.bloggingplatform.controller;

import com.example.bloggingplatform.dto.CreatePostRequest;
import com.example.bloggingplatform.dto.UpdatePostRequest;
import com.example.bloggingplatform.service.BlogPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BlogPostController.class)
class BlogPostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BlogPostService service;

    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    void setUp() { }

    @Test
    void createPost_returnsCreated() throws Exception {
        CreatePostRequest req = new CreatePostRequest();
        req.setTitle("T"); req.setContent("C"); req.setAuthor("A");
        when(service.createPost(any())).thenReturn(null); // we only assert status here

        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
    }

    @Test
    void updatePost_notFound_returns404() throws Exception {
        UpdatePostRequest req = new UpdatePostRequest();
        req.setTitle("x"); req.setContent("y");
        when(service.updatePost(Mockito.eq(999L), any())).thenReturn(java.util.Optional.empty());

        mockMvc.perform(put("/api/posts/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePost_notFound_returns404() throws Exception {
        when(service.deletePost(123L)).thenReturn(false);
        mockMvc.perform(delete("/api/posts/123")).andExpect(status().isNotFound());
    }
}
