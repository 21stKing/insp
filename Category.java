

package inventory_system;

import java.io.Serializable;

/**
 *
 * @author 
 */

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer catid;
    private String catname;
    private String subtype;

    public Category() {
    }

    public Category(Integer catid) {
        this.catid = catid;
    }

    public Category(Integer catid, String catname, String subtype) {
        this.catid = catid;
        this.catname = catname;
        this.subtype = subtype;
    }

    public Integer getCatid() {
        return catid;
    }

    public void setCatid(Integer catid) {
        this.catid = catid;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }
    
    public String getSubtype(){
        return subtype;
    }
    
    public void setSubtype(String subtype){
        this.subtype = subtype;
    }
        
}
