package com.example.demo.model;

import com.example.demo.annotation.CurrentYearOrLess;
import com.example.demo.auditable.BookAuditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;
import java.util.Date;
import java.util.List;

@Entity
@Table
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Getter
@Setter
@EqualsAndHashCode
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
}
