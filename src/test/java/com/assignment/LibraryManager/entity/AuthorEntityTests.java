package com.assignment.LibraryManager.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
public class AuthorEntityTests {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testAuthorEntity() {

        Author author = new Author();
        author.setId(1);
        author.setName("John Doe");
        author.setBiography("Biography");

        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        book1.setId(1);
        book1.setTitle("Book 1");
        book1.setAuthor("RD Sharma");

        Book book2 = new Book();
        book2.setId(2);
        book2.setTitle("Book 2");
        book2.setAuthor("HC Verma");

        books.add(book1);
        books.add(book2);

        author.setBooks(books);


        assertEquals(1, author.getId().intValue());
        assertEquals("John Doe", author.getName());
        assertEquals("Biography", author.getBiography());
        assertEquals(2, author.getBooks().size());
        assertEquals("Book 1", author.getBooks().get(0).getTitle());
        assertEquals("Book 2", author.getBooks().get(1).getTitle());
    }

}
