package com.erdemurut.springframework.repositories;

import com.erdemurut.springframework.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Customer findByFirstname(String name);
}
