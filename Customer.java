
package inventory_system;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Tebogo kekana - kinotech (Pty) Ltd.
 */

public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer custid;
    private String custname;
    private int catid;
    private String vatnumber;
    private int einvoice;
    private int salesrepid;
    private int conid;
    private int addressid;
    private int imageid;
    private int custState;
    private Date datecreated;
    private Date datemodified;
    private Date enddate;

    public Customer() {
    }

    public Customer(Integer custid) {
        this.custid = custid;
    }

    public Customer(Integer custid, String custname, int catid, String vatnumber, int einvoice, int salesrepid, int conid, int addressid, int imageid, int custState, Date datecreated, Date datemodified, Date enddate) {
        this.custid = custid;
        this.custname = custname;
        this.catid = catid;
        this.vatnumber = vatnumber;
        this.einvoice = einvoice;
        this.salesrepid = salesrepid;
        this.conid = conid;
        this.addressid = addressid;
        this.imageid = imageid;
        this.custState = custState;
        this.datecreated = datecreated;
        this.datemodified = datemodified;
        this.enddate = enddate;
    }

    public Integer getCustid() {
        return custid;
    }

    public void setCustid(Integer custid) {
        this.custid = custid;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public String getVatnumber() {
        return vatnumber;
    }

    public void setVatnumber(String vatnumber) {
        this.vatnumber = vatnumber;
    }

    public int getEinvoice() {
        return einvoice;
    }

    public void setEinvoice(int einvoice) {
        this.einvoice = einvoice;
    }

    public int getSalesrepid() {
        return salesrepid;
    }

    public void setSalesrepid(int salesrepid) {
        this.salesrepid = salesrepid;
    }

    public int getConid() {
        return conid;
    }

    public void setConid(int conid) {
        this.conid = conid;
    }

    public int getAddressid() {
        return addressid;
    }

    public void setAddressid(int addressid) {
        this.addressid = addressid;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public int getCustState() {
        return custState;
    }

    public void setCustState(int custState) {
        this.custState = custState;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Date getDatemodified() {
        return datemodified;
    }

    public void setDatemodified(Date datemodified) {
        this.datemodified = datemodified;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    
}
