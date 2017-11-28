/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory_system;

import java.awt.Color;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Tebogo kekana - kinotech (Pty) Ltd.
 */
public class Suppliers extends javax.swing.JInternalFrame {
    private static int id;
    private static int role;
    private static int EDIT_MODE = 0; //set to 1 if you are editing an existing supplier
    Date logtime;
    private int physicalAddress_id;
    private int postalAddress_id;
    private boolean physicalAddressExist;
    private boolean postalAddressExist;
    private String newAddressTypeToInsert;
    //Extra declaration
    private static ArrayList<Category> listCategories;
    private static String copyfrom;
    //Include other classes
    DialogBoxClass dialog;
    CustomDialog cd;
    static DefaultComboBoxModel categories_model;
    Date currentdate = new Date();
    DB db;
    private Vector<Vector<String>> data; 
        
    
    public Suppliers() {
        id      = getUserId(); 
        role    = getRole();
        logtime = new Date();
        copyfrom = "<html><u>Copy from Physical</u></html>";
        physicalAddressExist = false;
        postalAddressExist = false;
        newAddressTypeToInsert = "None";
        
        initComponents();
        getCategoryList();
        
        this.getContentPane().setBackground(Color.white); 
    }
    
    
    protected void setSupplier(int suppid){
       try{
             db   = new DB(RunProgram.CONNECTION_MODE);
             data = db.getSupplier(suppid); 
             db.close();
             setData();
         }catch(Exception ex){
             ex.printStackTrace();
         }
        
    }
    
    private void setData(){
        input_supplier_name.setText(data.get(0).elementAt(8));
        input_vat.setText(data.get(0).elementAt(9));
        int data_size = data.size();
        
        //set category
        String cat = "";
        int catid = Integer.parseInt(data.get(0).elementAt(6));
        int cat_count = drop_categories.getItemCount();
        
        for(int x = 0; x < cat_count;x++){ 
            cat = getCategoryName(catid);
            String catvalue = drop_categories.getItemAt(x);
            if(catvalue.equals(cat)){
                drop_categories.setSelectedIndex(x);
            }
        }
            
        //set checkboxes - invoice and active customer status
        int sup_state = Integer.parseInt(data.get(0).elementAt(10));
        int invoice = Integer.parseInt(data.get(0).elementAt(2));
        
        if(sup_state == 1){ supplier_status.setSelected(true);
        }else{ supplier_status.setSelected(false); }
        
        if(invoice == 1){ mail_invoice.setSelected(true);
        }else{ mail_invoice.setSelected(false); }
        
        /**
         * Set addresses (Physical & Delivery address)
         * Firstly, check if the supplier has more than one address and set accordingly
         */
        for(int x = 0; x < data_size;x++){
            int addresstype = Integer.parseInt(data.get(x).elementAt(32));
            if(addresstype == 2){  
              physicalAddress_id = Integer.parseInt(data.get(x).elementAt(25));
              address1.setText(data.get(x).elementAt(26));
              address2.setText(data.get(x).elementAt(27));
              city.setText(data.get(x).elementAt(28));
              province.setText(data.get(x).elementAt(30));
              country.setText(data.get(x).elementAt(29));
              zipcode.setText(data.get(x).elementAt(31));
              physicalAddressExist = true;
            }else if(addresstype == 1){
              postalAddress_id = Integer.parseInt(data.get(x).elementAt(25));
              address3.setText(data.get(x).elementAt(26));
              address4.setText(data.get(x).elementAt(27));
              city1.setText(data.get(x).elementAt(28));
              province1.setText(data.get(x).elementAt(30));
              country1.setText(data.get(x).elementAt(29));
              zipcode1.setText(data.get(x).elementAt(31));
              postalAddressExist = true;
            }
        }
        
         if(physicalAddressExist == true && postalAddressExist == false){
            newAddressTypeToInsert = "Postal";
        }else if(physicalAddressExist == false && postalAddressExist == true){
            newAddressTypeToInsert = "Physical";
        }else if(physicalAddressExist == false && postalAddressExist == false){
            newAddressTypeToInsert =  "Both";
        }else if(physicalAddressExist == true && postalAddressExist == true){
            newAddressTypeToInsert = "None";
        }
        
        //set contact
        contactname.setText(data.get(0).elementAt(15));
        contactmobile.setText(data.get(0).elementAt(17));
        contacttelephone.setText(data.get(0).elementAt(18));
        contactmail.setText(data.get(0).elementAt(16));
        contactfax.setText(data.get(0).elementAt(19));
        contactwebsite.setText(data.get(0).elementAt(20));
        int constate = Integer.parseInt(data.get(0).elementAt(21));
        if(constate == 1){ contact_status.setSelected(true);         
        }else{ contact_status.setSelected(false);}
        
    }
    
    public void saveSupplier(){
        if((input_supplier_name.getText().equals("") || contactname.getText().equals("") || contactmail.getText().equals(""))) 
        {
          JOptionPane.showMessageDialog(null, "Make sure that you have entered all required fields: Supplier Name, Contact name and Contact e-mail address", "ERROR - Inputs", 2); 
          
        }else if(!isEmailAddressValid(contactmail.getText())){
             JOptionPane.showMessageDialog(null, "Please enter a valid e-mail address", "ERROR - Inputs", 2); 
        }
        else
        {   
                
            String suppname = input_supplier_name.getText();
            String category = drop_categories.getSelectedItem().toString();
            String vat     = input_vat.getText();
            boolean status  = supplier_status.isSelected();
            boolean invoices = mail_invoice.isSelected();
            //Get contact
            String contname = contactname.getText();
            String mobile = contactmobile.getText();
            String tel = contacttelephone.getText();
            String email = contactmail.getText();
            String fax   = contactfax.getText();
            String web   = contactwebsite.getText();
            boolean constatus = contact_status.isSelected();
            int image = 0;
            int supid = 0;
            int conid = 0;
            //int addressid = 0;
            
            //Check if adrress fields are empty and get values
            String addr1  = "";
            String addr2  = "";
            String cty   =  "";
            String prov  = "";
            String countr = "";
            String zip   = "";
            String addr3  = "";
            String addr4  = "";
            String cty1   =  "";
            String prov1  = "";
            String countr1 = "";
            String zip1   = "";
            int insert_count = 0;
            int addressToinsert = 0;
            
            int sup_state = 0;
            int einvoice = 0;
            int contact_status = 0;
            int physical_addressid = 0;
            int postal_addressid = 0;
            
            if(!isAddressTypeEmpty(2)){
                //Get Physical address
                addr1  = address1.getText();
                addr2  = address2.getText();
                cty   =  city.getText();
                prov  = province.getText();
                countr = country.getText();
                zip   = zipcode.getText();
                insert_count++;
                addressToinsert= 2;
            }
            if(!isAddressTypeEmpty(3)){
                //Get Delivery address
                addr3  = address3.getText();
                addr4  = address4.getText();
                cty1   =  city1.getText();
                prov1  = province1.getText();
                countr1 = country1.getText();
                zip1   = zipcode1.getText();
                insert_count++;
                addressToinsert = 3;
            }
            
            String datecreated, enddate, datemodified;
	    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    datecreated = formatter2.format(logtime);
            datemodified = datecreated;
            enddate = "9999-12-31 00:00:00";
            
            if(status){ sup_state = 1;}
            if(invoices){einvoice = 1;}
            if(constatus){contact_status = 1;}
            
            int catid = 0;
            catid = getCategoryID(category); 
            if(catid <= 0){
               refreshCategoryList();
               catid = getCategoryID(category); 
            }
            
            try{
                db = new DB(RunProgram.CONNECTION_MODE);
                db.setCommit(false); 
                
                 if(EDIT_MODE == 0){
                    //insert supplier
                    String insert_supplier = "INSERT INTO supplier (`suppname`,`vatnumber`,`supp_state`,`datecreated`,`datemodified`,`enddate`)"+
                                             "VALUES ('" + suppname + "','" + vat + "',"+sup_state+",'"+datecreated+"','"+datemodified+"','"+enddate+"')";
                    db.executeSQL(insert_supplier);
                    supid = db.get_field_value("supplier","supp_id","suppname = '"+suppname+"'");    
                    //insert contact
                    String insert_contact = "INSERT INTO contact (`name`,`email`,`mobile`,`telephone`,`fax`,`website`,`con_state`,`datecreated`,`datemodified`,`enddate`)"+
                                   "VALUES ('"+contname+"','"+email+"','"+mobile+"','"+tel+"','"+fax+"','"+web+"',"+contact_status+",'"+datecreated+"','"+datemodified+"','"+enddate+"')";
                    db.executeSQL(insert_contact);
                    conid = db.get_field_value("contact","conid"," name = '"+contname+"'");    
                    //insert category info
                    //String insert_category_info = "INSERT INTO category_info (`catid`,`fieldid`,`data`)"+
                    //                              "VALUES ('"+catid+"',2,'"+supid+"')";
                    //db.executeSQL(insert_category_info);
                    //insert address
                    String insert_physical_address = "INSERT INTO address (`address1`,`address2`,`city`,`country`,`province`,`zipcode`,`addresstype`,`createdby`)"+
                                                        "VALUES ('"+addr1+"','"+addr2+"','"+cty+"','"+countr+"','"+prov+"','"+zip+"',2,'"+getUserId()+"')";
                    
                    String insert_postal_address = "INSERT INTO address (`address1`,`address2`,`city`,`country`,`province`,`zipcode`,`addresstype`,`createdby`)"+
                                                        "VALUES ('"+addr3+"','"+addr4+"','"+cty1+"','"+countr1+"','"+prov1+"','"+zip1+"',1,'"+getUserId()+"')";               
                     
                    if(insert_count == 2){
                      db.executeSQL(insert_physical_address);
                      physical_addressid = db.get_field_value("address","addressid","address1 = '"+addr1+"'");
                      db.executeSQL(insert_postal_address);  
                      postal_addressid = db.get_field_value("address","addressid","address1 = '"+addr3+"'");
                      
                      //insert supplier info
                      String insert_supplier_info = "INSERT INTO supplier_info (`supp_id`,`einvoice`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                                   " VALUES("+supid+ ","+einvoice+","+image+","+physical_addressid+","+conid+","+catid+"),"+
                                                                   "       ("+supid+ ","+einvoice+","+image+","+postal_addressid+","+conid+","+catid+")";
                      db.executeSQL(insert_supplier_info);
                      
                    }else if(insert_count == 1){
                           if(addressToinsert == 2){
                              db.executeSQL(insert_physical_address); 
                              physical_addressid = db.get_field_value("address","addressid","address1 = '"+addr1+"'");
                              String insert_supplier_info = "INSERT INTO supplier_info (`supp_id`,`einvoice`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                         "VALUES ("+supid+","+einvoice+","+image+","+physical_addressid+","+conid+","+catid+")";
                              db.executeSQL(insert_supplier_info);
                           }else if(addressToinsert == 1){
                              db.executeSQL(insert_postal_address);  
                              postal_addressid = db.get_field_value("address","addressid","address1 = '"+addr3+"'");
                              String insert_supplier_info = "INSERT INTO supplier_info (`supp_id`,`einvoice`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                         "VALUES ("+supid+","+einvoice+","+image+","+postal_addressid+","+conid+","+catid+")";
                              db.executeSQL(insert_supplier_info);
                           }     
                    }

                }else{
                    /***************************************************************
                    * You are in EDIT MODE, use vector data to get element at the  *
                    * specified point to get various details about the supplier    *
                    ***************************************************************/
                    //update supplier
                    supid = Integer.parseInt(data.get(0).elementAt(1));   
                    String update_supplier = "UPDATE supplier SET `suppname` = '" + suppname + "',`vatnumber` = '" + vat + "',`supp_state` = "+sup_state+",`datemodified` = '"+datemodified+"',`enddate` = '"+enddate+"'"+
                                             " WHERE `supp_id` = "+supid;
                    db.executeSQL(update_supplier);    
                    //update contact
                    conid = Integer.parseInt(data.get(0).elementAt(14));    
                    String update_contact = "UPDATE contact SET `name` = '"+contname+"',`email` = '"+email+"',`mobile` = '"+mobile+"',`telephone` = '"+tel+"',`fax` = '"+fax+"',`website` = '"+web+"',`con_state` = "+contact_status+" ,`datemodified` = '"+datemodified+"',`enddate` = '"+enddate+"'"+
                                            " WHERE `conid` = "+conid;
                    db.executeSQL(update_contact);
                    
                    //insert category info
                    //String update_category_info = "UPDATE category_info SET `catid` = "+catid+", `fieldid` = 2 WHERE `data` = "+supid;
                    //db.executeSQL(update_category_info);

                    //update address
                    String update_physical_address = "UPDATE address SET `address1` = '"+addr1+"',`address2` = '"+addr2+"',`city` = '"+cty+"',`country` = '"+countr+"',`province` = '"+prov+"',`zipcode` = '"+zip+"',`addresstype` = 2,`createdby` = '"+getUserId()+"'"+
                                             " WHERE `addressid` = "+physicalAddress_id;
                    String update_postal_address = "UPDATE address SET `address1` = '"+addr3+"',`address2` = '"+addr4+"',`city` = '"+cty1+"',`country` = '"+countr1+"',`province` = '"+prov1+"',`zipcode` = '"+zip1+"',`addresstype` = 1,`createdby` = '"+getUserId()+"'"+
                                             " WHERE `addressid` = "+postalAddress_id;
                    
                    String insert_physical_address = "INSERT INTO address (`address1`,`address2`,`city`,`country`,`province`,`zipcode`,`addresstype`,`createdby`)"+
                                                        "VALUES ("+addr1+"','"+addr2+"','"+cty+"','"+countr+"','"+prov+"','"+zip+"',2,'"+getUserId()+"')";
                    String insert_postal_address = "INSERT INTO address (`address1`,`address2`,`city`,`country`,`province`,`zipcode`,`addresstype`,`createdby`)"+
                                                        "VALUES ('"+addr3+"','"+addr4+"','"+cty1+"','"+countr1+"','"+prov1+"','"+zip1+"',1,'"+getUserId()+"')";               

                    if(insert_count == 2){
                      //check if there is a new address type to insert
                      if(newAddressTypeToInsert == "Physical"){
                          db.executeSQL(update_postal_address); 
                          //update supplier info
                          String update_supplier_info = "UPDATE supplier_info SET `einvoice` = "+einvoice+",`imageid` = "+image+", `conid` = "+conid+", `catid` = "+catid+""+
                                                         "WHERE `supp_id` = "+supid+""+
                                                         "  AND `addressid` = "+postalAddress_id;
                          db.executeSQL(update_supplier_info);
                          
                          db.executeSQL(insert_physical_address);
                          physical_addressid = db.get_field_value("address","addressid","address1 = '"+addr1+"'");
                          //insert supplier info
                          String insert_supplier_info = "INSERT INTO supplier_info (`supp_id`,`einvoice`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                         "VALUES ("+supid+","+einvoice+","+image+","+physical_addressid+","+conid+","+catid+")";
                          db.executeSQL(insert_supplier_info);
                        }else if(newAddressTypeToInsert == "Postal"){
                          db.executeSQL(update_physical_address); 
                          //update supplier info
                          String update_supplier_info = "UPDATE supplier_info SET `einvoice` = '" + einvoice + "',`imageid` = "+image+", `conid` = "+conid+", `catid` = "+catid+""+
                                                        " WHERE `supp_id` = "+supid+""+
                                                        "   AND `addressid` = "+physicalAddress_id;
                          db.executeSQL(update_supplier_info);
                          
                          db.executeSQL(insert_postal_address);
                          postal_addressid = db.get_field_value("address","addressid","address1 = '"+addr3+"'");
                          //insert customer info
                          String insert_supplier_info = "INSERT INTO supplier_info (`supp_id`,`einvoice`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                         "VALUES ("+supid+","+einvoice+","+image+","+postal_addressid+","+conid+","+catid+")";
                          db.executeSQL(insert_supplier_info);
                        }else if(newAddressTypeToInsert == "Both"){
                           db.executeSQL(insert_physical_address);
                           physical_addressid = db.get_field_value("address","addressid","address1 = '"+addr1+"'");
                           db.executeSQL(insert_postal_address);  
                           postal_addressid = db.get_field_value("address","addressid","address1 = '"+addr3+"'");
                           
                           String insert_supplier_info = "INSERT INTO supplier_info (`supp_id`,`einvoice`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                         " VALUES ("+supid+","+einvoice+","+image+","+physical_addressid+","+conid+","+catid+"),"+
                                                                 "("+supid+","+einvoice+","+image+","+postal_addressid+","+conid+","+catid+")";
                           db.executeSQL(insert_supplier_info);
                        }else if(newAddressTypeToInsert == "None"){
                           db.executeSQL(update_physical_address);
                           db.executeSQL(update_postal_address);          
                           
                          //update supplier info
                           String update_supplier_info = "UPDATE supplier_info SET `einvoice` = "+einvoice+",`imageid` = "+image+", `conid` = "+conid+", `catid` = "+catid+""+
                                                         " WHERE `supp_id` = "+supid+""+
                                                         "   AND `addressid` = "+physicalAddress_id;
                           db.executeSQL(update_supplier_info);

                           String update_supplier_info2 = "UPDATE supplier_info SET `einvoice` = "+einvoice+",`imageid` = "+image+", `conid` = "+conid+", `catid` = "+catid+""+
                                                          " WHERE `supp_id` = "+supid+""+
                                                          "   AND `addressid` = "+postalAddress_id;
                           db.executeSQL(update_supplier_info2);
                        }  
                    }else if(insert_count == 1){
                           if(addressToinsert == 2){
                              db.executeSQL(update_physical_address); 
                              //update customer info
                              String update_supplier_info = "UPDATE supplier_info SET `einvoice` = "+einvoice+",`imageid` = "+image+", `conid` = "+conid+", `catid` = "+catid+""+
                                                            " WHERE `supp_id` = "+supid+""+
                                                            "   AND `addressid` = "+physicalAddress_id;
                              db.executeSQL(update_supplier_info);
                           }else if(addressToinsert == 1){
                              db.executeSQL(update_postal_address);  
                              //update customer info
                              String update_supplier_info = "UPDATE supplier_info SET `einvoice` = "+einvoice+",`imageid` = "+image+", `conid` = "+conid+", `catid` = "+catid+""+
                                                            " WHERE `supp_id` = "+supid+""+
                                                            "   AND `addressid` = "+postalAddress_id;
                              db.executeSQL(update_supplier_info);
                           }
                    }
                }
                
                db.setCommit(true);
                db.close();
                if(EDIT_MODE == 0){
                   JOptionPane.showMessageDialog(null, "<html>"+suppname+" added successfully</html>", "Confirmation", 1);
                }else{
                   JOptionPane.showMessageDialog(null, "<html>"+suppname+" successfully updated</html>", "Confirmation", 1);
                }
               }
            catch (Exception e1)
               {  
                try {
                    System.out.println("Rolling back transactions...");
                    db.setRollback();
                    db.close();
                } catch (SQLException ex) {
                    System.out.println("Failed to rollback transactions...");
                    Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
                }
                  JOptionPane.showMessageDialog(null, e1, "ERROR - Failed to add Supplier", 2);
                  e1.printStackTrace(); //Remove after testing
               }
        } 
    }
    
    private void getCategoryList(){
        
         try{ 
             DB db = new DB(RunProgram.CONNECTION_MODE);
             listCategories = db.getCategories(this.getClass().getSimpleName());   
           }
        catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN CATEGORIES LIST",JOptionPane.ERROR_MESSAGE);}
        
        String [] categories; 
        categories = new String [listCategories.size() + 2];
        
        categories[0] = "(None)";
        categories[1] = "(Add new category)";
          
        for(int i=0;i < listCategories.size();i++){
              categories[2+i] = listCategories.get(i).getCatname();
        }
        
       categories_model = new DefaultComboBoxModel(categories); 
       drop_categories.setModel(categories_model); 
    }
    
    private int getCategoryID(String categoryname){
        int id = 0;
        for(int x = 0;x < listCategories.size();++x){
            if(listCategories.get(x).getCatname().equals(categoryname) ){
                id = listCategories.get(x).getCatid();
            }
        }
        
       return id;
    }
        
    private String getCategoryName(int categoryid){
       String category = "";
       for(int x = 0;x < listCategories.size();++x){
            if(listCategories.get(x).getCatid().equals(categoryid) ){
               category = listCategories.get(x).getCatname();
            }
       }          
       return category;
    }
    
    private boolean isEmailAddressValid(String email){
        boolean valid = true;
        try{
            InternetAddress ids = new InternetAddress(email);
            ids.validate();
        }catch(AddressException e){
            valid = false;
        }
        
        return valid;
    }
    
    private boolean isAddressTypeEmpty(int addresstype){
        boolean type = false;
        
        //Get Physical address = 2
        if(addresstype == 2){
           if(address1.getText().isEmpty() && address2.getText().isEmpty() && city.getText().isEmpty() && province.getText().isEmpty() && country.getText().isEmpty() && zipcode.getText().isEmpty())
             {
               type = true;
             }
         //Get Delivery address = 3
         }else if(addresstype == 3){ 
           if(address3.getText().isEmpty() && address4.getText().isEmpty() && city1.getText().isEmpty() && province1.getText().isEmpty() && country1.getText().isEmpty() && zipcode1.getText().isEmpty())
             {
               type = true;
             }   
         }
        
        return type;
    }
    
    public static void setRole(int roleIn){
        role = roleIn;
    }
    
    public static void setUserId(int idIn){
        id = idIn;
    }
        
    private int getRole(){
        return this.role;
    }
    
    private int getUserId(){
        return this.id;
    }
    
    public static void setMode(int mode){
        EDIT_MODE = mode; 
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        supplier_title = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        mail_invoice = new javax.swing.JCheckBox();
        drop_categories = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        input_supplier_name = new javax.swing.JTextField();
        input_vat = new javax.swing.JTextField();
        tabs_panel1 = new javax.swing.JTabbedPane();
        address_tab1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        address1 = new javax.swing.JTextField();
        address2 = new javax.swing.JTextField();
        city = new javax.swing.JTextField();
        province = new javax.swing.JTextField();
        country = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        address3 = new javax.swing.JTextField();
        address4 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        country1 = new javax.swing.JTextField();
        province1 = new javax.swing.JTextField();
        city1 = new javax.swing.JTextField();
        copy1 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        zipcode = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        zipcode1 = new javax.swing.JTextField();
        contact_tab = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        contactname = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        contactmobile = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        contacttelephone = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        contactmail = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        contactfax = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        contactwebsite = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        contact_status = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        supplier_status = new javax.swing.JCheckBox();
        jLabel37 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(153, 153, 153)), javax.swing.BorderFactory.createEtchedBorder()));
        setPreferredSize(MainWindow.home_desktop.getPreferredSize());

        jLabel1.setText("Supplier Name");

        drop_categories.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                drop_categoriesItemStateChanged(evt);
            }
        });

        jLabel17.setText("Active");

        address_tab1.setBackground(java.awt.Color.white);

        jLabel15.setText("Physical Address");

        jLabel16.setText("City");

        jLabel20.setText("Province/State");

        jLabel21.setText("Country");

        jLabel22.setText("Postal Address");

        jLabel23.setText("City");

        jLabel24.setText("Province/State");

        jLabel25.setText("Country");

        copy1.setForeground(java.awt.Color.blue);
        copy1.setText(copyfrom);
        copy1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                copy1MouseClicked(evt);
            }
        });

        jLabel26.setText("Zip Code");

        jLabel27.setText("Zip Code");

        javax.swing.GroupLayout address_tab1Layout = new javax.swing.GroupLayout(address_tab1);
        address_tab1.setLayout(address_tab1Layout);
        address_tab1Layout.setHorizontalGroup(
            address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(address_tab1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(address1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(address2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(address_tab1Layout.createSequentialGroup()
                        .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(province, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(country, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(city, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(address_tab1Layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(zipcode, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(address3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(address4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(address_tab1Layout.createSequentialGroup()
                        .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(province1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(country1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(city1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(address_tab1Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(copy1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(address_tab1Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(zipcode1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(144, 144, 144))
        );
        address_tab1Layout.setVerticalGroup(
            address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(address_tab1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(copy1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(address_tab1Layout.createSequentialGroup()
                        .addComponent(address1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(address2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(city)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(province, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(country, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(address_tab1Layout.createSequentialGroup()
                        .addComponent(address3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(address4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(city1)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(province1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(country1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(zipcode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(address_tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(zipcode1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        tabs_panel1.addTab("Address", address_tab1);

        contact_tab.setBackground(java.awt.Color.white);

        jLabel28.setText("Contact Details");

        jLabel29.setText("Contact Name");

        jLabel30.setText("Mobile");

        jLabel31.setText("Telephone");

        jLabel32.setText("E-mail");

        jLabel33.setText("Fax");

        jLabel34.setText("Website");

        jLabel35.setText("Active");

        javax.swing.GroupLayout contact_tabLayout = new javax.swing.GroupLayout(contact_tab);
        contact_tab.setLayout(contact_tabLayout);
        contact_tabLayout.setHorizontalGroup(
            contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contact_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(contact_tabLayout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contactname, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contact_tabLayout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contactmobile, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contact_tabLayout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contacttelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contact_tabLayout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contactmail, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contact_tabLayout.createSequentialGroup()
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contactfax, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contact_tabLayout.createSequentialGroup()
                        .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(contact_status)
                            .addComponent(contactwebsite, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(567, Short.MAX_VALUE))
        );
        contact_tabLayout.setVerticalGroup(
            contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contact_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(contactname, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(contactmobile)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(contacttelephone)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(contactmail)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(contactfax)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(contactwebsite)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contact_status))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs_panel1.addTab("Contact", contact_tab);

        jLabel3.setText("Category");

        jLabel6.setText("E-mail invoices");

        jLabel37.setText("VAT Number");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(supplier_title, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabs_panel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(drop_categories, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(input_supplier_name, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(85, 85, 85)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(input_vat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(supplier_status)
                            .addComponent(mail_invoice))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(supplier_title, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(input_supplier_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(drop_categories, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(supplier_status, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(11, 11, 11)
                        .addComponent(mail_invoice))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(input_vat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addComponent(tabs_panel1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void drop_categoriesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_drop_categoriesItemStateChanged
        if(evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            Object item = evt.getItem();
            if(item.equals("(Add new category)")){
                dialog = new DialogBoxClass(new JFrame(), true);
                dialog.setTitle("Add New "+this.getClass().getSimpleName()+" Category");
                dialog.setResizable(false);
                dialog.setModal(true);              //blocks other open windows for input
                //dialog.setLocationByPlatform(true);
                dialog.setActiveCallingClass(this.getClass().getSimpleName());  //Sets the class name so that the dialog instance can know which class component/window can do the refresh/update on the UI
                dialog.setVisible(true);
            }else{
                //Do nothing

            }
        }
    }//GEN-LAST:event_drop_categoriesItemStateChanged
    
    public static void updateCategoryList(String cat){
        categories_model.addElement(cat); 
    }
    
    /**
     * Updates the category list with new values from the database.
     */
    public void refreshCategoryList(){
         getCategoryList();
    }
    
    private void copy1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_copy1MouseClicked
        //Get Postal address
        String addr1  = address1.getText();
        String addr2  = address2.getText();
        String cty   =  city.getText();
        String prov  = province.getText();
        String countr = country.getText();
        String zip   = zipcode.getText();

        //Set Delivery address
        address3.setText(addr1);
        address4.setText(addr2);
        city1.setText(cty);
        province1.setText(prov);
        country1.setText(countr);
        zipcode1.setText(zip);
    }//GEN-LAST:event_copy1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address1;
    private javax.swing.JTextField address2;
    private javax.swing.JTextField address3;
    private javax.swing.JTextField address4;
    private javax.swing.JPanel address_tab1;
    private javax.swing.JTextField city;
    private javax.swing.JTextField city1;
    private javax.swing.JCheckBox contact_status;
    private javax.swing.JPanel contact_tab;
    private javax.swing.JTextField contactfax;
    private javax.swing.JTextField contactmail;
    private javax.swing.JTextField contactmobile;
    private javax.swing.JTextField contactname;
    private javax.swing.JTextField contacttelephone;
    private javax.swing.JTextField contactwebsite;
    private javax.swing.JLabel copy1;
    private javax.swing.JTextField country;
    private javax.swing.JTextField country1;
    private javax.swing.JComboBox<String> drop_categories;
    private javax.swing.JTextField input_supplier_name;
    private javax.swing.JTextField input_vat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox mail_invoice;
    private javax.swing.JTextField province;
    private javax.swing.JTextField province1;
    private javax.swing.JCheckBox supplier_status;
    public static javax.swing.JLabel supplier_title;
    private javax.swing.JTabbedPane tabs_panel1;
    private javax.swing.JTextField zipcode;
    private javax.swing.JTextField zipcode1;
    // End of variables declaration//GEN-END:variables
}
