package com.example.vendas.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.example.vendas.validation.NotEmptyList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
	
	@NotNull(message = "Infome o código do cliente.")
	private Integer cliente;
	
	@NotNull(message = "Campo total é obrigatório.")
	private BigDecimal total;
	
	@NotEmptyList(message = "Pedido não pode ser realizado sem itens.")
	private List<ItemPedidoDTO> itens;

	
}
