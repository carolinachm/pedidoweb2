package br.com.fdp.pedidos.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	@JoinColumn(nullable=false)
	private Client client;
	@Temporal(TemporalType.DATE)
	private Date dataPedido;
	@Temporal(TemporalType.DATE)
	private Date dataEntrega;
	@Embedded
	private Address enderecoEntrega = new Address();
	@Column(nullable = false, precision = 7, scale = 2)
	private BigDecimal valorTotal;
	@ManyToOne
	@JoinColumn(nullable=false)
	private Ceremonial ceremonial;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="order", fetch=FetchType.EAGER)
	private List<ItemPedido> itens =  new ArrayList<>();

}
