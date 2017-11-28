/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inventory_system;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author tebogo
 */

public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer suppId;
    private String suppname;
    private Integer addressid;
    private String contact;
    private int suppState;
    private Date enddate;

    public Supplier() {
    }
   
    public Supplier(int suppid, String suppname,int addressid,String contact,int state, Date enddate) {
      this.suppId = suppid;
      this.suppname = suppname;
      this.addressid = addressid;
      this.contact = contact;
      this.suppState = state;
      this.enddate = enddate;
    }
     
    public Supplier(Integer suppId) {
        this.suppId = suppId;
    }

    public Supplier(Integer suppId, int suppState) {
        this.suppId = suppId;
        this.suppState = suppState;
    }

    public Integer getSuppId() {
        return suppId;
    }

    public void setSuppId(Integer suppId) {
        this.suppId = suppId;
    }

    public String getSuppname() {
        return suppname;
    }

    public void setSuppname(String suppname) {
        this.suppname = suppname;
    }

    public Integer getAddressid() {
        return addressid;
    }

    public void setAddressid(Integer addressid) {
        this.addressid = addressid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getSuppState() {
        return suppState;
    }

    public void setSuppState(int suppState) {
        this.suppState = suppState;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
    
}
