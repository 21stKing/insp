
package inventory_system;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import org.apache.commons.codec.digest.*;


/**
 * version no 
 * @author tebogo
 */
public class PasswordRequest extends javax.swing.JFrame 
{
    private boolean passwordChange = false;
    Network net;
    NetworkDialog nd;
    private int hide;
    private int w, h;
    
    
    public PasswordRequest() 
    {    
        this.getContentPane().setBackground(new Color(226,223,214));
        //this.setLocationByPlatform(true); 
                
        initComponents();
        header_panel.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBackground(new Color(226,223,214));
        server_panel.setBackground(new Color(226,223,214));
        hide = 1;
        h = this.getHeight();
        w = this.getWidth();
        
        if(RunProgram.server.size() > 0){
            for(int x =0;x < RunProgram.server.size();x++){
                server_name.addItem(RunProgram.server.get(x).getServerName()); 
            }
            server_name.addItem("New Connection..."); 
            port.setText(""+RunProgram.server.get(0).getPortnumber());
        }else{
            server_name.addItem(""); 
            server_name.addItem("New Connection..."); 
        }
        
    }   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        exitButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        password_input = new javax.swing.JPasswordField();
        username_input = new javax.swing.JTextField();
        optionsButton = new javax.swing.JButton();
        server_panel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        server_name = new javax.swing.JComboBox();
        port = new javax.swing.JTextField();
        header_panel = new javax.swing.JPanel();
        label_header = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        gif_label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Authorization Request");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(new ImageIcon(getClass().getResource("/inventory_system/image/insp_icon_demo_39x45.png")).getImage());
        setLocationByPlatform(true);

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        okButton.setText("Login");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setLabelFor(username_input);
        jLabel1.setText("Username:");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setLabelFor(password_input);
        jLabel2.setText("Password:");

        password_input.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        password_input.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        password_input.setText("jPasswordField1");
        password_input.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                password_inputMouseClicked(evt);
            }
        });
        password_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                password_inputActionPerformed(evt);
            }
        });

        username_input.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        username_input.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        username_input.setToolTipText("Log in user name");
        username_input.setBorder(password_input.getBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(username_input)
                    .addComponent(password_input, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(username_input, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(password_input, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        optionsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/up_14x12.png"))); // NOI18N
        optionsButton.setText("Options");
        optionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsButtonActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setLabelFor(username_input);
        jLabel4.setText("Server name:");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setLabelFor(password_input);
        jLabel5.setText("Port number:");

        server_name.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        server_name.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                server_nameItemStateChanged(evt);
            }
        });

        port.setEditable(false);
        port.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        port.setToolTipText("Log in user name");
        port.setBorder(password_input.getBorder());

        javax.swing.GroupLayout server_panelLayout = new javax.swing.GroupLayout(server_panel);
        server_panel.setLayout(server_panelLayout);
        server_panelLayout.setHorizontalGroup(
            server_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(server_panelLayout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(server_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(server_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(server_name, 0, 135, Short.MAX_VALUE)
                    .addComponent(port))
                .addContainerGap(117, Short.MAX_VALUE))
        );
        server_panelLayout.setVerticalGroup(
            server_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(server_panelLayout.createSequentialGroup()
                .addGroup(server_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(server_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(server_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        label_header.setFont(new java.awt.Font("BMW Group", 1, 12)); // NOI18N
        label_header.setText("BMW South Africa");
        label_header.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel3.setFont(new java.awt.Font("BMW Group", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(192, 192, 192));
        jLabel3.setText("Technical Training Center");

        javax.swing.GroupLayout header_panelLayout = new javax.swing.GroupLayout(header_panel);
        header_panel.setLayout(header_panelLayout);
        header_panelLayout.setHorizontalGroup(
            header_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(header_panelLayout.createSequentialGroup()
                .addGroup(header_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(header_panelLayout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(label_header, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(header_panelLayout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(gif_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        header_panelLayout.setVerticalGroup(
            header_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, header_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_header, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(gif_label, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(server_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(header_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(optionsButton)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(server_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(optionsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
 
    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        getUserVerification();
    }//GEN-LAST:event_okButtonActionPerformed

    private void password_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_password_inputActionPerformed
       getUserVerification();
    }//GEN-LAST:event_password_inputActionPerformed

    private void password_inputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_password_inputMouseClicked
      password_input.setText(""); 
    }//GEN-LAST:event_password_inputMouseClicked

    private void optionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsButtonActionPerformed
        if(hide == 1){  
          server_panel.setVisible(false); 
          hide = 0;
          this.setSize(w, (h - server_panel.getHeight())); 
          optionsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/down_14x12.png")));
        }else{
           server_panel.setVisible(true);   
           hide = 1;
           this.setSize(w, h);  
           optionsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/up_14x12.png")));
        }
    }//GEN-LAST:event_optionsButtonActionPerformed

    private void server_nameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_server_nameItemStateChanged
        
        String item = "";
        
        if(evt.getStateChange() == ItemEvent.SELECTED){
           item = (String) evt.getItem(); 
            if(item == "New Connection...")
               {
                 //open a server configuration and test screen
                 ConnectionWizard cw = new ConnectionWizard();
                 
                 cw.setVisible(true);
                 
                 //cw.setLocationByPlatform(true);
               }
            else{
                  // Do nothing
                }
        }
    }//GEN-LAST:event_server_nameItemStateChanged

    private void getUserVerification()
    {
        String username = username_input.getText();
        ArrayList<User> user = new ArrayList<>();
        String host = (String) server_name.getSelectedItem();
        String port = this.port.getText();
        
        if("".equals(username_input.getText())) 
        {
           JOptionPane.showMessageDialog(null, "<html>Please type in a user name!</html>", "Missing username", 2);
        }
        else 
        {
               try
                {
                  DB db = new DB(RunProgram.CONNECTION_MODE);
                  char [] pword = password_input.getPassword();
                  Password pw = new Password(); 
                  String securePword = pw.getMD5Password(pword);
                  
                  String state = db.userVerification(username, securePword);
                      switch (state) {
                          case "exist":
                              Date currentdate = new Date();
                              user = db.getUser(username);
                              //user index is hardcoded to 0 because if the user exists then it will return one record.
                              //On user registration a user name check up is required to make hardcode 0 to work.
                              int userid = user.get(0).getUserid();
                              int role = user.get(0).getRoleid();
                              String fname = user.get(0).getFirstname();
                              String lname = user.get(0).getLastname();
                              
                              //set user profile
                              new UserProfile(userid);
                              
                              //check if its new user                                
                              String tmp = db.getTemporaryPassword(userid);
                              if(tmp == null || tmp == "")
                              {
                                //existing user  
                                new UserLog(userid,role,0,currentdate);  //captures details and log in time of user
                                MainWindow mainGUI = new MainWindow(userid,role,currentdate);
                                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                                int width = dim.width/2-mainGUI.getSize().width/2;
                                int height = dim.height/2-mainGUI.getSize().height/2;                         
                                mainGUI.setLocation(width,height); //Positions the program at the center of any desktop screen
                                this.setVisible(false);
                                mainGUI.setVisible(true); 

                                //System.out.println("Width: "+width);
                                //System.out.println("Height: "+height); 
                              }
                              else  
                              {
                                int image_id = user.get(0).getImageid();
                                ImageIcon newIcon = null;
                                if(image_id <= 0)
                                  {
                                    newIcon = new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/user_profile2.png"));
                                  }
                                else
                                  {
                                    ImageIcon image = db.getImage(user.get(0).getImageid()); 
                                    java.awt.Image img = image.getImage();
                                    BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
                                    Graphics g = bi.createGraphics();
                                    g.drawImage(img,0,0, 50, 70,null); 
                                    newIcon = new ImageIcon(bi);
                                  }


                                    String [] details = new String[3];
                                    details[0] = ""+userid; 
                                    details[1] = fname;
                                    details[2] = lname;

                                    ChangePasswordGUI cpg = new ChangePasswordGUI(new JFrame(),newIcon,details);
                                    cpg.setVisible(true); 
                                    Object answer = cpg.value;

                                    if(answer == "Change")
                                     {
                                        //now change password 
                                        char p1[] = cpg.new_pword_field.getPassword();
                                        char p2[] = cpg.cnew_pword_field.getPassword();
                                        String p = "", cp = "";

                                        for(int y = 0;y<p1.length;y++)
                                          {
                                             p = p + p1[y];
                                          }
                                         for(int y = 0;y<p2.length;y++)
                                          {
                                              cp = cp + p2[y];
                                          }
                                           //Check password rules
                                           if(p1.length > 8 || p2.length > 8 && p1.length < 8 || p2.length < 8)
                                           {
                                            JOptionPane.showMessageDialog(null, "<html>Password is 8 characters less. Provide password that is 8 characters long!!</html>", "Password Length Invalid", 2);   
                                           }  
                                           else if(!p.equals(cp))
                                           {
                                            JOptionPane.showMessageDialog(null, "<html>Passwords do not match. Please type in carefully!</html>", "Passwords do not Match", 2);   
                                           } 
                                           else if(p1.equals("") || p2.equals("") || p1.equals(null) || p2.equals(null) || p1.equals("jPasswordField1") || p2.equals("jPasswordField1"))
                                           {
                                            JOptionPane.showMessageDialog(null, "<html>Password cannot be empty. Please type in a password!</html>", "Passwords are empty", 2);   
                                           } 
                                           else
                                           {
                                             String batch = "UPDATE user SET password ='"+DigestUtils.md5Hex(p)+"' WHERE userid ="+userid;
                                             //update password and check if success   
                                              if(db.executeSQL(batch)){
                                                //delete temp password
                                                String batch2 = "DELETE * FROM temppassword WHERE userid = "+userid;
                                                if(db.executeSQL(batch2)){
                                                  JOptionPane.showMessageDialog(null, "<html>Password Successfully Changed.<br><br>You can now login with your username ("+username+") and new password!</html>", "Password", 1);   
                                                  new UserLog(userid,role,0,currentdate);  //captures details and log in time of user
                                                  MainWindow mainGUI = new MainWindow(userid,role,currentdate);
                                                  Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                                                  int width = dim.width/2-mainGUI.getSize().width/2;
                                                  int height = dim.height/2-mainGUI.getSize().height/2;                         
                                                  mainGUI.setLocation(width,height); //Positions the program at the center of any desktop screen
                                                  this.setVisible(false);
                                                  mainGUI.setVisible(true); 

                                                 System.out.println("Width: "+width);
                                                 System.out.println("Height: "+height);
                                                  
                                                }else{
                                                  JOptionPane.showMessageDialog(null, "<html>Your password was changed.<br>Please contact your administrator to manually delete the temporary password before you log in the system.</html>", "Password", 1);        
                                                  //TO-DO here
                                                  //raise an automated support ticket to the administrator
                                                }
                                              }else{
                                                  JOptionPane.showMessageDialog(null, "<html>Failed to update password.<br><br>Please try again or contact the administrator!</html>", "Password", 2);    
                                              }
                                           }
                                     }
                              }
                              break;
                          case "notexisting":
                              JOptionPane.showMessageDialog(null, "<html>User does not exist. Please try again!</html>", "Incorrect Credentials", 1);
                              break;
                          case "usernametrue":
                              JOptionPane.showMessageDialog(null, "<html>Password provided doesn't match with the Username. Please type in the correct password!</html>", "Incorrect Password", 2);
                              break;
                          case "passwordtrue":
                              JOptionPane.showMessageDialog(null, "<html>Username provided doesn't match with the password. Please type in the correct username!</html>", "Incorrect Username", 2);
                              break;
                          default: //do nothing
                              break;
                      }
                }
                catch (Exception e1)
                {
                 //JOptionPane.showMessageDialog(null, "<html>Username provided does not exist. Please try again!</html>", "Incorrect Username", 2);
                 JOptionPane.showMessageDialog(null, e1, "Verification Error", 2);
                 // e1.printStackTrace(); 
                }  
        }
        
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
        /**try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PasswordRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PasswordRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PasswordRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PasswordRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold> 
        * */
        
       

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PasswordRequest().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel gif_label;
    private javax.swing.JPanel header_panel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel label_header;
    private javax.swing.JButton okButton;
    private javax.swing.JButton optionsButton;
    private javax.swing.JPasswordField password_input;
    private javax.swing.JTextField port;
    private javax.swing.JComboBox server_name;
    private javax.swing.JPanel server_panel;
    private javax.swing.JTextField username_input;
    // End of variables declaration//GEN-END:variables
}
