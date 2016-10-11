package br.com.fdp.pedidos.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fdp.pedidos.model.Product;
import br.com.fdp.pedidos.repository.ProductRepository;
import br.com.fdp.pedidos.util.MensagemUtil;

@Named
@ViewScoped
public class ProductController {
	
	@Getter@Setter
	private Product product = new Product();
	@Getter@Setter
	private List<Product> products;
	@Autowired
	private ProductRepository productRepository;
	@Getter@Setter
	private boolean modoEdicao = false;
	@PostConstruct
	public void init(){
		
		products = productRepository.findAll();
		
	}
	public void save(){
		try{
		productRepository.save(getProduct());
		if(!isModoEdicao());
		products.add(product);
		product = new Product();
		setModoEdicao(false);
		MensagemUtil.mensagemAviso("Salvo com sucesso");
		}catch(ServiceException e){
			e.printStackTrace();
			MensagemUtil.mensagemErro("Erro ao salva." + e.getMessage());
		}
	}
	public void remove(Product product){
		try{
		productRepository.delete(product);
		products.remove(product);
		product = new Product();
		MensagemUtil.mensagemAviso("Excluido com sucesso");
		}catch(ServiceException e){
			e.printStackTrace();
			MensagemUtil.mensagemErro("Erro ao excluido." + e.getMessage());
		}
	}
	public void editar(Product product){
		try{
		setProduct(product);
		setModoEdicao(true);
		}catch(ServiceException e){
			e.printStackTrace();
			MensagemUtil.mensagemErro("Erro ao excluido." + e.getMessage());
		}
	}
	public void cancelar(){
		product = new Product();
		setModoEdicao(false);
	}

	
 
}
