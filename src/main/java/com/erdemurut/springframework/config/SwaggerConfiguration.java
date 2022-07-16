package com.erdemurut.springframework.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info()
				.title("Spring REST Services")
				.contact(contactDetails())
				.version("1.6.9")
				.description("REST services for customers, vendors and categories")
				.termsOfService("http://swagger.io/terms/")
				.license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")));
	}

	private Contact contactDetails() {
		Contact contact = new Contact();
		contact.setName("Erdem ÜRÜT");
		contact.setEmail("erdem.urut@gmail.com");
		contact.setUrl("https://github.com/erdemurutt");
		return contact;
	}
}
