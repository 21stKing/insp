/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory_system;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import static inventory_system.TrolleyGUI.basketCount;


/**
 *
 * @author qxf3984
 */
public class ReceiveGUI extends JDialog implements ActionListener,PropertyChangeListener {
    
    private static int id;
    private static int role;
    private static String rolename;
    Date logtime;
    public static int []  rowSelected;
    private Vector<Vector<String>> data; 
    private Vector<String> header;
    private Object ID;
    private DefaultTableModel model;   
    private static String learner;
    private static String date ;
    public static ArrayList<MultipleReceive> basketItems = new ArrayList<MultipleReceive>();
    private JOptionPane optionPane;
    private String cancelBtn = "Cancel";
    public Object value = "";
    public static String fname,lname; 
    public static String email;
    public static String f_email;
    public static String on_copy;
    public static String recipient;
    public static String subject;
    public static String lmessage;
    public static String fmessage;
    public static String results;
    private boolean sendMail;
    protected static String [] listFaci;
    protected static String [] listStoreman;
    
    public ReceiveGUI(JFrame aFrame,int idin, int roleIn) { 
        super(aFrame, true);
        id      = idin;
        role    = roleIn;
        logtime = new Date();
        //create headers for search table
        header = new Vector<String>();
        header.add("#");               //id
        header.add("Name");            //name
        header.add("Company ID");      //company_id
        header.add("Item ID");         //item id
        header.add("Description");     //Item Description
        header.add("Location");        //Location
        header.add("Issued Quantity"); //Quantity that was issued
        header.add("Date Received");     //receive date 
        header.add("Facilitator");     //facillitator
        header.add("Storeman");        //storeman
        header.add("#Quantity");
        header.add("Receive");
        
        initComponents();

        this.getContentPane().setBackground(Color.white);  //sets layout background color to white
         
        //get role name
        try{ConnectDatabase database = new ConnectDatabase();rolename = database.getRole(role); }
        catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"Cant get role name",JOptionPane.ERROR_MESSAGE);}
        
        switch (rolename) {
            case "Storeman":
                //Storeman list
               /* try{ConnectDatabase database = new ConnectDatabase();listStoreman = database.getListStoreman(id);String [] ccListStoreArray;ccListStoreArray = new String [listStoreman.length];
                    for(int i=0;i < listStoreman.length;i++){ ccListStoreArray[i] = listStoreman[i]; storemanDrop.addItem(ccListStoreArray[i]); }}
                catch(Exception e1){JOptionPane.showMessageDialog(null,e1,"ERROR IN STOREMAN LIST",JOptionPane.ERROR_MESSAGE);	}*/
                //facilitator list
                /*try{ConnectDatabase database = new ConnectDatabase();listFaci = database.getListFacilitator(id); String [] ccListFaciArray; ccListFaciArray = new String [listFaci.length];
                    for(int i=0;i < listFaci.length;i++){ ccListFaciArray[i] = listFaci[i]; facilitatorDrop2.addItem(ccListFaciArray[i]);}}
                catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN FACILITATOR LIST",JOptionPane.ERROR_MESSAGE);}*/
                break;
            case "Facilitaotor":
                /*try{ConnectDatabase database = new ConnectDatabase();listStoreman = database.getListStoreman();String [] ccListStoreArray;ccListStoreArray = new String [listStoreman.length];
                   for(int i=0;i < listStoreman.length;i++){ ccListStoreArray[i] = listStoreman[i]; storemanDrop.addItem(ccListStoreArray[i]); }}
               catch(Exception e1){JOptionPane.showMessageDialog(null,e1,"ERROR IN STOREMAN LIST",JOptionPane.ERROR_MESSAGE);	}*/
                break;
            case "Administrator":
                //storeman list
                /*try{ConnectDatabase database = new ConnectDatabase();listStoreman = database.getListStoreman();String [] ccListStoreArray;ccListStoreArray = new String [listStoreman.length];
                   for(int i=0;i < listStoreman.length;i++){ ccListStoreArray[i] = listStoreman[i]; storemanDrop.addItem(ccListStoreArray[i]); }}
               catch(Exception e1){JOptionPane.showMessageDialog(null,e1,"ERROR IN STOREMAN LIST",JOptionPane.ERROR_MESSAGE);	}*/
                //facilitator list
                //try{ConnectDatabase database = new ConnectDatabase();listFaci = database.getListFacilitator(id); String [] ccListFaciArray; ccListFaciArray = new String [listFaci.length];
                  //  for(int i=0;i < listFaci.length;i++){ ccListFaciArray[i] = listFaci[i]; facilitatorDrop.addItem(ccListFaciArray[i]);}}
                //catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN FACILITATOR LIST",JOptionPane.ERROR_MESSAGE);} 
                break;
            case "Superuser":
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
    
    private ReceiveGUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        issuedItemsTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        export = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        storemanDrop = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        facilitatorDrop = new javax.swing.JComboBox();
        textfield_compid = new javax.swing.JTextField();
        receive_error_msg_label = new javax.swing.JLabel();
        alert_image_display = new javax.swing.JLabel();

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Receive Multiple Items");
        setIconImage(new ImageIcon(getClass().getResource("/inventory_system/image/insp_icon_demo_45x45.png")).getImage());
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));

        issuedItemsTable.setModel(new javax.swing.table.DefaultTableModel(data,header));
        jScrollPane1.setViewportView(issuedItemsTable);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Receive", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel2.setToolTipText("Type in company id of a learner to view all items that have been issued.");

        jButton1.setText("Check");
        jButton1.setToolTipText("View all items that have been issued.");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Receive");
        jButton3.setToolTipText("View all items that have been issued.");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        export.setBackground(new java.awt.Color(255, 255, 255));
        export.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/excel-8.png"))); // NOI18N
        export.setToolTipText("export search results to  excl ");
        export.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exportMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exportMouseExited(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportMouseClicked(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel20.setText("Storeman");

        jLabel19.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel19.setText("Facilitator");

        textfield_compid.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        textfield_compid.setForeground(new java.awt.Color(153, 153, 153));
        textfield_compid.setText("company ID...");
        textfield_compid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textfield_compidMouseClicked(evt);
            }
        });
        textfield_compid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textfield_compidActionPerformed(evt);
            }
        });
        textfield_compid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textfield_compidKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textfield_compid, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(export)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(storemanDrop, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(facilitatorDrop, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(export, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textfield_compid, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(facilitatorDrop, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(storemanDrop, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        receive_error_msg_label.setForeground(java.awt.Color.red);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(149, 149, 149)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addComponent(alert_image_display, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(receive_error_msg_label, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(receive_error_msg_label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(alert_image_display, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportMouseClicked
        // export
        
      boolean help = false;
      int count = 1;
      model = (DefaultTableModel) issuedItemsTable.getModel();
      
      while (!help)
      {
        File file = new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath() + "\\results" + count + ".xls");
        try
        {
          FileWriter out = new FileWriter(file);
          for (int i = 0; i < model.getColumnCount(); i++)
          {
            out.write(model.getColumnName(i) + "\t");
          }
          out.write("\n");
          for (int i = 0; i < model.getRowCount(); i++) 
          {
            for (int j = 0; j < model.getColumnCount(); j++) 
            {
              out.write(model.getValueAt(i, j) + "\t");
            }
            out.write("\n");
          }
          out.close();
          String command = "cmd /c \"" + file.getPath() + "\"";
          Runtime.getRuntime().exec(command);
          help = true;
        }
        catch (IOException e1)
        {
          count++;
        }
      }
    }//GEN-LAST:event_exportMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String compid_input = textfield_compid.getText();
        if(compid_input.equals(""))
         {  
           textfield_compid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
           alert_image_display.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png"))); 
           receive_error_msg_label.setText("Warning: Please type in company id in the highlighted box!"); 
         } 
        else
        {
           textfield_compid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
           alert_image_display.setIcon(new javax.swing.ImageIcon(getClass().getResource(""))); 
           receive_error_msg_label.setText("");
           int compid = Integer.parseInt(textfield_compid.getText());
           int items = 0;

        try
        {   	
          ConnectDatabase database = new ConnectDatabase();
          String learnerName = database.getString("SELECT `name` FROM learner_list WHERE `company_id` LIKE '" + compid + "'");
          String learnerSurname = database.getString("SELECT `surname` FROM learner_list WHERE `company_id` LIKE " + compid);
          learner = learnerName + " " + learnerSurname;
          items = database.issuedItems(compid); //check if user borrowed item(s)
        }
        catch (Exception e1)
        {
            JOptionPane.showMessageDialog(null, "Learner with company ID :"+compid+" does not exist!", "Receiving Unssuccessful", 2);  
            //JOptionPane.showMessageDialog(null, ""+e1, "Receiving ERROR", 2); 
            learner = ""+compid;
        }
          
        try
        {
          ConnectDatabase database = new ConnectDatabase();  
            
          if(items <= 0)
          {
              if (learner == null)
              { 
               JOptionPane.showMessageDialog(null, "<html>"+compid+" was never issued any item. Please enter the correct Company ID!</html>", "Receiving Information - "+compid, 1); 
              }
              else
              {
               JOptionPane.showMessageDialog(null, "<html>"+learner+" was never issued any item. Please try again!</html>", "Receiving Information - "+compid, 1);    
              }  
          }    
          else
          {  
              //Now display all items borrowed/issued to by this learner
              data  = database.setIssuedItemsTable(""+compid);
              
              //populate the tables with data
              model = new javax.swing.table.DefaultTableModel(data,header);
              issuedItemsTable.setModel(new javax.swing.table.DefaultTableModel(data,header)); 
              issuedItemsTable.getColumn("Receive").setCellRenderer(new CheckBoxRenderer()); 
              issuedItemsTable.getColumn("Receive").setCellEditor(new CheckBoxEditor(new JCheckBox())); 
              database.setCloseDatabase();
          }
        }
        catch (Exception e1)
        {
          //JOptionPane.showMessageDialog(null, "Learner with company ID :"+companyid+" does not exist!", "Receiving Unssuccessful", 2);  
            JOptionPane.showMessageDialog(null, ""+e1, "Receiving ERROR", 2);  
        }
        
           
           
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void addToReceiveBasket(String name, String companyid,String itemID,String descrip,String location,String quantity, String date, String facilitator,String storeman,String receiveQuant)
    {     
       boolean status = false;
       try
        {     	
          ConnectDatabase database = new ConnectDatabase();
          String store_name  = database.getStore(location);
          int ID             = database.getID(location);
          int intquantity    = Integer.parseInt(receiveQuant);
          //check if the sum of receive quantity and the available item is not greater than the total quantity of items stored
          store_name = database.getStore(location);
          if (store_name.equals("Tool store"))
               status = database.borrowedItemsComparison(ID, intquantity,"tool_list");
          else if (store_name.equals("Consumable store"))
               status = database.borrowedItemsComparison(ID, intquantity,"consumables");
          else if (store_name.equals("Equipment store")) {
               status = database.borrowedItemsComparison(ID, intquantity,"equipment_list");}
        }
        catch (Exception e1)
        {
          JOptionPane.showMessageDialog(null, ""+e1, "BasketItems | Receive ERROR", 2); 
          
        } 
        
        if (!status)
        { 
          JOptionPane.showMessageDialog(null, "<html>You cannot receive more than what's in the store. Please check your location and try again!</html>", "Receiving Information - Warning", 2); 
        }
        else
        {
          basketItems.add(new MultipleReceive(name,companyid,itemID,descrip,location,quantity,date,facilitator,storeman,receiveQuant));        
        } 
    }
    
    private void exportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportMouseEntered
         export.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/excel-9.png")));
    }//GEN-LAST:event_exportMouseEntered

    private void exportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportMouseExited
        export.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/excel-8.png")));
    }//GEN-LAST:event_exportMouseExited

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //Set date
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date currentTime = new Date();
        date = formatter.format(currentTime); 
        String facilitator = (String)facilitatorDrop.getSelectedItem();
        String storeman = (String)storemanDrop.getSelectedItem();
        
        String compid_input = textfield_compid.getText();
        int compid = Integer.parseInt(compid_input);
        
        if(compid_input.equals(""))
         {  
           textfield_compid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
           alert_image_display.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png"))); 
           receive_error_msg_label.setText("Warning: Please type in company id in the highlighted box!"); 
         } 
        else
        {
           textfield_compid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
           alert_image_display.setIcon(new javax.swing.ImageIcon(getClass().getResource(""))); 
           receive_error_msg_label.setText(""); 
          
          if (learner == null)
           { 
              JOptionPane.showMessageDialog(null, "<html>Please press the <b>Check</b> button first <br>then in the <b>Receive</b> table column press the <br><b>received</b> button of the item you want to receive</html>", "Receiving Information Rules - "+textfield_compid.getText(), 1); 
           }
          else
            {
               try
                {
                  ConnectDatabase database = new ConnectDatabase();
                  int size = basketItems.size(); 
                  boolean less;
                  if(size != 0)
                  {  
                    for(int x = 0;x < size;x++)
                     {
                       String fields = ", Quantity, Storeman, Facilitator, `Date`, Company_id, Learner";
                       String values = ", '" + basketItems.get(x).getReceiveQuantity() + "', '" + basketItems.get(x).getstoreman() + "', '" + basketItems.get(x).getFacilitator() + "', #" + basketItems.get(x).getDate() + "#, '" + basketItems.get(x).getCompanyID() + "', '" + basketItems.get(x).getName() + "'";
                       int ID        = database.getID(basketItems.get(x).getLocation());
                       database.setNewDigitValue(ID, "Current", Integer.parseInt(basketItems.get(x).getReceiveQuantity()), 1);
                       database.setListToListItem(ID, fields, values, "receive_list_01");
 
                       if (database.getStore(basketItems.get(x).getLocation()).equals("Tool store")){
                            database.setNewToolQuantity(ID, Integer.parseInt(basketItems.get(x).getReceiveQuantity()), 1);
                            less = database.isborrowedItemLess(ID, Integer.parseInt(basketItems.get(x).getReceiveQuantity()), "tool_list");
                            if(less)
                                database.updateIssuedItem(ID, "tool_issue_list", Integer.parseInt(basketItems.get(x).getReceiveQuantity()), Integer.parseInt(basketItems.get(x).getQuantity()) );
                            else 
                                database.deleteIssuedItem(ID,"tool_issue_list");}
                       else if (database.getStore(basketItems.get(x).getLocation()).equals("Consumable store")){
                            database.setNewConsumableQuantity(ID, Integer.parseInt(basketItems.get(x).getReceiveQuantity()), 1);
                            less = database.isborrowedItemLess(ID, Integer.parseInt(basketItems.get(x).getReceiveQuantity()), "consumables");
                            if(less)
                                database.updateIssuedItem(ID, "consumables_issue_list", Integer.parseInt(basketItems.get(x).getReceiveQuantity()),Integer.parseInt(basketItems.get(x).getQuantity()));
                            else
                                database.deleteIssuedItem(ID,"consumables_issue_list");}
                       else if (database.getStore(basketItems.get(x).getLocation()).equals("Equipment store")) {
                            database.setNewEquipmentQuantity(ID, Integer.parseInt(basketItems.get(x).getReceiveQuantity()), 1);
                            less = database.isborrowedItemLess(ID, Integer.parseInt(basketItems.get(x).getReceiveQuantity()), "equipment_list");
                            if(less)
                                database.updateIssuedItem(ID, "equipment_issue_list", Integer.parseInt(basketItems.get(x).getReceiveQuantity()), Integer.parseInt(basketItems.get(x).getQuantity()));
                            else
                                database.deleteIssuedItem(ID,"equipment_issue_list");
                         }
                    }
                   }else{
                      //Receive button not clicked, so do nothing here
                      JOptionPane.showMessageDialog(null, "<html>You did not press the receive button. Please try again!</html>", "Receive Button", 1);
                      database.setCloseDatabase(); 
                  }
                     database.setCloseDatabase(); 
                     //basketItems.clear();             // clear all items in the list so that when you decide to receive again, the basket doesnt contain any data
                     alert_image_display.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/email_success.png")));
                     receive_error_msg_label.setText("Item(s) successfully issued.");
                     sendMail = true;
                }
              catch (Exception e1)
                {
                  //JOptionPane.showMessageDialog(null, "Learner with company ID :"+companyid+" does not exist!", "Receiving Unssuccessful", 2);  
                    JOptionPane.showMessageDialog(null, ""+e1, "Receiving ERROR", 2);  
                    sendMail = false;
                }
               
                if(sendMail)
                {  
                  //Add items to table list of items.
                  ConstructMail cm = new ConstructMail();
                  cm.setFacilitator(facilitator);
                  cm.setStoreman(storeman);
                  cm.setLearner(learner);
                  cm.setCompany_id(compid); 
                  cm.setDate(date);
                  lmessage = cm.MultipleReceivedItemsNotifyLearner(learner);
                  fmessage = cm.MultipleReceivedItemsNotifyFacilitator(facilitator);


                 //prepare e-mail notification for learner
                 try
                 {
                  ConnectDatabase database = new ConnectDatabase();      

                  subject = "RECEIVED ITEM(S) FROM "+compid;
                  recipient = database.getString("SELECT `e_mail` FROM learner_list WHERE `company_id` LIKE "+compid); 
                  String facil [] = facilitator.split(" ");
                  f_email   = database.getString("SELECT `e_mail` FROM facilitator WHERE `name` = '"+facil[0]+"' AND `surname` = '"+facil[1]+"'");
                  email     = "invt_store_program@bmw.co.za"; //database.getString("SELECT `e_mail` FROM users WHERE `company_id` LIKE "+id); 
                  on_copy   = "";//database.getString(""); 
                  }
                 catch(Exception e1){ e1.printStackTrace();}

                 //Send e-mail notification(receipt) to learner
                 new WorkerReceive().execute();     

                 //In backgroud, send facilitator an e-mail
                 SendEmail mail = new SendEmail(f_email,email,on_copy,fmessage,subject);
                 String state = mail.Send();

                 if("success".equals(state)){
                     alert_image_display.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/email_success.png")));
                     receive_error_msg_label.setText(facilitator+" notified successfully.");
                     receive_error_msg_label.setForeground(Color.green); 
                     basketItems.clear();             // clear all items in the list so that when you decide to receive again, the basket doesnt contain any data
                 }
                 else{
                     alert_image_display.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/email_success.png")));
                     receive_error_msg_label.setText("Failed to notify "+facilitator+".");
                 }
               } 
            }   
        }  
      
    }//GEN-LAST:event_jButton3ActionPerformed

    private void textfield_compidMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textfield_compidMouseClicked
        textfield_compid.setText("");
    }//GEN-LAST:event_textfield_compidMouseClicked

    private void textfield_compidKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textfield_compidKeyTyped
        textfield_compid.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        textfield_compid.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_textfield_compidKeyTyped

    private void textfield_compidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textfield_compidActionPerformed
        String faci_name = "";
        int learner_id = 0, faci_id = 0; 
        learner_id = Integer.parseInt(textfield_compid.getText().toString());
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
    }//GEN-LAST:event_textfield_compidActionPerformed

        
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
            java.util.logging.Logger.getLogger(ReceiveGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReceiveGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReceiveGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReceiveGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReceiveGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel alert_image_display;
    private javax.swing.JLabel export;
    public static javax.swing.JComboBox facilitatorDrop;
    private javax.swing.JTable issuedItemsTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel receive_error_msg_label;
    public static javax.swing.JComboBox storemanDrop;
    private javax.swing.JTextField textfield_compid;
    // End of variables declaration//GEN-END:variables
}
class CheckBoxRenderer extends JButton implements TableCellRenderer 
{

  public CheckBoxRenderer() {
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


class CheckBoxEditor extends DefaultCellEditor 
{
  protected JButton button;
  private String label;
  private String name;
  private String companyid;
  private String itemID;
  private String description;
  private String location;
  private String quantity;
  private String date;
  private String facilitator;
  private String storeman;
  private String receiveQuant;
  private boolean isPushed;
  private int rowToReceive; 
  public Trolley trolley;
 
  
  public CheckBoxEditor(JCheckBox checkBox) {
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
    name        = (String) table.getValueAt(row,1);
    companyid   = (String) table.getValueAt(row,2);
    itemID      = (String) table.getValueAt(row,3);
    description = (String) table.getValueAt(row,4);
    location    = (String) table.getValueAt(row,5);
    quantity    = (String) table.getValueAt(row,6);
    facilitator = (String) table.getValueAt(row,8);
    storeman    = (String) table.getValueAt(row,9);
    receiveQuant = (String) table.getValueAt(row,10);
    rowToReceive = row;
    //Set date
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    Date currentTime = new Date();
    date = formatter.format(currentTime); 
    
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
      
    label = "Receive";
    button.setText(label);
    isPushed = true;
    return button;
  }

  public Object getCellEditorValue()
  {
    if (isPushed) 
    {

      if(receiveQuant == null || receiveQuant == "" )
      { 
       ReceiveGUI.addToReceiveBasket(name,companyid, itemID,description,location,quantity,date,facilitator,storeman,"1");       
      }
      else
      {
       ReceiveGUI.addToReceiveBasket(name,companyid, itemID,description,location,quantity,date,facilitator,storeman,receiveQuant);         
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


class WorkerReceive extends SwingWorker<String, String> {

  /*
  * This should just create a frame that will hold a progress bar until the
  * work is done. Once done, it should remove the progress bar from the dialog
  * and add a label saying the task complete.
  */
   
  Progress pg = new Progress(); 
  //private JFrame frame = new JFrame(); 
  private JDialog dialog = new JDialog(pg, "Proccessing!", true);
  //private JProgressBar progressBar = new JProgressBar();
  public static String results;
  

  public WorkerReceive() {
    dialog.setPreferredSize(new Dimension(299, 160));
    pg.setResizable(false); 
    pg.setLocationByPlatform(true);   
    /*progressBar.setString("Please wait...");
    progressBar.setStringPainted(true);
    progressBar.setIndeterminate(true);*/
    dialog.getContentPane().add(pg.getContentPane());
    dialog.pack();
    dialog.setModal( false );
    dialog.setVisible(true);
      //pg.setVisible(true); 
  }

  @Override
  protected String doInBackground() throws Exception 
  {
    //send e-mail
    SendEmail mail = new SendEmail(ReceiveGUI.recipient,ReceiveGUI.email,ReceiveGUI.on_copy,ReceiveGUI.lmessage,ReceiveGUI.subject);
    String state = mail.Send();

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
      
      if(results == "success")
      {
           ReceiveGUI.alert_image_display.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/email_success.png")));
           ReceiveGUI.receive_error_msg_label.setText("Successfully notified Learner.");
           ReceiveGUI.receive_error_msg_label.setForeground(Color.green); 
      }else{
           ReceiveGUI.alert_image_display.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/warning.png")));
           ReceiveGUI.receive_error_msg_label.setText("Failed to notify Learner.");
           ReceiveGUI.receive_error_msg_label.setForeground(Color.red); 
      }  
       
  }

}
