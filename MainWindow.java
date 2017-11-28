package inventory_system;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

 
/**
 * @author Tebogo kekana - kinotech (Pty) Ltd
 */
//public class MainWindow extends JApplet
public class MainWindow extends javax.swing.JFrame {
    protected static int id;
    protected static int role;
    Date logtime;
    public static String [] locationList;
    protected static String learner;
    private String uname, fname, lname;
    private String item_description;
    private static int compID;
    SimpleDateFormat sdfFrom;
    SimpleDateFormat sdfTo;
    public static final DateFormat df = new SimpleDateFormat("hh:mm:ss");    
    private static String date ;
    public static int msgCount;
    public static String noticeMsg;
    private static String IconToShow;
    private boolean ok = false;
    //Declaration for a custom dialog 
    private JOptionPane optionPane;
    private String btnString1;
    private String btnString2;
    private String btnString3;
    private Object value;
    //CustomDialog cd;
    public static String recipient,email,on_copy,fmessage,lmessage,subject,f_email;
    private String space5, space14,space41, space48,space59;
    private String dueDate;
    protected static String [] listFaci;
    protected static String [] listStoreman;
    protected static String [] listCourse;
    protected static int [] listLevels;
    private static boolean jobRunning = true;
    private static String screen = "";
    private static String previous_screen = "";
    private Vector<Vector<Object>> data;
    private Vector<String> header;
    private String store_name;
    //Items items;
    Receiving receiving;
    MainWindowWelcomeScreen mainwindowwelcomescreen;
    ConsumableGUI cs;
    EquipmentGUI1 es;
    ToolGUI ts;
    IssueListGUI issueList;
    ReceiveListGUI receiveList;
    Download download;
    DownloadTask dt;
    PasswordRequest pr;
    static Customers customer;
    static Items item;
    static Suppliers supplier;
    static Quotations quotations;
    static QuotationList quotationsList;
    CustomerList customerlist;
    ItemList itemlist;
    SupplierList supplierlist;
    private boolean sendmail;
    private static DefaultTableModel model;  
    public static boolean learner_notice_state;
    public String barcode;
         
   public MainWindow(int idin,int roleIn, Date logtimein) {
        this.sdfTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.sdfFrom = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.value = "";
        this.btnString1 = "Yes";
        this.btnString2 = "No";
        this.btnString3 = "Cancel";
        this.id      = idin;
        this.role    = roleIn;
        this.logtime = logtimein;     
        this.space5 = String.format("%2s", " ");
        this.space14 = String.format("%22s", " ");
        this.space41 = String.format("%12s", " ");
        this.space48 = String.format("%15s", " ");
        
        initComponents();
        //initInternalFrames();
        mainWindow2InternalFrame();
        this.getContentPane().setBackground(new java.awt.Color(226,223,214)); 
               
        /*******************************************************
        * scanner input listener
        ********************************************************/
        scan_input.getDocument().addDocumentListener(new DocumentListener(){
           
            public void changedUpdate(DocumentEvent arg0) {
                //System.out.println("change update"); 
            }

            public void insertUpdate(DocumentEvent arg0) {
               //System.out.println("insert update"); 
               readBarcode();                 
            }

            public void removeUpdate(DocumentEvent arg0) {
               // System.out.println("remove update"); 
            }
        });
        
        /********************************************************
        * check if new msg exist and change icon color          * 
        ********************************************************/
        try{
         DB db = new  DB(RunProgram.CONNECTION_MODE);
         ArrayList<Messages> messages = new ArrayList<>();
         messages = db.getMessageByTypeAndDays(1, 5); // (msgtype,days) this can be implemented for user prefferenses 
         msgCount = messages.size();

         if(msgCount > 0){label_icon_notice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/notice_true.png")));
                          label_icon_notice.setToolTipText("("+msgCount+") new notification(s).");}
         else{
             label_icon_notice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/notice_false.png"))); 
             label_icon_notice.setToolTipText("No new notification(s).");
         }
         }
        catch(Exception e1){
            //JOptionPane.showMessageDialog(null,e1,"NoticeBoard Error Connection!",JOptionPane.INFORMATION_MESSAGE);
            label_icon_notice.setToolTipText("Cannot retrieve notifications. Please check connection.");
            label_icon_notice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/notice_warning.png"))); 
            e1.printStackTrace();
        }
        /********************************************************************************
        *       END OF MSG CHECK AND ICON COLOUR CHANGE                                 *
        *********************************************************************************/
         
        //pop up search table header
        header = new Vector<String>();
        header.add("Item ID");
        header.add("Item Code");
        header.add("Item Description");
        header.add("State");
        header.add("Date Created");
        header.add("Date Modified");      
        header.add("End Date");      
        
        //Basket Count
        items_count_label.setText(""+TrolleyGUI.basketCount); 
        
        network_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/ethernet_footer2.png")));
        network_label.setToolTipText("Online");
        int image_id = UserProfile.getImageID();
        if(image_id <= 0){
          profile_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/user_profile2.png")));
        }else{
           ImageIcon image = null;
           try{
               DB db = new DB(RunProgram.CONNECTION_MODE);
               image = db.getImage(UserProfile.getImageID()); 
           }catch(Exception e1){  e1.printStackTrace(); } 
                  
           java.awt.Image img = image.getImage();
           BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
           Graphics g = bi.createGraphics();
           g.drawImage(img,0,0, 30, 40,null); 
           ImageIcon newIcon = new ImageIcon(bi);
           profile_label.setIcon(newIcon); // Image
           profile_label.setHorizontalAlignment(SwingConstants.CENTER);  
        }
        profile_label.setText(UserProfile.getFirstName());
        profile_label.setToolTipText("Logged in as "+UserProfile.getUsername());
        profile_label.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N*/
        progressBar.setVisible(false);
        open_file.setVisible(false);
        
        
        Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e){
        Calendar now = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        Date currentTime = new Date();
        String timeText = df.format(now.getTime()); 
        date_label.setText(timeText); //date_label.setText(formatter.format(currentTime)); 
        String month, day;
        month = ""+now.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        day   = ""+formatter.format(currentTime); 
        date_label.setToolTipText(day+" "+month);  
         }
        });
        timer.start();
        
        new PlatformTray();
    }
  
   public String [] getActiveWindowList() {
           
       String frameArrayList [] = {""};
       frameArrayList[0] = this.getFrames().toString();
              
       return frameArrayList;       
   }
   
   private void initInternalFrames(){
      item = new Items();
       
   }
   
   private MainWindow() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        transactions_menu = new javax.swing.JPopupMenu();
        customer_transaction_menu = new javax.swing.JMenu();
        customer_quotes = new javax.swing.JMenuItem();
        customer_invoices = new javax.swing.JMenuItem();
        supplier_transaction_menu = new javax.swing.JMenu();
        item_transaction_menu = new javax.swing.JMenu();
        sales_transaction_menu = new javax.swing.JMenu();
        location_menu = new javax.swing.JPopupMenu();
        cs_menu = new javax.swing.JMenuItem();
        es_menu = new javax.swing.JMenuItem();
        ts_menu = new javax.swing.JMenuItem();
        administration_menu = new javax.swing.JPopupMenu();
        administration_menu_item = new javax.swing.JMenuItem();
        customer_menu = new javax.swing.JPopupMenu();
        add_customer = new javax.swing.JMenuItem();
        customer_list = new javax.swing.JMenuItem();
        item_menu = new javax.swing.JPopupMenu();
        add_item = new javax.swing.JMenuItem();
        item_list = new javax.swing.JMenuItem();
        supplier_menu = new javax.swing.JPopupMenu();
        add_supplier = new javax.swing.JMenuItem();
        supplier_list = new javax.swing.JMenuItem();
        toolBar = new javax.swing.JToolBar();
        previousButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        saveButton = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        issueListButton = new javax.swing.JButton();
        receiveListButton = new javax.swing.JButton();
        exportButton = new javax.swing.JButton();
        horizontalFiller3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(400, 0));
        scan_panel = new javax.swing.JPanel();
        scan_input = new javax.swing.JTextField();
        horizontalFiller = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(200, 0), new java.awt.Dimension(400, 0));
        search_panel = new javax.swing.JPanel();
        search_textfield = new javax.swing.JTextField();
        search_icon_label = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        profile_label = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jLabel1 = new javax.swing.JLabel();
        items_count_label = new javax.swing.JButton();
        footerToolBar = new javax.swing.JToolBar();
        date_label = new javax.swing.JLabel();
        horizontalFiller1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(190, 0), new java.awt.Dimension(190, 0));
        icon_holder = new javax.swing.JPanel();
        download_lb = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        open_file = new javax.swing.JLabel();
        horizontalFiller2 = new javax.swing.Box.Filler(new java.awt.Dimension(100, 0), new java.awt.Dimension(150, 0), new java.awt.Dimension(150, 0));
        label_icon_notice = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        email_notice_label = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        network_label = new javax.swing.JLabel();
        menu_label = new javax.swing.JLabel();
        transactions_label = new javax.swing.JLabel();
        location_label = new javax.swing.JLabel();
        administration_label = new javax.swing.JLabel();
        customer_label = new javax.swing.JLabel();
        home_desktop = new javax.swing.JDesktopPane();
        item_label = new javax.swing.JLabel();
        supplier_label = new javax.swing.JLabel();
        MenuBar = new javax.swing.JMenuBar();
        file = new javax.swing.JMenu();
        menuItemLogout = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuItemExit = new javax.swing.JMenuItem();
        edit = new javax.swing.JMenu();
        clear = new javax.swing.JMenuItem();
        view = new javax.swing.JMenu();
        stores = new javax.swing.JMenu();
        Consumables = new javax.swing.JMenuItem();
        Equipments = new javax.swing.JMenuItem();
        Tools = new javax.swing.JMenuItem();
        administration = new javax.swing.JMenuItem();
        system = new javax.swing.JMenu();
        masterdata = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        document = new javax.swing.JMenu();
        print = new javax.swing.JMenuItem();
        send = new javax.swing.JMenuItem();
        save = new javax.swing.JMenu();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        settings = new javax.swing.JMenuItem();
        help = new javax.swing.JMenu();
        helpContents = new javax.swing.JMenuItem();
        usermanual = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        about = new javax.swing.JMenuItem();

        transactions_menu.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        transactions_menu.setPreferredSize(new java.awt.Dimension(150, 250));

        customer_transaction_menu.setForeground(new java.awt.Color(102, 102, 102));
        customer_transaction_menu.setText("Customer");
        customer_transaction_menu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        customer_transaction_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                customer_transaction_menuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                customer_transaction_menuMouseExited(evt);
            }
        });

        customer_quotes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        customer_quotes.setForeground(new java.awt.Color(102, 102, 102));
        customer_quotes.setText("Customer Quotes");
        customer_quotes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                customer_quotesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                customer_quotesMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                customer_quotesMousePressed(evt);
            }
        });
        customer_transaction_menu.add(customer_quotes);

        customer_invoices.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        customer_invoices.setForeground(new java.awt.Color(102, 102, 102));
        customer_invoices.setText("Customer Invoices");
        customer_invoices.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                customer_invoicesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                customer_invoicesMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                customer_invoicesMousePressed(evt);
            }
        });
        customer_transaction_menu.add(customer_invoices);

        transactions_menu.add(customer_transaction_menu);

        supplier_transaction_menu.setForeground(new java.awt.Color(102, 102, 102));
        supplier_transaction_menu.setText("Supplier");
        supplier_transaction_menu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        supplier_transaction_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                supplier_transaction_menuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                supplier_transaction_menuMouseExited(evt);
            }
        });
        transactions_menu.add(supplier_transaction_menu);

        item_transaction_menu.setForeground(new java.awt.Color(102, 102, 102));
        item_transaction_menu.setText("Item");
        item_transaction_menu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        item_transaction_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                item_transaction_menuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                item_transaction_menuMouseExited(evt);
            }
        });
        transactions_menu.add(item_transaction_menu);

        sales_transaction_menu.setForeground(new java.awt.Color(102, 102, 102));
        sales_transaction_menu.setText("Sales");
        sales_transaction_menu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sales_transaction_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sales_transaction_menuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sales_transaction_menuMouseExited(evt);
            }
        });
        transactions_menu.add(sales_transaction_menu);

        location_menu.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location_menu.setPreferredSize(new java.awt.Dimension(150, 250));

        cs_menu.setText("Consumable");
        cs_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cs_menuMousePressed(evt);
            }
        });
        location_menu.add(cs_menu);

        es_menu.setText("Equipment");
        es_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                es_menuMousePressed(evt);
            }
        });
        location_menu.add(es_menu);

        ts_menu.setText("Tool");
        ts_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ts_menuMousePressed(evt);
            }
        });
        location_menu.add(ts_menu);

        administration_menu.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        administration_menu.setPreferredSize(new java.awt.Dimension(150, 250));

        administration_menu_item.setText("Settings");
        administration_menu_item.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                administration_menu_itemMousePressed(evt);
            }
        });
        administration_menu.add(administration_menu_item);

        customer_menu.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        customer_menu.setPreferredSize(new java.awt.Dimension(150, 250));

        add_customer.setText("Add a Customer");
        add_customer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                add_customerMousePressed(evt);
            }
        });
        customer_menu.add(add_customer);

        customer_list.setText("List of Customers");
        customer_list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                customer_listMousePressed(evt);
            }
        });
        customer_menu.add(customer_list);

        item_menu.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        item_menu.setPreferredSize(new java.awt.Dimension(150, 250));

        add_item.setText("Add an Item");
        add_item.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                add_itemMousePressed(evt);
            }
        });
        item_menu.add(add_item);

        item_list.setText("List of Items");
        item_list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                item_listMousePressed(evt);
            }
        });
        item_menu.add(item_list);

        supplier_menu.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        supplier_menu.setPreferredSize(new java.awt.Dimension(150, 150));

        add_supplier.setText("Add a supplier");
        add_supplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                add_supplierMousePressed(evt);
            }
        });
        supplier_menu.add(add_supplier);

        supplier_list.setText("List of suppliers");
        supplier_list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                supplier_listMousePressed(evt);
            }
        });
        supplier_menu.add(supplier_list);

        setDefaultCloseOperation(closeProgram());
        setTitle("Inventory Store Program 1.2.1.131107_7 ~ Technical Training Center");
        setBackground(new java.awt.Color(226, 223, 214));
        setIconImage(new ImageIcon(getClass().getResource("/inventory_system/image/insp_icon_demo_39x45.png")).getImage());
        setResizable(false);

        toolBar.setBackground(new java.awt.Color(204, 204, 204));
        toolBar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        toolBar.setFloatable(false);
        toolBar.setOpaque(false);

        previousButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/previous.png"))); // NOI18N
        previousButton.setToolTipText("Previous");
        previousButton.setEnabled(false);
        previousButton.setFocusable(false);
        previousButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        previousButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/previous.png"))); // NOI18N
        previousButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        previousButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousButtonActionPerformed(evt);
            }
        });
        toolBar.add(previousButton);

        nextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/next.png"))); // NOI18N
        nextButton.setToolTipText("Next");
        nextButton.setEnabled(false);
        nextButton.setFocusable(false);
        nextButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nextButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/next.png"))); // NOI18N
        nextButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });
        toolBar.add(nextButton);

        jSeparator6.setPreferredSize(new java.awt.Dimension(10, 0));
        toolBar.add(jSeparator6);

        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/save1.png"))); // NOI18N
        saveButton.setToolTipText("Save");
        saveButton.setFocusable(false);
        saveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/save2.png"))); // NOI18N
        saveButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        toolBar.add(saveButton);

        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/multiple_receive.png"))); // NOI18N
        jButton17.setToolTipText("Multiple receiving");
        jButton17.setFocusable(false);
        jButton17.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton17.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/multiple_receive2.png"))); // NOI18N
        jButton17.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        toolBar.add(jButton17);

        jSeparator7.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator7.setSeparatorSize(new java.awt.Dimension(10, 0));
        toolBar.add(jSeparator7);

        issueListButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/issue_list.png"))); // NOI18N
        issueListButton.setToolTipText("View list of issued items");
        issueListButton.setFocusable(false);
        issueListButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        issueListButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/issue_list2.png"))); // NOI18N
        issueListButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        issueListButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issueListButtonActionPerformed(evt);
            }
        });
        toolBar.add(issueListButton);

        receiveListButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/receive_list.png"))); // NOI18N
        receiveListButton.setToolTipText("View list of received items");
        receiveListButton.setFocusable(false);
        receiveListButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        receiveListButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/receive_list2.png"))); // NOI18N
        receiveListButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        receiveListButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receiveListButtonActionPerformed(evt);
            }
        });
        toolBar.add(receiveListButton);

        exportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/exportExcel.png"))); // NOI18N
        exportButton.setToolTipText("Export to excel file");
        exportButton.setEnabled(false);
        exportButton.setFocusable(false);
        exportButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        exportButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });
        toolBar.add(exportButton);
        toolBar.add(horizontalFiller3);

        scan_panel.setBackground(new java.awt.Color(255, 255, 255));
        scan_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 223, 214), 3, true));
        scan_panel.setPreferredSize(new java.awt.Dimension(150, 47));
        scan_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                scan_panelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                scan_panelMouseExited(evt);
            }
        });

        scan_input.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        scan_input.setForeground(new java.awt.Color(153, 153, 153));
        scan_input.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scan_input.setBorder(null);

        javax.swing.GroupLayout scan_panelLayout = new javax.swing.GroupLayout(scan_panel);
        scan_panel.setLayout(scan_panelLayout);
        scan_panelLayout.setHorizontalGroup(
            scan_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scan_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scan_input, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                .addContainerGap())
        );
        scan_panelLayout.setVerticalGroup(
            scan_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scan_input, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
        );

        toolBar.add(scan_panel);
        toolBar.add(horizontalFiller);

        search_panel.setBackground(new java.awt.Color(255, 255, 255));
        search_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 223, 214), 3, true));
        search_panel.setPreferredSize(new java.awt.Dimension(156, 47));
        search_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                search_panelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                search_panelMouseExited(evt);
            }
        });

        search_textfield.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        search_textfield.setForeground(new java.awt.Color(153, 153, 153));
        search_textfield.setText("Search...");
        search_textfield.setBorder(null);
        search_textfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                search_textfieldFocusLost(evt);
            }
        });
        search_textfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search_textfieldMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                search_textfieldMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                search_textfieldMouseExited(evt);
            }
        });
        search_textfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_textfieldActionPerformed(evt);
            }
        });
        search_textfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                search_textfieldKeyTyped(evt);
            }
        });

        search_icon_label.setBackground(new java.awt.Color(255, 255, 255));
        search_icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/search2.png"))); // NOI18N
        search_icon_label.setOpaque(true);
        search_icon_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                search_icon_labelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                search_icon_labelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                search_icon_labelMousePressed(evt);
            }
        });

        javax.swing.GroupLayout search_panelLayout = new javax.swing.GroupLayout(search_panel);
        search_panel.setLayout(search_panelLayout);
        search_panelLayout.setHorizontalGroup(
            search_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(search_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(search_icon_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(search_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        search_panelLayout.setVerticalGroup(
            search_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(search_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(search_icon_label, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                .addGap(7, 7, 7))
            .addComponent(search_textfield)
        );

        toolBar.add(search_panel);

        jSeparator10.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator10.setSeparatorSize(new java.awt.Dimension(20, 0));
        toolBar.add(jSeparator10);

        profile_label.setBackground(new java.awt.Color(226, 223, 214));
        profile_label.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        profile_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profile_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/user_profile.png"))); // NOI18N
        profile_label.setToolTipText("User");
        profile_label.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        profile_label.setIconTextGap(10);
        profile_label.setOpaque(true);
        profile_label.setPreferredSize(new java.awt.Dimension(110, 40));
        profile_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                profile_labelMousePressed(evt);
            }
        });
        toolBar.add(profile_label);

        jSeparator5.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator5.setSeparatorSize(new java.awt.Dimension(10, 0));
        toolBar.add(jSeparator5);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/trolley.png"))); // NOI18N
        jLabel1.setLabelFor(items_count_label);
        jLabel1.setToolTipText("");
        jLabel1.setName(""); // NOI18N
        toolBar.add(jLabel1);

        items_count_label.setBackground(new java.awt.Color(204, 204, 204));
        items_count_label.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        items_count_label.setToolTipText("Shows the current items in your trolley");
        items_count_label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        items_count_label.setIconTextGap(0);
        items_count_label.setPreferredSize(new java.awt.Dimension(50, 24));
        items_count_label.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        items_count_label.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                items_count_labelActionPerformed(evt);
            }
        });
        toolBar.add(items_count_label);

        footerToolBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        footerToolBar.setFloatable(false);

        date_label.setForeground(new java.awt.Color(0, 51, 255));
        date_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        date_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/clock2_15x15.png"))); // NOI18N
        date_label.setMaximumSize(new java.awt.Dimension(15, 16));
        date_label.setMinimumSize(new java.awt.Dimension(15, 16));
        date_label.setPreferredSize(new java.awt.Dimension(136, 16));
        footerToolBar.add(date_label);
        footerToolBar.add(horizontalFiller1);

        icon_holder.setBackground(new java.awt.Color(226, 223, 214));
        icon_holder.setMaximumSize(new java.awt.Dimension(400, 32767));
        icon_holder.setPreferredSize(new java.awt.Dimension(400, 21));

        download_lb.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        download_lb.setMaximumSize(new java.awt.Dimension(200, 14));
        download_lb.setMinimumSize(new java.awt.Dimension(200, 14));

        progressBar.setMaximumSize(new java.awt.Dimension(600, 16));
        progressBar.setMinimumSize(new java.awt.Dimension(600, 16));
        progressBar.setPreferredSize(new java.awt.Dimension(50, 14));

        javax.swing.GroupLayout icon_holderLayout = new javax.swing.GroupLayout(icon_holder);
        icon_holder.setLayout(icon_holderLayout);
        icon_holderLayout.setHorizontalGroup(
            icon_holderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(icon_holderLayout.createSequentialGroup()
                .addComponent(download_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 199, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        icon_holderLayout.setVerticalGroup(
            icon_holderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
            .addComponent(download_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        footerToolBar.add(icon_holder);

        open_file.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        open_file.setForeground(new java.awt.Color(0, 0, 255));
        open_file.setEnabled(false);
        open_file.setMaximumSize(new java.awt.Dimension(47, 14));
        open_file.setMinimumSize(new java.awt.Dimension(47, 14));
        open_file.setPreferredSize(new java.awt.Dimension(47, 14));
        open_file.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                open_fileMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                open_fileMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                open_fileMouseExited(evt);
            }
        });
        footerToolBar.add(open_file);
        footerToolBar.add(horizontalFiller2);

        label_icon_notice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_icon_notice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/notice_false.png"))); // NOI18N
        label_icon_notice.setToolTipText("");
        label_icon_notice.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_icon_notice.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        label_icon_notice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_icon_noticeMouseClicked(evt);
            }
        });
        footerToolBar.add(label_icon_notice);

        jSeparator3.setMaximumSize(new java.awt.Dimension(10, 32767));
        footerToolBar.add(jSeparator3);

        email_notice_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        email_notice_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/email_notify.png"))); // NOI18N
        email_notice_label.setToolTipText("Notifies you when an item has been successfully issued or received.");
        email_notice_label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        footerToolBar.add(email_notice_label);

        jSeparator1.setMaximumSize(new java.awt.Dimension(10, 32767));
        footerToolBar.add(jSeparator1);

        network_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        network_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/ethernet_footer.png"))); // NOI18N
        network_label.setToolTipText("Network Status");
        network_label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        network_label.setOpaque(true);
        footerToolBar.add(network_label);

        menu_label.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        menu_label.setForeground(new java.awt.Color(102, 102, 102));
        menu_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menu_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/menu_27x24.png"))); // NOI18N
        menu_label.setText("Menu");
        menu_label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        menu_label.setIconTextGap(20);

        transactions_label.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        transactions_label.setForeground(new java.awt.Color(102, 102, 102));
        transactions_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        transactions_label.setText("Transactions");
        transactions_label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        transactions_label.setComponentPopupMenu(transactions_menu);
        transactions_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                transactions_labelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                transactions_labelMouseExited(evt);
            }
        });

        location_label.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        location_label.setForeground(new java.awt.Color(102, 102, 102));
        location_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location_label.setText("Location");
        location_label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location_label.setComponentPopupMenu(location_menu);
        location_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                location_labelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                location_labelMouseExited(evt);
            }
        });

        administration_label.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        administration_label.setForeground(new java.awt.Color(102, 102, 102));
        administration_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        administration_label.setText("Administration");
        administration_label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        administration_label.setComponentPopupMenu(administration_menu);
        administration_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                administration_labelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                administration_labelMouseExited(evt);
            }
        });

        customer_label.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        customer_label.setForeground(new java.awt.Color(102, 102, 102));
        customer_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        customer_label.setText("Customers");
        customer_label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        customer_label.setComponentPopupMenu(customer_menu);
        customer_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                customer_labelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                customer_labelMouseExited(evt);
            }
        });

        home_desktop.setBackground(new java.awt.Color(255, 255, 255));
        home_desktop.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createEtchedBorder()));

        javax.swing.GroupLayout home_desktopLayout = new javax.swing.GroupLayout(home_desktop);
        home_desktop.setLayout(home_desktopLayout);
        home_desktopLayout.setHorizontalGroup(
            home_desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 855, Short.MAX_VALUE)
        );
        home_desktopLayout.setVerticalGroup(
            home_desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 597, Short.MAX_VALUE)
        );

        item_label.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        item_label.setForeground(new java.awt.Color(102, 102, 102));
        item_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        item_label.setText("Items");
        item_label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        item_label.setComponentPopupMenu(customer_menu);
        item_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                item_labelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                item_labelMouseExited(evt);
            }
        });

        supplier_label.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        supplier_label.setForeground(new java.awt.Color(102, 102, 102));
        supplier_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        supplier_label.setText("Suppliers");
        supplier_label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        supplier_label.setComponentPopupMenu(supplier_menu);
        supplier_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                supplier_labelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                supplier_labelMouseExited(evt);
            }
        });

        file.setMnemonic(KeyEvent.VK_F);
        file.setText("File");

        menuItemLogout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        menuItemLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/log_out.png"))); // NOI18N
        menuItemLogout.setText("Log out");
        menuItemLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemLogoutActionPerformed(evt);
            }
        });
        file.add(menuItemLogout);
        file.add(jSeparator2);

        menuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        menuItemExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/exit.png"))); // NOI18N
        menuItemExit.setText("Exit");
        menuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemExitActionPerformed(evt);
            }
        });
        file.add(menuItemExit);

        MenuBar.add(file);

        edit.setMnemonic(KeyEvent.VK_E);
        edit.setText("Edit");

        clear.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        clear.setText("Clear");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });
        edit.add(clear);

        MenuBar.add(edit);

        view.setMnemonic(KeyEvent.VK_V);
        view.setText("View");

        stores.setText("Stores");

        Consumables.setText("Consumables");
        Consumables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsumablesActionPerformed(evt);
            }
        });
        stores.add(Consumables);

        Equipments.setText("Equipments");
        Equipments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EquipmentsActionPerformed(evt);
            }
        });
        stores.add(Equipments);

        Tools.setText("Tools");
        Tools.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ToolsActionPerformed(evt);
            }
        });
        stores.add(Tools);

        view.add(stores);

        administration.setText("Administration");
        administration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                administrationActionPerformed(evt);
            }
        });
        view.add(administration);

        MenuBar.add(view);

        system.setMnemonic(KeyEvent.VK_S);
        system.setText("System");

        masterdata.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        masterdata.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/mdata.png"))); // NOI18N
        masterdata.setText("Master Data");
        masterdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masterdataActionPerformed(evt);
            }
        });
        system.add(masterdata);
        system.add(jSeparator8);

        document.setText("Document");

        print.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        print.setText("Print");
        print.setEnabled(false);
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });
        document.add(print);

        send.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        send.setText("Send");
        send.setEnabled(false);
        document.add(send);

        save.setText("Save");
        save.setEnabled(false);
        document.add(save);

        system.add(document);
        system.add(jSeparator9);

        settings.setText("Settings");
        settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsActionPerformed(evt);
            }
        });
        system.add(settings);

        MenuBar.add(system);

        help.setMnemonic(KeyEvent.VK_H);
        help.setText("Help");

        helpContents.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/help.png"))); // NOI18N
        helpContents.setText("Help Contents");
        helpContents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpContentsActionPerformed(evt);
            }
        });
        help.add(helpContents);

        usermanual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/usermanual.png"))); // NOI18N
        usermanual.setText("Download User Manual");
        usermanual.setToolTipText("Download User manual");
        usermanual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usermanualActionPerformed(evt);
            }
        });
        help.add(usermanual);
        help.add(jSeparator4);

        about.setText("About");
        about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutActionPerformed(evt);
            }
        });
        help.add(about);

        MenuBar.add(help);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(footerToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menu_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(transactions_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(location_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(administration_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customer_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(item_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(supplier_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(home_desktop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(menu_label, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(transactions_label, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(location_label, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customer_label, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(item_label, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(supplier_label, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(administration_label, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(home_desktop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(footerToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
   
   private void readBarcode(){
       barcode = "";
       barcode = barcode + scan_input.getText();
       int barcode_length = barcode.length();
       if(barcode_length == 13 /*this can be any number defined during barcode setup*/){
        //call items screen          
        item.setRole(role);
        item.setUserId(id);
        //item.setBarcode(barcode);
        item.setItemByBarcode(barcode); 
        itemInternalFrame();
        previousButton.setEnabled(true); 
        nextButton.setEnabled(false); 
        saveButton.setEnabled(true); 
        exportButton.setEnabled(false); 
       }
   }
         
   private void mainWindow2InternalFrame(){
    Object frames [] = new Object[home_desktop.getAllFrames().length];
    try{
        screen = home_desktop.getSelectedFrame().getTitle();
    }catch(NullPointerException e){
        System.out.println("Opening new screen...");
        /*if(home_desktop.getAllFrames().length <= 0)
        frames = home_desktop.getAllFrames();*/
        screen = "MainWindowWelcomeScreen";
    }
    this.mainwindowwelcomescreen = new MainWindowWelcomeScreen();
    this.mainwindowwelcomescreen.setVisible(true);
    ((BasicInternalFrameUI)this.mainwindowwelcomescreen.getUI()).setNorthPane(null);
    this.home_desktop.add(this.mainwindowwelcomescreen);
    try {
      this.mainwindowwelcomescreen.setSelected(true);
    }
    catch (PropertyVetoException e) {
    }
    
  }
   
   protected void issueListInternalFrame(){
       this.issueList = new IssueListGUI(id,role);
       this.issueList.setVisible(true); 
       ((BasicInternalFrameUI)this.issueList.getUI()).setNorthPane(null); 
       this.home_desktop.add(this.issueList);
       try{
           this.issueList.setSelected(true); 
       }
       catch(PropertyVetoException e){e.printStackTrace();}
   }
   
   protected void  receiveListInternalFrame(){
     this.receiveList = new ReceiveListGUI(id,role);
       this.receiveList.setVisible(true); 
       ((BasicInternalFrameUI)this.receiveList.getUI()).setNorthPane(null); 
       this.home_desktop.add(this.receiveList);
       try{
           this.receiveList.setSelected(true); 
       }
       catch(PropertyVetoException e){e.printStackTrace();}
   }
   
   protected void receiveInternalFrame(){
    this.receiving = new Receiving();
    this.receiving.setVisible(true);
    ((BasicInternalFrameUI)this.receiving.getUI()).setNorthPane(null);
    this.home_desktop.add(this.receiving);
    try {
      this.receiving.setSelected(true);
    }
    catch (PropertyVetoException e) {e.printStackTrace();
    }
  } 
   
   protected void consumableInternalFrame(){
    cs = new ConsumableGUI(id,role);
    this.cs.setVisible(true);
    ((BasicInternalFrameUI)this.cs.getUI()).setNorthPane(null);
    this.home_desktop.add(this.cs);
    try {
      this.cs.setSelected(true);
    }
    catch (PropertyVetoException e) {e.printStackTrace();
    }
  } 
   
   protected static void customerInternalFrame(){
    
    customer = new Customers();
    customer.setVisible(true);
    customer.setSize(home_desktop.getWidth(), home_desktop.getHeight()); 
    ((BasicInternalFrameUI)customer.getUI()).setNorthPane(null);
    home_desktop.add(customer);
    try {
      customer.setSelected(true);
    }
    catch (PropertyVetoException e) {e.printStackTrace();
    }
    saveButton.setEnabled(true);
       
   }
   
   protected void customerListInternalFrame(){
    
    this.customerlist = new CustomerList();
    this.customerlist.setVisible(true);
    this.customerlist.setSize(home_desktop.getWidth(), home_desktop.getHeight()); 
    ((BasicInternalFrameUI)this.customerlist.getUI()).setNorthPane(null);
    this.home_desktop.add(this.customerlist);
    try {
      this.customerlist.setSelected(true);
    }
    catch (PropertyVetoException e) {e.printStackTrace();
    }
    
    saveButton.setEnabled(true);
   
   }
   
   protected static void customerQuotationsInternalFrame() {
     
     quotations = new Quotations();
     quotations.setVisible(true);
     quotations.setSize(home_desktop.getWidth(), home_desktop.getHeight());
     ((BasicInternalFrameUI)quotations.getUI()).setNorthPane(null);
     home_desktop.add(quotations);
    try {
      quotations.setSelected(true);
    }
    catch (PropertyVetoException e) {e.printStackTrace();
    }
     saveButton.setEnabled(true);
   }
   
   protected static void customerQuotationsListInternalFrame() {
     
     quotationsList = new QuotationList();
     quotationsList.setVisible(true);
     quotationsList.setSize(home_desktop.getWidth(), home_desktop.getHeight());
     ((BasicInternalFrameUI)quotationsList.getUI()).setNorthPane(null);
     home_desktop.add(quotationsList);
    try {
      quotationsList.setSelected(true);
    }
    catch (PropertyVetoException e) {e.printStackTrace();
    }
     saveButton.setEnabled(true);
   }
   
   protected static void customerInvoicesInternalFrame() {
       
   }
   
   protected void itemListInternalFrame(){
    
    this.itemlist = new ItemList();
    this.itemlist.setVisible(true);
    this.itemlist.setSize(home_desktop.getWidth(), home_desktop.getHeight()); 
    ((BasicInternalFrameUI)this.itemlist.getUI()).setNorthPane(null);
    home_desktop.add(this.itemlist);
    try {
      this.itemlist.setSelected(true);
    }
    catch (PropertyVetoException e) {e.printStackTrace();
    }
    saveButton.setEnabled(true);
       
   }
   
   protected static void itemInternalFrame(){
    item = new Items();
    item.setVisible(true);
    item.setSize(home_desktop.getWidth(), home_desktop.getHeight()); 
    ((BasicInternalFrameUI)item.getUI()).setNorthPane(null);
    home_desktop.add(item);
    try {
      item.setSelected(true);
    }
    catch (PropertyVetoException e) {e.printStackTrace();
    }
    saveButton.setEnabled(true);
       
   }
   
   protected static void supplierInternalFrame(){
     supplier = new Suppliers();
     supplier.setVisible(true);
     supplier.setSize(home_desktop.getWidth(), home_desktop.getHeight()); 
     ((BasicInternalFrameUI)supplier.getUI()).setNorthPane(null);
     home_desktop.add(supplier);
     try {
       supplier.setSelected(true);
     }
     catch (PropertyVetoException e) {e.printStackTrace();
     }
     saveButton.setEnabled(true);  
   }
   
   protected void supplierListInternalFrame(){
    
     this.supplierlist = new SupplierList();
     this.supplierlist.setVisible(true);
     this.supplierlist.setSize(home_desktop.getWidth(), home_desktop.getHeight()); 
     ((BasicInternalFrameUI)this.supplierlist.getUI()).setNorthPane(null);
     home_desktop.add(this.supplierlist);
     try {
       this.supplierlist.setSelected(true);
     }
     catch (PropertyVetoException e) {e.printStackTrace();
     }
     saveButton.setEnabled(true);
       
   }
   
   private static Scene createScene() {    
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
               
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        Text text   = new Text();
        
        text.setX(40);
        text.setY(100);
        //text.setFont(new Font(25));
        text.setText("Welcome JavaFX!");

        root.getChildren().add(text);

        return (scene);
    }
   
   protected void equipmentInternalFrame(){
    this.es = new EquipmentGUI1(id,role);
    this.es.setVisible(true);
    ((BasicInternalFrameUI)this.es.getUI()).setNorthPane(null);
    this.home_desktop.add(this.es);
    try {
      this.es.setSelected(true);
    }
    catch (PropertyVetoException e) {e.printStackTrace();
    }
  } 
   
   protected void toolInternalFrame(){
    this.ts = new ToolGUI(id,role);
    this.ts.setVisible(true);
    ((BasicInternalFrameUI)this.ts.getUI()).setNorthPane(null);
    this.home_desktop.add(this.ts);
    try {
      this.ts.setSelected(true);
    }
    catch (PropertyVetoException e) {
    }
  }
    
    public void actionPerformed(ActionEvent e) {/** This method handles events for the text field. */
        optionPane.setValue(btnString1);
    }
        
    public void propertyChange(PropertyChangeEvent e)    {
        String prop = e.getPropertyName();    
        
        if (isVisible() && (e.getSource() == optionPane) && (JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) 
        {
          value = optionPane.getValue();
        }
    }
    
    private void printReceipt(String msg) throws PrintException, InterruptedException, IOException    {
        FileInputStream textstream = null; 
        
        try
        { 
          textstream = new FileInputStream("my_id.pdf"); 
        } catch (FileNotFoundException ffne)
        { 
            ffne.printStackTrace();
        }
        
        if(textstream == null) 
        { 
                return; 
        } 
        // Set the document type
        DocFlavor myFormat = DocFlavor.INPUT_STREAM.PDF;
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        DocPrintJob job = service.createPrintJob(); 
        job.addPrintJobListener(new JobCompleteMonitor());
        
        Doc myDoc = new SimpleDoc(textstream, myFormat, null); 
        job.print(myDoc, null); 
        
        while(jobRunning)
        {
            Thread.sleep(1000); 
        }
        
        System.out.println("Done printing!");
        textstream.close();
        
    }
    
    private static class JobCompleteMonitor extends PrintJobAdapter     {
        @Override
        public void printJobCompleted(PrintJobEvent jobEvent) {
            System.out.println("Job completed");
         jobRunning = false;
		}
    }
    
    private void menuItemLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemLogoutActionPerformed
        //check if trolley is empty
        if(TrolleyGUI.basketCount == 0)
        {
            UserLog log = new UserLog(id,role,1,logtime);
            pr = new PasswordRequest();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            int width = dim.width/2-pr.getSize().width/2;
            int height = dim.height/2-pr.getSize().height/2;  
            pr.setLocation(width,height); //Positions the program at the center of any desktop screen
            pr.setVisible(true); 
            this.setVisible(false);
        }
        else{
            int answer = JOptionPane.showConfirmDialog(null, "<html>You have <b>"+TrolleyGUI.basketCount+"</b> item(s) in the Trolley.<br>Are you sure you want to logout?</html>", "Items Trolley not empty", 0);
            
            if(answer == 0)
            {
                UserLog log = new UserLog(id,role,1,logtime);
                pr = new PasswordRequest();
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                int width = dim.width/2-pr.getSize().width/2;
                int height = dim.height/2-pr.getSize().height/2;  
                pr.setLocation(width,height); //Positions the program at the center of any desktop screen
                pr.setVisible(true); 
                this.setVisible(false);
            }else
            {
                //do not logout (do nothing)
            }
        }
    }//GEN-LAST:event_menuItemLogoutActionPerformed

    protected int closeProgram(){
        int close = DO_NOTHING_ON_CLOSE;
        if(TrolleyGUI.basketCount == 0)
        {
            UserLog log = new UserLog(id,role,1,logtime);
            close = EXIT_ON_CLOSE;
            PlatformTray.exitTray(); 
            //new PasswordRequest().setVisible(true);
            //this.setVisible(false);
        }
        else{
            int answer = JOptionPane.showConfirmDialog(null, "<html>You have <b>"+TrolleyGUI.basketCount+"</b> item(s) in the Trolley.<br>Are you sure you want to logout?</html>", "Items Trolley not empty", 0);
            
            if(answer == 0)
            {
                UserLog log = new UserLog(id,role,1,logtime);
                close = EXIT_ON_CLOSE;
                PlatformTray.exitTray(); 
            }else 
            {
                
            }
        }
        
        return close;
    }
    
    private void menuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemExitActionPerformed
         //check if trolley is empty
        if(TrolleyGUI.basketCount == 0)
        {
            UserLog log = new UserLog(id,role,1,logtime);
            this.setVisible(false);
            PlatformTray.exitTray(); 
            System.exit(0);
        }
        else{
            int answer = JOptionPane.showConfirmDialog(null, "<html>You have <b>"+TrolleyGUI.basketCount+"</b> item(s) in the Trolley.<br>Are you sure you want to logout?</html>", "Items Trolley not empty", 0);
            
            if(answer == 0)
            {
                UserLog log = new UserLog(id,role,1,logtime);
                this.setVisible(false);
                PlatformTray.exitTray(); 
                System.exit(0); 
            }else
            {
                //do not logout (do nothing)
            }
        }
       
    }//GEN-LAST:event_menuItemExitActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
            
    }//GEN-LAST:event_clearActionPerformed
    
    private void exportTableToExcel(){
      boolean help = false;
      int count = 1;
            
      while (!help)
      {
        File file = new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath() + "\\results" + count + ".xls");
        try
        {
          FileWriter out = new FileWriter(file);
          for (int i = 0; i < model.getColumnCount(); i++)
          {
            out.write(model.getColumnName(i) + "\t");
          }
          out.write("\n");
          for (int i = 0; i < model.getRowCount(); i++) 
          {
            for (int j = 0; j < model.getColumnCount(); j++) 
            {
              out.write(model.getValueAt(i, j) + "\t");
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
    
    protected static void setActiveTable(JTable activeTable) {
       model = (DefaultTableModel) activeTable.getModel(); 
    }
    
    private void ConsumablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsumablesActionPerformed
         cs_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cstore2.png"))); 
        cs_menu.setFont(new java.awt.Font("SansSerif", 1, 11));
        cs_menu.setBackground(new java.awt.Color(255,204,0));
        cs_menu.setBorder(javax.swing.BorderFactory.createBevelBorder(1));
        //initialize other labels
        /**profile_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/ethernet.png"))); 
        profile_label.setFont(new java.awt.Font("SansSerif", 0, 11));
        profile_label.setBackground(new java.awt.Color(226,223,214));
        profile_label.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));*/
        supplier_transaction_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/receive_home.png")));
        supplier_transaction_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        supplier_transaction_menu.setBackground(new java.awt.Color(226,223,214));
        supplier_transaction_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        //customer_transaction_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/issue_home.png"))); 
        //customer_transaction_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        //customer_transaction_menu.setBackground(new java.awt.Color(226,223,214));
        //customer_transaction_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        administration_menu_item.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        administration_menu_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/config.png")));
        administration_menu_item.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        administration_menu_item.setBackground(new java.awt.Color(226,223,214));
        es_menu.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        es_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/estore.png")));
        es_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        es_menu.setBackground(new java.awt.Color(226,223,214));
        ts_menu.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        ts_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/tstore.png")));
        ts_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        ts_menu.setBackground(new java.awt.Color(226,223,214));
        
        consumableInternalFrame();
        exportButton.setEnabled(true);
        /**ConsumableGUI window = new ConsumableGUI(id,role);
        this.setVisible(false);
        window.setVisible(true);*/
    }//GEN-LAST:event_ConsumablesActionPerformed

    private void EquipmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EquipmentsActionPerformed
        es_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/estore2.png"))); 
        es_menu.setFont(new java.awt.Font("SansSerif", 1, 11));
        es_menu.setBackground(new java.awt.Color(255,204,0));
        es_menu.setBorder(javax.swing.BorderFactory.createBevelBorder(1));
        //initialize other labels
        /**profile_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/ethernet.png"))); 
        profile_label.setFont(new java.awt.Font("SansSerif", 0, 11));
        profile_label.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        profile_label.setBackground(new java.awt.Color(226,223,214));*/
        supplier_transaction_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/receive_home.png")));
        supplier_transaction_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        supplier_transaction_menu.setBackground(new java.awt.Color(226,223,214));
        supplier_transaction_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        //customer_transaction_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/issue_home.png"))); 
        //customer_transaction_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        //customer_transaction_menu.setBackground(new java.awt.Color(226,223,214));
        //customer_transaction_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        administration_menu_item.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        administration_menu_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/config.png")));
        administration_menu_item.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        administration_menu_item.setBackground(new java.awt.Color(226,223,214));
        cs_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cstore.png"))); 
        cs_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        cs_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        cs_menu.setBackground(new java.awt.Color(226,223,214));
        ts_menu.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        ts_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/tstore.png")));
        ts_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        ts_menu.setBackground(new java.awt.Color(226,223,214));
        
        equipmentInternalFrame();
        exportButton.setEnabled(true);
        /**EquipmentGUI1 window = new EquipmentGUI1(id,role);
        this.setVisible(false);
        window.setVisible(true);*/
    }//GEN-LAST:event_EquipmentsActionPerformed

    private void ToolsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ToolsActionPerformed
         ts_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/tstore2.png"))); 
        ts_menu.setFont(new java.awt.Font("SansSerif", 1, 11));
        ts_menu.setBackground(new java.awt.Color(255,204,0));
        ts_menu.setBorder(javax.swing.BorderFactory.createBevelBorder(1));
        es_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/estore.png"))); 
        es_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        es_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        es_menu.setBackground(new java.awt.Color(226,223,214));
        cs_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cstore.png"))); 
        cs_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        cs_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        cs_menu.setBackground(new java.awt.Color(226,223,214));
        /**profile_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/ethernet.png"))); 
        profile_label.setFont(new java.awt.Font("SansSerif", 0, 11));
        profile_label.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        profile_label.setBackground(new java.awt.Color(226,223,214));*/
        supplier_transaction_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/receive_home.png")));
        supplier_transaction_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        supplier_transaction_menu.setBackground(new java.awt.Color(226,223,214));
        supplier_transaction_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        //customer_transaction_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/issue_home.png"))); 
        //customer_transaction_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        //customer_transaction_menu.setBackground(new java.awt.Color(226,223,214));
        //customer_transaction_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        administration_menu_item.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        administration_menu_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/config.png")));
        administration_menu_item.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        administration_menu_item.setBackground(new java.awt.Color(226,223,214));
               

        toolInternalFrame(); 
        exportButton.setEnabled(true);
        /**ToolGUI window = new ToolGUI(id,role);
         this.setVisible(false);
         window.setVisible(true); */
    }//GEN-LAST:event_ToolsActionPerformed
       
    private void helpContentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpContentsActionPerformed
        /**Help help = new Help();
        help.setVisible(true);*/
        Help help = new Help();
        help.setIconImage("/inventory_system/image/insp_icon_demo_45x45.png");
                
        //new Help(); 
    }//GEN-LAST:event_helpContentsActionPerformed

    private void usermanualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usermanualActionPerformed
        // Download User manual
        progressBar.setVisible(true); 
        progressBar.setStringPainted(true); 
        dt = new DownloadTask(); 
        dt.addPropertyChangeListener(new PropertyChangeListener(){
          public void propertyChange(PropertyChangeEvent e){
                if("progress".equals(e.getPropertyName())){
                    int progress = (Integer)e.getNewValue();
                    progressBar.setValue(progress);
                }
            }
        });
        dt.execute();
               
    }//GEN-LAST:event_usermanualActionPerformed

    private void items_count_labelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_items_count_labelActionPerformed
        TheTrolley window = new TheTrolley(id,role,screen);
        window.setLocationByPlatform(true); 
        window.setVisible(true);
    }//GEN-LAST:event_items_count_labelActionPerformed

    private void previousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousButtonActionPerformed
 
       String active_frame = home_desktop.getSelectedFrame().getClass().getSimpleName();
       
       if(screen == active_frame){
           
       }else{
           switch (screen) {
                    case "Items":
                         itemInternalFrame();
                        break;
                    case "MainWindowWelcomeScreen":
                         mainWindow2InternalFrame();
                         previousButton.setEnabled(false); 
                         nextButton.setEnabled(true); 
                        break;
               }
           
            /*try {
              ClassPath cp = ClassPath.from(ClassLoader.getSystemClassLoader());
              ImmutableSet<ClassInfo> is = cp.getTopLevelClasses("inventory_system");
              Object classInfo[] = is.toArray();
              String classes[] = new String[classInfo.length];
               for(int x=0;x<classInfo.length;x++){
                  String split[] =  classInfo[x].toString().split("\\.");
                  classes[0] = split[1];
                  //System.out.println(classes[0]);
               }
    
             } catch (IOException ex) { Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex); }*/
       }

    }//GEN-LAST:event_previousButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
       String active_frame = home_desktop.getSelectedFrame().getClass().getSimpleName();
       
       if(screen == active_frame){
           
       }else{
           switch (screen) {
                    case "Items":
                        itemInternalFrame();
                        break;
                    case "MainWindowWelcomeScreen":
                        mainWindow2InternalFrame();
                        previousButton.setEnabled(false); 
                        nextButton.setEnabled(true); 
                        break;
           }
       }
    }//GEN-LAST:event_nextButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        String active_frame = home_desktop.getSelectedFrame().getClass().getSimpleName(); 
        switch (active_frame) {
                    case "Items":
                         item.saveItem();
                        break;
                    case "Customers":
                         customer.saveCustomer();
                         break;
                    case "Suppliers":
                         supplier.saveSupplier();
                         break;
                    case "Quotations":
                          quotations.saveQuotation();
                          break;
                    case "MainWindowWelcomeScreen":
                        
                        break;  
           }
        
        
    }//GEN-LAST:event_saveButtonActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        ReceiveGUI window = new ReceiveGUI(new JFrame(),id,role);
        window.setLocationByPlatform(true); 
        window.setSize(870,595);
        window.setVisible(true);
        Object answer = window.value;
        
        if(answer == "Cancel")
        {
            
        }
        else{
            
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void issueListButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issueListButtonActionPerformed
       issueListInternalFrame();
       previousButton.setEnabled(false);
       nextButton.setEnabled(false);
       exportButton.setEnabled(true); 
    }//GEN-LAST:event_issueListButtonActionPerformed

    private void receiveListButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receiveListButtonActionPerformed
       receiveListInternalFrame();
       previousButton.setEnabled(false);
       nextButton.setEnabled(false);
       exportButton.setEnabled(true); 
    }//GEN-LAST:event_receiveListButtonActionPerformed

    private void profile_labelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profile_labelMousePressed
        /**profile_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/ethernet2.png"))); // NOI18N
        profile_label.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        profile_label.setBackground(new java.awt.Color(51,153,255)); 
        profile_label.setBorder(javax.swing.BorderFactory.createBevelBorder(1));
        //initialize other labels
        receive_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/receive_home.png")));
        receive_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        receive_menu.setBackground(new java.awt.Color(226,223,214));
        receive_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        issue_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/issue_home.png"))); 
        issue_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        issue_menu.setBackground(new java.awt.Color(226,223,214));
        issue_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        administration_menu_item.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        administration_menu_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/config.png")));
        administration_menu_item.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        administration_menu_item.setBackground(new java.awt.Color(226,223,214));
        cs_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cstore.png"))); 
        cs_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        cs_menu.setBackground(new java.awt.Color(226,223,214));
        cs_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        es_menu.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        es_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/estore.png")));
        es_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        es_menu.setBackground(new java.awt.Color(226,223,214));
        ts_menu.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        ts_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/tstore.png")));
        ts_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        ts_menu.setBackground(new java.awt.Color(226,223,214));*/
    }//GEN-LAST:event_profile_labelMousePressed

    private void search_panelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search_panelMouseEntered
        search_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153,153,153), 3, true));
    }//GEN-LAST:event_search_panelMouseEntered

    private void search_panelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search_panelMouseExited
        search_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 223, 214), 3, true));
    }//GEN-LAST:event_search_panelMouseExited

    private void search_textfieldMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search_textfieldMouseEntered
        search_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153,153,153), 3, true));
    }//GEN-LAST:event_search_textfieldMouseEntered

    private void search_textfieldMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search_textfieldMouseExited
         search_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(226, 223, 214), 3, true));
    }//GEN-LAST:event_search_textfieldMouseExited

    private void search_textfieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_textfieldKeyTyped
        search_textfield.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        search_textfield.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_search_textfieldKeyTyped

    private void search_icon_labelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search_icon_labelMouseEntered
        search_icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/search.png"))); 
    }//GEN-LAST:event_search_icon_labelMouseEntered

    private void search_icon_labelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search_icon_labelMouseExited
        search_icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/search2.png"))); 
    }//GEN-LAST:event_search_icon_labelMouseExited

    private void search_icon_labelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search_icon_labelMousePressed
       search();   
    }//GEN-LAST:event_search_icon_labelMousePressed

    private void search_textfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_textfieldActionPerformed
        search();
    }//GEN-LAST:event_search_textfieldActionPerformed

    private void search_textfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search_textfieldMouseClicked
       search_textfield.setText(""); 
    }//GEN-LAST:event_search_textfieldMouseClicked

    private void aboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutActionPerformed
        Version v = new Version(new JFrame());
        v.setVisible(true);
        v.setLocationByPlatform(true);
        Object answer = v.value;
    }//GEN-LAST:event_aboutActionPerformed

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
       exportTableToExcel();
    }//GEN-LAST:event_exportButtonActionPerformed

    private void administrationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_administrationActionPerformed
        //Check if user authorised
        exportButton.setEnabled(false);

        if(role==1 || role==2)
        {
            //Administration window = new Administration(id,role);
            Admin window = new Admin(new JFrame(),id,role);
            //Add transparent background to parent(MainWindow)
            /**JComponent glassPane = new JPanel();
            glassPane.setBackground(new Color(240,20,20,100)); 
            this.setGlassPane(glassPane); 
            glassPane.setVisible(true);*/ 
            window.setSize(1012,730); //width height
            window.setLocationByPlatform(true);
            window.setVisible(true);
            //window.setLocationRelativeTo(this); 

            Object answer = window.value;

            if(answer == "Yes")
            {
                //glassPane.setVisible(false);
                //System.out.println(answer);
            }
            else{
                //glassPane.setVisible(false);
                //System.out.println(answer);
            }
            
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Access Denied!","Unauthorized Access!", 3);
        }
    }//GEN-LAST:event_administrationActionPerformed

    private void label_icon_noticeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_icon_noticeMouseClicked
        // display pop up notification message
        
        PopMessageList pop = new PopMessageList();
       // pop.setLocation(label_icon_notice.getLocation().x,label_icon_notice.getLocation().y);        
        Component c = evt.getComponent();
        pop.setLocationRelativeTo(c);
        pop.setVisible(true); 
        //transactions_menu.show(c, c.getSize().width , c.getSize().width / 2); 
        
    }//GEN-LAST:event_label_icon_noticeMouseClicked

    private void masterdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masterdataActionPerformed
        //Check if user authorised

        if(role==1)
        {
            MasterDataWindow window = new MasterDataWindow(new JFrame(),id,role); 
            //Add transparent background to parent(MainWindow)
            /**JComponent glassPane = new JPanel();
            glassPane.setBackground(new Color(240,20,20,100)); 
            this.setGlassPane(glassPane); 
            glassPane.setVisible(true);*/ 
            //window.setSize(1012,730); //width height
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            int width = dim.width/2-window.getSize().width/2;
            int height = dim.height/2-window.getSize().height/2;  
            window.setLocation(width,height); //Positions the program at the center of any desktop screen
            window.setVisible(true);
            //window.setLocationRelativeTo(this); 

            /*Object answer = window.value;

            if(answer == "Yes")
            {
                //glassPane.setVisible(false);
                //System.out.println(answer);
            }
            else{
                //glassPane.setVisible(false);
                //System.out.println(answer);
            }*/
            
        }
        else
        {
            JOptionPane.showMessageDialog(null,"<html>Access Denied!<br>Only <b><i>system administrators</i></b>allowed.</html>","Unauthorized Access!", 3);
        }
    }//GEN-LAST:event_masterdataActionPerformed

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
       print();
    }//GEN-LAST:event_printActionPerformed

    private void open_fileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_open_fileMouseClicked
        Desktop desktop = Desktop.getDesktop();
        try{
             File f = new File(""+download.libFile);
	     desktop.open(f);  // opens application (MSWord)
             download_lb.setText(""); 
             progressBar.setVisible(false); 
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
        
        open_file.setVisible(false); 
    }//GEN-LAST:event_open_fileMouseClicked

    private void open_fileMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_open_fileMouseEntered
        open_file.setForeground(Color.blue);
        open_file.setFont(new Font("SansSerif",Font.BOLD,10));
    }//GEN-LAST:event_open_fileMouseEntered

    private void open_fileMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_open_fileMouseExited
        open_file.setForeground(Color.blue);
        open_file.setFont(new Font("SansSerif",Font.PLAIN,10));
    }//GEN-LAST:event_open_fileMouseExited
  
    private void transactions_labelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transactions_labelMouseEntered
        transactions_label.setForeground(Color.white);
        Component c = evt.getComponent();
        transactions_menu.show(c, c.getSize().width , c.getSize().width / 2); 
    }//GEN-LAST:event_transactions_labelMouseEntered

    private void transactions_labelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transactions_labelMouseExited
       transactions_label.setForeground(new java.awt.Color(102,102,102));  
    }//GEN-LAST:event_transactions_labelMouseExited

    private void location_labelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location_labelMouseEntered
        location_label.setForeground(Color.white);
        Component c = evt.getComponent();
        location_menu.show(c, c.getSize().width , c.getSize().width / 2);
    }//GEN-LAST:event_location_labelMouseEntered

    private void location_labelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location_labelMouseExited
         location_label.setForeground(new java.awt.Color(102,102,102));  
    }//GEN-LAST:event_location_labelMouseExited

    private void cs_menuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cs_menuMousePressed
        cs_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cstore2.png"))); 
        cs_menu.setFont(new java.awt.Font("SansSerif", 1, 11));
        cs_menu.setBackground(new java.awt.Color(255,204,0));
        cs_menu.setBorder(javax.swing.BorderFactory.createBevelBorder(1));
        //initialize other labels
        /**profile_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/ethernet.png"))); 
        profile_label.setFont(new java.awt.Font("SansSerif", 0, 11));
        profile_label.setBackground(new java.awt.Color(226,223,214));
        profile_label.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));*/
        supplier_transaction_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/receive_home.png")));
        supplier_transaction_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        supplier_transaction_menu.setBackground(new java.awt.Color(226,223,214));
        supplier_transaction_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        //customer_transaction_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/issue_home.png"))); 
        //customer_transaction_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        //customer_transaction_menu.setBackground(new java.awt.Color(226,223,214));
        //customer_transaction_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        administration_menu_item.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        administration_menu_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/config.png")));
        administration_menu_item.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        administration_menu_item.setBackground(new java.awt.Color(226,223,214));
        es_menu.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        es_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/estore.png")));
        es_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        es_menu.setBackground(new java.awt.Color(226,223,214));
        ts_menu.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        ts_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/tstore.png")));
        ts_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        ts_menu.setBackground(new java.awt.Color(226,223,214));
        
        consumableInternalFrame();
        exportButton.setEnabled(true); 
        previousButton.setEnabled(true);
        nextButton.setEnabled(false);
        //exportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/exportExcel.png"))); 
        /**ConsumableGUI window = new ConsumableGUI(id,role);
        this.setVisible(false);
        window.setVisible(true);*/
    }//GEN-LAST:event_cs_menuMousePressed

    private void es_menuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_es_menuMousePressed
       es_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/estore2.png"))); 
        es_menu.setFont(new java.awt.Font("SansSerif", 1, 11));
        es_menu.setBackground(new java.awt.Color(255,204,0));
        es_menu.setBorder(javax.swing.BorderFactory.createBevelBorder(1));
        //initialize other labels
        /**profile_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/ethernet.png"))); 
        profile_label.setFont(new java.awt.Font("SansSerif", 0, 11));
        profile_label.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        profile_label.setBackground(new java.awt.Color(226,223,214));*/
        supplier_transaction_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/receive_home.png")));
        supplier_transaction_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        supplier_transaction_menu.setBackground(new java.awt.Color(226,223,214));
        supplier_transaction_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        //customer_transaction_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/issue_home.png"))); 
        //customer_transaction_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        //customer_transaction_menu.setBackground(new java.awt.Color(226,223,214));
        //customer_transaction_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        administration_menu_item.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        administration_menu_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/config.png")));
        administration_menu_item.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        administration_menu_item.setBackground(new java.awt.Color(226,223,214));
        cs_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cstore.png"))); 
        cs_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        cs_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        cs_menu.setBackground(new java.awt.Color(226,223,214));
        ts_menu.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        ts_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/tstore.png")));
        ts_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        ts_menu.setBackground(new java.awt.Color(226,223,214));
        
        equipmentInternalFrame();
        exportButton.setEnabled(true); 
        previousButton.setEnabled(true); 
        nextButton.setEnabled(false);
        //exportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/exportExcel.png"))); 
        /**EquipmentGUI1 window = new EquipmentGUI1(id,role);
        this.setVisible(false);
        window.setVisible(true);*/
    }//GEN-LAST:event_es_menuMousePressed

    private void ts_menuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ts_menuMousePressed
       ts_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/tstore2.png"))); 
        ts_menu.setFont(new java.awt.Font("SansSerif", 1, 11));
        ts_menu.setBackground(new java.awt.Color(255,204,0));
        ts_menu.setBorder(javax.swing.BorderFactory.createBevelBorder(1));
        es_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/estore.png"))); 
        es_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        es_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        es_menu.setBackground(new java.awt.Color(226,223,214));
        cs_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cstore.png"))); 
        cs_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        cs_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        cs_menu.setBackground(new java.awt.Color(226,223,214));
        /**profile_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/ethernet.png"))); 
        profile_label.setFont(new java.awt.Font("SansSerif", 0, 11));
        profile_label.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        profile_label.setBackground(new java.awt.Color(226,223,214));*/
        supplier_transaction_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/receive_home.png")));
        supplier_transaction_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        supplier_transaction_menu.setBackground(new java.awt.Color(226,223,214));
        supplier_transaction_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        //customer_transaction_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/issue_home.png"))); 
        //customer_transaction_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        //customer_transaction_menu.setBackground(new java.awt.Color(226,223,214));
        //customer_transaction_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        administration_menu_item.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        administration_menu_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/config.png")));
        administration_menu_item.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        administration_menu_item.setBackground(new java.awt.Color(226,223,214));
               

        toolInternalFrame();
        exportButton.setEnabled(true); 
        previousButton.setEnabled(true); 
        nextButton.setEnabled(false);
        //exportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/exportExcel.png"))); 
        /**ToolGUI window = new ToolGUI(id,role);
        this.setVisible(false);
        window.setVisible(true);*/
    }//GEN-LAST:event_ts_menuMousePressed

    private void administration_menu_itemMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_administration_menu_itemMousePressed
          //administration_menu_item.setBackground(new java.awt.Color(255, 51, 51)); 
        exportButton.setEnabled(false);
        nextButton.setEnabled(false);
        previousButton.setEnabled(false);
        administration_menu_item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/config2.png"))); // NOI18N
        administration_menu_item.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        administration_menu_item.setBackground(new java.awt.Color(255,51,51)); 
        administration_menu_item.setBorder(javax.swing.BorderFactory.createBevelBorder(1));
        //initialize other labels
        cs_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/cstore.png"))); 
        cs_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        cs_menu.setBackground(new java.awt.Color(226,223,214));
        cs_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        /**profile_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/ethernet.png"))); 
        profile_label.setFont(new java.awt.Font("SansSerif", 0, 11));
        profile_label.setBackground(new java.awt.Color(226,223,214));
        profile_label.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));*/
        supplier_transaction_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/receive_home.png")));
        supplier_transaction_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        supplier_transaction_menu.setBackground(new java.awt.Color(226,223,214));
        supplier_transaction_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        //customer_transaction_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/issue_home.png"))); 
        //customer_transaction_menu.setFont(new java.awt.Font("SansSerif", 0, 11));
        //customer_transaction_menu.setBackground(new java.awt.Color(226,223,214));
        //customer_transaction_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        es_menu.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        es_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/estore.png")));
        es_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        es_menu.setBackground(new java.awt.Color(226,223,214));
        ts_menu.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        ts_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/tstore.png")));
        ts_menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        ts_menu.setBackground(new java.awt.Color(226,223,214));
        
            //Check if user authorised

        if(role == 1 || role == 2)
        {
            //Administration window = new Administration(id,role);
            Admin window = new Admin(new JFrame(),id,role);
            //Add transparent background to parent(MainWindow)
            /**JComponent glassPane = new JPanel();
            glassPane.setBackground(new Color(240,20,20,100)); 
            this.setGlassPane(glassPane); 
            glassPane.setVisible(true);*/ 
            window.setSize(1012,730); //width height
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            int width = dim.width/2-window.getSize().width/2;
            int height = dim.height/2-window.getSize().height/2;  
            window.setLocation(width,height); //Positions the program at the center of any desktop screen
            window.setVisible(true);
            //window.setLocationRelativeTo(this); 

            Object answer = window.value;

            if(answer == "Yes")
            {
                //glassPane.setVisible(false);
                //System.out.println(answer);
            }
            else{
                //glassPane.setVisible(false);
                //System.out.println(answer);
            }
            
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Access Denied!","Unauthorized Access!", 3);
        }
        
    }//GEN-LAST:event_administration_menu_itemMousePressed

    private void administration_labelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_administration_labelMouseExited
        administration_label.setForeground(new java.awt.Color(102,102,102));
    }//GEN-LAST:event_administration_labelMouseExited

    private void administration_labelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_administration_labelMouseEntered
        administration_label.setForeground(Color.white);
        Component c = evt.getComponent();
        administration_menu.show(c, c.getSize().width , c.getSize().width / 2);
    }//GEN-LAST:event_administration_labelMouseEntered

    private void scan_panelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scan_panelMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_scan_panelMouseEntered

    private void scan_panelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scan_panelMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_scan_panelMouseExited

    private void settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsActionPerformed
        
        
        
    }//GEN-LAST:event_settingsActionPerformed

    private void search_textfieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_search_textfieldFocusLost
        search_textfield.setText("Search..."); 
    }//GEN-LAST:event_search_textfieldFocusLost

    private void customer_labelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customer_labelMouseEntered
        customer_label.setForeground(Color.white);
        Component c = evt.getComponent();
        customer_menu.show(c, c.getSize().width , c.getSize().width / 2);
    }//GEN-LAST:event_customer_labelMouseEntered

    private void customer_labelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customer_labelMouseExited
        customer_label.setForeground(new java.awt.Color(102,102,102));
    }//GEN-LAST:event_customer_labelMouseExited

    private void add_customerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_customerMousePressed
        customerInternalFrame(); 
        Customers.customer_title.setText("New Customer"); 
        Customers.setMode(0); 
        Customers.setNewCustomerCode(); 
    }//GEN-LAST:event_add_customerMousePressed

    private void customer_listMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customer_listMousePressed
       customerListInternalFrame();
        //setActiveTable(activeTable); 
    }//GEN-LAST:event_customer_listMousePressed

    private void item_labelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_item_labelMouseEntered
       item_label.setForeground(Color.white);
       Component c = evt.getComponent();
       item_menu.show(c, c.getSize().width , c.getSize().width / 2);
    }//GEN-LAST:event_item_labelMouseEntered

    private void item_labelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_item_labelMouseExited
        item_label.setForeground(new java.awt.Color(102,102,102));
    }//GEN-LAST:event_item_labelMouseExited

    private void item_listMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_item_listMousePressed
       itemListInternalFrame();     
    }//GEN-LAST:event_item_listMousePressed

    private void add_itemMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_itemMousePressed
        itemInternalFrame(); //#edit
        Items.item_title.setText("New Item"); 
        Items.setMode(0); 
        Items.setNewItemCode(); 
    }//GEN-LAST:event_add_itemMousePressed

    private void supplier_labelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_supplier_labelMouseEntered
       supplier_label.setForeground(Color.white);
       Component c = evt.getComponent();
       supplier_menu.show(c, c.getSize().width , c.getSize().width / 2);
    }//GEN-LAST:event_supplier_labelMouseEntered

    private void supplier_labelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_supplier_labelMouseExited
         supplier_label.setForeground(new java.awt.Color(102,102,102));
    }//GEN-LAST:event_supplier_labelMouseExited

    private void add_supplierMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_supplierMousePressed
       supplierInternalFrame(); 
       Suppliers.supplier_title.setText("New Supplier");   
    }//GEN-LAST:event_add_supplierMousePressed

    private void supplier_listMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_supplier_listMousePressed
        supplierListInternalFrame(); 
    }//GEN-LAST:event_supplier_listMousePressed

    private void customer_quotesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customer_quotesMousePressed
       customerQuotationsListInternalFrame();
       quotationsList.quote_title.setText("Customer Quotes");
       quotationsList.setQuotationList();
    }//GEN-LAST:event_customer_quotesMousePressed

    private void customer_invoicesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customer_invoicesMousePressed
       customerInvoicesInternalFrame();
    }//GEN-LAST:event_customer_invoicesMousePressed

    private void customer_transaction_menuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customer_transaction_menuMouseEntered
        customer_transaction_menu.setForeground(Color.white);
    }//GEN-LAST:event_customer_transaction_menuMouseEntered

    private void customer_transaction_menuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customer_transaction_menuMouseExited
        customer_transaction_menu.setForeground(new java.awt.Color(102,102,102));  
    }//GEN-LAST:event_customer_transaction_menuMouseExited

    private void supplier_transaction_menuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_supplier_transaction_menuMouseEntered
        supplier_transaction_menu.setForeground(Color.white);
    }//GEN-LAST:event_supplier_transaction_menuMouseEntered

    private void supplier_transaction_menuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_supplier_transaction_menuMouseExited
        supplier_transaction_menu.setForeground(new java.awt.Color(102,102,102));  
    }//GEN-LAST:event_supplier_transaction_menuMouseExited

    private void item_transaction_menuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_item_transaction_menuMouseEntered
       item_transaction_menu.setForeground(Color.white); 
    }//GEN-LAST:event_item_transaction_menuMouseEntered

    private void item_transaction_menuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_item_transaction_menuMouseExited
       item_transaction_menu.setForeground(new java.awt.Color(102,102,102));  
    }//GEN-LAST:event_item_transaction_menuMouseExited

    private void sales_transaction_menuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sales_transaction_menuMouseEntered
        sales_transaction_menu.setForeground(Color.white); 
    }//GEN-LAST:event_sales_transaction_menuMouseEntered

    private void sales_transaction_menuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sales_transaction_menuMouseExited
        sales_transaction_menu.setForeground(new java.awt.Color(102,102,102)); 
    }//GEN-LAST:event_sales_transaction_menuMouseExited

    private void customer_quotesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customer_quotesMouseEntered
       customer_quotes.setForeground(Color.white); 
    }//GEN-LAST:event_customer_quotesMouseEntered

    private void customer_quotesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customer_quotesMouseExited
       customer_quotes.setForeground(new java.awt.Color(102,102,102)); 
    }//GEN-LAST:event_customer_quotesMouseExited

    private void customer_invoicesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customer_invoicesMouseEntered
        customer_invoices.setForeground(Color.white); 
    }//GEN-LAST:event_customer_invoicesMouseEntered

    private void customer_invoicesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customer_invoicesMouseExited
       customer_invoices.setForeground(new java.awt.Color(102,102,102)); 
    }//GEN-LAST:event_customer_invoicesMouseExited
    
    private void print(){ 
    }
    
    private void search(){
           
        /**SearchGUI window = new SearchGUI(id,role);
        this.setVisible(false);
        window.setVisible(true);*/
  
      String textfield_description_item = search_textfield.getText();
      String condition = "";

      try
      {
        DB db = new DB(RunProgram.CONNECTION_MODE); 
        
        if (!textfield_description_item.equals(""))
        {
          if (!condition.equals("")) {
            condition = condition + " AND";
          }
          condition = condition + " itemdescription like '%" + textfield_description_item + "%'";
        }
        
        data = db.get_table_search_results("item", condition); 
        db.close();
      }
      catch (Exception e_exception)
      {
        e_exception.printStackTrace();
      }   
      
      final SearchPopUpPanel sp = new SearchPopUpPanel();
      sp.setTableData(data, header);
      int x, y;
      x = search_textfield.getLocationOnScreen().x;
      y = search_textfield.getLocationOnScreen().y + (search_textfield.getHeight());
      System.out.println("Width: "+x+" Height: "+y);
      sp.setLocation(new Point(x,y)); 
      sp.setVisible(true);
      
      
      if(sp != null)
        {    
            itemInternalFrame();
                
            Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener()
            {
               @Override
                public void eventDispatched(AWTEvent event) {

                    if(event instanceof MouseEvent)
                    {
                        MouseEvent m = (MouseEvent)event;
                        if(m.getID() == MouseEvent.MOUSE_CLICKED)
                        {
                            sp.setVisible(false);
                            //set checkdata
                            setCheckData(SearchPopUpPanel.locationdata,SearchPopUpPanel.descriptiondata,SearchPopUpPanel.currentdata );
                            Toolkit.getDefaultToolkit().removeAWTEventListener(this);
                        }
                    }
                    if(event instanceof WindowEvent)
                    {
                        WindowEvent we = (WindowEvent)event;
                        if(we.getID() == WindowEvent.WINDOW_DEACTIVATED || we.getID() == WindowEvent.WINDOW_STATE_CHANGED)
                        {
                            sp.setVisible(true);
                            //set checkdata
                            setCheckData(SearchPopUpPanel.locationdata,SearchPopUpPanel.descriptiondata,SearchPopUpPanel.currentdata );
                            Toolkit.getDefaultToolkit().removeAWTEventListener(this);
                        }
                    }
                }

            }, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.WINDOW_EVENT_MASK);

        }

      sp.setVisible(true);
    }
    
    public static void setCheckData(String loc, String descrip, String cur)  {
        Issue.description.setText(descrip);
        Issue.avail.setText(cur);
        Issue.locations.setSelectedItem(loc);
  }
    
    public void setItemImage(int idIn) {
         //get image url
        /** File file     = new File("\\\\africa.bmw.corp\\winfs\\ZA-data\\ZA-P\\ZA-P-5\\ZA-P-50\\ZA-P-503\\Share\\1.2 Projects\\2012\\Tebogo Kekana\\Images\\"+idIn+".jpg");
         String filePath = file.toString();
         URL image_url = null;
            
         try
         {
           //image_url = new File("\\\\africa.bmw.corp\\winfs\\ZA-data\\ZA-P\\ZA-P-5\\ZA-P-50\\ZA-P-503\\Share\\1.2 Projects\\2012\\Tebogo Kekana\\Images\\"+idIn+".jpg").toURI().toURL(); 
           image_url = new File(filePath).toURI().toURL(); 
         }
         catch(MalformedURLException ex)
         {
           //Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,ex,"Invalid Path!", 3);  
            
         }
           
         //get Icon
         ImageIcon icon = null;  
         try 
          {
            icon = new ImageIcon(new java.net.URL(""+image_url));
          }
         catch (MalformedURLException ex)
          {
            //Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,ex,"Invalid Path!", 3);    
          }
           
           if(!file.exists())  //if image doesnt exists then display no photo image
           {
              item_image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/0.jpg"))); 
           }
           else
           {   
             //Resize the image icon
             Image img = icon.getImage();
             BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
             Graphics g = bi.createGraphics();
             g.drawImage(img,0,0, 75, 65,null);
             ImageIcon newIcon = new ImageIcon(bi);
             item_image.setIcon(newIcon); // Image
             item_image.setHorizontalAlignment(SwingConstants.CENTER);
           } */
  }  
    
    private int saveChanges(){
        int value = 0;
        final int DO_NOTHING = 0;
        final int EXIT_ON_CLOSE = 3;
        int items = TrolleyGUI.basketCount; 
        /**
        //check if there are items in trolley
        if(items == 0)
        {
           UserLog log = new UserLog(id,role,1,logtime); 
           value = EXIT_ON_CLOSE; 
        }
        else{
            int answer = JOptionPane.showConfirmDialog(null, "<html>You have <b>"+items+"</b> in Trolley. Are you sure you want to exit? </html>", "Items in Trolley", 0, 1);
            
            if(answer == 1)
            {
                value = EXIT_ON_CLOSE;
            }
            else{ 
                value = DO_NOTHING;
            }
        }
        */
        return value;
    }
    
    public static boolean issue_notify_true(){
        return true;
    }
    
    public static boolean issue_notify_false(){
        return false;
    }
    
    public static boolean receive_notify_true(){
        return true;
    }
    
    public static boolean receive_notify_false(){
        return false;
    }
    
    /**
     * @param args the command line arguments
     */
    public void main(String args[]) {
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
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Consumables;
    private javax.swing.JMenuItem Equipments;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenuItem Tools;
    private javax.swing.JMenuItem about;
    private javax.swing.JMenuItem add_customer;
    private javax.swing.JMenuItem add_item;
    private javax.swing.JMenuItem add_supplier;
    private javax.swing.JMenuItem administration;
    private javax.swing.JLabel administration_label;
    private javax.swing.JPopupMenu administration_menu;
    private javax.swing.JMenuItem administration_menu_item;
    private javax.swing.JMenuItem clear;
    private javax.swing.JMenuItem cs_menu;
    private javax.swing.JMenuItem customer_invoices;
    private javax.swing.JLabel customer_label;
    private javax.swing.JMenuItem customer_list;
    private javax.swing.JPopupMenu customer_menu;
    private javax.swing.JMenuItem customer_quotes;
    private javax.swing.JMenu customer_transaction_menu;
    private javax.swing.JLabel date_label;
    private javax.swing.JMenu document;
    protected static javax.swing.JLabel download_lb;
    private javax.swing.JMenu edit;
    private javax.swing.JLabel email_notice_label;
    private javax.swing.JMenuItem es_menu;
    public static javax.swing.JButton exportButton;
    private javax.swing.JMenu file;
    private javax.swing.JToolBar footerToolBar;
    private javax.swing.JMenu help;
    private javax.swing.JMenuItem helpContents;
    protected static javax.swing.JDesktopPane home_desktop;
    private javax.swing.Box.Filler horizontalFiller;
    private javax.swing.Box.Filler horizontalFiller1;
    private javax.swing.Box.Filler horizontalFiller2;
    private javax.swing.Box.Filler horizontalFiller3;
    private javax.swing.JPanel icon_holder;
    private javax.swing.JButton issueListButton;
    private javax.swing.JLabel item_label;
    private javax.swing.JMenuItem item_list;
    private javax.swing.JPopupMenu item_menu;
    private javax.swing.JMenu item_transaction_menu;
    public static javax.swing.JButton items_count_label;
    private javax.swing.JButton jButton17;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JLabel label_icon_notice;
    private javax.swing.JLabel location_label;
    private javax.swing.JPopupMenu location_menu;
    private javax.swing.JMenuItem masterdata;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JMenuItem menuItemLogout;
    private javax.swing.JLabel menu_label;
    public static javax.swing.JLabel network_label;
    protected static javax.swing.JButton nextButton;
    private javax.swing.JLabel open_file;
    protected static javax.swing.JButton previousButton;
    private javax.swing.JMenuItem print;
    public static javax.swing.JLabel profile_label;
    protected static javax.swing.JProgressBar progressBar;
    private javax.swing.JButton receiveListButton;
    private javax.swing.JMenu sales_transaction_menu;
    private javax.swing.JMenu save;
    protected static javax.swing.JButton saveButton;
    private javax.swing.JTextField scan_input;
    private javax.swing.JPanel scan_panel;
    private javax.swing.JLabel search_icon_label;
    private javax.swing.JPanel search_panel;
    private javax.swing.JTextField search_textfield;
    private javax.swing.JMenuItem send;
    private javax.swing.JMenuItem settings;
    private javax.swing.JMenu stores;
    private javax.swing.JLabel supplier_label;
    private javax.swing.JMenuItem supplier_list;
    private javax.swing.JPopupMenu supplier_menu;
    private javax.swing.JMenu supplier_transaction_menu;
    private javax.swing.JMenu system;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JLabel transactions_label;
    private javax.swing.JPopupMenu transactions_menu;
    private javax.swing.JMenuItem ts_menu;
    private javax.swing.JMenuItem usermanual;
    private javax.swing.JMenu view;
    // End of variables declaration//GEN-END:variables

/**
 * Download SwingWorker Class
 */
class DownloadTask extends SwingWorker<Void,Void>{
    public Void doInBackground(){
        //int progress = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        Date currentTime = new Date();
        String ddate = formatter.format(currentTime);
        String username = System.getProperty("user.name");
        download = new Download("http://sw090130.africa.bmw.corp/insp/MAN_20140128_Inventory_Store_Program_0001.docx",new File("C://Users//"+username+"/Desktop/MAN_"+ddate+"_Inventory_Store_Program_0001.docx")); 
        setProgress(0);
        download.downloadFile();
        
        if(download.state){
            open_file.setVisible(true);
            open_file.setText("<html><u>Open File</u></html>"); 
            open_file.setToolTipText(""+download.libFile);
            open_file.setForeground(Color.BLUE);
        }else{
            open_file.setVisible(false);
            download_lb.setText(""); 
            progressBar.setVisible(false); 
        }
        
        return null;
    }
}

}


class WorkerThread extends SwingWorker<String, String> {

  /*
  * This should just create a frame that will hold a progress bar until the
  * work is done. Once done, it should remove the progress bar from the dialog
  * and add a label saying the task complete.
  */
   
  Progress pg = new Progress(); 
  //private JFrame frame = new JFrame(); 
  private JDialog dialog = new JDialog(pg, "Proccessing!", true);
  //private JProgressBar progressBar = new JProgressBar();
  public static String results;
  public static String issue_learner_notify;
  public static String receive_learner_notify;
  
  public WorkerThread() {
    dialog.setPreferredSize(new Dimension(299, 160));
    pg.setResizable(false); 
    pg.setLocationByPlatform(true);   
    /*progressBar.setString("Please wait...");
    progressBar.setStringPainted(true);
    progressBar.setIndeterminate(true);*/
    dialog.getContentPane().add(pg.getContentPane());
    dialog.pack();
    dialog.setModal( false );
    dialog.setVisible(true);
      //pg.setVisible(true); 
  }

  @Override
  protected String doInBackground() throws Exception 
  {
    //send e-mail
    SendEmail mail = new SendEmail(MainWindow.recipient,MainWindow.email,MainWindow.on_copy,MainWindow.lmessage,MainWindow.subject);
    String state = mail.Send();

    return state;
  }

  protected void done() 
  {
     results = "";
      try {
          results = get();  // get the results returned by doInBackground() function and use them
      } catch (InterruptedException ex) {
          Logger.getLogger(GuiWorker.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ExecutionException ex) {
          Logger.getLogger(GuiWorker.class.getName()).log(Level.SEVERE, null, ex);
      }
    
      dialog.dispose();
      
      if(results == "success")
      {
           MainWindow.issue_notify_true();
           MainWindow.receive_notify_true();
      }else{
           MainWindow.issue_notify_false();
           MainWindow.receive_notify_false();
      }
       
  }

}



