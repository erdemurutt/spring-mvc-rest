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

	CustomerService customerService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);

		customerService = new CustomerServiceImpl(customerMapper, customerRepository);
	}

	@Test
	void getAllCustomers() throws Exception {
		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setFirstname("Erdem");
		customer1.setLastname("Ürüt");

		Customer customer2 = new Customer();
		customer2.setId(2L);
		customer2.setFirstname("Betül");
		customer2.setLastname("Ürüt");

		when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

		List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

		assertEquals(2, customerDTOS.size());
	}

	@Test
	void getCustomerById() {
		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setFirstname("Erdem");
		customer1.setLastname("Ürüt");

		when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer1));

		CustomerDTO customerDTO = customerService.getCustomerById(1L);

		assertEquals("Erdem", customerDTO.getFirstname());
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

		CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);

		assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
		assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());
	}

	@Test
	void saveCustomerByDTO() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname("Jim");

		Customer savedCustomer = new Customer();
		savedCustomer.setFirstname(customerDTO.getFirstname());
		savedCustomer.setLastname(customerDTO.getLastname());
		savedCustomer.setId(1L);

		when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

		CustomerDTO savedDto = customerService.saveCustomerByDTO(1L, customerDTO);

		assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
		assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());
	}
}