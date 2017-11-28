/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory_system;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tebogo kekana - kinotech (Pty) Ltd.
 */
public class QuotationItems extends javax.swing.JDialog implements ActionListener,PropertyChangeListener {

    private Vector<Vector<Object>> data; 
    private Vector<String> header;
    private static ArrayList<Category> listCategories;
    private static ArrayList<Itemtype> listItemType;
    public static int []  tableRowsSelected;
    public static int tableRowSelected;
    DB db;
    MyTableModel tmodel;
        
    public QuotationItems(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setItemList();
        itemtable.addMouseListener(new QuotationItems.MyMouseListener()); 
        itemtable.setModel(tmodel);  
        
        itemtable.createDefaultColumnsFromModel();
    }
    
     public class MyMouseListener extends MouseAdapter {
       public MyMouseListener() 
       {
      
       }
     
     public void mousePressed(MouseEvent e){
         if ((e.getClickCount() == 2) && (!e.isMetaDown()))
           {
             //tableRowsSelected = QuotationItems.this.itemtable.getSelectedRows(); //gets multiple selected rows
             int colCount = itemtable.getColumnCount() - 1;  // -1 is to eliminate the boolean table field value. column name is called Active
             tableRowSelected = QuotationItems.this.itemtable.getSelectedRow(); //gets single selected row
             String tableValues [] = new String[colCount];
             for(int col = 0;col < colCount;col++){
                 Object selectedvalue = QuotationItems.this.itemtable.getValueAt(tableRowSelected,col);
                 tableValues[col] = (String) selectedvalue;
             }             
             addItemToQuote(tableValues);
             //Close popup table
             QuotationItems.this.setVisible(false); 
           }
       }
     } 
    
   public static void addItemToQuote(String [] items){
      int rowCount = Quotations.quote_table.getRowCount();
      
      if(rowCount == 0){
        for(int r = 0; r < rowCount;r++){
            Quotations.tmodel.setValueAt("+", r, 0); 
            Quotations.tmodel.setValueAt("-", r, 1);
            Quotations.tmodel.setValueAt(items[0], r, 2); //Item code
            Quotations.tmodel.setValueAt(items[1], r, 3); //Description
            Quotations.tmodel.setValueAt("1", r, 4); //Quantity --- this is a default quantity
            Quotations.tmodel.setValueAt(items[4], r, 5); //Item Price
            Quotations.tmodel.setValueAt(items[4], r, 6); //Total Price
        }
        //Append row at the bottom of quote_table
        Quotations.tmodel.addRow( new Object[] {"+","-","", "", "", "", ""}); 
      }else if(rowCount > 0){
          for(int r = rowCount - 1; r < rowCount;r++){
            Quotations.tmodel.setValueAt("+", r, 0); 
            Quotations.tmodel.setValueAt("-", r, 1);
            Quotations.tmodel.setValueAt(items[0], r, 2); //Item code
            Quotations.tmodel.setValueAt(items[1], r, 3); //Description
            Quotations.tmodel.setValueAt("1", r, 4); //Quantity --- this is a default quantity
            Quotations.tmodel.setValueAt(items[4], r, 5); //Item Price
            Quotations.tmodel.setValueAt(items[4], r, 6); //Total Price
        }
        //Append row at the bottom of quote_table
        Quotations.tmodel.addRow( new Object[] {"+","-","", "", "", "", ""}); 
      }else{
          System.out.println("No rows found on table");
      }
      //Do calculations
      Quotations.calculateTotals(); 
   }  
      
   private void setItemList(){
       //Get Items
        try
        {
          db = new DB(RunProgram.CONNECTION_MODE);
          data  = db.getItems();      
          listCategories = db.getCategories();
          listItemType   = db.getItemTypes(); 
          db.close();
        }
        catch (Exception e){ e.printStackTrace(); }
        
        tmodel = new MyTableModel();
        
        for(int x =0;x<data.size();x++){
            boolean status;
            if(Integer.parseInt((String) data.get(x).get(9)) == 1){
                status = true;
            }else{ status = false; }
            String code    = (String) data.get(x).get(1);
            String desc    = (String) data.get(x).get(2);
            String type    = getItemTypeByID(Integer.parseInt((String) data.get(x).get(3)));
            String cat     = getCategoryByID(Integer.parseInt((String) data.get(x).get(4)));
            String sales   = (String) data.get(x).get(5);
            String purch   = (String) data.get(x).get(6);
            String qty     = (String) data.get(x).get(7);
            String date    = (String) data.get(x).get(8);
            tmodel.addRow( new Object[] { code, desc, type, cat, sales, purch, qty, date, status} );
        }
        
        //Activate table on main screen
        //MainWindow.setActiveTable(itemtable);
        
    }
   
   private String getCategoryByID(int catid){
        String category = "";
        for(int x = 0;x<listCategories.size();x++){
            if(listCategories.get(x).getCatid() == catid){
                category = listCategories.get(x).getCatname();
            }
        }
        
        return category;
    }
   
   private String getItemTypeByID(int typeid){
        String item = "";
        for(int x = 0;x<listItemType.size();x++){
            if(listItemType.get(x).getTypeid() == typeid){
                item = listItemType.get(x).getType();
            }
        }
        
        return item;
    }  
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        itemtable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/inventory_system/image/insp_icon_demo_39x45.png")).getImage());

        itemtable.setModel(new javax.swing.table.DefaultTableModel(data,header));
        jScrollPane1.setViewportView(itemtable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(QuotationItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuotationItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuotationItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuotationItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuotationItems dialog = new QuotationItems(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable itemtable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    public class MyTableModel extends DefaultTableModel {

    public MyTableModel() {
      //super(new String[]{"Field", "Data Type", "Primary Key"}, 0);
      super(new String[]{"Code", "Description", "Type","Category", "Sales Price", "Purch. Price.", "Qty On Hand","Date Created" ,"Active"}, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      Class clazz = String.class;
      switch (columnIndex) {
        case 0:
          clazz = String.class;
          break;
        case 8:
          clazz = Boolean.class;
          break;
      }
      return clazz;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
      return false;
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
