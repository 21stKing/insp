/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inventory_system;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
    @NamedQuery(name = "Itemissue.findAll", query = "SELECT i FROM Itemissue i"),
    @NamedQuery(name = "Itemissue.findByIssueid", query = "SELECT i FROM Itemissue i WHERE i.issueid = :issueid"),
    @NamedQuery(name = "Itemissue.findByItemid", query = "SELECT i FROM Itemissue i WHERE i.itemid = :itemid"),
    @NamedQuery(name = "Itemissue.findByLocationid", query = "SELECT i FROM Itemissue i WHERE i.locationid = :locationid"),
    @NamedQuery(name = "Itemissue.findByIssuedqty", query = "SELECT i FROM Itemissue i WHERE i.issuedqty = :issuedqty"),
    @NamedQuery(name = "Itemissue.findByIssueddate", query = "SELECT i FROM Itemissue i WHERE i.issueddate = :issueddate"),
    @NamedQuery(name = "Itemissue.findByIssuedby", query = "SELECT i FROM Itemissue i WHERE i.issuedby = :issuedby"),
    @NamedQuery(name = "Itemissue.findByTranscno", query = "SELECT i FROM Itemissue i WHERE i.transcno = :transcno"),
    @NamedQuery(name = "Itemissue.findByRefno", query = "SELECT i FROM Itemissue i WHERE i.refno = :refno")})
public class Itemissue implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer issueid;
    @Basic(optional = false)
    private int itemid;
    @Basic(optional = false)
    private int locationid;
    @Basic(optional = false)
    private int issuedqty;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date issueddate;
    @Basic(optional = false)
    private int issuedby;
    @Basic(optional = false)
    private int transcno;
    @Basic(optional = false)
    private String refno;

    public Itemissue() {
    }

    public Itemissue(Integer issueid) {
        this.issueid = issueid;
    }

    public Itemissue(Integer issueid, int itemid, int locationid, int issuedqty, Date issueddate, int issuedby, int transcno, String refno) {
        this.issueid = issueid;
        this.itemid = itemid;
        this.locationid = locationid;
        this.issuedqty = issuedqty;
        this.issueddate = issueddate;
        this.issuedby = issuedby;
        this.transcno = transcno;
        this.refno = refno;
    }

    public Integer getIssueid() {
        return issueid;
    }

    public void setIssueid(Integer issueid) {
        this.issueid = issueid;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getLocationid() {
        return locationid;
    }

    public void setLocationid(int locationid) {
        this.locationid = locationid;
    }

    public int getIssuedqty() {
        return issuedqty;
    }

    public void setIssuedqty(int issuedqty) {
        this.issuedqty = issuedqty;
    }

    public Date getIssueddate() {
        return issueddate;
    }

    public void setIssueddate(Date issueddate) {
        this.issueddate = issueddate;
    }

    public int getIssuedby() {
        return issuedby;
    }

    public void setIssuedby(int issuedby) {
        this.issuedby = issuedby;
    }

    public int getTranscno() {
        return transcno;
    }

    public void setTranscno(int transcno) {
        this.transcno = transcno;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (issueid != null ? issueid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Itemissue)) {
            return false;
        }
        Itemissue other = (Itemissue) object;
        if ((this.issueid == null && other.issueid != null) || (this.issueid != null && !this.issueid.equals(other.issueid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "inventory_system.Itemissue[ issueid=" + issueid + " ]";
    }
    
}
