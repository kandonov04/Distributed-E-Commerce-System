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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Konstantin
 */
@Entity
@Table(name = "artikal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artikal.findAll", query = "SELECT a FROM Artikal a"),
    @NamedQuery(name = "Artikal.findByIdArt", query = "SELECT a FROM Artikal a WHERE a.idArt = :idArt"),
    @NamedQuery(name = "Artikal.findByIme", query = "SELECT a FROM Artikal a WHERE a.ime = :ime"),
    @NamedQuery(name = "Artikal.findByOpis", query = "SELECT a FROM Artikal a WHERE a.opis = :opis"),
    @NamedQuery(name = "Artikal.findByCena", query = "SELECT a FROM Artikal a WHERE a.cena = :cena"),
    @NamedQuery(name = "Artikal.findByPopust", query = "SELECT a FROM Artikal a WHERE a.popust = :popust"),
    @NamedQuery(name = "Artikal.findByIdKor", query = "SELECT a FROM Artikal a WHERE a.idKor = :idKor")})
public class Artikal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idArt")
    private Integer idArt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ime")
    private String ime;
    @Size(max = 200)
    @Column(name = "opis")
    private String opis;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private BigDecimal cena;
    @Column(name = "popust")
    private Integer popust;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idKor")
    private int idKor;
    @JoinTable(name = "pripada", joinColumns = {
        @JoinColumn(name = "idArt", referencedColumnName = "idArt")}, inverseJoinColumns = {
        @JoinColumn(name = "idKat", referencedColumnName = "idKat")})
    @ManyToMany
    private List<Kategorija> kategorijaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artikal")
    private List<UKorpi> uKorpiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artikal")
    private List<UListiZelja> uListiZeljaList;

    public Artikal() {
    }

    public Artikal(Integer idArt) {
        this.idArt = idArt;
    }

    public Artikal(Integer idArt, String ime, BigDecimal cena, int idKor) {
        this.idArt = idArt;
        this.ime = ime;
        this.cena = cena;
        this.idKor = idKor;
    }

    public Integer getIdArt() {
        return idArt;
    }

    public void setIdArt(Integer idArt) {
        this.idArt = idArt;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public Integer getPopust() {
        return popust;
    }

    public void setPopust(Integer popust) {
        this.popust = popust;
    }

    public int getIdKor() {
        return idKor;
    }

    public void setIdKor(int idKor) {
        this.idKor = idKor;
    }

    @XmlTransient
    public List<Kategorija> getKategorijaList() {
        return kategorijaList;
    }

    public void setKategorijaList(List<Kategorija> kategorijaList) {
        this.kategorijaList = kategorijaList;
    }

    @XmlTransient
    public List<UKorpi> getUKorpiList() {
        return uKorpiList;
    }

    public void setUKorpiList(List<UKorpi> uKorpiList) {
        this.uKorpiList = uKorpiList;
    }

    @XmlTransient
    public List<UListiZelja> getUListiZeljaList() {
        return uListiZeljaList;
    }

    public void setUListiZeljaList(List<UListiZelja> uListiZeljaList) {
        this.uListiZeljaList = uListiZeljaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArt != null ? idArt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artikal)) {
            return false;
        }
        Artikal other = (Artikal) object;
        if ((this.idArt == null && other.idArt != null) || (this.idArt != null && !this.idArt.equals(other.idArt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Artikal[ idArt=" + idArt + " ]";
    }
    
}
