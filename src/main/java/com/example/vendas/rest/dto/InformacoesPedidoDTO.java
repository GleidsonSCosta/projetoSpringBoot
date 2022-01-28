package com.example.vendas.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformacoesPedidoDTO {
	
	private Integer codigo;
	private String cpf;
	private String nomeCliente;
	private BigDecimal total;
	private String dataPedido;
	private String status;
	private List<InformacaoItemPedidoDTO> itens;
}
