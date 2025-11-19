package com.example.bloggingplatform.controller;

import com.example.bloggingplatform.dto.CreatePostRequest;
import com.example.bloggingplatform.dto.PostResponse;
import com.example.bloggingplatform.dto.UpdatePostRequest;
import com.example.bloggingplatform.service.BlogPostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {

    private final BlogPostService service;

    public BlogPostController(BlogPostService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody CreatePostRequest req) {
        PostResponse created = service.createPost(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @Valid @RequestBody UpdatePostRequest req) {
        return service.updatePost(id, req)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        boolean removed = service.deletePost(id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return service.getPost(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) String search, Pageable pageable) {
        if (search == null || search.isBlank()) {
            Page<PostResponse> page = service.getAllPosts(pageable);
            return ResponseEntity.ok(page);
        } else {
            Page<PostResponse> page = service.search(search, pageable);
            return ResponseEntity.ok(page);
        }
    }
}
