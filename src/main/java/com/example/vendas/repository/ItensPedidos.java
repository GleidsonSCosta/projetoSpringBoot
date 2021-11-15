package com.example.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vendas.model.ItemPedido;

public interface ItensPedidos extends JpaRepository<ItemPedido, Integer> {

}
