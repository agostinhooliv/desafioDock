package br.com.desafioDock.bean;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
public abstract class Bean<T extends Comparable<T>> {

    private T id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_gravacao")
    private Date dataGravacao;
    @Column(name = "usuario_gravacao")
    private String usuarioGravacao;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public Date getDataGravacao() {
        return dataGravacao;
    }

    public void setDataGravacao(Date dataGravacao) {
        this.dataGravacao = dataGravacao;
    }

    public String getUsuarioGravacao() {
        return usuarioGravacao;
    }

    public void setUsuarioGravacao(String usuarioGravacao) {
        this.usuarioGravacao = usuarioGravacao;
    }
}