package br.com.desafioDock.dao;

import br.com.desafioDock.bean.Transacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransacoesDAO extends JpaRepository<Transacoes, Long> {
    List<Transacoes> findByDataTransacaoBetween(Date dataTransacaoInicial, Date dataTransacaoFinal);
}
