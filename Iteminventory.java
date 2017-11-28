
package inventory_system;

/**
 *
 * @author tebogo
 */

public class Iteminventory {
    
    private Integer invid;
    private int itemid;
    //private int locationid; deleted from db table
    private int totalqty;
    private int currqty;
    private int transcno;
    private int refno;

    public Iteminventory()
    {
    }

    public Iteminventory(Integer invid) {
        this.invid = invid;
    }
    
    /**
     * ItemInventory class constructor
     * @param invid
     * @param itemid
     * @param totalqty
     * @param currqty
     * @param transcno
     * @param refno 
     */
    public Iteminventory(Integer invid, int itemid, int totalqty, int currqty, int transcno, int refno) {
        this.invid = invid;
        this.itemid = itemid;
        //this.locationid = locationid; 
        this.totalqty = totalqty;
        this.currqty = currqty;
        this.transcno = transcno;
        this.refno = refno;
    }

    public Integer getInvid() {
        return invid;
    }

    public void setInvid(Integer invid) {
        this.invid = invid;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

   /* public int getLocationid() {
        return locationid;
    }

    public void setLocationid(int locationid) {
        this.locationid = locationid;
    }*/

    public int getTotalqty() {
        return totalqty;
    }

    public void setTotalqty(int totalqty) {
        this.totalqty = totalqty;
    }

    public int getCurrqty() {
        return currqty;
    }

    public void setCurrqty(int currqty) {
        this.currqty = currqty;
    }

    public int getTranscno() {
        return transcno;
    }

    public void setTranscno(int transcno) {
        this.transcno = transcno;
    }

    public int getRefno() {
        return refno;
    }

    public void setRefno(int refno) {
        this.refno = refno;
    }   

}
