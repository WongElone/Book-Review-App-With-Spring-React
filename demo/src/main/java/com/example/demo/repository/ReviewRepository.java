package com.example.demo.repository;

import com.example.demo.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewsByBookId(Long bookId);
    List<Review> findReviewsByBookId(Long bookId, Sort sortable);
    Page<Review> findReviewsByBookId(Long bookId, Pageable pageable);
}
