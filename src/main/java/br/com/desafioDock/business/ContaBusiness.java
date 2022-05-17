package br.com.desafioDock.business;

import br.com.desafioDock.bean.Conta;
import br.com.desafioDock.bean.Transacoes;
import br.com.desafioDock.dao.ContaDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
public class ContaBusiness {

    @Autowired
    private ContaDAO contaDAO;
    @Autowired
    private TransacoesBusiness transacoesBusiness;

    public Conta salvar(Conta conta) {
        return contaDAO.save(conta);
    }

    public Conta buscarContaPorId(Long idConta) {

        Optional<Conta> conta = buscarConta(idConta);

        if (conta.isPresent()) {
            return conta.get();
        }

        return null;
    }

    public Double consultaSaldo(Long idConta) {

        Optional<Conta> conta = buscarConta(idConta);

        if (conta.isPresent()) {
            return conta.get().getSaldo();
        }
        return null;

    }

    private Optional<Conta> buscarConta(Long idConta) {
        return contaDAO.findById(idConta);
    }

    public Conta bloqueioConta(Conta conta) {
        conta.setFlagAtivo(Boolean.FALSE);
        return contaDAO.save(conta);
    }

    public Conta deposito(Conta conta, Double valorDeposito) {
        conta.setSaldo(conta.getSaldo() + valorDeposito);
        transacoesBusiness.salvar(new Transacoes(conta, valorDeposito, new Date()));
        return contaDAO.save(conta);
    }

    public Conta saque(Long idConta, Double valorSaque) {

        Conta conta = buscarContaPorId(idConta);

        if (conta != null) {
            conta.setSaldo(conta.getSaldo() - valorSaque);
            transacoesBusiness.salvar(new Transacoes(conta, valorSaque, new Date()));
            return contaDAO.save(conta);
        }

        return null;
    }

    public List<Conta> listarTodas() {
        return contaDAO.findAll();
    }
}
