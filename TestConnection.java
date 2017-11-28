
package inventory_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author tebogo
 */
public class TestConnection {
    
    protected String url;
    protected String username;
    protected String password;
    protected static String static_password;
    protected int port;
    protected String database;
    protected String servername;
    //protected String driver_con_string;
    Connection con;
    
    public TestConnection(String url, String username, String password, int port, String database, String servername )
    {
        this.url = url;
        this.username = username;
        this.password = password;
        this.static_password = password;
        this.port = port;
        this.database = database;
        this.servername = servername;
        //this.driver_con_string = drivecon;
        createUrl();   
    }
    
    private void createUrl(){
      if(url.equals("") || url == null)
      {
          url = "jdbc:mysql:"+ "//" + servername + ":" + port +"/" + database + "?"; // jdbc:mysql://localhost/dimdb?
      }    
    }
    
    protected void Test() throws SQLException
    {
        con = DriverManager.getConnection(url, username, password);
        //con = DriverManager.getConnection(url+"user=kinotech_admin&password=21st_King079*");
    }
    
    /*protected boolean Test()
    {
        try{
            System.out.println("Connecting to database...");
            con = DriverManager.getConnection(url, username, password);
            //con = DriverManager.getConnection(url+"user=admin&password=admin123");
            results = true;
            System.out.println("Connection successfull established!");
        }catch(SQLException e)
        {
          throw new RuntimeException("Unable to connect to database!", e);
        }finally{
            System.out.println("Closing the connection.");
            if (con != null) try { con.close(); } catch (SQLException ignore) {System.out.println(ignore);}
        }
        
        return results;
    }*/
}
