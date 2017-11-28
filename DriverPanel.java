/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inventory_system;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class DriverPanel extends javax.swing.JPanel {

    private static String file_name;
    private static String fullPathName;
    private static String file_directory;
    private static String classname;
    private static String CLASS_PATH;
    static Server server;
    
    public DriverPanel()
    {
        initComponents();
        this.setBackground(new Color(226,223,214));
        
    }

    
   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        driverdrop = new javax.swing.JComboBox();
        driverpath = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        driverclass = new javax.swing.JTextField();
        drivername = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(564, 240));

        driverdrop.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        driverpath.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        addButton.setText("Add...");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        driverclass.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        drivername.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        jLabel1.setText("Driver:");
        jLabel1.setPreferredSize(new java.awt.Dimension(61, 14));

        jLabel2.setText("Driver File:");
        jLabel2.setPreferredSize(new java.awt.Dimension(61, 14));

        jLabel3.setText("Driver Class:");

        jLabel4.setText("Name:");
        jLabel4.setPreferredSize(new java.awt.Dimension(61, 14));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(driverdrop, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(driverpath)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(driverclass)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(drivername, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addComponent(addButton)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(driverdrop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(driverpath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(driverclass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(drivername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(123, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        file_browser();
    }//GEN-LAST:event_addButtonActionPerformed

    public void file_browser(){
        // Browse for file, open dialog
        //Open dialog
        JFileChooser c = new JFileChooser();
        c.addChoosableFileFilter(new FileNameExtensionFilter("Archive Files(*.jar)","*.jar"));
        c.setAcceptAllFileFilterUsed(true);
        int rVal = c.showOpenDialog(DriverPanel.this);

        if (rVal == JFileChooser.APPROVE_OPTION)
        {
            File file      = c.getSelectedFile();
            String filepath = "file:////"+file;
            try {
                //Load driver to classpath. if you do not add the driver file
                //to the programs class path then you will get a ClassNotFound Exception
                URLClassLoader loader = (URLClassLoader)ClassLoader.getSystemClassLoader();
                LoadClassPath lcp = new LoadClassPath(loader.getURLs());
                lcp.addURL(new URL(filepath));
                //get driver class name
                getDriverClass(file);
            } catch (IOException ex) {
                Logger.getLogger(DriverPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            file_name      = file.getName();
            file_directory = c.getCurrentDirectory().toString();
            fullPathName   = file_directory+File.separator+file_name;
        }

        driverpath.setText(fullPathName);

        if (rVal == JFileChooser.CANCEL_OPTION) {
            driverpath.setText("");
        }
        
    }   
    
    public void getDriverClass(File jar) throws IOException
    {
        List<String> classNames = new ArrayList<>();
        ZipInputStream zip      = null;
        try {
            zip = new ZipInputStream(new FileInputStream(jar));
        } catch (FileNotFoundException ex) {ex.printStackTrace(); }
        
        for(ZipEntry entry=zip.getNextEntry();entry!=null;entry=zip.getNextEntry())
            if(entry.getName().endsWith(".class") && !entry.isDirectory()) 
              {
                // This ZipEntry represents a class. Now, what class does it represent?
                StringBuilder className=new StringBuilder();
                for(String part : entry.getName().split("/")) {
                    if(className.length() != 0)
                        className.append(".");
                    className.append(part);
                    if(part.endsWith(".class"))
                        className.setLength(className.length()-".class".length());
                   }
                 classNames.add(className.toString());
              }
         
        for(int x=0;x<classNames.size();x++){
            //System.out.println(classNames.get(x));
            switch(classNames.get(x)){
                case "com.mysql.jdbc.Driver":  //MySQL driver  +++++++  "oracle.jdbc.driver.OracleDriver"   "com.microsoft.sqlserver.jdbc.SQLServerDriver"                              
                     drivername.setText("MySQL (Connector/ J Driver)");
                     driverdrop.addItem("MySQL (Connector/ J Driver)"); 
                     driverclass.setText(classNames.get(x));
                     driverclass.setEditable(false);
                     driverpath.setEditable(false); 
                     driverpath.setEnabled(false);  
                     
                     server = new Server();
                     server.setDriver("MySQL (Connector/ J Driver)");
                     server.setDatabase("mysql");
                     server.setPortnumber(3306);
                     server.setServerName("localhost"); 
                     server.setUsername("root"); 
                     server.setConnectionString("jdbc:mysql://localhost:3306/mysql?"); 
                    /*try { getDriverProperties(classNames.get(x));}
                    catch (Exception ex) {ex.printStackTrace();}*/
                break;
            }
        }  

    }
    
    
   /*public void getDriverProperties(String driverclassname) 
    {
        Properties info = new Properties();
        DriverPropertyInfo[] attributes = null;
        Driver driver = null;
        
        try { Class.forName(driverclassname);    
              //Connection con = DriverManager.getConnection("jdbc:mysql://localhost");
              driver = DriverManager.getDriver("jdbc:mysql");
              attributes = driver.getPropertyInfo("jdbc:mysql:", info);
        }catch(ClassNotFoundException ex) { ex.printStackTrace();
        }catch(SQLException ex) { ex.printStackTrace(); }
        
        // get the property metadata
         String hostname = attributes[0].description; 
         String localhost  = attributes[0].value;
         String port   = attributes[1].value;
        
         
         driverclass.setText(driver.getClass().getName());
         driverclass.setEditable(false);
         driverpath.setEditable(false); 
         driverpath.setEnabled(false);  
          
       /* for (int i = 0; i < attributes.length; i++)
        {
          // get the property metadata
          String name = attributes[i].name;
          String[] choices = attributes[i].choices;
          boolean required = attributes[i].required;
          String description = attributes[i].description;
          
          drivername.setText(description);
          driverclass.setText(driver.getClass().getName());
          driverclass.setEditable(false);
          driverpath.setEditable(false); 
          driverpath.setEnabled(false); 
          
          
          // printout property metadata
          System.out.println("Description: " + description);
          System.out.println("Name: "+name);
          System.out.println("Required: " + required);
          System.out.println("=================CHOICES=============== ");
          for (int j = 0; j < choices.length; j++) {
           System.out.print(" " + choices[j]);
          }

        }

    }*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTextField driverclass;
    private javax.swing.JComboBox driverdrop;
    private javax.swing.JTextField drivername;
    private javax.swing.JTextField driverpath;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
