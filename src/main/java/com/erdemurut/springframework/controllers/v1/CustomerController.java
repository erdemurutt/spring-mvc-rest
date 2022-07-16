package com.erdemurut.springframework.controllers.v1;

import com.erdemurut.springframework.api.v1.model.CustomerDTO;
import com.erdemurut.springframework.api.v1.model.CustomerListDTO;
import com.erdemurut.springframework.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Customer Endpoints")
@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

	public static final String BASE_URL = "/api/v1/customers";
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Operation(summary = "Get all customers in JSON array format")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public CustomerListDTO getAllCustomers() {
		return new CustomerListDTO(customerService.getAllCustomers());
	}

	@Operation(summary = "Get customer by name parameter in JSON object format")
	@GetMapping("/findByName/{name}")
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO getCustomerByName(@PathVariable String name) {
		return customerService.getCustomerByFirstName(name);
	}

	@Operation(summary = "Get customer by id parameter in JSON object format")
	@GetMapping("/findById/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO getCustomerById(@Parameter(description = "Id of customer to be searched")
									   @PathVariable Long id) {
		return customerService.getCustomerById(id);
	}

	@Operation(summary = "Create new customer with request body parameters")
	@ApiResponse(responseCode = "201", description = "Successful create")
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
		return customerService.createNewCustomer(customerDTO);
	}

	@Operation(summary = "Find customer by id and update all fields")
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
		return customerService.saveCustomerByDTO(id, customerDTO);
	}

	@Operation(summary = "Find customer by id and update given field only")
	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
		return customerService.patchCustomer(id, customerDTO);
	}

	@Operation(summary = "Delete customer by id parameter")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteCustomer(@PathVariable Long id) {
		customerService.deleteCustomerById(id);
	}
}
