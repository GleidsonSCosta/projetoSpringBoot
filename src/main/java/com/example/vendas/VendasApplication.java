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

	 @Bean CommandLineRunner init (@Autowired Clientes clientes) {
		 return arg -> {
			System.out.println("Salvando Cliente");
			clientes.salvar(new Cliente("Gleidson"));
			clientes.salvar(new Cliente("Davi Lucca"));
			
			List<Cliente> todosClientes = clientes.obterTodos();
			todosClientes.forEach(System.out::println);
			
			System.out.println("Atualizando clientes");
			todosClientes.forEach(c -> {
				c.setNome(c.getNome() + " Costa");
				clientes.atualizar(c);
			});
			
			todosClientes = clientes.obterTodos();
			todosClientes.forEach(System.out::println);
			
			System.out.println("Buscando clinetes");
			clientes.buscaPorNome("Cli").forEach(System.out::println);
			
			System.out.println("Deletando clientes");
			clientes.obterTodos().forEach(c -> {
				clientes.deletar(c);
			});
			
			todosClientes = clientes.obterTodos();
			if(todosClientes.isEmpty()) {
				System.out.println("Nenhum cliente encontrado");
			}else {
				todosClientes.forEach(System.out::println);
			}
			
		 };
	 }

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
