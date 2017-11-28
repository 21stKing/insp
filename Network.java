/*
 * This class checks the network status of the computer
 * @author tebogo
 */
package inventory_system;

import static java.lang.System.out;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;
import javax.swing.JOptionPane;



public class Network 
{
    ArrayList<String> netList;
    
    public Network()
    {
        
    }
    
    /**
     * <p> This method returns a list of network interfaces available on this machine
     * @return List of network interfaces
     * @throws SocketException 
     */
    protected ArrayList<String> getNetworkList()throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        netList = new ArrayList<String>();
         while(interfaces.hasMoreElements())
          {
            NetworkInterface nic = interfaces.nextElement();
            String name = nic.getDisplayName();
            netList.add(name);  
          }
        return netList;
    }
    
    /**
     * <p> Detects if the network interface name provided is up and running except loop back interface
     * @param interface_name
     * @return true if network interface is up and running, alternatively false
     * @throws SocketException 
     */
    protected boolean getState(String interface_name) throws SocketException 
    {
        boolean state = false;
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements())
        {
            NetworkInterface nic = interfaces.nextElement(); 
            if(!nic.isLoopback()){
                String net = nic.getDisplayName(); 
                if(net == null ? interface_name == null : net.equals(interface_name))
                {
                    state = nic.isUp();
                   //System.out.println("Network running : "+nic.isUp());
                   //System.out.println("Parent : "+nic.getName());
                }
            }    
        }
        
        return state;
    }

    /**
     * This method checks if a network interface name was set for the program to run
     * @return true if network was set successfully
     */
    protected boolean isNetworkSet(){
        String name = "";
        boolean isSet = true;
        ArrayList<NetworkConfig> network = new ArrayList<>();
        try{
            DB db = new  DB(RunProgram.CONNECTION_MODE);
            network = db.networkInfo();
            if(network.size() > 0){
                name = network.get(0).getNetworkInterfaceName();
            }
        }catch(Exception e){JOptionPane.showMessageDialog(null,e,"Network Database Connection Error!",JOptionPane.ERROR_MESSAGE);}
        
        if(name == null || name == "")
          { 
              isSet = false;
          }else{
            isSet = true;
        }
        return isSet;
    }
    
    /*protected boolean getState() throws SocketException 
    {
        boolean state = false;
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements())
        {
            NetworkInterface nic = interfaces.nextElement();
            if(nic.getDisplayName().equals("Intel(R) 82579LM Gigabit Network Connection"))
            {
                state = nic.isUp();
               //System.out.println("Network running : "+nic.isUp());
               //System.out.println("Parent : "+nic.getName());
            }
        }
        
        return state;
    }*/
}
    
