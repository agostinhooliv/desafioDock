package br.com.desafioDock.enums;

public enum TipoConta {
    PESSOA_FISICA (1),
    PESSOA_JURICA (2);

    private Integer codigo;

    private TipoConta(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }
}
