
package inventory_system;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author kinotech Pty Ltd
 */
public class Items extends javax.swing.JInternalFrame {

    private static int id;
    private static int role;
    Date logtime;
    private static String barcode;
    private static int newitemcode;
    public static String name;
    public static String surname;
    /*private String description;
    private int available;
    private int quantity;
    private String cat;
    private String location;
    private String pur_price;
    private String sal_price;
    private String unit_measure;*/
    public static ArrayList<Location> locationList;
    public static ArrayList<Category> categoryList;
    public static ArrayList<UnitMeasurement> measurementList;
    public static ArrayList<Supplier> supplierList;
    public static ArrayList<Manufacture> manufactureList;
    static DefaultComboBoxModel categories_model;
    static DefaultComboBoxModel location_model;
    static DefaultComboBoxModel measurement_model;
    static DefaultComboBoxModel supplier_model;
    static DefaultComboBoxModel manufacture_model;
    DB db;
    ResultSetMetaData md;
    private DefaultTableModel model_one;
    public static int []  tableRowSelected;
    public static int []  rowSelected;
    private Vector<Vector<String>> data; 
    private Vector<Vector<String>> supplierlist_data;
    private Vector<String> header;
    private Object ID;
    public static int []  tableRowSelected2;
    private Vector<Vector<String>> data2; 
    private Vector<String> header2;
    private Object ID2;
    private static String file_name;
    private static String fullPathName;
    private static String file_directory;
    private static String image_name;
    private static String fullPathImage;
    private static String image_directory;
    private static boolean IS_IMAGE_SET = false; 
    private static int EDIT_MODE = 0; //set to 1 if you are editing an existing customer
    DialogBoxClass dialog;
    
    
    public Items() {   
        id      = getUserId();
        role    = getRole();
        logtime = new Date();
        barcode = getBarcode();
        newitemcode = getNewItemCode();
        
        initComponents();
       
        getCategoryList();
        getLocationList();
        getUnitMeasurementList();
        getSupplierList();
        getManufactureList();
        buttonGroup.setSelected(physical_stock.getModel(), true);
        removeButton.setEnabled(false); 
        //Set item by barcode or location
        /*if(!barcode.isEmpty()){setItemByBarcode(barcode);
        }else{}*/
        
        item_image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/noimage.jpg")));        
        image_rules.setText("<html>Please click Upload Image to add your file.<br>Your file must not exceed 1MB in size.<br>We support the following file formats: .jpg, .png and .gif.<html>");
        
        this.getContentPane().setBackground(Color.white);
        
    }
    
    private int getCategoryID(String categoryname){
        int id = 0;
        for(int x = 0;x < categoryList.size();++x){
            if(categoryList.get(x).getCatname().equals(categoryname) ){
                id = categoryList.get(x).getCatid();
            }
        }
        
       return id;
    }
    
    private void getCategoryList(){
        
         try{ 
             DB db = new DB(RunProgram.CONNECTION_MODE);
             categoryList = db.getCategories(this.getClass().getSimpleName());    
           }
        catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN CATEGORIES LIST",JOptionPane.ERROR_MESSAGE);}
        
        String [] categories; 
        categories = new String [categoryList.size() + 2];
        
        categories[0] = "(None)";
        categories[1] = "(Add new category)";
          
        for(int i=0;i < categoryList.size();i++){
              categories[2+i] = categoryList.get(i).getCatname();
        }
        
       categories_model = new DefaultComboBoxModel(categories); 
       drop_category.setModel(categories_model); 
    }
    
    public void refreshCategoryList(){
       getCategoryList();
    }
    
    private int getLocationID(String locationname){
        int id = 0;
        for(int x = 0;x < locationList.size();++x){
            if(locationList.get(x).getLocname().equals(locationname) ){
                id = locationList.get(x).getLocationid();
            }
        }
        
       return id;
    }
    
    private void getLocationList(){
        
         try{ 
             DB db = new DB(RunProgram.CONNECTION_MODE);
             locationList = db.getLocations();   
           }
        catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN LOCATION LIST",JOptionPane.ERROR_MESSAGE);}
        
        String [] loc; 
        loc = new String [locationList.size() + 2];
        
        loc[0] = "(None)";
        loc[1] = "(Add new location)";
          
        for(int i=0;i < locationList.size();i++){
              loc[2+i] = locationList.get(i).getLocname();
        }
        
       location_model = new DefaultComboBoxModel(loc); 
       drop_locations.setModel(location_model); 
    }
    
    private int getUnitMeasurementID(String unit){
        int id = 0;
        for(int x = 0;x < measurementList.size();++x){
            if(measurementList.get(x).getUmDescription().equals(unit) ){
                id = measurementList.get(x).getUmid();
            }
        }
        
       return id;
    }
    
    private void getUnitMeasurementList(){
        
         try{ 
             DB db = new DB(RunProgram.CONNECTION_MODE);
              measurementList = db.getUnitMeasurements(); 
           }
        catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN UNIT MEASUREMENT LIST",JOptionPane.ERROR_MESSAGE);}
        
        String [] um; 
        um = new String [measurementList.size() + 2];
        
        um[0] = "(None)";
        um[1] = "(Add new measurement)";
          
        for(int i=0;i < measurementList.size();i++){
              um[2+i] = measurementList.get(i).getUmDescription();
        }
        
       measurement_model = new DefaultComboBoxModel(um); 
       drop_measurement.setModel(measurement_model); 
    }
    
    private int getSupplierID(String suppname){
        int id = 0;
        for(int x = 0;x < supplierList.size();++x){
            if(supplierList.get(x).getSuppname().equals(suppname) ){
                id = supplierList.get(x).getSuppId();
            }
        }
        
       return id;
    }
    
    private void getSupplierList(){
         final int addressid = 0;  //set adrressid to 0, dummy value because its not used
         int id = 0;
         String name = "";
         int state = 0;
         Date d;
         String contact = "";
         
         try{ 
             DB db = new DB(RunProgram.CONNECTION_MODE);
             supplierlist_data = db.getSuppliers(); 
             supplierList = new ArrayList<>();
             for(int x = 0;x < supplierlist_data.size();x++){
                 id = Integer.parseInt(supplierlist_data.get(x).elementAt(0));
                 name = supplierlist_data.get(x).elementAt(1);
                 contact = supplierlist_data.get(x).elementAt(4);
                 state =  Integer.parseInt(supplierlist_data.get(x).elementAt(8));
                 d = new Date();
                
                 supplierList.add(new Supplier(id, name,addressid,contact,state,d)); 
                 /*supplierList.add(x, new Supplier(Integer.parseInt(supplierlist_data.get(x).elementAt(0)),
                                              supplierlist_data.get(x).elementAt(1),
                                              addressid, //set adrressid 0, dummy value because its not used
                                              supplierlist_data.get(x).elementAt(4),
                                              Integer.parseInt(supplierlist_data.get(x).elementAt(8)),
                                              new Date(supplierlist_data.get(x).elementAt(0)))); */
             }
           }
        catch(Exception e1){ 
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null,e1,"ERROR IN SUPPLIER LIST",JOptionPane.ERROR_MESSAGE);}
        
         //supplierlist_data.get(0).elementAt(0);
            
        
         
        String [] suppliers; 
        suppliers = new String [supplierList.size() + 2];
        
        suppliers[0] = "(None)";
        suppliers[1] = "(Add new supplier)";
          
        for(int i=0;i < supplierList.size();i++){
              suppliers[2+i] = supplierList.get(i).getSuppname();
        }
        
       supplier_model = new DefaultComboBoxModel(suppliers); 
       drop_supplier.setModel(supplier_model); 
    }
    
    private int getManufactureID(String manname){
        int id = 0;
        for(int x = 0;x < manufactureList.size();++x){
            if(manufactureList.get(x).getManname().equals(manname) ){
                id = manufactureList.get(x).getManid();
            }
        }
        
       return id;
    }
    
    private void getManufactureList(){
        
         try{ 
             DB db = new DB(RunProgram.CONNECTION_MODE);
             manufactureList = db.getManufactures();    
           }
        catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN MANUFACTURE LIST",JOptionPane.ERROR_MESSAGE);}
        
        String [] manufactures; 
        manufactures = new String [manufactureList.size() + 2];
        
        manufactures[0] = "(None)";
        manufactures[1] = "(Add new manufacture)";
          
        for(int i=0;i < manufactureList.size();i++){
              manufactures[2+i] = manufactureList.get(i).getManname();
        }
        
       manufacture_model = new DefaultComboBoxModel(manufactures); 
       drop_manufacture.setModel(manufacture_model); 
    }
    
    private static Integer getNewItemCode(){
         try{
              DB db = new DB(RunProgram.CONNECTION_MODE);
              newitemcode  = db.get_last_record("item", "itemcode");
            }
          catch (Exception e1)
            {
                e1.printStackTrace();
                //JOptionPane.showMessageDialog(null, "<html>"+e1+"</html>", "Error - New Item Code", 1);
            }
        newitemcode += 1;
        return newitemcode;
    }
    
    public static void setNewItemCode() {
        item_code.setText(getNewItemCode().toString()); 
    }
    
    //set item by barcode
    public void setItemByBarcode(String itembarcode){
          boolean barcode_exist;
          ArrayList<Item> item = new ArrayList<>();
          ArrayList<Iteminventory> iteminv = new ArrayList<>();
          ArrayList<Location> location = new ArrayList<>();
          ArrayList<Category> category = new ArrayList<>();
          ImageIcon image;
          try{
                DB db = new DB(RunProgram.CONNECTION_MODE);
                barcode_exist  = db.executeSQL("SELECT * FROM barcode WHERE barcode = '"+itembarcode+"'"); 
                if(barcode_exist){
                    data     = db.getItemByBarcode(itembarcode);
                    setData();
                    /*iteminv  = db.getItemInventory(item.get(0).getItemid());
                    location = db.getLocation(item.get(0).getLocationid());
                    int catid = item.get(0).getCatid();
                    String cat = "";
                    int umid = item.get(0).getUmId();
                    String unit_measurement = "";
                    if(catid != 0){  cat = db.getItemCategory(catid);}
                    if(umid != 0){ unit_measurement = db.getUnitMeasurement(umid); }

                    //setCheckData(item.get(0).getItemdescription(), iteminv.get(0).getCurrqty()+"",iteminv.get(0).getTotalqty()+"" ,location.get(0).getLocname(),getBarcode(),cat, item.get(0).getPurchaseCost()+"", item.get(0).getSalesCost()+"", unit_measurement);  //description, quantity, available, location, barcode, category

                    int imageid = item.get(0).getImageid();
                    if(imageid <= 0){

                    }else{
                        image    = db.getImage(item.get(0).getImageid());
                        //Resize the image icon and set image
                        Image img = image.getImage();
                        BufferedImage bi = new BufferedImage(75,85,BufferedImage.TRANSLUCENT);//BufferedImage.TYPE_INT_ARGB
                       //BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TRANSLUCENT);//BufferedImage.TYPE_INT_ARGB
                        //Graphics g = bi.createGraphics();
                        Graphics2D g = (Graphics2D) bi.createGraphics();
                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
                        g.drawImage(img, 0, 0, 75, 85, null);
                        g.dispose();
                        //Dimension d = item_image.getPreferredSize();  // uses the photo label's width and height
                        //int x = d.width, y = d.height;
                        //g.drawImage(img,0,0, x, y,null);
                        //g.drawImage(img,0,0, img.getWidth(null), img.getHeight(null),null);  this gets original image pixels
                        ImageIcon newIcon = new ImageIcon(bi);
                        item_image.setIcon(newIcon); // Image
                        item_image.setHorizontalAlignment(SwingConstants.CENTER);
                    }

                    new Item(item.get(0).getItemid(), item.get(0).getItemdescription(),
                             item.get(0).getItembarcodeid() ,item.get(0).getItemState() , 
                             item.get(0).getSerialno(),item.get(0).getSuppId(),
                             item.get(0).getLock(),item.get(0).getLocationid(),
                             item.get(0).getCatid(), item.get(0).getDatecreated(),
                             item.get(0).getDatemodified(), item.get(0).getImageid(),
                             item.get(0).getManid(), item.get(0).getModelid(),
                             item.get(0).getPurchaseCost(),item.get(0).getSalesCost(),
                             item.get(0).getUmId()); */
                }else{
                    //alert user and prompt to use search function by typing the item description
                }
             }
          catch (Exception e1)
            {
                e1.printStackTrace();
                //JOptionPane.showMessageDialog(null, "<html>"+e1+"</html>", "Error - Item Barcode", 1);
            }
            //setItemImage(ID);  
    }
        
    protected void setItem(int itemid){
        
       try{
             db   = new DB(RunProgram.CONNECTION_MODE);
             data = db.getItem(itemid);
             db.close();
             setData();
         }catch(Exception ex){
             ex.printStackTrace();
         }
        
    }
    
    private void setData(){
                 
        item_code.setText(data.get(0).elementAt(1));
        item_description.setText(data.get(0).elementAt(2));
        
        //set category
        String cat = "";
        int catid = Integer.parseInt(data.get(0).elementAt(24));
        int cat_count = drop_category.getItemCount();
        
        for(int x = 0; x < cat_count;x++){ 
            cat = getCategoryName(catid);
            String catvalue = (String) drop_category.getItemAt(x);
            if(catvalue.equals(cat)){
                drop_category.setSelectedIndex(x);
            }
        }
           
        //set checkboxes - invoice and active customer status
        int item_state = Integer.parseInt(data.get(0).elementAt(3));
                
        if(item_state == 1){ item_status.setSelected(true);
        }else{ item_status.setSelected(false); }
        
        //set item type
        if(Integer.parseInt(data.get(0).elementAt(19)) == 2){  //Service
            buttonGroup.setSelected(service.getModel(), true);
        }else if(Integer.parseInt(data.get(0).elementAt(19)) == 1){ //Physical Stock
            buttonGroup.setSelected(physical_stock.getModel(), true);
        }
                
        //set item info
        item_cost.setText(data.get(0).elementAt(16));
        item_quantity.setText(data.get(0).elementAt(9)); 
        selling_price.setText(data.get(0).elementAt(17));
        markup.setText(data.get(0).elementAt(18));
        //set percentage
        double perc = 0.0;
        double m = 0.0;
        double s = 0.0;
        int prc = 0;
        int mk = 0;
        int sl = 0;
        String mark_up = markup.getText();
        String selling = selling_price.getText();
        
        if(mark_up.contains(".") || selling.contains(".")){  
            m = Double.parseDouble(mark_up);
            s = Double.parseDouble(selling);
            perc = (m/s)*100; 
            markuppercentage.setText(round(perc,2)+" %");
        }else{
            mk = Integer.parseInt(mark_up);
            sl = Integer.parseInt(selling);
            prc = (mk/sl)*100;
            perc = round((mk/sl)*100,2); //test
            markuppercentage.setText(round(prc,2)+" %");
        }
         
        //set inventory
        purchase_price.setText(data.get(0).elementAt(16));
        retail_price.setText(data.get(0).elementAt(17));
        current_stock.setText(data.get(0).elementAt(10));
        
        String um = "";
        int umid = Integer.parseInt(data.get(0).elementAt(21));
        int um_count = drop_measurement.getItemCount();
        for(int x = 0;x < um_count;x++){
            um = getUnitmeasurementName(umid);
            String umvalue = (String) drop_measurement.getItemAt(x);
            if(umvalue.equals(um)){
                drop_measurement.setSelectedIndex(x); 
            }
        }
        
        String loc = "";
        int locid = Integer.parseInt(data.get(0).elementAt(23));
        int loc_count = drop_locations.getItemCount();
        for(int x = 0;x < loc_count;x++){
            loc = getLocationName(locid);
            String locvalue = (String) drop_locations.getItemAt(x);
            if(locvalue.equals(loc)){
                drop_locations.setSelectedIndex(x); 
            }
        }
        
        
        //set extra info
        serialno.setText(data.get(0).elementAt(15));
        modelnumber.setText(data.get(0).elementAt(27));
        
        String sup = "";
        int supid = Integer.parseInt(data.get(0).elementAt(20));
        int sup_count = drop_supplier.getItemCount();
        for(int x = 0;x < sup_count;x++){
            sup = getSupplierName(supid);
            String supvalue = (String) drop_supplier.getItemAt(x);
            if(supvalue.equals(sup)){
                drop_supplier.setSelectedIndex(x); 
            }
        }
        
        String man = "";
        int manid = Integer.parseInt(data.get(0).elementAt(26));
        int man_count = drop_manufacture.getItemCount();
        for(int x = 0;x < man_count;x++){
            man = getManufactureName(manid);
            String manvalue = (String) drop_manufacture.getItemAt(x);
            if(manvalue.equals(man)){
                drop_manufacture.setSelectedIndex(x); 
            }
        }
       
        //set image
        ImageIcon image = null;
        try{
            db = new DB(RunProgram.CONNECTION_MODE);
            image = db.getImage(Integer.parseInt(data.get(0).elementAt(25)));  
            if(image.equals("") || image == null){
              IS_IMAGE_SET = false;
              //image = new ImageIcon(getClass().getResource("/inventory_system/image/noimage.jpg"));  //already set in constructor
            }else{
              IS_IMAGE_SET = true;
              removeButton.setEnabled(true); 
              java.awt.Image img = image.getImage();
              BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
              Graphics g = bi.createGraphics();
              g.drawImage(img,0,0, 85, 75,null);  // width ,height 
              ImageIcon newIcon = new ImageIcon(bi);
              item_image.setIcon(newIcon); // Image
            }
        }catch(Exception e1){
           //Image not found or error in retrieving an image, then get a default image
           e1.printStackTrace(); 
           /*image = new ImageIcon(getClass().getResource("/inventory_system/image/noimage.jpg")); 
           java.awt.Image img = image.getImage();
           BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
           Graphics g = bi.createGraphics();
           g.drawImage(img,0,0, 85, 75,null);  // width ,height 
           ImageIcon newIcon = new ImageIcon(bi);
           item_image.setIcon(newIcon); // Image*/ //already set in constructor
        }
              
    }
    
    private void clearData(){
        item_code.setText("");
        item_description.setText("");
        drop_category.setSelectedItem("");
        item_status.setSelected(false); 
        buttonGroup.setSelected(service.getModel(), true);
        buttonGroup.setSelected(physical_stock.getModel(), false);
        item_cost.setText("");
        item_quantity.setText(""); 
        selling_price.setText("");
        markup.setText("");
        selling_price.setText("");
        markuppercentage.setText("");
        purchase_price.setText("");
        retail_price.setText("");
        current_stock.setText("");
        drop_measurement.setSelectedItem(""); 
        drop_locations.setSelectedItem(""); 
        serialno.setText("");
        modelnumber.setText("");
        drop_supplier.setSelectedItem(""); 
        drop_manufacture.setSelectedItem(""); 
        //set image
        ImageIcon image = new ImageIcon(getClass().getResource("/inventory_system/image/noimage.jpg"));  //already set in constructor
        item_image.setIcon(image); // Image
        IS_IMAGE_SET = false;

    }
    
    private void getPercentage(){
        double perc = 0.0;
        double c = 0.0;
        double s = 0.0;
        int prc = 0;
        int ic = 0;
        int sl = 0;
        String cost = item_cost.getText();
        String selling = selling_price.getText();
        
        if(cost.contains(".") || selling.contains(".")){  
            c = Double.parseDouble(cost);
            s = Double.parseDouble(selling);
            perc = (c/s)*100; 
            markup.setText(""+round((s - c),2)+""); 
            markuppercentage.setText(round(perc,2)+" %");
        }else{
            ic = Integer.parseInt(cost);
            sl = Integer.parseInt(selling);
            prc = (ic/sl)*100;
            perc = round((ic/sl)*100,2); //test
            markup.setText(""+round((sl - ic),2)+"");
            markuppercentage.setText(round(prc,2)+" %");
        }
        
        //return "";
    }
    
    public static double round(double value, int places) {
       if(places < 0)
        {
          throw new IllegalArgumentException();
        }
       
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
     return bd.doubleValue();
    }
    
    protected static void setBarcode(String bar_code) {
        barcode = bar_code;
    }
    
    public static void setMode(int mode){
        EDIT_MODE = mode; 
    }
    
    private String getBarcode(){
        return this.barcode;
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
    
    private String getCategoryName(int categoryid){
       String category = "";
       for(int x = 0;x < categoryList.size();++x){
            if(categoryList.get(x).getCatid().equals(categoryid) ){
               category = categoryList.get(x).getCatname();
            }
       }          
       return category;
    }
    
    private String getUnitmeasurementName(int umid){
       String um = "";
       for(int x = 0;x < measurementList.size();++x){
            if(measurementList.get(x).getUmid().equals(umid) ){
               um = measurementList.get(x).getUmDescription();
            }
       }          
       return um;
    }
    
    private String getLocationName(int locid){
       String loc = "";
       for(int x = 0;x < locationList.size();++x){
            if(locationList.get(x).getLocationid().equals(locid) ){
               loc = locationList.get(x).getLocname();
            }
       }          
       return loc;
    }
    
    private String getSupplierName(int supid){
       String sup = "";
       for(int x = 0;x < supplierList.size();++x){
            if(supplierList.get(x).getSuppId().equals(supid) ){
               sup = supplierList.get(x).getSuppname();
            }
       }          
       return sup;
    }
    
    private String getManufactureName(int manid){
       String man = "";
       for(int x = 0;x < manufactureList.size();++x){
            if(manufactureList.get(x).getManid().equals(manid) ){
               man = manufactureList.get(x).getManname();
            }
       }          
       return man;
    }
            
    public static BufferedImage scaleImage(int w, int h, BufferedImage img) throws Exception {
        BufferedImage bi;
        bi = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(img, 0, 0, w, h, null);
        g2d.dispose();
        return bi;
    }
      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        item_title = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        drop_category = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        item_description = new javax.swing.JTextArea();
        item_code = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        selling_price = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        markup = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        markuppercentage = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        drop_locations = new javax.swing.JComboBox();
        drop_measurement = new javax.swing.JComboBox();
        retail_price = new javax.swing.JTextField();
        purchase_price = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        current_stock = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        item_image = new javax.swing.JLabel();
        fullPathImageLabel = new javax.swing.JLabel();
        uploadButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        image_rules = new javax.swing.JEditorPane();
        jLabel15 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        serialno = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        modelnumber = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        drop_supplier = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        drop_manufacture = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        item_status = new javax.swing.JCheckBox();
        jLabel18 = new javax.swing.JLabel();
        item_quant_label = new javax.swing.JLabel();
        item_cost = new javax.swing.JTextField();
        item_quantity = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        service = new javax.swing.JRadioButton();
        physical_stock = new javax.swing.JRadioButton();

        setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(153, 153, 153)), javax.swing.BorderFactory.createEtchedBorder()));
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Items");
        setPreferredSize(MainWindow.home_desktop.getPreferredSize());

        jLabel10.setText("Category");

        drop_category.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                drop_categoryItemStateChanged(evt);
            }
        });

        item_description.setColumns(20);
        item_description.setRows(5);
        jScrollPane1.setViewportView(item_description);

        item_code.setEditable(false);
        item_code.setBackground(java.awt.Color.lightGray);
        item_code.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        item_code.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        item_code.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_codeActionPerformed(evt);
            }
        });

        jLabel5.setText("Item code / Barcode");

        jLabel3.setText("Item description");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setText("Selling Price");

        selling_price.setToolTipText("Selling price");
        selling_price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                selling_priceKeyPressed(evt);
            }
        });

        jLabel13.setLabelFor(markup);
        jLabel13.setText("Mark Up Amount");

        markup.setEditable(false);
        markup.setToolTipText("Profit Gain. Purch. price - Sell. price ");

        jLabel14.setLabelFor(markup);
        jLabel14.setText("Mark Up Percentage");

        markuppercentage.setEditable(false);
        markuppercentage.setToolTipText("Mark Up Percentage");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(selling_price, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                    .addComponent(markup)
                    .addComponent(markuppercentage))
                .addContainerGap(571, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(selling_price, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(markup, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(markuppercentage, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(151, Short.MAX_VALUE))
        );

        tabs.addTab("Info", jPanel3);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setText("Purchase Price");

        jLabel9.setText("Retail Price");

        jLabel11.setText("Unit Measurement");

        jLabel2.setText("Location");

        drop_locations.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                drop_locationsItemStateChanged(evt);
            }
        });
        drop_locations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drop_locationsActionPerformed(evt);
            }
        });

        drop_measurement.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                drop_measurementItemStateChanged(evt);
            }
        });

        retail_price.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        retail_price.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        retail_price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retail_priceActionPerformed(evt);
            }
        });

        purchase_price.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        purchase_price.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        purchase_price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchase_priceActionPerformed(evt);
            }
        });

        jLabel7.setText("Quantity on Hand");

        current_stock.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        current_stock.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        current_stock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                current_stockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(retail_price, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(purchase_price, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(drop_measurement, 0, 160, Short.MAX_VALUE)
                    .addComponent(drop_locations, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(current_stock, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(277, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(purchase_price)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(retail_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(current_stock)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(drop_measurement, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(drop_locations, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(147, Short.MAX_VALUE))
        );

        tabs.addTab("Inventory", jPanel2);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(item_image, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(item_image, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );

        fullPathImageLabel.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N

        uploadButton.setText("Upload Image");
        uploadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadButtonActionPerformed(evt);
            }
        });

        removeButton.setText("Remove Image");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        image_rules.setEditable(false);
        image_rules.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        image_rules.setContentType("text/html"); // NOI18N
        image_rules.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        image_rules.setText("");
        image_rules.setToolTipText("");
        image_rules.setFocusable(false);
        jScrollPane2.setViewportView(image_rules);

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        jLabel15.setText("Filename: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fullPathImageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(uploadButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeButton))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(132, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fullPathImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(uploadButton)
                            .addComponent(removeButton))))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        tabs.addTab("Image", jPanel1);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Serial Number");

        jLabel8.setText("Model ");

        jLabel16.setText("Supplier");

        drop_supplier.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                drop_supplierItemStateChanged(evt);
            }
        });

        jLabel19.setText("Manufacture");

        drop_manufacture.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                drop_manufactureItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(serialno, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(modelnumber, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(drop_supplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(drop_manufacture, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(577, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(serialno, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(modelnumber, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(drop_supplier, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(drop_manufacture)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tabs.addTab("Extra Info", jPanel5);

        jLabel17.setText("Active");

        jLabel18.setText("Opening Cost");

        item_quant_label.setText("Opening Quantity");

        item_cost.setToolTipText("Purchase Price");

        item_quantity.setToolTipText("Starting quantity");

        jLabel1.setText("Item Type");

        buttonGroup.add(service);
        service.setText("Service");
        service.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                serviceMousePressed(evt);
            }
        });

        buttonGroup.add(physical_stock);
        physical_stock.setText("Physical Stock");
        physical_stock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                physical_stockMousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabs)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
                            .addComponent(item_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(drop_category, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                    .addComponent(item_code))
                                .addGap(72, 72, 72)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(item_quant_label, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(item_status)
                                    .addComponent(item_cost, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(item_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(142, 142, 142))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(service)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(physical_stock)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(item_title, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(item_code, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(item_status, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(item_cost))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(item_quant_label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(item_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(drop_category, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(service)
                    .addComponent(physical_stock))
                .addGap(27, 27, 27)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadButtonActionPerformed
        uploadImage();
    }//GEN-LAST:event_uploadButtonActionPerformed
    
    private void uploadImage(){
        JFileChooser c = new JFileChooser();
        c.addChoosableFileFilter(new FileNameExtensionFilter("Image(.jpg)","jpg"));
        c.setAcceptAllFileFilterUsed(true);
        int rVal = c.showOpenDialog(Items.this);

        if (rVal == JFileChooser.APPROVE_OPTION)
        {
            File file      = c.getSelectedFile();
            image_name      = file.getName();
            //file_name      = c.getSelectedFile().toString();
            image_directory = c.getCurrentDirectory().toString();
            fullPathImage   = image_directory+File.separator+image_name;
            fullPathImageLabel.setText(image_name);
            
            //Now, display the image on the window
            URL image_url = null;
            try {  image_url = new File(fullPathImage).toURI().toURL(); }
            catch(MalformedURLException ex)
            {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }

            //get Icon
            ImageIcon icon = null;
            try { icon = new ImageIcon(new java.net.URL(""+image_url)); }
            catch (MalformedURLException ex)
            {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Resize the image icon
            Image img = icon.getImage();
            BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img,0,0, 85, 75,null);
            ImageIcon newIcon = new ImageIcon(bi);
            item_image.setIcon(newIcon); // Image
            item_image.setHorizontalAlignment(SwingConstants.CENTER);
            removeButton.setEnabled(true); 
            IS_IMAGE_SET = true;
            
        }else if(rVal == JFileChooser.CANCEL_OPTION) {
             
            //fullPathImageLabel.setText("");
        }

    }
    
    
    public  void saveItem(){
         //if((textfield_description.getText()).equals("") && (textfield_stock_current.getText()).equals("") && (category.getSelectedItem()).equals("All"))
        if(item_description.getText().equals("") || item_code.getText().equals("")) 
          {
            JOptionPane.showMessageDialog(null, "Make sure that you have entered all required fields: Item Code and Item description", "ERROR - Inputs", 2); 
          }  //check if input is numerical value
        /*else if(!item_cost.getText().matches("^[+-]?\\d+$")  || !item_quantity.getText().matches("^[+-]?\\d+$") || !selling_price.getText().matches("^[+-]?\\d+$") || !purchase_price.getText().matches("^[+-]?\\d+$") || !retail_price.getText().matches("^[+-]?\\d+$"))    
          {
            JOptionPane.showMessageDialog(null, "Please enter valid numerical values", "ERROR - Inputs", 2); 
          }*/
        else
          { 
            String code = item_code.getText();
            String description = item_description.getText();
            String category = drop_category.getSelectedItem().toString();
            String opencost = item_cost.getText();
            String openquant = item_quantity.getText();
            String sellprice = selling_price.getText();
            String purchprice = purchase_price.getText();
            String markupprice = markup.getText();
            String retailprice = retail_price.getText();
            String currentstock =  current_stock.getText();
            boolean type1 = service.isSelected();
            boolean type2 = physical_stock.isSelected();
            boolean itemstate = item_status.isSelected();
            int itemid = 0;
            
            //get image from label
            Icon icon =  item_image.getIcon();
            byte[] imagebytes = null;
            if(IS_IMAGE_SET == false){
               item_image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/noimage.jpg")));
            }else{ 
                //split image name to get file extension type
                String split[] =  image_name.toString().split("\\.");
                String extension = split[1];
                    
                try {
                    BufferedImage bi = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bi,extension, baos);
                    baos.flush();
                    imagebytes = baos.toByteArray();
                    baos.close();
                } catch (IOException ex) {
                    System.out.println(ex);
                } 
              }
            
            String serial = serialno.getText();
            String model = modelnumber.getText();
            String supp  = drop_supplier.getSelectedItem().toString();
            String manuf = drop_manufacture.getSelectedItem().toString();
            int suppid = getSupplierID(supp);
            int manid  = getManufactureID(manuf);
            String unit_measure = drop_measurement.getSelectedItem().toString();
            int umid   = getUnitMeasurementID(unit_measure);
            int lock = 0;
            String location = drop_locations.getSelectedItem().toString();
            int locationid = getLocationID(location);
            int imageid = 0;
            int transaction = 0;
            int refno = 0; 
            //get category
            int catid = 0;
            catid = getCategoryID(category); 
            if(catid <= 0){
               refreshCategoryList();
               catid = getCategoryID(category); 
            }
            
            
             //get item type. Is this a Service or a Physical Stock
             int type = 1; //default
             if(type1 == true && type2 == false){
                 type = 1;    //service
             }else if(type1 == false && type2 == true){
                  type = 2;  //physical stock
             }
            
             //get item state
             int state = 0; //active
             if(itemstate){state = 1;}
             
             String datecreated, enddate, datemodified;
	     SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     datecreated = formatter2.format(logtime);
             datemodified = datecreated;
             enddate = "9999-12-31 00:00:00";
             
             try{
                db = new DB(RunProgram.CONNECTION_MODE);
                db.setCommit(false); 
                
                if(EDIT_MODE == 0){
                    //insert item
                    String insert_item = "INSERT INTO item (`itemcode`,`itemdescription`,`item_state`,`datecreated`,`datemodified`,`enddate`)"+
                                             "VALUES ('" + code + "','" + description + "',"+state+",'"+datecreated+"','"+datemodified+"','"+enddate+"')";
                    db.executeSQL(insert_item);
                    itemid = db.get_field_value("item","itemid","itemcode = '"+code+"'"); 
                    //insert image 
                    String insert_image = "INSERT INTO image (`image`)"+
                                          "     VALUES ('"+imagebytes+"')";
                    db.executeSQL(insert_image);
                    imageid = db.get_last_record("image", "imageid");  
                    
                    //insert item info
                    String insert_item_info = "";
                    if(type == 1){
                     insert_item_info = "INSERT INTO item_info (`itemid`,`serialno`,`purchase_cost`,`sales_cost`,`markup`,`typeid`,`supp_id`,`umid`,`lock`,`locationid`,`catid`,`imageid`,`manid`,`modelnumber`)"+
                                             "VALUES ('" +itemid+ "','" +serial+ "','"+opencost+"','"+sellprice+"','"+markupprice+"',"+type+","+suppid+","+umid+","+lock+","+locationid+","+catid+","+imageid+","+manid+",'"+model+"')";   
                     db.executeSQL(insert_item_info);
                    }else{
                     insert_item_info = "INSERT INTO item_info (`itemid`,`serialno`,`purchase_cost`,`sales_cost`,`markup`,`typeid`,`supp_id`,`umid`,`lock`,`locationid`,`catid`,`imageid`,`manid`,`modelnumber`)"+
                                             "VALUES ('" +itemid+ "','" +serial+ "','"+purchprice+"','"+retailprice+"','"+markupprice+"',"+type+","+suppid+","+umid+","+lock+","+locationid+","+catid+","+imageid+","+manid+",'"+model+"')";
                     db.executeSQL(insert_item_info);
                     
                     //insert item inventory
                     String insert_item_inv = "INSERT INTO iteminventory (`itemid`,`totalqty`,`currqty`,`transcno`,`refno`)"+
                                   "VALUES ("+itemid+",'"+openquant+"','"+currentstock+"','"+transaction+"','"+refno+"')";
                     db.executeSQL(insert_item_inv);
                    }
                    
                    //insert category info
                    String insert_category_info = "INSERT INTO category_info (`catid`,`fieldid`,`data`)"+
                                                  "VALUES ('"+catid+"',3,'"+itemid+"')";
                    db.executeSQL(insert_category_info);
                 
                }else{
                    //update item
                    itemid = Integer.parseInt(data.get(0).elementAt(0));   
                    String update_item = "UPDATE item SET `itemcode` = '" + code + "',`itemdescription` = '" +description+ "',`item_state` = "+state+",`datemodified` = '"+datemodified+"',`enddate` = '"+enddate+"'"+
                                             "WHERE `itemid` = "+itemid;
                    db.executeSQL(update_item);
                    //update image
                    imageid = db.get_field_value("item_info", "imageid", "`itemid` = "+itemid);
                    if(imagebytes.length > 0){
                       String update_image = "UPDATE image SET `image` = '"+imagebytes+"' WHERE `imageid` = "+itemid;
                       db.executeSQL(update_image);  
                     }
                    
                    //update item info
                    String update_item_info = "";
                    if(type == 1){
                      update_item_info = "UPDATE item_info SET `itemid` = "+itemid+",`serialno` = '"+serial+"',`purchase_cost` = "+opencost+","
                                                            + " `sales_cost` = "+sellprice+",`markup` = "+markupprice+",`typeid` = "+type+","
                                                            + "    `supp_id` = "+suppid+",`umid` = "+umid+",`lock` = "+lock+",`locationid` = "+locationid+","
                                                            + "      `catid` = "+catid+",`imageid` = "+imageid+",`manid` = "+manid+",`modelnumber` = '"+model+"'"+
                                             "WHERE `itemid` = "+itemid;    
                      db.executeSQL(update_item_info);
                    }else{
                      update_item_info = "UPDATE item_info SET `itemid` = "+itemid+",`serialno` = '"+serial+"',`purchase_cost` = "+purchprice+","
                                                            + " `sales_cost` = "+retailprice+",`markup` = "+markupprice+",`typeid` = "+type+","
                                                            + "    `supp_id` = "+suppid+",`umid` = "+umid+",`lock` = "+lock+",`locationid` = "+locationid+","
                                                            + "      `catid` = "+catid+",`imageid` = "+imageid+",`manid` = "+manid+",`modelnumber` = '"+model+"'"+
                                             "WHERE `itemid` = "+itemid;
                      db.executeSQL(update_item_info);
                      
                      //update item inventory
                      String update_item_inv = "UPDATE iteminventory SET `totalqty` = "+openquant+",`currqty` = "+currentstock+",`transcno` = '"+transaction+"',`refno` = '"+refno+"'"+
                                             " WHERE `itemid` = "+itemid+" )";
                      db.executeSQL(update_item_inv);
                    }
                    
                    //update category info
                    String update_category_info = "UPDATE category_info SET `catid` = "+catid+", `fieldid` = 3 WHERE `data` = "+itemid;
                    db.executeSQL(update_category_info);

                }
                
                db.setCommit(true);
                db.close();
                if(EDIT_MODE == 0){
                   JOptionPane.showMessageDialog(null, "<html>"+code+" added successfully</html>", "Confirmation", 1);
                    clearData();
                    setNewItemCode();
                }else{
                   JOptionPane.showMessageDialog(null, "<html>"+code+" successfully updated</html>", "Confirmation", 1);
                    clearData();
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
                  JOptionPane.showMessageDialog(null, e1, "ERROR - Failed to add Item", 2);
                  e1.printStackTrace(); //Remove after testing
               } 
            
      
        }
    }
        
    private void current_stockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_current_stockActionPerformed
     current_stock.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
        //msg_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
       // msg_label.setText("");
    }//GEN-LAST:event_current_stockActionPerformed

    private void drop_locationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drop_locationsActionPerformed
      drop_locations.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),0));
        //msg_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
       // msg_label.setText("");
    }//GEN-LAST:event_drop_locationsActionPerformed

    private void item_codeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_codeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_item_codeActionPerformed

    private void purchase_priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purchase_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_purchase_priceActionPerformed

    private void retail_priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retail_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_retail_priceActionPerformed

    private void serviceMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_serviceMousePressed
        //hide the Opening quantity input field and label
        item_quant_label.setVisible(false);
        item_quantity.setVisible(false);
    }//GEN-LAST:event_serviceMousePressed

    private void physical_stockMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_physical_stockMousePressed
        //show the Opening quantity input field and label
        item_quant_label.setVisible(true);
        item_quantity.setVisible(true);
    }//GEN-LAST:event_physical_stockMousePressed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        // remove image
        item_image.setIcon(null);
        item_image.revalidate();
    }//GEN-LAST:event_removeButtonActionPerformed

    private void drop_categoryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_drop_categoryItemStateChanged
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
    }//GEN-LAST:event_drop_categoryItemStateChanged

    private void selling_priceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_selling_priceKeyPressed
        getPercentage();
    }//GEN-LAST:event_selling_priceKeyPressed

    private void drop_supplierItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_drop_supplierItemStateChanged
        if(evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
          Object item = evt.getItem();
          if(item.equals("(Add new supplier)")){ 
              MainWindow.supplierInternalFrame();
              Suppliers.supplier_title.setText("New Supplier");  
              /*dialog = new DialogBoxClass(new JFrame(), true);
              dialog.setTitle("Add New supplier Category"); 
              dialog.setResizable(false); 
              dialog.setModal(true);              //blocks other open windows for input
              //dialog.setLocationByPlatform(true);
              dialog.setActiveCallingClass(this.getClass().getSimpleName());  //Sets the class name so that the dialog instance can know which class component/window can do the refresh/update on the UI 
              dialog.setVisible(true); */
          }else{
              //Do nothing
           
          }
       }
    }//GEN-LAST:event_drop_supplierItemStateChanged

    private void drop_manufactureItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_drop_manufactureItemStateChanged
         if(evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
          Object item = evt.getItem();
          if(item.equals("(Add new manufacture)")){ 
              dialog = new DialogBoxClass(new JFrame(), true);
              dialog.setTitle("Add New manufacture Category"); 
              dialog.setResizable(false); 
              dialog.setModal(true);              //blocks other open windows for input
              //dialog.setLocationByPlatform(true);
              dialog.setActiveCallingClass(this.getClass().getSimpleName());  //Sets the class name so that the dialog instance can know which class component/window can do the refresh/update on the UI 
              dialog.setVisible(true); 
          }else{
              //Do nothing
           
          }
       }
    }//GEN-LAST:event_drop_manufactureItemStateChanged

    private void drop_measurementItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_drop_measurementItemStateChanged
       if(evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
          Object item = evt.getItem();
          if(item.equals("(Add new measurement)")){ 
              dialog = new DialogBoxClass(new JFrame(), true);
              dialog.setTitle("Add New unit measurement Category"); 
              dialog.setResizable(false); 
              dialog.setModal(true);              //blocks other open windows for input
              //dialog.setLocationByPlatform(true);
              dialog.setActiveCallingClass(this.getClass().getSimpleName());  //Sets the class name so that the dialog instance can know which class component/window can do the refresh/update on the UI 
              dialog.setVisible(true); 
          }else{
              //Do nothing
           
          }
       }
    }//GEN-LAST:event_drop_measurementItemStateChanged

    private void drop_locationsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_drop_locationsItemStateChanged
         if(evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
          Object item = evt.getItem();
          if(item.equals("(Add new location)")){ 
              dialog = new DialogBoxClass(new JFrame(), true);
              dialog.setTitle("Add New location Category"); 
              dialog.setResizable(false); 
              dialog.setModal(true);              //blocks other open windows for input
              //dialog.setLocationByPlatform(true);
              dialog.setActiveCallingClass(this.getClass().getSimpleName());  //Sets the class name so that the dialog instance can know which class component/window can do the refresh/update on the UI 
              dialog.setVisible(true); 
          }else{
              //Do nothing
           
          }
       }
    }//GEN-LAST:event_drop_locationsItemStateChanged
   
   public static void updateCategoryList(String cat){
       categories_model.addElement(cat); 
   } 
   
   public static void updateSupplierList(String sup){
        supplier_model.addElement(sup);
   }
    
   public static void updateManufactureList(String man){
        manufacture_model.addElement(man);
   }
   
   public static void updateMeasurementList(String um){
        measurement_model.addElement(um);
   }
    
   public static void updateLocationList(String loc){
        location_model.addElement(loc);
   }
   
   private static void uploadPicture(String imgDir,int idIn) throws FileNotFoundException {  
        /**try{ RandomAccessFile raf = new RandomAccessFile( new File(imgDir ), "rw" );}
        catch(FileNotFoundException e)
        {System.out.println(e);}*/
        File source      = new File(imgDir);
        File destination = new File("C:\\Tebogo\\Own Stuff\\Documents\\Business\\My Company\\kinotech\\01_Projects\\Desktop Application Development\\Inventory Management System\\images\\product\\"+idIn+".jpg");
        
        try 
          {
           //FileUtils.copyDirectory(source, destination);
           FileUtils.copyFile(source, destination); 
          } 
        catch (IOException e) 
          {
            JOptionPane.showMessageDialog(null, "Sorry, Could not upload Picture\n"+e, "Picture Not Uploaded!", 2);   
            //e.printStackTrace();
          }
        
        
    }
    
   public static String getTable(String store) {
    String storeOut;
        switch (store) {
            case "Tool store":
                storeOut = "tool_list";
                break;
            case "Equipment store":
                storeOut = "equipment_list";
                break;
            case "Consumable store":
                storeOut = "consumables";
                break;
            default:
                storeOut = "";
                break;
        }
    
    return storeOut;
  } 
  
   public static String getTableFields(String table) {
    String fields;
        switch (table) {
            case "data_store_01":
                fields = "Store,`Location / Bin`,Description,Current,Creator";
                break;
            case "tool_list":
                fields = "ID,Tool_description,Available,Quantity,Store";
                break;
            case "equipment_list":
                fields = "ID,Equipment_name,Available,Quantity,Store";
                break;
            case "consumables":
                fields = "ID,`Consumable Name`,Quantity,Available,Store";
                break;
            case "learner_list":
                fields = "name,surname,company_id,group,level";
                break;  
            case "issue_list_01":
                fields = "ID,Store,`Location / Bin`,Description,Quantity,Date,Learner,Company_id,Group,level,Facilitator,Storeman";
                break;  
            case "receive_list_01":
                fields = "ID,Store,`Location / Bin`,Description,Quantity,Date,Area,Company_ID,Facilitator,Storeman";
                break;      
            default:
                fields = "";
                break;
        }
    
    return fields;
  }  
      
  public static String matchStoreAndLocation(String loc)  {
    String location = loc.toUpperCase();
    String store = ""; 
    char [] splitLoc = location.toCharArray();
    char charCheck   = splitLoc[0];
    
    if(charCheck == 'C'){
        store = "Consumable store";
    } else if(charCheck == 'T'){
        store = "Tool store";
    }else if(charCheck == 'E'){
        store = "Equipment store";
    }
    else
    {
        store = "notexisting";   
    }
    return store;
  } 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Items.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Items.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Items.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Items.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Items().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup;
    private static javax.swing.JTextField current_stock;
    private static javax.swing.JComboBox drop_category;
    private javax.swing.JComboBox drop_locations;
    private javax.swing.JComboBox<String> drop_manufacture;
    private javax.swing.JComboBox drop_measurement;
    private javax.swing.JComboBox<String> drop_supplier;
    private javax.swing.JLabel fullPathImageLabel;
    private javax.swing.JEditorPane image_rules;
    public static javax.swing.JTextField item_code;
    private javax.swing.JTextField item_cost;
    private static javax.swing.JTextArea item_description;
    private javax.swing.JLabel item_image;
    private javax.swing.JLabel item_quant_label;
    private javax.swing.JTextField item_quantity;
    private javax.swing.JCheckBox item_status;
    public static javax.swing.JLabel item_title;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField markup;
    private javax.swing.JTextField markuppercentage;
    private javax.swing.JTextField modelnumber;
    private javax.swing.JRadioButton physical_stock;
    private javax.swing.JTextField purchase_price;
    private javax.swing.JButton removeButton;
    private javax.swing.JTextField retail_price;
    private javax.swing.JTextField selling_price;
    private javax.swing.JTextField serialno;
    private javax.swing.JRadioButton service;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JButton uploadButton;
    // End of variables declaration//GEN-END:variables
}
