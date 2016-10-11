package br.com.fdp.pedidos.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import br.com.fdp.pedidos.model.User;

@Named
@ViewScoped
public class LoginController {
	@Getter@Setter
	private User user = new User();
	
	 public String doEfetuarLogin() {
		    if(user.equals(user.getLogin()) &&
		       user.equals(user.getSenha())) {
		      /* Se escrever o login e senha correto então vai para a tela principal do sistema. */
		      return "home";
		    } else {
		        /* Cria uma mensagem. */
		        FacesMessage msg = new FacesMessage("Usuário ou senha inválido!");
		        /* Obtém a instancia atual do FacesContext e adiciona a mensagem de erro nele. */
		        FacesContext.getCurrentInstance().addMessage("error", msg);
		        return null;
		    }
		  }

}
