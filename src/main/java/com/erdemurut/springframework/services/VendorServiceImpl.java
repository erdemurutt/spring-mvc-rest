package com.erdemurut.springframework.services;

import com.erdemurut.springframework.api.v1.mapper.VendorMapper;
import com.erdemurut.springframework.api.v1.model.VendorDTO;
import com.erdemurut.springframework.api.v1.model.VendorListDTO;
import com.erdemurut.springframework.controllers.v1.VendorController;
import com.erdemurut.springframework.domain.Vendor;
import com.erdemurut.springframework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

	private final VendorMapper vendorMapper;
	private final VendorRepository vendorRepository;

	public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
		this.vendorMapper = vendorMapper;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public VendorListDTO getAllVendors() {
		List<VendorDTO> vendorDTOS = vendorRepository.findAll()
				.stream()
				.map(vendor -> {
					VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);
					vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
					return vendorDTO;
				}).collect(Collectors.toList());

		return new VendorListDTO(vendorDTOS);
	}

	@Override
	public VendorDTO getVendorByName(String name) {
		VendorDTO returnDTO = vendorMapper.vendorToVendorDto(vendorRepository.findByName(name));
		if (returnDTO != null) {
			returnDTO.setVendorUrl(getVendorUrl(returnDTO.getId()));
			return returnDTO;
		}
		throw new ResourceNotFoundException();
	}

	@Override
	public VendorDTO getVendorById(Long id) {
		return vendorRepository.findById(id)
				.map(vendorMapper::vendorToVendorDto)
				.map(vendorDTO -> {
					vendorDTO.setVendorUrl(getVendorUrl(id));
					return vendorDTO;
				})
				.orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public VendorDTO createVendor(VendorDTO vendorDTO) {
		return saveAndReturnVendorDTO(vendorMapper.vendorDtoToVendor(vendorDTO));
	}

	public VendorDTO saveAndReturnVendorDTO(Vendor vendor) {
		Vendor savedVendor = vendorRepository.save(vendor);
		VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(savedVendor);
		vendorDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));
		return vendorDTO;
	}

	@Override
	public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
		Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
		vendor.setId(id);

		return saveAndReturnVendorDTO(vendor);
	}

	@Override
	public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
		return vendorRepository.findById(id).map(vendor -> {
			if (vendorDTO.getName() != null) {
				vendor.setName(vendorDTO.getName());
			}

			VendorDTO returnDTO = vendorMapper.vendorToVendorDto(vendorRepository.save(vendor));
			returnDTO.setVendorUrl(getVendorUrl(id));
			return returnDTO;
		}).orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public void deleteVendorById(Long id) {
		if (vendorRepository.findById(id).isPresent()) {
			vendorRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException();
		}
	}

	private String getVendorUrl(Long id) {
		return VendorController.BASE_URL + "/" + id;
	}
}
