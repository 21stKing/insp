
package inventory_system;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author qxf3984
 */
public class Version extends JDialog implements ActionListener,PropertyChangeListener {

    private JOptionPane optionPane;
    public Object value = "";
    
    public Version(JFrame aFrame) 
    {
        super(aFrame, true);
        initComponents();
        //jLabel5.setText("\u00A9");
        jLabel5.setText("<html>&copy 2014 kinotech Pty Ltd.</html>");
        this.getContentPane().setBackground(Color.white);
        setTitle("About InSP");
        
        //Create the JOptionPane.
        optionPane = new JOptionPane(this.getContentPane(),JOptionPane.PLAIN_MESSAGE,JOptionPane.PLAIN_MESSAGE,null);
        optionPane.setBackground(Color.white); 

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
    
    public void propertyChange(PropertyChangeEvent e)
    {
        String prop = e.getPropertyName();    
        
        if (isVisible() && (e.getSource() == optionPane) && (JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) 
        {
          value = optionPane.getValue();
         
          if (value == JOptionPane.UNINITIALIZED_VALUE)
            {
                //ignore reset
                return;
            }
            //Reset the JOptionPane's value.
            //If you don't do this, then if the user
            //presses the same button next time, no
            //property change event will be fired.
           // optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
          
        }
        
        clearAndHide();
            
        
    }
    
       /** This method clears the dialog and hides it. */
    public void clearAndHide()
    {
        setVisible(false);
    } 

    private Version() {throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/inventory_system/image/insp_icon_demo_39x45.png")).getImage());
        setLocationByPlatform(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jLabel1.setBackground(new java.awt.Color(0, 102, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/insp_icon_demo_39x45.png"))); // NOI18N
        jLabel1.setOpaque(true);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Inventory Store Program");

        jLabel3.setText("Version 1.2.1.131107_7");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/kinotech39x49.png"))); // NOI18N

        jLabel5.setForeground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(74, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(159, 159, 159))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Version.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Version.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Version.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Version.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Version().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
