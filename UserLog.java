package inventory_system;

import java.net.*;
import java.text.*;
import java.util.Date;
import javax.swing.JOptionPane;


public class UserLog 
{
	private static final long serialVersionUID = 1L;
	private static String compname;
	public static int id;
	public static int log;
	public static Date logfirst;
        public static int role;
        
        
	public UserLog(int userid,int roleIn, int logstate, Date datein)
	{
	  id = userid;
          role = roleIn;
	  log = logstate;
	  logfirst = datein;
	  String logoutdate;
	  
	  String timelogin;
	  SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  timelogin = formatter2.format(logfirst);
	  	  
	 try
  	   {
		 compname = InetAddress.getLocalHost().getHostName(); 
	   }
	 catch (Exception e)
        {
		 System.out.println("Exception caught ="+e.getMessage());
	}
    
	 /**********************************************************
	 *  When user logs in insert details to database.          *
	 *  And also when he logs out                              *
	 **********************************************************/
	 try{	    	
	      ConnectDatabase database = new ConnectDatabase();	
              
               if(logstate == 0)
                 {
                    String batch = "INSERT INTO userlog (userid,logindate,ipaddress) VALUES (" + id + ",'" + timelogin + "','" + compname + "')";
                    database.setBatch(batch);    	 
                 }
               else
                 {  
                     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                     Date currentdate = new Date();
                     logoutdate = formatter.format(currentdate);
                     
                     String loginDate = database.getLoginDate(id);
                     String batch = "UPDATE userlog SET logoutdate = ' " + logoutdate + "' WHERE userid = " + id + " AND logindate='" + loginDate + "'";
                     database.setBatch(batch);  
                 }
                      
                 database.setCloseDatabase();
  
	     }
	     catch(Exception e1)
	     {
	      JOptionPane.showMessageDialog(null, "<html>"+e1+"</html>", "Warning", 1);
	     }	 
	 }
}
