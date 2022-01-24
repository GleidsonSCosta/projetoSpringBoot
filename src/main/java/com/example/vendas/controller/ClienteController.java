package com.example.vendas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public void salvarCliente(@RequestBody Cliente cliente) {
//		clientesRepository.save(cliente);	
//	}

	@PostMapping
	public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
		Cliente clienteSalvo = clientesRepository.save(cliente);
		return ResponseEntity.ok(clienteSalvo);
	}

	@GetMapping("{id}")
	public ResponseEntity<Cliente> getClienteId(@PathVariable Integer id) {
		Optional<Cliente> cliente = clientesRepository.findById(id);
		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		Optional<Cliente> cliente = clientesRepository.findById(id);

		if (cliente.isPresent()) {
			clientesRepository.delete(cliente.get());
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("{id}")
	public ResponseEntity<?> update (@PathVariable Integer id, @RequestBody Cliente cliente) {
		return clientesRepository
				.findById(id)
				.map(clienteExistente -> {
			cliente.setId(clienteExistente.getId());
			clientesRepository.save(cliente);
			return ResponseEntity.ok().build();
			}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	
	//método usa os dados passos no front como filtro e retorna uma lista dos clientes
	
	
	@GetMapping
	public ResponseEntity<?> find(Cliente filtro) {
		ExampleMatcher matcher = ExampleMatcher
				.matching()
				.withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		
		Example<Cliente> example = Example.of(filtro, matcher);
		List<Cliente> lista = clientesRepository.findAll(example);
		return ResponseEntity.ok(lista);
	}
	
}



















