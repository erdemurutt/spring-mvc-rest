package com.erdemurut.springframework.services;

import com.erdemurut.springframework.api.v1.model.VendorDTO;
import com.erdemurut.springframework.api.v1.model.VendorListDTO;

public interface VendorService {
	VendorListDTO getAllVendors();

	VendorDTO getVendorByName(String name);

	VendorDTO getVendorById(Long id);

	VendorDTO createVendor(VendorDTO vendorDTO);

	VendorDTO updateVendor(Long id, VendorDTO vendorDTO);

	VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

	void deleteVendorById(Long id);
}

