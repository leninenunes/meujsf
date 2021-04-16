/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lenine.nunes
 */
@Entity
@Table(name = "efetivo", catalog = "meujsf", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Efetivo.findAll", query = "SELECT e FROM Efetivo e")
    , @NamedQuery(name = "Efetivo.findById", query = "SELECT e FROM Efetivo e WHERE e.id = :id")
    , @NamedQuery(name = "Efetivo.findByNome", query = "SELECT e FROM Efetivo e WHERE e.nome = :nome")
    , @NamedQuery(name = "Efetivo.findByDataCad", query = "SELECT e FROM Efetivo e WHERE e.dataCad = :dataCad")
    , @NamedQuery(name = "Efetivo.findByDataAlt", query = "SELECT e FROM Efetivo e WHERE e.dataAlt = :dataAlt")})
public class Efetivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "data_cad")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCad;
    @Basic(optional = false)
    @Column(name = "data_alt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlt;
    @JoinColumns({
        @JoinColumn(name = "funcao_id", referencedColumnName = "id")})
    @ManyToOne
    private Funcao funcao;

    public Efetivo() {
    }

    public Efetivo(Integer id) {
        this.id = id;
    }

    public Efetivo(Integer id, String nome, Date dataCad, Date dataAlt) {
        this.id = id;
        this.nome = nome;
        this.dataCad = dataCad;
        this.dataAlt = dataAlt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataCad() {
        return dataCad;
    }

    public void setDataCad(Date dataCad) {
        this.dataCad = dataCad;
    }

    public Date getDataAlt() {
        return dataAlt;
    }

    public void setDataAlt(Date dataAlt) {
        this.dataAlt = dataAlt;
    }

    public Funcao getFuncaoId() {
        return funcao;
    }

    public void setFuncaoId(Funcao funcao) {
        this.funcao = funcao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Efetivo)) {
            return false;
        }
        Efetivo other = (Efetivo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Efetivo[ id=" + id + " ]";
    }
    
}
