package com.example.vendas;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import com.example.vendas.model.Cliente;
import com.example.vendas.model.Pedido;
import com.example.vendas.repository.Clientes;
import com.example.vendas.repository.Pedidos;

@SpringBootApplication
@RestController
public class VendasApplication {

	 @Bean 
	 public CommandLineRunner init (
			 @Autowired Clientes clientes,
			 @Autowired Pedidos pedidos){
		 return arg -> {
			System.out.println("Salvando Cliente");
			clientes.save(new Cliente("Gleidson"));
			clientes.save(new Cliente("Davi Lucca"));
			
			Cliente cliente1 = new Cliente("Jose");
			clientes.save(cliente1);
			
			Pedido p = new Pedido();
			p.setCliente(cliente1);
			p.setDataPedido(LocalDate.now());
			p.setTotal(BigDecimal.valueOf(100));
			
			pedidos.save(p);
			
			Cliente cliente = clientes.findClienteFatchPedidos(cliente1.getId());
			System.out.println(cliente);
			System.out.println(cliente.getPedidos());
			
			//testando Query Methods
			boolean existe = clientes.existsByNome("Gleidson");
			System.out.println("Existe um cliente com o nome Gleidson? " + existe);

		 };
	 }

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
