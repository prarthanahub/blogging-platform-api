package com.example.bloggingplatform.repository;

import com.example.bloggingplatform.model.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    @Query("""
        SELECT b FROM BlogPost b
        WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :term, '%'))
           OR LOWER(b.content) LIKE LOWER(CONCAT('%', :term, '%'))
           OR LOWER(b.author) LIKE LOWER(CONCAT('%', :term, '%'))
    """)
    List<BlogPost> searchByTerm(@Param("term") String term);

    @Query("""
        SELECT b FROM BlogPost b
        WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :term, '%'))
           OR LOWER(b.content) LIKE LOWER(CONCAT('%', :term, '%'))
           OR LOWER(b.author) LIKE LOWER(CONCAT('%', :term, '%'))
    """)
    Page<BlogPost> searchByTerm(@Param("term") String term, Pageable pageable);
}
