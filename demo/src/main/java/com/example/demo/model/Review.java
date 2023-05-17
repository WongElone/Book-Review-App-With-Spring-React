package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Objects;


@Entity
@Table
public class Review extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY,
            optional = false, // tell database the relationship is required
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH,
            }) // optional element is set to false for non-null relationship.
    @JoinColumn(nullable = false) // nullable = false tell Hibernate the relationship is required
    private Book book;

    @NotBlank
    @Size(min = 3, max = 255)
    private String title;

    @NotBlank
    @Lob
    private String body;

    @NotNull
    @Max(5)
    @Min(1)
    private Integer rating;

//    @CreatedDate
//    @Column(updatable = false)
//    private Instant createdAt;

    public Review() {}

    public Review(Book book, String title, String body, Integer rating) {
        this.book = book;
        this.title = title;
        this.body = body;
        this.rating = rating;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(Id, review.Id) && Objects.equals(book, review.book) && Objects.equals(title, review.title) && Objects.equals(body, review.body) && Objects.equals(rating, review.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, book, title, body, rating);
    }

    @Override
    public String toString() {
        return "Review{" +
                "Id=" + Id +
                ", book=" + book +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", rating=" + rating +
                '}';
    }
}
