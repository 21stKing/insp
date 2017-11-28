/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inventory_system;


import java.util.Date;
/**
 *
 * @author tebogo
 */

public class Metadata {
  
    private Integer mid;
    private int userset;
    private Date setdate;

    public Metadata() {
    }

    public Metadata(Integer mid) {
        this.mid = mid;
    }

    public Metadata(Integer mid, int userset, Date setdate) {
        this.mid = mid;
        this.userset = userset;
        this.setdate = setdate;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public int getUserset() {
        return userset;
    }

    public void setUserset(int userset) {
        this.userset = userset;
    }

    public Date getSetdate() {
        return setdate;
    }

    public void setSetdate(Date setdate) {
        this.setdate = setdate;
    }

}
