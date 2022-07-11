package com.erdemurut.springframework.api.v1.mapper;

import com.erdemurut.springframework.api.v1.model.CategoryDTO;
import com.erdemurut.springframework.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

	public static final String NAME = "Fresh";
	public static final long ID = 1L;
	CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

	@Test
	void categoryToCategoryDTO() {
		Category category = new Category();
		category.setName(NAME);
		category.setId(ID);

		CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

		assertEquals(Long.valueOf(ID), categoryDTO.getId());
		assertEquals(NAME, categoryDTO.getName());
	}
}