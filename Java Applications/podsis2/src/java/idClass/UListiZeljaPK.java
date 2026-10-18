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
public class UListiZeljaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idList")
    private int idList;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idArt")
    private int idArt;

    public UListiZeljaPK() {
    }

    public UListiZeljaPK(int idList, int idArt) {
        this.idList = idList;
        this.idArt = idArt;
    }

    public int getIdList() {
        return idList;
    }

    public void setIdList(int idList) {
        this.idList = idList;
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
        hash += (int) idList;
        hash += (int) idArt;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UListiZeljaPK)) {
            return false;
        }
        UListiZeljaPK other = (UListiZeljaPK) object;
        if (this.idList != other.idList) {
            return false;
        }
        if (this.idArt != other.idArt) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UListiZeljaPK[ idList=" + idList + ", idArt=" + idArt + " ]";
    }
    
}
