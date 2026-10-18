/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idClass;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Konstantin
 */
@Embeddable
public class UNarudzbiniPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idN")
    private int idN;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idArt")
    private int idArt;

    public UNarudzbiniPK() {
    }

    public UNarudzbiniPK(int idN, int idArt) {
        this.idN = idN;
        this.idArt = idArt;
    }

    public int getIdN() {
        return idN;
    }

    public void setIdN(int idN) {
        this.idN = idN;
    }

    public int getIdArt() {
        return idArt;
    }

    public void setIdArt(int idArt) {
        this.idArt = idArt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idN;
        hash += (int) idArt;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UNarudzbiniPK)) {
            return false;
        }
        UNarudzbiniPK other = (UNarudzbiniPK) object;
        if (this.idN != other.idN) {
            return false;
        }
        if (this.idArt != other.idArt) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UNarudzbiniPK[ idN=" + idN + ", idArt=" + idArt + " ]";
    }
    
}
