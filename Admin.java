
package inventory_system;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author qxf3984
 */
public class Admin extends JDialog implements ActionListener,PropertyChangeListener 
{
  private static int id;
  private static int role;
  Date logtime;
  public static String name;
  public static String surname;
  public static String[] locationList;
  private Connection con;
  private ResultSet rs;
  ResultSetMetaData md;
  private DefaultTableModel model_one;
  public static int[] tableRowSelected;
  public static int[] rowSelected;
  private Vector<Vector<String>> data;
  private Vector<String> header;
  private Object ID;
  public static int[] tableRowSelected2;
  private Vector<Vector<String>> data2;
  private Vector<String> header2;
  private Object ID2;
  private static String file_name;
  private static String fullPathName;
  private static String file_directory;
  private static String image_name;
  private static String fullPathImage;
  private static String image_directory;
  private JScrollPane jScrollPane2;
  private JOptionPane optionPane;
  private String finishBtn = "Finish";
  private String okBtn = "Ok";
  public Object value = "";
  NoticePanel np;
  UsersGUI usr;
  Items st;
  Learners lf;
  Inconsistency inco;
  PendingItems pItems;
  AdminWelcomeScreen aws;
    
    public Admin(JFrame aFrame,int idin, int roleIn) 
    {  
        super(aFrame, true);
        //this.setSize(970,790);
        
        id = idin;
        role = roleIn;
        logtime = new Date(); 
        initComponents();
        AdminWelcomeScreenInternalFrame();
        this.getContentPane().setBackground(Color.white);
        setResizable(false);
        
        setTitle("Administration");

        //Create an array specifying the number of dialog buttons
        //and their text.
        Object[] options = {okBtn,finishBtn};

        //Create the JOptionPane.
        optionPane = new JOptionPane(this.getContentPane(),JOptionPane.PLAIN_MESSAGE,JOptionPane.OK_CANCEL_OPTION,null,options,options[0]);
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
        optionPane.setValue(okBtn);
    }

    /** This method reacts to state changes in the option pane. */

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
     

    private Admin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        navigation_path_label = new javax.swing.JLabel();
        desktop = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        file = new javax.swing.JMenu();
        menuItemImport = new javax.swing.JMenuItem();
        menuItemSave = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuItemBack = new javax.swing.JMenuItem();
        view = new javax.swing.JMenu();
        items = new javax.swing.JMenu();
        modify = new javax.swing.JMenuItem();
        pending = new javax.swing.JMenuItem();
        inconsistant = new javax.swing.JMenuItem();
        learners = new javax.swing.JMenu();
        modify_leaners = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/inventory_system/image/insp_icon_demo_39x45.png")).getImage());
        setType(java.awt.Window.Type.POPUP);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Home");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Notifications");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Notice Board");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Users");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Storeman/Facilitator");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Items");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Modifications");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Check for inconsistency");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Pending Items");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Learners");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("All Learners");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        navigation_path_label.setBackground(new java.awt.Color(255, 255, 255));
        navigation_path_label.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        navigation_path_label.setForeground(new java.awt.Color(153, 153, 153));

        desktop.setBackground(new java.awt.Color(255, 255, 255));

        file.setText("File");

        menuItemImport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/import.png"))); // NOI18N
        menuItemImport.setText("Import");
        menuItemImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemImportActionPerformed(evt);
            }
        });
        file.add(menuItemImport);

        menuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuItemSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/save.png"))); // NOI18N
        menuItemSave.setText("Save");
        menuItemSave.setEnabled(false);
        file.add(menuItemSave);
        file.add(jSeparator1);

        menuItemBack.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_BACK_SPACE, 0));
        menuItemBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/back.png"))); // NOI18N
        menuItemBack.setText("Back");
        menuItemBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemBackActionPerformed(evt);
            }
        });
        file.add(menuItemBack);

        menuBar.add(file);

        view.setText("View");

        items.setText("Items");

        modify.setText("Modify Item(s)");
        modify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyActionPerformed(evt);
            }
        });
        items.add(modify);

        pending.setText("Pending Item(s)");
        pending.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pendingActionPerformed(evt);
            }
        });
        items.add(pending);

        inconsistant.setText("Inconsistancy");
        inconsistant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inconsistantActionPerformed(evt);
            }
        });
        items.add(inconsistant);

        view.add(items);

        learners.setText("Learners");

        modify_leaners.setText("Modify");
        modify_leaners.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modify_leanersActionPerformed(evt);
            }
        });
        learners.add(modify_leaners);

        view.add(learners);

        menuBar.add(view);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(navigation_path_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(desktop, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(navigation_path_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                    .addComponent(desktop))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
    String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
    String parentNode = evt.getNewLeadSelectionPath().toString();
    
    switch (node)
    {
    case "Notice Board":
        createNoticeBoardFrame();
        navigation_path_label.setText(parentNode); 
      break;
    case "All Learners":
        createLearnersFrame();
        navigation_path_label.setText(parentNode);
      break;
    case "Modifications": 
         createItemsFrame();
         navigation_path_label.setText(parentNode);
      break;    
    case "Check for inconsistency": 
         createInconsistencyFrame();
         navigation_path_label.setText(parentNode);
      break;
    case "Pending Items":
         createPendingItemsFrame();
         navigation_path_label.setText(parentNode);
      break;
    case "Users":
          createUsersWindow();
          navigation_path_label.setText(parentNode);
      break;
    case "Storeman/Facilitator":
        createUsersWindow();
        navigation_path_label.setText(parentNode);
      break;  
    case "Home":
        AdminWelcomeScreenInternalFrame();
        navigation_path_label.setText(parentNode);
      break;      
    }
    }//GEN-LAST:event_jTree1ValueChanged

    private void menuItemImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemImportActionPerformed
         Learners ln = new Learners(id,role);
         ln.file_browser();
         ln.importLearners();
    }//GEN-LAST:event_menuItemImportActionPerformed

    private void menuItemBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemBackActionPerformed
        setVisible(false);
        
    }//GEN-LAST:event_menuItemBackActionPerformed

    private void modifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyActionPerformed
        createItemsFrame();
    }//GEN-LAST:event_modifyActionPerformed

    private void pendingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pendingActionPerformed
        createPendingItemsFrame();
    }//GEN-LAST:event_pendingActionPerformed

    private void inconsistantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inconsistantActionPerformed
      createInconsistencyFrame();
    }//GEN-LAST:event_inconsistantActionPerformed

    private void modify_leanersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modify_leanersActionPerformed
         createLearnersFrame();
    }//GEN-LAST:event_modify_leanersActionPerformed

    
   protected void AdminWelcomeScreenInternalFrame(){
    this.aws = new AdminWelcomeScreen();
    this.aws.setVisible(true);
    ((BasicInternalFrameUI)this.aws.getUI()).setNorthPane(null);
    this.desktop.add(this.aws);
    try {
      this.aws.setSelected(true);
    }
    catch (PropertyVetoException e) {
    }
  }
    
   protected void createNoticeBoardFrame()
  {
    this.np = new NoticePanel(id, role);
    this.np.setVisible(true);
    ((BasicInternalFrameUI)this.np.getUI()).setNorthPane(null);
    this.desktop.add(this.np);
    try {
      this.np.setSelected(true);
    }
    catch (PropertyVetoException e) {
    }
  }
  
  protected void createUsersWindow()
  {
       
      usr = new UsersGUI(id,role);
      usr.setVisible(true);
      ((BasicInternalFrameUI)this.usr.getUI()).setNorthPane(null);
    this.desktop.add(this.usr);
    try {
      this.usr.setSelected(true);
    }
    catch (PropertyVetoException e) {
    }
  }
  
  protected void createPendingItemsFrame()
  {
    pItems = new PendingItems();
    pItems.setVisible(true);
    ((BasicInternalFrameUI)this.pItems.getUI()).setNorthPane(null);
    this.desktop.add(this.pItems);
    try {
       pItems.setSelected(true);
    }
    catch (PropertyVetoException e) {
    }
  }
  
  protected void createLearnersFrame()
  {
      lf = new Learners(id, role);
      lf.setVisible(true);
      ((BasicInternalFrameUI)this.lf.getUI()).setNorthPane(null);
     this.desktop.add(lf);
     try {
       lf.setSelected(true);
      }
     catch (PropertyVetoException e) {
     }
  }
  
  protected void createItemsFrame()
  {
       
     st = new Items();
     st.setVisible(true);
     ((BasicInternalFrameUI)this.st.getUI()).setNorthPane(null);
      this.desktop.add(st);
     try {
       st.setSelected(true);
      }
     catch (PropertyVetoException e) {
     }
       
   }
  
  protected void createInconsistencyFrame()
  {
     inco = new Inconsistency(id, role);
     inco.setVisible(true);
     ((BasicInternalFrameUI)this.inco.getUI()).setNorthPane(null);
      this.desktop.add(inco);
     try {
       inco.setSelected(true);
      }
     catch (PropertyVetoException e) {
     }
      
  }   
     
  protected void closeInconsistencyFrame()
  {
    inco.dispose();
    inco.doDefaultCloseAction();
  } 
  
  protected void closeLearnersFrame()
  {
    lf.dispose();
  } 
  
  protected void closeItemsFrame()
  {
    this.st.dispose();
  } 

  protected void closeNoticeBoardFrame() 
  {
    this.np.dispose();
  }
  
  protected void closePendingItemsFrame() 
  {
    this.pItems.dispose();
  }
  
  protected void closeUsersWindow()
  {
      usr.dispose();
  }
    
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
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JMenu file;
    private javax.swing.JMenuItem inconsistant;
    private javax.swing.JMenu items;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTree jTree1;
    private javax.swing.JMenu learners;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuItemBack;
    private javax.swing.JMenuItem menuItemImport;
    private javax.swing.JMenuItem menuItemSave;
    private javax.swing.JMenuItem modify;
    private javax.swing.JMenuItem modify_leaners;
    private javax.swing.JLabel navigation_path_label;
    private javax.swing.JMenuItem pending;
    private javax.swing.JMenu view;
    // End of variables declaration//GEN-END:variables
}
