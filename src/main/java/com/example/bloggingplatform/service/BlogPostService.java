package com.example.bloggingplatform.service;

import com.example.bloggingplatform.dto.CreatePostRequest;
import com.example.bloggingplatform.dto.PostResponse;
import com.example.bloggingplatform.dto.UpdatePostRequest;
import com.example.bloggingplatform.model.BlogPost;
import com.example.bloggingplatform.repository.BlogPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BlogPostService {

    private final BlogPostRepository repository;

    public BlogPostService(BlogPostRepository repository) {
        this.repository = repository;
    }

    public PostResponse createPost(CreatePostRequest req) {
        BlogPost b = new BlogPost();
        b.setTitle(req.getTitle());
        b.setContent(req.getContent());
        b.setAuthor(req.getAuthor());
        BlogPost saved = repository.save(b);
        return toResponse(saved);
    }

    public Optional<PostResponse> updatePost(Long id, UpdatePostRequest req) {
        return repository.findById(id).map(existing -> {
            existing.setTitle(req.getTitle());
            existing.setContent(req.getContent());
            BlogPost saved = repository.save(existing);
            return toResponse(saved);
        });
    }

    public boolean deletePost(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Optional<PostResponse> getPost(Long id) {
        return repository.findById(id).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> search(String term) {
        return repository.searchByTerm(term).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> search(String term, Pageable pageable) {
        return repository.searchByTerm(term, pageable).map(this::toResponse);
    }

    private PostResponse toResponse(BlogPost b) {
        PostResponse r = new PostResponse();
        r.setId(b.getId());
        r.setTitle(b.getTitle());
        r.setContent(b.getContent());
        r.setAuthor(b.getAuthor());
        r.setCreatedAt(b.getCreatedAt());
        r.setUpdatedAt(b.getUpdatedAt());
        return r;
    }
}
