/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory_system;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.*;
import static inventory_system.TrolleyGUI.basketCount;


 
 

public class TheTrolley extends javax.swing.JFrame 
{
    
    private static int id;
    private static int role;
    Date logtime;
    public static int []  rowSelected;
    private Vector<Vector<String>> data; 
    private Vector<String> header;
    private DefaultTableModel model;
    TableColumn tc;
    Date datehelp;
    SimpleDateFormat sdfFrom = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    SimpleDateFormat sdfTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static int basketCount = 0;
    private static TrolleyGUI tg;
    public static ArrayList<Trolley> items;
    private static int NUM_IMAGES = 0;
    ImageIcon[] images;
    private String [][] descOfItems;
    private int idsOfImages[];
    private int i = 0;
    private int locationID = 0; 
    private String itemDescrip = "";
    private String itemQuantity = "";
    private String description[][];
    protected static int totalQuantity = 0;
    private String screen;
    
    public TheTrolley(int idin, int roleIn, String window) 
    {
        id      = idin;
        role = roleIn;
        screen = window;
        logtime = new Date();
        datehelp = null;
        model = new javax.swing.table.DefaultTableModel(data,header);
        //create headers for search table
        header = new Vector<String>();
        header.add("ItemID");
        header.add("Store");
        header.add("Location / Bin");
        header.add("Description");
        header.add("#Quantity");
        header.add("Trolley");
       
        initComponents();
        this.getContentPane().setBackground(Color.white);  //sets layout background color to white
        
        Vector<Vector<String>>trolleyVector = new Vector<Vector<String>>();
        
        items = tg.trolleyItems;
        
        for(int x = 0;x < items.size();x++)
        {   
            Vector<String> list = new Vector<String>();
            list.add(""+items.get(x).getItem_id()); 
            list.add(items.get(x).getStore()); 
            list.add(items.get(x).getLocation()); 
            list.add(items.get(x).getDescription()); 
            list.add(items.get(x).getQuantity()); 
            trolleyVector.add(list);
            totalQuantity += Integer.parseInt(items.get(x).getQuantity());
         }
        //populate the tables with data
        data  = trolleyVector;
        trolleyTable.setModel(new javax.swing.table.DefaultTableModel(data,header));  
        
       //now view Images of items in trolley
        idsOfImages = new int[items.size()];
        description = new String[items.size()][2];

        for(int y = 0;y< items.size();y++)
        { 
            try
                {
                  ConnectDatabase database = new ConnectDatabase();
                  locationID = database.getID(items.get(y).getLocation());
                  itemDescrip = database.getCheckData(locationID, "Description");
                  itemQuantity = items.get(y).getQuantity();
                  database.setCloseDatabase();
                }
                catch (Exception e1)                                             
                {
                  System.out.println(e1);
                }
                idsOfImages[y] = locationID;
                for(int z=0;z<1;z++)
                {
                 description[y][z]     = itemDescrip;
                 description[y][z + 1] = itemQuantity;
                }
                 
        }     
           
           slideShow(items.size(),idsOfImages,description);
           //Add delete button on the rows of each item in table 
           trolleyTable.getColumn("Trolley").setCellRenderer(new DeleteButtonRenderer()); 
           trolleyTable.getColumn("Trolley").setCellEditor(new DeleteButtonEditor(new JCheckBox()));
    }

    public TheTrolley() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        previousLabel = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        trolleyTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        descriptionLabel = new javax.swing.JLabel();
        quantityLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        imageIconLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        totalLabel = new javax.swing.JLabel();

        jLabel5.setText("jLabel5");

        jLabel4.setText("jLabel4");

        jLabel6.setText("jLabel6");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Trolley | Contents");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));

        previousLabel.setBackground(java.awt.Color.white);
        previousLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/previous.png"))); // NOI18N
        previousLabel.setToolTipText("Procced to issue");
        previousLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                previousLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                previousLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                previousLabelMouseExited(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        trolleyTable.setModel(new javax.swing.table.DefaultTableModel(data,header));
        trolleyTable.setAlignmentX(11.0F);
        trolleyTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                trolleyTableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(trolleyTable);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Trolley Contents", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 11))); // NOI18N

        jLabel1.setText("Description:");

        jLabel3.setText("Quantity:");

        descriptionLabel.setBackground(new java.awt.Color(255, 255, 255));
        descriptionLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        descriptionLabel.setForeground(new java.awt.Color(135, 85, 85));
        descriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        quantityLabel.setBackground(new java.awt.Color(255, 255, 255));
        quantityLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        quantityLabel.setForeground(new java.awt.Color(135, 85, 85));
        quantityLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel2.setBackground(new java.awt.Color(229, 229, 229));
        jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));

        imageIconLabel.setBackground(java.awt.Color.white);
        imageIconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageIconLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(291, 291, 291)
                .addComponent(imageIconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imageIconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        jLabel7.setText("Total:");

        totalLabel.setBackground(new java.awt.Color(255, 255, 255));
        totalLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        totalLabel.setForeground(new java.awt.Color(135, 85, 85));
        totalLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(descriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(quantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(totalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jLabel1)
                        .addGap(205, 205, 205)
                        .addComponent(jLabel3)
                        .addGap(116, 116, 116)
                        .addComponent(jLabel7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(descriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(totalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(25, 25, 25))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 746, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(previousLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 601, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(previousLabel)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
   
    
    public void slideShow(int size,int locationIds[], String desc[][])  {
        NUM_IMAGES = size;
        images     = new ImageIcon[NUM_IMAGES];
        descOfItems = desc;    
        totalLabel.setText(""+totalQuantity);
        //Get the images and put them into an array of ImageIcons.
        for (i = 0; i < NUM_IMAGES; i++) 
            {  
              images[i] = createImageIcon("\\\\africa.bmw.corp\\winfs\\ZA-data\\ZA-P\\ZA-P-5\\ZA-P-50\\ZA-P-503\\Share\\1.2 Projects\\2012\\Tebogo Kekana\\Images\\"+ locationIds[i] + ".jpg");
            }
        
        
        
        ActionListener taskPerformer = new ActionListener()
        {
           public void actionPerformed(ActionEvent evt) 
           {
             Next();
           }
        };
        new Timer(4000, taskPerformer).start();

    }
    void Next()  {
        i++;
     if(i>NUM_IMAGES-1) {
      i=0;
      
     }
     imageIconLabel.setIcon(images[i]);
     
      for(int z=0;z<1;z++)
          {
            descriptionLabel.setText(descOfItems[i][z]); 
            quantityLabel.setText(descOfItems[i][z+1]);    
          }
     
    }
    void Back() {
      i--;
     if(i<0) {
      i=NUM_IMAGES-1;
     }
     imageIconLabel.setIcon(images[i]);
     
      for(int z=0;z<1;z++)
          {
            descriptionLabel.setText(descOfItems[i][z]); 
            quantityLabel.setText(descOfItems[i][z+1]);    
          }
       
    } 
    protected ImageIcon createImageIcon(String path)  {
        /* Returns an ImageIcon, or null if the path was invalid. */
        //get image url
         File file     = new File(path);
         String filePath = file.toString();
         URL image_url = null;
            
         try
         {
           image_url = new File(filePath).toURI().toURL(); 
         }
         catch(MalformedURLException ex)
         {
           //Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,ex,"Invalid Path!", 3);  
            
         }
           
         //get Icon
         ImageIcon icon = null;  
         try 
          {
            icon = new ImageIcon(new java.net.URL(""+image_url));
          }
         catch (MalformedURLException ex)
          {
            //Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,ex,"Invalid Path!", 3);    
          }
           
           if(!file.exists())  //if image doesnt exists then display no photo image
           {
              imageIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/0.jpg"))); 
              return null;
           }
           else
           {   
             //Resize the image icon
             java.awt.Image img = icon.getImage();
             BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
             Graphics g = bi.createGraphics();
             g.drawImage(img,0,0, 75, 65,null);
             
             return new ImageIcon(bi);
             
           } 
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // cancel
         switch (screen)
        {
            case "MainWindow":
                //MainWindow main = new MainWindow(id,role,logtime);
                //main.setVisible(true);
                this.setVisible(false);       
                break;
            case "TrolleyGUI":
                //TrolleyGUI tg = new TrolleyGUI(new JFrame(),id,role);
                tg.setVisible(true);
                this.setVisible(false);       
                break;
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void previousLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_previousLabelMouseClicked
        // Back
        switch (screen)
        {
            case "MainWindow":
                this.setVisible(false);
                //MainWindow.items_count_label.setText(""+basketCount); 
                break;
            case "TrolleyGUI":
                //TrolleyGUI tg = new TrolleyGUI(new JFrame(),id,role);
                tg.setVisible(true);
                this.setVisible(false);       
                break;
        }
    }//GEN-LAST:event_previousLabelMouseClicked

    private void trolleyTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trolleyTableMousePressed
        // mouse listener on row selected
      
     if ((evt.getClickCount() == 2) && (!evt.isMetaDown()))
      {
        int row = TheTrolley.this.trolleyTable.getSelectedRow();
        String locationdata = null;
        String descriptiondata = null;
        String storedata = null;
        String currentdata = null;
       
        try
        {
          ConnectDatabase database = new ConnectDatabase();

          String location = (String)TheTrolley.this.trolleyTable.getValueAt(row, 2);
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
    }//GEN-LAST:event_trolleyTableMousePressed

    private void previousLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_previousLabelMouseEntered
        previousLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/previous2.png"))); 
    }//GEN-LAST:event_previousLabelMouseEntered

    private void previousLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_previousLabelMouseExited
         previousLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/previous.png"))); 
    }//GEN-LAST:event_previousLabelMouseExited

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
            java.util.logging.Logger.getLogger(TheTrolley.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TheTrolley.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TheTrolley.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TheTrolley.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TheTrolley().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JLabel imageIconLabel;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel previousLabel;
    private javax.swing.JLabel quantityLabel;
    protected static javax.swing.JLabel totalLabel;
    private javax.swing.JTable trolleyTable;
    // End of variables declaration//GEN-END:variables
}


class DeleteButtonRenderer extends JButton implements TableCellRenderer 
{

  public DeleteButtonRenderer() {
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


class DeleteButtonEditor extends DefaultCellEditor 
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
  private int rowToDelete; 
  public Trolley trolley;
 
  
  public DeleteButtonEditor(JCheckBox checkBox) {
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
      quantity     = (String) table.getValueAt(row,4);
      rowToDelete  = row;
      
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
    label = "Delete";
    button.setText(label);
    isPushed = true;
    return button;
  }
    
  public Object getCellEditorValue() {
    if (isPushed) 
    {
      //TrolleyGUI.addToBasket(itemId, store, location, description, currentStock, quantity); 
      //TrolleyGUI.trolleyItems.set(rowToDelete,new Trolley(itemId,store,location,description,currentStock,"1")); //ArrayList<Trolley>(new Trolley()));
      TrolleyGUI.removeFromBasket(rowToDelete);
      TheTrolley.totalQuantity -= 1;   //same as backetCount = basketCount - 1;
      TheTrolley.totalLabel.setText(""+TheTrolley.totalQuantity);        
      TheTrolley.items.remove(rowToDelete); 
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


