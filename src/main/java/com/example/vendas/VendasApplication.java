package com.example.vendas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import com.example.vendas.model.Cliente;
import com.example.vendas.repository.Clientes;

@SpringBootApplication
@RestController
public class VendasApplication {

	 @Bean 
	 public CommandLineRunner init (@Autowired Clientes clientes) {
		 return arg -> {
			System.out.println("Salvando Cliente");
			clientes.save(new Cliente("Gleidson"));
			clientes.save(new Cliente("Davi Lucca"));
			
			//testando Query Methods
			boolean existe = clientes.existsByNome("Gleidson");
			System.out.println("Existe um cliente com o nome Gleidson? " + existe);

			
// TESTANDO JPA
			
//			List<Cliente> todosClientes = clientes.findAll();
//			todosClientes.forEach(System.out::println);			
			
//			System.out.println("Atualizando clientes");
//			todosClientes.forEach(c -> {
//				c.setNome(c.getNome() + " Costa");
//				clientes.save(c);
//			});
//			
//			todosClientes = clientes.findAll();
//			todosClientes.forEach(System.out::println);
//			
//			System.out.println("Buscando clientes");
//			clientes.findByNomeLike("Cli").forEach(System.out::println);
//			
//			System.out.println("Deletando clientes");
//			clientes.findAll().forEach(c -> {
//				clientes.delete(c);
//			});
//			
//			todosClientes = clientes.findAll();
//			if(todosClientes.isEmpty()) {
//				System.out.println("Nenhum cliente encontrado");
//			}else {
//				todosClientes.forEach(System.out::println);
//			}
			
		 };
	 }

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
