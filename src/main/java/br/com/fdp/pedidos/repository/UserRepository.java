package br.com.fdp.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fdp.pedidos.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
