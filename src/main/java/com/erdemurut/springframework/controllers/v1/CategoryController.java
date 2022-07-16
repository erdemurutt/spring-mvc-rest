package com.erdemurut.springframework.controllers.v1;

import com.erdemurut.springframework.api.v1.model.CategoryDTO;
import com.erdemurut.springframework.api.v1.model.CategoryListDTO;
import com.erdemurut.springframework.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category Endpoints")
@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

	public static final String BASE_URL = "/api/v1/categories/";
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Operation(summary = "Get all categories in JSON array format")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public CategoryListDTO getAllCategories() {
		return new CategoryListDTO(categoryService.getAllCategories());
	}

	@Operation(summary = "Get category by name parameter in JSON object format")
	@GetMapping("{name}")
	@ResponseStatus(HttpStatus.OK)
	public CategoryDTO getCategoryByName(@PathVariable String name) {
		return categoryService.getCategoryByName(name);
	}

}
