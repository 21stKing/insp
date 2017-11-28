/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory_system;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

/**
 *
 * @author QXF3984
 */
public class PopMessageList extends javax.swing.JFrame {

    ArrayList<String> new_msg_list;
    private DefaultListModel<String> model = new DefaultListModel();;
    
    
    public PopMessageList() {
        initComponents();
        this.getContentPane().setBackground(Color.white);
        msgList.setBackground(new java.awt.Color(255,255,225));
        
        try{
         ConnectDatabase database = new  ConnectDatabase();
         new_msg_list = database.getMsgList();
         for(int x = 0;x<new_msg_list.size();x++){
            int value = (new_msg_list.indexOf(new_msg_list.get(x)))+1; 
            String item = "("+value+") "+new_msg_list.get(x); 
             model.addElement(item);}
             msgList.setModel(model); 
         }catch(Exception e1){JOptionPane.showMessageDialog(null,e1,"Message List Error!",JOptionPane.ERROR_MESSAGE);}     
        
        msgList.addMouseMotionListener(new MouseAdapter(){
        public void mouseMoved(MouseEvent me){
         Point p = new Point(me.getX(),me.getY());
         msgList.setSelectedIndex(msgList.locationToIndex(p)); 
        }
        }); 
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        msgList = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setType(java.awt.Window.Type.UTILITY);

        jLabel1.setBackground(new java.awt.Color(255, 255, 225));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("New notification(s)");
        jLabel1.setOpaque(true);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 225));

        msgList.setForeground(new java.awt.Color(0, 0, 255));
        msgList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                msgListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(msgList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void msgListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_msgListMouseClicked
        // get element clicked on list
        PopMessage p = new PopMessage(""+msgList.getSelectedValue());
        p.setLocationByPlatform(true); 
        p.setVisible(true); 
        this.setVisible(false); 
    }//GEN-LAST:event_msgListMouseClicked

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
            java.util.logging.Logger.getLogger(PopMessageList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PopMessageList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PopMessageList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PopMessageList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PopMessageList().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList msgList;
    // End of variables declaration//GEN-END:variables
}
