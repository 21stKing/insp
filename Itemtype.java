
package inventory_system;

import java.io.Serializable;

/**
 *
 * @author Tebogo kekana - kinotech (Pty) Ltd.
 */

public class Itemtype implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer typeid;
    private String type;

    public Itemtype() {
    }

    public Itemtype(Integer typeid) {
        this.typeid = typeid;
    }

    public Itemtype(Integer typeid, String type) {
        this.typeid = typeid;
        this.type = type;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
