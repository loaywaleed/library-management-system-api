package com.library.rest.repo;

import com.library.rest.models.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatronRepo extends JpaRepository<Patron, Long> {
    Optional<Patron> findByName(String name);
}
