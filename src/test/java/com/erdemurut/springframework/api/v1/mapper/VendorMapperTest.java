package com.erdemurut.springframework.api.v1.mapper;

import com.erdemurut.springframework.api.v1.model.VendorDTO;
import com.erdemurut.springframework.domain.Vendor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VendorMapperTest {

	public static final String NAME = "someName";

	VendorMapper vendorMapper = VendorMapper.INSTANCE;

	@Test
	void vendorToVendorDTO() {
		Vendor vendor = new Vendor();
		vendor.setName(NAME);

		VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);

		assertEquals(vendor.getName(), vendorDTO.getName());
	}

	@Test
	void vendorDTOtoVendor() {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);

		Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);

		assertEquals(vendorDTO.getName(), vendor.getName());
	}
}