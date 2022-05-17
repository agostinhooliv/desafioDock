package br.com.desafioDock.business;

import br.com.desafioDock.bean.Conta;
import br.com.desafioDock.bean.Transacoes;
import br.com.desafioDock.dao.TransacoesDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
public class TransacoesBusiness {

    @Autowired
    private TransacoesDAO transacoesDAO;

    public Transacoes salvar(Transacoes transacoes) {
        return transacoesDAO.save(transacoes);
    }

    public List<Transacoes> getListaPorConta(Conta conta) {
        return transacoesDAO.findAll().stream().filter(transacao -> transacao.getConta().equals(conta)).collect(Collectors.toList());
    }

    public List<Transacoes> getListaPorContaPorPeriodo(Conta conta, Date dataTransacaoInicial, Date dataTransacaoFinal) {
        return transacoesDAO.findByDataTransacaoBetween(dataTransacaoInicial, dataTransacaoFinal).stream().filter(transacao -> transacao.getConta().equals(conta)).collect(Collectors.toList());
    }
}
