/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Konstantin
 */
@Entity
@Table(name = "lista_zelja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListaZelja.findAll", query = "SELECT l FROM ListaZelja l"),
    @NamedQuery(name = "ListaZelja.findByIdList", query = "SELECT l FROM ListaZelja l WHERE l.idList = :idList"),
    @NamedQuery(name = "ListaZelja.findByIdKor", query = "SELECT l FROM ListaZelja l WHERE l.idKor = :idKor"),
    @NamedQuery(name = "ListaZelja.findByNapravljena", query = "SELECT l FROM ListaZelja l WHERE l.napravljena = :napravljena")})
public class ListaZelja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idList")
    private Integer idList;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idKor")
    private int idKor;
    @Column(name = "napravljena", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date napravljena;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "listaZelja")
    private List<UListiZelja> uListiZeljaList;

    public ListaZelja() {
    }

    public ListaZelja(Integer idList) {
        this.idList = idList;
    }

    public ListaZelja(Integer idList, int idKor) {
        this.idList = idList;
        this.idKor = idKor;
    }

    public Integer getIdList() {
        return idList;
    }

    public void setIdList(Integer idList) {
        this.idList = idList;
    }

    public int getIdKor() {
        return idKor;
    }

    public void setIdKor(int idKor) {
        this.idKor = idKor;
    }

    public Date getNapravljena() {
        return napravljena;
    }

    public void setNapravljena(Date napravljena) {
        this.napravljena = napravljena;
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
        hash += (idList != null ? idList.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaZelja)) {
            return false;
        }
        ListaZelja other = (ListaZelja) object;
        if ((this.idList == null && other.idList != null) || (this.idList != null && !this.idList.equals(other.idList))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ListaZelja[ idList=" + idList + " ]";
    }
    
}
