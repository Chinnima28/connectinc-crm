package com.chinmayie.chinspring;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByOwnerEmail(String ownerEmail);
    Optional<Customer> findByIdAndOwnerEmail(Long id, String ownerEmail);
}
