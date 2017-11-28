
package inventory_system;

/**
 *
 * @author
 */


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import static inventory_system.Learners.tableRowSelected;


public class Learners extends JInternalFrame {

    private static int id;
    private static int role;
    Date logtime;
    public static String name;
    public static String surname;
    public static String [] locationList;
    private Connection con;
    private ResultSet rs;
    ResultSetMetaData md;
    private DefaultTableModel model_one;
    public static int []  tableRowSelected;
    public static int []  rowSelected;
    private Vector<Vector<String>> data; 
    private Vector<String> header;
    private Object ID;
    public static int []  tableRowSelected2;
    private static String file_name;
    private static String fullPathName;
    private static String file_directory;
    private static String image_name;
    private static String fullPathImage;
    private static String image_directory;
    protected static String [] listFaci;
    private DefaultTableModel tmodel;
    private JMenuItem menu_item;
    private String facilitator;
    
    public Learners(int idin, int roleIn)   {
        id      = idin;
        role    = roleIn;
        logtime = new Date();
       
        //create headers for learner table
       /* header = new Vector<String>();
        header.add("Learner ID");
        header.add("Name");
        header.add("Surname");
        header.add("Company ID");
        header.add("Group");
        header.add("Level");
        header.add("E-mail");*/
        
        initComponents();
        buttonPanel.setBackground(Color.white); 
        this.getContentPane().setBackground(Color.white);  //sets layout background color to white
        learnerTable.addMouseListener(new Learners.MyMouseListener());
       
        //facilitator list
       // try{ConnectDatabase database = new ConnectDatabase();listFaci = database.getListFacilitator(); String [] ccListFaciArray; ccListFaciArray = new String [listFaci.length];
        //    for(int i=0;i < listFaci.length;i++){ ccListFaciArray[i] = listFaci[i]; facilitatorDrop.addItem(ccListFaciArray[i]);}}
        //catch(Exception e1){ JOptionPane.showMessageDialog(null,e1,"ERROR IN FACILITATOR LIST",JOptionPane.ERROR_MESSAGE);}
        
        setFacilitatorMenu();
        
        setSize(837, 600); //setSize(835, 590) or setSize(714, 475); (horizontal, vertical)
        setVisible(true);
        setLocation(0, 0);
    }

    public class MyMouseListener extends MouseAdapter {
     public MyMouseListener() 
      {
      
      }
     
     public void mousePressed(MouseEvent e){
         if ((e.getClickCount() == 1) && (!e.isMetaDown()))
           {
             tableRowSelected = Learners.this.learnerTable.getSelectedRows();
        	 //SettingsGUI.this.learnerTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        	 
           }
       }
  } 
    
    private Learners() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *Get list of facilitators and insert into popup menu
     */
    public void setFacilitatorMenu(){
       
        int facil_size = listFaci.length;
        String facilitator = "";
        
        for(int x = 0;x<facil_size;x++){
            facilitator = listFaci[x];
            menu_item = new javax.swing.JMenuItem(facilitator);
            assignFacilitator.add(menu_item);
            menu_item.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    assignFacilitatorActionPerformed(e);
                }
            }); 
         }
            
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        table_popmenu = new javax.swing.JPopupMenu();
        edit = new javax.swing.JMenuItem();
        view = new javax.swing.JMenuItem();
        save = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        delete = new javax.swing.JMenu();
        learner_selected = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        assign = new javax.swing.JMenu();
        assignFacilitator = new javax.swing.JMenu();
        facilitator_popup = new javax.swing.JPopupMenu();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        input_first_name = new javax.swing.JTextField();
        input_last_name = new javax.swing.JTextField();
        input_company_id = new javax.swing.JTextField();
        input_email = new javax.swing.JTextField();
        drop_group = new javax.swing.JComboBox();
        drop_level = new javax.swing.JComboBox();
        facilitatorDrop = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        learnerTable = new javax.swing.JTable();
        file_dir = new javax.swing.JLabel();
        icon_error_label = new javax.swing.JLabel();
        error_label = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();
        addLearnerButton = new javax.swing.JButton();
        changeLevelButton = new javax.swing.JButton();
        deleteLearnerButton = new javax.swing.JButton();
        DisplayButton = new javax.swing.JButton();
        browse_file_button = new javax.swing.JButton();
        importButton = new javax.swing.JButton();

        edit.setText("Edit");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });
        table_popmenu.add(edit);

        view.setText("View");
        view.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewActionPerformed(evt);
            }
        });
        table_popmenu.add(view);

        save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        save.setText("Save");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        table_popmenu.add(save);
        table_popmenu.add(jSeparator1);

        delete.setText("Delete");

        learner_selected.setText("Selected learner(s)");
        learner_selected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                learner_selectedActionPerformed(evt);
            }
        });
        delete.add(learner_selected);

        table_popmenu.add(delete);
        table_popmenu.add(jSeparator2);

        assign.setText("Assign...");

        assignFacilitator.setText("Facilitator");
        assignFacilitator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignFacilitatorActionPerformed(evt);
            }
        });
        assign.add(assignFacilitator);

        table_popmenu.add(assign);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("You can add/delete/modify/import learner(s)");

        jLabel2.setText("First Name");

        jLabel3.setText("Last Name");

        jLabel4.setText("Company ID");

        jLabel5.setText("E-mail");

        jLabel6.setText("Group");

        jLabel7.setText("Level");

        jLabel8.setText("Facilitator");

        input_first_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_first_nameActionPerformed(evt);
            }
        });

        input_last_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_last_nameActionPerformed(evt);
            }
        });

        input_company_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_company_idActionPerformed(evt);
            }
        });

        input_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_emailActionPerformed(evt);
            }
        });

        drop_group.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Autotronics", "Mechatronics" }));

        drop_level.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "2", "3", "4" }));

        learnerTable.setAutoCreateRowSorter(true);
        learnerTable.setModel(new javax.swing.table.DefaultTableModel(data,header));
        learnerTable.setComponentPopupMenu(table_popmenu);
        learnerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                learnerTableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(learnerTable);

        file_dir.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        file_dir.setForeground(new java.awt.Color(102, 0, 153));

        error_label.setForeground(java.awt.Color.red);

        buttonPanel.setBackground(new java.awt.Color(255, 255, 255));
        buttonPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        addLearnerButton.setText("Add");
        addLearnerButton.setToolTipText("Add");
        addLearnerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLearnerButtonActionPerformed(evt);
            }
        });

        changeLevelButton.setText("Change Level");
        changeLevelButton.setToolTipText("Change Level");
        changeLevelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeLevelButtonActionPerformed(evt);
            }
        });

        deleteLearnerButton.setText("Delete");
        deleteLearnerButton.setToolTipText("Delete");
        deleteLearnerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteLearnerButtonActionPerformed(evt);
            }
        });

        DisplayButton.setText("Display");
        DisplayButton.setToolTipText("Display learner(s)");
        DisplayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DisplayButtonActionPerformed(evt);
            }
        });

        browse_file_button.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        browse_file_button.setForeground(new java.awt.Color(102, 0, 153));
        browse_file_button.setText("Browse");
        browse_file_button.setToolTipText("Browse for file");
        browse_file_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browse_file_buttonActionPerformed(evt);
            }
        });

        importButton.setText("Import");
        importButton.setToolTipText("import learner list from excel file (.xls)");
        importButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buttonPanelLayout.createSequentialGroup()
                        .addComponent(browse_file_button, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(importButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(buttonPanelLayout.createSequentialGroup()
                        .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addLearnerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(changeLevelButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(deleteLearnerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DisplayButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addLearnerButton)
                    .addComponent(deleteLearnerButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DisplayButton)
                    .addComponent(changeLevelButton))
                .addGap(9, 9, 9)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(browse_file_button)
                    .addComponent(importButton))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(262, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(icon_error_label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(error_label, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(facilitatorDrop, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(input_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(input_last_name, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(input_company_id, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(input_email, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(drop_group, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(drop_level, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(file_dir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(16, 16, 16))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(input_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(input_last_name, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(input_company_id, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(input_email, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(drop_group, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(drop_level, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(facilitatorDrop, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(file_dir, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(error_label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(icon_error_label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void browse_file_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browse_file_buttonActionPerformed
       file_browser();
    }//GEN-LAST:event_browse_file_buttonActionPerformed

    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed
        importLearners();
    }//GEN-LAST:event_importButtonActionPerformed

    private void learnerTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_learnerTableMousePressed
       /* if ((evt.getClickCount() == 2) && (!evt.isMetaDown()))
        {
            int row = Learners.this.learnerTable.getSelectedRow();
            String learnerID = (String)Learners.this.learnerTable.getValueAt(row, 0);
            String name      = (String)Learners.this.learnerTable.getValueAt(row, 1);
            String surname   = (String) Learners.this.learnerTable.getValueAt(row, 2);
            String Companyid = (String) Learners.this.learnerTable.getValueAt(row, 3);
            String group     = (String) Learners.this.learnerTable.getValueAt(row, 4);
            String level     = (String) Learners.this.learnerTable.getValueAt(row, 5);
            String e_mail    = (String) Learners.this.learnerTable.getValueAt(row, 6);
            //wrap learner details into an array
            String [] learnerDetails = {learnerID,name,surname,Companyid,group,level,e_mail};
            //int thisCompid  = Integer.parseInt(Companyid);

            //get image for learner
            ImageIcon icon = null;
            try{ icon = new ImageIcon(new java.net.URL("http://haf0gau01.w9/photos/"+Companyid+".jpg"));}
            catch (MalformedURLException ex){Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);}
            //Resize the image icon
            Image img = icon.getImage();
            BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img,0,45, 145, 145,null); // drawImage(image,x-coodinates,y-coordinates,width, length,obsever)
            ImageIcon newIcon = new ImageIcon(bi);

            PhotoFrame pf = new PhotoFrame(newIcon,id,learnerDetails);
            pf.setVisible(true);

        }*/
    }//GEN-LAST:event_learnerTableMousePressed

    private void changeLevelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeLevelButtonActionPerformed
       // Change level
        int [] learnerIDs = new int[tableRowSelected.length];

        for(int x = 0; x < tableRowSelected.length;x++)
        {
            ID = learnerTable.getValueAt(tableRowSelected[x], 0);
            int learnerID = Integer.valueOf((String) ID);
            learnerIDs[x] = learnerID;
            //System.out.println(learnerIDs[x]);
        }

        JFrame frame = new JFrame("Inventoy Store System v1.2 ~ Change Level");
        ChangeLevelGUI wGUI = new ChangeLevelGUI(id,learnerIDs);
        wGUI.setVisible(true);

    }//GEN-LAST:event_changeLevelButtonActionPerformed

    private void DisplayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DisplayButtonActionPerformed
        if ((drop_group.getSelectedItem().equals("")) || (drop_level.getSelectedItem().equals("")))
        {
            JOptionPane.showMessageDialog(null, "Please enter Group and Level of learners you want to be displayed", "Missing Input", 1);
        }
        else
        {
            String groupToDisplay = (String) drop_group.getSelectedItem();
            String condition;

            if ((groupToDisplay.equals("All")) && (drop_level.getSelectedItem().equals("All")))
            {
                condition = "learner_list.learner_id >= 1";
            }
            else
            {

                if (groupToDisplay.equals("All"))
                {
                    condition = "learner_list.level=" + drop_level.getSelectedItem() + " ";
                }
                else
                {

                    if (drop_level.getSelectedItem().equals("All"))
                    {
                        condition = "learner_list.group='" + groupToDisplay + "'";
                    }
                    else
                    {
                        condition = "learner_list.group='" + groupToDisplay + "' and learner_list.level=" + drop_level.getSelectedItem();
                    }
                }
            }
            try
            {
                ConnectDatabase database = new ConnectDatabase();
                data = database.setLearnerTable(condition);
                header = database.getTableHeader("learner_list");
                database.setCloseDatabase();
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(null,ex,"Database Connection Failed",1);
            }
 
            //learnerTable.setModel(new javax.swing.table.DefaultTableModel(data,header));
            setTableData(false);
            //learnerTable = new JTable(data, columnNames);
            //jScrollPane1 = new javax.swing.JScrollPane(learnerTable);

        }

    }//GEN-LAST:event_DisplayButtonActionPerformed

    private void deleteLearnerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteLearnerButtonActionPerformed
        if (input_company_id.getText().equals(""))
        {
            input_company_id.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));  //set company id to error
            icon_error_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_label.setText("Warning: Please type in Company ID of a learner you want to delete!");
        }
        else
        {
            input_company_id.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            icon_error_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
            error_label.setText("");

            int compid = Integer.parseInt(input_company_id.getText());

            try
            {

                con = null;
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                con = DriverManager.getConnection("jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb)};DBQ=//africa.bmw.corp/winfs/ZA-data/ZA-P/ZA-P-5/ZA-P-50/ZA-P-503/Share/1.2 Projects/2012/Tebogo Kekana/Databases/africa.bmw.corp_prod/paintedbody_store.mdb", "Admin", "paintedbody9");
                Statement s = con.createStatement();
                Statement s1 = con.createStatement();

                String query = "SELECT name, surname FROM learner_list WHERE company_id=" + compid;
                s.execute(query);
                rs = s.getResultSet();

                if (rs.next())
                {
                    do
                    {
                        name = rs.getString("name");
                        surname = rs.getString("surname");

                        int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + name + " " + surname, "Delete Confirmation", 0);

                        if (answer == 0)
                        {
                            String query2 = "DELETE * FROM learner_list WHERE company_id=" + compid;
                            s1.execute(query2);
                            JOptionPane.showMessageDialog(null, ""+name+" "+surname+" is succesfully deleted!", compid + "Delete Successfull", 3);
                        } else {
                            if (answer != 1)
                            continue;
                            s1.close();
                        }
                    }
                    while (
                        rs.next());
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "There is no leaner with a Company ID (" + compid + ") you have provided.", compid + " does not exist", 2);
                }
                s.close();
                con.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }
    }//GEN-LAST:event_deleteLearnerButtonActionPerformed

    private void addLearnerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLearnerButtonActionPerformed
          if(input_first_name.getText().equals("") && input_last_name.getText().equals("") && input_company_id.getText().equals("") && input_email.getText().equals("") )
        {
            input_first_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));  //missing first name
            input_last_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));  //missing last name
            input_company_id.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));  //missing company id
            input_email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));  //missing email
            icon_error_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_label.setText("Warning: Please fill in all required fields in the highlighted boxes!");
        }
        else if(input_first_name.getText().equals(""))
        {
            input_first_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1)); //set first name to error
            input_last_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            input_company_id.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            input_email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            icon_error_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_label.setText("Warning: Please type in Fisrt Name in the highlighted box!");
        }
        else if ((input_last_name.getText().equals("")))
        {
            input_last_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1)); //set last name to error
            input_first_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            input_company_id.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            input_email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            icon_error_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_label.setText("Warning: Please type in Last Name in the highlighted box!");
        }
        else if ((input_company_id.getText().equals("")))
        {
            input_company_id.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));  //set company id to error
            input_email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            input_last_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            input_first_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            icon_error_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_label.setText("Warning: Please type in Company ID in the highlighted box!");
        }
         else if ((input_email.getText().equals("")))
        {   
            input_email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));  //set company id to error
            input_company_id.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));  //set company id to error
            input_last_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            input_first_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            icon_error_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_label.setText("Warning: Please type in Company ID in the highlighted box!");
        }
        else if (input_first_name.getText().equals("") && input_last_name.getText().equals(""))
        {
            input_last_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            input_first_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            input_company_id.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            input_email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            icon_error_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_label.setText("Warning: Please fill in all required fields in the highlighted boxes!");
        }
        else if (input_first_name.getText().equals("") && input_company_id.getText().equals(""))
        {
            input_last_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            input_first_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            input_company_id.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            input_email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            icon_error_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_label.setText("Warning: Please fill in all required fields in the highlighted boxes!");
        }
        else if (input_last_name.getText().equals("") && input_company_id.getText().equals(""))
        {
            input_last_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            input_first_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            input_company_id.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255,0,0),1));
            input_email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            icon_error_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cancel.png")));
            error_label.setText("Warning: Please fill in all required fields in the highlighted boxes!");
        }
        else
        {
            input_company_id.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));  //set company id to error
            input_last_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            input_first_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            input_email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
            icon_error_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
            error_label.setText("");

            String fname = input_first_name.getText();
            String lname = input_last_name.getText();
            int compid = Integer.parseInt(input_company_id.getText());
            String group = (String)drop_group.getSelectedItem();
            String level = (String)drop_level.getSelectedItem();
            String email = input_email.getText();

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date currentdate = new Date();
            String logdate = formatter.format(currentdate);
            
            //get learner's facilitator's id
            int facilitator_id = 0;
            int learner_id = 0;
            String facilitator_name = facilitatorDrop.getSelectedItem().toString();
            
            try{
                ConnectDatabase database = new ConnectDatabase();
                learner_id = database.getID(compid);
            }
            catch(Exception e1){ System.out.println("WARNING - Learner Already Exists\n\n"+e1);}
            
            if(learner_id > 0)
            {
                JOptionPane.showMessageDialog(null, "Learner Already Exists", "WARNING", 2);
            }else{
                  try
                    {
                        ConnectDatabase database = new ConnectDatabase();
                        facilitator_id = database.getFacilitator(facilitator_name);

                        String batch = "INSERT INTO learner_list (`name`,`surname`,`company_id`,`group`,`level`,`e_mail`) VALUES ('" + fname + "','" + lname + "'," + compid + ",'" + group + "', '" + level + "','"+email+"')";
                        database.setBatch(batch);

                        int learnerID = database.getID(compid);
                        String update_learner_list_history_table = "INSERT INTO learner_list_history (`learner_id`,`name`,`surname`,`company_id`,`group`,`level`,`change_date`,`changedBy`)" +
                        "VALUES("+learnerID+",'"+fname+"','"+lname+"',"+compid+",'"+group+"',"+level+",'"+logdate+"',"+UserLog.id+")";

                        String update_learner_detail = "INSERT INTO learner_details (`learner_id`,`facilitator_id`) VALUES ("+compid+","+facilitator_id+")";
                        database.setBatch(update_learner_detail);

                        database.updateLearnerTable(update_learner_list_history_table);
                        database.setCloseDatabase();

                        JOptionPane.showMessageDialog(null, "<html>You have succesfully added " + fname + "</html>", "Confirmation", 1);
                    }
                    catch (Exception e1)
                    {
                        JOptionPane.showMessageDialog(null, e1, "ERROR - Contact Administrator", 2);
                    }
                
            }
            
        }
    }//GEN-LAST:event_addLearnerButtonActionPerformed

    public void importLearners(){
        
        if(fullPathName == null)  
        {   
            JOptionPane.showMessageDialog(null, "<html>Please select a file to import from your machine!</html>", "File Missing", 2);
        }
        else{   
            //declare local variables for inserting data to database from excel list
            String name = "";
            String surname = "";
            String company_id = "";
            String group = "";
            String level = "";
            String email = "";
            int numOfLearners = 0;
            String [] learnerDetails;

            //import data from excell file
            ExcelSheetReader excel = new ExcelSheetReader();
            excel.readExcelFile(fullPathName);
            List cellList = excel.cellDataList;
            //learnerDetails = new String[5];

            for(int i = 1; i < cellList.size(); i++)
            {
                List cellTempList = (List) cellList.get(i);
                learnerDetails = new String[cellTempList.size()];
                for (int j = 0; j < cellTempList.size(); j++)
                {
                    HSSFCell hssfCell = (HSSFCell) cellTempList.get(j);
                    String stringCellValue = hssfCell.toString();
                    if(j<6){
                      learnerDetails[j] = stringCellValue;}   
                }

                name = learnerDetails[0];
                surname = learnerDetails[1];
                company_id = learnerDetails[2];
                String splitCompid = company_id.split("\\.")[0]; 
                int coid = Integer.parseInt(splitCompid);
                group = learnerDetails[3];
                level = learnerDetails[4];
                email = learnerDetails[5];
                //convert first letter to uppeCase
                name = name.substring(0,1).toUpperCase()+""+name.substring(1).toLowerCase();
                surname = surname.substring(0,1).toUpperCase()+""+surname.substring(1).toLowerCase();
                group = group.substring(0,1).toUpperCase()+""+group.substring(1).toLowerCase();

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date currentdate = new Date();
                String logdate = formatter.format(currentdate);

                //insert into database
                try
                 {
                    ConnectDatabase database = new ConnectDatabase();
                    String batch = "INSERT INTO learner_list (`name`,`surname`,`company_id`,`group`,`level`,`e_mail`) VALUES ('" + name + "','" + surname + "'," + coid + ",'" + group + "', '" + level + "','"+email+"')";
                    database.setBatch(batch);
                    numOfLearners += 1;  
                    int learnerID = database.getID(coid);
                    String update_learner_list_history_table = "INSERT INTO learner_list_history (`learner_id`,`name`,`surname`,`company_id`,`group`,`level`,`change_date`,`changedBy`)" +
                    "VALUES("+learnerID+",'"+name+"','"+surname+"',"+coid+",'"+group+"',"+level+",'"+logdate+"',"+UserLog.id+")";

                    database.updateLearnerTable(update_learner_list_history_table);
                    database.setCloseDatabase();
                }
                catch (Exception e1)
                {
                    JOptionPane.showMessageDialog(null, e1, "ImportExcel Error - Contact Administrator", 2);
                }
               //System.out.println(name+" "+surname+" "+coid+" "+group+" "+level); 


                //initialize the array to be empty
                learnerDetails = new String[cellTempList.size()];
            }

            JOptionPane.showMessageDialog(null, "<html>You have succesfully Imported " +numOfLearners+ " learner(s)</html>", "Learners Imported Succefully", 1);
        }
    }

    public void file_browser(){
        // Browse for file, open dialog
        //Open dialog
        JFileChooser c = new JFileChooser();
        c.addChoosableFileFilter(new FileNameExtensionFilter("MS Excel Document(.xls)","xls"));
        c.setAcceptAllFileFilterUsed(true);
        int rVal = c.showOpenDialog(Learners.this);

        if (rVal == JFileChooser.APPROVE_OPTION)
        {
            File file      = c.getSelectedFile();
            file_name      = file.getName();
            //file_name      = c.getSelectedFile().toString();
            file_directory = c.getCurrentDirectory().toString();
            fullPathName   = file_directory+File.separator+file_name;
        }

        //browse_file_button.setText(file_name);
        file_dir.setText(fullPathName);

        if (rVal == JFileChooser.CANCEL_OPTION) {
            browse_file_button.setText("");
            file_dir.setText("");
        }
        
        
    }   
    
    private void input_company_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_company_idActionPerformed
        input_company_id.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
        icon_error_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
        error_label.setText("");
    }//GEN-LAST:event_input_company_idActionPerformed

    private void input_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_emailActionPerformed
        input_email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
        icon_error_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
        error_label.setText("");
    }//GEN-LAST:event_input_emailActionPerformed

    private void input_last_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_last_nameActionPerformed
        input_last_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
        icon_error_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
        error_label.setText("");
    }//GEN-LAST:event_input_last_nameActionPerformed

    private void input_first_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_first_nameActionPerformed
          input_first_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0),1));
        icon_error_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
        error_label.setText("");
    }//GEN-LAST:event_input_first_nameActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
       learnerTable.setBackground(Color.white);
       setTableData(true);
    }//GEN-LAST:event_editActionPerformed

    private void viewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewActionPerformed
            int row = Learners.this.learnerTable.getSelectedRow();
            String learnerID = (String)Learners.this.learnerTable.getValueAt(row, 0);
            String name      = (String)Learners.this.learnerTable.getValueAt(row, 1);
            String surname   = (String) Learners.this.learnerTable.getValueAt(row, 2);
            String Companyid = (String) Learners.this.learnerTable.getValueAt(row, 3);
            String group     = (String) Learners.this.learnerTable.getValueAt(row, 4);
            String level     = (String) Learners.this.learnerTable.getValueAt(row, 5);
            String e_mail    = (String) Learners.this.learnerTable.getValueAt(row, 6);
            //wrap learner details into an array
            String [] learnerDetails = {learnerID,name,surname,Companyid,group,level,e_mail};
            //int thisCompid  = Integer.parseInt(Companyid);

            //get image for learner
            ImageIcon icon = null;
            try{ icon = new ImageIcon(new java.net.URL("http://haf0gau01.w9/photos/"+Companyid+".jpg"));}
            catch (MalformedURLException ex){Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);}
            //Resize the image icon
            Image img = icon.getImage();
            BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img,0,45, 145, 145,null); // drawImage(image,x-coodinates,y-coordinates,width, length,obsever)
            ImageIcon newIcon = new ImageIcon(bi);

            PhotoFrame pf = new PhotoFrame(newIcon,id,learnerDetails);
            pf.setVisible(true);
    }//GEN-LAST:event_viewActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        int row = Learners.this.learnerTable.getSelectedRow();
         Object selectedID = Learners.this.learnerTable.getValueAt(row,0); 
         int id = Integer.parseInt((String) selectedID);
         int columnCount = learnerTable.getColumnCount();
         String columns[] = new String[columnCount];
         String data[] = new String[columnCount];
         String [] splitColumn;
         String [] splitColumn2;
         String column;
          String valueFromTable;
         String batch = "UPDATE learner_list SET "; 
          
         for(int x=0;x<columnCount;x++){
            column = learnerTable.getColumnName(x);  
            splitColumn = column.split("\\["); 
            splitColumn2 = splitColumn[1].split("\\]");
            columns[x] = splitColumn2[0]; //get hearders
            //get data
            try{
                valueFromTable = (Learners.this.learnerTable.getValueAt(row, x)).toString();
                data[x] = valueFromTable; 
            } catch(NullPointerException e){data[x] = "";}            
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
                 batch = batch + "`"+columns[x+1]+"` = "+data[x+1]; 
                 }else{
                     batch = batch + "`"+columns[x+1]+"` = "+data[x+1]+","; 
                 }
             }else{
                 if(x == indentifyer){
                 batch = batch + "`"+columns[x+1]+"` = '"+data[x+1]+"'";
                 }else{
                     batch = batch + "`"+columns[x+1]+"` = '"+data[x+1]+"',";
                 }
             }

         }
        
         batch = batch + " WHERE "+columns[0]+"="+id;
         
         try{
             ConnectDatabase database = new ConnectDatabase();
             database.setBatch(batch); 
             file_dir.setText("Record successfully update!"); 
             file_dir.setForeground(Color.GREEN);
         }catch(Exception ex){
              file_dir.setText("Failed to update record"); 
              file_dir.setForeground(Color.red);
              Logger.getLogger(Learners.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_saveActionPerformed

    private void learner_selectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_learner_selectedActionPerformed
        
        int selected_learners [] = learnerTable.getSelectedRows();
        int size = selected_learners.length;
        int records = 0;
        int coid = 0;  
        try{ 
            ConnectDatabase database = new ConnectDatabase();
            for(int x = 0;x < size;x++){
               coid = Integer.parseInt((String)learnerTable.getValueAt(selected_learners[x], 3));
               database.deleteLearner(coid);
               records +=1;
            }
             file_dir.setText(""+records+" record(s) deleted"); 
          }catch(Exception e){
              e.printStackTrace();
          }  
    }//GEN-LAST:event_learner_selectedActionPerformed

    private void assignFacilitatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignFacilitatorActionPerformed
       String facilitator = "";
       if(evt.getActionCommand().equals(" ") || evt.getActionCommand().isEmpty()){ 
           //System.out.println("No facilitator selected");
           file_dir.setText("No facilitator selected"); 
       }else{
           file_dir.setText("");
           facilitator = evt.getActionCommand();
           this.facilitator = facilitator;
           assignFacilitator(this.facilitator);
       }
    }//GEN-LAST:event_assignFacilitatorActionPerformed
    
    private void assignFacilitator(String facilitator_name){
        int selected_learners [] = learnerTable.getSelectedRows();
        int size = selected_learners.length;
        int records = 0;
        int coid = 0; 
        int faci_coid = 0;
        try{ 
            ConnectDatabase database = new ConnectDatabase();
            faci_coid = database.getFacilitator(facilitator_name); 
            for(int x = 0;x < size;x++){
               coid = Integer.parseInt((String)learnerTable.getValueAt(selected_learners[x], 3));
               database.assignFacilitatorToLearner(coid, faci_coid,false);
               records +=1;
            }
             file_dir.setText(""+records+" learner(s) successfully assigned to "+facilitator_name); 
          }catch(Exception e){
             e.printStackTrace();
             file_dir.setText("Failed to assign facilitator - "+facilitator_name+" to learner(s)");  
          }  
        
    }
    private void setTableData(boolean editable){
           int records = 0;
           if(editable){
            tmodel = new javax.swing.table.DefaultTableModel(data,header){
             @Override
             public boolean isCellEditable(int row, int column) {
             //all cells false
             return true;
              }
            };
            learnerTable.setModel(tmodel);
            records = tmodel.getRowCount();
            file_dir.setText(""+records+" record(s) returned"); 
            file_dir.setForeground(new Color(57,104,156)); 
            
           }else{
               tmodel = new javax.swing.table.DefaultTableModel(data,header){
             @Override
             public boolean isCellEditable(int row, int column) {
             //all cells false
             return false;
              }
            };
            learnerTable.setBackground(new Color(200,200,200)); 
            learnerTable.setModel(tmodel);  
            records = tmodel.getRowCount();
            file_dir.setText(""+records+" record(s) returned"); 
            file_dir.setForeground(new Color(57,104,156)); 
          }   
    }
    
    private static void uploadPicture(String imgDir,int idIn) throws FileNotFoundException{  
        /**try{ RandomAccessFile raf = new RandomAccessFile( new File(imgDir ), "rw" );}
        catch(FileNotFoundException e)
        {System.out.println(e);}*/
        File source      = new File(imgDir);
        File destination = new File("\\\\Africa.bmw.corp\\winfs\\ZA-data\\ZA-P\\ZA-P-5\\ZA-P-50\\ZA-P-503\\Share\\1.2 Projects\\2012\\Tebogo Kekana\\Images\\"+idIn+".jpg");
        
        try 
          {
           //FileUtils.copyDirectory(source, destination);
           FileUtils.copyFile(source, destination); 
          } 
        catch (IOException e) 
          {
            JOptionPane.showMessageDialog(null, "Sorry, Could not upload Picture\n"+e, "Picture Not Uploaded!", 2);   
            //e.printStackTrace();
          }
        
        
    }
    
    public String getTable(String store){
    String storeOut;
        switch (store) {
            case "Tool store":
                storeOut = "tool_list";
                break;
            case "Equipment store":
                storeOut = "equipment_list";
                break;
            case "Consumable store":
                storeOut = "consumables";
                break;
            default:
                storeOut = "";
                break;
        }
    
    return storeOut;
  } 
  
    public String getTableFields(String table) {
     String fields;
        switch (table) {
            case "data_store_01":
                fields = "Store,`Location / Bin`,Description,Current,Creator";
                break;
            case "tool_list":
                fields = "ID,Tool_description,Available,Quantity,Store";
                break;
            case "equipment_list":
                fields = "ID,Equipment_name,Available,Quantity,Store";
                break;
            case "consumables":
                fields = "ID,`Consumable Name`,Quantity,Available,Store";
                break;
            case "learner_list":
                fields = "name,surname,company_id,group,level";
                break;  
            case "issue_list_01":
                fields = "ID,Store,`Location / Bin`,Description,Quantity,Date,Learner,Company_id,Group,level,Facilitator,Storeman";
                break;  
            case "receive_list_01":
                fields = "ID,Store,`Location / Bin`,Description,Quantity,Date,Area,Company_ID,Facilitator,Storeman";
                break;      
            default:
                fields = "";
                break;
        }
    
    return fields;
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
            java.util.logging.Logger.getLogger(Learners.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Learners.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Learners.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Learners.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Learners().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DisplayButton;
    private javax.swing.JButton addLearnerButton;
    private javax.swing.JMenu assign;
    private javax.swing.JMenu assignFacilitator;
    private javax.swing.JButton browse_file_button;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton changeLevelButton;
    private javax.swing.JMenu delete;
    private javax.swing.JButton deleteLearnerButton;
    private javax.swing.JComboBox drop_group;
    private javax.swing.JComboBox drop_level;
    private javax.swing.JMenuItem edit;
    public static javax.swing.JLabel error_label;
    private javax.swing.JComboBox facilitatorDrop;
    private javax.swing.JPopupMenu facilitator_popup;
    private javax.swing.JLabel file_dir;
    public static javax.swing.JLabel icon_error_label;
    private javax.swing.JButton importButton;
    private javax.swing.JTextField input_company_id;
    private javax.swing.JTextField input_email;
    private javax.swing.JTextField input_first_name;
    private javax.swing.JTextField input_last_name;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTable learnerTable;
    private javax.swing.JMenuItem learner_selected;
    private javax.swing.JMenuItem save;
    private javax.swing.JPopupMenu table_popmenu;
    private javax.swing.JMenuItem view;
    // End of variables declaration//GEN-END:variables
}
