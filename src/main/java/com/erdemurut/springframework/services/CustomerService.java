package com.erdemurut.springframework.services;

import com.erdemurut.springframework.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
	List<CustomerDTO> getAllCustomers();

	CustomerDTO getCustomerById(Long id);

	CustomerDTO getCustomerByFirstName(String name);

	CustomerDTO createNewCustomer(CustomerDTO customerDTO);

	CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO);

	CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);

	void deleteCustomerById(Long id);
}
