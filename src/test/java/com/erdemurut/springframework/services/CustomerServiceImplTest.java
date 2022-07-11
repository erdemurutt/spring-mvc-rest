package com.erdemurut.springframework.services;

import com.erdemurut.springframework.api.v1.mapper.CustomerMapper;
import com.erdemurut.springframework.api.v1.model.CustomerDTO;
import com.erdemurut.springframework.domain.Customer;
import com.erdemurut.springframework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceImplTest {

	@Mock
	CustomerRepository customerRepository;

	CustomerMapper customerMapper = CustomerMapper.INSTANCE;

	CustomerServiceImpl customerServiceImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		customerServiceImpl = new CustomerServiceImpl();
		customerServiceImpl.setCustomerMapper(customerMapper);
		customerServiceImpl.setCustomerRepository(customerRepository);
	}

	@Test
	void getAllCustomers() throws Exception {
		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setFirstname("Michale");
		customer1.setLastname("Weston");

		Customer customer2 = new Customer();
		customer2.setId(2L);
		customer2.setFirstname("Sam");
		customer2.setLastname("Axe");

		when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

		List<CustomerDTO> customerDTOS = customerServiceImpl.getAllCustomers();

		assertEquals(2, customerDTOS.size());

	}

	@Test
	void getCustomerById() throws Exception {
		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setFirstname("Michale");
		customer1.setLastname("Weston");

		when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer1));

		CustomerDTO customerDTO = customerServiceImpl.getCustomerById(1L);

		assertEquals("Michale", customerDTO.getFirstname());
	}

	@Test
	void createNewCustomer() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname("Jim");

		Customer savedCustomer = new Customer();
		savedCustomer.setFirstname(customerDTO.getFirstname());
		savedCustomer.setLastname(customerDTO.getLastname());
		savedCustomer.setId(1L);

		when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

		CustomerDTO savedDto = customerServiceImpl.createNewCustomer(customerDTO);

		assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
		assertEquals("/api/v1/customer/1", savedDto.getCustomerUrl());
	}
}