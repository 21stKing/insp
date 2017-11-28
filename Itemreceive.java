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
    @NamedQuery(name = "Itemreceive.findAll", query = "SELECT i FROM Itemreceive i"),
    @NamedQuery(name = "Itemreceive.findByReceiveid", query = "SELECT i FROM Itemreceive i WHERE i.receiveid = :receiveid"),
    @NamedQuery(name = "Itemreceive.findByItemid", query = "SELECT i FROM Itemreceive i WHERE i.itemid = :itemid"),
    @NamedQuery(name = "Itemreceive.findByLocationid", query = "SELECT i FROM Itemreceive i WHERE i.locationid = :locationid"),
    @NamedQuery(name = "Itemreceive.findByReceivedqty", query = "SELECT i FROM Itemreceive i WHERE i.receivedqty = :receivedqty"),
    @NamedQuery(name = "Itemreceive.findByReceiveddate", query = "SELECT i FROM Itemreceive i WHERE i.receiveddate = :receiveddate"),
    @NamedQuery(name = "Itemreceive.findByReceivedby", query = "SELECT i FROM Itemreceive i WHERE i.receivedby = :receivedby"),
    @NamedQuery(name = "Itemreceive.findByTranscno", query = "SELECT i FROM Itemreceive i WHERE i.transcno = :transcno"),
    @NamedQuery(name = "Itemreceive.findByRefno", query = "SELECT i FROM Itemreceive i WHERE i.refno = :refno")})
public class Itemreceive implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer receiveid;
    @Basic(optional = false)
    private int itemid;
    @Basic(optional = false)
    private int locationid;
    @Basic(optional = false)
    private int receivedqty;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date receiveddate;
    @Basic(optional = false)
    private int receivedby;
    @Basic(optional = false)
    private int transcno;
    @Basic(optional = false)
    private String refno;

    public Itemreceive() {
    }

    public Itemreceive(Integer receiveid) {
        this.receiveid = receiveid;
    }

    public Itemreceive(Integer receiveid, int itemid, int locationid, int receivedqty, Date receiveddate, int receivedby, int transcno, String refno) {
        this.receiveid = receiveid;
        this.itemid = itemid;
        this.locationid = locationid;
        this.receivedqty = receivedqty;
        this.receiveddate = receiveddate;
        this.receivedby = receivedby;
        this.transcno = transcno;
        this.refno = refno;
    }

    public Integer getReceiveid() {
        return receiveid;
    }

    public void setReceiveid(Integer receiveid) {
        this.receiveid = receiveid;
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

    public int getReceivedqty() {
        return receivedqty;
    }

    public void setReceivedqty(int receivedqty) {
        this.receivedqty = receivedqty;
    }

    public Date getReceiveddate() {
        return receiveddate;
    }

    public void setReceiveddate(Date receiveddate) {
        this.receiveddate = receiveddate;
    }

    public int getReceivedby() {
        return receivedby;
    }

    public void setReceivedby(int receivedby) {
        this.receivedby = receivedby;
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
        hash += (receiveid != null ? receiveid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Itemreceive)) {
            return false;
        }
        Itemreceive other = (Itemreceive) object;
        if ((this.receiveid == null && other.receiveid != null) || (this.receiveid != null && !this.receiveid.equals(other.receiveid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "inventory_system.Itemreceive[ receiveid=" + receiveid + " ]";
    }
    
}
