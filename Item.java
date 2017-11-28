
package inventory_system;

import java.util.Date;


public class Item {

    private Integer itemid;
    private String itemdescription;
    private Integer itembarcodeid;
    private Integer itemState;
    private String serialno;
    private double purchase_cost;
    private double sales_cost;
    private int suppId;
    private int umid;
    private Integer lock;
    private int locationid;
    private int catid;
    private Date datecreated;
    private Date datemodified;
    private int imageid;
    private int manid;
    private int modelid;

    public Item() {
    }

    public Item(Integer itemid) {
        this.itemid = itemid;
    }
    
    /**
     * Item class constructor
     * @param itemid
     * @param itemdescription
     * @param barcodeid
     * @param itemstate
     * @param serialno
     * @param purchase_cost
     * @param sales_cost
     * @param suppId
     * @param lockstate
     * @param locationid
     * @param category
     * @param datecreated
     * @param datemodified
     * @param imageid
     * @param manid
     * @param modelid 
     */
    public Item(int itemid, String itemdescription,int barcodeid ,int itemstate , String serialno, int suppId,int lockstate,  int locationid, int category, Date datecreated, Date datemodified, int imageid, int manid, int modelid, double purchase_cost, double sales_cost, int umid) {
        this.itemid = itemid;
        this.itemdescription = itemdescription;
        this.itembarcodeid = barcodeid;
        this.itemState = itemstate;
        this.serialno = serialno;
        this.purchase_cost = purchase_cost;
        this.sales_cost = sales_cost;
        this.suppId = suppId;
        this.umid = umid;
        this.lock = lockstate;
        this.locationid = locationid;
        this.catid = category;
        this.datecreated = datecreated;
        this.datemodified = datemodified;
        this.imageid = imageid;
        this.manid = manid;
        this.modelid = modelid;
    }

    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public String getItemdescription() {
        return itemdescription;
    }

    public void setItemdescription(String itemdescription) {
        this.itemdescription = itemdescription;
    }

    public Integer getItembarcodeid() {
        return itembarcodeid;
    }

    public void setItembarcodeid(Integer itembarcodeid) {
        this.itembarcodeid = itembarcodeid;
    }

    public Integer getItemState() {
        return itemState;
    }

    public void setItemState(Integer itemState) {
        this.itemState = itemState;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public int getSuppId() {
        return this.suppId;
    }
    
     public void setSuppId(int suppId) {
        this.suppId = suppId;
    }
    
    public double getPurchaseCost() {
        return this.purchase_cost;
    }
    
    public void setPurchaseCost(double purchase_cost) {
        this.purchase_cost = purchase_cost;
    }
    
    public double getSalesCost() {
        return this.sales_cost;
    }
    
    public void setSalesCost(double sales_cost) {
        this.sales_cost = sales_cost;
    }
    
    public int getUmId() {
        return this.umid;
    }
    
   public void setUmId(int umid) {
        this.umid = umid;
    }

    public Integer getLock() {
        return lock;
    }

    public void setLock(Integer lock) {
        this.lock = lock;
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

    public int getLocationid() {
        return locationid;
    }

    public void setLocationid(int locationid) {
        this.locationid = locationid;
    }
    
     public int getCatid() {
        return catid;
    }

    public void setCatid(int categoryid) {
        this.catid = categoryid;
    }
    
    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public int getManid() {
        return manid;
    }

    public void setManid(int manid) {
        this.manid = manid;
    }

    public int getModelid() {
        return modelid;
    }

    public void setModelid(int modelid) {
        this.modelid = modelid;
    }
    
    
}
