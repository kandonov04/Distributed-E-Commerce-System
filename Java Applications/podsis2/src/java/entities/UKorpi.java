/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import idClass.UKorpiPK;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Konstantin
 */
@Entity
@Table(name = "u_korpi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UKorpi.findAll", query = "SELECT u FROM UKorpi u"),
    @NamedQuery(name = "UKorpi.findByIdK", query = "SELECT u FROM UKorpi u WHERE u.uKorpiPK.idK = :idK"),
    @NamedQuery(name = "UKorpi.findByIdArt", query = "SELECT u FROM UKorpi u WHERE u.uKorpiPK.idArt = :idArt"),
    @NamedQuery(name = "UKorpi.findByKolicina", query = "SELECT u FROM UKorpi u WHERE u.kolicina = :kolicina")})
public class UKorpi implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UKorpiPK uKorpiPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kolicina")
    private int kolicina;
    @JoinColumn(name = "idArt", referencedColumnName = "idArt", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Artikal artikal;
    @JoinColumn(name = "idK", referencedColumnName = "idK", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Korpa korpa;

    public UKorpi() {
    }

    public UKorpi(UKorpiPK uKorpiPK) {
        this.uKorpiPK = uKorpiPK;
    }

    public UKorpi(UKorpiPK uKorpiPK, int kolicina) {
        this.uKorpiPK = uKorpiPK;
        this.kolicina = kolicina;
    }

    public UKorpi(int idK, int idArt) {
        this.uKorpiPK = new UKorpiPK(idK, idArt);
    }

    public UKorpiPK getUKorpiPK() {
        return uKorpiPK;
    }

    public void setUKorpiPK(UKorpiPK uKorpiPK) {
        this.uKorpiPK = uKorpiPK;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public Artikal getArtikal() {
        return artikal;
    }

    public void setArtikal(Artikal artikal) {
        this.artikal = artikal;
    }

    public Korpa getKorpa() {
        return korpa;
    }

    public void setKorpa(Korpa korpa) {
        this.korpa = korpa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uKorpiPK != null ? uKorpiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UKorpi)) {
            return false;
        }
        UKorpi other = (UKorpi) object;
        if ((this.uKorpiPK == null && other.uKorpiPK != null) || (this.uKorpiPK != null && !this.uKorpiPK.equals(other.uKorpiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UKorpi[ uKorpiPK=" + uKorpiPK + " ]";
    }
    
}
