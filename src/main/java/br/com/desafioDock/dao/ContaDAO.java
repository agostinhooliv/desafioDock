package br.com.desafioDock.dao;

import br.com.desafioDock.bean.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaDAO extends JpaRepository<Conta, Long> {
}
