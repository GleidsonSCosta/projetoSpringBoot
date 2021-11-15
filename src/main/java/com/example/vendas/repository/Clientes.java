package com.example.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vendas.model.Cliente;

public interface Clientes extends JpaRepository<Cliente, Integer> {

	List<Cliente> findByNomeLike(String nome);

	boolean existsByNome(String nome);
	
	@Query("select c from Cliente c left join fetch c.pedidos where c.id = :id ")
	Cliente findClienteFatchPedidos(@Param("id") Integer id);
	
}
