
package inventory_system;

public class MultipleReceive 
{   
    private String name;
    private String companyid;
    private String itemID;
    private String description;
    private String location;
    private String quantity;
    private String date;
    private String facilitator;
    private String storeman;
    private String receiveQuanttity;
    MultipleReceive[] items;

    
    public MultipleReceive(String name, String companyid,String itemID,String descrip,String location,String quantity, String date, String facilitator,String storeman,String receiveQuant) //constructor
    {
        this.name        = name;
        this.companyid   = companyid;
        this.itemID      = itemID;
        this.description = descrip;
        this.location    = location;
        this.quantity    = quantity;
        this.date        = date;
        this.facilitator = facilitator;
        this.storeman    = storeman;
        this.receiveQuanttity = receiveQuant;
    }
    
    //Set methods here
    /**public void setstoreman(String storeman){this.storeman = storeman;}
    public void setQuantity(String quantity){this.quantity = quantity;}
    public void setFacilitator(String facilitator){this.facilitator = facilitator;}
    public void setDate(String date){this.date = date;}
    public void setCompanyID(String companyid) {this.companyid = companyid;}*/
    
    //Get methods here
    public String getName(){return name;}
    public String getCompanyID(){return companyid;}
    public String getItemID(){return itemID;}
    public String getDescription(){return description;}
    public String getLocation(){return location;}
    public String getQuantity(){return quantity ;}
    public String getFacilitator(){return facilitator ;}
    public String getDate(){return date;}
    public String getstoreman(){return storeman;}
    public String getReceiveQuantity(){return receiveQuanttity;}
    
    @Override
    public String toString()
    {
       return this.getName()+" "+this.getstoreman()+" "+this.getQuantity()+" "+this.getFacilitator()+" "+this.getDate()+" "+this.getCompanyID()+" "+this.getDescription()+" "+this.getLocation()+"\n";        
    }
    
    public void displayTrolleyContents()
    {
        System.out.println("ItemID: "+storeman+"\n"
                         + "Store: "+quantity+"\n"
                         + "Location: "+facilitator+"\n"
                         + "Description: "+date+"\n"
                         + "Current Stock: "+companyid+"\n");
    }
    
}
