package com.example.demo.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Integer score;

    public Test(Long id, Integer score) {
        Id = id;
        this.score = score;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return Objects.equals(Id, test.Id) && Objects.equals(score, test.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, score);
    }

    @Override
    public String toString() {
        return "Test{" +
                "Id=" + Id +
                ", score=" + score +
                '}';
    }
}
