
package inventory_system;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;



/**
 *
 * @author qxf3984
 */
public class Issue extends javax.swing.JInternalFrame {
     
    private Connection con;
    private ResultSet rs;
    private static int id;
    private static int role;
    private static String barcode;
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
    private Vector<Vector<String>> data; 
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
    protected static String [] listLevels;
    private static boolean jobRunning = true;
    private String helpURL = "http://localhost/kinotech/test.html";
    private static String screen = "MainWindow";
        
    public Issue() {
        initComponents();
        //setSize(840, 600); 
        setLocation(0, 0);  
        this.getContentPane().setBackground(Color.white);
        id = getUserId();
        role = getRole();
        barcode = getBarcode();
        
        //Set item by barcode or location
        if(!barcode.isEmpty()){
            setItemByBarcode(barcode);
        }else if(locations.getSelectedItem() != null || locations.getSelectedItem() != " "){
            
        }
        
        
        //Location
        try{ConnectDatabase database = new ConnectDatabase(); 
        locationList = database.getLocation(); String [] locationArray;locationArray = new String [locationList.length];
            for(int i=0;i < locationList.length;i++){ locationArray[i] = locationList[i];locations.addItem(locationArray[i]); }}
        catch(Exception e1){JOptionPane.showMessageDialog(null,e1,"ERROR IN LOCATION",JOptionPane.ERROR_MESSAGE);}
        
        //get role name
        try{ConnectDatabase database = new ConnectDatabase();rolename = database.getRole(role);  }
        catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"Cant get role name",JOptionPane.ERROR_MESSAGE);}
        
        switch (rolename) {
            case "Storeman":
                
                break;
            case "Facilitaotor":
                
                break;
            case "Administrator":
               
                break;
            case "Superuser":
                
                break;    
        }
 
        //course list
        /*try{ConnectDatabase database = new ConnectDatabase();listCourse = database.getListCourse(); String [] listCourseArray; listCourseArray = new String [listCourse.length];
            for(int i=0;i < listCourse.length;i++){ listCourseArray[i] = listCourse[i]; courseDrop.addItem(listCourseArray[i]);}}
        catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN COURSE LIST",JOptionPane.ERROR_MESSAGE);}
        
         //levels list
        try{ConnectDatabase database = new ConnectDatabase();listLevels = database.getListLevels(); String [] listLevelsArray; listLevelsArray = new String [listLevels.length];
            for(int i=0;i < listLevels.length;i++){ listLevelsArray[i] = listLevels[i]; levelsDrop.addItem(listLevelsArray[i]);}}
        catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN LEVELS LIST",JOptionPane.ERROR_MESSAGE);}*/
        
    }

    public static void setRole(int roleIn){
        role = roleIn;
    }
    
    public static void setUserId(int idIn){
        id = idIn;
    }
    
    protected static void setBarcode(String bar_code) {
        barcode = bar_code;
    }
        
    private int getRole(){
        return this.role;
    }
    
    private int getUserId(){
        return this.id;
    }
    
    private String getBarcode(){
        return this.barcode;
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        locations = new javax.swing.JComboBox();
        description = new javax.swing.JTextField();
        qty = new javax.swing.JTextField();
        avail = new javax.swing.JTextField();
        item_image = new javax.swing.JLabel();
        description_lb = new javax.swing.JLabel();
        qty_lb = new javax.swing.JLabel();
        avail_lb = new javax.swing.JLabel();
        screen_label = new java.awt.Label();
        item_img = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(MainWindow.home_desktop.getPreferredSize());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel2.setText("Location");

        locations.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        locations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locationsActionPerformed(evt);
            }
        });

        description.setEditable(false);
        description.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        description.setForeground(new java.awt.Color(135, 85, 85));
        description.setBorder(null);
        description.setDisabledTextColor(new java.awt.Color(51, 153, 255));
        description.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descriptionActionPerformed(evt);
            }
        });

        qty.setEditable(false);
        qty.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        qty.setForeground(new java.awt.Color(135, 85, 85));
        qty.setBorder(null);
        qty.setDisabledTextColor(new java.awt.Color(51, 153, 255));

        avail.setEditable(false);
        avail.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        avail.setForeground(new java.awt.Color(135, 85, 85));
        avail.setBorder(null);
        avail.setDisabledTextColor(new java.awt.Color(51, 153, 255));

        description_lb.setBackground(new java.awt.Color(255, 255, 255));
        description_lb.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        description_lb.setText("Description");
        description_lb.setOpaque(true);

        qty_lb.setBackground(new java.awt.Color(255, 255, 255));
        qty_lb.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        qty_lb.setText("Quantity");
        qty_lb.setOpaque(true);

        avail_lb.setBackground(new java.awt.Color(255, 255, 255));
        avail_lb.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        avail_lb.setText("Available");
        avail_lb.setOpaque(true);

        screen_label.setBackground(javax.swing.UIManager.getDefaults().getColor("CheckBox.shadow"));
        screen_label.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        screen_label.setForeground(new java.awt.Color(255, 255, 255));
        screen_label.setText("Issue");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(566, 566, 566)
                        .addComponent(item_image, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(avail_lb, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addComponent(qty_lb, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addComponent(description_lb, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(avail, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(qty, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(description, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(locations, 0, 400, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(item_img, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(80, 80, 80))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(screen_label, javax.swing.GroupLayout.PREFERRED_SIZE, 814, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(screen_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(locations, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(description)
                            .addComponent(description_lb, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(qty_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(avail_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(avail, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(item_img, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(59, 59, 59)
                .addComponent(item_image, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        locations.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(228, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void descriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descriptionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_descriptionActionPerformed

    private void locationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locationsActionPerformed
        locations.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),0));
    }//GEN-LAST:event_locationsActionPerformed
    
    //set item by barcode
    public void setItemByBarcode(String itembarcode){
          ArrayList<Barcode> barcode = new ArrayList<>();
          ArrayList<Item> item = new ArrayList<>();
          ArrayList<Iteminventory> iteminv = new ArrayList<>();
          ArrayList<Location> location = new ArrayList<>();
          ImageIcon image;
          try{
                DB db = new DB(RunProgram.CONNECTION_MODE);
                barcode  = db.getBarcode(itembarcode);
                data     = db.getItemByBarcode(itembarcode);
                iteminv  = db.getItemInventory(item.get(0).getItemid());
                location = db.getLocation(item.get(0).getLocationid());
                int imageid = item.get(0).getImageid();
                if(imageid <= 0){
                    
                }else{
                    image    = db.getImage(item.get(0).getImageid());
                    //Resize the image icon and set image
                    Image img = image.getImage();
                    BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
                    Graphics g = bi.createGraphics();
                    Dimension d = item_img.getPreferredSize();  // uses the photo label's width and height
                    int x = d.width, y = d.height;
                    g.drawImage(img,0,0, x, y,null);
                    //g.drawImage(img,0,0, img.getWidth(null), img.getHeight(null),null);  this gets original image pixels
                    ImageIcon newIcon = new ImageIcon(bi);
                    item_img.setIcon(newIcon); // Image
                    item_img.setHorizontalAlignment(SwingConstants.CENTER);
                }
                
                //item = db.get_table_records_sql("barcode", "barcode="+itembarcode);
                //String bcodeid = item.get(0).get(0);
                //item.clear();
                //item  = db.getItemByBarCodeID(bcodeid);
                //location = db.get_table_record_sql("location", "locaname", "where locationid="+item.get(0).get(7)); 
                setCheckData(item.get(0).getItemdescription(), iteminv.get(0).getCurrqty()+"",iteminv.get(0).getTotalqty()+"" ,location.get(0).getLocname());  //description, quantity, available, location
               
             }
          catch (Exception e1)
            {
                JOptionPane.showMessageDialog(null, "<html>"+e1+"</html>", "Error - Item Barcode", 1);
            }
            //setItemImage(ID);  
    }
    
    public void setItemImage(int idIn) {
         //get image url
         File file     = new File("\\\\africa.bmw.corp\\winfs\\ZA-data\\ZA-P\\ZA-P-5\\ZA-P-50\\ZA-P-503\\Share\\1.2 Projects\\2012\\Tebogo Kekana\\Images\\"+idIn+".jpg");
         String filePath = file.toString();
         URL image_url = null;
            
         try
         {
           //image_url = new File("\\\\africa.bmw.corp\\winfs\\ZA-data\\ZA-P\\ZA-P-5\\ZA-P-50\\ZA-P-503\\Share\\1.2 Projects\\2012\\Tebogo Kekana\\Images\\"+idIn+".jpg").toURI().toURL(); 
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
              item_image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/0.jpg"))); 
           }
           else
           {   
             //Resize the image icon
             Image img = icon.getImage();
             BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
             Graphics g = bi.createGraphics();
             g.drawImage(img,0,0, 75, 65,null);
             ImageIcon newIcon = new ImageIcon(bi);
             item_image.setIcon(newIcon); // Image
             item_image.setHorizontalAlignment(SwingConstants.CENTER);
           } 
  }  
    
    public void setCheckData(String descrip, String quantity, String available, String location)  {
        description.setText(descrip);
        qty.setText(quantity);
        avail.setText(available);
        locations.setSelectedItem(location);
  }

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
            java.util.logging.Logger.getLogger(Issue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Issue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Issue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Issue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Issue().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTextField avail;
    private javax.swing.JLabel avail_lb;
    public static javax.swing.JTextField description;
    private javax.swing.JLabel description_lb;
    public static javax.swing.JLabel item_image;
    public static javax.swing.JLabel item_img;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JComboBox locations;
    public static javax.swing.JTextField qty;
    private javax.swing.JLabel qty_lb;
    protected java.awt.Label screen_label;
    // End of variables declaration//GEN-END:variables
}
