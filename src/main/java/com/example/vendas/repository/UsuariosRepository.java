package com.example.vendas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vendas.model.Usuarios;

public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {

	Optional<Usuarios> findByLogin(String login);
	
}
