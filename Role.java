
package inventory_system;


/**
 *
 * @author tebogo
 */

public class Role  {
   
    private Integer roleid;
    private String rolename;
    private String description;
    private Integer roleState;

    public Role() {
    }

    public Role(Integer roleid, String rolename, String roledescription, int rolestate) {
        this.roleid = roleid;
        this.rolename = rolename;
        this.description = roledescription;
        this.roleState = rolestate;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRoleState() {
        return roleState;
    }

    public void setRoleState(Integer roleState) {
        this.roleState = roleState;
    }
}
