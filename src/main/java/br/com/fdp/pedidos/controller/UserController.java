package br.com.fdp.pedidos.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fdp.pedidos.model.Product;
import br.com.fdp.pedidos.model.User;
import br.com.fdp.pedidos.repository.UserRepository;
import br.com.fdp.pedidos.util.MensagemUtil;

@Named
@ViewScoped
public class UserController {
	@Getter
	@Setter
	private User user = new User();
	@Getter
	@Setter
	private List<User> users;
	@Autowired
	private UserRepository userRepository;
	@Getter
	@Setter
	private boolean modoEdicao = false;

	@PostConstruct
	public void init() {
		users = userRepository.findAll();
	}

	public void save() {
		try {
			userRepository.save(getUser());
			if (!isModoEdicao())
				users.add(user);
			user = new User();
			setModoEdicao(false);
			MensagemUtil.mensagemAviso("Salvo com sucesso");
		} catch (ServiceException e) {
			e.printStackTrace();
			MensagemUtil.mensagemErro("Erro ao Salvar." + e.getMessage());
		}

	}

	public void remover(User user) {
		try {
			userRepository.delete(user);
			users.remove(user);
			user = new User();
			MensagemUtil.mensagemAviso("Excluido com sucesso");
		} catch (ServiceException e) {
			e.printStackTrace();
			MensagemUtil.mensagemErro("Erro ao excluir." + e.getMessage());
		}
	}

	public void editar(User user) {
		try{
		setUser(user);
		setModoEdicao(true);
		MensagemUtil.mensagemAviso("Alterado com sucesso");
		} catch (ServiceException e) {
			e.printStackTrace();
			MensagemUtil.mensagemErro("Erro ao alterar." + e.getMessage());
		}
	}

	public void cancelar() {
		user = new User();
		setModoEdicao(false);
	}

}
