/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory_system;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author qxf3984
 */
public class ViewTable extends JInternalFrame {
    protected static DefaultTableModel tmodel;
    private static Vector<Vector<String>> data; 
    private static Vector<String> header;
    private static String activeTable;
    private static int records;
  
    public ViewTable() 
    {
        initComponents();
    }

    public void setModel(Vector<Vector<String>> data,Vector<String> header){
        this.data = data;
        this.header = header;
    }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        table_popupmenu = new javax.swing.JPopupMenu();
        edit = new javax.swing.JMenuItem();
        save = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        delete_record = new javax.swing.JMenuItem();
        delete_all = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        edit.setText("Edit");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });
        table_popupmenu.add(edit);

        save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        save.setText("Save");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        table_popupmenu.add(save);
        table_popupmenu.add(jSeparator1);

        delete_record.setText("Delete");
        delete_record.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_recordActionPerformed(evt);
            }
        });
        table_popupmenu.add(delete_record);

        delete_all.setText("Delete All");
        delete_all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_allActionPerformed(evt);
            }
        });
        table_popupmenu.add(delete_all);

        setBackground(new java.awt.Color(255, 255, 255));
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        table.setAutoCreateRowSorter(true);
        table.setBackground(new java.awt.Color(200, 200, 200));
        table.setModel(new javax.swing.table.DefaultTableModel(data,header));
        table.setComponentPopupMenu(table_popupmenu);
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 54, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed

        table.setBackground(Color.white);
        setTableData(true,activeTable);

    }//GEN-LAST:event_editActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        int row = ViewTable.this.table.getSelectedRow();
        Object selectedID = ViewTable.this.table.getValueAt(row,0);
        int id = Integer.parseInt((String) selectedID);
        int columnCount = table.getColumnCount();
        String columns[] = new String[columnCount];
        String data[] = new String[columnCount];
        String [] splitColumn;
        String [] splitColumn2;
        String column, column2;
        boolean has_space;
        String batch = "UPDATE "+activeTable+" SET ";

        for(int x=0;x<columnCount;x++){
            column = table.getColumnName(x);
            has_space = hasSpace(column);
            if(has_space){
                columns[x] = column;
            }else{
                splitColumn = column.split("\\[");
                splitColumn2 = splitColumn[1].split("\\]");
                columns[x] = splitColumn2[0];
            }
            //get data
            try{data[x] = (ViewTable.this.table.getValueAt(row, x)).toString();}
            catch(NullPointerException e){e.printStackTrace(); data[x] = "";}
        }

        //create batch
        for(int x=0;x<columnCount-1;x++){
            //check if type is integer
            boolean integer = false;
            try{
                int typeInt = Integer.parseInt(data[x+1]);
                integer = true;
            }catch(NumberFormatException e){e.printStackTrace();
            }

            int indentifyer = (columnCount-1)-1;
            if(integer == true){
                if(x == indentifyer){
                    batch = batch +columns[x+1]+" = "+data[x+1];
                }else{
                    batch = batch + columns[x+1]+" = "+data[x+1]+",";
                }
            }else{
                if(x == indentifyer){
                    batch = batch +columns[x+1]+" = '"+data[x+1]+"'";
                }else{
                    batch = batch +columns[x+1]+" = '"+data[x+1]+"',";
                }
            }

        }

        batch = batch + " WHERE "+columns[0]+"="+id;

        try{
            ConnectDatabase database = new ConnectDatabase();
            database.setBatch(batch);
            MasterDataWindow.table_message.setText("Record successfully update!");
            MasterDataWindow.table_message.setForeground(Color.GREEN);
        }catch(Exception ex){
            MasterDataWindow.table_message.setText("Failed to update record");
            MasterDataWindow.table_message.setForeground(Color.red);
            Logger.getLogger(ViewTable.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_saveActionPerformed

    private void delete_recordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_recordActionPerformed
        int row = ViewTable.this.table.getSelectedRow();
        Object selectedID = ViewTable.this.table.getValueAt(row,0);
        int id = Integer.parseInt((String) selectedID);
        int columnCount = table.getColumnCount();
        String columns[] = new String[columnCount];

        String [] splitColumn;
        String [] splitColumn2;
        String column;
        String batch = "DELETE * FROM "+activeTable;

        for(int x=0;x<columnCount;x++){
            column = table.getColumnName(x);
            splitColumn = column.split("\\[");
            splitColumn2 = splitColumn[1].split("\\]");
            columns[x] = splitColumn2[0];
        }

        batch = batch + " WHERE "+columns[0]+" = "+id;

        try{
            ConnectDatabase database = new ConnectDatabase();
            database.setBatch(batch);
            MasterDataWindow.table_message.setText("Record successfully deleted!");
            MasterDataWindow.table_message.setForeground(Color.GREEN);
        }catch(Exception ex){
            MasterDataWindow.table_message.setText("Failed to delete record");
            MasterDataWindow.table_message.setForeground(Color.red);
            Logger.getLogger(ViewTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_delete_recordActionPerformed

    private void delete_allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_allActionPerformed
        // this deletes all records in the current opened table
        int [] selected_rows = ViewTable.this.table.getSelectedRows();
        int size = selected_rows.length;
        int columnCount = table.getColumnCount();
        String columns[] = new String[columnCount];
        int id = 0;
        String [] splitColumn;
        String [] splitColumn2;
        String column;
        String batch = "DELETE * FROM "+activeTable;
        records = 0;
        
        for(int x=0;x<columnCount;x++){
            column = table.getColumnName(x);
            splitColumn = column.split("\\[");
            splitColumn2 = splitColumn[1].split("\\]");
            columns[x] = splitColumn2[0];
        }
        
        try{
            ConnectDatabase database = new ConnectDatabase();
            for(int x = 0;x < size;x++){
                id = Integer.parseInt((String)table.getValueAt(selected_rows[x], 0));
                batch = batch + " WHERE "+columns[0]+" = "+id;
                database.setBatch(batch); 
                batch = "DELETE * FROM "+activeTable;
                records +=1;
            }
            MasterDataWindow.table_message.setText("#"+records+" record(s) successfully deleted!");
            MasterDataWindow.table_message.setForeground(Color.GREEN);
        }catch(Exception ex){
            MasterDataWindow.table_message.setText("Failed to delete record - ID:"+id);
            MasterDataWindow.table_message.setForeground(Color.red);
            Logger.getLogger(ViewTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_delete_allActionPerformed
   
    protected static String getTableName(){
        return activeTable;
    }
    
    protected static void setTableData(boolean editable,String table_name){
          //activeTable = MasterDataWindow.list_of_tables.getSelectedValue().toString();
          //check if table is not a temporary table in access
         activeTable = table_name; 
         boolean isTempTable = checkTable(activeTable);
          
          try{
                ConnectDatabase database = new ConnectDatabase();
                data = database.getTableData(activeTable);
                header = database.getTableHeader(activeTable);
                database.setCloseDatabase();
                
            }catch(Exception e1){
                //e1.printStackTrace();
                MasterDataWindow.table_message.setText(activeTable+" is a temporary system table, therefore you cannot view it"); 
                MasterDataWindow.table_message.setForeground(Color.red);
                //exportTable.setEnabled(false); 
            }
            
           if(editable){
            tmodel = new javax.swing.table.DefaultTableModel(data,header){
             @Override
             public boolean isCellEditable(int row, int column) {
             //all cells false
             return true;
              } 
            };
            table.setModel(tmodel);
            //exportTable.setEnabled(true); 
            records = tmodel.getRowCount();
            if(!isTempTable){
            MasterDataWindow.table_message.setText(""+records+" record(s) returned"); 
            MasterDataWindow.table_message.setForeground(new Color(57,104,156)); 
            }else{
                MasterDataWindow.table_message.setText(activeTable+" is a temporary system table, therefore you cannot view it");
                MasterDataWindow.table_message.setForeground(Color.red);
                //exportTable.setEnabled(false); 
            }
           }else{
               tmodel = new javax.swing.table.DefaultTableModel(data,header){
             @Override
             public boolean isCellEditable(int row, int column) {
             //all cells false
             return false;
              }
            };
            table.setBackground(new Color(200,200,200)); 
            table.setModel(tmodel);  
            //exportTable.setEnabled(true); 
            records = tmodel.getRowCount();
            if(!isTempTable){
            MasterDataWindow.table_message.setText(""+records+" record(s) returned"); 
            MasterDataWindow.table_message.setForeground(new Color(57,104,156)); 
            }else{
                MasterDataWindow.table_message.setText(activeTable+" is a temporary system table, therefore you cannot view it"); 
                MasterDataWindow.table_message.setForeground(Color.red);
                //exportTable.setEnabled(false); 
            }
           }
               
           
           
    }
      
    private static boolean checkTable(String table_name){
        boolean state = false;
        String splitTable [] = table_name.split("~"); 
        String result = splitTable[0];
        
        if(result.isEmpty()){
            state = true;
        }else{
            state = false;
        }
        return state; 
    } 
    
    protected static void exportTableToExcel(){
      boolean help = false;
      int count = 1;
            
      while (!help)
      {
        File file = new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath() + "\\results" + count + ".xls");
        try
        {
          FileWriter out = new FileWriter(file);
          for (int i = 0; i < tmodel.getColumnCount(); i++)
          {
            out.write(tmodel.getColumnName(i) + "\t");
          }
          out.write("\n");
          for (int i = 0; i < tmodel.getRowCount(); i++) 
          {
            for (int j = 0; j < tmodel.getColumnCount(); j++) 
            {
              out.write(tmodel.getValueAt(i, j) + "\t");
            }
            out.write("\n");
          }
          out.close();
          String command = "cmd /c \"" + file.getPath() + "\"";
          Runtime.getRuntime().exec(command);
          help = true;
        }
        catch (IOException e1)
        {
          e1.printStackTrace();
          count++;
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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewTable().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem delete_all;
    private javax.swing.JMenuItem delete_record;
    private javax.swing.JMenuItem edit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem save;
    protected static javax.swing.JTable table;
    private javax.swing.JPopupMenu table_popupmenu;
    // End of variables declaration//GEN-END:variables

    
    private boolean hasSpace(String table_field) 
    { 
       boolean answer; 
       if(table_field.contains(" ")){ 
           answer = true;
       }else{
         answer =false;
    }
     return answer;  
   }
}
