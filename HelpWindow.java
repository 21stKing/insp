
package inventory_system;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 *
 * @author qxf3984
 */
public class HelpWindow extends JFrame implements HyperlinkListener, ActionListener
{
    private String initialURL;
    
    public HelpWindow(String initURL) 
    {           
        
        this.initialURL = initURL;
        addWindowListener(new ExitListener());
        WindowUtilities.setNativeLookAndFeel();
        initComponents(); 
        try {
              editorPane = new JEditorPane(initialURL);
              editorPane.addHyperlinkListener(this);
        } catch (IOException ex) {
            Logger.getLogger(HelpWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }

    private HelpWindow() {
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

        jToolBar = new javax.swing.JToolBar();
        backButton = new javax.swing.JButton();
        forwardButton = new javax.swing.JButton();
        printButton = new javax.swing.JButton();
        jTabbedPane = new javax.swing.JTabbedPane();
        contentsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        searchPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        find_input = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        editorPane = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Help");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jToolBar.setFloatable(false);
        jToolBar.setRollover(true);

        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/help_back.png"))); // NOI18N
        backButton.setToolTipText("Previous page");
        backButton.setFocusable(false);
        backButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        backButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        jToolBar.add(backButton);

        forwardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/help_forward.png"))); // NOI18N
        forwardButton.setToolTipText("Next page");
        forwardButton.setFocusable(false);
        forwardButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        forwardButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar.add(forwardButton);

        printButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/printer.png"))); // NOI18N
        printButton.setToolTipText("Print");
        printButton.setFocusable(false);
        printButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        printButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar.add(printButton);

        jTabbedPane.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Inventory Store Program  Help");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("What is ISP?");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Getting started");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("System requirements");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("ISP screen layout");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Tools");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("basketball");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Equipments");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("hot dogs");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Consumables");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("hot dogs");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(jTree1);

        javax.swing.GroupLayout contentsPanelLayout = new javax.swing.GroupLayout(contentsPanel);
        contentsPanel.setLayout(contentsPanelLayout);
        contentsPanelLayout.setHorizontalGroup(
            contentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
        );
        contentsPanelLayout.setVerticalGroup(
            contentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("Contents", contentsPanel);

        searchPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Find:");

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(find_input, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(find_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 670, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Search", searchPanel);

        editorPane.setEditable(false);
        editorPane.setContentType("text/html"); // NOI18N
        editorPane.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
            public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {
                editorPaneHyperlinkUpdate(evt);
            }
        });
        jScrollPane2.setViewportView(editorPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane)
                    .addComponent(jScrollPane2)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here://
    }//GEN-LAST:event_backButtonActionPerformed
    
   /** private String setContent(String docPath){
        
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(docPath));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) { e.printStackTrace();
        }
        String content = contentBuilder.toString();
        
        return content;
    }*/
    
    public void actionPerformed(ActionEvent event) {
        String url = "http://localhost/kinotech/test.html";
        try {
          editorPane.setPage(new URL(url));
        } catch(IOException ioe) {
          JOptionPane.showMessageDialog(this,"Can't follow link to "+url + ": " + ioe, "URL Adress Error", JOptionPane.ERROR_MESSAGE);
        }
  }    
    
    private void editorPaneHyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {//GEN-FIRST:event_editorPaneHyperlinkUpdate
      if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            try 
            {
              editorPane.setPage(evt.getURL());
            } catch(IOException ioe) 
            {
                JOptionPane.showMessageDialog(this,"Can't follow link to "+evt.getURL().toExternalForm() + ": " + ioe, "URL Adress Error", JOptionPane.ERROR_MESSAGE);
            }
    }
    }//GEN-LAST:event_editorPaneHyperlinkUpdate

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
            java.util.logging.Logger.getLogger(HelpWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HelpWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HelpWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HelpWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HelpWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JPanel contentsPanel;
    private javax.swing.JEditorPane editorPane;
    private javax.swing.JTextField find_input;
    private javax.swing.JButton forwardButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JToolBar jToolBar;
    private javax.swing.JTree jTree1;
    private javax.swing.JButton printButton;
    private javax.swing.JPanel searchPanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void hyperlinkUpdate(HyperlinkEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}