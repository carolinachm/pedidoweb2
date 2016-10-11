package br.com.fdp.pedidos.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;


import lombok.Getter;
import lombok.Setter;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fdp.pedidos.model.Ceremonial;

import br.com.fdp.pedidos.repository.CeremonialRepository;

import br.com.fdp.pedidos.util.MensagemUtil;

@Named
@ViewScoped
public class CeremonialController {
	@Getter@Setter
	private Ceremonial ceremonial = new Ceremonial();
	@Getter@Setter
	private List<Ceremonial> ceremonials;

	@Autowired
	private CeremonialRepository ceremonialRepository;
	@Getter@Setter
	private boolean modoEdicao = false;

	@PostConstruct
	public void init() {

		ceremonials = ceremonialRepository.findAll();
		
	}

	public void save() {
		try{
		ceremonialRepository.save(getCeremonial());
		if (!isModoEdicao())
			ceremonials.add(ceremonial);
		ceremonial = new Ceremonial();
		setModoEdicao(false);
		MensagemUtil.mensagemAviso("Salvo com sucesso");
		}catch(ServiceException e){
			MensagemUtil.mensagemErro("Erro ao Salvar." + e.getMessage());
		}
	}
	public void remove(Ceremonial ceremonial){
		try{
		ceremonialRepository.delete(ceremonial);
		ceremonials.remove(ceremonial);
		ceremonial = new Ceremonial();
		MensagemUtil.mensagemAviso("Excluido com sucesso");
		}catch(ServiceException e){
			MensagemUtil.mensagemErro("Erro ao deletar." + e.getMessage());
		}
	}
	public void editar(Ceremonial ceremonial){
		try{
		setCeremonial(ceremonial);
		MensagemUtil.mensagemAviso("Atualizado com sucesso");
		setModoEdicao(true);
		
		}catch(ServiceException e){
			MensagemUtil.mensagemErro("Erro ao atualizar." + e.getMessage());
		}
		
	}
	public void cancelar(){
		ceremonial = new Ceremonial();
		setModoEdicao(false);
	}

}
