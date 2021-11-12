package com.example.vendas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vendas.model.Cliente;
import com.example.vendas.repository.ClientesRepository;

/*
 * a classe ClienteService depende da ClienteRepository para salvar o clinete na base de dados
 * injetando a ClienteRepository através do construtor 
 * 
 * */

@Service
public class ClienteService {
	
	private ClientesRepository repository;
	
	@Autowired
	public ClienteService(ClientesRepository repository) {
		this.repository = repository;
	}
	
	public void salvarCliente(Cliente cliente) {
		validarCliente(cliente);
		this.repository.persistir(cliente);
	}
	public void  validarCliente(Cliente cliente) {
		//validar cliente antes de salvar
	}
}
