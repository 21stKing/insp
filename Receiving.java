/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory_system;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class Receiving extends javax.swing.JInternalFrame {

    private Connection con;
    private ResultSet rs;
    private static int id;
    private static int role;
    private static String rolename;
    Date logtime;
    public static String [] locationList;
    private static String groupName;
    private static int groupLevel;
    private static String nameOfLearner;
    private static String learner;
    private static int compID;
    SimpleDateFormat sdfFrom = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    SimpleDateFormat sdfTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String date ;
    public static int msgCount;
    public static String noticeMsg;
    private static String IconToShow;
    private boolean ok = false;
    //Declaration for a custom dialog 
    private JOptionPane optionPane;
    private String btnString1 = "Yes";
    private String btnString2 = "No";
    private String btnString3 = "Cancel";
    private JLabel photoLabel = new JLabel();
    private Object value = "";
   //CustomDialog cd;
    public static String recipient,email,on_copy,fmessage,lmessage,subject,f_email;
    private String space5, space14,space41, space48,space59;
    private String dueDate;
    protected static String [] listFaci;
    protected static String [] listStoreman;
    protected static String [] listCourse;
    protected static int [] listLevels;
    private static boolean jobRunning = true;
    private String helpURL = "http://localhost/kinotech/test.html";
    private static String screen = "MainWindow";
    
    public Receiving()
    {
        initComponents();
        //setSize(840, 600); //width, height 
        setLocation(0, 0);
        this.getContentPane().setBackground(Color.white);
              
        //Location
        try{ConnectDatabase database = new ConnectDatabase(); locationList = database.getLocation(); String [] locationArray;locationArray = new String [locationList.length];
            for(int i=0;i < locationList.length;i++){ locationArray[i] = locationList[i];jComboBox1.addItem(locationArray[i]); }}
        catch(Exception e1){JOptionPane.showMessageDialog(null,e1,"ERROR IN LOCATION",JOptionPane.ERROR_MESSAGE);}
        
        //get role name
        try{ConnectDatabase database = new ConnectDatabase();rolename = database.getRole(role); }
        catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"Cant get role name",JOptionPane.ERROR_MESSAGE);}
        
       switch (rolename) {
            case "Storeman":
                //Storeman list
                /*try{ConnectDatabase database = new ConnectDatabase();listStoreman = database.getListStoreman(id);String [] ccListStoreArray;ccListStoreArray = new String [listStoreman.length];
                    for(int i=0;i < listStoreman.length;i++){ ccListStoreArray[i] = listStoreman[i]; storemanDrop.addItem(ccListStoreArray[i]); }}
                catch(Exception e1){JOptionPane.showMessageDialog(null,e1,"ERROR IN STOREMAN LIST",JOptionPane.ERROR_MESSAGE);	}*/
                //facilitator list
                /*try{ConnectDatabase database = new ConnectDatabase();listFaci = database.getListFacilitator(id); String [] ccListFaciArray; ccListFaciArray = new String [listFaci.length];
                    for(int i=0;i < listFaci.length;i++){ ccListFaciArray[i] = listFaci[i]; facilitatorDrop2.addItem(ccListFaciArray[i]);}}
                catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN FACILITATOR LIST",JOptionPane.ERROR_MESSAGE);}*/
                break;
            case "Facilitaotor":
               /* try{ConnectDatabase database = new ConnectDatabase();listStoreman = database.getListStoreman();String [] ccListStoreArray;ccListStoreArray = new String [listStoreman.length];
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
               /* try{ConnectDatabase database = new ConnectDatabase();listStoreman = database.getListStoreman();String [] ccListStoreArray;ccListStoreArray = new String [listStoreman.length];
                   for(int i=0;i < listStoreman.length;i++){ ccListStoreArray[i] = listStoreman[i]; storemanDrop.addItem(ccListStoreArray[i]); }}
               catch(Exception e1){JOptionPane.showMessageDialog(null,e1,"ERROR IN STOREMAN LIST",JOptionPane.ERROR_MESSAGE);	}*/
                //facilitator list
                //try{ConnectDatabase database = new ConnectDatabase();listFaci = database.getListFacilitator(id); String [] ccListFaciArray; ccListFaciArray = new String [listFaci.length];
                  //  for(int i=0;i < listFaci.length;i++){ ccListFaciArray[i] = listFaci[i]; facilitatorDrop.addItem(ccListFaciArray[i]);}}
                //catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN FACILITATOR LIST",JOptionPane.ERROR_MESSAGE);} 
                break;    
        }
 
    }

    public static void setRole(int roleIn){
        role = roleIn;
    }
    
    public static void setUserId(int idIn){
        id = idIn;
    }
    
    private int getRole(){
        return role;
    }
    
    private int getUserId(){
        return id;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        receive_textfield = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        quantity_textfield = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        facilitatorDrop = new javax.swing.JComboBox();
        storemanDrop = new javax.swing.JComboBox();
        receive_error_msg_label = new javax.swing.JLabel();
        alert_image_display_01 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(MainWindow.home_desktop.getPreferredSize());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Receiving", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 18))); // NOI18N

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel16.setText("Company ID");

        receive_textfield.setForeground(new java.awt.Color(135, 85, 85));
        receive_textfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        receive_textfield.setToolTipText("Company ID of a learner who was issued an item.");
        receive_textfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receive_textfieldActionPerformed(evt);
            }
        });
        receive_textfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                receive_textfieldKeyTyped(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel17.setText("Quantity");

        quantity_textfield.setForeground(new java.awt.Color(135, 85, 85));
        quantity_textfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        quantity_textfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantity_textfieldActionPerformed(evt);
            }
        });
        quantity_textfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                quantity_textfieldKeyTyped(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel19.setText("Facilitator");

        jLabel20.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel20.setText("Storeman");

        receive_error_msg_label.setForeground(java.awt.Color.red);

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel2.setText("Location");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(alert_image_display_01, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(receive_error_msg_label, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(receive_textfield, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(quantity_textfield)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(storemanDrop, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(facilitatorDrop, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(receive_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(storemanDrop, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(quantity_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(facilitatorDrop, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(alert_image_display_01, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(receive_error_msg_label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void receive_textfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receive_textfieldActionPerformed
        // TODO add your handling code here:
        receive_textfield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
        alert_image_display_01.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
        receive_error_msg_label.setText("");
        
        String item = "", faci_name = "";
        int learner_id = 0, faci_id = 0; 
        learner_id = Integer.parseInt(receive_textfield.getText().toString());
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
    }//GEN-LAST:event_receive_textfieldActionPerformed

    private void receive_textfieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_receive_textfieldKeyTyped
        receive_textfield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
        //jTextField8.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 11));
        //jTextField8.setForeground(Color.black);
        alert_image_display_01.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
        receive_error_msg_label.setText("");
    }//GEN-LAST:event_receive_textfieldKeyTyped

    private void quantity_textfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantity_textfieldActionPerformed
        // TODO add your handling code here:
        quantity_textfield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
        alert_image_display_01.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
        receive_error_msg_label.setText("");
    }//GEN-LAST:event_quantity_textfieldActionPerformed

    private void quantity_textfieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quantity_textfieldKeyTyped
        quantity_textfield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
        //jTextField9.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 11));
        //jTextField9.setForeground(Color.black);
        alert_image_display_01.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
        receive_error_msg_label.setText("");
    }//GEN-LAST:event_quantity_textfieldKeyTyped

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        jComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),0));
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
            java.util.logging.Logger.getLogger(Receiving.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Receiving.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Receiving.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Receiving.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Receiving().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel alert_image_display_01;
    public static javax.swing.JComboBox facilitatorDrop;
    public static javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel3;
    public static javax.swing.JTextField quantity_textfield;
    public static javax.swing.JLabel receive_error_msg_label;
    public static javax.swing.JTextField receive_textfield;
    public static javax.swing.JComboBox storemanDrop;
    // End of variables declaration//GEN-END:variables
}
