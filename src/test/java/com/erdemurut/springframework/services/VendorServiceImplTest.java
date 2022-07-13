package com.erdemurut.springframework.services;

import com.erdemurut.springframework.api.v1.mapper.VendorMapper;
import com.erdemurut.springframework.api.v1.model.VendorDTO;
import com.erdemurut.springframework.api.v1.model.VendorListDTO;
import com.erdemurut.springframework.domain.Vendor;
import com.erdemurut.springframework.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;

class VendorServiceImplTest {

	public static final String NAME_1 = "Vendor1";
	public static final long ID_1 = 1L;
	public static final String NAME_2 = "Vendor2";
	public static final long ID_2 = 1L;

	@Mock
	VendorRepository vendorRepository;

	VendorServiceImpl vendorService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);

		vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
	}

	@Test
	void getAllVendors() {
		List<Vendor> vendors = Arrays.asList(getFirstVendor(), getSecondVendor());
		given(vendorRepository.findAll()).willReturn(vendors);

		VendorListDTO vendorListDTO = vendorService.getAllVendors();

		//then
		then(vendorRepository).should(times(1)).findAll();
		assertThat(vendorListDTO.getVendors().size(), is(equalTo(2)));
	}

	@Test
	void getVendorById() {
		Vendor vendor = getFirstVendor();

		given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));

		VendorDTO vendorDTO = vendorService.getVendorById(1L);

		then(vendorRepository).should(times(1)).findById(anyLong());

		assertThat(vendorDTO.getName(), is(equalTo(NAME_1)));
	}

	@Test
	void createVendor() {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME_1);

		Vendor vendor = getFirstVendor();

		given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

		VendorDTO savedVendorDTO = vendorService.createVendor(vendorDTO);

		then(vendorRepository).should().save(any(Vendor.class));
		assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
	}

	@Test
	void updateVendor() {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME_1);

		Vendor vendor = getFirstVendor();

		given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

		VendorDTO savedVendorDTO = vendorService.updateVendor(ID_1, vendorDTO);

		then(vendorRepository).should().save(any(Vendor.class));
		assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
	}

	@Test
	void patchVendor() {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME_1);

		Vendor vendor = getFirstVendor();

		given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
		given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

		VendorDTO savedVendorDTO = vendorService.patchVendor(ID_1, vendorDTO);

		then(vendorRepository).should().save(any(Vendor.class));
		then(vendorRepository).should(times(1)).findById(anyLong());
		assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
	}

	public Vendor getFirstVendor() {
		Vendor vendor1 = new Vendor();
		vendor1.setId(ID_1);
		vendor1.setName(NAME_1);
		return vendor1;
	}

	public Vendor getSecondVendor() {
		Vendor vendor2 = new Vendor();
		vendor2.setId(ID_2);
		vendor2.setName(NAME_2);
		return vendor2;
	}
}