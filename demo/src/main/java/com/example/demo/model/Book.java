package com.example.demo.model;

import com.example.demo.annotation.CurrentYearOrLess;
import com.example.demo.auditable.BookAuditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Book extends BookAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String title;

    @Size(max = 1000)
    private String description;

    @NotNull
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH
            }
    )
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Review> reviews;

    private String coverImageRelativeUri;


    @CurrentYearOrLess
    @Min(0)
    private Integer firstPublicationYear;

    public Book() {}

    public Book(String title, String description, List<Author> authors, String coverImageRelativeUri, Integer firstPublicationYear) {
        this.title = title;
        this.description = description;
        this.authors = authors;
        this.coverImageRelativeUri = coverImageRelativeUri;
        this.firstPublicationYear = firstPublicationYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getCoverImageRelativeUri() {
        return coverImageRelativeUri;
    }

    public void setCoverImageRelativeUri(String coverImageRelativeUri) {
        this.coverImageRelativeUri = coverImageRelativeUri;
    }

    public Integer getFirstPublicationYear() {
        return firstPublicationYear;
    }

    public void setFirstPublicationYear(Integer firstPublicationYear) {
        this.firstPublicationYear = firstPublicationYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id) && title.equals(book.title) && Objects.equals(description, book.description) && Objects.equals(authors, book.authors) && Objects.equals(reviews, book.reviews) && Objects.equals(coverImageRelativeUri, book.coverImageRelativeUri) && Objects.equals(firstPublicationYear, book.firstPublicationYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, authors, reviews, coverImageRelativeUri, firstPublicationYear);
    }
}
