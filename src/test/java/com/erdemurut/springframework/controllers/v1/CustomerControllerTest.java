package com.erdemurut.springframework.controllers.v1;

import com.erdemurut.springframework.api.v1.model.CustomerDTO;
import com.erdemurut.springframework.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static com.erdemurut.springframework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {

	@Mock
	CustomerService customerService;

	@InjectMocks
	CustomerController customerController;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	@Test
	void getAllCustomers() throws Exception {
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstname("Michale");
		customer1.setLastname("Weston");
		customer1.setCustomerUrl("/api/v1/customer/1");

		CustomerDTO customer2 = new CustomerDTO();
		customer2.setFirstname("Sam");
		customer2.setLastname("Axe");
		customer2.setCustomerUrl("/api/v1/customer/2");

		when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.customers", hasSize(2)));
	}

	@Test
	void getCustomerByName() throws Exception {
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstname("Betül");
		customer1.setLastname("Ürüt");
		customer1.setCustomerUrl("/api/v1/customer/1");

		when(customerService.getCustomerByFirstName(anyString())).thenReturn(customer1);

		//when
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/findByName/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", equalTo("Betül")));
	}

	@Test
	void getCustomerById() throws Exception {
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstname("Erdem");
		customer1.setLastname("Ürüt");
		customer1.setCustomerUrl("/api/v1/customer/1");

		when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

		//when
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/findById/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", equalTo("Erdem")));
	}

	@Test
	void createNewCustomer() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname("Erdem");
		customerDTO.setLastname("Ürüt");

		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customerDTO.getFirstname());
		returnDTO.setLastname(customerDTO.getLastname());
		returnDTO.setCustomerUrl("/api/v1/customers/1");

		when(customerService.createNewCustomer(customerDTO)).thenReturn(returnDTO);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(customerDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstname", equalTo("Erdem")))
				.andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
	}

	@Test
	void testUpdateCustomer() throws Exception {
		//given
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstname("Erdem");
		customer.setLastname("Ürüt");

		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customer.getFirstname());
		returnDTO.setLastname(customer.getLastname());
		returnDTO.setCustomerUrl("/api/v1/customers/1");

		when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

		//when/then
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(customer)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", equalTo("Erdem")))
				.andExpect(jsonPath("$.lastname", equalTo("Ürüt")))
				.andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
	}

	@Test
	void testDeleteCustomer() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(customerService).deleteCustomerById(anyLong());
	}
}