
package inventory_system;

/**
 *
 * @author tebogo
 */

public class Location {

    private Integer locationid;
    private String locname;
    private String locdescription;
    private int locState;
    private int locQty;
    private String locType;

    public Location() {
    }

    public Location(Integer locationid) {
        this.locationid = locationid;
    }

    public Location(int locationid, String locname, String locdescription, int locState, int locQty, String locType) {
        this.locationid = locationid;
        this.locname = locname;
        this.locdescription = locdescription;
        this.locState = locState;
        this.locQty = locQty;
        this.locType = locType;
    }

    public Integer getLocationid() {
        return locationid;
    }

    public void setLocationid(Integer locationid) {
        this.locationid = locationid;
    }

    public String getLocname() {
        return locname;
    }

    public void setLocname(String locname) {
        this.locname = locname;
    }

    public String getLocdescription() {
        return locdescription;
    }

    public void setLocdescription(String locdescription) {
        this.locdescription = locdescription;
    }

    public int getLocState() {
        return locState;
    }

    public void setLocState(int locState) {
        this.locState = locState;
    }

    public int getLocQty() {
        return locQty;
    }

    public void setLocQty(int locQty) {
        this.locQty = locQty;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }   
}
