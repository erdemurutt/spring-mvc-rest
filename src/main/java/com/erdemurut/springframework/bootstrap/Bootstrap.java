package com.erdemurut.springframework.bootstrap;

import com.erdemurut.springframework.domain.Category;
import com.erdemurut.springframework.domain.Customer;
import com.erdemurut.springframework.repositories.CategoryRepository;
import com.erdemurut.springframework.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
	private final CategoryRepository categoryRepository;
	private final CustomerRepository customerRepository;

	public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		loadCategories();
		loadCustomers();
	}

	private void loadCategories() {
		Category fruits = new Category();
		fruits.setName("Fruits");

		Category dried = new Category();
		dried.setName("Dried");

		Category fresh = new Category();
		fresh.setName("Fresh");

		Category exotic = new Category();
		exotic.setName("Exotic");

		Category nuts = new Category();
		nuts.setName("Nuts");

		categoryRepository.save(fruits);
		categoryRepository.save(dried);
		categoryRepository.save(fresh);
		categoryRepository.save(exotic);
		categoryRepository.save(nuts);

		System.out.println("Category Data Loaded --> " + categoryRepository.count());
	}

	private void loadCustomers() {
		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setFirstname("erdem");
		customer1.setLastname("ürüt");

		Customer customer2 = new Customer();
		customer2.setId(2L);
		customer2.setFirstname("betül");
		customer2.setLastname("ürüt");

		Customer customer3 = new Customer();
		customer3.setId(3L);
		customer3.setFirstname("şeyma");
		customer3.setLastname("kızılkaya");

		customerRepository.save(customer1);
		customerRepository.save(customer2);
		customerRepository.save(customer3);

		System.out.println("Customer Data Loaded --> " + customerRepository.count());
	}
}
