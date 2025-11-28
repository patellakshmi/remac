package com.qswar.hc.repository;

import com.qswar.hc.model.AllowlistUser; // Renamed
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Using Optional

@Repository
public interface AllowlistUserRepository extends JpaRepository<AllowlistUser, Long> {

    // Spring Data JPA provides automatic query generation for these methods.
    // No need for @Query annotation unless the query is complex.

    // Returns Optional for safer null handling
    Optional<AllowlistUser> findByEmail(String email);

    Optional<AllowlistUser> findByPhone(String phone);

    // Added a method to check for existence
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}