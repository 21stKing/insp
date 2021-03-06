
package inventory_system;

import static inventory_system.MainWindow.exportButton;
import static inventory_system.MainWindow.previousButton;
import static inventory_system.MainWindow.nextButton;
import static inventory_system.MainWindow.saveButton;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tebogo kekana - kinotech (Pty) Ltd.
 */
public class ItemList extends javax.swing.JInternalFrame {
    
    private static int id;
    private static int role;
    Date logtime;
    private Vector<Vector<String>> data; 
    private Vector<String> header;
    private static ArrayList<Category> listCategories;
    private static ArrayList<Itemtype> listItemType;
    MyTableModel tmodel;
    public static int []  tableRowSelected;
    DB db;
    MainWindow main;
    
    public ItemList()  {   
        
        logtime = new Date();
        initComponents();
        setItemList();
        //setTableData(true);
        itemtable.addMouseListener(new ItemList.MyMouseListener());
        itemtable.setModel(tmodel); 
        itemtable.createDefaultColumnsFromModel();
        //String[] actions = {"Actions", "Edit", "Delete", "Add Invoice","Add Quote","Add Receipt", "Quick Report"};
        //JComboBox cbox = new JComboBox(actions);
        //customertable.getColumnModel().getColumn(8).setCellEditor(new DefaultCellEditor(cbox)); 
        this.getContentPane().setBackground(Color.white);
    }
   
    public class MyMouseListener extends MouseAdapter {
     public MyMouseListener() 
      {
      
      }
     
     public void mousePressed(MouseEvent e){
         if ((e.getClickCount() == 1) && (!e.isMetaDown()))
           {
             tableRowSelected = ItemList.this.itemtable.getSelectedRows();
           }
       }
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
            if(Integer.parseInt(data.get(x).get(9)) == 1){
                status = true;
            }else{ status = false; }
            String code    = data.get(x).get(1);
            String desc    = data.get(x).get(2);
            String type    = getItemTypeByID(Integer.parseInt(data.get(x).get(3)));
            String cat     = getCategoryByID(Integer.parseInt(data.get(x).get(4)));
            String sales   = data.get(x).get(5);
            String purch   = data.get(x).get(6);
            String qty     = data.get(x).get(7);
            String date    = data.get(x).get(8);
            tmodel.addRow( new Object[] { code, desc, type, cat, sales, purch, qty, date, status} );
        }
       
        
        //Activate table on main screen
        MainWindow.setActiveTable(itemtable);
        
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

        action_popupmenu = new javax.swing.JPopupMenu();
        edit_item_menu = new javax.swing.JMenuItem();
        delete_item_menu = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        add_quote_item_menu = new javax.swing.JMenuItem();
        add_invoice_item_menu = new javax.swing.JMenuItem();
        add_receipt_item_menu = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        quick_report_item_menu = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        addItem = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        itemtable = new javax.swing.JTable();

        edit_item_menu.setText("Edit");
        edit_item_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_item_menuActionPerformed(evt);
            }
        });
        action_popupmenu.add(edit_item_menu);

        delete_item_menu.setText("Delete");
        action_popupmenu.add(delete_item_menu);
        action_popupmenu.add(jSeparator2);

        add_quote_item_menu.setText("Add Quote");
        action_popupmenu.add(add_quote_item_menu);

        add_invoice_item_menu.setText("Add Invoice");
        action_popupmenu.add(add_invoice_item_menu);

        add_receipt_item_menu.setText("Add Receipt");
        action_popupmenu.add(add_receipt_item_menu);
        action_popupmenu.add(jSeparator3);

        quick_report_item_menu.setText("Quick Report");
        action_popupmenu.add(quick_report_item_menu);

        setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(153, 153, 153)), javax.swing.BorderFactory.createEtchedBorder()));
        setTitle("Item List");
        setPreferredSize(MainWindow.home_desktop.getPreferredSize());

        jLabel1.setText("List of Items");

        addItem.setText("Add New Item");
        addItem.setToolTipText("Add a new customer");
        addItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItemActionPerformed(evt);
            }
        });

        itemtable.setModel(new javax.swing.table.DefaultTableModel(data,header));
        itemtable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        itemtable.setComponentPopupMenu(action_popupmenu);
        jScrollPane1.setViewportView(itemtable);

        jScrollPane2.setViewportView(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addItem, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItemActionPerformed
        this.dispose();
        MainWindow.itemInternalFrame();
        MainWindow.item.item_title.setText("New Item"); 
        MainWindow.item.setMode(0); //edit mode, very important. 
        MainWindow.item.setNewItemCode(); 
        exportButton.setEnabled(false); 
        previousButton.setEnabled(true);
        nextButton.setEnabled(false);
        saveButton.setEnabled(true);
    }//GEN-LAST:event_addItemActionPerformed

    private void edit_item_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_item_menuActionPerformed
        int row = ItemList.this.itemtable.getSelectedRow();
        Object selectedvalue = ItemList.this.itemtable.getValueAt(row,0);
        String code = (String) selectedvalue;
        int itemid  =  getItemID(code);
        
        this.dispose(); 
        MainWindow.itemInternalFrame();
        /*Customers customers = new Customers();
        customers.setVisible(true);
        customers.setSize(MainWindow.home_desktop.getWidth(), MainWindow.home_desktop.getHeight()); 
        ((BasicInternalFrameUI)customers.getUI()).setNorthPane(null);
        MainWindow.home_desktop.add(customers);
        try {
             customers.setSelected(true); 
            }
        catch (PropertyVetoException e) {e.printStackTrace(); }*/
        
        MainWindow.item.item_title.setText("Edit Item - "+code); 
        MainWindow.item.setMode(1); //edit mode, very important. 
        MainWindow.item.setItem(itemid);         
        exportButton.setEnabled(false); 
        previousButton.setEnabled(true);
        nextButton.setEnabled(false);
        saveButton.setEnabled(true);
    }//GEN-LAST:event_edit_item_menuActionPerformed
    
    private int getItemID(String code){
        int id = 0;
        for(int x = 0;x< data.size();x++){
            if(data.get(x).elementAt(1) == code){
                id = Integer.parseInt(data.get(x).elementAt(0));
            }
        }
        
        return id;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu action_popupmenu;
    private javax.swing.JButton addItem;
    private javax.swing.JMenuItem add_invoice_item_menu;
    private javax.swing.JMenuItem add_quote_item_menu;
    private javax.swing.JMenuItem add_receipt_item_menu;
    private javax.swing.JMenuItem delete_item_menu;
    private javax.swing.JMenuItem edit_item_menu;
    private javax.swing.JTable itemtable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenuItem quick_report_item_menu;
    // End of variables declaration//GEN-END:variables

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
    
    private boolean hasSpace(String table_field)     { 
       boolean answer; 
       if(table_field.contains(" ")){ 
           answer = true;
       }else{
         answer =false;
    }
     return answer;  
   }
}
