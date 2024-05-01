package com.assignment.LibraryManager.repository;

import com.assignment.LibraryManager.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
