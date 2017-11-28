
package inventory_system;

import java.util.Date;

/**
 *
 * @author tebogo
 */

public class MessageError {
    
    private Integer erid;
    private String ermsgCat;
    private String ermsg;
    private Date erdate;
    private String erpriority;
    private int msgtypeid;
    private int userid;
    private String module;

    public MessageError() {
    }

    public MessageError(Integer erid) {
        this.erid = erid;
    }

    public MessageError(Integer erid, String ermsgCat, String ermsg, Date erdate, String erpriority,int msgtypeid, int userid, String module) {
        this.erid = erid;
        this.ermsgCat = ermsgCat;
        this.ermsg = ermsg;
        this.erdate = erdate;
        this.erpriority = erpriority;
        this.msgtypeid = msgtypeid;
        this.userid = userid;
        this.module = module;
    }

    public Integer getErid() {
        return erid;
    }

    public void setErid(Integer erid) {
        this.erid = erid;
    }

    public String getErmsgCat() {
        return ermsgCat;
    }

    public void setErmsgCat(String ermsgCat) {
        this.ermsgCat = ermsgCat;
    }

    public String getErmsg() {
        return ermsg;
    }

    public void setErmsg(String ermsg) {
        this.ermsg = ermsg;
    }

    public Date getErdate() {
        return erdate;
    }

    public void setErdate(Date erdate) {
        this.erdate = erdate;
    }

    public String getErpriority() {
        return erpriority;
    }

    public void setErpriority(String erpriority) {
        this.erpriority = erpriority;
    }
    
     public int getMsgTypeid() {
        return msgtypeid;
    }

    public void setMsgTypeid(int msgtypeid) {
        this.msgtypeid = msgtypeid;
    }
    
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }    
}
