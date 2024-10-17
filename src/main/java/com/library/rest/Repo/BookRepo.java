package com.library.rest.Repo;
import com.library.rest.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepo extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

}
