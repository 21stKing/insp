/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inventory_system;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author QXF3984
 */
@Entity
@Table(catalog = "dimdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Manufacture.findAll", query = "SELECT m FROM Manufacture m"),
    @NamedQuery(name = "Manufacture.findByManid", query = "SELECT m FROM Manufacture m WHERE m.manid = :manid"),
    @NamedQuery(name = "Manufacture.findByManname", query = "SELECT m FROM Manufacture m WHERE m.manname = :manname"),
    @NamedQuery(name = "Manufacture.findByAddressid", query = "SELECT m FROM Manufacture m WHERE m.addressid = :addressid"),
    @NamedQuery(name = "Manufacture.findByContact", query = "SELECT m FROM Manufacture m WHERE m.contact = :contact"),
    @NamedQuery(name = "Manufacture.findByManState", query = "SELECT m FROM Manufacture m WHERE m.manState = :manState"),
    @NamedQuery(name = "Manufacture.findByEnddate", query = "SELECT m FROM Manufacture m WHERE m.enddate = :enddate")})
public class Manufacture implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer manid;
    @Basic(optional = false)
    private String manname;
    @Basic(optional = false)
    private int addressid;
    @Basic(optional = false)
    private String contact;
    @Basic(optional = false)
    @Column(name = "man_state")
    private int manState;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date enddate;

    public Manufacture() {
    }

    public Manufacture(Integer manid) {
        this.manid = manid;
    }

    public Manufacture(Integer manid, String manname, int addressid, String contact, int manState, Date enddate) {
        this.manid = manid;
        this.manname = manname;
        this.addressid = addressid;
        this.contact = contact;
        this.manState = manState;
        this.enddate = enddate;
    }

    public Integer getManid() {
        return manid;
    }

    public void setManid(Integer manid) {
        this.manid = manid;
    }

    public String getManname() {
        return manname;
    }

    public void setManname(String manname) {
        this.manname = manname;
    }

    public int getAddressid() {
        return addressid;
    }

    public void setAddressid(int addressid) {
        this.addressid = addressid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getManState() {
        return manState;
    }

    public void setManState(int manState) {
        this.manState = manState;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (manid != null ? manid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Manufacture)) {
            return false;
        }
        Manufacture other = (Manufacture) object;
        if ((this.manid == null && other.manid != null) || (this.manid != null && !this.manid.equals(other.manid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "inventory_system.Manufacture[ manid=" + manid + " ]";
    }
    
}
