
package inventory_system;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.FormatStringValue;
import org.jdesktop.swingx.table.DatePickerCellEditor;

/**
 *
 * @author qxf3984
 */
public class InsertTableData extends JInternalFrame {

    private Vector<Vector<String>> data; 
    private Vector<String> header;
    private static String table_name;
    private DefaultTableModel tmodel;
    private String primaryKey;
    private String date;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    FormatStringValue sv = new FormatStringValue(sdf); //dd-MM-yyyy HH:mm:ss
    private int columnIndex;
    public InsertTableData() 
    {   
        initComponents();
        //setTableName(MasterDataWindow.tableName);
        columnIndex = 0;
         try{
             ConnectDatabase database = new ConnectDatabase();
             header = database.getTableHeader(table_name);
             data = new Vector<>(); //do not return any data from db. this will make sure that you only inset new data
             //data   = database.getTableData(table_name); 
             columnIndex = database.getIndexOfDateColumnType(table_name);
            }catch(Exception ex){
              MasterDataWindow.table_message.setText("Loading table( "+table_name+" ) failed."); 
              MasterDataWindow.table_message.setToolTipText("Loading table( "+table_name+" ) failed."); 
              MasterDataWindow.table_message.setForeground(new Color(57,104,156));
              Logger.getLogger(Learners.class.getName()).log(Level.SEVERE, null, ex);
            }
         tmodel = new javax.swing.table.DefaultTableModel(data,header);
         inserTableValues.setModel(tmodel);
         inserTableValues.createDefaultColumnsFromModel();
         DatePickerCellEditor dpc = new DatePickerCellEditor(sdf);
         dpc.setClickCountToStart(0); 
         if(columnIndex == 0){
         }else{
            inserTableValues.getColumnModel().getColumn(columnIndex - 1).setCellEditor(dpc);
            inserTableValues.getColumnModel().getColumn(columnIndex - 1).setCellRenderer(new DefaultTableRenderer(sv,JLabel.CENTER)); 
         }
    }
    
    protected static void setTableName(String table){
        table_name = table;
    }
    
    protected static String getTableName(){
        return table_name;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        inserTableValues = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        addRow = new javax.swing.JButton();
        deleteRow = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        save = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Insert new table values"));

        inserTableValues.setAutoCreateRowSorter(true);
        inserTableValues.setModel(new javax.swing.table.DefaultTableModel(data,header));
        inserTableValues.setCellSelectionEnabled(true);
        jScrollPane1.setViewportView(inserTableValues);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRowActionPerformed
         tmodel.addRow(new Object[]{new String()});        
    }//GEN-LAST:event_addRowActionPerformed

    private void deleteRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteRowActionPerformed
        int rows = 0;
        try{rows = tmodel.getRowCount();
           if(rows != 0){
             tmodel.removeRow(rows-1);     
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

        int rowCount         = inserTableValues.getRowCount();
        int columnCount = inserTableValues.getColumnCount();
        String columns[] = new String[columnCount];
        String [] splitColumn;
        String [] splitColumn2;
        String column;
        String valueFromTable;
        String InsertSQL = "",fieldNames = "",values = "";
        String data[][] = new String[rowCount][columnCount];
        String valuesToInsert[] = new String[rowCount];
        
        //Get column names
        for(int x=0;x<columnCount;x++){
            column = inserTableValues.getColumnName(x);
            splitColumn = column.split("\\[");
            splitColumn2 = splitColumn[1].split("\\]");
            columns[x] = splitColumn2[0];
        }
        
        //Get data from table
        for(int x=0;x<rowCount;x++){
           for(int y=0;y<columnCount;y++){
              try{
                  valueFromTable = (InsertTableData.this.inserTableValues.getValueAt(x, y)).toString();
                  data[x][y] = valueFromTable; 
                 } catch(NullPointerException e)
                      {
                         data[x][y] = "";
                      }     
             }
         }
        
        /*****************************************
         * taking note of a possible autonumber 
         *****************************************/
        try{
            ConnectDatabase database = new ConnectDatabase();
            primaryKey = database.getPrimaryKey(table_name);
        }catch(Exception ex){
            Logger.getLogger(ViewTable.class.getName()).log(Level.SEVERE, null, ex);
            primaryKey = "";
        }
        
        /*****************************************
         * created table field names
         *****************************************/
        if(primaryKey == ""){
            int colCount = columnCount;
            int removeComaIdentifyer = colCount-1;
            for(int x=0;x<colCount;x++){
              if(x==removeComaIdentifyer){
                 fieldNames = fieldNames + columns[x];
              }else{
                 fieldNames = fieldNames + columns[x]+",";
              }           
            }
        }else{
            int colCount2 = columnCount-1;
            int removeComaIdentifyer2 = colCount2;
            for(int x=1;x<=colCount2;x++){
              if(x==removeComaIdentifyer2){
                 fieldNames = fieldNames + columns[x];
              }else{
                 fieldNames = fieldNames + columns[x]+",";
              }           
             }
        } 
        
        /*****************************************
         * create values                         *   
         *****************************************/
        if(primaryKey == ""){
            int colCount = columnCount;
            for(int r=0;r<rowCount;r++){
               for(int c=0;c<colCount;c++){ 
                //check if type is integer
                boolean integer = false;
                try{
                    int typeInt = Integer.parseInt(data[r][c]);
                    integer = true;
                }catch(NumberFormatException e){//e.printStackTrace();
                }

                int removeComaIdentifyer = colCount-1;     //identifyer
                if(integer == true){
                    if(c == removeComaIdentifyer){
                        values = values + data[r][c];
                    }else{
                        values = values + data[r][c]+",";
                    }
                }else{
                    if(c == removeComaIdentifyer){
                        if(columnIndex == 0){ //checks checks if there is a date type field in the table
                          values = values + "'"+data[r][c]+"'"; 
                        }else{
                            if(c == columnIndex-1){
                                try {date = modifyDateLayout(data[r][c]);
                                } catch (ParseException ex) {Logger.getLogger(InsertTableData.class.getName()).log(Level.SEVERE, null, ex);}
                                values = values + "#"+date+"#"; 
                              }else{
                                values = values + "'"+data[r][c]+"'"; 
                            }
                        }
                    }else{
                        if(columnIndex == 0){
                          values = values + "'"+data[r][c]+"',";
                        }else{
                            if(c == columnIndex-1){
                                try { date = modifyDateLayout(data[r][c]);
                                } catch (ParseException ex) {Logger.getLogger(InsertTableData.class.getName()).log(Level.SEVERE, null, ex);}
                                values = values + "#"+date+"#,";
                            }else{
                                values = values + "'"+data[r][c]+"',";
                            }
                        }  
                    }
                }
              }
               valuesToInsert[r] = values;
               values = "";
             }
        }else{
            int colCount = columnCount-1;
            for(int r=0;r<rowCount;r++){
               for(int c=1;c<=colCount;c++){ 
                //check if type is integer
                boolean integer = false;
                try{
                    int typeInt = Integer.parseInt(data[r][c]);
                    integer = true;
                }catch(NumberFormatException e){//e.printStackTrace();
                }

                int removeComaIdentifyer = colCount;     //identifyer
                if(integer == true){
                    if(c == removeComaIdentifyer){
                       if(columnIndex == 0){ //checks checks if there is a date type field in the table 
                           values = values + data[r][c];
                       }else{
                            if(c == (columnIndex-1) - 1){
                                try {date = modifyDateLayout(data[r][c]);
                                } catch (ParseException ex) {Logger.getLogger(InsertTableData.class.getName()).log(Level.SEVERE, null, ex);}
                                values = values + "#"+date+"#"; 
                              }else{
                                values = values + ""+data[r][c]+""; 
                            }  
                       }       
                    }else{
                         if(columnIndex == 0){
                          values = values + data[r][c]+",";
                        }else{
                            if(c == (columnIndex-1) - 1){
                                try { date = modifyDateLayout(data[r][c]);
                                } catch (ParseException ex) {Logger.getLogger(InsertTableData.class.getName()).log(Level.SEVERE, null, ex);}
                                values = values + "#"+date+"#,";
                            }else{
                                values = values + data[r][c]+",";
                            }
                        }
                        //values = values + data[r][c]+",";
                    }
                }else{
                    if(c == removeComaIdentifyer){
                         if(columnIndex == 0){
                            values = values + "'"+data[r][c]+"'"; 
                        }else{
                            if(c == columnIndex-1){
                                try { date = modifyDateLayout(data[r][c]);
                                } catch (ParseException ex) {Logger.getLogger(InsertTableData.class.getName()).log(Level.SEVERE, null, ex);}
                                values = values + "#"+date+"#";
                            }else{
                                values = values + "'"+data[r][c]+"'"; 
                            }
                        }
                        
                        //values = values + "'"+data[r][c]+"'"; 
                    }else{
                        if(columnIndex == 0){
                            values = values + "'"+data[r][c]+"',";
                        }else{
                            if(c == columnIndex-1){
                                try { date = modifyDateLayout(data[r][c]);
                                } catch (ParseException ex) {Logger.getLogger(InsertTableData.class.getName()).log(Level.SEVERE, null, ex);}
                                values = values + "#"+date+"#,";
                            }else{
                               values = values + "'"+data[r][c]+"',";
                            }
                        }                        
                        //values = values + "'"+data[r][c]+"',";
                    }
                }
              }
               valuesToInsert[r] = values;
               values = "";
             }
        }

        try{
            ConnectDatabase database = new ConnectDatabase();
            for(int x=0;x<valuesToInsert.length;x++){
              InsertSQL = "INSERT INTO "+table_name+"("+fieldNames+") VALUES("+valuesToInsert[x]+")";  
              database.setBatch(InsertSQL);
            }
            MasterDataWindow.table_message.setText("Table successfully update!");
            MasterDataWindow.table_message.setForeground(Color.GREEN);
        }catch(Exception ex){
            MasterDataWindow.table_message.setText("Failed to insert values ~ "+ex.getMessage().toString());
            MasterDataWindow.table_message.setForeground(Color.red);
            MasterDataWindow.table_message.setToolTipText("Failed to insert values ~ "+ex.getMessage().toString()); 
            //Logger.getLogger(ViewTable.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_saveActionPerformed

    private String modifyDateLayout(String inputDate) throws ParseException{
       Date date = new SimpleDateFormat("EEE MMM d hh:mm:ss z yyyy").parse(inputDate);
       SimpleDateFormat newDate = new SimpleDateFormat("dd-MM-yyyy");
       SimpleDateFormat newTime = new SimpleDateFormat("HH:mm:ss");
       Date Date = new Date();
       String dateOnly = newDate.format(date);
       String time = newTime.format(Date);
       
       String ddate = dateOnly+" "+time;
       //String ddate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(date);
       return ddate;
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
            java.util.logging.Logger.getLogger(InsertTableData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InsertTableData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InsertTableData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InsertTableData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InsertTableData().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addRow;
    private javax.swing.JButton deleteRow;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JTable inserTableValues;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton save;
    // End of variables declaration//GEN-END:variables

    
 /* public class DateCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object value, boolean selected, boolean hasFocus, int row, int column) {

        if (value instanceof Date) {

            // You could use SimpleDateFormatter instead
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            value = formatter.getDateInstance().format(value);

        }


        return super.getTableCellRendererComponent(jtable, value, selected, hasFocus, row, column);

    } 
  } */ 
    
}

