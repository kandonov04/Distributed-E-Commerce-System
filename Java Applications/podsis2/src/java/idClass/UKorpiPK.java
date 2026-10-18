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
public class UKorpiPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idK")
    private int idK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idArt")
    private int idArt;

    public UKorpiPK() {
    }

    public UKorpiPK(int idK, int idArt) {
        this.idK = idK;
        this.idArt = idArt;
    }

    public int getIdK() {
        return idK;
    }

    public void setIdK(int idK) {
        this.idK = idK;
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
        hash += (int) idK;
        hash += (int) idArt;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UKorpiPK)) {
            return false;
        }
        UKorpiPK other = (UKorpiPK) object;
        if (this.idK != other.idK) {
            return false;
        }
        if (this.idArt != other.idArt) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UKorpiPK[ idK=" + idK + ", idArt=" + idArt + " ]";
    }
    
}
