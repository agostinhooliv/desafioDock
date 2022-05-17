package br.com.desafioDock.service;

import br.com.desafioDock.bean.Conta;
import br.com.desafioDock.bean.Pessoa;
import br.com.desafioDock.business.ContaBusiness;
import br.com.desafioDock.rabbit.Rabbitmq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dock/conta")
public class ContaService {

    @Autowired
    private ContaBusiness contaBusiness;
    @Autowired
    private Rabbitmq rabbitmq;

    @PostMapping("/salvar")
    @ResponseBody
    public ResponseEntity<?> save(@RequestBody Conta conta, @RequestParam(defaultValue = "0", required = true) Long pessoaId) {
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(pessoaId);
        if (pessoa != null) {
            conta.setPessoa(pessoa);
            return new ResponseEntity<>(contaBusiness.salvar(conta), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/consultaSaldo")
    public ResponseEntity<?> consultaSaldo(@RequestParam(defaultValue = "0", required = true) Long idConta) {
        Conta conta = getConta(idConta);
        if (conta != null) {
            return new ResponseEntity<>(contaBusiness.consultaSaldo(idConta), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/bloquearConta")
    public ResponseEntity<?> bloquearConta(@RequestParam(defaultValue = "0", required = true) Long idConta) {
        Conta conta = getConta(idConta);
        if (conta != null) {
            return new ResponseEntity<>(contaBusiness.bloqueioConta(conta), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/deposito")
    public ResponseEntity<?> deposito(@RequestParam(defaultValue = "0", required = true) Long idConta, @RequestParam(defaultValue = "0", required = true) Double valorDeposito) {
        Conta conta = getConta(idConta);
        if (conta != null) {
            return new ResponseEntity<>(contaBusiness.deposito(conta, valorDeposito), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/saque")
    public ResponseEntity<?> saque(@RequestParam(defaultValue = "0", required = true) Long idConta, @RequestParam(defaultValue = "0", required = true) Double valorSaque) {
        Conta conta = getConta(idConta);
        if (conta != null) {
            try {
                rabbitmq.pulicarFila(idConta, valorSaque);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/consultarContas")
    public ResponseEntity<?> consultarContas() {
        return new ResponseEntity<>(contaBusiness.listarTodas(), HttpStatus.OK);
    }

    private Conta getConta(Long idConta) {
        return contaBusiness.buscarContaPorId(idConta);
    }
}
