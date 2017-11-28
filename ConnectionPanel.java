
package inventory_system;

import java.awt.Color;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author tebogo
 */
public class ConnectionPanel extends javax.swing.JPanel 
{
    private static String getHost;
    private static String getDb;
    private static String getUser;
    private static String getPort;
    private static String getJDBCurl;        
    
    public ConnectionPanel() {
        initComponents();
        test_results_icon.setForeground(Color.black);
        test_results_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
        this.setBackground(new Color(226,223,214));
        ConnectionWizard.finishButton.setEnabled(true);
        //ConnectionWizard.nextButton.setEnabled(false);
        
          host.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void changedUpdate(DocumentEvent e){updatePath();}
            @Override
            public void removeUpdate(DocumentEvent e){updatePath();}
            @Override
            public void insertUpdate(DocumentEvent e){updatePath();}  
            public void updatePath(){
             con_string.setText("jdbc:mysql://"+host.getText()+":"+port.getText()+"/"+database.getText()+"?"); 
           }});
          
           database.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void changedUpdate(DocumentEvent e){updatePath();}
            @Override
            public void removeUpdate(DocumentEvent e){updatePath();}
            @Override
            public void insertUpdate(DocumentEvent e){updatePath();}  
            public void updatePath(){
             con_string.setText("jdbc:mysql://"+host.getText()+":"+port.getText()+"/"+database.getText()+"?"); 
           }});
           
           port.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void changedUpdate(DocumentEvent e){updatePath();}
            @Override
            public void removeUpdate(DocumentEvent e){updatePath();}
            @Override
            public void insertUpdate(DocumentEvent e){updatePath();}  
            public void updatePath(){
             con_string.setText("jdbc:mysql://"+host.getText()+":"+port.getText()+"/"+database.getText()+"?"); 
           }});
        
    } 
 
   protected static void setServer(){
 
        getHost = DriverPanel.server.getServerName();
        getDb  = DriverPanel.server.getDatabase();
        getUser = DriverPanel.server.getUsername();
        getPort = ""+DriverPanel.server.getPortnumber();
        getJDBCurl = DriverPanel.server.getConnectionString();
        
        host.setText(getHost);
        database.setText(getDb);
        port.setText(getPort);
        con_string.setText(getJDBCurl);
        username.setText(getUser); 
   }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        host = new javax.swing.JTextField();
        database = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        port = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        passworcheck = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        con_string = new javax.swing.JTextField();
        testconnection = new javax.swing.JButton();
        test_results_icon = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        jScrollPane1 = new javax.swing.JScrollPane();
        test_results = new javax.swing.JTextArea();

        jLabel1.setText("Host:");

        jLabel2.setText("Database:");

        jLabel3.setText("Port:");

        jLabel4.setText("User name:");

        jLabel5.setText("Password:");

        passworcheck.setText("Remember Password");

        jLabel6.setText("JDBC URL:");

        con_string.setEditable(false);
        con_string.setBackground(new java.awt.Color(255, 255, 255));

        testconnection.setText("Test Connection");
        testconnection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testconnectionActionPerformed(evt);
            }
        });

        password.setText("jPasswordField1");

        test_results.setEditable(false);
        test_results.setBackground(new java.awt.Color(240, 240, 240));
        test_results.setColumns(20);
        test_results.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        test_results.setLineWrap(true);
        test_results.setRows(2);
        test_results.setBorder(null);
        jScrollPane1.setViewportView(test_results);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(host)
                                    .addComponent(database)
                                    .addComponent(port, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(username))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(23, 23, 23)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(passworcheck)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(password)))))
                            .addComponent(con_string))
                        .addGap(28, 28, 28))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(test_results_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addGap(195, 195, 195)
                                .addComponent(testconnection)
                                .addContainerGap(221, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(host, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(database, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passworcheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(con_string, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(testconnection)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(test_results_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void testconnectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testconnectionActionPerformed
        // Test database connection
        int portnumber = Integer.parseInt(port.getText());
        String connectString = con_string.getText();
        String pword = "";
        try {
            Password pass = new Password();
            pword = pass.charToString(password.getPassword());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ConnectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        TestConnection test = new TestConnection(connectString, username.getText(), pword, portnumber, database.getText(), host.getText());
        try {
            test.Test();
            test_results.setText("Connection succeeded.");
            test_results.setForeground(Color.black);
            test_results_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/email_success.png")));
        } catch (SQLException ex) {
            test_results.setText("Connection failed!\n "+ex);
            test_results.setToolTipText(""+ex); 
            test_results.setForeground(Color.red); 
            test_results_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
        }
    }//GEN-LAST:event_testconnectionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected static javax.swing.JTextField con_string;
    protected static javax.swing.JTextField database;
    protected static javax.swing.JTextField host;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    protected static javax.swing.JCheckBox passworcheck;
    protected static javax.swing.JPasswordField password;
    protected static javax.swing.JTextField port;
    private javax.swing.JTextArea test_results;
    private javax.swing.JLabel test_results_icon;
    private javax.swing.JButton testconnection;
    protected static javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
