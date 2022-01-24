package com.example.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vendas.model.Cliente;
import com.example.vendas.model.Pedido;

public interface Pedidos extends JpaRepository<Pedido, Integer>{
	
	//Retorna a lista de pedidos passando o cliente
	//List<Pedido> findByCliente(Cliente cliente);
}
