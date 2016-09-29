package br.com.fdp.pedidos.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fdp.pedidos.model.ItemPedido;
import br.com.fdp.pedidos.model.Order;
import br.com.fdp.pedidos.model.Payment;
import br.com.fdp.pedidos.repository.OrderRepository;
import br.com.fdp.pedidos.repository.PaymentRepository;

@Named
@ViewScoped
public class PaymentController {
	
	@Getter@Setter
	private Payment payment = new Payment();
	@Getter@Setter
	private List<Payment> payments;
	@Getter@Setter
	private List<Order> orders;
	@Getter@Setter
	private boolean modoEdicao = false;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private OrderRepository orderRepository;

	@PostConstruct
	public void init(){
		payments = paymentRepository.findAll();
		
	}
	public void save(){
		paymentRepository.save(getPayment());
		if(!isModoEdicao())
			payments.add(payment);
		payment = new Payment();
		setModoEdicao(false);
	}
	public void remove(Payment payment){
		paymentRepository.delete(payment);
		payments.remove(payment);
		payment = new Payment();
	}
	public void editar(Payment payment){
		setPayment(payment);
		setModoEdicao(true);
	}
	public void cancelar() {
		payment = new Payment();
		setModoEdicao(false);
	}
	public void adicionarPagamento() {
	


	}
	

}
