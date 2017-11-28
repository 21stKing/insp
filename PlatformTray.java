
package inventory_system;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;


public class PlatformTray 
{    
     final PopupMenu popup = new PopupMenu();
     final static TrayIcon trayIcon = new TrayIcon(createImage("/inventory_system/image/insp_trayicon_online_45x39.png", "tray icon"));
     final static SystemTray tray = SystemTray.getSystemTray();
     Date currentdate = new Date();
     String timelogin;
     
     public PlatformTray()
     {
        //Check the PlatformTray support
        if (!SystemTray.isSupported()) {
            System.out.println("System Tray is not supported");
            return;
        }
        
	SimpleDateFormat formatter2 = new SimpleDateFormat("yyMMdd HH:mm");
	timelogin = formatter2.format(currentdate);
        
        // Create a popup menu components
        String session = "Online, "+UserProfile.getUsername()+" | "+timelogin+"";
        MenuItem sessionItem = new MenuItem(session); 
        //MenuItem displayItem = new MenuItem("Display...");
        //MenuItem logoutItem = new MenuItem("Log out");
        MenuItem exitItem = new MenuItem("Exit");
        
        //Add components to popup menu
        popup.add(sessionItem);
        //popup.add(displayItem);
        popup.addSeparator();
        //popup.add(logoutItem);
        popup.add(exitItem);
        
        trayIcon.setPopupMenu(popup);
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Inventory Store Program");
        
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }
        
        /*trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"This dialog box is run from System Tray");
            }
        });*/  
        
         
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 Date currentdate = new Date();
                 if(TrolleyGUI.basketCount == 0)
                    {
                        UserLog log = new UserLog(UserProfile.getUserID(),UserProfile.getRole(),1,currentdate);
                        tray.remove(trayIcon);
                        System.exit(0);
                    }
                    else{
                        int answer = JOptionPane.showConfirmDialog(null, "<html>You have <b>"+TrolleyGUI.basketCount+"</b> item(s) in the Trolley.<br>Are you sure you want to logout?</html>", "Items Trolley not empty", 0);

                        if(answer == 0)
                        {
                            UserLog log = new UserLog(UserProfile.getUserID(),UserProfile.getRole(),1,currentdate);
                            tray.remove(trayIcon);
                            System.exit(0); 
                        }else
                        {
                            //do not logout (do nothing)
                        }
                    }
            }
        });
    }
     
    protected static void exitTray(){
      tray.remove(trayIcon);  
    }
    
     //Obtain the image URL
    protected static java.awt.Image createImage(String path, String description) {
        URL imageURL = PlatformTray.class.getResource(path);
        
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    } 
}
