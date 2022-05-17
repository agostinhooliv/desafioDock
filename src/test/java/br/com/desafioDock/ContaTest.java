package br.com.desafioDock;

import br.com.desafioDock.bean.Conta;
import br.com.desafioDock.bean.Pessoa;
import br.com.desafioDock.business.ContaBusiness;
import br.com.desafioDock.enums.TipoConta;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContaTest {

    @Autowired
    private ContaBusiness contaBusiness;

    @Test
    @Rollback(true)
    public void testarDeposito() throws Exception {
        Conta conta = new Conta();
        conta.setPessoa(new Pessoa(1L));
        conta.setFlagAtivo(Boolean.TRUE);
        conta.setTipoConta(TipoConta.PESSOA_JURICA);
        conta.setLimiteSaqueDiario(100.00);
        conta.setSaldo(10.00);
        assertNotNull(contaBusiness);
        contaBusiness.deposito(conta, 10.00);
        Double saldoFinal = 20.00;
        assertEquals(conta.getSaldo(), saldoFinal);
    }

    @Test
    @Rollback(true)
    public void testarSaque() throws Exception {
        Conta conta = new Conta();
        conta.setIdConta(1L);
        conta.setPessoa(new Pessoa(1L));
        conta.setFlagAtivo(Boolean.TRUE);
        conta.setTipoConta(TipoConta.PESSOA_JURICA);
        conta.setLimiteSaqueDiario(100.00);
        conta.setSaldo(10.00);
        assertNotNull(contaBusiness);
        conta = contaBusiness.saque(conta.getIdConta(), 10.00);
        Double saldoFinal = 0.00;
        assertEquals(conta.getSaldo(), saldoFinal);
    }

    @Test
    @Rollback(true)
    public void testarBloqueio() throws Exception {
        Conta conta = new Conta();
        conta.setPessoa(new Pessoa(1L));
        conta.setFlagAtivo(Boolean.TRUE);
        conta.setTipoConta(TipoConta.PESSOA_JURICA);
        conta.setLimiteSaqueDiario(100.00);
        conta.setSaldo(10.00);
        assertNotNull(contaBusiness);
        conta = contaBusiness.bloqueioConta(conta);
        assertNotEquals(conta.getFlagAtivo(), Boolean.TRUE);
    }
}
