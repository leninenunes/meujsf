/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lenine
 */
@Entity
@Table(name = "funcao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Funcao.findAll", query = "SELECT f FROM Funcao f")
    , @NamedQuery(name = "Funcao.findById", query = "SELECT f FROM Funcao f WHERE f.id = :id")
    , @NamedQuery(name = "Funcao.findByNome", query = "SELECT f FROM Funcao f WHERE f.nome = :nome")})
//    , @NamedQuery(name = "Funcao.findByDataCad", query = "SELECT f FROM Funcao f WHERE f.dataCad = :dataCad")
//    , @NamedQuery(name = "Funcao.findByDataAlt", query = "SELECT f FROM Funcao f WHERE f.dataAlt = :dataAlt")})
public class Funcao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
//    @Basic(optional = false)
//    @Column(name = "data_cad")
//    @Temporal(TemporalType.TIMESTAMP)
    @Transient
    private Date dataCad;
//    @Basic(optional = false)
//    @Column(name = "data_alt")
//    @Temporal(TemporalType.TIMESTAMP)
    @Transient
    private Date dataAlt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcaoId")
    private Collection<Efetivo> efetivoCollection;

    public Funcao() {
    }

    public Funcao(Integer id) {
        this.id = id;
    }

    public Funcao(Integer id, String nome, Date dataCad, Date dataAlt) {
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

    @XmlTransient
    public Collection<Efetivo> getEfetivoCollection() {
        return efetivoCollection;
    }

    public void setEfetivoCollection(Collection<Efetivo> efetivoCollection) {
        this.efetivoCollection = efetivoCollection;
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
        if (!(object instanceof Funcao)) {
            return false;
        }
        Funcao other = (Funcao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Funcao[ id=" + id + " ]";
    }
    
}
