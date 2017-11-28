
package inventory_system;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.mail.internet.InternetAddress;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.jdesktop.swingx.renderer.FormatStringValue;

/**
 * @author Tebogo kekana - kinotech (Pty) Ltd.
 */
public class Quotations extends javax.swing.JInternalFrame {
    private static int id;
    private static int role;
    private static int EDIT_MODE = 0; //set to 1 if you are editing an existing customer
    private static boolean CELL_EDITABLE;
    Date logtime, quote_due_date, quote_start_date;
    Calendar calendar;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    FormatStringValue sv = new FormatStringValue(sdf); //dd-MM-yyyy HH:mm:ss
    private int physicalAddress_id;
    private int deliveryAddress_id;
    private boolean physicalAddressExist;
    private boolean deliveryAddressExist;
    private String newAddressTypeToInsert;
    private static int newquotation;
    //Extra declaration
    private static ArrayList<AddressType> listAddress;
    private static ArrayList<Category> listCategories;
    private static ArrayList<User> listSalesRep;
    private static ArrayList<Itemtype> listItemType;
    private static String copyfrom;
    //Include other classes
    DialogBoxClass dialog;
    CustomDialog cd;
    static DefaultComboBoxModel customers_model;
    static DefaultComboBoxModel addresstype_model;
    static DefaultComboBoxModel salesrep_model;
    static DefaultComboBoxModel select_item_model;
    Date currentdate = new Date();
    DB db;
    private Vector<Vector<String>> data;
    private Vector<String> header;
    //MyTableModel tmodel;
    public static DefaultTableModel tmodel;
    public static int []  tableRowSelected;
    String new_quote_date;
    int date_counter_check = 0;
    TableColumn columnIndex;
    TableModelListener tml;
       
  public Quotations()     {
        id      = getUserId(); 
        role    = getRole();
        logtime = new Date();
        quote_due_date = new Date();
        quote_start_date = new Date();
        calendar = Calendar.getInstance();
        physicalAddressExist = false;
        deliveryAddressExist = false;
        newAddressTypeToInsert = "None";
        newquotation = getNewQuote();                
        initComponents();
        
        getCustomerList();
        getAddressList(); 
        getSalesRepList();   
        setDate();
        //setItemList();
        
        //tmodel = new MyTableModel();
        tmodel = new DefaultTableModel();
        tmodel.setDataVector(new Object[][] { { "+","-", "", "","","",""}},
                             new Object[]     {"Add","Remove", "Item", "Description", "Qty","Item Price", "Total"});
                             
        //quote_table.addMouseListener(new Quotations.MyMouseListener());
        quote_table.setModel(tmodel);
        quote_table.getColumn("Add").setCellRenderer(new ButtonRenderer()); 
        quote_table.getColumn("Add").setCellEditor( new ButtonEditor(new JCheckBox()));
        quote_table.getColumn("Remove").setCellRenderer(new ButtonRenderer()); 
        quote_table.getColumn("Remove").setCellEditor( new ButtonEditor(new JCheckBox()));
        setTableModelListener();
        
        //tmodel.addRow(new Object[]{new String()}); 
        //quote_table.createDefaultColumnsFromModel();

        
        //columnIndex.setCellEditor(new KComboBoxEditor(values));
        //columnIndex.setCellRenderer(new KComboBoxRenderer(values)); 
        
        this.getContentPane().setBackground(Color.white);  //sets layout background color to white
    }
    
  public class MyMouseListener extends MouseAdapter {
     public MyMouseListener() 
      {
      
      }
     
     public void mousePressed(MouseEvent e){
         if ((e.getClickCount() == 1) && (!e.isMetaDown()))
           {
             tableRowSelected = Quotations.this.quote_table.getSelectedRows();
           }
       }
  } 
  
  private void disableCellEditing(){
      int rows = quote_table.getRowCount();
      int cols = quote_table.getColumnCount();
      
      for(int r = 0;r < rows;r++){
         for(int c = 0;c < cols;c++){
           quote_table.isCellEditable(r,c);           
         }
      }
  }
  
  private void setTableModelListener(){
      tml = new TableModelListener() {
          @Override
          public void tableChanged(TableModelEvent e) {
            if(e.getType() == TableModelEvent.UPDATE){
                if(e.getColumn() == 4){
                   calculateTotals();
                   updateQuoteTable(quote_table.getModel().getValueAt(e.getFirstRow(),e.getColumn()).toString(),e.getFirstRow(),6);
               } 
               /*System.out.println("Cell " + e.getFirstRow() + ", "
                            + e.getColumn() + " changed. The new value: "
                            + quote_table.getModel().getValueAt(e.getFirstRow(),
                            e.getColumn())); */
            }
          }
      };
      quote_table.getModel().addTableModelListener(tml); 
  }
  
  public static void calculateTotals() {
      double total = 0;
      double total_item_price = 0;
      double total_items_price = 0;
      double quantity = 1;
      double totalvat = 0;
      double vat = 0.14;  //DO NOT hardcode VAT, change it to a database value
      int rows = quote_table.getRowCount() - 1;
      for(int r = 0;r < rows;r++){
          Object item_pricel = quote_table.getValueAt(r,5); 
          String item_pricel_string = item_pricel.toString();
          if(item_pricel_string.isEmpty()){
              item_pricel_string = "0";
          }
          total = Double.parseDouble(item_pricel_string); 
           
          Object quantity_value = quote_table.getValueAt(r,4); 
          String quantity_string = quantity_value.toString();
          if(quantity_string.isEmpty()){
              quantity_string = "1";
          }
          quantity = Double.parseDouble(quantity_string); 
          
          total_item_price = total * quantity;
          total_items_price = total_items_price + total_item_price;
      }
      
      totalvat = totalvat * vat;
            
      sub_total.setText(total_items_price+""); 
      total_items.setText(total_items_price+"");
      total_vat.setText(totalvat+""); 
      total_items_price = 0.0;
  }
  
  private void updateQuoteTable(String quantity,int r, int c){
      double total = 0;
      double total_updated = 0;
      double qty = 0;
      Object item_price = quote_table.getValueAt(r,5); 
      String item_pricel_string = item_price.toString();
      if(item_pricel_string.isEmpty()){
           item_pricel_string = "0";
       }
      total = Double.parseDouble(item_pricel_string);
      qty = Double.parseDouble(quantity);
      total_updated = total * qty;
      quote_table.setValueAt(total_updated, r, c);
  }
  
  public static void setQuoteStatus(){
      input_quote_status.setText(""+getQuoteStatus()); 
  }
  
  private static String getQuoteStatus(){
      String status = "";
      try{
              DB db = new DB(RunProgram.CONNECTION_MODE);
              status = db.getQuotationStatus(newquotation);
            }
          catch (Exception e1)
            {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "<html>"+e1+"</html>", "Error - Quote Not Generated", 1);
            }
      
      return status;
  }
  
  private static int getQuoteStatusID(String quote){
      int statusid = 0;
       try{
              DB db = new DB(RunProgram.CONNECTION_MODE);
              statusid = db.getQuotationStatus(quote);
            }
          catch (Exception e1)
            {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "<html>"+e1+"</html>", "Error - Quote Status Not Generated", 1);
            }
      return statusid;
  }
  
  private static int getNewQuote(){
       int newquote = 0;
       try{
              DB db = new DB(RunProgram.CONNECTION_MODE);
              newquote = db.get_last_record("quotation", "quotenumber");
            }
          catch (Exception e1)
            {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "<html>"+e1+"</html>", "Error - Quote Not Generated", 1);
            }
        newquote += 1;
        return newquote;
      
  }
   
  public static void setNewQuote() {
        input_quote.setText(""+getNewQuote());  
  }
  
  public void setItem(String itemcode){ 
       //Get Items
        try
        {
          db = new DB(RunProgram.CONNECTION_MODE);
          data  = db.getItemByCode(itemcode);
          listCategories = db.getCategories();
          listItemType   = db.getItemTypes(); 
          
          db.close();
        }
        catch (Exception e){ e.printStackTrace(); }
        
        //tmodel = new DefaultTableModel();
        
        for(int x =0;x<data.size();x++){
            boolean status;
            if(Integer.parseInt(data.get(x).get(9)) == 1){
                status = true;
            }else{ status = false; }
            String code    = data.get(x).get(1);
            String desc    = data.get(x).get(2);
            String type    = getItemTypeByID(Integer.parseInt(data.get(x).get(3)));
            String cat     = getCategoryByID(Integer.parseInt(data.get(x).get(4)));
            String sales   = data.get(x).get(5);
            String purch   = data.get(x).get(6);
            String qty     = data.get(x).get(7);
            String date    = data.get(x).get(8);
 
            String total   = "120";
            tmodel.addRow( new Object[] { code, desc, qty, sales, total} );
        }
       
        
        //Activate table on main screen
       // MainWindow.setActiveTable(quote_table);
        
    }
    
  private String getCategoryByID(int catid){
        String category = "";
        for(int x = 0;x<listCategories.size();x++){
            if(listCategories.get(x).getCatid() == catid){
                category = listCategories.get(x).getCatname();
            }
        }
        
        return category;
    }
   
  private String getItemTypeByID(int typeid){
        String item = "";
        for(int x = 0;x<listItemType.size();x++){
            if(listItemType.get(x).getTypeid() == typeid){
                item = listItemType.get(x).getType();
            }
        }
        
        return item;
    }  
       
  private void setDate(){
        //set quote start date
        calendar.setTime(quote_start_date);
        quote_start_date = calendar.getTime();
        qoute_date.setDate(quote_start_date); 
        
        //set quote expiry date
        calendar.setTime(quote_due_date);
        calendar.add(Calendar.DATE, 31);
        quote_due_date = calendar.getTime();
        quote_expiry_date.setDate(quote_due_date);
        
        //set date format
        qoute_date.setFormats(sdf); 
        quote_expiry_date.setFormats(sdf); 
  }
  
  private void search(){
           
      /*String textfield_description_item = search_textfield.getText();
      String sgl_string = "";

      try
      {
        DB db = new DB();
        
        if (!textfield_description_item.equals(""))
        {
          if (!sgl_string.equals("")) {
            sgl_string = sgl_string + " AND";
          }
          sgl_string = sgl_string + " Description like '%" + textfield_description_item + "%'";
        }
        
        data = db.setSearchTable(sgl_string, "data_store_01");
        db.close();
      }
      catch (Exception e_exception)
      {
        e_exception.printStackTrace();
      }   
      
      final SearchPopUpPanel sp = new SearchPopUpPanel();
      //sp.setTableData(data, header);
      sp.setLocation(quote_table.getLocation().x,quote_table.getLocation().y );
      
      if(sp != null)
        {    
            itemInternalFrame();
                
            Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener()
            {
               @Override
                public void eventDispatched(AWTEvent event) {

                    if(event instanceof MouseEvent)
                    {
                        MouseEvent m = (MouseEvent)event;
                        if(m.getID() == MouseEvent.MOUSE_CLICKED)
                        {
                            sp.setVisible(false);
                            //set checkdata
                            setCheckData(SearchPopUpPanel.locationdata,SearchPopUpPanel.descriptiondata,SearchPopUpPanel.currentdata );
                            Toolkit.getDefaultToolkit().removeAWTEventListener(this);
                        }
                    }
                    if(event instanceof WindowEvent)
                    {
                        WindowEvent we = (WindowEvent)event;
                        if(we.getID() == WindowEvent.WINDOW_DEACTIVATED || we.getID() == WindowEvent.WINDOW_STATE_CHANGED)
                        {
                            sp.setVisible(true);
                            //set checkdata
                            setCheckData(SearchPopUpPanel.locationdata,SearchPopUpPanel.descriptiondata,SearchPopUpPanel.currentdata );
                            Toolkit.getDefaultToolkit().removeAWTEventListener(this);
                        }
                    }
                }

            }, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.WINDOW_EVENT_MASK);

        }

      sp.setVisible(true);*/
    }
  
  private String createDate(String date){
       String formatedDate = "";
       String [] splitDate = date.split(" "); 
       
       String month [] = new String[13];
       month[0] = " ";
       month[1] = "Jan";
       month[2] = "Feb";
       month[3] = "Mar";
       month[4] = "Apr";
       month[5] = "May";
       month[6] = "Jun";
       month[7] = "Jul";
       month[8] = "Aug";
       month[9] = "Sep";
       month[10] = "Oct";
       month[11] = "Nov";
       month[12] = "Dec";
       
       formatedDate = splitDate[2];
       for(int y=0;y<month.length;y++){
          if(splitDate[1].equals(month[y])){
              formatedDate = formatedDate +"-"+ Arrays.asList(month).indexOf(month[y]);  
          } 
       }
      
       formatedDate = formatedDate + "-" +splitDate[5];
       return formatedDate;
    }
    
  private void getCustomerList(){
         Vector<Vector<String>> cust = new Vector<>();                 
         try{ 
             DB db = new DB(RunProgram.CONNECTION_MODE);
             cust = db.getCustomers();  
           }
        catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN CUSTOMER LIST",JOptionPane.ERROR_MESSAGE);}
                 
        String [] customers; 
        customers = new String [cust.size() + 2];
        
        customers[0] = "(None)";
        customers[1] = "(Add new customer)";
          
        for(int i=0;i < cust.size();i++){
              customers[2+i] = cust.get(i).elementAt(1); //customer name
        }
        
       customers_model = new DefaultComboBoxModel(customers); 
       drop_customers.setModel(customers_model); 
    }
    
  private void getAddressList(){
        
         try{ 
             DB db = new DB(RunProgram.CONNECTION_MODE);
             listAddress = db.getAdressTypeList();   
           }
        catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN ADRESS TYPE LIST",JOptionPane.ERROR_MESSAGE);}
        
        String [] types; 
        types = new String [listAddress.size() + 1];
        
        types[0] = "";
                  
        for(int i=0;i < listAddress.size();i++){
              types[1+i] = listAddress.get(i).getAddressdescription();
        }
        
       addresstype_model = new DefaultComboBoxModel(types); 
       drop_address.setModel(addresstype_model); 
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
       // categories_model.addElement(cat); 
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

        quotation_title = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        drop_customers = new javax.swing.JComboBox<>();
        input_contact = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        input_vat = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        input_phone = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        input_email = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        input_quote = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        drop_salesrep = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        input_quote_status = new javax.swing.JTextField();
        qoute_date = new org.jdesktop.swingx.JXDatePicker();
        quote_expiry_date = new org.jdesktop.swingx.JXDatePicker();
        jPanel3 = new javax.swing.JPanel();
        drop_address = new javax.swing.JComboBox<>();
        address1 = new javax.swing.JTextField();
        address2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        zipcode = new javax.swing.JTextField();
        country = new javax.swing.JTextField();
        province = new javax.swing.JTextField();
        city = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        quote_table = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        sub_total = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        total_vat = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        payment_terms = new javax.swing.JTextPane();
        jLabel20 = new javax.swing.JLabel();
        total_items = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(153, 153, 153)), javax.swing.BorderFactory.createEtchedBorder()));
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Customer Quotations");
        setPreferredSize(MainWindow.home_desktop.getPreferredSize());

        jPanel1.setBackground(java.awt.Color.white);

        jLabel3.setText("Contact Name");

        jLabel1.setText("Customer Name");

        drop_customers.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                drop_customersItemStateChanged(evt);
            }
        });

        jLabel15.setText("VAT Number");

        jLabel17.setText("Phone No.");

        jLabel21.setText("E-mail");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(drop_customers, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(input_contact, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(input_vat, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(input_phone, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(input_email, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(drop_customers, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(input_contact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(input_phone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(input_vat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(input_email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBackground(java.awt.Color.white);

        input_quote.setEditable(false);
        input_quote.setBackground(java.awt.Color.lightGray);
        input_quote.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        input_quote.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setText("Quote#");

        drop_salesrep.setToolTipText("");
        drop_salesrep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                drop_salesrepItemStateChanged(evt);
            }
        });

        jLabel16.setText("Sales Rep");

        jLabel4.setText("Date");

        jLabel5.setText("Expiry Date");

        jLabel6.setText("Status");

        input_quote_status.setEditable(false);
        input_quote_status.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        input_quote_status.setForeground(new java.awt.Color(27, 189, 18));
        input_quote_status.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        qoute_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qoute_dateActionPerformed(evt);
            }
        });
        qoute_date.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                qoute_datePropertyChange(evt);
            }
        });

        quote_expiry_date.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(drop_salesrep, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(input_quote, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(input_quote_status, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(qoute_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(quote_expiry_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(input_quote, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(qoute_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(quote_expiry_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(input_quote_status, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(drop_salesrep, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.setBackground(java.awt.Color.white);

        drop_address.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                drop_addressItemStateChanged(evt);
            }
        });

        jLabel8.setText("City");

        jLabel9.setText("Province/State");

        jLabel10.setText("Country");

        jLabel18.setText("Zip Code");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(drop_address, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(address2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(province, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(country, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(city, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(zipcode, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(address1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(drop_address, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(address1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(address2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(city)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(province, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(country, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(zipcode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(java.awt.Color.white);

        quote_table.setAutoCreateRowSorter(true);
        quote_table.setModel(new javax.swing.table.DefaultTableModel(data,header));
        jScrollPane1.setViewportView(quote_table);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setText("Customer Details");

        jLabel11.setText("Address");

        jLabel12.setText("Quote Details");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Sub Total:");

        sub_total.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sub_total.setText("0.0");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Total");

        total_vat.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        total_vat.setText("0.0");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setText("Payment Terms:");

        payment_terms.setToolTipText("Basics intructions for the customer. E.g Don't forgot to state your customer reference no.");
        jScrollPane2.setViewportView(payment_terms);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("0.00% VAT");

        total_items.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        total_items.setText("0.0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(quotation_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(total_vat, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(total_items, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sub_total, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(3, 3, 3)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(quotation_title, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(sub_total, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(total_items, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(total_vat, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(239, 239, 239))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
  protected void setCustomer(String cust_name){
       int custid = 0; 
       try{
             db   = new DB(RunProgram.CONNECTION_MODE);
             custid = db.get_field_value("customer", "custid", "custname = '"+cust_name+"'"); 
             data = db.getCustomer(custid);
             db.close();
             setData();
         }catch(Exception ex){
             ex.printStackTrace();
         }
        
    }
    
  private void setAddressType(String address_description){
        int addresstypeid = 0;
        try{
             db   = new DB(RunProgram.CONNECTION_MODE);
             addresstypeid = db.get_field_value("addresstype", "addresstypeid", "addressdescription = '"+address_description+"'"); 
             db.close();
         }catch(Exception ex){
             ex.printStackTrace();
         }
        
        for(int x = 0; x < data.size();x++){
           int current_cust_address_type_id = Integer.parseInt(data.get(x).elementAt(22));
           if(addresstypeid == current_cust_address_type_id){
              address1.setText(data.get(x).elementAt(16));
              address2.setText(data.get(x).elementAt(17));
              city.setText(data.get(x).elementAt(18));
              province.setText(data.get(x).elementAt(20));
              country.setText(data.get(x).elementAt(19)); 
              zipcode.setText(data.get(x).elementAt(21));
            }else{
              address1.setText("");
              address2.setText("");
              city.setText("");
              province.setText("");
              country.setText("");
              zipcode.setText(""); 
            }
        }      
    }

  private void getAddressType(String address_description){
    int addresstypeid = 0;
        try{
             db   = new DB(RunProgram.CONNECTION_MODE);
             addresstypeid = db.get_field_value("addresstype", "addresstypeid", "addressdescription = '"+address_description+"'"); 
             db.close();
         }catch(Exception ex){
             ex.printStackTrace();
         }
        
        for(int x = 0; x < data.size();x++){
           int current_cust_address_type_id = Integer.parseInt(data.get(x).elementAt(22));
           if(addresstypeid == current_cust_address_type_id){
              address1.setText(data.get(x).elementAt(16));
              address2.setText(data.get(x).elementAt(17));
              city.setText(data.get(x).elementAt(18));
              province.setText(data.get(x).elementAt(20));
              country.setText(data.get(x).elementAt(19)); 
              zipcode.setText(data.get(x).elementAt(21));
            }else{
              address1.setText("");
              address2.setText("");
              city.setText("");
              province.setText("");
              country.setText("");
              zipcode.setText(""); 
            }
        }        
  }  
  
  private void setData(){
                 
        input_contact.setText(data.get(0).elementAt(25));
        input_phone.setText(data.get(0).elementAt(27));
        input_vat.setText(data.get(0).elementAt(10));
        input_email.setText(data.get(0).elementAt(26)); 
        int data_size = data.size();
        
        //set category
        //String cat = "";
        //int catid = Integer.parseInt(data.get(0).elementAt(7));
        /*int cat_count = drop_categories.getItemCount();
        
        for(int x = 0; x < cat_count;x++){ 
            cat = getCategoryName(catid);
           String catvalue = drop_categories.getItemAt(x);
            if(catvalue.equals(cat)){
           //    drop_categories.setSelectedIndex(x);
            }
        }*/
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
      
        //set address type
        String type = "";
        int addresstypeid = Integer.parseInt(data.get(0).elementAt(22));
        int addresstype_count = drop_address.getItemCount();
        
        for(int y = 0; y < addresstype_count;y++){ 
            type = getAddressTypeName(addresstypeid);
            String addresstypevalue = drop_address.getItemAt(y);
            if( addresstypevalue.equals(type)){
                drop_address.setSelectedIndex(y);
            }
        }
        
         address1.setText(data.get(0).elementAt(16));
         address2.setText(data.get(0).elementAt(17));
         city.setText(data.get(0).elementAt(18));
         province.setText(data.get(0).elementAt(20));
         country.setText(data.get(0).elementAt(19));
         zipcode.setText(data.get(0).elementAt(21));
        
        //set checkboxes - invoice and active customer status
        int cust_state = Integer.parseInt(data.get(0).elementAt(11));
        int invoice = Integer.parseInt(data.get(0).elementAt(2));
        
       /* if(cust_state == 1){ customer_status.setSelected(true);
        }else{ customer_status.setSelected(false); }
        
        if(invoice == 1){ mail_invoice.setSelected(true);
        }else{ mail_invoice.setSelected(false); }*/
        
        /**
         * Set addresses (Physical & Delivery address)
         * 
         * Firstly, check if the customer has more than one address and set accordingly
         * 
         */
        
        /*for(int x = 0; x < data_size;x++){
           //int addresstype = Integer.parseInt(data.get(x).elementAt(26));
           if(addresstypeid == 2){
              physicalAddress_id = Integer.parseInt(data.get(x).elementAt(15));
              address1.setText(data.get(x).elementAt(16));
              address2.setText(data.get(x).elementAt(17));
              city.setText(data.get(x).elementAt(18));
              province.setText(data.get(x).elementAt(20));
              country.setText(data.get(x).elementAt(19));
              zipcode.setText(data.get(x).elementAt(21));
              physicalAddressExist = true; 
              //drop_address.
            }else if(addresstypeid == 3){
              deliveryAddress_id = Integer.parseInt(data.get(x).elementAt(15));
              /*address3.setText(data.get(x).elementAt(16));
              address4.setText(data.get(x).elementAt(17));
              city1.setText(data.get(x).elementAt(18));
              province1.setText(data.get(x).elementAt(20));
              country1.setText(data.get(x).elementAt(19));
              zipcode1.setText(data.get(x).elementAt(21));
              deliveryAddressExist = true;
            }
        }*/
        
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
        /*contactname.setText(data.get(0).elementAt(29));
        contactmobile.setText(data.get(0).elementAt(31));
        contacttelephone.setText(data.get(0).elementAt(32));
        contactmail.setText(data.get(0).elementAt(30));
        contactfax.setText(data.get(0).elementAt(33));
        contactwebsite.setText(data.get(0).elementAt(34));
        int constate = Integer.parseInt(data.get(0).elementAt(35));
        if(constate == 1){ contact_status.setSelected(true);         
        }else{ contact_status.setSelected(false);}*/
      
    }
    
  public void saveQuotation(){ 
        if((drop_customers.getSelectedItem().toString().equals("")) || input_contact.getText().equals("") || input_email.getText().equals("") || drop_address.getSelectedItem().toString().equals("")) 
        {
          JOptionPane.showMessageDialog(null, "Required fields missing: Customer Name, Contact name, Contact e-mail and Address", "ERROR - Inputs", 2); 
        }
        else if(address1.getText().equals("") || address2.getText().equals("") || zipcode.getText().equals("")){
          JOptionPane.showMessageDialog(null, "Customer Address missing.", "ERROR - Inputs", 2); 
        }
        else
        {   
            //Get customer details
            String customer = drop_customers.getSelectedItem().toString();
            int custid = Integer.parseInt(data.get(0).elementAt(8));
            String contact_name = input_contact.getText();
            String vat     = input_vat.getText();
            String tel = input_phone.getText();
            String email = input_email.getText();
            //Get Adress
            String address = drop_address.getSelectedItem().toString();
            int addresstype = Integer.parseInt(data.get(0).elementAt(22));
            String addr1  = address1.getText();
            String addr2  = address2.getText();
            String cty   =  city.getText();
            String prov  = province.getText();
            String countr = country.getText();
            String zip   = zipcode.getText();
            //Get Quote details
            String quote_number = input_quote.getText();
            String quote_status = input_quote_status.getText();
            int qstatus = getQuoteStatusID(quote_status);
            int quoteid = 0;
            String datecreated, expirydate, datemodified;
	    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    datecreated = formatter2.format(quote_start_date);
            expirydate  = formatter2.format(quote_due_date);
            String salesrep = drop_salesrep.getSelectedItem().toString();
            int salerepid = getSalesRepID(salesrep); 
            if(salerepid <= 0){
               refreshSalesRepresentativeList();
               salerepid = getSalesRepID(salesrep); 
            }
            
            //Get items from quotation table
            int rowCount = quote_table.getRowCount() - 1;
            int columnCount = quote_table.getColumnCount();
            /*String cellValue;
            for(int r = 0;r < rowCount;r++){
              for(int c = 2;c < columnCount;c++){
                  Object cellvalue = Quotations.this.quote_table.getValueAt(r, c);
                  //cellValue = (String) cellvalue;  //Note. You will get Double cannot be cast to String error
                  System.out.println(cellvalue);
              }    
            }*/
  
            try{
                db = new DB(RunProgram.CONNECTION_MODE);
                db.setCommit(false); 
                
                if(EDIT_MODE == 0){
                    //insert quotation
                    String insert_quotation = "INSERT INTO quotation (`quotenumber`,`custid`,`salesrep`,`quote_status`,`datecreated`,`enddate`)"+
                                             "VALUES ("+quote_number+","+custid+","+salerepid+","+qstatus+",'"+datecreated+"','"+expirydate+"')";
                    db.executeSQL(insert_quotation);
                    quoteid = db.get_field_value("quotation","id","quotenumber = '"+quote_number+"'");    
                    
                    //insert quotationline
                    String comma = ",";
                    boolean moreRecords = false;
                    String insert_quotationline = "INSERT INTO quotationline (`quoteid`,`itemcode`,`qty`,`totalprice`) VALUES ";
                    
                    if(rowCount == 1){
                        int r = 0;
                        String itemcode = (String) Quotations.this.quote_table.getValueAt(r, 2);
                        int qty = Integer.parseInt(Quotations.this.quote_table.getValueAt(r, 4).toString());
                        Object doublevalue =  Quotations.this.quote_table.getValueAt(r, 6);
                        String totalprice = doublevalue+"";
                        insert_quotationline = insert_quotationline + "("+quoteid+",'"+itemcode+"',"+qty+",'"+totalprice+"')";
                        db.executeSQL(insert_quotationline);
                    }else{
                        for(int r = 0;r < rowCount;r++){ 
                            String itemcode = (String) Quotations.this.quote_table.getValueAt(r, 2);
                            int qty = Integer.parseInt(Quotations.this.quote_table.getValueAt(r, 4).toString());
                            Object doublevalue =  Quotations.this.quote_table.getValueAt(r, 6);
                            String totalprice = doublevalue+"";
                            if(moreRecords){
                                insert_quotationline = insert_quotationline +comma+ "("+quoteid+",'"+itemcode+"',"+qty+",'"+totalprice+"')";
                            }else{
                                insert_quotationline = insert_quotationline + "("+quoteid+",'"+itemcode+"',"+qty+",'"+totalprice+"')";
                            }
                            
                            if(r < rowCount){
                                moreRecords = true;   
                             }
                          }
                        db.executeSQL(insert_quotationline); 
                    }
                    
                    
                    
                    
                   
                    
                    /* //conid = db.get_field_value("contact","conid"," name = '"+contname+"'"); *
                    //insert category info
                    //String insert_category_info = "INSERT INTO category_info (`catid`,`fieldid`,`data`)"+
                    //                              "VALUES ('"+catid+"',1,'"+custid+"')";
                    //db.executeSQL(insert_category_info);
                    //insert address
                    String insert_physical_address = "INSERT INTO address (`address1`,`address2`,`city`,`country`,`province`,`zipcode`,`addresstype`,`createdby`)"+
                                                        "VALUES ("+addr1+"','"+addr2+"','"+cty+"','"+countr+"','"+prov+"','"+zip+"',2,'"+getUserId()+"')";
                    
                    String insert_delivery_address = "INSERT INTO address (`address1`,`address2`,`city`,`country`,`province`,`zipcode`,`addresstype`,`createdby`)"+
                                                        "VALUES ('"+addr3+"','"+addr4+"','"+cty1+"','"+countr1+"','"+prov1+"','"+zip1+"',3,'"+getUserId()+"')";               
                     
                    if(insert_count == 2){
                      db.executeSQL(insert_physical_address);
                      physical_addressid = db.get_field_value("address","addressid","address1 = '"+addr1+"'");
                      db.executeSQL(insert_delivery_address);  
                      delivery_addressid = db.get_field_value("address","addressid","address1 = '"+addr3+"'");
                      
                      //insert customer info
                      String insert_customer_info = "INSERT INTO customer_info (`custid`,`einvoice`,`salesrepid`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                                   "VALUES ('" + custid + "','" + einvoice + "',"+salerepid+",'"+image+"',"+physical_addressid+","+conid+","+catid+")"+
                                                                   "       ('" + custid + "','" + einvoice + "',"+salerepid+",'"+image+"',"+delivery_addressid+","+conid+","+catid+")";
                      db.executeSQL(insert_customer_info);
                      
                    }else if(insert_count == 1){
                       if(addressToinsert == 2){
                           db.executeSQL(insert_physical_address); 
                            physical_addressid = db.get_field_value("address","addressid","address1 = '"+addr1+"'");
                            String insert_customer_info = "INSERT INTO customer_info (`custid`,`einvoice`,`salesrepid`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                          " VALUES ('" + custid + "','" + einvoice + "',"+salerepid+",'"+image+"',"+physical_addressid+","+conid+","+catid+")";
                           db.executeSQL(insert_customer_info);
                       }else if(addressToinsert == 3){
                           db.executeSQL(insert_delivery_address);  
                           delivery_addressid = db.get_field_value("address","addressid","address1 = '"+addr3+"'");
                           String insert_customer_info = "INSERT INTO customer_info (`custid`,`einvoice`,`salesrepid`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                          " VALUES ('" + custid + "','" + einvoice + "',"+salerepid+",'"+image+"',"+delivery_addressid+","+conid+","+catid+")";
                           db.executeSQL(insert_customer_info);
                       }
                    }*/

                }else{
                    /***************************************************************
                    * You are in EDIT MODE, use vector data to get element at the  *
                    * specified point to get various details about the customer    *
                    ***************************************************************
                    //update customer
                    custid = Integer.parseInt(data.get(0).elementAt(1));   
                    String update_customer = "UPDATE customer SET `custname` = '" + customer + "',`vatnumber` = '" + vat + "',`cust_state` = "+cust_state+",`datemodified` = '"+datemodified+"',`enddate` = '"+enddate+"'"+
                                             " WHERE `custid` = "+custid;
                    db.executeSQL(update_customer);    
                    //update contact
                    conid = Integer.parseInt(data.get(0).elementAt(24));    
                    /*String update_contact = "UPDATE contact SET `name` = '"+contname+"',`email` = '"+email+"',`mobile` = '"+mobile+"',`telephone` = '"+tel+"',`fax` = '"+fax+"',`website` = '"+web+"',`con_state` = "+contact_status+" ,`datemodified` = '"+datemodified+"',`enddate` = '"+enddate+"'"+
                                            " WHERE `conid` = "+conid;
                    db.executeSQL(update_contact);*
                    
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

                          String update_customer_info = "UPDATE customer_info SET `einvoice` = '" + einvoice + "',`salesrepid` = "+salerepid+",`imageid` = '"+image+"', `conid` = "+conid+", `catid` = "+catid+""+
                                                        " WHERE `custid` = "+custid+""+
                                                        "   AND `addressid` = "+deliveryAddress_id;
                          db.executeSQL(update_customer_info);
                          
                          db.executeSQL(insert_physical_address);
                          physical_addressid = db.get_field_value("address","addressid","address1 = '"+addr1+"'");
                          //insert customer info
                          String insert_customer_info = "INSERT INTO customer_info (`custid`,`einvoice`,`salesrepid`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                        " VALUES ('" + custid + "','" + einvoice + "',"+salerepid+",'"+image+"',"+physical_addressid+","+conid+","+catid+")";
                          db.executeSQL(insert_customer_info);
                        }else if(newAddressTypeToInsert == "Delivery"){
                          db.executeSQL(update_physical_address); 
                          
                          String update_customer_info = "UPDATE customer_info SET `einvoice` = '" + einvoice + "',`salesrepid` = "+salerepid+",`imageid` = '"+image+"', `conid` = "+conid+", `catid` = "+catid+""+
                                                         " WHERE `custid` = "+custid+""+
                                                         "   AND `addressid` = "+physicalAddress_id;
                          db.executeSQL(update_customer_info);
                          
                          db.executeSQL(insert_delivery_address);
                          delivery_addressid = db.get_field_value("address","addressid","address1 = '"+addr3+"'");
                          //insert customer info
                          String insert_customer_info = "INSERT INTO customer_info (`custid`,`einvoice`,`salesrepid`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                        " VALUES ('" + custid + "','" + einvoice + "',"+salerepid+",'"+image+"',"+delivery_addressid+","+conid+","+catid+")";
                          db.executeSQL(insert_customer_info);
                        }else if(newAddressTypeToInsert == "Both"){
                           db.executeSQL(insert_physical_address);
                           physical_addressid = db.get_field_value("address","addressid","address1 = '"+addr1+"'");
                           db.executeSQL(insert_delivery_address);  
                           delivery_addressid = db.get_field_value("address","addressid","address1 = '"+addr3+"'");
                           
                           String insert_customer_info = "INSERT INTO customer_info (`custid`,`einvoice`,`salesrepid`,`imageid`,`addressid`, `conid`, `catid`)"+
                                                                   "VALUES ('" + custid + "','" + einvoice + "',"+salerepid+",'"+image+"',"+physical_addressid+","+conid+","+catid+")"+
                                                                   "       ('" + custid + "','" + einvoice + "',"+salerepid+",'"+image+"',"+delivery_addressid+","+conid+","+catid+")";
                           db.executeSQL(insert_customer_info);
                        }else if(newAddressTypeToInsert == "None"){
                           db.executeSQL(update_physical_address);
                           db.executeSQL(update_delivery_address);          
                           
                           //update customer info
                           String update_customer_info = "UPDATE customer_info SET `einvoice` = '" + einvoice + "',`salesrepid` = "+salerepid+",`imageid` = '"+image+"', `conid` = "+conid+", `catid` = "+catid+""+
                                                         " WHERE `custid` = "+custid+""+
                                                         "   AND `addressid` = "+physicalAddress_id;
                           db.executeSQL(update_customer_info);

                           String update_customer_info2 = "UPDATE customer_info SET `einvoice` = '" + einvoice + "',`salesrepid` = "+salerepid+",`imageid` = '"+image+"', `conid` = "+conid+", `catid` = "+catid+""+
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
                              String update_customer_info = "UPDATE customer_info SET `einvoice` = '" + einvoice + "',`salesrepid` = "+salerepid+",`imageid` = '"+image+"', `conid` = "+conid+", `catid` = "+catid+""+
                                                         " WHERE `custid` = "+custid+""+
                                                         "   AND `addressid` = "+deliveryAddress_id;
                              db.executeSQL(update_customer_info);
                           }
                    }*/
                }
                
                db.setCommit(true);
                db.close();
                if(EDIT_MODE == 0){
                   JOptionPane.showMessageDialog(null, "<html>Quote #"+quote_number+" saved successfully</html>", "Confirmation", 1);
                }else{
                   JOptionPane.showMessageDialog(null, "<html>Quote #"+quote_number+" successfully updated</html>", "Confirmation", 1);
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
                    Logger.getLogger(Quotations.class.getName()).log(Level.SEVERE, null, ex);
                }
                  JOptionPane.showMessageDialog(null, e1, "ERROR - Failed to save Quotation", 2);
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
           /*if(address3.getText().isEmpty() && address4.getText().isEmpty() && city1.getText().isEmpty() && province1.getText().isEmpty() && country1.getText().isEmpty() && zipcode1.getText().isEmpty())
             {
               type = true;
             } */  
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
         //getCategoryList();
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
    
  private String getAddressTypeName(int addresstypeid){
       String adresstype = "";
       for(int x = 0;x < listAddress.size();x++){
           if(listAddress.get(x).getAddresstypeid().equals(addresstypeid)){ 
               adresstype = listAddress.get(x).getAddressdescription();
           }
       } 
       return adresstype;
    }
    
  public void refreshSalesRepresentativeList(){
         getSalesRepList();
    }
    
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

    private void drop_customersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_drop_customersItemStateChanged
        if(evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
          Object item = evt.getItem();
          if(item.equals("(Add new customer)")){ 
              MainWindow.customerInternalFrame(); 
              Customers.customer_title.setText("New Customer"); 
          }else if(item.equals("(None)")){
             //Do nothing  
          }else{
             setCustomer(item.toString());
          }
       }
    }//GEN-LAST:event_drop_customersItemStateChanged

    private void drop_addressItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_drop_addressItemStateChanged
         if(evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
          Object item = evt.getItem();
          if(item.equals("")){
             //Do nothing  
          }else{
             setAddressType(item.toString());
          }
       }
    }//GEN-LAST:event_drop_addressItemStateChanged

    private void qoute_datePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_qoute_datePropertyChange
       String new_date = evt.getNewValue().toString();
       date_counter_check = date_counter_check + 1;
       if(date_counter_check == 1 || date_counter_check >= 4){
           new_quote_date = createDate(new_date); 
           calendar = Calendar.getInstance();
           try { 
               calendar.setTime(sdf.parse(new_quote_date));
           } catch (ParseException ex) {
               Logger.getLogger(Quotations.class.getName()).log(Level.SEVERE, null, ex);
           }
           calendar.add(Calendar.DATE, 31);
           quote_due_date = calendar.getTime();
           quote_expiry_date.setDate(quote_due_date);

           //set date format
           quote_expiry_date.setFormats(sdf); 
           date_counter_check = 1;
       }
            
    }//GEN-LAST:event_qoute_datePropertyChange

    private void qoute_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qoute_dateActionPerformed

    }//GEN-LAST:event_qoute_dateActionPerformed

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
            java.util.logging.Logger.getLogger(Quotations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Quotations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Quotations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Quotations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Quotations().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address1;
    private javax.swing.JTextField address2;
    private javax.swing.JTextField city;
    private javax.swing.JTextField country;
    private javax.swing.JComboBox<String> drop_address;
    private javax.swing.JComboBox<String> drop_customers;
    private javax.swing.JComboBox<String> drop_salesrep;
    private javax.swing.JTextField input_contact;
    private javax.swing.JTextField input_email;
    private javax.swing.JTextField input_phone;
    private static javax.swing.JTextField input_quote;
    private static javax.swing.JTextField input_quote_status;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextPane payment_terms;
    private javax.swing.JTextField province;
    private org.jdesktop.swingx.JXDatePicker qoute_date;
    public static javax.swing.JLabel quotation_title;
    private org.jdesktop.swingx.JXDatePicker quote_expiry_date;
    public static javax.swing.JTable quote_table;
    private static javax.swing.JLabel sub_total;
    private static javax.swing.JLabel total_items;
    private static javax.swing.JLabel total_vat;
    private javax.swing.JTextField zipcode;
    // End of variables declaration//GEN-END:variables

    
  class ButtonRenderer extends JButton implements TableCellRenderer {

  public ButtonRenderer() {
    setOpaque(true);
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
    boolean isSelected, boolean hasFocus, int row, int column) {
    if (isSelected) {
      setForeground(table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    } else {
      setForeground(table.getForeground());
      setBackground(UIManager.getColor("Button.background"));
    }
    setText((value == null) ? "" : value.toString());
    return this;
  }
}

/**
 * @version 1.0 11/09/98
 */

class ButtonEditor extends DefaultCellEditor {
  protected JButton button;

  private String label;

  private boolean isPushed;

  public ButtonEditor(JCheckBox checkBox) {
    super(checkBox);
    button = new JButton();
    button.setOpaque(true);        
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
      }
    });
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {
    if (isSelected) {
      button.setForeground(table.getSelectionForeground());
      button.setBackground(table.getSelectionBackground());
    } else {
      button.setForeground(table.getForeground());
      button.setBackground(table.getBackground());
    }
    label = (value == null) ? "" : value.toString();
    button.setText(label);
    isPushed = true;
    return button;
  }

  public Object getCellEditorValue() {
    if (isPushed) {
       if(button.getText().equals("+")){ 
         final QuotationItems sp = new QuotationItems(new JFrame(), true);
         int x, y;
         x = button.getLocationOnScreen().x;
         y = button.getLocationOnScreen().y + (button.getHeight());
         //System.out.println("Width: "+x+" Height: "+y);
         disableCellEditing();
         sp.setLocation(new Point(x,y)); 
         sp.setVisible(true);    
       }else if(button.getText().equals("-")){
           int rowSelected = quote_table.getSelectedRow();
           int colCount = quote_table.getColumnCount();
           boolean delete = true;
           
           /*
            If the row selected is a default row, i.e has an add and remove button only, then dont remove the row from the table model
           */
           for(int c = 2;c < colCount;c++){
             if(quote_table.getModel().getValueAt(rowSelected,c).toString().isEmpty()){
               delete = false;
             }
           }
           if(delete){
             tmodel.removeRow(rowSelected);    
           }
           calculateTotals();
       }
    }
    isPushed = false;
    return new String(label);
  }

  public boolean stopCellEditing() {
    isPushed = false;
    return super.stopCellEditing();
  }
  
  /*public boolean isCellEditable(int row, int column){
      if(column == 4){
         return true; 
      }
      return false;
  }*/
  
  protected void fireEditingStopped() {
    super.fireEditingStopped();
  }
}
    
    
   /** public class MyTableModel extends DefaultTableModel {

    public MyTableModel() {
      //super(new String[]{"Field", "Data Type", "Primary Key"}, 0);
      //super(new String[]{"Code", "Description", "Type","Category", "Sales Price", "Purch. Price.", "Qty On Hand","Date Created" ,"Active"}, 0);
      super(new String[]{"Add Item", "Item", "Description", "Quantity","Price", "Discount", "Disc%", "Total"}, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      Class clazz = String.class;
      switch (columnIndex) {
        case 0:
          clazz = String.class;
          break;
        case 8:
          clazz = Boolean.class;
          break;
      }
      return clazz;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
      return true;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
      if (aValue instanceof Boolean && column == 2) {
        System.out.println(aValue);
        Vector rowData = (Vector)getDataVector().get(row);
        rowData.set(2, (boolean)aValue);
        fireTableCellUpdated(row, column);
      }
      else{
          Vector rowData = (Vector)getDataVector().get(row);
          rowData.set(column, aValue);
          fireTableCellUpdated(row, column);
         
      }
    }

  }
    
    private boolean hasSpace(String table_field)     { 
       boolean answer; 
       if(table_field.contains(" ")){ 
           answer = true;
       }else{
         answer =false;
    }
     return answer;  
   }

}


class KComboBoxRenderer extends JComboBox implements TableCellRenderer {
  public KComboBoxRenderer(String[] items) {
    super(items);
  }
  
  public void actionPerformed(ActionEvent e) {
     System.out.println("ComboBox Action Performed");   
  }
  
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
      boolean hasFocus, int row, int column) {
    if (isSelected) {
      setForeground(table.getSelectionForeground());
      super.setBackground(table.getSelectionBackground());
    } else {
      setForeground(table.getForeground());
      setBackground(table.getBackground());
    }
    setSelectedItem(value);
    return this;
  }

}

class KComboBoxEditor extends DefaultCellEditor {
  public KComboBoxEditor(String[] items) {
    super(new JComboBox(items));
  }**/
   
}
