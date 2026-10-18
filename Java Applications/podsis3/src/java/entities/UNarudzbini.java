/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import idClass.UNarudzbiniPK;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "u_narudzbini")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UNarudzbini.findAll", query = "SELECT u FROM UNarudzbini u"),
    @NamedQuery(name = "UNarudzbini.findByIdN", query = "SELECT u FROM UNarudzbini u WHERE u.uNarudzbiniPK.idN = :idN"),
    @NamedQuery(name = "UNarudzbini.findByIdArt", query = "SELECT u FROM UNarudzbini u WHERE u.uNarudzbiniPK.idArt = :idArt"),
    @NamedQuery(name = "UNarudzbini.findByKolicina", query = "SELECT u FROM UNarudzbini u WHERE u.kolicina = :kolicina"),
    @NamedQuery(name = "UNarudzbini.findByJedinicnaCena", query = "SELECT u FROM UNarudzbini u WHERE u.jedinicnaCena = :jedinicnaCena")})
public class UNarudzbini implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UNarudzbiniPK uNarudzbiniPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kolicina")
    private int kolicina;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "jedinicnaCena")
    private BigDecimal jedinicnaCena;
    @JoinColumn(name = "idN", referencedColumnName = "idN", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Narudzbina narudzbina;

    public UNarudzbini() {
    }

    public UNarudzbini(UNarudzbiniPK uNarudzbiniPK) {
        this.uNarudzbiniPK = uNarudzbiniPK;
    }

    public UNarudzbini(UNarudzbiniPK uNarudzbiniPK, int kolicina, BigDecimal jedinicnaCena) {
        this.uNarudzbiniPK = uNarudzbiniPK;
        this.kolicina = kolicina;
        this.jedinicnaCena = jedinicnaCena;
    }

    public UNarudzbini(int idN, int idArt) {
        this.uNarudzbiniPK = new UNarudzbiniPK(idN, idArt);
    }

    public UNarudzbiniPK getUNarudzbiniPK() {
        return uNarudzbiniPK;
    }

    public void setUNarudzbiniPK(UNarudzbiniPK uNarudzbiniPK) {
        this.uNarudzbiniPK = uNarudzbiniPK;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public BigDecimal getJedinicnaCena() {
        return jedinicnaCena;
    }

    public void setJedinicnaCena(BigDecimal jedinicnaCena) {
        this.jedinicnaCena = jedinicnaCena;
    }

    public Narudzbina getNarudzbina() {
        return narudzbina;
    }

    public void setNarudzbina(Narudzbina narudzbina) {
        this.narudzbina = narudzbina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uNarudzbiniPK != null ? uNarudzbiniPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UNarudzbini)) {
            return false;
        }
        UNarudzbini other = (UNarudzbini) object;
        if ((this.uNarudzbiniPK == null && other.uNarudzbiniPK != null) || (this.uNarudzbiniPK != null && !this.uNarudzbiniPK.equals(other.uNarudzbiniPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UNarudzbini[ uNarudzbiniPK=" + uNarudzbiniPK + " ]";
    }
    
}
