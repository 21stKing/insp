
package inventory_system;

import java.util.Vector;

/**
 *
 * @author qxf3984
 */
public class SearchPopUpPanel extends javax.swing.JPopupMenu {
    
    private Vector<Vector<Object>> data; 
    private Vector<String> header;
    protected static String locationdata = null;
    protected static String descriptiondata = null;
    protected static String storedata = null;
    protected static String currentdata = null;
    
    public SearchPopUpPanel() 
    {
        initComponents(); 
        MainWindow.previousButton.setEnabled(true); 
    }
    
    public void setTableData(Vector<Vector<Object>> dataIn,Vector<String> headerIn)
    {
        this.data = dataIn;
        this.header = headerIn;
        searchTable.setModel(new javax.swing.table.DefaultTableModel(data,header));  
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        searchTable = new javax.swing.JTable();

        searchTable.setModel(new javax.swing.table.DefaultTableModel(data,header));
        searchTable.setAlignmentX(11.0F);
        searchTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                searchTableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(searchTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTableMousePressed
        // mouse listener on row selected

        if ((evt.getClickCount() == 1) && (!evt.isMetaDown()))
        {    
             int row = SearchPopUpPanel.this.searchTable.getSelectedRow();
             //String location = (String)SearchPopUpPanel.this.searchTable.getValueAt(row, 2);
             String itemID = (String)SearchPopUpPanel.this.searchTable.getValueAt(row, 0);
             int ID = Integer.parseInt(itemID);
             locationdata = (String)SearchPopUpPanel.this.searchTable.getValueAt(row, 2);
             descriptiondata = (String)SearchPopUpPanel.this.searchTable.getValueAt(row, 3);
             storedata = (String)SearchPopUpPanel.this.searchTable.getValueAt(row, 1);
             currentdata = (String)SearchPopUpPanel.this.searchTable.getValueAt(row, 4);
             MainWindow.setCheckData(locationdata, descriptiondata, currentdata);
        }

    }//GEN-LAST:event_searchTableMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable searchTable;
    // End of variables declaration//GEN-END:variables
}
