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

	public static final String API_URL = "/api/v1/customer/";
	private final CustomerMapper customerMapper;
	private final CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
		this.customerMapper = customerMapper;
		this.customerRepository = customerRepository;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository
				.findAll()
				.stream()
				.map(customer -> {
					CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
					customerDTO.setCustomerUrl(API_URL + customer.getId());
					return customerDTO;
				}).collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		return customerRepository.findById(id)
				.map(customerMapper::customerToCustomerDTO)
				.map(customerDTO -> {
					customerDTO.setCustomerUrl(API_URL + id);
					return customerDTO;
				})
				.orElseThrow(RuntimeException::new);
	}

	@Override
	public CustomerDTO getCustomerByFirstName(String name) {
		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customerRepository.findByFirstname(name));
		customerDTO.setCustomerUrl(API_URL + customerDTO.getId());
		return customerDTO;
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
		returnDTO.setCustomerUrl(API_URL + savedCustomer.getId());

		return returnDTO;
	}

	@Override
	public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
		return customerRepository.findById(id).map(customer -> {

			if (customerDTO.getFirstname() != null) {
				customer.setFirstname(customerDTO.getFirstname());
			}

			if (customerDTO.getLastname() != null) {
				customer.setLastname(customerDTO.getLastname());
			}

			CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
			returnDTO.setCustomerUrl(API_URL + id);

			return returnDTO;
		}).orElseThrow(RuntimeException::new);
	}

	@Override
	public void deleteCustomerById(Long id) {
		customerRepository.deleteById(id);
	}
}
