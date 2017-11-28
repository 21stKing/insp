/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory_system;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author tebogo
 */
public class NetWorkSetup extends javax.swing.JFrame {
    RunProgram rp;
    Network net;
    ArrayList<String> netListArray;
    ArrayList<String> activeNetListArray;
    private DefaultListModel<String> model = new DefaultListModel();
    private DefaultListModel<String> model4activeNet = new DefaultListModel();
    private String netName, activeNetName;
    public NetWorkSetup() 
    {
        initComponents();
        this.getContentPane().setBackground(Color.white);
        ArrayList<NetworkConfig> network = new ArrayList<>();
        try{
           DB db = new  DB(RunProgram.CONNECTION_MODE);
           network  = db.networkInfo();
           if(network.size() > 0){
            netName = network.get(0).getNetworkInterfaceName();     
           }
          }catch(Exception e){JOptionPane.showMessageDialog(null,e,"Failed to get Network Name!",JOptionPane.ERROR_MESSAGE);}
        
        currentNetwork_label.setText(netName);  
        net = new  Network();
        try {
            netListArray = net.getNetworkList();
        } catch (SocketException ex) {
            Logger.getLogger(NetWorkSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         for(int x = 0;x<netListArray.size();x++)
         {
            boolean isActive = false; 
            String item = netListArray.get(x); 
            model.addElement(item); //list of all network interfaces
            try{ isActive = networkTest(item);            
            }catch(SocketException ex){Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);};
            if(!isActive){
                //dont add anything
            }else{
                model4activeNet.addElement(item);
            }
         }
         
         activeNetworkList.setModel(model4activeNet);
         networkList.setModel(model); 
                
         networkList.addMouseMotionListener(new MouseAdapter(){
         public void mouseMoved(MouseEvent me){
         Point p = new Point(me.getX(),me.getY());
         networkList.setSelectedIndex(networkList.locationToIndex(p)); 
        }
        }); 
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        networkList = new javax.swing.JList();
        testButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        icon_label = new javax.swing.JLabel();
        msg_label = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        currentNetwork_label = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        activeNetworkList = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Network Configuration");
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jLabel1.setText("Please select a network interface below:");

        networkList.setBackground(new java.awt.Color(204, 204, 204));
        networkList.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        networkList.setSelectionBackground(java.awt.SystemColor.control);
        networkList.setSelectionForeground(new java.awt.Color(0, 0, 0));
        networkList.setVisibleRowCount(5);
        jScrollPane1.setViewportView(networkList);

        testButton.setText("Test");
        testButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        icon_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel2.setText("Current network");

        currentNetwork_label.setEditable(false);
        currentNetwork_label.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        loginButton.setText("Login");
        loginButton.setEnabled(false);
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        activeNetworkList.setBackground(new java.awt.Color(204, 204, 204));
        activeNetworkList.setSelectionBackground(java.awt.SystemColor.control);
        activeNetworkList.setSelectionForeground(new java.awt.Color(0, 0, 0));
        activeNetworkList.setVisibleRowCount(5);
        jScrollPane2.setViewportView(activeNetworkList);

        jLabel3.setText("Below are active and running networks:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(currentNetwork_label, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(testButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(loginButton))
                        .addGap(89, 89, 89))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(icon_label, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(msg_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(testButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loginButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(currentNetwork_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(icon_label, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(msg_label, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        //save network
        String network2Save = activeNetworkList.getSelectedValue().toString();
        
        try{
            DB db = new DB(RunProgram.CONNECTION_MODE);
            /**if(db.executeSQL("UPDATE network_config SET network_interface_name = '"+network2Save+"' AND userid = "+UserProfile.getUserID())){
               icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/success.png"))); 
               msg_label.setText("Network saved successfully!"); 
               currentNetwork_label.setText(network2Save);    
            }*/
            db.addBatch("UPDATE network_config SET network_interface_name = "+network2Save+" AND userid = "+UserProfile.getUserID());
            icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/success.png"))); 
            msg_label.setText("Network saved successfully!"); 
            currentNetwork_label.setText(network2Save); 
        }catch(Exception e){ 
            JOptionPane.showMessageDialog(null, ""+e, "Failed to save "+network2Save, JOptionPane.ERROR_MESSAGE);
            System.exit(0); 
        }
        
        
    }//GEN-LAST:event_saveButtonActionPerformed

    private void testButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testButtonActionPerformed
        //test network
        String network2Test = network2Test = activeNetworkList.getSelectedValue().toString();
               
        //check if network is up running
        boolean isRunning = false;
        try{ isRunning = networkTest(network2Test); 
        } catch (SocketException ex){Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);}
        
        if(isRunning == true){
            icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/success.png"))); 
            msg_label.setText("<html>Network <b>("+network2Test+")</b> is up and running!</html>"); 
            msg_label.setForeground(Color.green); 
            //enable login button
            loginButton.setEnabled(true);
        }else{
            icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png"))); 
            msg_label.setText("<html>Network <b>("+network2Test+")</b> is not running!</html>"); 
            msg_label.setForeground(Color.red); 
            loginButton.setEnabled(false);
        }
        
        
        
    }//GEN-LAST:event_testButtonActionPerformed
    
    protected boolean networkTest(String networkInterfaceName) throws SocketException{
        boolean isRunning = false;
        try{ isRunning = net.getState(networkInterfaceName); 
        } catch (SocketException ex){Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);}
        return isRunning;
    }
    
    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        
      PasswordRequest passwordGUI = new PasswordRequest();
      passwordGUI.setLocationByPlatform(true); 
      passwordGUI.setVisible(true);  
      this.setVisible(false); 
    }//GEN-LAST:event_loginButtonActionPerformed

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
            java.util.logging.Logger.getLogger(NetWorkSetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NetWorkSetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NetWorkSetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NetWorkSetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NetWorkSetup().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList activeNetworkList;
    private javax.swing.JTextField currentNetwork_label;
    private javax.swing.JLabel icon_label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel msg_label;
    private javax.swing.JList networkList;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton testButton;
    // End of variables declaration//GEN-END:variables
}
