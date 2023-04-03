package com.io.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "Bearer Authentication", scheme = "bearer", bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(security = @SecurityRequirement(name = "Bearer Authentication"), info = @Info(title = "HealthIO REST API v1.0", contact = @Contact(name = "Gustavo Tavares", url = "https://github.com/gsTavares", email = "gustavo.silva365@fatec.sp.gov.br")))
public class HealthApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthApplication.class, args);
	}

}
