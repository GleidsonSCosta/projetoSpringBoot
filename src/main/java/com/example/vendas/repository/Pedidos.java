package com.example.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vendas.model.Pedido;

public interface Pedidos extends JpaRepository<Pedido, Integer>{

}
