
package inventory_system;

import java.util.Date;

/**
 *
 * @author tebogo
 */

public class Server {
    
    private Integer sid;
    private String serverName;
    private int portnumber;
    private String databasename;
    private String username;
    private String password;
    private Integer pwmemory;
    private String connectionString;
    private int serverState;
    private String driver;
    private Date setdate;

    public Server() {
    }

    public Server(Integer sid) {
        this.sid = sid;
    }

    public Server(Integer sid, String serverName, int portnumber, String databasename, String username, String password, int pwmemory,String connectionString, int serverState, String driver, Date setdate) {
        this.sid = sid;
        this.serverName = serverName;
        this.portnumber = portnumber;
        this.databasename = databasename;
        this.username = username;
        this.password = password;
        this.pwmemory = pwmemory;
        this.connectionString = connectionString;
        this.serverState = serverState;
        this.driver = driver;
        this.setdate = setdate;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getPortnumber() {
        return portnumber;
    }

    public void setPortnumber(int portnumber) {
        this.portnumber = portnumber;
    }

    public String getDatabase() {
        return databasename;
    }

    public void setDatabase(String databasename) {
        this.databasename = databasename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
     public int getPWMemory() {
        return pwmemory;
    }

    public void setPWMemory(int pwmemory) {
        this.pwmemory = pwmemory;
    }

    public Integer getPwmemory() {
        return pwmemory;
    }

    public void setPwmemory(Integer pwmemory) {
        this.pwmemory = pwmemory;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public int getServerState() {
        return serverState;
    }

    public void setServerState(int serverState) {
        this.serverState = serverState;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Date getSetdate() {
        return setdate;
    }

    public void setSetdate(Date setdate) {
        this.setdate = setdate;
    }

}
