
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.mail.internet.InternetAddress;

/**
 * @author Tebogo kekana - kinotech (Pty) Ltd.
 */
public class Customers extends javax.swing.JInternalFrame {
    private static int id;
    private static int role;
    private static int EDIT_MODE = 0; //set to 1 if you are editing an existing customer
    Date logtime;
    private int physicalAddress_id;
    private int deliveryAddress_id;
    private boolean physicalAddressExist;
    private boolean deliveryAddressExist;
    private String newAddressTypeToInsert;
    private static int newcustomercode;
    //Extra declaration
    private static ArrayList<Category> listCategories;
    private static ArrayList<User> listSalesRep;
    private static String copyfrom;
    //Include other classes
    DialogBoxClass dialog;
    CustomDialog cd;
    static DefaultComboBoxModel categories_model;
    static DefaultComboBoxModel salesrep_model;
    Date currentdate = new Date();
    DB db;
    private Vector<Vector<String>> data; 
    private static int customerid;
    
    public Customers() 
    {
        id      = getUserId(); 
        role    = getRole();
        logtime = new Date();
        copyfrom = "<html><u>Copy from Physical</u></html>";
        physicalAddressExist = false;
        deliveryAddressExist = false;
        newAddressTypeToInsert = "None";
        newcustomercode = getNewCustomerCode();
        initComponents();
        
        getCategoryList(); 
        getSalesRepList();   
        
        //setSize(840, 600); 
        //setLocation(0, 0);
        this.getContentPane().setBackground(Color.white);  //sets layout background color to white
    }
    
    private static Integer getNewCustomerCode(){
        int newcode = 0;
         try{
              DB db = new DB(RunProgram.CONNECTION_MODE);
              newcode  = db.get_last_record("customerline", "custcode");
            }
          catch (Exception e1)
            {
                e1.printStackTrace();
                //JOptionPane.showMessageDialog(null, "<html>"+e1+"</html>", "Error - New Item Code", 1);
            }
        
        newcode += 1;
        return newcode;
    }
    
    public static void setNewCustomerCode() {
        input_customer_code.setText(getNewCustomerCode().toString());  
    }
    
    private void setCustomerCode(int custid){
         int customercode = 0;
         try{ 
             DB db = new DB(RunProgram.CONNECTION_MODE);
             customercode = db.get_field_value("customerline", "custcode", "custid = "+custid);
           }
        catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN CATEGORIES LIST",JOptionPane.ERROR_MESSAGE);}
        
        input_customer_code.setText(""+customercode+"");  
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
       
    private void getSalesRepList(){
        try{ 
              DB db = new DB(RunProgram.CONNECTION_MODE);
             listSalesRep = db.getSalesRepresentativeList();   
           }
        catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN SALES REPRESENTATIVE LIST",JOptionPane.ERROR_MESSAGE);}
        
        String [] salesrep; 
        salesrep = new String [listSalesRep.size() + 2];
        salesrep[0] = "(None)";
        salesrep[1] = "(Add new sales rep)";
                   
        for(int i=0;i < listSalesRep.size();i++){
            salesrep[2+i] = listSalesRep.get(i).getFirstname()+" "+listSalesRep.get(i).getLastname();
        }
        
        salesrep_model = new DefaultComboBoxModel(salesrep);
        drop_salesrep.setModel(salesrep_model); 
        
    }
            
    public static void updateCategoryList(String cat){
        categories_model.addElement(cat); 
    }
        
    public static void updateSalesRepList(String salesrep){
        salesrep_model.addElement(salesrep); 
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

        customer_title = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        input_customer_name = new javax.swing.JTextField();
        customer_status = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        mail_invoice = new javax.swing.JCheckBox();
        drop_categories = new javax.swing.JComboBox<>();
        tabs_panel = new javax.swing.JTabbedPane();
        address_tab = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        address1 = new javax.swing.JTextField();
        address2 = new javax.swing.JTextField();
        city = new javax.swing.JTextField();
        province = new javax.swing.JTextField();
        country = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        address3 = new javax.swing.JTextField();
        address4 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        country1 = new javax.swing.JTextField();
        province1 = new javax.swing.JTextField();
        city1 = new javax.swing.JTextField();
        copy = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        zipcode = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        zipcode1 = new javax.swing.JTextField();
        contact_tab = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        contactname = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        contactmobile = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        contacttelephone = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        contactmail = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        contactfax = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        contactwebsite = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        contact_status = new javax.swing.JCheckBox();
        drop_salesrep = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        input_vat = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        input_customer_code = new javax.swing.JTextField();

        setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(153, 153, 153)), javax.swing.BorderFactory.createEtchedBorder()));
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Customer");
        setPreferredSize(MainWindow.home_desktop.getPreferredSize());

        jLabel1.setText("Customer Name");

        jLabel3.setText("Category");

        jLabel6.setText("E-mail invoices");

        drop_categories.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                drop_categoriesItemStateChanged(evt);
            }
        });

        address_tab.setBackground(java.awt.Color.white);

        jLabel7.setText("Physical Address");

        jLabel8.setText("City");

        jLabel9.setText("Province/State");

        jLabel10.setText("Country");

        jLabel11.setText("Delivery Address");

        jLabel12.setText("City");

        jLabel13.setText("Province/State");

        jLabel14.setText("Country");

        copy.setForeground(java.awt.Color.blue);
        copy.setText(copyfrom);
        copy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                copyMouseClicked(evt);
            }
        });

        jLabel18.setText("Zip Code");

        jLabel19.setText("Zip Code");

        javax.swing.GroupLayout address_tabLayout = new javax.swing.GroupLayout(address_tab);
        address_tab.setLayout(address_tabLayout);
        address_tabLayout.setHorizontalGroup(
            address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(address_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(address1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(address2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(address_tabLayout.createSequentialGroup()
                        .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(province, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(country, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(city, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(address_tabLayout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(zipcode, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(address3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(address4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(address_tabLayout.createSequentialGroup()
                        .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(province1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(country1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(city1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(address_tabLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(copy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(address_tabLayout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(zipcode1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(144, 144, 144))
        );
        address_tabLayout.setVerticalGroup(
            address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(address_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(copy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(address_tabLayout.createSequentialGroup()
                        .addComponent(address1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(address2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(city)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(province, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(country, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(address_tabLayout.createSequentialGroup()
                        .addComponent(address3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(address4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(city1)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(province1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(country1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(zipcode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(address_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(zipcode1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(87, Short.MAX_VALUE))
        );

        tabs_panel.addTab("Address", address_tab);

        contact_tab.setBackground(java.awt.Color.white);

        jLabel20.setText("Contact Details");

        jLabel21.setText("Contact Name");

        jLabel22.setText("Mobile");

        jLabel23.setText("Telephone");

        jLabel24.setText("E-mail");

        jLabel25.setText("Fax");

        jLabel26.setText("Website");

        jLabel27.setText("Active");

        javax.swing.GroupLayout contact_tabLayout = new javax.swing.GroupLayout(contact_tab);
        contact_tab.setLayout(contact_tabLayout);
        contact_tabLayout.setHorizontalGroup(
            contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contact_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(contact_tabLayout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contactname, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contact_tabLayout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contactmobile, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contact_tabLayout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contacttelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contact_tabLayout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contactmail, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contact_tabLayout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contactfax, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contact_tabLayout.createSequentialGroup()
                        .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(contact_status)
                            .addComponent(contactwebsite, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(447, Short.MAX_VALUE))
        );
        contact_tabLayout.setVerticalGroup(
            contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contact_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(contactname, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(contactmobile)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(contacttelephone)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(contactmail)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(contactfax)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(contactwebsite)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contact_status))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs_panel.addTab("Contact", contact_tab);

        drop_salesrep.setToolTipText("");
        drop_salesrep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                drop_salesrepItemStateChanged(evt);
            }
        });

        jLabel16.setText("Sales Rep");

        jLabel15.setText("VAT Number");

        jLabel17.setText("Active");

        jLabel2.setText("Customer Code / ID");

        input_customer_code.setEditable(false);
        input_customer_code.setBackground(java.awt.Color.lightGray);
        input_customer_code.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        input_customer_code.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        input_customer_code.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabs_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(customer_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(drop_categories, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(drop_salesrep, 0, 160, Short.MAX_VALUE)
                            .addComponent(input_customer_name)
                            .addComponent(input_customer_code))
                        .addGap(85, 85, 85)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(input_vat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(customer_status)
                            .addComponent(mail_invoice))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customer_title, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(customer_status, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(11, 11, 11)
                        .addComponent(mail_invoice))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(input_vat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(input_customer_code, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(input_customer_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(drop_categories, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(drop_salesrep, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabs_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    protected void setCustomer(int custid){
        
       try{
             db   = new DB(RunProgram.CONNECTION_MODE);
             data = db.getCustomer(custid);
             db.close();
             setCustomerCode(custid);
             setData();
         }catch(Exception ex){
             ex.printStackTrace();
         }
        
    }
        
    private void setData(){
                 
        input_customer_name.setText(data.get(0).elementAt(9));
        input_vat.setText(data.get(0).elementAt(10));
        int data_size = data.size();
        
        //set category
        String cat = "";
        int catid = Integer.parseInt(data.get(0).elementAt(7));
        int cat_count = drop_categories.getItemCount();
        
        for(int x = 0; x < cat_count;x++){ 
            cat = getCategoryName(catid);
            String catvalue = drop_categories.getItemAt(x);
            if(catvalue.equals(cat)){
                drop_categories.setSelectedIndex(x);
            }
        }
        //set sales representative
        String sales = "";
        int salesrepid = Integer.parseInt(data.get(0).elementAt(3));
        int salesrep_count = drop_salesrep.getItemCount();
        
        for(int y = 0; y < salesrep_count;y++){ 
            sales = getSalesRepName(salesrepid);
            String salesrepvalue = drop_salesrep.getItemAt(y);
            if( salesrepvalue.equals(sales)){
                drop_salesrep.setSelectedIndex(y);
            }
        }
        
        //set checkboxes - invoice and active customer status
        int cust_state = Integer.parseInt(data.get(0).elementAt(11));
        int invoice = Integer.parseInt(data.get(0).elementAt(2));
        
        if(cust_state == 1){ customer_status.setSelected(true);
        }else{ customer_status.setSelected(false); }
        
        if(invoice == 1){ mail_invoice.setSelected(true);
        }else{ mail_invoice.setSelected(false); }
        
        /**
         * Set addresses (Physical & Delivery address)
         * 
         * Firstly, check if the customer has more than one address and set accordingly
         * 
         */
        
        for(int x = 0; x < data_size;x++){
           int addresstype = Integer.parseInt(data.get(x).elementAt(22));
           if(addresstype == 2){
              physicalAddress_id = Integer.parseInt(data.get(x).elementAt(15));
              address1.setText(data.get(x).elementAt(16));
              address2.setText(data.get(x).elementAt(17));
              city.setText(data.get(x).elementAt(18));
              province.setText(data.get(x).elementAt(20));
              country.setText(data.get(x).elementAt(19));
              zipcode.setText(data.get(x).elementAt(21));
              physicalAddressExist = true; 
            }else if(addresstype == 3){
              deliveryAddress_id = Integer.parseInt(data.get(x).elementAt(15));
              address3.setText(data.get(x).elementAt(16));
              address4.setText(data.get(x).elementAt(17));
              city1.setText(data.get(x).elementAt(18));
              province1.setText(data.get(x).elementAt(20));
              country1.setText(data.get(x).elementAt(19));
              zipcode1.setText(data.get(x).elementAt(21));
              deliveryAddressExist = true;
            }
        }
        
        if(physicalAddressExist == true && deliveryAddressExist == false){
            newAddressTypeToInsert = "Delivery";
        }else if(physicalAddressExist == false && deliveryAddressExist == true){
            newAddressTypeToInsert = "Physical";
        }else if(physicalAddressExist == false && deliveryAddressExist == false){
            newAddressTypeToInsert =  "Both";
        }else if(physicalAddressExist == true && deliveryAddressExist == true){
            newAddressTypeToInsert = "None";
        }
        
        //set contact
        contactname.setText(data.get(0).elementAt(25));
        contactmobile.setText(data.get(0).elementAt(27));
        contacttelephone.setText(data.get(0).elementAt(28));
        contactmail.setText(data.get(0).elementAt(26));
        contactfax.setText(data.get(0).elementAt(29));
        contactwebsite.setText(data.get(0).elementAt(30));
        int constate = Integer.parseInt(data.get(0).elementAt(31));
        if(constate == 1){ contact_status.setSelected(true);         
        }else{ contact_status.setSelected(false);}
      
    }
    
    private void clearData(){
        input_customer_name.setText("");
        input_vat.setText("");
        drop_categories.setSelectedItem(""); 
        drop_salesrep.setSelectedItem(""); 
        customer_status.setSelected(false);
        mail_invoice.setSelected(false);
        address1.setText("");
        address2.setText("");
        city.setText("");
        province.setText("");
        country.setText("");
        zipcode.setText("");
        address3.setText("");
        address4.setText("");
        city1.setText("");
        province1.setText("");
        country1.setText("");
        zipcode1.setText("");
        contactname.setText("");
        contactmobile.setText("");
        contacttelephone.setText("");
        contactmail.setText("");
        contactfax.setText("");
        contactwebsite.setText("");
        contact_status.setSelected(false);
    }
    
    public void saveCustomer(){
        if((input_customer_name.getText().equals("") || contactname.getText().equals("") || contactmail.getText().equals(""))) 
        {
          JOptionPane.showMessageDialog(null, "Make sure that you have entered all required fields: Customer Name, Contact name and Contact e-mail address", "ERROR - Inputs", 2); 
          
        }else if(!isEmailAddressValid(contactmail.getText())){
             JOptionPane.showMessageDialog(null, "Please enter a valid e-mail address", "ERROR - Inputs", 2); 
        }
        else
        {   
            String customer = input_customer_name.getText();
            String category = drop_categories.getSelectedItem().toString();
            String salesrep = drop_salesrep.getSelectedItem().toString();
            String vat     = input_vat.getText();
            boolean status  = customer_status.isSelected();
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
            int custid = 0;
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
            
            int cust_state = 0;
            int einvoice = 0;
            int contact_status = 0;
            int physical_addressid = 0;
            int delivery_addressid = 0;
          
            if(!isAddressTypeEmpty(2)){
                //Check Physical address
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
                //Check Delivery address
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
                        
            if(status){ cust_state = 1;}
            if(invoices){einvoice = 1;}
            if(constatus){contact_status = 1;}
           
            int salerepid  = 0;
            salerepid = getSalesRepID(salesrep); 
            if(salerepid <= 0){
               refreshSalesRepresentativeList();
               salerepid = getSalesRepID(salesrep); 
            }
            
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
                    //insert customer
                    String insert_customer = "INSERT INTO customer (`custname`,`vatnumber`,`cust_state`,`datecreated`,`datemodified`,`enddate`)"+
                                             "VALUES ('" + customer + "','" + vat + "',"+cust_state+",'"+datecreated+"','"+datemodified+"','"+enddate+"')";
                    db.executeSQL(insert_customer);
                    custid = db.get_field_value("customer","custid","custname = '"+customer+"'");    
                    //insert contact
                    String insert_contact = "INSERT INTO contact (`name`,`email`,`mobile`,`telephone`,`fax`,`website`,`con_state`,`datecreated`,`datemodified`,`enddate`)"+
                                   "VALUES ('"+contname+"','"+email+"','"+mobile+"','"+tel+"','"+fax+"','"+web+"',"+contact_status+",'"+datecreated+"','"+datemodified+"','"+enddate+"')";
                    db.executeSQL(insert_contact);
                    conid = db.get_field_value("contact","conid"," name = '"+contname+"'");    
                    //insert category info
                    //String insert_category_info = "INSERT INTO category_info (`catid`,`fieldid`,`data`)"+
                    //                              "VALUES ('"+catid+"',1,'"+custid+"')";
                    //db.executeSQL(insert_category_info);
                    //insert address
                    String insert_physical_address = "INSERT INTO address (`address1`,`address2`,`city`,`country`,`province`,`zipcode`,`addresstype`,`createdby`)"+
                                                        "VALUES ('"+addr1+"','"+addr2+"','"+cty+"','"+countr+"','"+prov+"','"+zip+"',2,'"+getUserId()+"')";
                    
                    String insert_delivery_address = "INSERT INTO address (`address1`,`address2`,`city`,`country`,`province`,`zipcode`,`addresstype`,`createdby`)"+
                                                        "VALUES ('"+addr3+"','"+addr4+"','"+cty1+"','"+countr1+"','"+prov1+"','"+zip1+"',3,'"+getUserId()+"')";               
                     
                    if(insert_count == 2){
                      db.executeSQL(insert_physical_address);
                      physical_addressid = db.get_field_value("address","addressid","address1 = '"+addr1+"'");
                      db.executeSQL(insert_delivery_address);  
                      delivery_addressid = db.get_field_value("address","addressid","address1 = '"+addr3+"'");
                      
                      //insert customer info
                      String insert_customer_info = "INSERT INTO customer_info (`custid`,`einvoice`,`salesrepid`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                                   "VALUES (" + custid + "," + einvoice + ","+salerepid+","+image+","+delivery_addressid+","+conid+","+catid+"),"
                                                                        + "(" + custid + "," + einvoice + ","+salerepid+","+image+","+physical_addressid+","+conid+","+catid+")";
                      db.executeSQL(insert_customer_info);
                      
                    }else if(insert_count == 1){
                       if(addressToinsert == 2){
                           db.executeSQL(insert_physical_address); 
                            physical_addressid = db.get_field_value("address","addressid","address1 = '"+addr1+"'");
                            String insert_customer_info = "INSERT INTO customer_info (`custid`,`einvoice`,`salesrepid`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                          " VALUES (" + custid + "," + einvoice + ","+salerepid+","+image+","+physical_addressid+","+conid+","+catid+")";
                           db.executeSQL(insert_customer_info);
                       }else if(addressToinsert == 3){
                           db.executeSQL(insert_delivery_address);  
                           delivery_addressid = db.get_field_value("address","addressid","address1 = '"+addr3+"'");
                           String insert_customer_info = "INSERT INTO customer_info (`custid`,`einvoice`,`salesrepid`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                          " VALUES (" + custid + "," + einvoice + ","+salerepid+","+image+","+delivery_addressid+","+conid+","+catid+")";
                           db.executeSQL(insert_customer_info);
                       }
                    }

                }else{
                    /***************************************************************
                    * You are in EDIT MODE, use vector data to get element at the  *
                    * specified point to get various details about the customer    *
                    ***************************************************************/
                    //update customer
                    custid = Integer.parseInt(data.get(0).elementAt(1));   
                    String update_customer = "UPDATE customer SET `custname` = '" + customer + "',`vatnumber` = '" + vat + "',`cust_state` = "+cust_state+",`datemodified` = '"+datemodified+"',`enddate` = '"+enddate+"'"+
                                             " WHERE `custid` = "+custid;
                    db.executeSQL(update_customer);    
                    //update contact
                    conid = Integer.parseInt(data.get(0).elementAt(24));    
                    String update_contact = "UPDATE contact SET `name` = '"+contname+"',`email` = '"+email+"',`mobile` = '"+mobile+"',`telephone` = '"+tel+"',`fax` = '"+fax+"',`website` = '"+web+"',`con_state` = "+contact_status+" ,`datemodified` = '"+datemodified+"',`enddate` = '"+enddate+"'"+
                                            " WHERE `conid` = "+conid;
                    db.executeSQL(update_contact);
                    
                    //insert category info
                    //String update_category_info = "UPDATE category_info SET `catid` = "+catid+", `fieldid` = 1 WHERE `data` = "+custid;
                    //db.executeSQL(update_category_info);

                    //update address             
                    String update_physical_address = "UPDATE address SET `address1` = '"+addr1+"',`address2` = '"+addr2+"',`city` = '"+cty+"',`country` = '"+countr+"',`province` = '"+prov+"',`zipcode` = '"+zip+"',`addresstype` = 2,`createdby` = '"+getUserId()+"'"+
                                             " WHERE `addressid` = "+physicalAddress_id;
                    String update_delivery_address = "UPDATE address SET `address1` = '"+addr3+"',`address2` = '"+addr4+"',`city` = '"+cty1+"',`country` = '"+countr1+"',`province` = '"+prov1+"',`zipcode` = '"+zip1+"',`addresstype` = 3,`createdby` = '"+getUserId()+"'"+
                                             " WHERE `addressid` = "+deliveryAddress_id;
                    
                    String insert_physical_address = "INSERT INTO address (`address1`,`address2`,`city`,`country`,`province`,`zipcode`,`addresstype`,`createdby`)"+
                                                        "VALUES ("+addr1+"','"+addr2+"','"+cty+"','"+countr+"','"+prov+"','"+zip+"',2,'"+getUserId()+"')";
                    String insert_delivery_address = "INSERT INTO address (`address1`,`address2`,`city`,`country`,`province`,`zipcode`,`addresstype`,`createdby`)"+
                                                        "VALUES ('"+addr3+"','"+addr4+"','"+cty1+"','"+countr1+"','"+prov1+"','"+zip1+"',3,'"+getUserId()+"')";               
                    
                    if(insert_count == 2){
                       //check if there is a new address type to insert
                       if(newAddressTypeToInsert == "Physical"){
                          db.executeSQL(update_delivery_address); 

                          String update_customer_info = "UPDATE customer_info SET `einvoice` = " +einvoice+ ",`salesrepid` = "+salerepid+",`imageid` = "+image+", `conid` = "+conid+", `catid` = "+catid+""+
                                                        " WHERE `custid` = "+custid+""+
                                                        "   AND `addressid` = "+deliveryAddress_id;
                          db.executeSQL(update_customer_info);
                          
                          db.executeSQL(insert_physical_address);
                          physical_addressid = db.get_field_value("address","addressid","address1 = '"+addr1+"'");
                          //insert customer info
                          String insert_customer_info = "INSERT INTO customer_info (`custid`,`einvoice`,`salesrepid`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                        " VALUES (" + custid + "," + einvoice + ","+salerepid+","+image+","+physical_addressid+","+conid+","+catid+")";
                          db.executeSQL(insert_customer_info);
                        }else if(newAddressTypeToInsert == "Delivery"){
                          db.executeSQL(update_physical_address); 
                          
                          String update_customer_info = "UPDATE customer_info SET `einvoice` = "+einvoice+",`salesrepid` = "+salerepid+",`imageid` = "+image+", `conid` = "+conid+", `catid` = "+catid+""+
                                                         " WHERE `custid` = "+custid+""+
                                                         "   AND `addressid` = "+physicalAddress_id;
                          db.executeSQL(update_customer_info);
                          
                          db.executeSQL(insert_delivery_address);
                          delivery_addressid = db.get_field_value("address","addressid","address1 = '"+addr3+"'");
                          //insert customer info
                          String insert_customer_info = "INSERT INTO customer_info (`custid`,`einvoice`,`salesrepid`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                        " VALUES ("+custid+","+einvoice+","+salerepid+","+image+","+delivery_addressid+","+conid+","+catid+")";
                          db.executeSQL(insert_customer_info);
                        }else if(newAddressTypeToInsert == "Both"){
                           db.executeSQL(insert_physical_address);
                           physical_addressid = db.get_field_value("address","addressid","address1 = '"+addr1+"'");
                           db.executeSQL(insert_delivery_address);  
                           delivery_addressid = db.get_field_value("address","addressid","address1 = '"+addr3+"'");
                           
                           String insert_customer_info = "INSERT INTO customer_info (`custid`,`einvoice`,`salesrepid`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                                   "VALUES ("+custid+","+einvoice+","+salerepid+","+image+","+physical_addressid+","+conid+","+catid+")"+
                                                                   "       ("+custid+","+einvoice+","+salerepid+","+image+","+delivery_addressid+","+conid+","+catid+")";
                           db.executeSQL(insert_customer_info);
                        }else if(newAddressTypeToInsert == "None"){
                           db.executeSQL(update_physical_address);
                           db.executeSQL(update_delivery_address);          
                           
                           //update customer info
                           String update_customer_info = "UPDATE customer_info SET `einvoice` = " + einvoice + ",`salesrepid` = "+salerepid+",`imageid` = "+image+", `conid` = "+conid+", `catid` = "+catid+""+
                                                         " WHERE `custid` = "+custid+""+
                                                         "   AND `addressid` = "+physicalAddress_id;
                           db.executeSQL(update_customer_info);

                           String update_customer_info2 = "UPDATE customer_info SET `einvoice` = " + einvoice + ",`salesrepid` = "+salerepid+",`imageid` = "+image+", `conid` = "+conid+", `catid` = "+catid+""+
                                                         " WHERE `custid` = "+custid+""+
                                                         "   AND `addressid` = "+deliveryAddress_id;
                           db.executeSQL(update_customer_info2);   
                        } 
                    }else if(insert_count == 1){
                           if(addressToinsert == 2){
                              db.executeSQL(update_physical_address); 
                              //physical_addressid = db.get_field_value("address","addressid","address1 = '"+addr1+"'");
                              
                              //update customer info
                              String update_customer_info = "UPDATE customer_info SET `einvoice` = '" + einvoice + "',`salesrepid` = "+salerepid+",`imageid` = '"+image+"', `conid` = "+conid+", `catid` = "+catid+""+
                                                         " WHERE `custid` = "+custid+""+
                                                         "   AND `addressid` = "+physicalAddress_id;
                             db.executeSQL(update_customer_info);
                           }else if(addressToinsert == 3){
                              db.executeSQL(update_delivery_address);  
                              //delivery_addressid = db.get_field_value("address","addressid","address1 = '"+addr3+"'");
                              
                              //update customer info
                              String update_customer_info = "UPDATE customer_info SET `einvoice` = " + einvoice + ",`salesrepid` = "+salerepid+",`imageid` = "+image+", `conid` = "+conid+", `catid` = "+catid+""+
                                                         " WHERE `custid` = "+custid+""+
                                                         "   AND `addressid` = "+deliveryAddress_id;
                              db.executeSQL(update_customer_info);
                           }
                    }
                }
                
                db.setCommit(true);
                db.close();
                if(EDIT_MODE == 0){
                   JOptionPane.showMessageDialog(null, "<html>"+customer+" added successfully</html>", "Confirmation", 1);
                   clearData();
                   setNewCustomerCode();
                }else{
                   JOptionPane.showMessageDialog(null, "<html>"+customer+" successfully updated</html>", "Confirmation", 1);
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
                  JOptionPane.showMessageDialog(null, e1, "ERROR - Failed to add Customer", 2);
                  e1.printStackTrace(); //Remove after testing
               }
        } 
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
        
        //check if Physical address fields are empty
        if(addresstype == 2){
           if(address1.getText().isEmpty() && address2.getText().isEmpty() && city.getText().isEmpty() && province.getText().isEmpty() && country.getText().isEmpty() && zipcode.getText().isEmpty())
             {
               type = true;
             }
         //check if Delivery address fields are empty
         }else if(addresstype == 3){ 
           if(address3.getText().isEmpty() && address4.getText().isEmpty() && city1.getText().isEmpty() && province1.getText().isEmpty() && country1.getText().isEmpty() && zipcode1.getText().isEmpty())
             {
               type = true;
             }   
         }
        
        return type;
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
    
    public void refreshCategoryList(){
         getCategoryList();
    }
    
    private int getSalesRepID(String salesrepname){
        int salesrep_userid = 0;
        String fname = "", lname = "";
        
        if(!salesrepname.equals("(None)") || !salesrepname.equals("(Add new sales rep)")){
            String splitName [] = salesrepname.split(" "); 
            fname = splitName[0];
            lname = splitName[1];
        }
        
        
        for(int x = 0;x < listSalesRep.size();++x){
            if(listSalesRep.get(x).getFirstname().equals(fname) || listSalesRep.get(x).getLastname().equals(lname)){
                salesrep_userid = listSalesRep.get(x).getUserid();
            }
        }
   
        return salesrep_userid;
    }
    
    private String getSalesRepName(int salesrepid){
       String salesrep = "";
       for(int x = 0;x < listSalesRep.size();x++){
           if(listSalesRep.get(x).getUserid().equals(salesrepid)){ 
               salesrep = listSalesRep.get(x).getFirstname()+" "+listSalesRep.get(x).getLastname();
           }
       } 
       return salesrep;
    }
    
    public void refreshSalesRepresentativeList(){
         getSalesRepList();
    }
    
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

    private void drop_salesrepItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_drop_salesrepItemStateChanged
          if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
          Object item = evt.getItem();
          if(item.equals("(Add new sales rep)")){ 
              cd = new CustomDialog(new JFrame(), true);
              cd.setTitle("Add New Sales Representative");
              cd.setResizable(false);
              cd.setModal(true); 
              cd.setActiveCallingClass(this.getClass().getSimpleName()); 
              cd.setVisible(true); 

          }else{
              //Do nothing
           
          }
       }
        
    }//GEN-LAST:event_drop_salesrepItemStateChanged

    private void copyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_copyMouseClicked
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
    }//GEN-LAST:event_copyMouseClicked

    /**
     * @param args the command line arguments
     */
    public void main(String args[]) {
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
            java.util.logging.Logger.getLogger(Customers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Customers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Customers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Customers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Customers().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address1;
    private javax.swing.JTextField address2;
    private javax.swing.JTextField address3;
    private javax.swing.JTextField address4;
    private javax.swing.JPanel address_tab;
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
    private javax.swing.JLabel copy;
    private javax.swing.JTextField country;
    private javax.swing.JTextField country1;
    private javax.swing.JCheckBox customer_status;
    public static javax.swing.JLabel customer_title;
    private javax.swing.JComboBox<String> drop_categories;
    private javax.swing.JComboBox<String> drop_salesrep;
    private static javax.swing.JTextField input_customer_code;
    private javax.swing.JTextField input_customer_name;
    private javax.swing.JTextField input_vat;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox mail_invoice;
    private javax.swing.JTextField province;
    private javax.swing.JTextField province1;
    private javax.swing.JTabbedPane tabs_panel;
    private javax.swing.JTextField zipcode;
    private javax.swing.JTextField zipcode1;
    // End of variables declaration//GEN-END:variables
}
