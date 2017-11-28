
package inventory_system;

/**
 *
 * @author kinotech pty ltd
 */

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ConnectDatabase
{
  private Connection con;
  private JTable table;
  private ResultSet rs,rs2,rs3;
  private ResultSetMetaData rsmd;
  private DatabaseMetaData dbmd;
  public static int value;
  protected String path;

  public ConnectDatabase()throws Exception
  {
    //localhost
    path = "jdbc:mysql://localhost/dimdb?" ;
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    con = DriverManager.getConnection(path+"user=admin&password=admin123");   
   
    //remote connection
    //path = "jdbc:mysql://mysql.kinotech.co.za:3307/teb15_dimdb?" ; //41.185.8.156
    //Class.forName("com.mysql.jdbc.Driver").newInstance();
    //con = DriverManager.getConnection(path+"user=teb15_admin&password=adm@kino2014");   
  }
  /**
   * Path to your access db 
   * example:
   *        jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb)};DBQ=C:\\DatabaseFolder\\db_name.mdb
   * or     jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb)};DBQ=//network_drive_name/folder_name/db_name.mdb
   * @param db_url_local
   * @throws Exception 
   */
  public ConnectDatabase(String db_url_local)throws Exception
  {
    
    Class.forName("com.mysql.jdbc.Driver");
    con = DriverManager.getConnection(db_url_local+"user=admin&password=admin123");   
  }
 
  public ArrayList<String> TableList() throws SQLException{
    ArrayList<String> tables = new ArrayList<String>();
    int x = 1;
    String schema= "";
    DatabaseMetaData md = con.getMetaData();
    rs = md.getTables(null, null, "%", null );

    //x = rs.getMetaData().getColumnCount();
     while (rs.next())
      { 
        /*System.out.println("Catalog : "+rs.getString(1));
        System.out.println("Schema : "+rs.getString(4));
        System.out.println("Table name : "+rs.getString(3)); 
        System.out.println("Table name: "+rs.getString("TABLE_NAME"));*/
          
        schema = rs.getString(4).toString();            
        if(schema.equals("SYSTEM TABLE") || schema.equals("VIEW")){ 
          // System.out.println("None user table");
        }
        else{
          tables.add((rs.getString(3)).toString()); 
        }
        x++;
      }
    return tables; 
  }
  
  public void setTableData(JTable tablein, String sgl_stringin, String tableopenin) throws SQLException
  {
    table = tablein;
    String sgl_string = sgl_stringin;
    String tableopen = tableopenin;
    Statement stmt = con.createStatement();
    String order_value;
    
    if ((tableopen.equals("order_list")) || (tableopen.equals("receive_list")) || (tableopen.equals("issue_list_01")))
    {
      order_value = "Date";
    }
    else
    {
   
      if (tableopen.equals("tool_list"))
      {
        order_value = "`ID`";
      }
      else
      {
       
        if (tableopen.equals("learner_list"))
        {
          order_value = "`learner_id`";
        }
        else
        {
       
          if (tableopen.equals("equipment_list"))
          {
            order_value = "`ID`";
          }
          else
          {
            
            if (tableopen.equals("equipment_issue_list"))
            {
              order_value = "`Date OUT`";
            }
            else
            {
        
              if (tableopen.equals("tool_issue_list"))
              {
                order_value = "`Date OUT`";
              }
              else
              {
            
                if (tableopen.equals("consumables"))
                {
                  order_value = "`ID`";
                }
                else
                {
            
                  if (tableopen.equals("consumables_issue_list"))
                  {
                    order_value = "`Date OUT`";
                  }
                  else
                  {
                
                    if (tableopen.equals("tool_receive_list"))
                    {
                      order_value = "`Date IN`";
                    }
                    else
                    {
                     if (tableopen.equals("notice_board"))
                        {
                          order_value = "`Notice_Date`";
                        }
                        else
                        {
                      if (tableopen.equals("learner_list"))
                      {
                        order_value = "`learner_id`";
                      }
                      else
                      {
                  
                        if (sgl_string.substring(1, 10).equals("In Repair")) {
                          order_value = "`Send To Repair`";
                        }
                        else
                        {
                          order_value = "`Location / Bin`";
                        }
                      }
                     } 
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    if (sgl_string.equals(""))
    {
      rs = stmt.executeQuery("SELECT * FROM " + tableopen + " Order by " + order_value);
    }
    else if (!sgl_string.equals(""))
    {
      rs = stmt.executeQuery("SELECT * FROM " + tableopen + " where " + sgl_string + " Order by " + order_value);
    }

    rsmd = rs.getMetaData();
    int clmCnt = rsmd.getColumnCount();
    int clmCnttable = table.getColumnCount();
    int r = 0;
    int k = 1;

    while (rs.next())
    {
      ((DefaultTableModel)table.getModel()).addRow(new Object[1]);

      for (int i = 0; i < clmCnttable; i++)
      {
        while ((!this.table.getColumnName(i).equals(this.rsmd.getColumnName(k))) && (k < clmCnt))
        {
          k++;
        }

        if (this.table.getColumnName(i).equals(this.rsmd.getColumnName(k)))
        {
          this.table.setValueAt(this.rs.getString(k), r, i);
        }
        k = 1;
      }
      r++;
    }
    rs.close();
    stmt.close();
  }
  
  /**
   * Returns all data of a table in the database for a specified table name
   * @param tableIn
   * @return table data
   * @throws SQLException 
   */
  public Vector getTableData(String tableName) throws SQLException
  {
      Vector<Vector<String>>tableVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
  
      rs = stmt.executeQuery("SELECT * FROM " + tableName);
      rsmd = rs.getMetaData();
      int colCount = rsmd.getColumnCount();
         
      while (rs.next())
      {
        Vector<String> tFields = new Vector<String>();
        for(int x = 1;x <= colCount;x++){
            tFields.add(rs.getString(x)); 
        }
        tableVector.add(tFields);
      }   
      
      rs.close();
      stmt.close();
      
      return tableVector;
  }
  
  /**
   * Returns table headers from a specified table
   * @param tableName
   * @return table headers
   * @throws SQLException 
   */
  public Vector getTableHeader(String tableName) throws SQLException
  {
      Vector<Vector<String>>tableVector = new Vector<Vector<String>>();
      
      
      Statement stmt = con.createStatement();
  
      rs = stmt.executeQuery("SELECT * FROM " + tableName);
      rsmd = rs.getMetaData();
      int colCount = rsmd.getColumnCount();
      int col = 0;
      boolean isTableEmpty = true;
      while(rs.next())
      {  
        isTableEmpty = false;
        if(col != colCount)
        {
          for(int x = 1;x <= colCount;x++){
              Vector<String> tFields = new Vector<String>(); 
              tFields.add(rsmd.getColumnName(x).toString());
              tableVector.add(tFields);
          }
          col = colCount;
        }
      }   
      
      //if table is empty then get column names only
      if(isTableEmpty){  
          for(int x=1;x<=colCount;x++){
            Vector<String> tFields = new Vector<String>(); 
            tFields.add(rsmd.getColumnName(x).toString());
            tableVector.add(tFields);
          }
      }
      
      
      rs.close();
      stmt.close();
      
      return tableVector;
  }
  
  
  public Vector setLearnerTable(String sql_query) throws SQLException
  {
      Vector<Vector<String>>learnerVector = new Vector<Vector<String>>();
      
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM learner_list WHERE " + sql_query + " Order by learner_id" );
     
      while (rs.next())
      {
        Vector<String> learner = new Vector<String>();
        learner.add(rs.getString(1)); // Learner ID
        learner.add(rs.getString(2)); // Name
        learner.add(rs.getString(3)); // Surname
        learner.add(rs.getString(4)); // Company id
        learner.add(rs.getString(5)); // Group
        learner.add(rs.getString(6)); // Level
        learner.add(rs.getString(7)); // Email
        learnerVector.add(learner);
      }
      
      rs.close();
      stmt.close();
      
      
      return learnerVector;
  }
  
  public Vector setNoticeTable() throws SQLException
  {
      Vector<Vector<String>>noticeVector = new Vector<Vector<String>>();
      
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM notice_board Order by Notice_Date" );
     
      while (rs.next())
      {
        Vector<String> notice = new Vector<String>();
        notice.add(rs.getString(1)); // msg ID
        notice.add(rs.getString(2)); // Notification
        notice.add(rs.getString(3)); // Notice date
        notice.add(rs.getString(4)); // User id
        noticeVector.add(notice);
      }
      
      rs.close();
      stmt.close();
      
      
      return noticeVector;
  }
  
   public Vector setChangeTable(int [] learnerIDs) throws SQLException
  {
      Vector<Vector<String>>learnerVector = new Vector<Vector<String>>();
      //int [] IDS;
      
      Statement stmt = con.createStatement();
      for(int x = 0; x < learnerIDs.length;x++)
      {
        rs = stmt.executeQuery("SELECT * FROM learner_list WHERE learner_id LIKE " + learnerIDs[x] + " Order by learner_id" );
     
        while (rs.next())
        {
          Vector<String> learner = new Vector<String>();
          learner.add(rs.getString(2)); // Name
          learner.add(rs.getString(3)); // Surname
          learner.add(rs.getString(5)); // Group
          learner.add(rs.getString(6)); // Level
          learnerVector.add(learner);
        }
      } 
      
      rs.close();
      stmt.close();
      
      
      return learnerVector;
  }
   
 public Vector setSearchTable(String sgl, String tableIn) throws SQLException
  {
      Vector<Vector<Object>>searchVector = new Vector<Vector<Object>>();
      Statement stmt = con.createStatement();
      
       if (sgl.equals(""))
        {
          rs = stmt.executeQuery("SELECT * FROM " + tableIn + " Order by ID");
        }
        else if (!sgl.equals(""))
        {
          rs = stmt.executeQuery("SELECT * FROM " + tableIn + " where " + sgl + " Order by ID");
        }
      
     
      while (rs.next())
      {
        Vector<Object> search = new Vector<Object>();
        
        search.add(rs.getString(1)); // ID
        search.add(rs.getString(2)); // Store
        search.add(rs.getString(3)); // Location
        search.add(rs.getString(4)); // Description
        search.add(rs.getString(5)); // Current
        search.add(rs.getString(6)); // Creator
        searchVector.add(search);
      }
      
      rs.close();
      stmt.close();
      
      
      return searchVector;
  }
 
 public Vector setConsumableTable(String tableIn) throws SQLException
  {
      Vector<Vector<String>>conVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
  
      rs = stmt.executeQuery("SELECT * FROM " + tableIn + " Order by ID");

      while (rs.next())
      {
        Vector<String> consm = new Vector<String>();
        consm.add(rs.getString(1)); // Consum. ID
        consm.add(rs.getString(2)); // User ID
        consm.add(rs.getString(3)); // Consumable name
        consm.add(rs.getString(5)); // available
        consm.add(rs.getString(4)); // quantity
        consm.add(rs.getString(6)); // store
        conVector.add(consm);
      }
      
      
      
      rs.close();
      stmt.close();
      //  //nb: if you uncomment this line then you will get a general error
      
      return conVector;
  }
 
  public Vector setUserTable(int coid) throws SQLException
 {
     Vector<Vector<String>>userVector = new Vector<Vector<String>>();
     Statement stmt = con.createStatement();
     rs = stmt.executeQuery("SELECT * FROM user WHERE `company_id` NOT LIKE "+coid+" AND `Role` NOT LIKE 'superuser' ORDER BY `company_id`");
     
      while (rs.next())
      {
        Vector<String> user = new Vector<String>();
        user.add(rs.getString(2)); // compid
        user.add(rs.getString(3)); // username
        user.add(rs.getString(5)); // role
        //user.add(rs.getString(6)); // role code
        user.add(rs.getString(7)); // email
        userVector.add(user);
      }  
     
     return userVector;
 }
  
   public Vector setPendingItemsTable() throws SQLException
  {
      Vector<Vector<String>>pendVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
  
      rs = stmt.executeQuery("SELECT * FROM consumables_issue_list ORDER BY ID");

      while (rs.next())
      {
        Vector<String> pend = new Vector<String>();
        pend.add(rs.getString(1)); // issue ID
        pend.add(rs.getString(2)); // name
        pend.add(rs.getString(3)); // compid
        pend.add(rs.getString(4)); // item no.
        pend.add(rs.getString(5)); // item descrp.
        pend.add(rs.getString(6)); // qty
        pend.add(rs.getString(7)); // date out
        pend.add(rs.getString(8)); // location
        pend.add(rs.getString(9)); // facilitator
        pend.add(rs.getString(10)); // storeman
        pendVector.add(pend);
      }  
      
      
      rs = stmt.executeQuery("SELECT * FROM equipment_issue_list ORDER BY ID");

      while (rs.next())
      {
        Vector<String> pend = new Vector<String>();
        pend.add(rs.getString(1)); // issue ID
        pend.add(rs.getString(2)); // name
        pend.add(rs.getString(3)); // compid
        pend.add(rs.getString(4)); // item no.
        pend.add(rs.getString(5)); // item descrp.
        pend.add(rs.getString(6)); // qty
        pend.add(rs.getString(7)); // date out
        pend.add(rs.getString(8)); // location
        pend.add(rs.getString(9)); // facilitator
        pend.add(rs.getString(10)); // storeman
        pendVector.add(pend);
      } 
      
      rs = stmt.executeQuery("SELECT * FROM tool_issue_list ORDER BY ID");

      while (rs.next())
      {
        Vector<String> pend = new Vector<String>();
        pend.add(rs.getString(1)); // issue ID
        pend.add(rs.getString(2)); // name
        pend.add(rs.getString(3)); // compid
        pend.add(rs.getString(4)); // item no.
        pend.add(rs.getString(5)); // item descrp.
        pend.add(rs.getString(6)); // qty
        pend.add(rs.getString(7)); // date out
        pend.add(rs.getString(8)); // location
        pend.add(rs.getString(9)); // facilitator
        pend.add(rs.getString(10)); // storeman
        pendVector.add(pend);
      } 
      
      rs.close();
      stmt.close();
      //  //nb: if you uncomment this line then you will get a general error
      
      return pendVector;
  }
 
 
  public Vector setIncoTable(String tableIn) throws SQLException
  {
      Vector<Vector<String>>incoVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
      Statement stmt2 = con.createStatement();
      String quantity = "";
      rs = stmt.executeQuery("SELECT * FROM " + tableIn + " Order by ID");

      while (rs.next())
      {
        Vector<String> inco = new Vector<String>();
        String ID = rs.getString(1);
        String STORE = rs.getString(2);
        String LOCATION = rs.getString(3);
        String DESCRIPTION = rs.getString(4);
        String CURRENT = rs.getString(5);
        
        inco.add(ID); // ID
        inco.add(STORE); // store
        inco.add(LOCATION); // location/ bin
        inco.add(DESCRIPTION); // description
        inco.add(CURRENT); // current
        
        if(STORE.equals("Consumable store")) 
        {
          rs2 = stmt2.executeQuery("SELECT Quantity FROM consumables WHERE ID LIKE "+ID);
          while(rs2.next()){quantity = rs2.getString("Quantity");} 
          inco.add(quantity); // quantity of a respective item in its respective table
        }
        else if(STORE.equals("Equipment store"))
        {
          rs2 = stmt2.executeQuery("SELECT Quantity FROM equipment_list WHERE ID LIKE "+ID);   
          while(rs2.next()){quantity = rs2.getString("Quantity");} 
           inco.add(quantity); // quantity of a respective item in its respective table
        }
        else if(STORE.equals("Tool store")) 
        {
          rs2 = stmt2.executeQuery("SELECT Quantity FROM tool_list WHERE ID LIKE "+ID);     
          while(rs2.next()){quantity = rs2.getString("Quantity");} 
           inco.add(quantity); // quantity of a respective item in its respective table
        }

        incoVector.add(inco);
      }

      rs.close();
      stmt.close();
           
      return incoVector;
  }
 
 public Vector setReceiveListTable(String tableIn) throws SQLException
  {
      Vector<Vector<String>>receiveVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
  
      rs = stmt.executeQuery("SELECT * FROM " + tableIn + " Order by ID");

      while (rs.next())
      {
        Vector<String> receive = new Vector<String>();
        receive.add(rs.getString(1)); // receive id
        receive.add(rs.getString(2)); // id
        receive.add(rs.getString(3)); // store
        receive.add(rs.getString(4)); // location / bin
        receive.add(rs.getString(5)); // description
        receive.add(rs.getString(6)); // quantity
        receive.add(rs.getString(7)); // date
        receive.add(rs.getString(8)); // company id
        receive.add(rs.getString(9)); // learner
        receive.add(rs.getString(10)); //facilitator
        receive.add(rs.getString(11)); // storeman
        receiveVector.add(receive);
      }
      
      rs.close();
      stmt.close();
      //  //nb: if you uncomment this line then you will get a general error
      
      return receiveVector;
  }
 
  public Vector setReceiveListTableSearch(String sgl,String tableIn) throws SQLException
  {
      Vector<Vector<String>>receiveVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
  
      if (sgl.equals(""))
        {
          rs = stmt.executeQuery("SELECT * FROM " + tableIn + " Order by ID");
        }
        else if (!sgl.equals(""))
        {
          rs = stmt.executeQuery("SELECT * FROM " + tableIn + " where " + sgl + " Order by ID");
        }

      while (rs.next())
      {
        Vector<String> receive = new Vector<String>();
        receive.add(rs.getString(1)); // receive id
        receive.add(rs.getString(2)); // id
        receive.add(rs.getString(3)); // store
        receive.add(rs.getString(4)); // location / bin
        receive.add(rs.getString(5)); // description
        receive.add(rs.getString(6)); // quantity
        receive.add(rs.getString(7)); // date
        receive.add(rs.getString(8)); // company id
        receive.add(rs.getString(9)); // learner
        receive.add(rs.getString(10)); //facilitator
        receive.add(rs.getString(11)); // storeman
        receiveVector.add(receive);
      }
      
      rs.close();
      stmt.close();
      //  //nb: if you uncomment this line then you will get a general error
      
      return receiveVector;
  }
 
 
 public Vector setIssueListTable(String tableIn) throws SQLException
  {
      Vector<Vector<String>>issueVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
  
      rs = stmt.executeQuery("SELECT * FROM " + tableIn + " Order by ID");

      while (rs.next())
      {
        Vector<String> issue = new Vector<String>();
        issue.add(rs.getString(1)); // issue id
        issue.add(rs.getString(2)); // id
        issue.add(rs.getString(3)); // store
        issue.add(rs.getString(4)); // location / bin
        issue.add(rs.getString(5)); // description
        issue.add(rs.getString(6)); // quantity
        issue.add(rs.getString(7)); // date
        issue.add(rs.getString(8)); // learner
        issue.add(rs.getString(9)); // company id
        issue.add(rs.getString(10)); //group 
        issue.add(rs.getString(11)); //level
        issue.add(rs.getString(12)); //facilitator
        issue.add(rs.getString(13)); // storeman
        issueVector.add(issue);
      }
      
      rs.close();
      stmt.close();
      //  //nb: if you uncomment this line then you will get a general error
      
      return issueVector;
  }
 
  public Vector setIssuedItemsTable(String companyid) throws SQLException
  {
      Vector<Vector<String>>issueVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
      Statement stmt2 = con.createStatement();
      Statement stmt3 = con.createStatement();
      
      rs = stmt.executeQuery("SELECT * FROM tool_issue_list WHERE company_id LIKE "+companyid+" Order by ID");
  
      while (rs.next())
      {
        Vector<String> issue = new Vector<String>();
        issue.add(rs.getString(1)); // id
        issue.add(rs.getString(2)); // name
        issue.add(rs.getString(3)); // company id
        issue.add(rs.getString(4)); // item number
        issue.add(rs.getString(5)); // description
        issue.add(rs.getString(8)); // location
        issue.add(rs.getString(6)); // issued quantity
        issue.add(rs.getString(7)); // date out
        issue.add(rs.getString(9)); // facillitator
        issue.add(rs.getString(10));// storeman
        issueVector.add(issue);
       }
      
      rs = stmt.executeQuery("SELECT * FROM consumables_issue_list WHERE company_id LIKE "+companyid+" Order by ID");
      while (rs.next())
      {
        Vector<String> issue = new Vector<String>();
        issue.add(rs.getString(1)); // id
        issue.add(rs.getString(2)); // name
        issue.add(rs.getString(3)); // company id
        issue.add(rs.getString(4)); // item number
        issue.add(rs.getString(5)); // description
        issue.add(rs.getString(8)); // location
        issue.add(rs.getString(6)); // issued quantity
        issue.add(rs.getString(7)); // date out
        issue.add(rs.getString(9)); // facillitator
        issue.add(rs.getString(10));// storeman
        issueVector.add(issue);
       }
      
      rs = stmt.executeQuery("SELECT * FROM equipment_issue_list WHERE company_id LIKE "+companyid+" Order by ID");
      while (rs.next())
      {
        Vector<String> issue = new Vector<String>();
        issue.add(rs.getString(1)); // id
        issue.add(rs.getString(2)); // name
        issue.add(rs.getString(3)); // company id
        issue.add(rs.getString(4)); // item number
        issue.add(rs.getString(5)); // description
        issue.add(rs.getString(8)); // location
        issue.add(rs.getString(6)); // issued quantity
        issue.add(rs.getString(7)); // date out
        issue.add(rs.getString(9)); // facillitator
        issue.add(rs.getString(10));// storeman
        issueVector.add(issue);
       }
      
      rs.close();
      stmt.close();
      //  //nb: if you uncomment this line then you will get a general error
      
      return issueVector;
  }
 
 
  public Vector setIssueListTableSearch(String sgl,String tableIn) throws SQLException
  {
      Vector<Vector<String>>issueVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
  
      if (sgl.equals(""))
        {
          rs = stmt.executeQuery("SELECT * FROM " + tableIn + " Order by ID");
        }
        else if (!sgl.equals(""))
        {
          rs = stmt.executeQuery("SELECT * FROM " + tableIn + " where " + sgl + " Order by ID");
        }

      while (rs.next())
      {
        Vector<String> issue = new Vector<String>();
        issue.add(rs.getString(1)); // issue id
        issue.add(rs.getString(2)); // id
        issue.add(rs.getString(3)); // store
        issue.add(rs.getString(4)); // location / bin
        issue.add(rs.getString(5)); // description
        issue.add(rs.getString(6)); // quantity
        issue.add(rs.getString(7)); // date
        issue.add(rs.getString(8)); // learner
        issue.add(rs.getString(9)); // company id
        issue.add(rs.getString(10)); //group 
        issue.add(rs.getString(11)); //level
        issue.add(rs.getString(12)); //facilitator
        issue.add(rs.getString(13)); // storeman
        issueVector.add(issue);
      }
      
      rs.close();
      stmt.close();
      //  //nb: if you uncomment this line then you will get a general error
      
      return issueVector;
  }
 
  public Vector setIssuedItemsTableSearch(String sgl) throws SQLException
  {
      Vector<Vector<String>>issueVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
  
      if (sgl.equals(""))
        {
          rs = stmt.executeQuery("SELECT * FROM tool_issue_list Order by ID");
          while (rs.next())
            {
              Vector<String> issue = new Vector<String>();
              issue.add(rs.getString(1)); // issue id
              issue.add(rs.getString(2)); // id
              issue.add(rs.getString(3)); // store
              issue.add(rs.getString(4)); // location / bin
              issue.add(rs.getString(5)); // description
              issue.add(rs.getString(6)); // quantity
              issue.add(rs.getString(7)); // date
              issue.add(rs.getString(8)); // learner
              issue.add(rs.getString(9)); // company id
              issue.add(rs.getString(10)); //group 
              issue.add(rs.getString(11)); //level
              issue.add(rs.getString(12)); //facilitator
              issue.add(rs.getString(13)); // storeman
              issueVector.add(issue);
            }
          
          rs = stmt.executeQuery("SELECT * FROM consumables_issue_list Order by ID");
          while (rs.next())
            {
              Vector<String> issue = new Vector<String>();
              issue.add(rs.getString(1)); // issue id
              issue.add(rs.getString(2)); // id
              issue.add(rs.getString(3)); // store
              issue.add(rs.getString(4)); // location / bin
              issue.add(rs.getString(5)); // description
              issue.add(rs.getString(6)); // quantity
              issue.add(rs.getString(7)); // date
              issue.add(rs.getString(8)); // learner
              issue.add(rs.getString(9)); // company id
              issue.add(rs.getString(10)); //group 
              issue.add(rs.getString(11)); //level
              issue.add(rs.getString(12)); //facilitator
              issue.add(rs.getString(13)); // storeman
              issueVector.add(issue);
            }
          
          rs = stmt.executeQuery("SELECT * FROM equipment_issue_list Order by ID");
          while (rs.next())
            {
              Vector<String> issue = new Vector<String>();
              issue.add(rs.getString(1)); // issue id
              issue.add(rs.getString(2)); // id
              issue.add(rs.getString(3)); // store
              issue.add(rs.getString(4)); // location / bin
              issue.add(rs.getString(5)); // description
              issue.add(rs.getString(6)); // quantity
              issue.add(rs.getString(7)); // date
              issue.add(rs.getString(8)); // learner
              issue.add(rs.getString(9)); // company id
              issue.add(rs.getString(10)); //group 
              issue.add(rs.getString(11)); //level
              issue.add(rs.getString(12)); //facilitator
              issue.add(rs.getString(13)); // storeman
              issueVector.add(issue);
            }
        }
        else if (!sgl.equals(""))
        {
          rs = stmt.executeQuery("SELECT * FROM tool_issue_list where " + sgl + " Order by ID");
          while (rs.next())
            {
              Vector<String> issue = new Vector<String>();
              issue.add(rs.getString(1)); // issue id
              issue.add(rs.getString(2)); // id
              issue.add(rs.getString(3)); // store
              issue.add(rs.getString(4)); // location / bin
              issue.add(rs.getString(5)); // description
              issue.add(rs.getString(6)); // quantity
              issue.add(rs.getString(7)); // date
              issue.add(rs.getString(8)); // learner
              issue.add(rs.getString(9)); // company id
              issue.add(rs.getString(10)); //group 
              issue.add(rs.getString(11)); //level
              issue.add(rs.getString(12)); //facilitator
              issue.add(rs.getString(13)); // storeman
              issueVector.add(issue);
            }
          
          rs = stmt.executeQuery("SELECT * FROM consumables_issue_list where " + sgl + " Order by ID");
          while (rs.next())
            {
              Vector<String> issue = new Vector<String>();
              issue.add(rs.getString(1)); // issue id
              issue.add(rs.getString(2)); // id
              issue.add(rs.getString(3)); // store
              issue.add(rs.getString(4)); // location / bin
              issue.add(rs.getString(5)); // description
              issue.add(rs.getString(6)); // quantity
              issue.add(rs.getString(7)); // date
              issue.add(rs.getString(8)); // learner
              issue.add(rs.getString(9)); // company id
              issue.add(rs.getString(10)); //group 
              issue.add(rs.getString(11)); //level
              issue.add(rs.getString(12)); //facilitator
              issue.add(rs.getString(13)); // storeman
              issueVector.add(issue);
            }
          
          rs = stmt.executeQuery("SELECT * FROM equipment_issue_list where " + sgl + " Order by ID");
          while (rs.next())
            {
              Vector<String> issue = new Vector<String>();
              issue.add(rs.getString(1)); // issue id
              issue.add(rs.getString(2)); // id
              issue.add(rs.getString(3)); // store
              issue.add(rs.getString(4)); // location / bin
              issue.add(rs.getString(5)); // description
              issue.add(rs.getString(6)); // quantity
              issue.add(rs.getString(7)); // date
              issue.add(rs.getString(8)); // learner
              issue.add(rs.getString(9)); // company id
              issue.add(rs.getString(10)); //group 
              issue.add(rs.getString(11)); //level
              issue.add(rs.getString(12)); //facilitator
              issue.add(rs.getString(13)); // storeman
              issueVector.add(issue);
            }
        }    
      
      rs.close();
      stmt.close();
      //  //nb: if you uncomment this line then you will get a general error
      
      return issueVector;
  }
 
 public Vector setConsumableTableSearch(String sgl, String tableIn) throws SQLException
  {
      Vector<Vector<String>>conVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
      
       if (sgl.equals(""))
        {
          rs = stmt.executeQuery("SELECT * FROM " + tableIn + " Order by ID");
        }
        else if (!sgl.equals(""))
        {
          rs = stmt.executeQuery("SELECT * FROM " + tableIn + " WHERE "+sgl+" Order by ID");
        }
      
      

      while (rs.next())
      {
        Vector<String> consm = new Vector<String>();
        consm.add(rs.getString(1)); // Consum. ID
        consm.add(rs.getString(2)); // User ID
        consm.add(rs.getString(3)); // Consumable name
        consm.add(rs.getString(4)); // quantity
        consm.add(rs.getString(5)); // available
        consm.add(rs.getString(6)); // store
        conVector.add(consm);
      }
      
      
      
      rs.close();
      stmt.close();
      //  //nb: if you uncomment this line then you will get a general error
      
      return conVector;
  }
 
  
 public Vector setConsumableListTable(String tableIn) throws SQLException
  {
      Vector<Vector<String>>conListVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
 
      rs = stmt.executeQuery("SELECT * FROM " + tableIn + " Order by ID");
    
      while (rs.next())
      {
        Vector<String> consmList = new Vector<String>();
        consmList.add(rs.getString(1)); // ID
        consmList.add(rs.getString(2)); // name and surname
        consmList.add(rs.getString(3)); // company id
        consmList.add(rs.getString(4)); // item number
        consmList.add(rs.getString(5)); // eq. descrp.
        consmList.add(rs.getString(6)); // quantity
        consmList.add(rs.getString(7)); // date out
        consmList.add(rs.getString(8)); // location
        consmList.add(rs.getString(9)); // facilitator
        consmList.add(rs.getString(10)); // storeman
        conListVector.add(consmList);
      }
      
      
      
      rs.close();
      stmt.close();
      
      
      return conListVector;
  }
  
  public Vector setEquipmentTable(String tableIn) throws SQLException
  {
      Vector<Vector<String>>eqVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
  
      rs = stmt.executeQuery("SELECT * FROM " + tableIn + " Order by Equipment_ID");

      while (rs.next())
      {
        Vector<String> eq = new Vector<String>();
        eq.add(rs.getString(1)); // eq ID
        eq.add(rs.getString(2)); // ID
        eq.add(rs.getString(3)); // eq name
        eq.add(rs.getString(4)); // available
        eq.add(rs.getString(5)); // quantity
        eq.add(rs.getString(6)); // store
        eqVector.add(eq);
      }
      
      
      
      rs.close();
      stmt.close();
      //  //nb: if you uncomment this line then you will get a general error
      
      return eqVector;
  }
  
  public Vector setEquipmentTableSearch(String sgl,String tableIn) throws SQLException
  {
      Vector<Vector<String>>eqVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
  
       if (sgl.equals(""))
        {
          rs = stmt.executeQuery("SELECT * FROM " + tableIn + " Order by ID");
        }
        else if (!sgl.equals(""))
        {
          rs = stmt.executeQuery("SELECT * FROM " + tableIn + " WHERE "+sgl+" Order by ID");
        }

      while (rs.next())
      {
        Vector<String> eq = new Vector<String>();
        eq.add(rs.getString(1)); // eq ID
        eq.add(rs.getString(2)); // ID
        eq.add(rs.getString(3)); // eq name
        eq.add(rs.getString(4)); // available
        eq.add(rs.getString(5)); // quantity
        eq.add(rs.getString(6)); // store
        eqVector.add(eq);
      }
      
      
      
      rs.close();
      stmt.close();
      //  //nb: if you uncomment this line then you will get a general error
      
      return eqVector;
  }
  
   public Vector setEquipmentListTable(String tableIn) throws SQLException
  {
      Vector<Vector<String>>eqListVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
 
      rs = stmt.executeQuery("SELECT * FROM " + tableIn + " Order by ID");
    
      while (rs.next())
      {
        Vector<String> eqList = new Vector<String>();
        eqList.add(rs.getString(1)); // user ID
        eqList.add(rs.getString(2)); // name and surname
        eqList.add(rs.getString(3)); // company id
        eqList.add(rs.getString(4)); // item number
        eqList.add(rs.getString(5)); // cons. descrp.
        eqList.add(rs.getString(6)); // quantity
        eqList.add(rs.getString(7)); // date out
        eqList.add(rs.getString(8)); // location
        eqList.add(rs.getString(9)); // facilitator
        eqList.add(rs.getString(10)); // storeman
        eqListVector.add(eqList);
      }
      
      
      
      rs.close();
      stmt.close();
      
      
      return eqListVector;
  }
  
   public Vector setToolTable(String tableIn) throws SQLException
  {
      Vector<Vector<String>>toolVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
  
      rs = stmt.executeQuery("SELECT * FROM " + tableIn + " Order by ID");

      while (rs.next())
      {
        Vector<String> tool = new Vector<String>();
        tool.add(rs.getString(1)); // tool. ID
        tool.add(rs.getString(2)); // ID
        tool.add(rs.getString(3)); // tool name
        tool.add(rs.getString(4)); // Available
        tool.add(rs.getString(5)); // quantity
        tool.add(rs.getString(6)); // store
        tool.add(rs.getString(7)); // size
        toolVector.add(tool);
      }
      
      
      
      rs.close();
      stmt.close();
      //  //nb: if you uncomment this line then you will get a general error
      
      return toolVector;
  }
   
  public Vector setToolTableSearch(String sgl ,String tableIn) throws SQLException
  {
      Vector<Vector<String>>toolVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
  
      if (sgl.equals(""))
        {
          rs = stmt.executeQuery("SELECT * FROM " + tableIn + " Order by ID");
        }
        else if (!sgl.equals(""))
        {
          rs = stmt.executeQuery("SELECT * FROM " + tableIn + " WHERE "+sgl+" Order by ID");
        }

      while (rs.next())
      {
        Vector<String> tool = new Vector<String>();
        tool.add(rs.getString(1)); // tool. ID
        tool.add(rs.getString(2)); // ID
        tool.add(rs.getString(3)); // tool name
        tool.add(rs.getString(4)); // Available
        tool.add(rs.getString(5)); // quantity
        tool.add(rs.getString(6)); // store
        tool.add(rs.getString(7)); // size
        toolVector.add(tool);
      }
      
      
      
      rs.close();
      stmt.close();
      //  //nb: if you uncomment this line then you will get a general error
      
      return toolVector;
  }  
  
 public Vector setToolListTable(String tableIn) throws SQLException
  {
      Vector<Vector<String>>toolListVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
 
      rs = stmt.executeQuery("SELECT * FROM " + tableIn + " Order by ID");
    
      while (rs.next())
      {
        Vector<String> toolList = new Vector<String>();
        toolList.add(rs.getString(1)); // ID
        toolList.add(rs.getString(2)); // name and surname
        toolList.add(rs.getString(3)); // company id
        toolList.add(rs.getString(4)); // item number
        toolList.add(rs.getString(5)); // tool descrp.
        toolList.add(rs.getString(6)); // quantity
        toolList.add(rs.getString(7)); // date out
        toolList.add(rs.getString(8)); // location
        toolList.add(rs.getString(9)); // facilitator
        toolList.add(rs.getString(10)); // storeman
        toolListVector.add(toolList);
      }
      
      
      
      rs.close();
      stmt.close();
      
      
      return toolListVector;
  } 
   
  public void setListToListItem(int idin, String fieldin, String valuesin, String tableopenin)  throws SQLException
  {
    int id = idin;
    String field = fieldin;
    String values = valuesin;
    String tableopen = tableopenin;
    
    
    
    
    Statement stmt = con.createStatement();
    Statement stmt1 = con.createStatement();
    Statement stmteinf = con.createStatement();

    rs = stmt.executeQuery("SELECT * FROM data_store_01 where ID like " + id);

    ResultSet rs_issue = stmt1.executeQuery("SELECT * FROM " + tableopen);

    rsmd = rs.getMetaData();
    ResultSetMetaData rsmd_issue = rs_issue.getMetaData();

    int clmCnt = rsmd.getColumnCount();
    int clmCntissue = rsmd_issue.getColumnCount();

    String insertfields = "";
    String insertvalues = "";

    rs.next();

    for (int i = 1; i <= clmCnt; i++)
    {
      for (int j = 1; j <= clmCntissue; j++)
      {
        if (!rsmd_issue.getColumnName(j).equals(this.rsmd.getColumnName(i)))
          continue;
        if (insertfields == "")
        {
          insertfields = "`" + rsmd_issue.getColumnName(j) + "`";

          insertvalues = "'" + this.rs.getString(i) + "'";
        }
        else
        {
          insertfields = insertfields + ", `" + rsmd_issue.getColumnName(j) + "`";
          insertvalues = insertvalues + ", '" + this.rs.getString(i) + "'";
        }
      }
    }

    String fieldsToInsert = insertfields + field;
    String valuesToInsert = insertvalues + values;

    stmteinf.addBatch("INSERT INTO " + tableopen + " (" + fieldsToInsert + ") VALUES (" + valuesToInsert + ")");
    stmteinf.executeBatch();

    con.commit();
    rs.close();
    rs_issue.close();
    stmt.close();
    stmt1.close();
    stmteinf.close();
  }

  public void setNewDigitValue(int idin, String digitin, int quantityin, int operatorin) throws SQLException
  {
    int id = idin;
    String digit = digitin;
    int quantity = quantityin;
    int operator = operatorin;
    
    
    Statement stmtcurrent = con.createStatement();
    rs = stmtcurrent.executeQuery("SELECT `" + digit + "` FROM data_store_01 where ID like " + id);
    rs.next();
    int value = rs.getInt(1);

    Statement stmt = con.createStatement();

    if (operator == 0)
    {
      stmt.addBatch("UPDATE data_store_01 SET `" + digit + "` = " + (value - quantity) + " WHERE ID LIKE " + id);
    }
    else
    {
      stmt.addBatch("UPDATE data_store_01 SET `" + digit + "` = " + (value + quantity) + " WHERE ID LIKE " + id);
    }

    stmt.executeBatch();

    this.rs.close();
    stmtcurrent.close();
    stmt.close();
  }

  public void setNewToolQuantity(int idin, int quantityin, int operatorin) throws SQLException
  {
    int id = idin;
    int quantity = quantityin;
    int operator = operatorin;
    
    
    Statement stmtcurrent = con.createStatement();
    rs = stmtcurrent.executeQuery("SELECT `Available` FROM tool_list where `ID` like " + id);
    rs.next();
    int value = rs.getInt("Available");

    Statement stmt = con.createStatement();

    if (operator == 0)
    {
      stmt.addBatch("UPDATE tool_list SET `Available` = " + (value - quantity) + " WHERE `ID` LIKE " + id);
    }
    else
    {
      stmt.addBatch("UPDATE tool_list SET `Available` = " + (value + quantity) + " WHERE `ID` LIKE " + id);
    }
    stmt.executeBatch();

    this.rs.close();
    stmtcurrent.close();
    stmt.close();
  }

  public void setNewConsumableQuantity(int idin, int quantityin, int operatorin) throws SQLException
  {
    int id = idin;
    int quantity = quantityin;
    int operator = operatorin;
    
    
    Statement stmt = con.createStatement();
    Statement stmtcurrent = con.createStatement();
    
    rs = stmtcurrent.executeQuery("SELECT `Available` FROM consumables where `ID` like " + id);
    rs.next();
    int value = rs.getInt("Available");

  

    if (operator == 0)
    {
      stmt.addBatch("UPDATE consumables SET `Available` = " + (value - quantity) + " WHERE `ID` LIKE " + id);
    }
    else
    {
      stmt.addBatch("UPDATE consumables SET `Available` = " + (value + quantity) + " WHERE `ID` LIKE " + id);
    }
    stmt.executeBatch();

    rs.close();
    stmtcurrent.close();
    stmt.close();
    
  }
  
   public void updateIssuedItem(int itemid, String table, int receiveQuant, int quantity) throws SQLException
  {
       Statement stmt = con.createStatement();  
       
       if((quantity - receiveQuant) == 0){
           PreparedStatement pt = con.prepareStatement("DELETE * FROM "+table+" WHERE `Item Number` LIKE "+itemid+""); 
           pt.executeUpdate();
           con.commit();  
           pt.close(); 
       }else{
           stmt.addBatch("UPDATE "+table+" SET `Quantity` = " +(quantity - receiveQuant)+ " WHERE `Item Number` LIKE " + itemid);
           stmt.executeBatch();
           stmt.close(); 
       }
           
          
  }
  
  public void deleteIssuedItem(int itemid, String table) throws SQLException
  {
       int id = itemid;
       //Statement stmt = con.createStatement();
       //rs = stmt.executeQuery("DELETE * FROM "+table+" WHERE `Item Number` LIKE "+id+"");
       //rs.next();
       //int IDout = rs.getInt(1);
       
       PreparedStatement pt = con.prepareStatement("DELETE * FROM "+table+" WHERE `Item Number` LIKE "+id+""); 
       pt.executeUpdate();
       con.commit();  
       pt.close();  
  }

  public void setNewEquipmentQuantity(int idin, int quantityin, int operatorin) throws SQLException
  {
    int id = idin;
    int quantity = quantityin;
    int operator = operatorin;
    
    
    Statement stmt = con.createStatement();  
    Statement stmtcurrent = con.createStatement();
    rs = stmtcurrent.executeQuery("SELECT `Available` FROM equipment_list where `ID` like " + id);
    rs.next();
    int value = rs.getInt("Available");
   

    if (operator == 0)
    {
      stmt.addBatch("UPDATE equipment_list SET `Available` = " + (value - quantity) + " WHERE `ID` LIKE " + id);
    }
    else
    {
      stmt.addBatch("UPDATE equipment_list SET `Available` = " + (value + quantity) + " WHERE `ID` LIKE " + id);
    }
    stmt.executeBatch();

    this.rs.close();
    stmtcurrent.close();
    stmt.close();
  }

  public void setChange(int IDin, String[] fieldsin, String[] valuesin)
    throws SQLException
  {
    int id = IDin;
    String[] fields = fieldsin;
    String[] values = valuesin;
    
    
    Statement stmt = con.createStatement();

    for (int i = 0; i <= 8; i++)
    {
      if (!values[i].equals("##")) {
        stmt.addBatch("UPDATE data_store_01 SET " + fields[i] + " = " + values[i] + " WHERE ID LIKE " + id);
      }
    }
    stmt.executeBatch();
    stmt.close();
    
  }
  
  /**
   * Returns primary key of the table provided
   * @param table
   * @return primary key
   * @throws SQLException 
   */
  public String getPrimaryKey(String table) throws SQLException
  { 
    
    
    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT * FROM "+table);
    rsmd = rs.getMetaData();
    int cols = rsmd.getColumnCount(); 
    String fieldName = "";
    boolean isAutoIncrement;
    
    for(int x = 1;x<=cols;x++){
        isAutoIncrement = rsmd.isAutoIncrement(x);
        if(isAutoIncrement){
           fieldName = rsmd.getColumnName(x);
        }
     } 
    rs.close();
    
    
    return fieldName;    
  }
  
  /**
   * Returns index of a column that is of datetime data type
   * @param table
   * @return index
   * @throws SQLException 
   */
   public int getIndexOfDateColumnType(String table) throws SQLException
  { 
    
    
    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT * FROM "+table);
    rsmd = rs.getMetaData();
    int cols = rsmd.getColumnCount(); 
    String fieldName = "";
    int columnType = 0;
    int columnIndex = 0;
    
    /*
     *       MS Access Data type index
     *   --------------------------------------------------------------------
     *    type  |      4        12      93      -1     8        2       -7
     *   --------------------------------------------------------------------
     *    index |  autonumber  text  datetime  memo  number  currency  yesno  
     *   --------------------------------------------------------------------
     */
    for(int x = 1;x<=cols;x++){
         columnType = rsmd.getColumnType(x);
         if(columnType == 93){  
           fieldName = rsmd.getColumnName(x);
           columnIndex = x;
           System.out.println("Column Name: "+fieldName+" Type: "+columnType+" [datetime] Column Index: "+columnIndex);
         }
     }
      
    rs.close();
    
    
    return columnIndex ;    
  }
  
  public void setDatabaseUpdate(String wherein, String tableopenin, String fieldin, String valuein) throws SQLException
  {
    String where = wherein;
    String tableopen = tableopenin;
    String field = fieldin;
    String value = valuein;
    
    
    Statement stmt = con.createStatement();
    stmt.addBatch("UPDATE " + tableopen + " SET `" + field + "` = " + value + " WHERE " + where);
    stmt.executeBatch();
    stmt.close();
    
  }

  public void setBatch(String batchin) throws SQLException
  {
    String batch = batchin;
    
    
    Statement stmt = con.createStatement();
    
    stmt.addBatch(batch);
    System.out.println(batch);
    stmt.executeBatch();
    //con.commit();
    
    stmt.close();
    
  }

  public int getCurrentStockCheck(int IDin)
    throws SQLException
  {
    int id = IDin;
   
    
    
    Statement stmt = con.createStatement();
  
    this.rs = stmt.executeQuery("SELECT MIN, Current FROM data_store_01 where ID like " + id);
    this.rs.next();
    int check;
  
    if (this.rs.getInt(1) < this.rs.getInt(2))
    {
      check = 0;
    }
    else
    {
      check = 1;
    }
    this.rs.close();
    stmt.close();
    return check;
  }
  
  public int borrowedItems(int IDin)throws SQLException
  {
    int id = IDin;
    int count = 0; 
    
    
    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT COUNT (Description) FROM issue_list_01 WHERE Company_id LIKE "+id+"");
    rs = stmt.getResultSet();
	  
    while(rs.next())
      {
	count = rs.getInt(1);  
      }
	  
    rs.close();
    stmt.close();
    
	  
    return count;
  }
  
   public int issuedItems(int IDin)throws SQLException
  {
    int id = IDin;
    int count = 0; 
    
    
    Statement stmt = con.createStatement();
    
    rs = stmt.executeQuery("SELECT COUNT (`Consumable Description`) FROM consumables_issue_list WHERE company_id LIKE "+id+"");  
    while(rs.next())
      {
	count = count +rs.getInt(1);  
      }
    
    rs = stmt.executeQuery("SELECT COUNT (`Equipment Description`) FROM equipment_issue_list WHERE company_id LIKE "+id+"");  
    while(rs.next())
      {
	count = count + rs.getInt(1);  
      }
    
    rs = stmt.executeQuery("SELECT COUNT (`Tool Description`) FROM tool_issue_list WHERE company_id LIKE "+id+"");  
    while(rs.next())
      {
	count = count + rs.getInt(1);  
      }
	  
    rs.close();
    stmt.close();
    
	  
    return count;
  }
  
   
   public boolean isborrowedItemLess(int locationID,int receiveQuantityIn, String table_name)throws SQLException
  {
    boolean result = false;
    int id = locationID;
    int quantity  = 0;
    int available = 0;
    
    
    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT `Quantity`, `Available` FROM "+table_name+" WHERE ID LIKE "+id);
    rs = stmt.getResultSet();
	  
    while(rs.next())
      {
	quantity  = rs.getInt("Quantity"); 
        available = rs.getInt("Available"); 
      }
    
    if(receiveQuantityIn >= quantity)
    {
        result = false;
    } 
    else
    {
       result = true; 
    }    
	  
    rs.close();
    stmt.close();
    
	  
    return result;
  }
   
   public boolean borrowedItemsComparison(int locationID,int receiveQuantityIn, String table_name)throws SQLException
  {
    boolean result = false;
    int id = locationID;
    int quantity  = 0;
    int available = 0;
    
    
    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT `Quantity`, `Available` FROM "+table_name+" WHERE ID LIKE "+id);
    rs = stmt.getResultSet();
	  
    while(rs.next())
      {
	quantity  = rs.getInt("Quantity"); 
        available = rs.getInt("Available"); 
      }
    
    if(receiveQuantityIn > quantity)
    {
        result = false;
    } 
    else if((receiveQuantityIn + available) <= quantity)
    {
      result = true;
    }
    else
    {
       result = false; 
    }    
	  
    rs.close();
    stmt.close();
    
	  
    return result;
  }

private int ccListSizeCategories() throws SQLException
{   
    int size = 0;
    
    
    
    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT COUNT(catname) FROM category");
    rs = stmt.getResultSet(); 
    
    while(this.rs.next())
    {
	size = rs.getInt(1);  
        //size = rs.getInt("COUNT(CATNAME)");  
    }
	  
    
    return size;
} 

private int listSizeSalesRep() throws SQLException
{   
    int size = 0;

    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT COUNT(firstname) FROM user WHERE roleid = 3");
    rs = stmt.getResultSet(); 
    
    while(this.rs.next())
    {
	size = rs.getInt(1);  
        //size = rs.getInt("COUNT(CATNAME)");  
    }
	  
    
    return size;
} 

private int listSizeLevels() throws SQLException
{   
    int size = 0;
    
    
    
    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT DISTINCT course_level FROM courses");
    rs = stmt.getResultSet(); 
    
    while(this.rs.next())
    {
	size +=1;  
    }
	  
    
    return size;
}

private int listSizeCourse() throws SQLException
{   
    int size = 0;
    
    
    
    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT distinct course_name FROM courses");
    rs = stmt.getResultSet(); 
    
    while(this.rs.next())
    {
	size +=1;  
    }
	  
    
    return size;
} 

public String getLoginDate(int id) throws SQLException
{
    String logIn = "";
    
    
    
    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT logindate FROM userlog WHERE userid ="+id);
    rs = stmt.getResultSet(); 
    
    while(this.rs.next())
    {
	logIn = rs.getString("logindate");  
    }
    
    return logIn;
}

private int ccListSizeStoreman() throws SQLException
{   
    int size = 0;
    
    
    
    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT COUNT(name) FROM storeman AS Expr1000");
    rs = stmt.getResultSet(); 
    
    while(this.rs.next())
    {
	size = rs.getInt("Expr1000");  
    }
	  
    
    return size;
} 
 
public int getLearnersFacilitatorsID(int learnerID) throws SQLException
{   
    int facilitator = 0;
    
    
    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT facilitator_id FROM learner_details WHERE learner_id LIKE "+learnerID);
    rs = stmt.getResultSet(); 
    
    while(this.rs.next())
    {
	facilitator = rs.getInt("facilitator_id");  
    }
	  
    return facilitator;
}

private int locationSize() throws SQLException
{   
    int size = 0;
    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT COUNT(locname) FROM location AS locsize");
    rs = stmt.getResultSet(); 
    
    while(this.rs.next())
    {
	size = rs.getInt("count(locname)");  
    }
    rs.close();
    return size;
}
//get location list
public String [] getLocation() throws SQLException
{      String[] loc = new String[locationSize()];  
       Statement stmt = con.createStatement();
       rs  = stmt.executeQuery("SELECT locname FROM location WHERE loc_state=1 ORDER BY locname ASC");
       rs  = stmt.getResultSet();
       int rowCount = 0;        
       while( rs.next())
          {    
               String location = rs.getString("locname");
               loc[rowCount] = location;   
               rowCount++;               
          }                             
       rs.close();  
       return loc; 
}

/**
 * Returns list of STORES and an optional extra field at the top or bottom 
 * of the list specified by the user
 * @param extra
 * @param extra_val
 * @param position
 * @return STORE LIST
 * @throws SQLException 
 */
public ArrayList<String> getStore(boolean extra,String extra_val,int position) throws SQLException
{
        ArrayList<String> store = new ArrayList<String>();
        Statement stmt = con.createStatement();
        rs  = stmt.executeQuery("SELECT store_name FROM store_list");
        rs  = stmt.getResultSet();

        while( rs.next())
           {
                String location = rs.getString("store_name");
                if(location.isEmpty()){     
                }else{
                  store.add(location);          
                }
           }
        
        int last = store.indexOf(store.get(store.size()- 1));
        if(extra){
           if(position == 0){ 
             store.add(0, extra_val); 
           }else{
               store.add(last, extra_val); 
           } 
        }else{
           //don't add anything 
        }

        rs.close();
		  
	return store; 
}



/**
 *  Returns full name of the facilitator by passing the company id of the facilitator
 * @param faci_id
 * @return facilitator full name
 * @throws SQLException 
 */
public String getFacilitator(int faci_id) throws SQLException
{ 
      String facilitator = "";
      String name ="" , surname = "";     
        
        
        Statement stmt = con.createStatement();

        rs  = stmt.executeQuery("SELECT  `name`, `surname` FROM facilitator WHERE company_id LIKE "+faci_id);
        rs  = stmt.getResultSet();
        int rowCount = 0;
        while( rs.next())
           {
             name = rs.getString("name");
             surname = rs.getString("surname");            
           }
         facilitator = name+" "+surname;                
        rs.close();
        
		  
	return facilitator; 
}

/**
 *  Returns a company id of the facilitator by passing the name of the facilitator
 * @param faci_id
 * @return facilitator company id
 * @throws SQLException 
 */
public int getFacilitator(String faci_name) throws SQLException
{ 
        int companyid = 0;
        String name ="" , surname = "";     
        String splitName [] = faci_name.split(" ");
        name = splitName[0];
        surname = splitName[1];
        
        
        Statement stmt = con.createStatement();

        rs  = stmt.executeQuery("SELECT `company_id` FROM facilitator WHERE name LIKE '"+name+"' AND surname LIKE '"+surname+"'");
        rs  = stmt.getResultSet();
        while( rs.next())
           {
             companyid = rs.getInt("company_id");
           }
         
        rs.close();
        
		  
	return companyid; 
}

/**
 * Assings a facilitator to a learner by passing 
 * the learner and facilitator's company ids and 
 * also change a learner's facilitator
 * @param coid
 * @param faci_coid 
 * @param update
 */
public  void assignFacilitatorToLearner(int coid, int faci_coid, boolean update) throws SQLException {
  
  Statement st = con.createStatement();
  Statement st2 = con.createStatement();
    
  if(update){
      st2.addBatch("UPDATE learner_details SET `facilitator_id` = " +faci_coid+ " WHERE learner_id LIKE " +coid);
      st2.executeBatch();  
  }else{
       st.executeUpdate("INSERT INTO learner_details (`learner_id`,`facilitator_id`) VALUES("+coid+","+faci_coid+")");
  }
  
  st.close();
  st2.close();
  
} 

public String [] getListCategories(int id) throws SQLException
{ 
      String[] ListCategories;
      ListCategories = new String[ccListSizeCategories()];
     
        
        
        Statement stmt = con.createStatement();

        rs  = stmt.executeQuery("SELECT  `name`, `surname` FROM facilitator ");
        rs  = stmt.getResultSet();
        int rowCount = 0;
        while( rs.next())
           {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String fullname = name+" "+surname;
                ListCategories[rowCount] = fullname;   
                rowCount++;               
           }
                
        rs.close();
        
		  
	return ListCategories; 
}

public String [] getListLevels() throws SQLException
{ 
      String [] ListLevels;
      ListLevels = new String[listSizeLevels()];
     
        
        
        Statement stmt = con.createStatement();

        rs  = stmt.executeQuery("SELECT DISTINCT `course_level` FROM courses");
        rs  = stmt.getResultSet();
        int rowCount = 0;
        while( rs.next())
           {
                int level = rs.getInt("course_level");
                ListLevels[rowCount] = ""+level;   
                rowCount++;               
           }
                
        rs.close();
        
		  
	return ListLevels; 
}

public String [] getListCourse() throws SQLException
{ 
      String[] ListCourse;
      ListCourse = new String[listSizeCourse()];
     
        
        
        Statement stmt = con.createStatement();

        rs  = stmt.executeQuery("SELECT DISTINCT `course_name` FROM courses ");
        rs  = stmt.getResultSet();
        int rowCount = 0;
        while( rs.next())
           {
                String name = rs.getString("course_name");
                ListCourse[rowCount] = name;   
                rowCount++;               
           }
                
        rs.close();
        
		  
	return ListCourse; 
}

public String [] getCCListFacilitator() throws SQLException
{ 
      String[] ccListFacilitator;
      ccListFacilitator = new String[ccListSizeCategories()];
     
        
        
        Statement stmt = con.createStatement();

        rs  = stmt.executeQuery("SELECT  `e_mail` FROM facilitator ");
        rs  = stmt.getResultSet();
        int rowCount = 0;
        while( rs.next())
           {
                String email = rs.getString("e_mail");
                ccListFacilitator[rowCount] = email;   
                rowCount++;               
           }
                
        rs.close();
        
		  
	return ccListFacilitator; 
}
  
  public ArrayList<String> getMsgList()throws SQLException{
      
      int count = newMsgCount();
      int x = 0;
      ArrayList<String> msgList = new ArrayList<String>();
      
	  
          
          Statement stmt = con.createStatement();

          rs = stmt.executeQuery("SELECT message FROM message GROUP BY message ORDER BY Max(msgdate) DESC");
          rs = stmt.getResultSet();
	  
          //for(int x = Math.abs(count - count); x < count;x--)
            //  {
                while(rs.next())
                {  
                  msgList.add(rs.getString("message"));  
                  x+=1;
                }
	      //}
	  rs.close();
	  stmt.close();
	  
	 
        return msgList;  
  }
  //get notice message for the notice board
  public String showMsg() throws SQLException
  {	  
	  String msgOut = "";
	  
          
          Statement stmt = con.createStatement();

	  //rs = stmt.executeQuery("SELECT Notification FROM notice_board WHERE Notice_Date > now()- 1 AND Notice_Date < now() ");
	  //rs = stmt.executeQuery("SELECT Notification FROM notice_board WHERE Notice_Date >= now() ");
          rs = stmt.executeQuery("SELECT TOP 1 Notification FROM notice_board GROUP BY Notification ORDER BY Max(Notice_Date) DESC");
          rs = stmt.getResultSet();
	  
	  while(rs.next())
	  {
		msgOut = rs.getString("Notification");  
	  }
	  
	  rs.close();
	  stmt.close();
	  
	  
	  return msgOut;
  }
  
  public String getNetworkName() throws SQLException
  {	  
	  String network_name = "";
	  
          
          Statement stmt = con.createStatement();
          rs = stmt.executeQuery("SELECT network_interface_name FROM network_config");
          rs = stmt.getResultSet();
	  
	  while(rs.next())
	  {
		network_name = rs.getString("network_interface_name");  
	  }
	  
	  rs.close();
	  stmt.close();
	  
	  
	  return network_name;
  }
  
  //Icon to display due to available notice message
  public int newMsgCount() throws SQLException
  {	  
	  int countNewMsgs = 0;
	  
          
          Statement stmt = con.createStatement();
  
	  //rs = stmt.executeQuery("SELECT COUNT(Notification) FROM notice_board AS Expr1000 WHERE Notice_date = date() ");
          //rs = stmt.executeQuery("SELECT COUNT(Notification) FROM notice_board AS Expr1000 WHERE Notice_Date >= Date()-1 AND Notice_Date <= Now()");
          rs = stmt.executeQuery("SELECT COUNT(*) FROM message");
	  rs = stmt.getResultSet();
	  
	  while(rs.next())
	  {
		countNewMsgs = rs.getInt("count(*)");  
	  }
	  
	  rs.close();
	  stmt.close();
	  
	  
	  return countNewMsgs;
  }
  
  public int msgCount() throws SQLException
  {	  
	  int countMsgs = 0;
	  
	  
          
          Statement stmt = con.createStatement();
	  rs = stmt.executeQuery("SELECT COUNT(*) FROM message");
	  rs = stmt.getResultSet();
	  
	  while(this.rs.next())
	  {
		countMsgs = this.rs.getInt("count(*)");  
	  }
	  
	  rs.close();
	  stmt.close();
	  
	  
	  return countMsgs;
  }
  
  //Get new notification(s) to display
//Icon to display due to available notice message
  public String [] newNotification(int msgCount, int decider) throws SQLException
  {	  
	  
      String [] ArrayMsg = {};
      ArrayMsg = new String[msgCount];
      int count = 0;
      
	
        
        Statement stmt = con.createStatement();
	  
	  //Decider = Boolean, if 1 then get latest msgs else if 0 get all msgs
	  if(decider == 1)
	    {  
	       this.rs = stmt.executeQuery("SELECT message FROM message WHERE msgate = date() ");
	       this.rs = stmt.getResultSet();
	    }
	  else
	    {
		  this.rs = stmt.executeQuery("SELECT message FROM message");
	      this.rs = stmt.getResultSet();
	    }
	  
	  if(msgCount >= 1)
	  {	  
	    while(this.rs.next())
	    {
	    	String msg = this.rs.getString("message");
	    	ArrayMsg[count] = msg;
	    	count++;
	    }
	  }
	  else
	  {
		  ArrayMsg[count] = "";
	  }
	    
	  this.rs.close();
	  stmt.close();
	  
	  return ArrayMsg;
  }
  
  public String compareQuanty(int ID2, String quantity) 
  {  
      String quant = quantity;
      
       
      
      return quantity;
  }
	
	
  public int getQuantityCheck(int IDin, int quantityin, String fieldin) throws SQLException
  {
    int id = IDin;
    int quantity = quantityin;
    String field = fieldin;
    
    
    
    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT `" + field + "` FROM data_store_01 where ID like " + id);
    rs.next();
    int check;

    if (rs.getInt(1) < quantity)
    {
      check = 0;
    }
    else
    {
      check = 1;
    }
    rs.close();
    stmt.close();
    
    
    return check;
  }
  
   public int getQuantity(int IDin) throws SQLException
  {
    int id = IDin;
    
    
    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT `Current` FROM data_store_01 where ID like " + id);
    rs.next();
    int result = rs.getInt(1);
    rs.close();
    stmt.close();
    
    
    return result;
  }
   
   public String checkItemData(int IDin, String fieldIn, String tableIn) throws SQLException
  {
    int id = IDin;
    String string = fieldIn;
    
    
    
    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT " + string + " FROM "+tableIn+" where ID like " + id);
    rs.next();
    String stringout = rs.getString(1);

    rs.close();
    stmt.close();
    

    return stringout;
  }
   
  
  public String getCheckData(int IDin, String stringin) throws SQLException
  {
    int id = IDin;
    String string = stringin;

    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT " + string + " FROM ITEMINVENTORY WHERE itemid LIKE " + id);
    rs.next();
    String stringout = rs.getString(1);

    rs.close();
    stmt.close();
    
    return stringout;
  }
  
  public String getQuantityForItem(int IDin,String tableIn) throws SQLException
  {
        int id = IDin;
        String table = tableIn;

        
        
        Statement stmt = con.createStatement();
        rs = stmt.executeQuery("SELECT `Quantity` FROM "+table+" where ID like " + id);
        rs.next();
        String stringout = rs.getString(1);

        rs.close();
        stmt.close();
        

        return stringout; 
      
      
  }

  public String getItemDescription(int item_id) throws SQLException
  {
    int descrip_id = item_id;
  
    
    
    Statement stmt = con.createStatement();  
    rs = stmt.executeQuery("SELECT Description FROM data_store_01 where `ID` like '" + descrip_id + "'");
    rs.next();
    String ItemDescription = this.rs.getString("Description");

    this.rs.close();
    stmt.close();
    

    return ItemDescription;
  }

  public String getStore(String location) throws SQLException
  {
    String loc = location;
    String storeOut = "";
    
    
    Statement stmt = con.createStatement();

    rs = stmt.executeQuery("SELECT `Store` FROM data_store_01 where `Location / Bin` like '" + loc + "'");
    rs = stmt.getResultSet();
	  
    while(rs.next())
      {
         storeOut = rs.getString("Store");
      }

    rs.close();
    stmt.close();
    

    return storeOut;
  }
 
  public void deleteLearner(int compid) throws SQLException
  {
        int id = compid;
        PreparedStatement pt = con.prepareStatement("DELETE * FROM learner_list WHERE `company_id` = "+id); 
        pt.executeUpdate();
        con.commit();  
        pt.close(); 
  }   
  
  
  public int getID(String bcodeid) throws SQLException
  {  
    int bcode = Integer.parseInt(bcodeid);
    Statement stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT itemid FROM ITEM WHERE `itembarcodeid` LIKE '" + bcode + "'");
    rs.next();
    int IDout = rs.getInt(1);
    con.commit(); 
    
    rs.close();
    stmt.close();
    
    return IDout;
  }
  
  public void deleteRow(int idin, String tablename) throws SQLException
  {
  	int id = idin;
  	String tableToUpdate = tablename;
  	
        
        Statement stmt = con.createStatement();
        rs = stmt.executeQuery("DELETE FROM "+tableToUpdate+" WHERE `Item Number` LIKE "+id+"");
        rs.next();
        int IDout = rs.getInt(1);
        con.commit(); 

        rs.close();
        stmt.close();
        
  	
  }
  

  public int getLocationTest(String sgl_locationin, String tableopenin) throws SQLException
  {
    String sgl_location = sgl_locationin;
    String tableopen = tableopenin;
    
    
    Statement stmt = con.createStatement();

    rs = stmt.executeQuery("SELECT Count (*)FROM " + tableopen + " where locname like '" + sgl_location + "'");

    rs.next();
    int out = rs.getInt(1);

    rs.close();
    stmt.close();
    

    return out;
  }

  public int getOrderNumberCheck(String ord_numbin) throws SQLException
  {
    String ord_numb = ord_numbin;
    
    
    Statement stmt = con.createStatement();

    rs = stmt.executeQuery("SELECT Count (*)FROM order_list where `Order Number` like '" + ord_numb + "'");

    rs.next();
    int out = rs.getInt(1);

    rs.close();
    stmt.close();
    

    return out;
  }

  public int getInt(String sql_stringin) throws SQLException
  {
    String sgl_string = sql_stringin;
    
    
    Statement stmt = con.createStatement();

    rs = stmt.executeQuery(sgl_string);
    rs.next();
    int value = rs.getInt(1);

    rs.close();
    stmt.close();
    

    return value;
  }

  public String getString(String sql_stringin) throws SQLException
  {
    String sgl_string = sql_stringin;
    
    
    Statement stmt = con.createStatement();

    rs = stmt.executeQuery(sgl_string);
    rs.next();
    String value = this.rs.getString(1);

    rs.close();
    stmt.close();
    

    return value;
  }
  
   
  public int getIssuedItems(String companyid) throws SQLException
  {
       
        
       Statement stmt = con.createStatement();

        rs = stmt.executeQuery("SELECT `Location/Bin` FROM issue_list_01 WHERE Company_id='"+companyid+ "'");
        rs.next();
        int value = rs.getInt("Expr1");

        rs.close();
        stmt.close();
        
        return 0;
  }         
  
  public int getInteger(String sql_stringin) throws SQLException
  {
    String sgl_string = sql_stringin;
    
    
    Statement stmt = con.createStatement();

    this.rs = stmt.executeQuery(sgl_string);
    this.rs.next();
    int value = this.rs.getInt("Available");

    rs.close();
    stmt.close();
    
    return value;
  }
  
  public int getUserID(String user) throws SQLException
  {      
      String username = user;
      
      
      Statement st = con.createStatement();

      rs = st.executeQuery("SELECT id FROM user WHERE username='" + username + "'");
      rs.next();

      int id = rs.getInt("id"); 	
	    
      rs.close();
      st.close();
      
      
     return id;
  }
  
  /**
   * Returns a userid by passing a username
   * @param user
   * @return user id
   * @throws SQLException 
   */
  public int get_user_id(String user) throws SQLException
  {      
      String username = user;
      Statement st = con.createStatement();

      rs = st.executeQuery("SELECT userid FROM user WHERE username='" + username + "'");
      rs.next();

      int id = rs.getInt("userid"); 	
	    
      rs.close();
      st.close();
      
      
     return id;
  }
  
  public String getRole(int roleid) throws SQLException
  {
      String userRole = "";
      
      
      
      Statement st = con.createStatement();

      rs = st.executeQuery("SELECT rolename FROM role WHERE roleid LIKE " + roleid + "");
      rs.next();

      userRole = rs.getString("rolename"); 	
	    
      rs.close();
      st.close();
      
      
      
      return userRole; 
  }        
          
  public String tempPasswordVerification(int user_id) throws SQLException
  {
    String pword="";  

    Statement st = con.createStatement();

    rs = st.executeQuery("SELECT temppassword FROM temppassword WHERE userid =" +user_id);
    if(!rs.next())
    {
        pword = null;
    }
    else{
        pword = rs.getString("temppassword");   
    }

    
     
    rs.close();
    st.close();
    
    
    return pword;
  }
  
  public String userVerification(String user, String passwd) throws SQLException
  {
	  
    String status = "";
    String username = user;
    String password = passwd;
    
    
    Statement st = con.createStatement();

    //rs = st.executeQuery("SELECT User_id, Username, Password FROM users WHERE Username='" + username + "' AND Password='" + password + "'");
    rs = st.executeQuery("SELECT * FROM user WHERE username='" + username + "'");
    rs.next();

    String unameUpperCase = rs.getString("username");
    String uname = unameUpperCase.toLowerCase();
    String pword = rs.getString("password");
    int    id    = rs.getInt("userid");
    

    if ((uname == null) && (pword == null))
    {
      status = "notexisting";
    }
    else
    {
   
      if ((uname.equals(username)) && (pword.equals(password)))
      {
        status = "exist";
      }
      else
      {
  
        if ((!uname.equals(username)) && (pword.equals(password)))
        {
          status = "passwordtrue";
        }
        else if((uname.equals(username)) && (!pword.equals(password))) 
        {
          status = "usernametrue";          
        }
      }
    }
     
    rs.close();
    st.close();
    
    
    return status;
  }
  
  /**
   * Return details of user in an array form
   * @param userid
   * @return
   * @throws SQLException 
   */
  public String [] getUserProfile(int userid) throws SQLException
  {
	  
    String userDetails [] = new String[14];
    Statement st = con.createStatement();

    rs = st.executeQuery("SELECT * FROM user WHERE userid LIKE "+userid);
    rs.next();

    int user_id  = rs.getInt("userid");
    String uname = rs.getString("username");
    String fname = rs.getString("firstname");
    String lname = rs.getString("lastname");
    String email = rs.getString("email");
    String hometel = rs.getString("hometel");
    String cell = rs.getString("mobile");
    int addressid = rs.getInt("addressid");
    int roleid = rs.getInt("roleid");
    int imageid = rs.getInt("imageid");
    int state = rs.getInt("user_state");
    String dcreated = rs.getString("datecreated");
    String dmodified = rs.getString("datemodified");
    String enddate  = rs.getString("enddate");
       
    userDetails[0]  = ""+user_id;
    userDetails[1]  = uname;
    userDetails[2]  = fname;     
    userDetails[3]  = lname;
    userDetails[4]  = email;
    userDetails[5]  = hometel;
    userDetails[6]  = cell;
    userDetails[7]  = ""+addressid;
    userDetails[8]  = ""+roleid;
    userDetails[9]  = ""+imageid;
    userDetails[10] = ""+state;
    userDetails[11] = dcreated;
    userDetails[12] = dmodified;
    userDetails[13] = enddate;
    
    rs.close();
    st.close();
    
    
    return userDetails;
  }
  
  public String getName(int idin) throws SQLException
  {         
            String name = "";
	    
                
            Statement st = con.createStatement();
	    rs = st.executeQuery("SELECT name FROM learner_list WHERE learner_id = "+idin+""); 	
	    rs = st.getResultSet();
	    
            while(rs.next())
            {
             name = rs.getString("name");  
            }
	    rs.close();
	    st.close();
	    
            
	    return name;
  }
  
  public String getSurname(int idin) throws SQLException
  {         
            String surname = "";
	    
            
            Statement st = con.createStatement();
	    rs = st.executeQuery("SELECT `surname` FROM learner_list WHERE learner_id ="+idin+"");
            rs = st.getResultSet();
                           
            while(rs.next())
            {
	     surname = rs.getString("surname");  
            }
            
	    rs.close();
	    st.close();
	    
            
	    return surname;
  }
  
  public String getGroup(int idin) throws SQLException
  {         
            String group = "";
            
                
	    Statement st = con.createStatement();
            rs = st.executeQuery("SELECT group FROM learner_list WHERE `learner_id`="+idin+""); 	
	    rs = st.getResultSet();
            
            while(rs.next())
            {
              group = rs.getString("group");     
            }
	     
	    rs.close();
	    st.close();
	    
            
	    return group;
  }
  
    public String getEmail(int idin) throws SQLException
  {         
            String email = "";
            
                
	    Statement st = con.createStatement();
            rs = st.executeQuery("SELECT e_mail FROM learner_list WHERE `learner_id`="+idin+""); 	
	    rs = st.getResultSet();
            
            while(rs.next())
            {
              email = rs.getString("e_mail");     
            }
	     
	    rs.close();
	    st.close();
	    
            
	    return email;
  }
  
  public int getLevel(int idin) throws SQLException
  {
	    int grouplevel = 0;
	    
                	
	    Statement st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM learner_list WHERE `learner_id`="+idin+""); 	
	    rs = st.getResultSet();	
	    
            while(rs.next())
            {
	     grouplevel = rs.getInt("level");  
            }
            
	    rs.close();
	    st.close();
            
	    
	    return grouplevel;
  }
  
  /**
   *  Returns company id of a learner [learner_list]
   * @param idin
   * @return learner company id
   * @throws SQLException 
   */
  public int getCOID(int idin) throws SQLException
  { 
            
                	    	
	    Statement st = con.createStatement();
            rs = st.executeQuery("SELECT company_id FROM learner_list WHERE `learner_id`="+idin+""); 	
	    rs.next();	
	    int compid = rs.getInt("company_id");  
	     
	    rs.close();
	    st.close();
            
	    
	    return compid;
  }
  
  /**
   * Returns company id of learner by passing in a full name of the learner 
   * @param learner_full_name
   * @return company id
   * @throws SQLException 
   */
  public int getCOID(String learner_full_name) throws SQLException
  { 
            String name = "", surname = "";
            String splitName [] = learner_full_name.split(" "); 
            name = splitName[0];
            surname = splitName[1];
            
                	    	
	    Statement st = con.createStatement();
            rs = st.executeQuery("SELECT company_id FROM learner_list WHERE `name` = '"+name+"' AND `surname` = '"+surname+"'"); 	
	    rs.next();	
	    int compid = rs.getInt("company_id");  
	     
	    rs.close();
	    st.close();
            
	    
	    return compid;
  }
  /**
   * Returns learner id from the databse table [learner_list]
   * @param compid
   * @return learner id
   * @throws SQLException 
   */
  public int getID(int compid) throws SQLException
  {
	    
                	
	    Statement st = con.createStatement();
            rs = st.executeQuery("SELECT learner_id FROM learner_list WHERE `company_id`="+compid+""); 	
	    rs.next();	
	    int id = rs.getInt("learner_id");  
	     
	    rs.close();
	    st.close();
            
	    
	    return id;
  }
  
  public void updateLearnerTable(String batchin) throws SQLException
  {
	  String batch = batchin;
          
          
	  Statement stmt = con.createStatement();
	  stmt.addBatch(batch);
	  System.out.println(batch);
	  stmt.executeBatch();
	  stmt.close();
          
	  
	  
  }
  
  
  public void changeGroupLevel(int [] learnerIDs, int levelin, int userid) throws SQLException
  {
	
            	    	 
        Statement st = con.createStatement();
        Statement st2 = con.createStatement();
        
	int [] ids = learnerIDs;
	int new_level = levelin;
	int user = userid;
	int x;
	
	String changedate;
	SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	Date currentdate = new Date();
	changedate = formatter2.format(currentdate);

        
        for(x = 0;x < ids.length;x++)
        {   
          //get learner data for the selected id and insert to a history table for record purposes   
          st.executeUpdate("INSERT INTO learner_list_history (`learner_id`,`name`,`surname`,`company_id`,`group`,`level`,`change_date`,`changedBy`)" +
    	              	     "VALUES("+ids[x]+",'"+getName(ids[x])+"','"+getSurname(ids[x])+"',"+getCOID(ids[x])+",'"+getGroup(ids[x])+"',"+new_level+",'"+changedate+"',"+user+")");	

          //update group levels in learner_list table
          st2.addBatch("UPDATE learner_list SET `level` = " + new_level + " WHERE learner_id LIKE " + ids[x]);
          st2.executeBatch();  
        }   

        JOptionPane.showMessageDialog(null,"Complete!","Successfully Changed Level",1);

        st.close();
        st2.close();
        
    
  }
   
  /**
   * Checks if the provided category exists.
   * @param category
   * @return true or false if the category doesn't exist
   * @throws SQLException 
   */
   public boolean getCategory(String category) throws SQLException {
        
       boolean status = true;
        Statement st = con.createStatement();
        rs = st.executeQuery("SELECT catname FROM category WHERE `catname` ='"+category+"'"); 	
	if(!rs.next())
         {
           status = false;
         }

	rs.close();
	st.close();
        
        return status;
  }
  
    
  public boolean isSet() throws SQLException
  {
      Statement st = con.createStatement();
      boolean isSet = false;
      rs = st.executeQuery("SELECT userset FROM metadata"); 	
      rs.next();
      int userset = rs.getInt("userset");  
      if(userset == 1){
          isSet = true;
      }else{
          isSet = false;
      }
      rs.close();
      st.close();
      return isSet;
  }
  
  /**
   * close database connection
   * @throws SQLException 
   */
  public void setCloseDatabase() throws SQLException
  {
       this.con.close();
  }

 

}