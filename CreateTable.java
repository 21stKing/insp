/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory_system;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import static inventory_system.MasterDataWindow.list_of_tables;

/**
 *
 * @author qxf3984
 */
public class CreateTable extends JInternalFrame {

    public static int []  rowSelected;
    private Vector<Vector<String>> data; 
    private Vector<String> header;
    private DefaultTableModel tmodel;
    protected String tableName;
    MyTableModel model;
    public static int []  tableRowSelected;
    final JCheckBox checkBox = new JCheckBox();
    
    public CreateTable(){
      
        initComponents();
       
        createTable.addMouseListener(new CreateTable.MyMouseListener());
        model = new MyTableModel();
        model.addRow(new Object[]{"", "Text", false});
        model.addRow(new Object[]{"", "Text", false});
        model.addRow(new Object[]{"", "Text", false});
        model.addRow(new Object[]{"", "Text", false});
       
        createTable.setModel(model); 
        createTable.createDefaultColumnsFromModel();
        String[] dataTypes = {"Text", "Memo", "Number", "Date/Time","Currency","AutoNumber", "Yes/No"};
        JComboBox cbox = new JComboBox(dataTypes);
        createTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(cbox)); 
    }
    
    public class MyMouseListener extends MouseAdapter {
     public MyMouseListener() 
      {
      
      }
     
     public void mousePressed(MouseEvent e){
         if ((e.getClickCount() == 1) && (!e.isMetaDown()))
           {
             tableRowSelected = CreateTable.this.createTable.getSelectedRows();
           }
       }
  } 
    
    protected void setNewTableName(String table_name){
        this.tableName = table_name.toLowerCase();
        new_table_name.setText(tableName); 
    }
   
    protected String getNewTableName(){
        return this.tableName;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        createTable = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        addRow = new javax.swing.JButton();
        deleteRow = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        save = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        new_table_name = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Create table"));

        createTable.setAutoCreateRowSorter(true);
        createTable.setModel(new javax.swing.table.DefaultTableModel(data,header));
        createTable.setCellSelectionEnabled(true);
        jScrollPane1.setViewportView(createTable);

        jToolBar1.setFloatable(false);

        addRow.setBackground(new java.awt.Color(213, 245, 213));
        addRow.setText("Add Row");
        addRow.setToolTipText("insert row");
        addRow.setFocusable(false);
        addRow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addRow.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRowActionPerformed(evt);
            }
        });
        jToolBar1.add(addRow);

        deleteRow.setBackground(new java.awt.Color(245, 209, 209));
        deleteRow.setText("Delete Row");
        deleteRow.setToolTipText("delete row");
        deleteRow.setFocusable(false);
        deleteRow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteRow.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deleteRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteRowActionPerformed(evt);
            }
        });
        jToolBar1.add(deleteRow);
        jToolBar1.add(filler1);

        save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/save.png"))); // NOI18N
        save.setToolTipText("Save table");
        save.setFocusable(false);
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        jToolBar1.add(save);

        jLabel1.setText("Table name:");

        new_table_name.setEditable(false);
        new_table_name.setBackground(new java.awt.Color(204, 204, 204));
        new_table_name.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(new_table_name, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(new_table_name, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRowActionPerformed
       model.addRow(new Object[]{"","",false}); 
    }//GEN-LAST:event_addRowActionPerformed

    private void deleteRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteRowActionPerformed
        int rows = 0;
        try{rows = model.getRowCount();
           if(rows != 0){
             model.removeRow(rows-1);     
           }else{
            MasterDataWindow.table_message.setForeground(Color.red);   
            MasterDataWindow.table_message.setText("No rows to remove!"); 
           } 
        }catch(NullPointerException e){
          MasterDataWindow.table_message.setForeground(Color.red);
          MasterDataWindow.table_message.setText("No rows to remove!"); 
        }
    }//GEN-LAST:event_deleteRowActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        int rows = 0;
        try{rows = model.getRowCount();
            if(rows != 0){
               // int row = CreateTable.this.createTable.getSelectedRow();
                int columnCount = createTable.getColumnCount();
                String data[][] = new String[rows][columnCount];
                String valueFromTable;
                
                for(int x=0;x<rows;x++){
                    for(int y=0;y<columnCount;y++){
                      try{
                       valueFromTable = (CreateTable.this.createTable.getValueAt(x, y)).toString();
                       data[x][y] = valueFromTable; 
                      } catch(NullPointerException e)
                          { data[x][y] = "";}     
                    }
                }
                
                String fieldsSQL = "";
                
                for(int x=0;x<rows;x++){
                    for(int y=0;y<columnCount;y++){
                        int removeComaIdentifyer = (rows-1);
                        if(y==1){
                            switch(data[x][y]){
                                case "Text"     : fieldsSQL = fieldsSQL + " text "; 
                                                  break;
                                case "Memo"     : fieldsSQL = fieldsSQL + " memo "; 
                                                  break;  
                                case "Number"   : fieldsSQL = fieldsSQL + " number "; 
                                                  break; 
                                case "Date/Time": fieldsSQL = fieldsSQL + " datetime "; 
                                                  break; 
                                case "Currency" : fieldsSQL = fieldsSQL + " currency "; 
                                                  break;     
                                case "AutoNumber": fieldsSQL = fieldsSQL + " autoincrement "; 
                                                  break;     
                                case "Yes/No"   : fieldsSQL = fieldsSQL + " yesno "; 
                                                  break; 
                                default         : fieldsSQL = fieldsSQL; 
                                                  break;    
                            }                  
                        }else if(y==2){
                             if(data[x][y].equals("true") && (data[x][1].equals("Number") || data[x][1].equals("AutoNumber"))) { 
                                 if(x==removeComaIdentifyer){
                                   fieldsSQL = fieldsSQL + "primary key";
                                 }else{
                                   fieldsSQL = fieldsSQL + "primary key,";  
                                 }
                             }else{
                                 if(x==removeComaIdentifyer){
                                   fieldsSQL = fieldsSQL+"";
                                 }else{
                                   fieldsSQL = fieldsSQL+",";
                                 }
                             }
                        }else{
                            fieldsSQL = fieldsSQL + data[x][y]; 
                        }
                        
                       }
                }
                
                String creatTableSQL = "CREATE TABLE "+getNewTableName()+"("+fieldsSQL+")";
                
                try{
                    DB db = new DB(RunProgram.CONNECTION_MODE);
                    db.addBatch(creatTableSQL); 
                    MasterDataWindow.table_message.setForeground(new Color(57,104,156));
                    MasterDataWindow.table_message.setText("Table[ "+getNewTableName()+" ] successfully created!"); 
                    MasterDataWindow.addValuesButton.setEnabled(true);
                    //MasterDataWindow.setTableName(getNewTableName()); 
                    createTable.setBackground(new Color(200,200,200)); 
                    createTable.setEnabled(false); 
                }catch(Exception ex){
                     MasterDataWindow.table_message.setText("Failed to create table. Try again. ~ "+ex.getMessage()); 
                     MasterDataWindow.table_message.setToolTipText("Failed to create table. Try again. ~ "+ex.getMessage()); 
                     MasterDataWindow.table_message.setForeground(Color.red);
                     Logger.getLogger(Learners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                MasterDataWindow.table_message.setForeground(new Color(57,104,156));
                MasterDataWindow.table_message.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/warning.png")));
                MasterDataWindow.table_message.setText("Cannot save a blank table!"); 
            } 
        }catch(Exception e){Logger.getLogger(CreateTable.class.getName()).log(Level.SEVERE, null, e);
           MasterDataWindow.table_message.setForeground(Color.red);
           MasterDataWindow.table_message.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
           MasterDataWindow.table_message.setText("Error while trying to save the "+getNewTableName()+". Please check your table carefully."); 
           MasterDataWindow.table_message.setText("Error while trying to save the "+getNewTableName()+". Please check your table carefully."); 
         }
    }//GEN-LAST:event_saveActionPerformed
   
   
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
            java.util.logging.Logger.getLogger(CreateTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateTable().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addRow;
    private javax.swing.JTable createTable;
    private javax.swing.JButton deleteRow;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField new_table_name;
    private javax.swing.JButton save;
    // End of variables declaration//GEN-END:variables

 public class MyTableModel extends DefaultTableModel {

    public MyTableModel() {
      super(new String[]{"Field", "Data Type", "Primary Key"}, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      Class clazz = String.class;
      switch (columnIndex) {
        case 0:
          clazz = String.class;
          break;
        case 2:
          clazz = Boolean.class;
          break;
      }
      return clazz;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
      return true;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
      if (aValue instanceof Boolean && column == 2) {
        System.out.println(aValue);
        Vector rowData = (Vector)getDataVector().get(row);
        rowData.set(2, (boolean)aValue);
        fireTableCellUpdated(row, column);
      }
      else{
          Vector rowData = (Vector)getDataVector().get(row);
          rowData.set(column, aValue);
          fireTableCellUpdated(row, column);
         
      }
    }

  }
    
}

