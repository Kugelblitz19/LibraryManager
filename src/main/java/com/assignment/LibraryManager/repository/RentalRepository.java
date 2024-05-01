package com.assignment.LibraryManager.repository;

import com.assignment.LibraryManager.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;
import java.util.List;

//@EnableJpaRepositories
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    List<Rental> findByReturnDateIsNullAndRentalDateBefore(LocalDate dueDate);
    //  List<Rental> findByReturnDateBeforeAndReturnedFalse(LocalDate today);

    // List<Rental> findByReturnDateIsNullAndRentalDateBefore(LocalDate dueDate);
}
