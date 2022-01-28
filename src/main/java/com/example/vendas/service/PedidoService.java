package com.example.vendas.service;

import java.util.Optional;

import com.example.vendas.enumerado.StatusPedido;
import com.example.vendas.model.Pedido;
import com.example.vendas.rest.dto.PedidoDTO;

public interface PedidoService {
	
	Pedido salvar(PedidoDTO dto);
	
	Optional<Pedido> obterPedidoCompleto(Integer id);
	
	void atualizaStatusPedido(Integer id, StatusPedido statusPedido);
}
