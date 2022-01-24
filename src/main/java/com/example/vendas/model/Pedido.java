package com.example.vendas.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@Column (name = "data_pedido")
	private LocalDate datapedido;
	
	@Column
	private BigDecimal total;
	
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public LocalDate getDataPedido() {
		return datapedido;
	}
	
	public void setDataPedido(LocalDate datapedido) {
		this.datapedido = datapedido;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "Pedido (id=" + id + ", data pedido=" + datapedido + ", total=" + total + ")";
	}

}
