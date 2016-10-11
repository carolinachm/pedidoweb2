package br.com.fdp.pedidos.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;


import lombok.Getter;
import lombok.Setter;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fdp.pedidos.model.Package;
import br.com.fdp.pedidos.repository.PackageRepository;
import br.com.fdp.pedidos.util.MensagemUtil;

@Named
@ViewScoped
public class PackageController {
	@Getter@Setter
	private Package embrulho = new Package();
	@Getter@Setter
	private List<Package> embrulhos;

	@Autowired
	private PackageRepository embrulhoRepository;
	@Getter@Setter
	private boolean modoEdicao = false;

	@PostConstruct
	public void init() {

		embrulhos = embrulhoRepository.findAll();
		
	}

	public void save() {
		try{
		embrulhoRepository.save(getEmbrulho());
		if (!isModoEdicao())
			embrulhos.add(embrulho);
		embrulho = new Package();
		setModoEdicao(false);
		MensagemUtil.mensagemAviso("Salvo com sucesso");
		}catch(ServiceException e){
			e.printStackTrace();
			MensagemUtil.mensagemErro("Erro ao salva." + e.getMessage());
		}
	}
	public void remove(Package embrulho){
		try{
		embrulhoRepository.delete(embrulho);
		embrulhos.remove(embrulho);
		embrulho = new Package();
		MensagemUtil.mensagemAviso("Excluido com sucesso");
		}catch(ServiceException e){
			e.printStackTrace();
			MensagemUtil.mensagemErro("Erro ao deletar." + e.getMessage());
		}
	}
	public void editar(Package embrulho){
		try{
		setEmbrulho(embrulho);
		setModoEdicao(true);
		MensagemUtil.mensagemAviso("Alterado com sucesso");
		}catch(ServiceException e){
			e.printStackTrace();
			MensagemUtil.mensagemErro("Erro ao alterar." + e.getMessage());
		}
	}
	public void cancelar(){
		embrulho = new Package();
		setModoEdicao(false);
	}

	
}
