package inventory_system;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;

public class NoticeBoard extends JInternalFrame
{
  private JButton deleteButton;
  private JButton viewButton;
  private javax.swing.JLabel icon_error_label;
  private javax.swing.JLabel error_label;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JScrollPane jScrollPane1;
  private JTable noticeTable;
  private JTextField input_msg;
  private JButton saveButton;
  private JPanel main;
  private static int id;
  private static String role;
  Date logtime;
  private Connection con;
  private ResultSet rs;
  ResultSetMetaData md;
  private DefaultTableModel model_one;
  public static int []  tableRowSelected;
  public static int []  rowSelected;
  private Vector<Vector<String>> data; 
  private Vector<String> header;
  private Object ID;
  private javax.swing.JLabel noticeLabel;
  
  public NoticeBoard(int idin, String roleIn)
  {
    super("Notice Board window", false, false, true, false);

    id = idin;
    role = roleIn;
    this.logtime = new Date();
    
    //create headers for notice board table
    header = new Vector<String>();
    header.add("MsgID");
    header.add("Notification");
    header.add("Notice Date");
    header.add("UserID");
    initComponents();
    
    try{
      ConnectDatabase database = new ConnectDatabase();
      data = database.setNoticeTable();
      database.setCloseDatabase();
    }
    catch (Exception e_exception)
     {
      e_exception.printStackTrace();
     }
    noticeTable.setModel(new javax.swing.table.DefaultTableModel(data,header));
    
    getContentPane().setBackground(Color.white);
    this.main.setBackground(Color.white);
    add(this.main);
    setSize(835, 590);
    setVisible(true);
    setLocation(150, 0);
    
    noticeTable.addMouseListener(new NoticeBoard.NoticeTableListener());
    
  }
  public class NoticeTableListener extends MouseAdapter 
    {
     public NoticeTableListener() 
      {
      
      }
     
     public void mousePressed(MouseEvent evt) 
      {
         if ((evt.getClickCount() == 1) && (!evt.isMetaDown()))
           {
             rowSelected = NoticeBoard.this.noticeTable.getSelectedRows();
           }
       }
    } 
  private void initComponents()
  {
    saveButton = new JButton();
    deleteButton = new JButton();
    jScrollPane1 = new JScrollPane();
    noticeTable = new JTable();
    jLabel1 = new JLabel();
    input_msg = new JTextField();
    jLabel2 = new JLabel();
    viewButton = new JButton();
    main = new JPanel();
    error_label = new javax.swing.JLabel();
    icon_error_label = new javax.swing.JLabel();

    setBackground(new Color(255, 255, 255));
    setPreferredSize(new Dimension(570, 290));

    saveButton.setText("Save");
    saveButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        NoticeBoard.this.saveButtonActionPerformed(evt);
      }
    });
    
    error_label.setForeground(new java.awt.Color(255, 0, 0));
    
    deleteButton.setText("Delete");
    deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButton1ActionPerformed(evt);
            }
        });

    noticeTable.setModel(new DefaultTableModel(new Object[][] { { null, null, null }, { null, null, null }, { null, null, null }, { null, null, null } }, new String[] { "Title 1", "Title 2", "Title 3" }));

    jScrollPane1.setViewportView(noticeTable);

    jLabel1.setText("Set notifications  for learners, storeman and facilitators here:");

    input_msg.setForeground(new Color(51, 51, 255));
    

    jLabel2.setText("Message:");

    viewButton.setText("View");
    viewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewButtonActionPerformed(evt);
            }
        });

    GroupLayout layout = new GroupLayout(this.main);
    main.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(icon_error_label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(error_label, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(this.jLabel2)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(this.input_msg)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(this.saveButton, -2, 110, -2))
            .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.jLabel1).addGroup(layout.createSequentialGroup()
            .addComponent(this.jScrollPane1, -2, 452, -2)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.deleteButton, -2, 110, -2)
            .addComponent(this.viewButton, -2, 110, -2))))
            .addGap(0, 0, 32767)))
            .addContainerGap()));

    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(this.jLabel1)
            .addGap(37, 37, 37)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
            .addComponent(this.jScrollPane1, -2, 165, -2)
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(this.input_msg, -2, 27, -2)
            .addComponent(this.jLabel2)
            .addComponent(this.saveButton, -2, 25, -2)))
            .addGroup(layout.createSequentialGroup()
            .addComponent(this.deleteButton, -2, 25, -2)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(this.viewButton, -2, 25, -2)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(error_label, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(icon_error_label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(37, 37, 37)));
  } 
  private void saveButtonActionPerformed(ActionEvent evt)  {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    Date currentTime = new Date();
    String theDate = formatter.format(currentTime);

    if (input_msg.getText().equals(""))
    {
       input_msg.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0), 1));
       error_label.setText("Please type in a notification message!"); 
       icon_error_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
    }
    else
    {
      input_msg.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
      error_label.setText(""); 
      icon_error_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
      
      String sql = "INSERT INTO notice_board (Notification,Notice_Date,UserID)VALUES('" + this.input_msg.getText() + "','" + theDate + "'," + id + ")";
      try
      {
        ConnectDatabase database = new ConnectDatabase();
        database.setBatch(sql);
      }
      catch (Exception ex)
      {
        JOptionPane.showMessageDialog(null, "", "", 1);
      }
    }
  }
  
  private void viewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewButtonActionPerformed
        // view notification 

        int row = noticeTable.getSelectedRow();
        Object notice = null;
        String noticeMsg = "";
        
        try{notice = noticeTable.getValueAt(row, 1); noticeMsg = ""+ notice;}
        catch(Exception c){System.out.println(c); }
        
        if(notice == null)
        {
          error_label.setText("");  
        } 
        else
        {
          error_label.setText(noticeMsg);
        }
        
    }//GEN-LAST:event_viewButtonActionPerformed
  
  private void deleteButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButton1ActionPerformed
      
        int [] msgIDs = new int[rowSelected.length];
    	int learnerID = 0;
         
            for(int x = 0; x < rowSelected.length;x++)
            {
                    ID = noticeTable.getValueAt(rowSelected[x], 0);
                    learnerID = Integer.valueOf((String) ID);
                    msgIDs[x] = learnerID; 
                    //System.out.println(learnerIDs[x]);                
            }

            //remove row(s) from user table here
            model_one = (DefaultTableModel) noticeTable.getModel();
            int numRows = msgIDs.length;
            for(int i=0;i<numRows;i++)
            {
               model_one.removeRow(noticeTable.getSelectedRow());
            }

            //remove row(s) from database table
            if(rowSelected.length <= 1)
            {  
                String sgl = "DELETE * FROM notice_board WHERE MsgID = "+learnerID;
                try
                {
                   ConnectDatabase database = new ConnectDatabase(); 
                   database.setBatch(sgl); 
                   database.setCloseDatabase();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            else
            {  
                for(int x = 0; x < rowSelected.length;x++)
                {  
                    String sgl = "DELETE * FROM notice_board WHERE MsgID = "+msgIDs[x];
                    try
                    {
                       ConnectDatabase database = new ConnectDatabase(); 
                       database.setBatch(sgl); 
                       database.setCloseDatabase();
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }    
            }
    }//GEN-LAST:event_deleteButton1ActionPerformed
}