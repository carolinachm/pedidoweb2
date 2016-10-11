package br.com.fdp.pedidos.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import br.com.fdp.pedidos.repository.UserRepository;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserRepository userRepository;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http
			.userDetailsService(userDetailsService())
			.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/home.jsf").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.permitAll()
			.defaultSuccessUrl("/home.jsf")
			.and()
			.logout();
			
		
	}
	

	@Override
	protected UserDetailsService userDetailsService() {

		List<br.com.fdp.pedidos.model.User> usuarios = userRepository.findAll();

		List<UserDetails> users = new ArrayList<>();

		for (br.com.fdp.pedidos.model.User u : usuarios) {
			UserDetails user = new User(u.getLogin(), u.getSenha(),
					AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_"
							+ u.getPerfil()));
			users.add(user);
		}

		return new InMemoryUserDetailsManager(users);
	}
}
