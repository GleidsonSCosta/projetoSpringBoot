package com.example.vendas.rest.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformacaoItemPedidoDTO {
	
	private String descricao;
	private BigDecimal precoUnitario;
	private Integer quantidade;
	
}
