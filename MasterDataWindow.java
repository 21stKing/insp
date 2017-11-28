
package inventory_system;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author qxf3984
 */
public class MasterDataWindow extends JDialog implements ActionListener,PropertyChangeListener 
{    
    private static int id;
    private static int role;
    Date logtime;
    private JOptionPane optionPane;
    private String finishBtn = "Finish";
    private String okBtn = "Ok";
    public Object value = "";
    ArrayList<String> tableslist;
    private DefaultListModel<String> model = new DefaultListModel();
    private DefaultTableModel tmodel;
    public static int []  rowSelected;
    private Vector<Vector<String>> data; 
    private Vector<String> header;
    private String activeTable;
    protected static String tableName;
    private int records;
    BlankWindow bw;
    ViewTable vt;
    CreateTable ct;
    InsertTableData td;
  
   public MasterDataWindow(JFrame aFrame,int idin, int roleIn){
        
        super(aFrame, true);
        //this.setSize(970,790);
        id = idin;
        role = roleIn;
        logtime = new Date(); 
        initComponents();
        //this.setBackground(new Color(226,223,214));
        BlankWindowInternalFrame();
        this.getContentPane().setBackground(Color.white);
        this.setTitle("Master Data ~ "+id );
        Object[] options = {okBtn,finishBtn};
        //Create the JOptionPane.
        optionPane = new JOptionPane(this.getContentPane(),JOptionPane.PLAIN_MESSAGE,JOptionPane.OK_CANCEL_OPTION,null,options,options[0]);
        optionPane.setBackground(new Color(226,223,214)); 
        //Make this dialog display it.
        setContentPane(optionPane);

        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                    optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
            }
        });

        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);
        
        list_of_tables.addMouseMotionListener(new MouseAdapter(){
        public void mouseMoved(MouseEvent me){
         Point p = new Point(me.getX(),me.getY());
         list_of_tables.setSelectedIndex(list_of_tables.locationToIndex(p)); 
        }
        });
        
        list_of_tables.addMouseListener(new MouseAdapter(){
        public void mouseReleased(MouseEvent e){
          if(e.isPopupTrigger() && e.getClickCount() == 1){
           // call popup menu
           list_popupmenu.show(e.getComponent(), e.getX(),e.getY());              
          }                
         }
        public void mousePressed(MouseEvent e){
            if(e.isPopupTrigger() && e.getClickCount() == 1){
                //call popup menu
                System.out.println("Mouse pressed");
            }
        }
        });
        
    }
       
   public void actionPerformed(ActionEvent e) {
        optionPane.setValue(okBtn);
    }
 
   public void propertyChange(PropertyChangeEvent e){
        String prop = e.getPropertyName();    
        if (isVisible() && (e.getSource() == optionPane) && (JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) 
        {
          value = optionPane.getValue();
         
          if (value == JOptionPane.UNINITIALIZED_VALUE)
            {
                //ignore reset
                return;
            }
        }
        
        clearAndHide();
    }
    
   public void clearAndHide() {
        setVisible(false);
    }  
    
   private MasterDataWindow() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   protected void BlankWindowInternalFrame(){
    this.bw = new BlankWindow();
    this.bw.setVisible(true);
    ((BasicInternalFrameUI)this.bw.getUI()).setNorthPane(null);
    this.desktopPanel.add(this.bw);
    try {
      this.bw.setSelected(true); 
    }
    catch (PropertyVetoException e) {
    }
  }
   
   protected void ViewTableInternalFrame(){
    this.vt = new ViewTable();
    this.vt.setVisible(true);
    ((BasicInternalFrameUI)this.vt.getUI()).setNorthPane(null);
    this.desktopPanel.add(this.vt);
    try {
      this.vt.setSelected(true); 
    }
    catch (PropertyVetoException e) {
    }
  }
    
   protected void CreateTableInternalFrame(){
    this.ct = new CreateTable();
    this.ct.setVisible(true);
    ((BasicInternalFrameUI)this.ct.getUI()).setNorthPane(null);
    this.desktopPanel.add(this.ct);
    try {
      this.ct.setSelected(true); 
    }
    catch (PropertyVetoException e) {
    }
  } 
     
   protected void insertTableDataInternalFrame(){
    this.td = new InsertTableData();
    this.td.setVisible(true);
    ((BasicInternalFrameUI)this.td.getUI()).setNorthPane(null);
    this.desktopPanel.add(this.td);
    try {
      this.td.setSelected(true); 
    } 
    catch (PropertyVetoException e) {
    }
  }
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        list_popupmenu = new javax.swing.JPopupMenu();
        Display = new javax.swing.JMenuItem();
        Edit = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        delete = new javax.swing.JMenuItem();
        table_popupmenu = new javax.swing.JPopupMenu();
        edit = new javax.swing.JMenuItem();
        save = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        delete_record = new javax.swing.JMenuItem();
        jScrollPane2 = new javax.swing.JScrollPane();
        list_of_tables = new javax.swing.JList();
        jToolBar1 = new javax.swing.JToolBar();
        viewTablesButton = new javax.swing.JButton();
        createTable = new javax.swing.JButton();
        exportTableButton = new javax.swing.JButton();
        addValuesButton = new javax.swing.JButton();
        table_message = new javax.swing.JLabel();
        desktopPanel = new javax.swing.JDesktopPane();

        Display.setText("Display");
        Display.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DisplayActionPerformed(evt);
            }
        });
        list_popupmenu.add(Display);

        Edit.setText("Edit");
        Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditActionPerformed(evt);
            }
        });
        list_popupmenu.add(Edit);
        list_popupmenu.add(jSeparator2);

        delete.setText("Delete");
        delete.setToolTipText("Delete");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        list_popupmenu.add(delete);

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(226, 223, 214));
        setIconImage(new ImageIcon(getClass().getResource("/inventory_system/image/insp_icon_demo_39x45.png")).getImage());
        setLocationByPlatform(true);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        list_of_tables.setBackground(new java.awt.Color(200, 200, 200));
        list_of_tables.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                list_of_tablesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(list_of_tables);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setOpaque(false);

        viewTablesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/table_icon_22x20.png"))); // NOI18N
        viewTablesButton.setText("Tables");
        viewTablesButton.setToolTipText("view tables");
        viewTablesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewTablesButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(viewTablesButton);

        createTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/new_table_22x19.png"))); // NOI18N
        createTable.setText("New");
        createTable.setToolTipText("Create new table");
        createTable.setFocusable(false);
        createTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createTableActionPerformed(evt);
            }
        });
        jToolBar1.add(createTable);

        exportTableButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/exportExcel.png"))); // NOI18N
        exportTableButton.setText("Export");
        exportTableButton.setToolTipText("Export table to excel");
        exportTableButton.setEnabled(false);
        exportTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportTableButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(exportTableButton);

        addValuesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/add_table_values_22x20.png"))); // NOI18N
        addValuesButton.setText("Insert");
        addValuesButton.setEnabled(false);
        addValuesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addValuesButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(addValuesButton);

        table_message.setForeground(new java.awt.Color(57, 104, 156));

        desktopPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(157, 157, 157)
                .addComponent(table_message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(desktopPanel))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                    .addComponent(desktopPanel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(table_message, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(160, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void viewTablesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewTablesButtonActionPerformed
        model.clear();
        try {
             ConnectDatabase database = new ConnectDatabase();
             tableslist = database.TableList();
         } catch (Exception ex) {
            Logger.getLogger(MasterDataWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int size = tableslist.size();
        for(int x = 0; x<size;x++){
        model.addElement(tableslist.get(x));
        }
        list_of_tables.setModel(model); 
        
        //ViewTableInternalFrame();
    }//GEN-LAST:event_viewTablesButtonActionPerformed

    private void list_of_tablesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_list_of_tablesMouseClicked
        //get selected value and display the table accordingly 
        if ((evt.getClickCount() == 2) && (!evt.isMetaDown()))
        {  
           ViewTableInternalFrame();  
           activeTable = list_of_tables.getSelectedValue().toString(); 
           ViewTable.setTableData(false,activeTable);
        }
        addValuesButton.setEnabled(true); 
        exportTableButton.setEnabled(true); 
    }//GEN-LAST:event_list_of_tablesMouseClicked
    
    private void DisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DisplayActionPerformed
      //setTableData(false);
      ViewTableInternalFrame();  
      activeTable = list_of_tables.getSelectedValue().toString(); 
      ViewTable.setTableData(false,activeTable);  
    }//GEN-LAST:event_DisplayActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed

      // table.setBackground(Color.white);
      // setTableData(true);
       
    }//GEN-LAST:event_editActionPerformed

    private void EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditActionPerformed
        ViewTableInternalFrame();  
        ViewTable.table.setBackground(Color.white);
        activeTable = list_of_tables.getSelectedValue().toString(); 
        ViewTable.setTableData(true,activeTable);
    }//GEN-LAST:event_EditActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        /* int row = MasterDataWindow.this.table.getSelectedRow();
         Object selectedID = MasterDataWindow.this.table.getValueAt(row,0); 
         int id = Integer.parseInt((String) selectedID);
         int columnCount = table.getColumnCount();
         String columns[] = new String[columnCount];
         String data[] = new String[columnCount];
         String [] splitColumn;
         String [] splitColumn2;
         String column;
         String batch = "UPDATE "+activeTable+" SET "; 
          
         for(int x=0;x<columnCount;x++){
            column = table.getColumnName(x);  
            splitColumn = column.split("\\["); 
            splitColumn2 = splitColumn[1].split("\\]");
            columns[x] = splitColumn2[0];
            //get data
            data[x] = (MasterDataWindow.this.table.getValueAt(row, x)).toString();
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
                 batch = batch + columns[x+1]+" = "+data[x+1]; 
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
             table_message.setText("Record successfully update!"); 
             table_message.setForeground(Color.GREEN);
         }catch(Exception ex){
              table_message.setText("Failed to update record"); 
              table_message.setForeground(Color.red);
              Logger.getLogger(MasterDataWindow.class.getName()).log(Level.SEVERE, null, ex);
         }
        */
    }//GEN-LAST:event_saveActionPerformed

    private void delete_recordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_recordActionPerformed
        /* int row = MasterDataWindow.this.table.getSelectedRow();
         Object selectedID = MasterDataWindow.this.table.getValueAt(row,0); 
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
             table_message.setText("Record successfully deleted!"); 
             table_message.setForeground(Color.GREEN);
         }catch(Exception ex){
              table_message.setText("Failed to delete record"); 
              table_message.setForeground(Color.red);
              Logger.getLogger(MasterDataWindow.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }//GEN-LAST:event_delete_recordActionPerformed

    private void exportTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportTableButtonActionPerformed
        ViewTable.exportTableToExcel(); 
    }//GEN-LAST:event_exportTableButtonActionPerformed

    private void createTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createTableActionPerformed
        
        exportTableButton.setEnabled(false); 
        // create a new database table
        String table_name = "";
        try{
        table_name = (JOptionPane.showInputDialog(null, "Enter table name", "New Table",JOptionPane.PLAIN_MESSAGE )).toLowerCase();
        if(table_name.isEmpty()){
           table_message.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
           table_message.setText("Cannot create a blank table. Please try again!"); 
           table_message.setForeground(Color.red);  
        }else{
            CreateTableInternalFrame();
            ct.setNewTableName(table_name); 
            table_message.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
            table_message.setText(""); 
            table_message.setForeground(Color.white);
        }
        
        }catch(NullPointerException e){
           table_message.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
           table_message.setText("Action aborted by user"); 
           //table_message.setText("Table cannot be created.~ "+e); 
           table_message.setForeground(Color.red);  
        }
    }//GEN-LAST:event_createTableActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        // delete table
        activeTable = list_of_tables.getSelectedValue().toString(); 
        deleteDatabaseTable(activeTable);
        ViewTableInternalFrame(); 
        
    }//GEN-LAST:event_deleteActionPerformed

    private void addValuesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addValuesButtonActionPerformed
       
       String tname = ViewTable.getTableName(); 
       td.setTableName(tname);
       insertTableDataInternalFrame();
       //td.setTableName(activeTable);
       //InsertTableData.setTableName(getTableName()); 
    }//GEN-LAST:event_addValuesButtonActionPerformed
    
    protected static void setTableName(String tname){
        tableName = tname;
    }
    
    protected static String getTableName(){
        
        return tableName;
    }
    
    private void deleteDatabaseTable(String table){
        String sql = "Drop Table "+table;
        
        int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "+table, "Confirm Table Deletion", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if(answer == 0){
            switch(table){
                case "administrator" : MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break; 
                case "consumables": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;
                case "courses": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;    
                case "data_store_01": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;    
                case "db_config": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;    
                case "equipment_issue_list": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;
                case "equipment_list": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;    
                case "equipment_receive_list": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;
                case "facilitator": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;    
                case "issue_list_01": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;    
                case "learner_details": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;    
                case "learner_list": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;    
                case "learner_list_hist": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;
                case "network_config": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;    
                case "notice_board": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;    
                case "receive_list_01": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;
                case "storeman": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;    
                case "storeman_list": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;   
                case "tempPasswords": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;
                case "tool_issue_list": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;    
                case "tool_list": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;
                case "tool_receive_list": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;    
                case "userlog": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;    
                case "users": MasterDataWindow.table_message.setText("System tables( "+table+" ) cannot be deleted!");
                                      break;    
                default         :     try{
                                        ConnectDatabase database = new ConnectDatabase();
                                        database.setBatch(sql); 
                                        MasterDataWindow.table_message.setForeground(new Color(57,104,156));
                                        MasterDataWindow.table_message.setText("Table[ "+table+" ] successfully deleted!"); 
                                      }catch(Exception ex){
                                         MasterDataWindow.table_message.setText("Failed to delete [ "+table+" ] ~ "+ex.getMessage()); 
                                         MasterDataWindow.table_message.setForeground(Color.red);
                                      }
                                      break;    
                    
            }
        }else{
           MasterDataWindow.table_message.setForeground(new Color(57,104,156));
           MasterDataWindow.table_message.setText("Action arboted!"); 
        }
        
    }
    
    private void exportTableToExcel(){
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
    
    /*private void setTableData(boolean editable){
          activeTable = list_of_tables.getSelectedValue().toString();
          //check if table is not a temporary table in access
          boolean isTempTable = checkTable(activeTable);
          
          try{
                ConnectDatabase database = new ConnectDatabase();
                data = database.getTableData(activeTable);
                header = database.getTableHeader(activeTable);
                database.setCloseDatabase();
                
            }catch(Exception e1){
                //e1.printStackTrace();
                table_message.setText(activeTable+" is a temporary system table, therefore you cannot view it"); 
                table_message.setForeground(Color.red);
                exportTable.setEnabled(false); 
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
            exportTable.setEnabled(true); 
            records = tmodel.getRowCount();
            if(!isTempTable){
            table_message.setText(""+records+" record(s) returned"); 
            table_message.setForeground(new Color(57,104,156)); 
            }else{
                table_message.setText(activeTable+" is a temporary system table, therefore you cannot view it");
                table_message.setForeground(Color.red);
                exportTable.setEnabled(false); 
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
            exportTable.setEnabled(true); 
            records = tmodel.getRowCount();
            if(!isTempTable){
            table_message.setText(""+records+" record(s) returned"); 
            table_message.setForeground(new Color(57,104,156)); 
            }else{
                table_message.setText(activeTable+" is a temporary system table, therefore you cannot view it"); 
                table_message.setForeground(Color.red);
                exportTable.setEnabled(false); 
            }
           }
               
           
           
    }*/
    
    private boolean checkTable(String table_name){
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
            java.util.logging.Logger.getLogger(MasterDataWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MasterDataWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MasterDataWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MasterDataWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MasterDataWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Display;
    private javax.swing.JMenuItem Edit;
    protected static javax.swing.JButton addValuesButton;
    private javax.swing.JButton createTable;
    private javax.swing.JMenuItem delete;
    private javax.swing.JMenuItem delete_record;
    protected javax.swing.JDesktopPane desktopPanel;
    private javax.swing.JMenuItem edit;
    private javax.swing.JButton exportTableButton;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    protected static javax.swing.JList list_of_tables;
    private javax.swing.JPopupMenu list_popupmenu;
    private javax.swing.JMenuItem save;
    public static javax.swing.JLabel table_message;
    private javax.swing.JPopupMenu table_popupmenu;
    private javax.swing.JButton viewTablesButton;
    // End of variables declaration//GEN-END:variables
}
