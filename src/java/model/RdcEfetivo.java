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
@Table(name = "rdc_efetivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RdcEfetivo.findAll", query = "SELECT r FROM RdcEfetivo r")
    , @NamedQuery(name = "RdcEfetivo.findById", query = "SELECT r FROM RdcEfetivo r WHERE r.id = :id")
    , @NamedQuery(name = "RdcEfetivo.findByCodigo", query = "SELECT r FROM RdcEfetivo r WHERE r.codigo = :codigo")
    , @NamedQuery(name = "RdcEfetivo.findByDataRdc", query = "SELECT r FROM RdcEfetivo r WHERE r.dataRdc = :dataRdc")})
public class RdcEfetivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "data_rdc")
    @Temporal(TemporalType.DATE)
    private Date dataRdc;

    public RdcEfetivo() {
    }

    public RdcEfetivo(Integer id) {
        this.id = id;
    }

    public RdcEfetivo(Integer id, String codigo) {
        this.id = id;
        this.codigo = codigo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getDataRdc() {
        return dataRdc;
    }

    public void setDataRdc(Date dataRdc) {
        this.dataRdc = dataRdc;
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
        if (!(object instanceof RdcEfetivo)) {
            return false;
        }
        RdcEfetivo other = (RdcEfetivo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.RdcEfetivo[ id=" + id + " ]";
    }
    
}
