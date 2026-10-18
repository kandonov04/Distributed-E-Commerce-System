/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Konstantin
 */
@Entity
@Table(name = "korpa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korpa.findAll", query = "SELECT k FROM Korpa k"),
    @NamedQuery(name = "Korpa.findByIdK", query = "SELECT k FROM Korpa k WHERE k.idK = :idK"),
    @NamedQuery(name = "Korpa.findByIdKor", query = "SELECT k FROM Korpa k WHERE k.idKor = :idKor"),
    @NamedQuery(name = "Korpa.findByUkupnaCena", query = "SELECT k FROM Korpa k WHERE k.ukupnaCena = :ukupnaCena")})
public class Korpa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idK")
    private Integer idK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idKor")
    private int idKor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ukupnaCena")
    private BigDecimal ukupnaCena;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "korpa")
    private List<UKorpi> uKorpiList;

    public Korpa() {
    }

    public Korpa(Integer idK) {
        this.idK = idK;
    }

    public Korpa(Integer idK, int idKor) {
        this.idK = idK;
        this.idKor = idKor;
    }

    public Integer getIdK() {
        return idK;
    }

    public void setIdK(Integer idK) {
        this.idK = idK;
    }

    public int getIdKor() {
        return idKor;
    }

    public void setIdKor(int idKor) {
        this.idKor = idKor;
    }

    public BigDecimal getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(BigDecimal ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    @XmlTransient
    public List<UKorpi> getUKorpiList() {
        return uKorpiList;
    }

    public void setUKorpiList(List<UKorpi> uKorpiList) {
        this.uKorpiList = uKorpiList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idK != null ? idK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korpa)) {
            return false;
        }
        Korpa other = (Korpa) object;
        if ((this.idK == null && other.idK != null) || (this.idK != null && !this.idK.equals(other.idK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Korpa[ idK=" + idK + " ]";
    }
    
}
