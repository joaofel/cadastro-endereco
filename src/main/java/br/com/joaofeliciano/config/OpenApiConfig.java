package br.com.joaofeliciano.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI cadastroEnderecoOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Cadastro de Endereco")
						.description("Api para cadastro de enderecos")
						.version("1.0")
						.termsOfService("http://www.teste.com")
						.contact(new Contact()
								.name("teste")
								.email("teste@teste.com"))
						.license(new License()
								.name("LICENSE")
								.url("http://www.teste.com")));
	}
}
