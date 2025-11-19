package com.example.bloggingplatform.service;

import com.example.bloggingplatform.dto.CreatePostRequest;
import com.example.bloggingplatform.dto.UpdatePostRequest;
import com.example.bloggingplatform.model.BlogPost;
import com.example.bloggingplatform.repository.BlogPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BlogPostServiceTest {

    private BlogPostRepository repository;
    private BlogPostService service;

    @BeforeEach
    void setUp() {
        repository = mock(BlogPostRepository.class);
        service = new BlogPostService(repository);
    }

    @Test
    void createPost_savesAndReturns() {
        CreatePostRequest req = new CreatePostRequest();
        req.setTitle("Hello");
        req.setContent("Content");
        req.setAuthor("Alice");

        BlogPost saved = new BlogPost();
        saved.setId(1L);
        saved.setTitle(req.getTitle());
        saved.setContent(req.getContent());
        saved.setAuthor(req.getAuthor());

        when(repository.save(any())).thenReturn(saved);

        var resp = service.createPost(req);

        assertThat(resp.getId()).isEqualTo(1L);
        assertThat(resp.getTitle()).isEqualTo("Hello");

        ArgumentCaptor<BlogPost> cap = ArgumentCaptor.forClass(BlogPost.class);
        verify(repository).save(cap.capture());
        assertThat(cap.getValue().getAuthor()).isEqualTo("Alice");
    }

    @Test
    void updatePost_existing_updates() {
        BlogPost existing = new BlogPost();
        existing.setId(2L);
        existing.setTitle("Old");
        existing.setContent("Old content");

        when(repository.findById(2L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        UpdatePostRequest req = new UpdatePostRequest();
        req.setTitle("New");
        req.setContent("New content");

        var updated = service.updatePost(2L, req);
        assertThat(updated).isPresent();
        assertThat(updated.get().getTitle()).isEqualTo("New");
    }

    @Test
    void deletePost_nonExisting_returnsFalse() {
        when(repository.existsById(10L)).thenReturn(false);
        boolean res = service.deletePost(10L);
        assertThat(res).isFalse();
    }

    @Test
    void search_delegatesToRepo() {
        when(repository.searchByTerm("term")).thenReturn(List.of());
        var list = service.search("term");
        assertThat(list).isEmpty();
        verify(repository).searchByTerm("term");
    }
}
