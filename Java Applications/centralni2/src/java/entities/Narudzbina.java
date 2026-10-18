/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Konstantin
 */
@Entity
@Table(name = "narudzbina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Narudzbina.findAll", query = "SELECT n FROM Narudzbina n"),
    @NamedQuery(name = "Narudzbina.findByIdN", query = "SELECT n FROM Narudzbina n WHERE n.idN = :idN"),
    @NamedQuery(name = "Narudzbina.findByIdKor", query = "SELECT n FROM Narudzbina n WHERE n.idKor = :idKor"),
    @NamedQuery(name = "Narudzbina.findByUkupnaCena", query = "SELECT n FROM Narudzbina n WHERE n.ukupnaCena = :ukupnaCena"),
    @NamedQuery(name = "Narudzbina.findByNapravljeno", query = "SELECT n FROM Narudzbina n WHERE n.napravljeno = :napravljeno"),
    @NamedQuery(name = "Narudzbina.findByAdresa", query = "SELECT n FROM Narudzbina n WHERE n.adresa = :adresa"),
    @NamedQuery(name = "Narudzbina.findByIdG", query = "SELECT n FROM Narudzbina n WHERE n.idG = :idG")})
public class Narudzbina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idN")
    private Integer idN;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idKor")
    private int idKor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ukupnaCena")
    private BigDecimal ukupnaCena;
    @Column(name = "napravljeno")
    @Temporal(TemporalType.TIMESTAMP)
    private Date napravljeno;
    @Size(max = 45)
    @Column(name = "adresa")
    private String adresa;
    @Column(name = "idG")
    private Integer idG;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idN")
    private Transakcija transakcija;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "narudzbina")
    private List<UNarudzbini> uNarudzbiniList;

    public Narudzbina() {
    }

    public Narudzbina(Integer idN) {
        this.idN = idN;
    }

    public Narudzbina(Integer idN, int idKor, BigDecimal ukupnaCena) {
        this.idN = idN;
        this.idKor = idKor;
        this.ukupnaCena = ukupnaCena;
    }

    public Integer getIdN() {
        return idN;
    }

    public void setIdN(Integer idN) {
        this.idN = idN;
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

    public Date getNapravljeno() {
        return napravljeno;
    }

    public void setNapravljeno(Date napravljeno) {
        this.napravljeno = napravljeno;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Integer getIdG() {
        return idG;
    }

    public void setIdG(Integer idG) {
        this.idG = idG;
    }

    public Transakcija getTransakcija() {
        return transakcija;
    }

    public void setTransakcija(Transakcija transakcija) {
        this.transakcija = transakcija;
    }

    @XmlTransient
    public List<UNarudzbini> getUNarudzbiniList() {
        return uNarudzbiniList;
    }

    public void setUNarudzbiniList(List<UNarudzbini> uNarudzbiniList) {
        this.uNarudzbiniList = uNarudzbiniList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idN != null ? idN.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Narudzbina)) {
            return false;
        }
        Narudzbina other = (Narudzbina) object;
        if ((this.idN == null && other.idN != null) || (this.idN != null && !this.idN.equals(other.idN))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Narudzbina[ idN=" + idN + " ]";
    }
    
}
