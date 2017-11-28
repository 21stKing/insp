package inventory_system;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.*;
import javax.swing.*;


/**
 * @author Tebogo Kekana
 * 
 * <p> Date 2014-10
 */

public class RunProgram 
{      
    private boolean network_state;
    protected String netName;
    protected String network_inter_name;
    private boolean userSet;
    public static int CONNECTION_MODE;
    Network net;
    //NetworkDialog nd;
    public static ArrayList<Server> server;
    public static ArrayList<String> network_list;
    
    public RunProgram()
    {
       /**
        * Get network interfaces of the machine and check each if its running.
        * Finally, check if you can connect to the internet. 
        */
        net = new Network();
        try {
            network_list = net.getNetworkList();
            for(int x=0;x < network_list.size(); x++){
              boolean isRunning = net.getState(network_list.get(x));
              if(isRunning){ 
                 //you can test for internet connection 
                 network_inter_name = network_list.get(x);
              }
            }
        } catch (SocketException ex) {
            JOptionPane.showMessageDialog(null,ex,"Network Interfaces",JOptionPane.ERROR_MESSAGE);
            System.exit(0); 
        }
        
        /**
         * Now that we have successfully retrieved an interface that is running, let's 
         * make the system to connect to the remote server else if not then connect to the local machine server
         * (connect to the database server that runs locally on this machine)
         */
        if(network_inter_name == "" || network_inter_name == null)
        {          
          CONNECTION_MODE = 0; //local connection (localhost)
        }else{
            //since there is a network interface actively running, let's try to open connection
            try{
              //URL url = new URL("http://129.232.179.98"); //http://kinotech.co.za 129.232.179.98
              URL url = new URL("http://kinotech.co.za"); //http://129.232.156.132 
              URLConnection con = url.openConnection();
              con.connect();
              CONNECTION_MODE = 1; // remote connection (remote server)
            }catch(IOException e){
              JOptionPane.showMessageDialog(null,e,"Remote server unreachable",JOptionPane.INFORMATION_MESSAGE);  
              CONNECTION_MODE = 0;
            } 
         }
        
        
        //test if remote server is reachable 
       //network_state = net.isNetworkSet(); //checks if network name is set in the database 
        
       //if(network_state == true)
         // {   
              //Initialization check (after installation)
              try{
                DB db = new  DB(RunProgram.CONNECTION_MODE);
                userSet = db.isSet();
                if(!userSet){
                    //call admin window
                    AdminUserSettings aus = new AdminUserSettings();
                    aus.setLocationByPlatform(true); 
                    aus.setVisible(true); 
                }else{
                    //show login window
                    //get network name
                   // ArrayList<NetworkConfig> network = new ArrayList<>();
                    //network = db.networkInfo();
                    //netName = "";
                    //netName = network.get(0).getNetworkInterfaceName();
                    
                    //get active server names
                    server = db.getActiveServer();
                    
                    //check if network is up running
                    //boolean isRunning = true;
                    //try{ isRunning = net.getState(netName); 
                    //} catch (SocketException ex){Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);}

                    //if(isRunning == true){
                        PasswordRequest passwordGUI = new PasswordRequest();
                        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                        int width = dim.width/2-passwordGUI.getSize().width/2;
                        int height = dim.height/2-passwordGUI.getSize().height/2;  
                        passwordGUI.setLocation(width,height); //Positions the program at the center of any desktop screen
                        passwordGUI.setVisible(true);  
                        
                        /*network_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/ethernet_footer2.png")));
                        network_label.setToolTipText("Online");
                        profile_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/user_profile2.png")));
                        profile_label.setText(up.getFirstName());
                        profile_label.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N*/
                    //}else{

                          //JOptionPane.showMessageDialog(null, "<html>Please connect a network cable to your machine<br>Or contact your administrator for help!</html>", "Network Cable Not Connected", JOptionPane.INFORMATION_MESSAGE);
                          /*nd = new NetworkDialog(new JFrame());
                          nd.setSize(312, 225);
                          nd.setLocationByPlatform(true); 
                          nd.setVisible(true);
                          Object answer = nd.value;

                          if(answer == "Yes"){
                            //work offline, prompt local database
                            //getUserVerificationOffline();
                            }else if(answer == "Setup network"){
                              CONNECTION_MODE = 1;   
                             //se up network 
                              NetWorkSetup setup = new NetWorkSetup();
                              setup.setLocationByPlatform(true); 
                              setup.setVisible(true); 
                             //network_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/ethernet_footer.png")));
                             //network_label.setToolTipText("Offline"); 
                            }else if(answer == "No"){
                           System.exit(0); }*/
                   // }
                }
               }catch(Exception e)
               {
                   //JOptionPane.showMessageDialog(null,e,"First time usage | Administrator user needs to be set",JOptionPane.ERROR_MESSAGE);
                   JOptionPane.showMessageDialog(null,e,"Database connection Error",JOptionPane.ERROR_MESSAGE);
               }
         /* } 
        else
          {
             JOptionPane.showMessageDialog(null, "<html>Please connect a network cable to your machine<br>Or contact your administrator for help!</html>", "Network Cable Not Connected", JOptionPane.INFORMATION_MESSAGE);  
             //System.exit(0);
             //set up network 
             NetWorkSetup setup = new NetWorkSetup();
             setup.setLocationByPlatform(true); 
             setup.setVisible(true); 
          }*/
              
    }
    
    public void networkSetup(){
        NetworkDialog nd = new NetworkDialog(new JFrame());
        nd.setSize(312, 225);
        nd.setLocationByPlatform(true); 
        nd.setVisible(true);
        Object answer = nd.value;

        if(answer == "Yes"){
         //work offline, prompt local database
         //getUserVerificationOffline();
        }else if(answer == "Setup network"){
           CONNECTION_MODE = 1;   
          //se up network 
          NetWorkSetup setup = new NetWorkSetup();
          setup.setLocationByPlatform(true); 
          setup.setVisible(true); 
          //network_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/inventory_system/image/ethernet_footer.png")));
          //network_label.setToolTipText("Offline"); 
         }else if(answer == "No"){
          System.exit(0); }
    }
    
    public static void main(String[] args)
    {
       try
        {UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch(Exception e)
        {System.out.println("Nimbus isn't available");}
        
          /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RunProgram();
            }
        });
    }
}
