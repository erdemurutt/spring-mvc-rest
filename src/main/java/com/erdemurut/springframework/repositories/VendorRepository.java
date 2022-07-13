package com.erdemurut.springframework.repositories;

import com.erdemurut.springframework.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
	Vendor findByName(String name);
}
