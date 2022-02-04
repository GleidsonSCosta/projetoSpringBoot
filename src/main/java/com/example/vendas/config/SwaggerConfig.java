package com.example.vendas.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spi.service.contexts.SecurityContextBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private Contact contato() {
		return new Contact(
				"Gleidson Costa",
				"hhtp://www.seusite.com.br",
				"gleidson@gmail.com"
				);
	}
	
	private ApiInfoBuilder inforcoesApi() {
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
		
		apiInfoBuilder.title("Titulo - Rest API");
		apiInfoBuilder.description("Exemplo de uso da API");
		apiInfoBuilder.version("1.0");
		apiInfoBuilder.termsOfServiceUrl("Termo de uso: Open Source");
		apiInfoBuilder.license("Licença - Minha API");
		apiInfoBuilder.licenseUrl("hhtp://www.seusite.com.br");
		apiInfoBuilder.contact(this.contato());
		
		return apiInfoBuilder;
	}
	
	@Bean
	public Docket detalhesApi() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		docket
		.select()
		.apis(RequestHandlerSelectors.basePackage("com.example.vendas.rest.controller"))
		.paths(PathSelectors.any())
		.build()
		.securitySchemes(Arrays.asList(apikey()))
		.securityContexts(Arrays.asList(securityContext()))
		.apiInfo(this.inforcoesApi().build())
		.consumes(new HashSet<String>(Arrays.asList("application/json")))
		.produces(new HashSet<String>(Arrays.asList("application/json")));
		
		return docket;
	}
	
	public ApiKey apikey() {
		return new ApiKey("JWT", "Authorization", "header");
	}
	
	private SecurityContext securityContext() {
	    return SecurityContext
	    		.builder()
	    		.securityReferences(defaultAuth())
	    		.forPaths(PathSelectors.any()).build();
	}

	
	private List<SecurityReference> defaultAuth(){
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] scopes = new AuthorizationScope[1];
		scopes[0] = authorizationScope;
		SecurityReference reference = new SecurityReference("JWT", scopes);
		List<SecurityReference> auths = new ArrayList<>();
		auths.add(reference);
		return auths;
	}

}
