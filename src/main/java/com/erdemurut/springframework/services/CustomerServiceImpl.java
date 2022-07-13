package com.erdemurut.springframework.services;

import com.erdemurut.springframework.api.v1.mapper.CustomerMapper;
import com.erdemurut.springframework.api.v1.model.CustomerDTO;
import com.erdemurut.springframework.domain.Customer;
import com.erdemurut.springframework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerMapper customerMapper;
	private final CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
		this.customerMapper = customerMapper;
		this.customerRepository = customerRepository;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository.findAll()
				.stream()
				.map(customer -> {
					CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
					customerDTO.setCustomerUrl("/api/v1/customer/" + customer.getId());
					return customerDTO;
				}).collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		return customerRepository.findById(id)
				.map(customerMapper::customerToCustomerDTO)
				.orElseThrow(RuntimeException::new);
	}

	@Override
	public CustomerDTO getCustomerByFirstName(String name) {
		return customerMapper.customerToCustomerDTO(customerRepository.findByFirstname(name));
	}

	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
		return saveAndReturnDTO(customerMapper.customerDtoToCustomer(customerDTO));
	}

	@Override
	public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
		Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
		customer.setId(id);

		return saveAndReturnDTO(customer);
	}

	public CustomerDTO saveAndReturnDTO(Customer customer) {
		Customer savedCustomer = customerRepository.save(customer);

		CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);
		returnDTO.setCustomerUrl("/api/v1/customer/" + savedCustomer.getId());

		return returnDTO;
	}
}
