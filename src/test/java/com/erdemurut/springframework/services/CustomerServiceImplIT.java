package com.erdemurut.springframework.services;

import com.erdemurut.springframework.api.v1.mapper.CustomerMapper;
import com.erdemurut.springframework.api.v1.model.CustomerDTO;
import com.erdemurut.springframework.bootstrap.Bootstrap;
import com.erdemurut.springframework.domain.Customer;
import com.erdemurut.springframework.repositories.CategoryRepository;
import com.erdemurut.springframework.repositories.CustomerRepository;
import com.erdemurut.springframework.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class CustomerServiceImplIT {
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	VendorRepository vendorRepository;

	CustomerService customerService;

	@BeforeEach
	public void setUp() throws Exception {
		System.out.println("Loading Customer Data");
		System.out.println(customerRepository.findAll().size());

		//setup data for testing
		Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
		bootstrap.run(); //load data

		customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
	}

	@Test
	void patchCustomerUpdateFirstName() {
		String updatedName = "UpdatedName";
		long id = getCustomerIdValue();

		Customer originalCustomer = customerRepository.getOne(id);
		assertNotNull(originalCustomer);
		//save original first name
		String originalFirstName = originalCustomer.getFirstname();
		String originalLastName = originalCustomer.getLastname();

		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(updatedName);

		customerService.patchCustomer(id, customerDTO);

		Customer updatedCustomer = customerRepository.findById(id).get();

		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getFirstname());
		assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstname())));
		assertThat(originalLastName, equalTo(updatedCustomer.getLastname()));
	}

	@Test
	void patchCustomerUpdateLastName() {
		String updatedName = "UpdatedName";
		long id = getCustomerIdValue();

		Customer originalCustomer = customerRepository.getOne(id);
		assertNotNull(originalCustomer);

		String originalFirstName = originalCustomer.getFirstname();
		String originalLastName = originalCustomer.getLastname();

		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setLastname(updatedName);

		customerService.patchCustomer(id, customerDTO);

		Customer updatedCustomer = customerRepository.findById(id).get();

		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getLastname());
		assertThat(originalFirstName, equalTo(updatedCustomer.getFirstname()));
		assertThat(originalLastName, not(equalTo(updatedCustomer.getLastname())));
	}

	private Long getCustomerIdValue() {
		List<Customer> customers = customerRepository.findAll();

		System.out.println("Customers Found: " + customers.size());

		return customers.get(0).getId();
	}
}
