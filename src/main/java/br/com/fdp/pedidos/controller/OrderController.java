package br.com.fdp.pedidos.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fdp.pedidos.model.ItemPedido;
import br.com.fdp.pedidos.model.Order;
import br.com.fdp.pedidos.model.Ceremonial;
import br.com.fdp.pedidos.model.Client;
import br.com.fdp.pedidos.model.Package;
import br.com.fdp.pedidos.model.Product;
import br.com.fdp.pedidos.model.State;
import br.com.fdp.pedidos.repository.CeremonialRepository;
import br.com.fdp.pedidos.repository.ClientRepository;
import br.com.fdp.pedidos.repository.OrderRepository;
import br.com.fdp.pedidos.repository.ProductRepository;
import br.com.fdp.pedidos.repository.StateRepository;

@Named
@ViewScoped
@Data
public class OrderController {

	private Order order = new Order();
	
	private ItemPedido item = new ItemPedido();
	
	private Package embrulho = new Package();
	
	private List<Order> orders;
	
	private List<Client> clients;
	
	private List<Ceremonial> ceremonials;

	private List<Product> products;
	
	private List<Package> embrulhos;
	
	private List<ItemPedido> itens;

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CeremonialRepository ceremonialRepository;
	@Autowired
	private ProductRepository productRepository;
	@Getter
	@Setter
	private boolean modoEdicao = false;

	@PostConstruct
	public void init() {

		orders = orderRepository.findAll();
		clients = clientRepository.findAll();
		ceremonials = ceremonialRepository.findAll();
		products = productRepository.findAll();

	}

	public void save() {
		orderRepository.save(getOrder());
		if (!isModoEdicao())
			orders.add(order);
		order = new Order();
		setModoEdicao(false);
	}

	public void remove(Order order) {
		orderRepository.delete(order);
		orders.remove(order);
		order = new Order();
	}

	public void editar(Order order) {
		setOrder(order);
		setModoEdicao(true);

	}

	public void cancelar() {
		order = new Order();
		setModoEdicao(false);
	}

	public void adicionaPedido() {
		item.setOrder(order);
		order.getItem().add(item);
		item = new ItemPedido();

	}

}
