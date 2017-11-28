
package inventory_system;

import java.util.Date;

/**
 *
 * @author tebogo
 */

public class NetworkConfig  {
   
    private Integer netid;
   
    private String networkInterfaceName;
   
    private Date setdate;
    private Integer userid;

    public NetworkConfig() {
    }

    public NetworkConfig(Integer netid) {
        this.netid = netid;
    }

    public NetworkConfig(Integer netid, String networkInterfaceName, Date setdate, int userid) {
        this.netid = netid;
        this.networkInterfaceName = networkInterfaceName;
        this.setdate = setdate;
        this.userid = userid;
    }

    public Integer getNetid() {
        return netid;
    }

    public void setNetid(Integer netid) {
        this.netid = netid;
    }

    public String getNetworkInterfaceName() {
        return networkInterfaceName;
    }

    public void setNetworkInterfaceName(String networkInterfaceName) {
        this.networkInterfaceName = networkInterfaceName;
    }

    public Date getSetdate() {
        return setdate;
    }

    public void setSetdate(Date setdate) {
        this.setdate = setdate;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }    
}
