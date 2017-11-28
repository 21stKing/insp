
package inventory_system;

import java.util.ArrayList;


public class ConstructMail
{   
    private String space5, space14,space41, space48,space59;
    protected static String description;
    protected static String store;
    protected static String location;
    protected static int quantity;
    private String facilitator;
    protected static String storeman;
    protected static String learner;
    protected static int companyid;
    protected static String date;
    protected static String returnDate;
    protected static String role;
    ArrayList<Trolley> items;
    ArrayList<MultipleReceive> receiveBasketItems;
    
    public ConstructMail() 
    { 
       space5 = String.format("%5s", " ");
       space14 = String.format("%2s", " ");
       space41 = String.format("%12s", " ");
       space48 = String.format("%15s", " ");
    }
    
    public void ConstructItems(String desc,String location, int quantity)  //(String desc,String location, int quantity,String facilitator,String storeman,String learner,int compid, String date)
    {
       this.description = desc;
       this.location = location;
       this.quantity = quantity;
      // this.facilitator = facilitator;
      // this.storeman = storeman;  
      // this.learner = learner;
       //this.companyid = compid;
      // this.date = date; 
    }
    
    public void setFacilitator(String facil){this.facilitator = facil;}
    public void setStoreman(String storeman){this.storeman = storeman;}
    public void setLearner(String learner){this.learner = learner;}
    public void setCompany_id(int coid){this.companyid = coid;}
    public void setDate(String dateIn){this.date = dateIn;}
    public void setReturnDate(String dueDate) {this.returnDate = dueDate;}
    public void setStore(String store){this.store = store;}
    public void setRole(String role){this.role = role;}
    
    
    public String getDescription(){return description;}
    public String getStore(){return store;}
    public String getLocation(){return location;}
    public int getQuantity(){return quantity;}
    public String getFacilitator(){return facilitator;}
    public String getStoreman(){return storeman;}
    public String getLearner(){return learner;}
    public int getCompanyid(){return companyid;}
    public String getDate(){return date;}
    public String getReturnDate(){return returnDate;}
    public String getRole(){return role;}
    
    public String FacilitatorNotificationCopy(String faci) {   
        StringBuilder sb = new StringBuilder();
        sb.append( "<html>"
                 + " <body>"
                 + "  Dear <font color=\"red\">"+faci+"</font><br>"
                 + "   Learner - <b><font color=\"red\">"+getCompanyid()+"</font></b> has been issued the following item(s) by "+storeman+"<br>"
                 + "   <b>Return Date <font color=\"red\">"+getReturnDate()+"</font></b><br><br>"
                 + "  <table border=\"1\">"
                 + "   <tr><th>Description</th><th>QTY</th><th>Date Issued</th><th>Location</th><th>Store</th></tr>"
                 + "   <tr><td>"+description+"</td><td>"+quantity+"</td><td>"+getDate()+"</td><td>"+location+"</td><td>"+getStore()+"</td></tr>"
                 + "  </table>"
                 + "  <br>"
                 + "  Regards,<br><br>"
                 + "  <i>Inventory Store Program (InSP) - BMW Technical Training Centre</i>"
                 + " </body>"
                 + "</html>"); 
        
        String message = sb.toString();
        return message;
    }
    
    /**
     * <p> Returns a notification message in an html fomart that has to be sent to a facilitator
     * @param faci
     * @return  message in html fomart 
     */
    public String FacilitatorReceivedItemsNotify(String faci) {   
        StringBuilder sb = new StringBuilder();
        sb.append( "<html>"
                 + " <body>"
                 + "  Dear <font color=\"red\">"+faci+"</font><br>"
                 + "   Learner - <b><font color=\"red\">"+getCompanyid()+"</font></b> returned the following item(s) proccessed by "+storeman+"<br>"
                 + "   <b>Received on <font color=\"red\">"+getDate()+"</font></b><br><br>"
                 + "  <table border=\"1\">"
                 + "   <tr><th>Description</th><th>QTY</th><th>Date Issued</th><th>Location</th><th>Store</th></tr>"
                 + "   <tr><td>"+description+"</td><td>"+quantity+"</td><td>"+getDate()+"</td><td>"+location+"</td><td>"+getStore()+"</td></tr>"
                 + "  </table>"
                 + "  <br>"
                 + "  Regards,<br><br>"
                 + "  <i>Inventory Store Program (InSP) - BMW Technical Training Centre</i>"
                 + " </body>"
                 + "</html>"); 
        
        String message = sb.toString();
        return message;
    }
    
    public String LearnerNotificationCopy(String learner) {        
        StringBuilder sb = new StringBuilder();
        sb.append( "<html>"
                 + " <body>"
                 + "  Dear <font color=\"red\">"+learner+"</font><br>"
                 + "   You have been issued the following item(s) from "+getStore()+"<br>"
                 + "   <b>Return Date <font color=\"red\">"+getReturnDate()+"</font></b><br><br>"
                 + "  <table border=\"1\">"
                 + "   <tr><th>Description</th><th>QTY</th><th>Date Issued</th><th>Location</th><th>Store</th></tr>"
                 + "   <tr><td>"+description+"</td><td>"+quantity+"</td><td>"+getDate()+"</td><td>"+location+"</td><td>"+getStore()+"</td></tr>"
                 + "  </table>"
                 + "  <br>"
                 + "  Facilitator: "+getFacilitator()+"<br>"
                 + "  Storeman   : "+getStoreman()+"<br><br>"
                 + "  Regards,<br><br>"
                 + "  <i>Inventory Store Program (InSP) - BMW Technical Training Centre</i>"
                 + " </body>"
                 + "</html>"); 
        
        String message = sb.toString();
        return message;
    }
    
    /**
     * <p> Returns a notification message in an html format that has to be sent to a learner
     * @param learner
     * @return <p> message in html format that is to be sent to a learner who returned the item(s)
     */
    public String LearnerReceivedItemsNotifify(String learner) {        
        StringBuilder sb = new StringBuilder();
        sb.append( "<html>"
                 + " <body>"
                 + "  Dear <font color=\"red\">"+learner+"</font><br>"
                 + "  The following item(s) have been returned:<br>"
                 + "   <b>Date <font color=\"red\">"+getDate()+"</font></b><br><br>"
                 + "  <table border=\"1\">"
                 + "   <tr><th>Description</th><th>QTY</th><th>Date Issued</th><th>Location</th><th>Store</th></tr>"
                 + "   <tr><td>"+description+"</td><td>"+quantity+"</td><td>"+getDate()+"</td><td>"+location+"</td><td>"+getStore()+"</td></tr>"
                 + "  </table>"
                 + "  <br>"
                 + "  Facilitator: "+getFacilitator()+"<br>"
                 + "  Storeman   : "+getStoreman()+"<br><br>"
                 + "  Regards,<br><br>"
                 + "  <i>Inventory Store Program (InSP) - BMW Technical Training Centre</i>"
                 + " </body>"
                 + "</html>"); 
        
        String message = sb.toString();
        return message;
    }
    
    public String NewUser(String username,String fullname,String tmpPword) { 
       StringBuilder sb = new StringBuilder();
       sb.append( "<html>"
                 + " <body>"
                 + "  Dear <font color=\"red\">"+fullname+"</font><br><br>"
                 + "  You have been allocated a new role as <font color=\"red\"><b>"+getRole().toUpperCase()+"</b></font>"
                 + "  <p>"
                 + "   Username          : <b>"+username+"</b><br>"
                 + "   Temporary Password: <b>"+tmpPword+"</b><br>"
                 + "   Please log on the Inventory Store System to change password!" 
                 + "  </p>"
                 + "  Regards,<br><br>"
                 + "  <i>Inventory Store Program (InSP) - BMW Technical Training Centre</i>"
                 + " </body>"
                 + "</html>"); 
        
       String message = sb.toString();
       return message; 
    }
    
    
     public String NewPassword(String username,String newPword) { 
       StringBuilder sb = new StringBuilder();
       sb.append( "<html>"
                 + " <body>"
                 + "  Hello <font color=\"blue\">"+username+"</font><br><br>"
                 + "  Your password has been successfully changed"
                 + "  <p>"
                 + "   New Password: <b>"+newPword+"</b><br>"
                 + "   Please keep this safe for it is very important and for security purposes." 
                 + "  </p>"
                 + "  Regards,<br><br>"
                 + "  <i>Inventory Store Program (InSP) - BMW Technical Training Centre</i>"
                 + " </body>"
                 + "</html>"); 
        
       String message = sb.toString();
       return message; 
    }
     
    public String MultipleItemsNotificationFacilitator(String facilitator){        
        StringBuilder sb = new StringBuilder();
        String line1,line2,line3,line;
        line2="";
        items = TrolleyGUI.trolleyItems;
        String itemsArray [] = new String[items.size()];
        
        line1 = "<html>"
                 + " <body>"
                 + "  Dear <font color=\"red\">"+facilitator+"</font><br>"
                 + "   Learner - "+getCompanyid()+" has been issued the following item(s):<br>"
                 + "   <b>Return Date <font color=\"red\">"+getReturnDate()+"</font></b><br><br>"
                 + "  <table border=\"1\">"
                 + "   <tr><th>Description</th><th>QTY</th><th>Date Issued</th><th>Location</th><th>Store</th></tr>";
        
        for(int x = 0;x < items.size();x++)
           {
              //line2 = "<tr><td>"+items.get(x).+"</td><td>"+quantity+"</td><td>"+date+"</td><td>"+location+"</td><td>"+store+"</td></tr>";
              line = "<tr><td>"+items.get(x).getDescription()+"</td><td>"+items.get(x).getQuantity()+"</td><td>"+date+"</td><td>"+items.get(x).getLocation()+"</td><td>"+items.get(x).getStore()+"</td></tr>";
              itemsArray[x] = line;
           }
        for(int y=0;y<itemsArray.length;y++){          
            line2 = line2 + itemsArray[y]; }
        
           line3 = "  </table>"
                 + "  <br>"
                 + "  Storeman   : "+getStoreman()+"<br>"
                 + "  <i>Inventory Store Program (InSP) - BMW Technical Training Centre</i>"
                 + " </body>"
                 + "</html>";
        
        sb.append(line1+line2+line3); 
        
        
        String message = sb.toString();
        return message;
    }
    
    public String MultipleItemsNotificationLearner(String learner) {        
        StringBuilder sb = new StringBuilder();
        String line1,line2,line3,line;
        line2="";
        items = TrolleyGUI.trolleyItems;
        String itemsArray [] = new String[items.size()];
        
        line1 = "<html>"
                 + " <body>"
                 + "  Dear <font color=\"red\">"+learner+"</font><br>"
                 + "   You have been issued the following item(s):<br>"
                 + "   <b>Return Date <font color=\"red\">"+getReturnDate()+"</font></b><br><br>"
                 + "  <table border=\"1\">"
                 + "   <tr><th>Description</th><th>QTY</th><th>Date Issued</th><th>Location</th><th>Store</th></tr>";
        
         for(int x = 0;x < items.size();x++)
           {
              //line2 = "<tr><td>"+items.get(x).+"</td><td>"+quantity+"</td><td>"+date+"</td><td>"+location+"</td><td>"+store+"</td></tr>";
              line = "<tr><td>"+items.get(x).getDescription()+"</td><td>"+items.get(x).getQuantity()+"</td><td>"+date+"</td><td>"+items.get(x).getLocation()+"</td><td>"+items.get(x).getStore()+"</td></tr>";
              itemsArray[x] = line;
           }
        for(int y=0;y<itemsArray.length;y++){          
            line2 = line2 + itemsArray[y]; }
               
         line3 = "  </table>"
                 + "  <br>"
                 + "  Facilitator: "+facilitator+"<br>"
                 + "  Storeman   : "+storeman+"<br>"
                 + "  <i>Inventory Store Program (InSP) - BMW Technical Training Centre</i>"
                 + " </body>"
                 + "</html>";
        

          
          
        sb.append(line1+line2+line3); 
        
        String message = sb.toString();
        return message;
    }
    
    public String MultipleReceivedItemsNotifyFacilitator(String facilitator){
        StringBuilder sb = new StringBuilder();
        String line1,line2,line3,line;
        line2="";
        receiveBasketItems = ReceiveGUI.basketItems;
        String itemsArray [] = new String[receiveBasketItems.size()];
        
        line1 = "<html>"
                 + " <body>"
                 + "  Dear <font color=\"red\">"+facilitator+"</font><br>"
                 + "   Learner - "+getCompanyid()+" has returned the following item(s):<br>"
                 + "   <b>Date <font color=\"red\">"+getDate()+"</font></b><br><br>"
                 + "  <table border=\"1\">"
                 + "   <tr><th>Description</th><th>QTY</th><th>Date Issued</th><th>Location</th><th>Receive QTY</th></tr>";
        
        for(int x = 0;x < receiveBasketItems.size();x++)
           {
              //line2 = "<tr><td>"+items.get(x).+"</td><td>"+quantity+"</td><td>"+date+"</td><td>"+location+"</td><td>"+store+"</td></tr>";
              line = "<tr><td>"+receiveBasketItems.get(x).getDescription()+"</td><td>"+receiveBasketItems.get(x).getQuantity()+"</td><td>"+date+"</td><td>"+receiveBasketItems.get(x).getLocation()+"</td><td>"+receiveBasketItems.get(x).getReceiveQuantity()+"</td></tr>";
              itemsArray[x] = line;
           }
        for(int y=0;y<itemsArray.length;y++){          
            line2 = line2 + itemsArray[y]; }
        
           line3 = "  </table>"
                 + "  <br>"
                 + "  Storeman   : "+getStoreman()+"<br>"
                 + "  <i>Inventory Store Program (InSP) - BMW Technical Training Centre</i>"
                 + " </body>"
                 + "</html>";
        
        sb.append(line1+line2+line3); 
        
        
        String message = sb.toString();
        return message;
    }
    
    public String MultipleReceivedItemsNotifyLearner(String learner){
        StringBuilder sb = new StringBuilder();
        String line1,line2,line3,line;
        line2="";
        receiveBasketItems = ReceiveGUI.basketItems;
        String itemsArray [] = new String[receiveBasketItems.size()];
        
        line1 = "<html>"
                 + " <body>"
                 + "  Dear <font color=\"red\">"+learner+"</font><br>"
                 + "  The following item(s) has been received by "+getStoreman()+":<br>"
                 + "   <b>Receive Date <font color=\"red\">"+getDate()+"</font></b><br><br>"
                 + "  <table border=\"1\">"
                 + "   <tr><th>Description</th><th>QTY</th><th>Date Issued</th><th>Location</th><th>Receive QTY</th></tr>";
        
        for(int x = 0;x < receiveBasketItems.size();x++)
           {
              //line2 = "<tr><td>"+items.get(x).+"</td><td>"+quantity+"</td><td>"+date+"</td><td>"+location+"</td><td>"+store+"</td></tr>";
              line = "<tr><td>"+receiveBasketItems.get(x).getDescription()+"</td><td>"+receiveBasketItems.get(x).getQuantity()+"</td><td>"+date+"</td><td>"+receiveBasketItems.get(x).getLocation()+"</td><td>"+receiveBasketItems.get(x).getReceiveQuantity()+"</td></tr>";
              itemsArray[x] = line;
           }
        for(int y=0;y<itemsArray.length;y++){          
            line2 = line2 + itemsArray[y]; }
        
           line3 = "  </table>"
                 + "  <br>"
                 + "  <i>Inventory Store Program (InSP) - BMW Technical Training Centre</i>"
                 + " </body>"
                 + "</html>";
        
        sb.append(line1+line2+line3); 
        
        
        String message = sb.toString();
        return message;
    }
}
