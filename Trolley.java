
package inventory_system;

public class Trolley 
{
    private int item_id;
    private String store;
    private String location;
    private String description;
    private String current_stock;
    private String quantity;
    Trolley[] items;
    
    public Trolley(int item_id, String store, String location, String description, String current_stock, String quantity) //constructor
    {
        this.item_id       = item_id;
        this.store         = store;
        this.location      = location;
        this.description   = description;
        this.current_stock = current_stock;
        this.quantity      = quantity;
    }
    
    //Set methods here
    public void setItem_id(int itemID){this.item_id = itemID;}
    public void setStore(String store){this.store = store;}
    public void setLocation(String location){this.location = location;}
    public void setDescription(String description){this.description = description;}
    public void setCurrentStock(String currentStock) {this.current_stock = currentStock;}
    public void setQuantity(String quantity){this.quantity = quantity;}
    
    //Get methods here
    public int getItem_id(){return item_id;}
    public String getStore(){return store ;}
    public String getLocation(){return location;}
    public String getDescription(){return description;}
    public String getCurrentStock(){return current_stock;}
    public String getQuantity(){return quantity ;}
    
    @Override
    public String toString()
    {
       return this.getItem_id()+" "+this.getStore()+" "+this.getLocation()+" "+this.getDescription()+" "+this.getCurrentStock()+" "+this.getQuantity()+"\n";        
    }
    
    public void displayTrolleyContents()
    {
        System.out.println("ItemID: "+item_id+"\n"
                         + "Store: "+store+"\n"
                         + "Location: "+location+"\n"
                         + "Description: "+description+"\n"
                         + "Current Stock: "+current_stock+"\n"
                         + "Quantity: "+quantity);
    }
    
}
