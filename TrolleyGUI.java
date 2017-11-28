/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory_system;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.*;


 
public class TrolleyGUI extends JDialog implements ActionListener,PropertyChangeListener
{
    
    private static int id;
    private static int role;
    private static String rolename;
    Date logtime;
    public static int []  rowSelected;
    private Vector<Vector<Object>> data; 
    private Vector<String> header;
    private Object ID;
    private DefaultTableModel model;
    TableColumn tc;
    private JTextField textFieldColor;
    Date datehelp;
    private static String date,dueDate;
    SimpleDateFormat sdfFrom = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    SimpleDateFormat sdfTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Trolley item;
    public static int basketCount = 0;
    public static ArrayList<Trolley> trolleyItems = new ArrayList<Trolley>();
    public static ArrayList<ConstructMail> mailItems = new ArrayList<ConstructMail>();
    public static String facilitator ;
    public static String faci;
    public static String storeman;   
    public static String company_id; 
    public static String fname,lname,learner; 
    public static String email;
    public static String f_email;
    public static String on_copy;
    public static String recipient;
    public static String subject;
    public static String lmessage;
    public static String fmessage;
    public static String results;
    private boolean sendmail;
    private String screen = "TrolleyGUI";
    private JOptionPane optionPane;
    private String cancelBtn = "Cancel";
    public Object value = "";
    protected static String [] listFaci;
    protected static String [] listStoreman;
    public static ArrayList<String> storeList;
    public static ArrayList<SendEmail> emailList = new ArrayList<SendEmail>();
    
    
    public TrolleyGUI(JFrame aFrame,int idin, int roleIn) 
    {  
        super(aFrame, true);
        id      = idin;
        role = roleIn;
        logtime = new Date();
        datehelp = null;
        model = new javax.swing.table.DefaultTableModel(data,header);
        //create headers for search table
        header = new Vector<String>();
        header.add("ItemID");
        header.add("Store");
        header.add("Location / Bin");
        header.add("Description");
        header.add("Current");
        header.add("Creator");
        header.add("#Quantity");
        header.add("Trolley");
       
        initComponents();
        this.getContentPane().setBackground(Color.white);  //sets layout background color to white
        
        try
        {

            ConnectDatabase database = new ConnectDatabase();	
            storeList = database.getStore(true,"All",0);
            String [] storeArray;
            storeArray = new String[storeList.size()];
            
            for(int x=0; x < storeList.size();x++){
                if(storeList.get(x) == null){ 
                   // System.out.println("Empty");
                }else{
                   storeArray[x] = storeList.get(x); 
                   comboBox_store.addItem(storeArray[x]);    
                }
            }  
        }
        catch(Exception e1)
        {JOptionPane.showMessageDialog(null,e1,"ERROR IN STORE LOCATION",JOptionPane.ERROR_MESSAGE);}
        
         //get role name
        try{ConnectDatabase database = new ConnectDatabase();rolename = database.getRole(role); }
        catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"Cant get role name",JOptionPane.ERROR_MESSAGE);}
        
        switch (rolename) {
            case "storeman":
                //Storeman list
                /*try{ConnectDatabase database = new ConnectDatabase();listStoreman = database.getListStoreman(id);String [] ccListStoreArray;ccListStoreArray = new String [listStoreman.length];
                    for(int i=0;i < listStoreman.length;i++){ ccListStoreArray[i] = listStoreman[i]; storemanDrop.addItem(ccListStoreArray[i]); }}
                catch(Exception e1){JOptionPane.showMessageDialog(null,e1,"ERROR IN STOREMAN LIST",JOptionPane.ERROR_MESSAGE);	}*/
                //facilitator list
                /*try{ConnectDatabase database = new ConnectDatabase();listFaci = database.getListFacilitator(id); String [] ccListFaciArray; ccListFaciArray = new String [listFaci.length];
                    for(int i=0;i < listFaci.length;i++){ ccListFaciArray[i] = listFaci[i]; facilitatorDrop2.addItem(ccListFaciArray[i]);}}
                catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN FACILITATOR LIST",JOptionPane.ERROR_MESSAGE);}*/
                break;
            case "facilitaotor":
                /*try{ConnectDatabase database = new ConnectDatabase();listStoreman = database.getListStoreman();String [] ccListStoreArray;ccListStoreArray = new String [listStoreman.length];
                   for(int i=0;i < listStoreman.length;i++){ ccListStoreArray[i] = listStoreman[i]; storemanDrop.addItem(ccListStoreArray[i]); }}
               catch(Exception e1){JOptionPane.showMessageDialog(null,e1,"ERROR IN STOREMAN LIST",JOptionPane.ERROR_MESSAGE);	}*/
                break;
            case "administrator":
                //storeman list
                /*try{ConnectDatabase database = new ConnectDatabase();listStoreman = database.getListStoreman();String [] ccListStoreArray;ccListStoreArray = new String [listStoreman.length];
                   for(int i=0;i < listStoreman.length;i++){ ccListStoreArray[i] = listStoreman[i]; storemanDrop.addItem(ccListStoreArray[i]); }}
               catch(Exception e1){JOptionPane.showMessageDialog(null,e1,"ERROR IN STOREMAN LIST",JOptionPane.ERROR_MESSAGE);	}*/
                //facilitator list
               // try{ConnectDatabase database = new ConnectDatabase();listFaci = database.getListFacilitator(id); String [] ccListFaciArray; ccListFaciArray = new String [listFaci.length];
                 //   for(int i=0;i < listFaci.length;i++){ ccListFaciArray[i] = listFaci[i]; facilitatorDrop.addItem(ccListFaciArray[i]);}}
                //catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN FACILITATOR LIST",JOptionPane.ERROR_MESSAGE);} 
                break;
             case "superuser":
                //storeman list
                /*try{ConnectDatabase database = new ConnectDatabase();listStoreman = database.getListStoreman();String [] ccListStoreArray;ccListStoreArray = new String [listStoreman.length];
                   for(int i=0;i < listStoreman.length;i++){ ccListStoreArray[i] = listStoreman[i]; storemanDrop.addItem(ccListStoreArray[i]); }}
               catch(Exception e1){JOptionPane.showMessageDialog(null,e1,"ERROR IN STOREMAN LIST",JOptionPane.ERROR_MESSAGE);	}*/
                //facilitator list
                //try{ConnectDatabase database = new ConnectDatabase();listFaci = database.getListFacilitator(id); String [] ccListFaciArray; ccListFaciArray = new String [listFaci.length];
                  //  for(int i=0;i < listFaci.length;i++){ ccListFaciArray[i] = listFaci[i]; facilitatorDrop.addItem(ccListFaciArray[i]);}}
                //catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN FACILITATOR LIST",JOptionPane.ERROR_MESSAGE);} 
                break;   
                
        }
        
        MainWindow.items_count_label.setText(""+basketCount); 
        
        setTitle("Trolley | Multiple Item issue");
        Object[] options = {cancelBtn};

        //Create the JOptionPane.
        optionPane = new JOptionPane(this.getContentPane(),JOptionPane.PLAIN_MESSAGE,JOptionPane.CANCEL_OPTION,null,options,options[0]);
        optionPane.setBackground(new Color(226,223,214)); 
        
        //Make this dialog display it.
        setContentPane(optionPane);

        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                    optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
            }
        });

        //Register an event handler that reacts to option pane state changes.
           optionPane.addPropertyChangeListener(this);
    } 

        
    public void actionPerformed(ActionEvent e) {
        optionPane.setValue(cancelBtn);
    }
     
    /*This method reacts to state changes in the option pane. */
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();    
        
        if (isVisible() && (e.getSource() == optionPane) && (JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) 
        {
          value = optionPane.getValue();
         
          if (value == JOptionPane.UNINITIALIZED_VALUE)
            {
                //ignore reset
                return;
            }
        }
        
        clearAndHide();
            
        
    }
    
       /* This method clears the dialog and hides it. */
    public void clearAndHide() {
        setVisible(false);
    } 
    
    public TrolleyGUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        comboBox_store = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        textfield_location = new javax.swing.JTextField();
        textfield_description = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        items_count_label = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        facilitatorDrop = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        storemanDrop = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        compid_textfield = new javax.swing.JTextField();
        issue_error_msg_label = new javax.swing.JLabel();
        alert_image_display = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        searchTable = new javax.swing.JTable();

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Trolley | Multiple Item issue");
        setLocationByPlatform(true);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Items", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        jLabel6.setBackground(java.awt.Color.white);
        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setText("Store");

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel4.setText("Location");

        textfield_description.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        textfield_description.setForeground(new java.awt.Color(153, 153, 153));
        textfield_description.setText("Search Item...");
        textfield_description.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textfield_descriptionMouseClicked(evt);
            }
        });
        textfield_description.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textfield_descriptionKeyTyped(evt);
            }
        });

        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/search2.png"))); // NOI18N
        searchButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/search.png"))); // NOI18N
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(comboBox_store, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(textfield_location, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(textfield_description, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchButton)
                .addContainerGap(118, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboBox_store, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(textfield_location, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textfield_description)
                    .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Item Basket", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 12))); // NOI18N

        items_count_label.setBackground(new java.awt.Color(55, 128, 196));
        items_count_label.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        items_count_label.setForeground(new java.awt.Color(255, 255, 255));
        items_count_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        items_count_label.setText(""+basketCount);
        items_count_label.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(56, 119, 196), null));
        items_count_label.setOpaque(true);
        items_count_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                items_count_labelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                items_count_labelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                items_count_labelMouseExited(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Items in trolley");

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/issue.png"))); // NOI18N
        jButton1.setToolTipText("Issue out");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/trolley.png"))); // NOI18N
        jButton3.setToolTipText("Trolley contents");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(items_count_label, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(items_count_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel14.setText("Facilitator");

        facilitatorDrop.setToolTipText("");

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel13.setText("Storeman");

        storemanDrop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storemanDropActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel3.setText("Learner Company ID");

        compid_textfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compid_textfieldActionPerformed(evt);
            }
        });

        issue_error_msg_label.setBackground(new java.awt.Color(255, 255, 255));
        issue_error_msg_label.setForeground(new java.awt.Color(255, 0, 0));

        searchTable.setModel(new javax.swing.table.DefaultTableModel(data,header));
        searchTable.setAlignmentX(11.0F);
        searchTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                searchTableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(searchTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(alert_image_display, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(issue_error_msg_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(90, 90, 90))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(facilitatorDrop, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(storemanDrop, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(compid_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(compid_textfield, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(facilitatorDrop, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(storemanDrop, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(alert_image_display, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(issue_error_msg_label, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void setItemsCount(int val)
    {
       //Update Quantity label on the gui
       items_count_label.setText(""+val);  
    }
    public static void addToBasket(int itemId,String store,String location,String description,String currentStock, String quantity){
        int stock = Integer.parseInt(currentStock); 
        int quant = 1; 
        /**facilitator = (String) facilitator_comboBox.getSelectedItem();
        storeman    = (String) storeman_comboBox.getSelectedItem();
        company_id  = (String) compid_textfield.getText();
        int compid = Integer.parseInt(company_id);
        int learner_id;
       
        try{
             ConnectDatabase database = new ConnectDatabase();
             learner_id = database.getID(compid);
             fname = database.getName(learner_id); 
             lname = database.getSurname(learner_id); 
             learner = fname+" "+lname;
             database.setCloseDatabase();
            }catch(Exception ex){System.out.println(ex);}*/
        
        if(quantity == null)
        {
           if(stock < 1)
            {
                JOptionPane.showMessageDialog(null,"<html>Cannot add item to trolley.<br> There are #"+currentStock+" "+description+"(s) in Store</html>","Item not available",1);
            }
           else if(stock < quant)
            {
                JOptionPane.showMessageDialog(null,"<html>Cannot add item to trolley.<br> There are #"+currentStock+" "+description+"(s) in Store available</html>","Not enough items",1);
            }
           else 
            {
                //add to trolley
                trolleyItems.add(new Trolley(itemId,store,location,description,currentStock,"1")); 
                basketCount = basketCount + quant;
               // mailItems.add(new ConstructMail(description,location,1)); 
             }    
        }
        else 
        {
            quant = Integer.parseInt(quantity);
            if(stock < 1)
             {
                JOptionPane.showMessageDialog(null,"<html>Cannot add item to trolley.<br> There are #"+currentStock+" "+description+"(s) in Store</html>","Item not available",1);
             }
            else if(stock < quant)
             {
                JOptionPane.showMessageDialog(null,"<html>Cannot add item to trolley.<br> There are #"+currentStock+" "+description+"(s) in Store available</html>","Not enough items",1);
             }
            else 
             {
                //addd to trolley
                //item = new Trolley(itemId,store,location,description,currentStock,quantity);
                trolleyItems.add(new Trolley(itemId,store,location,description,currentStock,quantity)); 
                basketCount = basketCount + quant;
               // mailItems.add(new ConstructMail(description,location,Integer.parseInt(quantity)));//(new ConstructMail(description,location,Integer.parseInt(quantity),facilitator,storeman,learner,compid,date) //create e-mail with all items issued out
             } 
        }
        
         items_count_label.setText(""+basketCount); 
    }   
    
    public static void removeFromBasket(int row){
        trolleyItems.remove(row);
        basketCount -= 1;
        items_count_label.setText(""+basketCount); 
    }
    
    
    private void searchTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTableMousePressed
        // mouse listener on row selected
     if ((evt.getClickCount() == 2) && (!evt.isMetaDown()))
       {
        int row = TrolleyGUI.this.searchTable.getSelectedRow();
        String locationdata = null;
        String descriptiondata = null;
        String storedata = null;
        String currentdata = null;
       
        try
        {
          ConnectDatabase database = new ConnectDatabase();

          String location = (String)TrolleyGUI.this.searchTable.getValueAt(row, 2);
          int ID = database.getID(location);
          locationdata = database.getCheckData(ID, "`Location / Bin`");
          descriptiondata = database.getCheckData(ID, "Description");
          storedata = database.getCheckData(ID, "Store");
          currentdata = database.getCheckData(ID, "Current");

          MainWindow main = new MainWindow( UserLog.id,UserLog.role, UserLog.logfirst);
          this.setVisible(false);
          main.setVisible(true);
          //main.setCheckData(locationdata, descriptiondata, storedata, currentdata);
          database.setCloseDatabase();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
        
        
    }//GEN-LAST:event_searchTableMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
       if ((compid_textfield.getText().equals("")))
      {
        compid_textfield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),2));  
        alert_image_display.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png"))); 
        issue_error_msg_label.setText("Warning: Please type in a company id on the highlighted box!"); 
      }
      else
      {
              
       facilitator = (String) facilitatorDrop.getSelectedItem();
       storeman    = (String) storemanDrop.getSelectedItem();
       company_id  = (String) compid_textfield.getText();
       String group;
       fname = "";
       lname = "";
       group   = "";
       int level,learner_id;
       level = 0;
       int compid = Integer.parseInt(company_id);

        try 
        {
          ConnectDatabase database = new ConnectDatabase();
          learner_id = database.getID(compid);
          fname = database.getName(learner_id); 
          lname = database.getSurname(learner_id); 
          learner = fname+" "+lname;
          group   = database.getGroup(learner_id); 
          level   = database.getLevel(learner_id);
          database.setCloseDatabase();
          
        } 
        catch(Exception ex)
        {
            System.out.println(ex);
        }
        
  
        //Set date
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat fReturnDate = new SimpleDateFormat("dd-MM-yyyy");
        String returnDate,returnTime;
        Date currentTime = new Date();
                        
        date = formatter.format(currentTime); 
        returnDate = fReturnDate.format(currentTime);
        returnTime = "16:00:00";
        dueDate = returnDate+" "+returnTime;
        
        
       
        
        // Issue verification
        // Display learner photo, name, company id and item descrip.
        ImageIcon icon = null;  
          try 
          {
            icon = new ImageIcon(new java.net.URL("http://haf0gau01.w9/photos/"+compid+".jpg"));
          }
          catch (MalformedURLException ex)
          {
              Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
          }     
        
        //Resize the image icon
        java.awt.Image img = icon.getImage();
        BufferedImage bi = new BufferedImage(125,125,BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        g.drawImage(img,0,0, 125, 125,null);
        ImageIcon newIcon = new ImageIcon(bi);
        String message = "<html>Issue <b><i>#"+basketCount+"</i></b> item(s) to <u>"+fname+"</u>";
        
        //Change dialog box's background color
        UIManager.put("OptionPane.background", Color.white);
        UIManager.put("Panel.background", Color.white);
         
        int answer = JOptionPane.showConfirmDialog(null, message, "Issue Confirmation", 0, 1, newIcon);
        
        if (answer == 0)
            {
             
             for(int x = 0;x < trolleyItems.size();x++)
              {
                //Add to trolley basket
                //System.out.println((trolleyItems.get(x)).toString());  
               
                System.out.println(trolleyItems.get(x).getItem_id()); 
                String fields = ",`Quantity`,`Date`,`Learner`,`Company_id`,`Group`,`level`,`Storeman`,`Facilitator`";
                String values = ",'" + trolleyItems.get(x).getQuantity() + "',#" + date + "# ,'" + fname + "','" + compid + "', '" + group + "','" + level + "','" + storeman + "','" + facilitator + "'"; 
            
                int intquantity = 0;
                intquantity = Integer.parseInt(trolleyItems.get(x).getQuantity());
                try
                {
                  ConnectDatabase database = new ConnectDatabase();
                  int ID = database.getID(trolleyItems.get(x).getLocation());
                  database.setNewDigitValue(ID, "Current", intquantity, 0);

                    if (database.getStore(trolleyItems.get(x).getLocation()).equals("Consumable store"))
                    {
                      database.setNewConsumableQuantity(ID, intquantity, 0);
                      String batch = "INSERT INTO consumables_issue_list (`Name`,`company_id`,`Item Number`,`Consumable Description`,`Quantity`,`Date OUT`,`Location`,`Storeman`,`Facilitator`) VALUES ('" + fname + "','" + compid + "'," + ID + ",'" + trolleyItems.get(x).getDescription() + "', " + intquantity + ",#" + date + "#, '" + trolleyItems.get(x).getLocation() + "', '" + storeman + "','" + facilitator + "')";
                      database.setBatch(batch);
                    }
                    else if (database.getStore(trolleyItems.get(x).getLocation()).equals("Equipment store"))
                    {
                      database.setNewEquipmentQuantity(ID, intquantity, 0);
                      String batch = "INSERT INTO equipment_issue_list (`Name`,`company_id`,`Item Number`,`Equipment Description`,`Quantity`,`Date OUT`,`Location`,`Storeman`, `Facilitator`) VALUES ('" + fname + "','" + compid + "'," + ID + ",'" + trolleyItems.get(x).getDescription() + "', " + intquantity + ",#" + date + "#, '" + trolleyItems.get(x).getLocation() + "', '" + storeman + "','" + facilitator + "')";
                      database.setBatch(batch);
                    }
                    else if (database.getStore(trolleyItems.get(x).getLocation()).equals("Tool store"))
                    {
                      database.setNewToolQuantity(ID, intquantity, 0);
                      String batch = "INSERT INTO tool_issue_list (`Name`,`company_id`,`Item Number`,`Tool Description`,`Quantity`,`Date OUT`,`Location`,`Storeman`,`Facilitator`) VALUES ('" + fname + "','" + compid + "'," + ID + ",'" + trolleyItems.get(x).getDescription() + "', " + intquantity + ",#" + date + "#, '" + trolleyItems.get(x).getLocation() + "', '" + storeman + "','" + facilitator + "')";
                      database.setBatch(batch);
                    }
                    
                    alert_image_display.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/email_success.png")));
                    issue_error_msg_label.setText("Item(s) successfully issued.");
                    issue_error_msg_label.setForeground(Color.green); 
                    database.setListToListItem(ID, fields, values, "issue_list_01"); 
                    database.setCloseDatabase();
                    sendmail = true;
                }
                catch (Exception e1)
                {
                  sendmail = false;  
                  JOptionPane.showMessageDialog(null, e1, "Verify Location", 2);
                }   
              }
                
               if(sendmail)
               {  
                 //Add items to table list of items.
                 ConstructMail cm = new ConstructMail();
                 cm.setFacilitator(facilitator);
                 cm.setStoreman(storeman);
                 cm.setLearner(learner);
                 cm.setCompany_id(compid); 
                 cm.setDate(date);
                 cm.setReturnDate(dueDate);
                 lmessage = cm.MultipleItemsNotificationLearner(learner);
                 fmessage = cm.MultipleItemsNotificationFacilitator(facilitator);
                 
                 
                //prepare e-mail notification for learner
                try
                {
                 ConnectDatabase database = new ConnectDatabase();      

                 subject = "ISSUED ITEMS - "+company_id;
                 recipient = database.getString("SELECT `e_mail` FROM learner_list WHERE `company_id` LIKE "+company_id); 
                 String facil [] = facilitator.split(" ");
                 f_email   = database.getString("SELECT `e_mail` FROM facilitator WHERE `name` = '"+facil[0]+"' AND `surname` = '"+facil[1]+"'");
                 email     = "invt_store_program@bmw.co.za"; //database.getString("SELECT `e_mail` FROM users WHERE `company_id` LIKE "+id); 
                 on_copy   = "";//database.getString(""); 
                 }
                catch(Exception e1){ e1.printStackTrace();}
             
                emailList.add(new SendEmail(recipient,email,on_copy,lmessage,subject));
                emailList.add(new SendEmail(f_email,email,"",fmessage,subject));
                
                //Send e-mail notification(receipt) to learner
                new Worker().execute();     
                
                /*In backgroud, send facilitator an e-mail
                SendEmail mail = new SendEmail(f_email,email,email,fmessage,subject);
                String state = mail.Send();

                if("success".equals(state)){
                    alert_image_display.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/email_success.png")));
                    issue_error_msg_label.setText(facilitator+" notified successfully.");
                    issue_error_msg_label.setForeground(Color.green); 
                }
                else{
                    alert_image_display.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
                    issue_error_msg_label.setText("Failed to notify "+facilitator+".");
                }*/
               }
              //JOptionPane.showMessageDialog(null, "<html>You have succesfully issued #"+basketCount+" items to " + fname + "</html>", "Issue Confirmation", 1);
             //Now clear the basket's count value and basket/trolley data
             //basketCount = 0;
             //trolleyItems.clear();
             
             
             
            }
        else
            {
             //do nothing   
            }
      } 
      
    }//GEN-LAST:event_jButton1ActionPerformed

    private void compid_textfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compid_textfieldActionPerformed
        compid_textfield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));  
        alert_image_display.setIcon(new javax.swing.ImageIcon(getClass().getResource(""))); 
        issue_error_msg_label.setText(""); 
        
         String faci_name = "";
        int learner_id = 0, faci_id = 0; 
        learner_id = Integer.parseInt(compid_textfield.getText().toString());
        facilitatorDrop.removeAllItems(); //clear all items
        //get learner comp id, get learners facilitator id and facilitator full name of the learner
        try{ConnectDatabase database = new ConnectDatabase();
            faci_id = database.getLearnersFacilitatorsID(learner_id);
            faci_name = database.getFacilitator(faci_id);
        }catch(Exception ex){ex.printStackTrace();}  
        //populate facilitator drop down with the learners facilitator
        String [] ccListFaciArray;
        ccListFaciArray = new String [1];
        for(int i=0;i < ccListFaciArray.length;i++)
        {
          ccListFaciArray[i] = faci_name; 
          facilitatorDrop.addItem(ccListFaciArray[i]);
        }    
    }//GEN-LAST:event_compid_textfieldActionPerformed

    private void items_count_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_items_count_labelMouseClicked
        items_count_label.setText(""+basketCount);
    }//GEN-LAST:event_items_count_labelMouseClicked

    private void items_count_labelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_items_count_labelMouseEntered
        items_count_label.setBackground(new java.awt.Color(56, 119, 196));
        
    }//GEN-LAST:event_items_count_labelMouseEntered

    private void items_count_labelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_items_count_labelMouseExited
        items_count_label.setBackground(new java.awt.Color(55,128,196));
    }//GEN-LAST:event_items_count_labelMouseExited

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
         TheTrolley window = new TheTrolley(id,role,screen);
         this.setVisible(false);
         window.setVisible(true); 
    }//GEN-LAST:event_jButton3ActionPerformed

    private void textfield_descriptionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textfield_descriptionMouseClicked
        textfield_description.setText("");
    }//GEN-LAST:event_textfield_descriptionMouseClicked

    private void textfield_descriptionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textfield_descriptionKeyTyped
        textfield_description.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        textfield_description.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_textfield_descriptionKeyTyped

    private void storemanDropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_storemanDropActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_storemanDropActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
          // Search
        
      String comboBox_store_item = (String)comboBox_store.getSelectedItem();
      String textfield_location_item = textfield_location.getText();
      if (textfield_location_item.equals(""))
      {
        textfield_location_item = "%";
      }
      String textfield_description_item = textfield_description.getText();
      if(textfield_description_item.equals("Search Item..."))
      {
          textfield_description_item = "";
      }
          
      String sgl_string = "";

      try
      {
        ConnectDatabase database = new ConnectDatabase();
        
         if (!comboBox_store_item.equals("All"))
        {
          sgl_string = sgl_string + " Store like '%" + comboBox_store_item + "%'";
        }
        if (!textfield_location_item.equals(""))
        {
          if (!sgl_string.equals("")) {
            sgl_string = sgl_string + " AND";
          }
          sgl_string = sgl_string + " `Location / Bin` like '" + textfield_location_item + "'";
        }

        if (!textfield_description_item.equals(""))
        {
          if (!sgl_string.equals("")) {
            sgl_string = sgl_string + " AND";
          }
          sgl_string = sgl_string + " Description like '%" + textfield_description_item + "%'";
        }
        
        data = database.setSearchTable(sgl_string, "data_store_01");
        database.setCloseDatabase();
      }
      catch (Exception e_exception)
      {
        e_exception.printStackTrace();
      }   
      
     searchTable.setModel(new javax.swing.table.DefaultTableModel(data,header));   
     searchTable.getColumn("Trolley").setCellRenderer(new ButtonRenderer()); 
     searchTable.getColumn("Trolley").setCellEditor(new ButtonEditor(new JCheckBox()));
     //searchTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0))); 
     searchTable.getColumnModel().getColumn(0).setPreferredWidth(10); //ItemID
     searchTable.getColumnModel().getColumn(6).setPreferredWidth(14); //Quantity
     searchTable.getColumnModel().getColumn(4).setPreferredWidth(11); //Current
     searchTable.getColumnModel().getColumn(5).setPreferredWidth(11); //Creator
  
    }//GEN-LAST:event_searchButtonActionPerformed

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
            java.util.logging.Logger.getLogger(TrolleyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrolleyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrolleyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrolleyGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrolleyGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel alert_image_display;
    private javax.swing.JComboBox comboBox_store;
    private javax.swing.JTextField compid_textfield;
    private javax.swing.JComboBox facilitatorDrop;
    private javax.swing.JLabel issue_error_msg_label;
    private static javax.swing.JLabel items_count_label;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton searchButton;
    private javax.swing.JTable searchTable;
    private javax.swing.JComboBox storemanDrop;
    private javax.swing.JTextField textfield_description;
    private javax.swing.JTextField textfield_location;
    // End of variables declaration//GEN-END:variables
}


class ButtonRenderer extends JButton implements TableCellRenderer 
{

  public ButtonRenderer() {
    setOpaque(true);
  }

  public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) 
  {
    if (isSelected)
	{
      setForeground(table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    } 
	else
	{
      setForeground(table.getForeground());
      setBackground(UIManager.getColor("Button.background"));
    }
    setText((value == null) ? "" : value.toString());
    return this;
  }
}


class ButtonEditor extends DefaultCellEditor 
{
  protected JButton button;

  private String label;
  private int itemId;
  private String store;
  private String location;
  private String description;
  private String currentStock;
  private String quantity;
  private String facilitator;
  private String storeman;
  private int compID;
  private boolean isPushed;
 
  
  public ButtonEditor(JCheckBox checkBox) 
  {
    super(checkBox);
    button = new JButton();
    button.setOpaque(true);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
      }
    });
  }
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
  {
      
      //String thisID = (String) table.getValueAt(row, 0);
      itemId       = Integer.parseInt((String) table.getValueAt(row, 0)) ;
      store        = (String) table.getValueAt(row,1);
      location     = (String) table.getValueAt(row,2);
      description  = (String) table.getValueAt(row,3);
      currentStock = (String) table.getValueAt(row,4);
      quantity     = (String) table.getValueAt(row,6);
      
      if (isSelected)
        {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        }
      else
        {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
      
    //label = (value == null) ? "" : value.toString();
    label = "Add";
    button.setText(label);
    isPushed = true;
    return button;
  }
  public Object getCellEditorValue()
  {
    if (isPushed) 
    {
       int stock = Integer.parseInt(currentStock);
        
       if(stock!=0)
       {
         TrolleyGUI.addToBasket(itemId, store, location, description, currentStock, quantity); 
         //setBasketCount(itemId, store, location, description, currentStock, quantity); 
       } 
       else
       {  
        JOptionPane.showMessageDialog(null, "Canno't add "+description+" to trolley. Current Stock #"+currentStock,"No stock",1);     
       } 

    }
    isPushed = false;
    return new String(label);
  }
  public boolean stopCellEditing() 
  {
    isPushed = false;
    return super.stopCellEditing();
  }
  protected void fireEditingStopped() 
  {
    super.fireEditingStopped();
  }
}

/***********************************************************
 * Worker Class 
 * @author qxf3984
 ***********************************************************/
class Worker extends SwingWorker<String, String> 
{
  //private JFrame frame = new JFrame(); 
  Progress pg = new Progress();
  private JDialog dialog = new JDialog(pg, "Proccessing!", true);
 // private JProgressBar progressBar = new JProgressBar();
  public static String results;
  

  public Worker() {
    dialog.setPreferredSize(new Dimension(200,100));
    pg.setResizable(false); 
    pg.setLocationRelativeTo(null);  
    /*progressBar.setString("Please wait...");
    progressBar.setStringPainted(true);
    progressBar.setIndeterminate(true);*/
    dialog.getContentPane().add(pg.getContentPane());
    dialog.pack();
    dialog.setModal( false );
    dialog.setVisible(true);
  }

  @Override
  protected String doInBackground() throws Exception 
  {
    int size = TrolleyGUI.emailList.size();
    String state = "";
    //send e-mail
    for(int x = 0; x < size;x++){
        SendEmail mail = new SendEmail(TrolleyGUI.emailList.get(x).recipient(),TrolleyGUI.emailList.get(x).sender(),TrolleyGUI.emailList.get(x).cc(),TrolleyGUI.emailList.get(x).body(),TrolleyGUI.emailList.get(x).subject());
        state = mail.Send();
     }

    return state;
  }

  protected void done() 
  {
     results = "";
      try {
          results = get();  // get the results returned by doInBackground() function and use them
      } catch (InterruptedException ex) {
          Logger.getLogger(GuiWorker.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ExecutionException ex) {
          Logger.getLogger(GuiWorker.class.getName()).log(Level.SEVERE, null, ex);
      }
    
      dialog.dispose();
      
      // JOptionPane.showMessageDialog(null, "<html>Item(s) succesfully issued.</html>", "Issue Confirmation", 1);
      JOptionPane.showMessageDialog(null, "<html>You have succesfully issued #"+TrolleyGUI.basketCount+" items to " + TrolleyGUI.fname + "</html>", "Issue Confirmation", 1);
      TrolleyGUI.basketCount = 0;
      TrolleyGUI.trolleyItems.clear();
      TrolleyGUI.emailList.clear();
  }

}