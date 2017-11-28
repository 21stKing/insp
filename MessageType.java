/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inventory_system;


/**
 *
 * @author tebogo
 */

public class MessageType {
   
    private Integer msgtypeid;
    private String msgtype;

    public MessageType() {
    }

    public MessageType(Integer msgtypeid) {
        this.msgtypeid = msgtypeid;
    }

    public MessageType(Integer msgtypeid, String msgtype) {
        this.msgtypeid = msgtypeid;
        this.msgtype = msgtype;
    }

    public Integer getMsgtypeid() {
        return msgtypeid;
    }

    public void setMsgtypeid(Integer msgtypeid) {
        this.msgtypeid = msgtypeid;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }   
}
