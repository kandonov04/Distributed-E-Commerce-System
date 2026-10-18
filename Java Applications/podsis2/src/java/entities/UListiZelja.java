/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import idClass.UListiZeljaPK;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Konstantin
 */
@Entity
@Table(name = "u_listi_zelja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UListiZelja.findAll", query = "SELECT u FROM UListiZelja u"),
    @NamedQuery(name = "UListiZelja.findByIdList", query = "SELECT u FROM UListiZelja u WHERE u.uListiZeljaPK.idList = :idList"),
    @NamedQuery(name = "UListiZelja.findByIdArt", query = "SELECT u FROM UListiZelja u WHERE u.uListiZeljaPK.idArt = :idArt"),
    @NamedQuery(name = "UListiZelja.findByDodato", query = "SELECT u FROM UListiZelja u WHERE u.dodato = :dodato")})
public class UListiZelja implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UListiZeljaPK uListiZeljaPK;
    @Column(name = "dodato")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dodato;
    @JoinColumn(name = "idArt", referencedColumnName = "idArt", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Artikal artikal;
    @JoinColumn(name = "idList", referencedColumnName = "idList", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ListaZelja listaZelja;

    public UListiZelja() {
    }

    public UListiZelja(UListiZeljaPK uListiZeljaPK) {
        this.uListiZeljaPK = uListiZeljaPK;
    }

    public UListiZelja(int idList, int idArt) {
        this.uListiZeljaPK = new UListiZeljaPK(idList, idArt);
    }

    public UListiZeljaPK getUListiZeljaPK() {
        return uListiZeljaPK;
    }

    public void setUListiZeljaPK(UListiZeljaPK uListiZeljaPK) {
        this.uListiZeljaPK = uListiZeljaPK;
    }

    public Date getDodato() {
        return dodato;
    }

    public void setDodato(Date dodato) {
        this.dodato = dodato;
    }

    public Artikal getArtikal() {
        return artikal;
    }

    public void setArtikal(Artikal artikal) {
        this.artikal = artikal;
    }

    public ListaZelja getListaZelja() {
        return listaZelja;
    }

    public void setListaZelja(ListaZelja listaZelja) {
        this.listaZelja = listaZelja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uListiZeljaPK != null ? uListiZeljaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UListiZelja)) {
            return false;
        }
        UListiZelja other = (UListiZelja) object;
        if ((this.uListiZeljaPK == null && other.uListiZeljaPK != null) || (this.uListiZeljaPK != null && !this.uListiZeljaPK.equals(other.uListiZeljaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UListiZelja[ uListiZeljaPK=" + uListiZeljaPK + " ]";
    }
    
}
