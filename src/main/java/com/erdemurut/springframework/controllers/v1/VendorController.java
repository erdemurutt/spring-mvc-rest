package com.erdemurut.springframework.controllers.v1;

import com.erdemurut.springframework.api.v1.model.VendorDTO;
import com.erdemurut.springframework.api.v1.model.VendorListDTO;
import com.erdemurut.springframework.services.VendorService;
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

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
	public static final String BASE_URL = "/api/v1/vendors";

	private final VendorService vendorService;

	public VendorController(VendorService vendorService) {
		this.vendorService = vendorService;
	}

	@GetMapping("/getAllVendors")
	@ResponseStatus(HttpStatus.OK)
	public VendorListDTO getAllVendors() {
		return vendorService.getAllVendors();
	}

	@GetMapping("/findByName/{name}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO getVendorByName(@PathVariable String name) {
		return vendorService.getVendorByName(name);
	}

	@GetMapping("/findById/{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO getVendorById(@PathVariable Long id) {
		return vendorService.getVendorById(id);
	}

	@PostMapping("/createVendor")
	@ResponseStatus(HttpStatus.CREATED)
	public VendorDTO createVendor(@RequestBody VendorDTO vendorDTO) {
		return vendorService.createVendor(vendorDTO);
	}

	@PutMapping("/updateVendor/{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
		return vendorService.updateVendor(id, vendorDTO);
	}

	@PatchMapping("/patchVendor/{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
		return vendorService.patchVendor(id, vendorDTO);
	}

	@DeleteMapping("/deleteVendor/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteVendor(@PathVariable Long id) {
		vendorService.deleteVendorById(id);
	}
}
