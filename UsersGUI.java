
package inventory_system;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.codec.digest.DigestUtils;
import static inventory_system.NewUserThread.results;


/**
 *
 * @author qxf3984
 */
public class UsersGUI extends JInternalFrame {

    private static int id;
    private static int role;
    Date logtime;
    public static int []  rowSelected;
    private Vector<Vector<String>> data; 
    private Vector<String> header;
    private Object ID; 
    private DefaultTableModel model;
    private String roleSelected;
    private boolean sendmail = true;
    protected static  String email;
    protected static String message;
    protected static String company_id;
    protected static String subject;
    protected static String recipient;
    protected static String on_copy;
    protected static String blind_copy;
    private int row;
    private JPasswordField password_input;
    private  URL url;
    
    public UsersGUI(int idin, int roleIn) 
    {
        id = idin;
        role = roleIn;
        logtime = new Date(); 
        model = new javax.swing.table.DefaultTableModel(data,header);
        password_input = new JPasswordField();
        url = null;
        
        //create headers for consumable table
        header = new Vector<String>();
        header.add("Company ID");
        header.add("Username");
        header.add("Role");
        header.add("E-mail");
        initComponents();
        this.getContentPane().setBackground(Color.white);
        
        try
        {
          ConnectDatabase database = new ConnectDatabase();
          data  = database.setUserTable(id);
          database.setCloseDatabase();
        }
        catch (Exception e_exception)
        {
          e_exception.printStackTrace();
        }
        
       userTable.setModel(new javax.swing.table.DefaultTableModel(data,header)); 
       
    }

    private UsersGUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        deleteButton = new javax.swing.JButton();
        passwordButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        photo_lb = new javax.swing.JLabel();
        email_lb = new javax.swing.JLabel();
        role_textview = new javax.swing.JLabel();
        name_lb = new javax.swing.JLabel();
        more_detail_lb = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        firstname_lb = new javax.swing.JTextField();
        lastname_lb = new javax.swing.JTextField();
        username_lb = new javax.swing.JTextField();
        companyid_lb = new javax.swing.JTextField();
        e_mail_lb = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        storeman_rb = new javax.swing.JRadioButton();
        facilitator_rb = new javax.swing.JRadioButton();
        administrator_rb = new javax.swing.JRadioButton();
        superuser_rb = new javax.swing.JRadioButton();
        saveButton = new javax.swing.JButton();
        error_message_label = new javax.swing.JLabel();
        error_icon_label = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        userTable.setModel(new javax.swing.table.DefaultTableModel(data,header));
        userTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                userTableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(userTable);

        jLabel1.setText("You can add and delete users: Storeman and Facilitator");

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        passwordButton.setText("New Password");
        passwordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Tip:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 102, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("<html>Once you delete a user you cannot undo the<br>action. Make sure that you really need to delete <br>the user!</html>");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Role");
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        photo_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        photo_lb.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        email_lb.setForeground(new java.awt.Color(51, 51, 255));
        email_lb.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        email_lb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                email_lbMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                email_lbMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                email_lbMousePressed(evt);
            }
        });

        role_textview.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        role_textview.setForeground(new java.awt.Color(51, 51, 255));
        role_textview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        name_lb.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        name_lb.setForeground(new java.awt.Color(51, 51, 255));
        name_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        more_detail_lb.setForeground(new java.awt.Color(0, 0, 255));
        more_detail_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        more_detail_lb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                more_detail_lbMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                more_detail_lbMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                more_detail_lbMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(role_textview, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(name_lb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(email_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(more_detail_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(photo_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(role_textview, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(name_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(photo_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(email_lb, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(more_detail_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("New user");
        jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setText("Name:");

        jLabel7.setText("Surname:");

        jLabel8.setText("Q-Number:");

        jLabel9.setText("Company ID:");

        jLabel10.setText("E-mail:");

        firstname_lb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                firstname_lbKeyTyped(evt);
            }
        });

        lastname_lb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastname_lbActionPerformed(evt);
            }
        });

        username_lb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                username_lbActionPerformed(evt);
            }
        });

        companyid_lb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                companyid_lbActionPerformed(evt);
            }
        });

        jLabel11.setText("Assign user role:");

        storeman_rb.setText("Storeman");
        storeman_rb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeman_rbActionPerformed(evt);
            }
        });

        facilitator_rb.setText("Facilitator");
        facilitator_rb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facilitator_rbActionPerformed(evt);
            }
        });

        administrator_rb.setText("Administrator");
        administrator_rb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                administrator_rbActionPerformed(evt);
            }
        });

        superuser_rb.setText("Super user");
        superuser_rb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                superuser_rbActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(firstname_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lastname_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(storeman_rb))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(username_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(facilitator_rb))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(companyid_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(administrator_rb))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(saveButton))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(e_mail_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(superuser_rb)))))
                        .addGap(0, 110, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(firstname_lb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lastname_lb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(storeman_rb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(username_lb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(facilitator_rb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(companyid_lb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(administrator_rb))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(e_mail_lb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(superuser_rb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveButton)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        error_message_label.setForeground(java.awt.Color.red);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(deleteButton)
                                    .addComponent(passwordButton)
                                    .addComponent(jLabel2))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(error_icon_label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(error_message_label, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passwordButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(error_message_label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(error_icon_label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        //delete selected user
          row           = UsersGUI.this.userTable.getSelectedRow();
          String compid     = (String) UsersGUI.this.userTable.getValueAt(row, 0);
          int companyid     = Integer.parseInt(compid);
          String username   = (String) UsersGUI.this.userTable.getValueAt(row, 1);
          String role       = (String) UsersGUI.this.userTable.getValueAt(row, 2);
          
          int answer = JOptionPane.showConfirmDialog(null,"<html>Are you sure you want to delete <b>"+username+"</b>", "Delete User("+compid+") Confirmation", 0, 2);
          
          if(answer == 0)
          {
              try{
                  ConnectDatabase database = new ConnectDatabase();
                  String batch = "DELETE FROM users WHERE company_id = "+companyid;
                  database.setBatch(batch); 
                  
                  String batch2 = "DELETE FROM "+role+" WHERE company_id = "+companyid;
                  database.setBatch(batch2); 
                  database.setCloseDatabase();
                  JOptionPane.showMessageDialog(null,"User successfully deleted!", "Success!", 1);
                 }
              catch(Exception e){ JOptionPane.showMessageDialog(null, e, "Failed to delete user!", 2);}
          } 
          else
          { }
       
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void passwordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordButtonActionPerformed
          row           = UsersGUI.this.userTable.getSelectedRow();
          String compid     = (String) UsersGUI.this.userTable.getValueAt(row, 0);
          int companyid     = Integer.parseInt(compid);
          String username   = (String) UsersGUI.this.userTable.getValueAt(row, 1);
          String role       = (String) UsersGUI.this.userTable.getValueAt(row, 2);
          String email_adr  = (String) UsersGUI.this.userTable.getValueAt(row, 3);
                  
          int answer = JOptionPane.showConfirmDialog(null,"<html>Change password for: <b>"+username+"</b>", "Password Change ("+compid+") Confirmation", 0, 2);
          
          if(answer == 0)
          {
             // String newpassword = JOptionPane.showInputDialog(null, "Please type in a new password", "New Password", JOptionPane.PLAIN_MESSAGE);
              int enter = JOptionPane.showConfirmDialog(null,password_input,"Please type in a new password",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE );
              if(enter == JOptionPane.OK_OPTION)
              {  
                 String newpassword = new String(password_input.getPassword());
                
                 //if(newpassword.equals(null)|| newpassword.equals(" ")) 
                  if(newpassword.isEmpty()) 
                   {
                     //System.out.print("Password is empty or null"); 
                     JOptionPane.showMessageDialog(null,"Your password is blank. Please try again!", "Password Empty!", 2);
                   }
                 else{
                       SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                       Date currentdate = new Date();
                       String changedate = formatter.format(currentdate);  
                      
                      try{
                        ConnectDatabase database = new ConnectDatabase();
                        String batch = "UPDATE users SET `Password` = '"+newpassword+"', `creator` = "+id+", `date_created` = '"+changedate+"' WHERE company_id = "+companyid;
                        database.setBatch(batch); 
                        database.setCloseDatabase();
                        sendmail = true;
                        JOptionPane.showMessageDialog(null,"Password successfully changed!", "Success!", 1);}
                      catch(Exception e){ JOptionPane.showMessageDialog(null, e, "Failed to change password for user ("+username+")!", 2); sendmail = false;}
                 }
                  
                  if(sendmail == true)
                    {  
                      //Add items to table list of items.
                      ConstructMail cm = new ConstructMail(); 
                      message = cm.NewPassword(username.toUpperCase(),newpassword); 
                      subject = "Password Request Change";
                      recipient = email_adr;  
                      on_copy   = "tebogo.ka.kekana@partner.bmw.co.za";//database.getString(""); 

                      email = "invt_store_program@bmw.co.za";

                     //Send e-mail notification(receipt) to learner
                     new NewPasswordThread().execute();     
                    } 
                
              }
              else{ System.out.print("Either you chose cancel or closed");}   
          }    
          else
          { }
    }//GEN-LAST:event_passwordButtonActionPerformed

    private void userTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTableMousePressed
          // single click on the a row and display learner and item info below the table
       if ((evt.getClickCount() == 1) && (!evt.isMetaDown()))
        { //get values from the row selected
          row           = UsersGUI.this.userTable.getSelectedRow();
          String compid     = (String) UsersGUI.this.userTable.getValueAt(row, 0);
          int companyid     = Integer.parseInt(compid);
          String username   = (String) UsersGUI.this.userTable.getValueAt(row, 1);
          email             = (String) UsersGUI.this.userTable.getValueAt(row, 3);
          String role     = (String) UsersGUI.this.userTable.getValueAt(row, 2);
          String name = "", surname = "";  
          try {
               //url = new URL("http://dzadci20.africa.bmw.corp:6771/sap/bc/webdynpro/sap/zhrad_profile?pernr="+companyid); //development
               url = new URL("http://pzapdi35.africa.bmw.corp:8080/sap/bc/webdynpro/sap/zhrad_profile?pernr="+companyid);   //production
           } catch (MalformedURLException ex) {
               Logger.getLogger(UsersGUI.class.getName()).log(Level.SEVERE, null, ex);
           }
          
          //get image of learner selected
          ImageIcon icon = null;  
          try{ icon = new ImageIcon(new java.net.URL("http://haf0gau01.w9/photos/"+companyid+".jpg"));}
          catch (MalformedURLException ex){Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);}
          
           //get learner small detail
            try{ ConnectDatabase database = new ConnectDatabase();
              switch (role)
              {
                  case "facilitator":
                      name   = database.getString("SELECT `name` FROM facilitator WHERE `company_id` LIKE '" + companyid + "'");
                      surname = database.getString("SELECT `surname` FROM facilitator WHERE `company_id` LIKE '" + companyid + "'");
                      break;
                  case "storeman":
                      name   = database.getString("SELECT `name` FROM storeman WHERE `company_id` LIKE '" + companyid + "'");
                      surname = database.getString("SELECT `surname` FROM storeman WHERE `company_id` LIKE '" + companyid + "'");
                      break;
                  case "administrator":
                      name   = database.getString("SELECT `name` FROM administrator WHERE `company_id` LIKE '" + companyid + "'");
                      surname = database.getString("SELECT `surname` FROM administrator WHERE `company_id` LIKE '" + companyid + "'");
                      break;    
              }
                  database.setCloseDatabase();
                }
            catch (Exception e1){e1.printStackTrace();}
          
          //Resize the image icon   
          java.awt.Image img = icon.getImage();
          BufferedImage bi = new BufferedImage(125,125,BufferedImage.TYPE_INT_RGB);
          bi.getGraphics().drawImage(img, 0, 0, null);
          Graphics g = bi.getGraphics();
          g.drawImage(img,0,0, 125, 125,this);
          ImageIcon newIcon = new ImageIcon(bi);
          
          //now assign selected values to the UI
          role_textview.setText(role);
          email_lb.setText("<html><u>"+email+"</u></html>");
          more_detail_lb.setText("<html><u>more</u></html>");
          more_detail_lb.setToolTipText("<html><u>Go to: "+url+"</u></html>");
          name_lb.setText(name+" "+surname); 
          photo_lb.setIcon(newIcon); 
        }       
        
    }//GEN-LAST:event_userTableMousePressed

    private void email_lbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_email_lbMouseExited
        email_lb.setForeground(Color.blue);
        email_lb.setFont(new Font("SansSerif",Font.BOLD,11));
    }//GEN-LAST:event_email_lbMouseExited

    private void email_lbMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_email_lbMousePressed
        //MailBoxWindow box = new MailBoxWindow(email);
        MailBoxWindow box = new MailBoxWindow("invt_store_program@bmw.co.za");
        box.setVisible(true);
    }//GEN-LAST:event_email_lbMousePressed

    private void email_lbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_email_lbMouseEntered
       email_lb.setForeground(Color.blue);
       email_lb.setFont(new Font("SansSerif",Font.BOLD,13));
    }//GEN-LAST:event_email_lbMouseEntered

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
       if(firstname_lb.getText().equals("") && lastname_lb.getText().equals("") && companyid_lb.getText().equals("") && e_mail_lb.getText().equals("") && username_lb.getText().equals("")) 
        {
            firstname_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));  //missing first name
            lastname_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));  //missing last name
            companyid_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));  //missing company id
            e_mail_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            username_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            error_icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_message_label.setText("Warning: Please fill in all required fields in the highlighted boxes!");
        }
        else if(firstname_lb.getText().equals(""))
        {
            firstname_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1)); //set first name to error
            error_icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_message_label.setText("Warning: Please type in Fisrt Name in the highlighted box!");
        }
        else if ((lastname_lb.getText().equals("")))
        {
            lastname_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1)); //set last name to error
            error_icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_message_label.setText("Warning: Please type in Last Name in the highlighted box!");
        }
        else if ((companyid_lb.getText().equals("")))
        {
            companyid_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));  //set company id to error
            error_icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_message_label.setText("Warning: Please type in Company ID in the highlighted box!");
        }
        else if( e_mail_lb.getText().equals("")) 
        {
            e_mail_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            error_icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_message_label.setText("Warning: Please fill in e-mail address in the highlighted boxes!");
        }
         else if( username_lb.getText().equals("")) 
        {
            username_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            error_icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_message_label.setText("Warning: Please fill in a Username in the highlighted boxes!");
        }
        else if (firstname_lb.getText().equals("") && lastname_lb.getText().equals(""))
        {
            lastname_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            firstname_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            error_icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_message_label.setText("Warning: Please fill in all required fields in the highlighted boxes!");
        }
        else if (firstname_lb.getText().equals("") && companyid_lb.getText().equals(""))
        {
            firstname_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            companyid_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            error_icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_message_label.setText("Warning: Please fill in all required fields in the highlighted boxes!");
        }
        else if (lastname_lb.getText().equals("") && companyid_lb.getText().equals(""))
        {
            lastname_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            companyid_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            error_icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_message_label.setText("Warning: Please fill in all required fields in the highlighted boxes!");
        }
        else if(companyid_lb.getText().equals("") && e_mail_lb.getText().equals("") && username_lb.getText().equals("")) 
        {
            companyid_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));  //missing company id
            e_mail_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            username_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            error_icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_message_label.setText("Warning: Please fill in all required fields in the highlighted boxes!");
        }
        else if(companyid_lb.getText().equals("") && e_mail_lb.getText().equals("")) 
        {
            companyid_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));  //missing company id
            e_mail_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            error_icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_message_label.setText("Warning: Please fill in all required fields in the highlighted boxes!");
        }
         else       
        {
                //generate 8 character random password
                String p = "";
                p = (String) UUID.randomUUID().toString().subSequence(0, 8);
                
                if(roleSelected == null || roleSelected.equals("")) 
                {
                     //error_icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
                     //error_message_label.setText("Select a Role for the new user you are adding!"); 
                    JOptionPane.showMessageDialog(null,"Assign a role to the user!","Select Role",1);
                }
                else
                {
                    companyid_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));  //set company id to error
                    lastname_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
                    firstname_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
                    e_mail_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
                    username_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
                    error_icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
                    error_message_label.setText("");      
                    
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    Date currentdate = new Date();
                    String changedate = formatter.format(currentdate); 
                    String fname = firstname_lb.getText();
                    String lname = lastname_lb.getText();
                    String username = username_lb.getText();
                    int compid = Integer.parseInt(companyid_lb.getText());
                    String email_adr = e_mail_lb.getText();
                    
                    
                    try
                    {
                        ConnectDatabase database = new ConnectDatabase();
                        String batch = "INSERT INTO "+roleSelected+" (`company_id`,`name`,`surname`, `e_mail`) VALUES (" + compid + ",'" + fname + "','" + lname + "','" + email_adr + "')";
                        database.setBatch(batch);
                        
                        String update_users = "INSERT INTO users (`company_id`,`Username`,`Password`,`Role`,`e_mail`,`creator`,`date_created`)" +
                        "VALUES("+compid+",'"+username.toUpperCase()+"','"+DigestUtils.md5Hex(p)+"','"+roleSelected+"','"+email_adr+"',"+id+",'"+changedate+"')";
                        database.setBatch(update_users);
                        
                        String update_tmp = "INSERT INTO tempPasswords (`company_id`, `tmp_password`) VALUES("+compid+",'"+DigestUtils.md5Hex(p)+"')";
                        database.setBatch(update_tmp);
                        
                        database.setCloseDatabase();
                        sendmail = true;

                        //JOptionPane.showMessageDialog(null, "<html>You have succesfully added " + fname + " "+lname+" as a new "+roleSelected+"</html>", "Success", 1);
                     }
                    catch (Exception e1)
                    {   
                        sendmail = false;
                        JOptionPane.showMessageDialog(null, e1, "New User not added", 2);
                    }  
                    
                    if(sendmail == true)
                    {  
                      //Add items to table list of items.
                      ConstructMail cm = new ConstructMail();
                      cm.setRole(roleSelected); 
                      message = cm.NewUser(username.toUpperCase(), fname+" "+lname, p); 
                      subject = "New Role Allocation";
                      recipient = email_adr;  
                      on_copy   = "tebogo.ka.kekana@partner.bmw.co.za";//database.getString(""); 

                     //prepare e-mail notification for learner
                     /*try
                     {
                      ConnectDatabase database = new ConnectDatabase();      
                      email     = database.getString("SELECT `e_mail` FROM users WHERE `company_id` LIKE "+id); 
                     }
                     catch(Exception e1){ e1.printStackTrace();}*/
                      email = "invt_store_program@bmw.co.za";

                     //Send e-mail notification(receipt) to learner
                     new NewUserThread().execute();     
                    }
                }
           // }
        }   
    }//GEN-LAST:event_saveButtonActionPerformed

    private void storeman_rbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_storeman_rbActionPerformed
          roleSelected = "storeman";
    }//GEN-LAST:event_storeman_rbActionPerformed

    private void facilitator_rbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facilitator_rbActionPerformed
        roleSelected = "facilitator";
    }//GEN-LAST:event_facilitator_rbActionPerformed

    private void administrator_rbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_administrator_rbActionPerformed
        roleSelected = "administrator";
    }//GEN-LAST:event_administrator_rbActionPerformed

    private void superuser_rbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_superuser_rbActionPerformed
      roleSelected = "superuser";
    }//GEN-LAST:event_superuser_rbActionPerformed

    private void firstname_lbKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_firstname_lbKeyTyped
         firstname_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
    }//GEN-LAST:event_firstname_lbKeyTyped

    private void lastname_lbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastname_lbActionPerformed
       lastname_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
    }//GEN-LAST:event_lastname_lbActionPerformed

    private void username_lbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_username_lbActionPerformed
        username_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
    }//GEN-LAST:event_username_lbActionPerformed

    private void companyid_lbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_companyid_lbActionPerformed
       companyid_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));  //set company id to error
    }//GEN-LAST:event_companyid_lbActionPerformed

    private void more_detail_lbMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_more_detail_lbMousePressed
        //alert user of SAP ZAP LOGON
        int answer = JOptionPane.showConfirmDialog(null,"<html>By clicking the <b>Yes</b> button you may be required to enter your<br>"
                                                         + "<i>SAP Login</i> details in order to procced.<br><br>Would you like to continue?</html>", "Opening browser...", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        
        if(answer == 0){
             // open web browser
            Desktop desktop = Desktop.getDesktop();
            if(desktop.isSupported(Desktop.Action.BROWSE)){
                try{
                    desktop.browse(url.toURI());
                    return;
                }catch(Exception e){e.printStackTrace();}
            }else{
               JOptionPane.showMessageDialog(this,"Cannot launch browser...\n Please visit\n"+url.getHost(),"Not supported",JOptionPane.INFORMATION_MESSAGE);             
            }
        }else{
            //do nothing
        }
    }//GEN-LAST:event_more_detail_lbMousePressed

    private void more_detail_lbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_more_detail_lbMouseEntered
         more_detail_lb.setForeground(Color.blue);
         more_detail_lb.setFont(new Font("Tahoma",Font.PLAIN,12));
    }//GEN-LAST:event_more_detail_lbMouseEntered

    private void more_detail_lbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_more_detail_lbMouseExited
         more_detail_lb.setForeground(Color.blue);
         more_detail_lb.setFont(new Font("Tahoma",Font.PLAIN,11));
    }//GEN-LAST:event_more_detail_lbMouseExited

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
            java.util.logging.Logger.getLogger(UsersGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UsersGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UsersGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UsersGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UsersGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton administrator_rb;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField companyid_lb;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField e_mail_lb;
    private javax.swing.JLabel email_lb;
    public static javax.swing.JLabel error_icon_label;
    public static javax.swing.JLabel error_message_label;
    private javax.swing.JRadioButton facilitator_rb;
    private javax.swing.JTextField firstname_lb;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lastname_lb;
    private javax.swing.JLabel more_detail_lb;
    private javax.swing.JLabel name_lb;
    private javax.swing.JButton passwordButton;
    private javax.swing.JLabel photo_lb;
    private javax.swing.JLabel role_textview;
    private javax.swing.JButton saveButton;
    private javax.swing.JRadioButton storeman_rb;
    private javax.swing.JRadioButton superuser_rb;
    private javax.swing.JTable userTable;
    private javax.swing.JTextField username_lb;
    // End of variables declaration//GEN-END:variables
}

class NewUserThread extends SwingWorker<String, String> {

  /*
  * This should just create a frame that will hold a progress bar until the
  * work is done. Once done, it should remove the progress bar from the dialog
  * and add a label saying the task complete.
  */

  private JFrame frame = new JFrame(); 
  private JDialog dialog = new JDialog(frame, "Proccessing!", true);
  private JProgressBar progressBar = new JProgressBar();
  public static String results;
  

  public NewUserThread() {
    dialog.setPreferredSize(new Dimension(200,100));
    frame.setResizable(false); 
    frame.setLocationRelativeTo(null);  
    progressBar.setString("Please wait...");
    progressBar.setStringPainted(true);
    progressBar.setIndeterminate(true);
    dialog.getContentPane().add(progressBar);
    dialog.pack();
    dialog.setModal( false );
    dialog.setVisible(true);
  }

  @Override
  protected String doInBackground() throws Exception 
  {
    //send e-mail
    SendEmail mail = new SendEmail(UsersGUI.recipient,UsersGUI.email,UsersGUI.on_copy,UsersGUI.message,UsersGUI.subject);
    String state = mail.Send();

    return state;
  }

  protected void done() 
  {
     results = "";
      try {
          results = get();  // get the results returned by doInBackground() function and use them
      } catch (InterruptedException ex) {
          Logger.getLogger(GuiWorker.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ExecutionException ex) {
          Logger.getLogger(GuiWorker.class.getName()).log(Level.SEVERE, null, ex);
      }
    
      dialog.dispose();
      
       JOptionPane.showMessageDialog(null, "<html>User succesfully Added and Assigned Role.</html>", "New User & Role Assignment Confirmation", 1);
  }

}

class NewPasswordThread extends SwingWorker<String, String> { 
  public static String results;

  public NewPasswordThread(){}

  @Override
  protected String doInBackground() throws Exception {
    //send e-mail
    SendEmail mail = new SendEmail(UsersGUI.recipient,UsersGUI.email,UsersGUI.on_copy,UsersGUI.message,UsersGUI.subject);
    String state = mail.Send();
    return state;
  }

  protected void done(){
     results = "";
      try {
          results = get();  // get the results returned by doInBackground() function and use them
          UsersGUI.error_message_label.setForeground(Color.green); 
          UsersGUI.error_message_label.setText("Successfully sent new password to user");
      } catch (InterruptedException ex) {
          Logger.getLogger(GuiWorker.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ExecutionException ex) {
          Logger.getLogger(GuiWorker.class.getName()).log(Level.SEVERE, null, ex);
      }

  }

}

