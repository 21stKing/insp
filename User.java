
package inventory_system;

import java.util.Date;

/**
 *
 * @author tebogo
 */

public class User  {
    
    
    private Integer userid;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String hometel;
    private String mobile;
    private Integer addressid;
    private Integer roleid;
    private Integer imageid;
    private int userState;
    private Date datecreated;
    private Date datemodified;
    private Date enddate;

    public User() {
    }

    public User(Integer userid) {
        this.userid = userid;
    }
    
    /*
    * Modified for sales reps
    */
    public User(Integer userid, String firstname, String lastname,String email){
        this.userid = userid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
    
    public User(Integer userid, String username, String password, String firstname, String lastname,String email, String hometel, String mobile, int addressid, int roleid, int imageid, int userState, Date datecreated, Date datemodified, Date enddate) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.hometel = hometel;
        this.mobile = mobile;
        this.addressid = addressid;
        this.roleid = roleid;
        this.imageid = imageid;
        this.userState = userState;
        this.datecreated = datecreated;
        this.datemodified = datemodified;
        this.enddate = enddate;        
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    protected String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHometel() {
        return hometel;
    }

    public void setHometel(String hometel) {
        this.hometel = hometel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getAddressid() {
        return addressid;
    }

    public void setAddressid(Integer addressid) {
        this.addressid = addressid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getImageid() {
        return imageid;
    }

    public void setImageid(Integer imageid) {
        this.imageid = imageid;
    }

    public int getUserState() {
        return userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
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
