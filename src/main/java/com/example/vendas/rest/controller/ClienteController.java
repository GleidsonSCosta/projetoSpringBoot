package com.example.vendas.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.vendas.model.Cliente;
import com.example.vendas.repository.Clientes;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	private Clientes clientesRepository;

	@Autowired
	public ClienteController(Clientes clientesRepository) {
		this.clientesRepository = clientesRepository;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente salvarCliente(@RequestBody @Valid Cliente cliente) {
		return clientesRepository.save(cliente);	
	}

//	@PostMapping
//	public ResponseEntity<Cliente> save(@RequestBody @Valid  Cliente cliente) {
//		Cliente clienteSalvo = clientesRepository.save(cliente);
//		return ResponseEntity.ok(clienteSalvo);
//	}

	@GetMapping("{id}")
	public Cliente getClienteId(@PathVariable Integer id) {
		return clientesRepository
				.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		clientesRepository.findById(id)
			.map(cliente -> {
				clientesRepository.delete(cliente);
				return Void.TYPE;
				})
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));
		

		
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update (@PathVariable Integer id, @RequestBody @Valid  Cliente cliente) {
		clientesRepository
			.findById(id)
			.map(clienteExistente -> {
			cliente.setId(clienteExistente.getId());
			clientesRepository.save(cliente);
			return clienteExistente;
			}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinete não encontrado."));
	}
	
	
	//método usa os dados passos no front como filtro e retorna uma lista dos clientes
	
	
	@GetMapping
	public List<Cliente> find(Cliente filtro) {
		ExampleMatcher matcher = ExampleMatcher
				.matching()
				.withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		
		Example<Cliente> example = Example.of(filtro, matcher);
		return clientesRepository.findAll(example);
		
	}
	
}



















