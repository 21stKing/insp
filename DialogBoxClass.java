
package inventory_system;

import javax.swing.JOptionPane;

/**
 *
 * @author Tebogo kekana - kinotech (Pty) Ltd.
 */
public class DialogBoxClass extends java.awt.Dialog {

    private String ACTIVE_CALLING_CLASS;
    //Customers customer;
    DB db;
    
    public DialogBoxClass(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        categoryname = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();

        setLocationRelativeTo(saveButton);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jLabel1.setText("Category Name");

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(categoryname, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(categoryname, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(saveButton)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public void setActiveCallingClass(String classname){
        this.ACTIVE_CALLING_CLASS = classname;
    }
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        if(categoryname.getText().equals("") || categoryname.getText().isEmpty()){ 
            JOptionPane.showMessageDialog(null, "Please enter Category Name.", "Error", 3);
        }else{
            //save the category
            String category_name = categoryname.getText();
            //System.out.println(categoryname.getText()); 
               try
                 {
                     db = new DB(RunProgram.CONNECTION_MODE);
                     db.setCommit(false); 
                     if(db.getCategory(category_name)){
                          JOptionPane.showMessageDialog(null, category_name+", already exists.", "Error", 2); 
                     }else{
                            
                        String batch = "INSERT INTO category (`catname`, `subtype`) VALUES ('"+category_name+"','"+ACTIVE_CALLING_CLASS+"')";
                        db.executeSQL(batch);
                        db.setCommit(true); 

                        JOptionPane.showMessageDialog(null, "<html>" + category_name + " added successfully</html>", "Confirmation", 1);
                        //close the dialog box
                        setVisible(false);
                        dispose();
                        
                        //update the category dropdown list for the calling class(customer/item/supplier/etc)
                        switch(ACTIVE_CALLING_CLASS){
                            case "Customers":
                                  Customers.updateCategoryList(category_name); 
                                  break;
                            case "Suppliers":
                                  Suppliers.updateCategoryList(category_name); 
                                  break;
                            case "Items":
                                  Items.updateCategoryList(category_name); 
                                  Items.updateSupplierList(category_name);
                                  Items.updateManufactureList(category_name);
                                  Items.updateMeasurementList(category_name);
                                  Items.updateLocationList(category_name); 
                                  break;
                            default:
                                break;
                        }
                     }  
                    db.close(); 
                 }
                 catch (Exception e1)
                  {
                      JOptionPane.showMessageDialog(null, e1, "ERROR - Failed to add "+ACTIVE_CALLING_CLASS+" Category", 2);
                  }
            
        }
            
    }//GEN-LAST:event_saveButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogBoxClass dialog = new DialogBoxClass(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField categoryname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}