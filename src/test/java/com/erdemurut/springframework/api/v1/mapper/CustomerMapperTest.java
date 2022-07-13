package com.erdemurut.springframework.api.v1.mapper;

import com.erdemurut.springframework.api.v1.model.CustomerDTO;
import com.erdemurut.springframework.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

	public static final String FIRSTNAME = "Erdem";
	public static final String LASTNAME = "Ürüt";
	CustomerMapper customerMapper = CustomerMapper.INSTANCE;

	@Test
	void customerToCustomerDTO() {
		Customer customer = new Customer();
		customer.setFirstname(FIRSTNAME);
		customer.setLastname(LASTNAME);

		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

		assertEquals(FIRSTNAME, customerDTO.getFirstname());
		assertEquals(LASTNAME, customerDTO.getLastname());

	}

	@Test
	void customerDtoToCustomer() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRSTNAME);
		customerDTO.setLastname(LASTNAME);

		Customer customer = customerMapper.customerDtoToCustomer(customerDTO);

		assertEquals(FIRSTNAME, customer.getFirstname());
		assertEquals(LASTNAME, customer.getLastname());
	}
}