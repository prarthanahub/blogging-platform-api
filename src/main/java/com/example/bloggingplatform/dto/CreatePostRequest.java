package com.example.bloggingplatform.dto;

import jakarta.validation.constraints.NotBlank;

public class CreatePostRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String author;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
}
