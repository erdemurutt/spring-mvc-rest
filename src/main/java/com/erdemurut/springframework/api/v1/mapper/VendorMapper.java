package com.erdemurut.springframework.api.v1.mapper;

import com.erdemurut.springframework.api.v1.model.VendorDTO;
import com.erdemurut.springframework.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper {
	VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

	@Mapping(source = "id", target = "id")
	VendorDTO vendorToVendorDto(Vendor vendor);

	Vendor vendorDtoToVendor(VendorDTO vendorDTO);
}
