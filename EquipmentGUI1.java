
package inventory_system;

import java.awt.Color;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EquipmentGUI1 extends javax.swing.JInternalFrame {

    private static int id;
    private static int role;
    Date logtime;
    public static int []  rowSelected;
    private Vector<Vector<String>> data; 
    private Vector<String> header;
    private Object ID;
    private DefaultTableModel model;
    public static int []  rowSelected2;
    private Vector<Vector<String>> data2; 
    private Vector<String> header2;
    private Object ID2;
    private DefaultTableModel model2;
    private static String learner;
    
    
    public EquipmentGUI1(int idin, int roleIn)
    {   
        id      = idin;
        role    = roleIn;
        logtime = new Date();
        model = new javax.swing.table.DefaultTableModel(data,header);
        model2 = new javax.swing.table.DefaultTableModel(data2,header2);
        //create headers for consumable table
        header = new Vector<String>();
        header.add("ItemID");
        header.add("ID");
        header.add("Equipment Name");
        header.add("Available");
        header.add("Quantity");
        header.add("Store");
        
        //create headers for consumable issue list table
        header2 = new Vector<String>();
        header2.add("ID");
        header2.add("Full Name");
        header2.add("Comapany ID");
        header2.add("ItemID");
        header2.add("Equipment Descp.");
        header2.add("Quantity");
        header2.add("Date OUT");
        header2.add("Location");
        header2.add("Facilitator");
        header2.add("Storeman");
        
        initComponents();
        setSize(840, 600); 
        setLocation(0, 0);
        this.getContentPane().setBackground(Color.white);  //sets layout background color to white
        
        try
        {
          ConnectDatabase database = new ConnectDatabase();
          data  = database.setEquipmentTable("equipment_list");
          data2 = database.setEquipmentListTable("equipment_issue_list");
          database.setCloseDatabase();
        }
        catch (Exception e_exception)
        {
          e_exception.printStackTrace();
        }
        
        //populate the tables with data
        equipmentTable.setModel(new javax.swing.table.DefaultTableModel(data,header));   
        equipmentListTable.setModel(new javax.swing.table.DefaultTableModel(data2,header2));   
        //Activate table on main screen
        MainWindow.setActiveTable(equipmentTable);
    }

    private EquipmentGUI1() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        equipmentTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        equipmentListTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Equipments");

        equipmentTable.setModel(new javax.swing.table.DefaultTableModel(data,header));
        equipmentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                equipmentTableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(equipmentTable);

        equipmentListTable.setModel(new javax.swing.table.DefaultTableModel(data2,header2));
        equipmentListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                equipmentListTableMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(equipmentListTable);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Issued Equipments");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 19, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void equipmentTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_equipmentTableMousePressed
        
       /* if ((evt.getClickCount() == 2) && (!evt.isMetaDown()))
        {
          int row = EquipmentGUI1.this.equipmentTable.getSelectedRow();
          String locationdata = null;
          String descriptiondata = null;
          String storedata = null;
          String currentdata = null;

          try
          {
            ConnectDatabase database = new ConnectDatabase();

            String thisID = (String) EquipmentGUI1.this.equipmentTable.getValueAt(row, 1);
            int ID = Integer.parseInt(thisID) ;
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
        */
    }//GEN-LAST:event_equipmentTableMousePressed

    private void equipmentListTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_equipmentListTableMousePressed
        
      if ((evt.getClickCount() == 2) && (!evt.isMetaDown()))
        { 
          int items = 0; 
          boolean status = false;
          String store_name = "";  
          int thisID = 0;   
          int row = EquipmentGUI1.this.equipmentListTable.getSelectedRow();
          String issueID   = (String) EquipmentGUI1.this.equipmentListTable.getValueAt(row, 0);
          String fullname  = (String) EquipmentGUI1.this.equipmentListTable.getValueAt(row, 1);
          String Companyid = (String) EquipmentGUI1.this.equipmentListTable.getValueAt(row, 2);
          String itemID    = (String) EquipmentGUI1.this.equipmentListTable.getValueAt(row, 3);
          String equip   = (String) EquipmentGUI1.this.equipmentListTable.getValueAt(row, 4);
          String quant     = (String) EquipmentGUI1.this.equipmentListTable.getValueAt(row, 5);
          String dateout   = (String) EquipmentGUI1.this.equipmentListTable.getValueAt(row, 6);
          String location  = (String) EquipmentGUI1.this.equipmentListTable.getValueAt(row, 7);
          String facil     = (String) EquipmentGUI1.this.equipmentListTable.getValueAt(row, 8);
          String storeman  = (String) EquipmentGUI1.this.equipmentListTable.getValueAt(row, 9);
          
          //convert some string values to integers
          int thisIssueID = Integer.parseInt(issueID);
          int thisCompid  = Integer.parseInt(Companyid);
          int thisItemID  = Integer.parseInt(itemID);
          int thisQuant   = Integer.parseInt(quant);
          
          String fields = ", Quantity, Storeman, Facilitator, `Date`, Company_id, Learner";
          String values = ", '" + thisQuant + "', '" + storeman + "', '" + facil + "', #" + dateout + "#, '" + thisCompid + "', '" + fullname + "'"; 
          
          int answer = JOptionPane.showConfirmDialog(null, "<html>Are you sure you want to receive: <b><i>" + equip+"</i></b> issued to : <html><b><u>"+ fullname+"</u></b></html>", "Receive Confirmation", 0);
          
          if (answer == 0)
             {   
               
               try
                { 

                  ConnectDatabase database = new ConnectDatabase();
                  thisID = database.getID(location);
                  store_name  = database.getStore(location);
                  String learnerName = database.getString("SELECT `name` FROM learner_list WHERE `company_id` LIKE '" + thisCompid + "'");
                  String learnerSurname = database.getString("SELECT `surname` FROM learner_list WHERE `company_id` LIKE " + thisCompid);
                  learner = learnerName + " " + learnerSurname;

                  items = database.borrowedItems(thisCompid); //check if user borrowed an item

                  //check if the sum of receive quantity and the available item is not greater than the total quantity of items stored
                  store_name = database.getStore(location);

                  if (store_name.equals("Tool store"))
                       status = database.borrowedItemsComparison(thisID, thisQuant,"tool_list");
                  else if (store_name.equals("Consumable store"))
                       status = database.borrowedItemsComparison(thisID, thisQuant,"consumables");
                  else if (store_name.equals("Equipment store")) {
                       status = database.borrowedItemsComparison(thisID, thisQuant,"equipment_list");}

                }
                catch (Exception e1)
                {
                    JOptionPane.showMessageDialog(null, "Learner with company ID :"+thisCompid+" does not exist!", "Receiving Unssuccessful", 2);  
                    //JOptionPane.showMessageDialog(null, ""+e1, "Receiving ERROR", 2); 
                    learner = ""+thisCompid;
                }  
                 
                
               try
                {
                  ConnectDatabase database = new ConnectDatabase();  

                  if(items <= 0)
                  {
                      if (learner == null)
                      { 
                       JOptionPane.showMessageDialog(null, "<html>"+thisCompid+" was never issued any item. Please enter the correct Company ID!</html>", "Receiving Information - "+thisCompid, 1); 
                      }
                      else
                      {
                       JOptionPane.showMessageDialog(null, "<html>"+learner+" was never issued any item. Please enter the correct Company ID!</html>", "Receiving Information - "+thisCompid, 1);    
                      }  
                  }    
                  else
                  { 
                      if (status != true)
                      { 
                          JOptionPane.showMessageDialog(null, "<html>You cannot receive more than what's in the store. Please check your location and try again!</html>", "Receiving Information - Warning", 2); 
                      }
                      else
                      {
                        //thisID = database.getID(location);
                        database.setNewDigitValue(thisID, "Current", thisQuant, 1);
                        database.setListToListItem(thisID, fields, values, "receive_list_01");
                        database.setNewEquipmentQuantity(thisID, thisQuant, 1);
                        database.deleteIssuedItem(thisID,"equipment_issue_list");
                        JOptionPane.showMessageDialog(null, "<html>Item succesfully received</html>", "Receive Confirmation", 1);
                        database.setCloseDatabase();
                      }       
                    }
                  }
                  catch (Exception e1)
                  {
                    //JOptionPane.showMessageDialog(null, "Learner with company ID :"+companyid+" does not exist!", "Receiving Unssuccessful", 2);  
                      JOptionPane.showMessageDialog(null, ""+e1, "Receiving ERROR", 2);  
                  }
            }
           else
            {
              
            } 
        }
    }//GEN-LAST:event_equipmentListTableMousePressed

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
            java.util.logging.Logger.getLogger(EquipmentGUI1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EquipmentGUI1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EquipmentGUI1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EquipmentGUI1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EquipmentGUI1().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable equipmentListTable;
    private javax.swing.JTable equipmentTable;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
