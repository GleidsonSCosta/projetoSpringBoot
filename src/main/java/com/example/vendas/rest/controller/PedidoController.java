package com.example.vendas.rest.controller;

import static org.springframework.http.HttpStatus.*;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.vendas.enumerado.StatusPedido;
import com.example.vendas.model.ItemPedido;
import com.example.vendas.model.Pedido;
import com.example.vendas.rest.dto.AtualizacaoStatusPedidoDTO;
import com.example.vendas.rest.dto.InformacaoItemPedidoDTO;
import com.example.vendas.rest.dto.InformacoesPedidoDTO;
import com.example.vendas.rest.dto.PedidoDTO;
import com.example.vendas.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
	
	private PedidoService service;
	
	public PedidoController(PedidoService service) {
		this.service = service;
	}
	
	@PostMapping
	@ResponseStatus(CREATED)
	public Integer save(@RequestBody PedidoDTO dto) {
		Pedido pedido = service.salvar(dto);
		return pedido.getId();
	}
	
	@PatchMapping("{id}")
	@ResponseStatus(NO_CONTENT)
	private void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto ) {
		
		String novoStatus = dto.getNovoStatus();
		service.atualizaStatusPedido(id, StatusPedido.valueOf(novoStatus) );
	}
	
	@GetMapping("{id}")
	public InformacoesPedidoDTO getByid(@PathVariable Integer id ) {
		return service.obterPedidoCompleto(id)
				.map(p -> converter(p))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));	
	}
	private InformacoesPedidoDTO converter(Pedido pedido) {
		return InformacoesPedidoDTO
			.builder()
			.codigo(pedido.getId())
			.dataPedido(pedido.getDatapedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
			.cpf(pedido.getCliente().getCpf())
			.nomeCliente(pedido.getCliente().getNome())
			.total(pedido.getTotal())
			.status(pedido.getStatus().name())//pega o conteúdo de ENUM
			.itens(converter(pedido.getItens()))
			.build();
			
	}
	private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
		
		if(CollectionUtils.isEmpty(itens)) {
			return Collections.emptyList();
		}
		
		return itens.stream().map(
				item -> InformacaoItemPedidoDTO
				.builder().descricao(item.getProduto().getDescricao())
				.precoUnitario(item.getProduto().getPreco())
				.quantidade(item.getQuantidade())
				.build()
				).collect(Collectors.toList());
	}
}

























