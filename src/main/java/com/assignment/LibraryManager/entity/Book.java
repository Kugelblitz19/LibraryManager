package com.assignment.LibraryManager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
//@Table(name = "Book_tbl")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String author;
    private String isbn;
    private LocalDate publicationYear;
    @ManyToOne
    private Author authors;
    private boolean available;


    public Book(int i, String s, String s1, String number, LocalDate of) {
    }

    public Book(String book1, String author1, String isbn1, LocalDate now) {
    }
}
