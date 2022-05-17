package br.com.desafioDock.service;

import br.com.desafioDock.bean.Conta;
import br.com.desafioDock.business.ContaBusiness;
import br.com.desafioDock.business.TransacoesBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api/dock/transacoes")
public class TransacoesService {

    @Autowired
    private ContaBusiness contaBusiness;
    @Autowired
    private TransacoesBusiness transacoesBusiness;

    @GetMapping("/extratoTransacoes")
    public ResponseEntity<?> extratoTransacoes(@RequestParam(defaultValue = "0", required = true) Long idConta) {
        Conta conta = contaBusiness.buscarContaPorId(idConta);
        if (conta != null) {
            return new ResponseEntity<>(transacoesBusiness.getListaPorConta(conta), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/extratoTransacoesPeriodo")
    public ResponseEntity<?> extratoTransacoesPeriodo(@RequestParam(defaultValue = "0", required = true) Long idConta,
                                                      @RequestParam(defaultValue = "0", required = true) String dataTransacaoInicial,
                                                      @RequestParam(defaultValue = "0", required = true) String dataTransacaoFinal) {
        Conta conta = contaBusiness.buscarContaPorId(idConta);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (conta != null) {
            try {
                return new ResponseEntity<>(transacoesBusiness.getListaPorContaPorPeriodo(conta, sdf.parse(dataTransacaoInicial), sdf.parse(dataTransacaoFinal)), HttpStatus.OK);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        sdf = null;
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
