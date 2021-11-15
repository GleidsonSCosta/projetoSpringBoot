package com.example.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vendas.model.Produto;

public interface Produtos extends JpaRepository<Produto, Integer>{

}
