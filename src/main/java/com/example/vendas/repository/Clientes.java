package com.example.vendas.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.tree.RowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.vendas.model.Cliente;

import antlr.actions.cpp.ActionLexerTokenTypes;

@Repository
public class Clientes {

	@Autowired
	private EntityManager entityManager;

	@Transactional
	public Cliente salvar(Cliente cliente) {
		entityManager.persist(cliente);
		return cliente;
	}
	
	@Transactional
	public Cliente atualizar(Cliente cliente) {
		entityManager.merge(cliente);
		return cliente;
	}
	
	@Transactional
	public void deletar(Cliente cliente) {
		if(!entityManager.contains(cliente)) {
			cliente = entityManager.merge(cliente);
		}
		entityManager.remove(cliente);
	}
	
	@Transactional
	public void deletar(Integer id) {
		Cliente cliente = entityManager.find(Cliente.class, id);
		deletar(cliente);
	}
	
	@Transactional(readOnly = true)
	public List<Cliente> buscaPorNome(String nome){
		String jpql = "select c from Cliente c where c.nome like :nome";
		TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
		query.setParameter("nome","%" + nome + "%");
		return query.getResultList();	
	}
	
	@Transactional
	public List<Cliente> obterTodos(){
		return entityManager.createQuery("from Cliente", Cliente.class).getResultList();
	}
	
}
