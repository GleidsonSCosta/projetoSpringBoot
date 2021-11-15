package com.example.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vendas.model.Cliente;

public interface Clientes extends JpaRepository<Cliente, Integer> {

	List<Cliente> findByNomeLike(String nome);
	
	//query method
	
	List<Cliente> findByNomeOrId(String nome, Integer id);
	List<Cliente> findByNomeOrIdOrderById(String nome, Integer id);
	Cliente findOneByNome(String nome);
	boolean existsByNome(String nome);

	
	
	// TRABALHANDO COM JPA ENTITY MANAGER

//	@Autowired
//	private EntityManager entityManager;
//
//	@Transactional
//	public Cliente salvar(Cliente cliente) {
//		entityManager.persist(cliente);
//		return cliente;
//	}
//	
//	@Transactional
//	public Cliente atualizar(Cliente cliente) {
//		entityManager.merge(cliente);
//		return cliente;
//	}
//	
//	@Transactional
//	public void deletar(Cliente cliente) {
//		if(!entityManager.contains(cliente)) {
//			cliente = entityManager.merge(cliente);
//		}
//		entityManager.remove(cliente);
//	}
//	
//	@Transactional
//	public void deletar(Integer id) {
//		Cliente cliente = entityManager.find(Cliente.class, id);
//		deletar(cliente);
//	}
//	
//	@Transactional(readOnly = true)
//	public List<Cliente> buscaPorNome(String nome){
//		String jpql = "select c from Cliente c where c.nome like :nome";
//		TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
//		query.setParameter("nome","%" + nome + "%");
//		return query.getResultList();	
//	}
//	
//	@Transactional
//	public List<Cliente> obterTodos(){
//		return entityManager.createQuery("from Cliente", Cliente.class).getResultList();
//	}
	
}
