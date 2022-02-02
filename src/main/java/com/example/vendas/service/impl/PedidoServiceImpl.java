package com.example.vendas.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.vendas.enumerado.StatusPedido;
import com.example.vendas.exception.PedidoNaoEncontradoException;
import com.example.vendas.exception.RegraNegocioException;
import com.example.vendas.model.Cliente;
import com.example.vendas.model.ItemPedido;
import com.example.vendas.model.Pedido;
import com.example.vendas.model.Produto;
import com.example.vendas.repository.Clientes;
import com.example.vendas.repository.ItensPedidos;
import com.example.vendas.repository.Pedidos;
import com.example.vendas.repository.Produtos;
import com.example.vendas.rest.dto.ItemPedidoDTO;
import com.example.vendas.rest.dto.PedidoDTO;
import com.example.vendas.service.PedidoService;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class PedidoServiceImpl implements PedidoService {
	
	private final Pedidos repository;
	private final Clientes clientesRepository;
	private final Produtos produtosRepository;
	private final ItensPedidos itensPedidoRepository;
	
	@Override
	public Pedido salvar(PedidoDTO dto) {
		
		Integer idCliente = dto.getCliente();
		Cliente cliente = clientesRepository
				.findById(idCliente)
				.orElseThrow(() -> new RegraNegocioException("Codigo de cliente inválido"));
		
		Pedido pedido = new Pedido();
		pedido.setTotal(dto.getTotal());
		pedido.setDatapedido(LocalDate.now());
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.REALIZADO);
		
		List<ItemPedido> itensPedido = converteItens(pedido, dto.getItens());
		repository.save(pedido);
		itensPedidoRepository.saveAll(itensPedido);
		
		pedido.setItens(itensPedido);
		
		return pedido;
	}
	
	private List<ItemPedido> converteItens(Pedido pedido, List<ItemPedidoDTO> itens) {
		if(itens.isEmpty()) {
			throw new RegraNegocioException("Para realizar pedido deve informar itens.");
		}
		
		return itens
				.stream()
				.map( dto -> {
			Integer idProduto = dto.getProduto();
			Produto produto =  produtosRepository
			.findById(idProduto)
			.orElseThrow(() -> new RegraNegocioException("Id de produto inaválido. " + idProduto));
			
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setQuantidade(dto.getQuantidade());
			itemPedido.setPedido(pedido);
			itemPedido.setProduto(produto);
			
			return itemPedido;
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<Pedido> obterPedidoCompleto(Integer id) {
		
		return repository.findByIdFetchItens(id);
	}

	@Override
	@Transactional
	public void atualizaStatusPedido(Integer id, StatusPedido statusPedido) {
		repository.findById(id).map(pedido -> {
			pedido.setStatus(statusPedido);
			return repository.save(pedido);
		}).orElseThrow(() -> new PedidoNaoEncontradoException());
		
	} 
	
	
}















