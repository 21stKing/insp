
package inventory_system;

import java.util.Date;


/**
 *
 * @author tebogo
 */

public class Messages {
    
    private Integer msgid;
    private String message;
    private int msgtypeid;
    private Date msgdate;
    private int userid;

    public Messages() {
    }

    public Messages(Integer msgid) {
        this.msgid = msgid;
    }

    public Messages(Integer msgid, String message, int msgtypeid, Date msgdate, int userid) {
        this.msgid = msgid;
        this.message = message;
        this.msgtypeid = msgtypeid;
        this.msgdate = msgdate;
        this.userid = userid;
    }

    public Integer getMsgid() {
        return msgid;
    }

    public void setMsgid(Integer msgid) {
        this.msgid = msgid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMsgtypeid() {
        return msgtypeid;
    }

    public void setMsgtypeid(int msgtypeid) {
        this.msgtypeid = msgtypeid;
    }

    public Date getMsgdate() {
        return msgdate;
    }

    public void setMsgdate(Date msgdate) {
        this.msgdate = msgdate;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
    
}
