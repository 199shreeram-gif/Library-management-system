package com.company.librarysystem.repository;

import com.company.librarysystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Spring Boot automatically translates this crazy long method name into a SQL "LIKE" query!
    // It will search for any book where the title OR the ISBN contains the word the user typed.
    List<Book> findByTitleContainingIgnoreCaseOrIsbnContainingIgnoreCase(String title, String isbn);

}