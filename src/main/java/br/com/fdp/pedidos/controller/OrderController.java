package br.com.fdp.pedidos.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fdp.pedidos.model.ItemPedido;
import br.com.fdp.pedidos.model.Order;
import br.com.fdp.pedidos.model.Ceremonial;
import br.com.fdp.pedidos.model.Client;
import br.com.fdp.pedidos.model.Package;
import br.com.fdp.pedidos.model.Payment;
import br.com.fdp.pedidos.model.Product;
import br.com.fdp.pedidos.repository.CeremonialRepository;
import br.com.fdp.pedidos.repository.ClientRepository;
import br.com.fdp.pedidos.repository.OrderRepository;
import br.com.fdp.pedidos.repository.PackageRepository;
import br.com.fdp.pedidos.repository.PaymentRepository;
import br.com.fdp.pedidos.repository.ProductRepository;

@Named
@ViewScoped
public class OrderController {
	@Getter
	@Setter
	private Order order = new Order();
	@Getter
	@Setter
	private Product product = new Product();
	@Getter
	@Setter
	private ItemPedido item = new ItemPedido();
	@Getter
	@Setter
	private Package embrulho = new Package();
	@Getter
	@Setter
	private List<Order> orders;
	@Getter
	@Setter
	private List<Client> clients;
	@Getter
	@Setter
	private List<Ceremonial> ceremonials;
	@Getter
	@Setter
	private List<Product> products;
	@Getter
	@Setter
	private List<Package> embrulhos;
	@Getter
	@Setter
	private List<ItemPedido> itens;
	@Getter
	@Setter
	private List<Payment> payments;

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CeremonialRepository ceremonialRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private PackageRepository embrulhoRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Getter
	@Setter
	private boolean modoEdicao = false;

	@PostConstruct
	public void init() {

		orders = orderRepository.findAll();
		clients = clientRepository.findAll();
		ceremonials = ceremonialRepository.findAll();
		products = productRepository.findAll();
		embrulhos = embrulhoRepository.findAll();
		payments = paymentRepository.findAll();

		itens = new ArrayList<ItemPedido>();

	}

	public void save() {
		orderRepository.save(getOrder());
		if (!isModoEdicao())
			orders.add(order);
		order = new Order();
		itens = new ArrayList<ItemPedido>();
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

	public void adicionarPedido() {
		item.setOrder(order);
		order.getItens().add(item);
		item = new ItemPedido();
		
		int achou = -1;
		for(int posicao = 0; posicao < itens.size();posicao++){
			if(itens.get(posicao).getProduct().equals(product)){
				achou = posicao;
			}
			if(itens.get(posicao).getEmbrulho().equals(embrulho)){
				achou = posicao;
			}
			
		}
		if (achou < 0) {
			item.setValorProduto(product.getValor());
			item.setValorEmbrulho(embrulho.getValorPackage());
			item.setQuantidade(new Short("1"));
			itens.add(item);
		} else {
			ItemPedido item = itens.get(achou);
			item.setQuantidade(new Short(item.getQuantidade() + 1 + ""));
			item.setValorProduto(product.getValor().multiply(
					new BigDecimal(item.getQuantidade())));
			item.setValorEmbrulho(embrulho.getValorPackage().multiply(
					new BigDecimal(item.getQuantidade())));
		}
		calcular();

	}

	public void calcular() {
		order.setValorTotal(new BigDecimal("0.00"));
		for (int posicao = 0; posicao < itens.size(); posicao++) {
			ItemPedido item = itens.get(posicao);
			order.setValorTotal(item.getValorEmbrulho().add(
					item.getValorProduto()));
		}

	}

	public void excluirPedido(ItemPedido item) {

		order.getItens().remove(item);
	}

}
