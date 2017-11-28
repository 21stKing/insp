
package inventory_system;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author tebogo
 * 
 */
public class DB
{
  private Connection con;
  private ResultSet rs;
  private ResultSetMetaData rsmd;
  private DatabaseMetaData dbmd;
  private String path;
  private int connectionMode;
  private final int offline = 0;
  private final int online = 1;
  
  /**
  * Load Database Driver. Driver must already be in the classpath 
  * @throws Exception 
  */
  public DB() throws Exception{
      try{
         Class.forName("com.mysql.jdbc.Driver").newInstance();
      }catch(ClassNotFoundException e){
          throw new RuntimeException("Cannot find MySQL Connector Driver in the classpath!", e);
      }
  }
  
  /**
   * Database connection mode. Online connection determined by integer 1 and Offline connection
   * determined by integer 0.
   * @param connectionmode
   * @throws Exception 
   */
  public DB(int connectionmode)throws Exception{
    connectionMode = connectionmode;
    switch (connectionMode){
        case 0:     //local connection. most probably localhost
        //Class.forName("com.mysql.jdbc.Driver").newInstance();  //no need to load driver everytime you establish a connection, only oones is enough
        path = "jdbc:mysql://localhost:3306/dimdb?" ;
        con = DriverManager.getConnection(path+"user=admin&password=admin123"); 
        break;
        case 1:     //remote connection
        //Class.forName("com.mysql.jdbc.Driver").newInstance();    
        path = "jdbc:mysql://kinotech.co.za:3306/kinotech_dimdb?" ; 
        con = DriverManager.getConnection(path+"user=kinotech_admin&password=admin@Kinotech2016");  
        //con = DriverManager.getConnection(path, "kinotech_admin", "21st_King079*");
        break;    
    }
  }
  
  protected void setCommit(boolean state) throws SQLException{
          con.setAutoCommit(state);
  }
  
  protected void setRollback() throws SQLException{
      con.rollback();
  }
  
  protected void setConnectionMode(int mode){
      connectionMode =  mode; 
  }
  
  /****************************************************************************
   *                PERFORM PRE-DEFINED SQL STATEMENTS
   *     ------------------------------------------------------------------
   * The purpose of the pre-defined  sql statements is to provide raw data based
   * on the parameters supplied. 
   * This can be used by vectors, array lists and objects
   * 
   ***************************************************************************/
  
  /**
   * Database table name list
   * @return list of db tables
   * @throws SQLException 
   */
  public ArrayList<String> get_tables() throws SQLException{
    ArrayList<String> tables = new ArrayList<String>();
    int x = 1;
    String schema= "";
    DatabaseMetaData md = con.getMetaData();
    rs = md.getTables(null, null, "%", null );

     while (rs.next())
      { 
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
  
   /**
   * Get all records of a table in the database for a specified table name
   * @param tablename
   * @return table data
   * @throws SQLException 
   */
  public Vector get_table_records(String tablename) throws SQLException {
      Vector<Vector<String>>tableVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
  
      rs = stmt.executeQuery("SELECT * FROM " + tablename);
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
  
  public Vector get_table_search_results(String table, String condition) throws SQLException{
      Vector<Vector<Object>> searchVector = new Vector<Vector<Object>>();
      Statement stmt = con.createStatement();
 
      if (condition.equals(""))
       {
         rs = stmt.executeQuery("SELECT * FROM "+table);
       }
       else if (!condition.equals(""))
       {
         rs = stmt.executeQuery("SELECT * FROM "+table+" WHERE "+condition);
       }
      
      while (rs.next())
      {
        Vector<Object> search = new Vector<Object>();
        search.add(rs.getString(1)); // ID
        search.add(rs.getString(2)); // code
        search.add(rs.getString(3)); // description
        search.add(rs.getString(4)); // state
        search.add(rs.getString(5)); // date created
        search.add(rs.getString(6)); // date modified
        search.add(rs.getString(7)); // end date
        searchVector.add(search);
      }
      
      rs.close();
      stmt.close();

      return searchVector;
  }
  
  /**
   * Insert values into table specifying table name, field name(s) and value(s)
   * @param table
   * @param fields
   * @param values
   * @throws SQLException 
   */
  public void insert_table_record(String table, String fields, String values)throws SQLException {
      Statement stmt = con.createStatement();
      String sql = "INSERT INTO " + table + " (" + fields + ") VALUES (" + values + ")";
      stmt.executeUpdate(sql);
      stmt.close();

  }
  
    /**
   * Updates table record with given field name, value, condition and cond_val
   * @param table
   * @param field
   * @param value
   * @param condition
   * @param cond_val
     * @return 1 if success 0 if failed
   * @throws SQLException 
   */
  public int update_table_record(String table, String field, String value, String condition,String cond_val)throws SQLException {
      Statement stmt = con.createStatement();
      String sql = "UPDATE " + table + " SET "+field+" = "+value+" WHERE "+condition+" LIKE "+cond_val+"";
      int updated = stmt.executeUpdate(sql);
      stmt.close();
      return updated;
  }
  
   /**
   * Get table records based on condition
   * @param tablename
   * @param criteria
   * @return table data
   * @throws SQLException 
   */
  public Vector get_table_records_sql(String tablename, String criteria) throws SQLException {
      Vector<Vector<String>>tableVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
  
      rs = stmt.executeQuery("SELECT * FROM " + tablename + " WHERE " + criteria);
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
   * Get table records based on condition and table fields 
   * @param tablename
   * @param fields
   * @param condition
   * @return table data
   * @throws SQLException 
   */
  public Vector get_table_record_sql(String tablename, String fields, String condition) throws SQLException {
      Vector<Vector<String>>tableVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
  
      rs = stmt.executeQuery("SELECT " +fields+ " FROM " + tablename + " WHERE " + condition);
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
  
  
  public int get_field_value(String tablename, String field, String condition) throws SQLException{
      int value = 0;
      Statement stmt = con.createStatement();
      
      rs = stmt.executeQuery("SELECT "+field+" FROM "+tablename+" WHERE "+condition);
      rs  = stmt.getResultSet();

      while( rs.next())
      {
        value = rs.getInt(field); 
      }

      return value;
  }
  
  /**
   * Get the last record id (auto incremented field) of the table
   * @param table
   * @param fieldid
   * @return last record id
   * @throws SQLException 
   */
  public int get_last_record(String table, String fieldid) throws SQLException{
      int id = 0;
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT MAX("+fieldid+") FROM "+table+""); 
      rs  = stmt.getResultSet();

      while( rs.next())
      {
        id = rs.getInt("MAX("+fieldid+")"); 
      }
      return id;
  }
  
  /**
   * Runs SQL statement provided and returns a vector table
   * @param sql
   * @return vector table
   * @throws SQLException 
   */
   public Vector run_sql(String sql) throws SQLException {
      Vector<Vector<String>>tableVector = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
  
      rs = stmt.executeQuery(sql);
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
   * Executes SQL statement provided 
   * @param sql
   * @return true if success where found and/or success or false if no results found and/or failed
   * @throws SQLException 
   */
   public boolean executeSQL(String sql) throws SQLException {
      boolean success; 
      Statement stmt = con.createStatement();
      success = stmt.execute(sql); //con.commit();  //Note: Commented out con.commit() because we are running sql transactions. We set autocommit to false
      stmt.close();
      return success;
  }
  
   public void addBatch(String sql) throws SQLException{   

      Statement stmt = con.createStatement();
      stmt.addBatch(sql);
      stmt.executeBatch();
      stmt.close();
   }
   
   /**
    * Creates a list of a table field and an optional extra field at the top or bottom 
    * of the list. Best for drop down list.
    * @param extra
    * @param value
    * @param position
    * @return list (array list)
    * @throws SQLException 
    */
   public ArrayList<String> getList(String fieldname, String table,boolean extra,String value,int position) throws SQLException   {
       
           ArrayList<String> list = new ArrayList<String>();
           Statement stmt = con.createStatement();
           rs  = stmt.executeQuery("SELECT "+fieldname+" FROM "+table);
           rs  = stmt.getResultSet();

           while( rs.next())
              {
                   String location = rs.getString(fieldname);
                   if(location.isEmpty()){     
                   }else{
                     list.add(location);          
                   }
              }

           int last = list.indexOf(list.get(list.size()- 1));
           if(extra){
              if(position == 0){ 
                list.add(0, value); 
              }else{
                  list.add(last, value); 
              } 
           }else{
              //don't add anything 
           }

           rs.close();
           stmt.close();
           
           return list; 
   }
   
   
   
  /****************************************************************************
   *                PERFORM SYSTEM DEFINED SQL STATEMENTS
   *     ------------------------------------------------------------------
   * The purpose of the system defined sql statements is to provide structured data 
   * from different tables
   * Example: 
   * 
   * Getting an items complete information like item description, manufacture, location
   * image, bar code, and all other information related to the item
   * 
   ***************************************************************************/
   
   
   /**
    * Get item information by id
    * @param itemid
    * @return vector table
    * @throws SQLException 
    */
   /*public ArrayList<Item> getItem(int itemid)throws SQLException{
      ArrayList<Item> item = new ArrayList<Item>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM ITEM WHERE ITEMID="+itemid);     
      while (rs.next())
      {
        int item_id = rs.getInt("itemid");
        String itemdescription = rs.getString("itemdescription");
        int itembarcodeid = rs.getInt("itembarcodeid");
        int itemState = rs.getInt("item_state");
        String serialno = rs.getString("seralno"); 
        double purchase_cost = rs.getDouble("purchase_cost");
        double sale_cost = rs.getDouble("sale_cost");               
        int umid = rs.getInt("umid");  
        int suppId = rs.getInt("supp_id");
        int lock = rs.getInt("lock");
        int locationid = rs.getInt("locationid");
        int catid = rs.getInt("catid");
        Date datecreated = rs.getDate("datecreated");
        Date datemodified = rs.getDate("datemodified");
        int imageid = rs.getInt("imageid"); 
        int manid = rs.getInt("manid");
        int modelid = rs.getInt("modelid");      
        item.add(new Item(item_id, itemdescription, itembarcodeid ,itemState , serialno, suppId, lock, locationid,catid, datecreated, datemodified, imageid, manid, modelid, purchase_cost, sale_cost, umid));
      }   
      
      rs.close();
      stmt.close();
      
      return item;
   }*/
   
   public Vector getItem(int id) throws SQLException {
     
      Vector<Vector<String>> item = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
      
      rs = stmt.executeQuery("SELECT * FROM ITEM i" +
                             " LEFT JOIN ITEMINVENTORY inv ON i.itemid = inv.itemid" +
                             " LEFT JOIN ITEM_INFO ii ON i.itemid = ii.itemid" +
                             " WHERE i.itemid = "+id+"");
                             //" GROUP BY i.itemcode");

      while (rs.next())
      {
       /**----------------------------------------------------------------------
        *!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! NOTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        * ----------------------------------------------------------------------
        * If you make any modifications to vector c then,
        * you must also make necessary changes to the components
        * or classes that uses this method. Examples of such classes are
        * Customers and Quotations.
        */   
        Vector<String> i = new Vector<String>();
        //Item
        i.add(rs.getString(1)); // Item ID
        i.add(rs.getString(2)); // item code
        i.add(rs.getString(3)); // item description
        i.add(rs.getString(4)); // item state
        i.add(rs.getString(5)); // date created
        i.add(rs.getString(6)); // date modified
        i.add(rs.getString(7)); // end date
        //iteminventory
        i.add(rs.getString(8)); // inventory id
        i.add(rs.getString(9)); // item ID
        i.add(rs.getString(10)); // total quantity
        i.add(rs.getString(11)); // current quantity
        i.add(rs.getString(12)); // transaction number
        i.add(rs.getString(13)); // reference number
        //item_info
        i.add(rs.getString(14)); // id
        i.add(rs.getString(15)); // item id
        i.add(rs.getString(16)); // serial number
        i.add(rs.getString(17)); // purchase cost
        i.add(rs.getString(18)); // sales cost
        i.add(rs.getString(19)); // mark up
        i.add(rs.getString(20)); // item type id
        i.add(rs.getString(21)); // supplier id
        i.add(rs.getString(22)); // unit measurement id
        i.add(rs.getString(23)); // item lock state
        i.add(rs.getString(24)); // location id
        i.add(rs.getString(25)); // category id
        i.add(rs.getString(26)); // image id
        i.add(rs.getString(27)); // manufacture id
        i.add(rs.getString(28)); // model id
        
        /*i.add(rs.getString(29)); // website
        i.add(rs.getString(30)); // con_state
        i.add(rs.getString(31)); // date created
        i.add(rs.getString(32)); // date modified
        i.add(rs.getString(33)); // end date
        //categories info
        i.add(rs.getString(34)); // category info id
        i.add(rs.getString(35)); // category id
        i.add(rs.getString(36)); // customer id
        //customer info 
        i.add(rs.getString(37)); // customer info id
        i.add(rs.getString(38)); // customer id
        i.add(rs.getString(39)); // accepts electronic invoice
        i.add(rs.getString(40)); // salesrep id
        i.add(rs.getString(41)); // image id*/
        item.add(i);
      }
 
      rs.close();
      stmt.close();
            
      return item;
   }
   
    public Vector getItemByCode(String itemcode) throws SQLException {
     
      Vector<Vector<String>> item = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
      
      rs = stmt.executeQuery("SELECT * FROM ITEM i" +
                             " LEFT JOIN ITEMINVENTORY inv ON i.itemid = inv.itemid" +
                             " LEFT JOIN ITEM_INFO ii ON i.itemid = ii.itemid" +
                             " WHERE i.itemcode = "+itemcode+"");
                             //" GROUP BY i.itemcode");

      while (rs.next())
      {
       /**----------------------------------------------------------------------
        *!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! NOTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        * ----------------------------------------------------------------------
        * If you make any modifications to vector c then,
        * you must also make necessary changes to the components
        * or classes that uses this method. Examples of such classes are
        * Customers and Quotations.
        */   
        Vector<String> i = new Vector<String>();
        //Item
        i.add(rs.getString(1)); // Item ID
        i.add(rs.getString(2)); // item code
        i.add(rs.getString(3)); // item description
        i.add(rs.getString(4)); // item state
        i.add(rs.getString(5)); // date created
        i.add(rs.getString(6)); // date modified
        i.add(rs.getString(7)); // end date
        //iteminventory
        i.add(rs.getString(8)); // inventory id
        i.add(rs.getString(9)); // item ID
        i.add(rs.getString(10)); // total quantity
        i.add(rs.getString(11)); // current quantity
        i.add(rs.getString(12)); // transaction number
        i.add(rs.getString(13)); // reference number
        //item_info
        i.add(rs.getString(14)); // id
        i.add(rs.getString(15)); // item id
        i.add(rs.getString(16)); // serial number
        i.add(rs.getString(17)); // purchase cost
        i.add(rs.getString(18)); // sales cost
        i.add(rs.getString(19)); // mark up
        i.add(rs.getString(20)); // item type id
        i.add(rs.getString(21)); // supplier id
        i.add(rs.getString(22)); // unit measurement id
        i.add(rs.getString(23)); // item lock state
        i.add(rs.getString(24)); // location id
        i.add(rs.getString(25)); // category id
        i.add(rs.getString(26)); // image id
        i.add(rs.getString(27)); // manufacture id
        i.add(rs.getString(28)); // model id
        
        /*i.add(rs.getString(29)); // website
        i.add(rs.getString(30)); // con_state
        i.add(rs.getString(31)); // date created
        i.add(rs.getString(32)); // date modified
        i.add(rs.getString(33)); // end date
        //categories info
        i.add(rs.getString(34)); // category info id
        i.add(rs.getString(35)); // category id
        i.add(rs.getString(36)); // customer id
        //customer info 
        i.add(rs.getString(37)); // customer info id
        i.add(rs.getString(38)); // customer id
        i.add(rs.getString(39)); // accepts electronic invoice
        i.add(rs.getString(40)); // salesrep id
        i.add(rs.getString(41)); // image id*/
        item.add(i);
      }
 
      rs.close();
      stmt.close();
            
      return item;
   }
   
   public Vector getItemByBarcode(String barcode) throws SQLException {
     
      Vector<Vector<String>> item = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
      
      rs = stmt.executeQuery("SELECT * FROM BARCODE b" +
                             " LEFT JOIN BARCODELINE bc ON b.barcodeid = bc.barcodeid" +
                             " LEFT JOIN ITEM i ON bc.itemid = i.itemid" +
                             " LEFT JOIN ITEMINVENTORY  inv ON i.itemid = inv.itemid" +
                             " LEFT JOIN ITEM_INFO ii ON i.itemid = ii.itemid" +
                             " WHERE b.barcode = '"+barcode+"'");
      
      /*rs = stmt.executeQuery("SELECT * FROM ITEM i" +
                             " LEFT JOIN ITEMINVENTORY inv ON i.itemid = inv.itemid" +
                             " LEFT JOIN ITEM_INFO ii ON i.itemid = ii.itemid" +
                             " WHERE i.itemid = "+id+"");*/
        
      while (rs.next())
      {
       /**----------------------------------------------------------------------
        *!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! NOTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        * ----------------------------------------------------------------------
        * If you make any modifications to vector c then,
        * you must also make necessary changes to the components
        * or classes that uses this method. Examples of such classes are
        * Customers and Quotations.
        */   
        Vector<String> i = new Vector<String>();
        //barcode
        /*i.add(rs.getString(1)); // barcodeid
        i.add(rs.getString(2)); // barcode
        i.add(rs.getString(3)); // imageid
        //barcode line
        i.add(rs.getString(4)); // id
        i.add(rs.getString(5)); // barcodeid
        i.add(rs.getString(6)); // itemid */
        //Item
        i.add(rs.getString(7)); // Item ID
        i.add(rs.getString(8)); // item code
        i.add(rs.getString(9)); // item description
        i.add(rs.getString(10)); // item state
        i.add(rs.getString(11)); // date created
        i.add(rs.getString(12)); // date modified
        i.add(rs.getString(13)); // end date
        //iteminventory
        i.add(rs.getString(14)); // inventory id
        i.add(rs.getString(15)); // item ID
        i.add(rs.getString(16)); // total quantity
        i.add(rs.getString(17)); // current quantity
        i.add(rs.getString(18)); // transaction number
        i.add(rs.getString(19)); // reference number
        //item_info
        i.add(rs.getString(20)); // id
        i.add(rs.getString(21)); // item id
        i.add(rs.getString(22)); // serial number
        i.add(rs.getString(23)); // purchase cost
        i.add(rs.getString(24)); // sales cost
        i.add(rs.getString(25)); // mark up
        i.add(rs.getString(26)); // item type id
        i.add(rs.getString(27)); // supplier id
        i.add(rs.getString(28)); // unit measurement id
        i.add(rs.getString(29)); // item lock state
        i.add(rs.getString(30)); // location id
        i.add(rs.getString(31)); // category id
        i.add(rs.getString(32)); // image id
        i.add(rs.getString(33)); // manufacture id
        i.add(rs.getString(34)); // model id
        
        item.add(i);
      }
 
      rs.close();
      stmt.close();
            
      return item;
   }
   
   
   /**
    * Get item information by bar code id
    * @param barcodeid
    * @return array list
    * @throws SQLException 
    */
   /*public ArrayList<Item> getItemByBarcode(int barcodeid)throws SQLException{
      ArrayList<Item> item = new ArrayList<Item>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM ITEM WHERE itembarcodeid="+barcodeid);     
      while (rs.next())
      {
        int item_id = rs.getInt("itemid");
        String itemdescription = rs.getString("itemdescription");
        int itembarcodeid = rs.getInt("itembarcodeid");
        int itemState = rs.getInt("item_state");
        String serialno = rs.getString("serialno"); 
        double purchase_cost = rs.getDouble("purchase_cost");
        double sale_cost = rs.getDouble("sales_cost");               
        int umid = rs.getInt("umid");                 
        int suppId = rs.getInt("supp_id");
        int lock = rs.getInt("lock");
        int locationid = rs.getInt("locationid");
        int catid = rs.getInt("catid");
        Date datecreated = rs.getDate("datecreated");
        Date datemodified = rs.getDate("datemodified");
        int imageid = rs.getInt("imageid"); 
        int manid = rs.getInt("manid");
        int modelid = rs.getInt("modelid");      
        item.add(new Item(item_id, itemdescription, itembarcodeid ,itemState , serialno, suppId, lock, locationid,catid, datecreated, datemodified, imageid, manid, modelid, purchase_cost, sale_cost, umid));
      }   
      
      rs.close();
      stmt.close();
      
      return item;
   }*/
    
   /**
     * Get item inventory information
     * @param itemid
     * @return array list
     * @throws SQLException 
     */
   public ArrayList<Iteminventory> getItemInventory(int itemid)throws SQLException{
      ArrayList<Iteminventory> iteminv = new ArrayList<Iteminventory>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM iteminventory WHERE itemid="+itemid);     
      while (rs.next())
      {
        int invid = rs.getInt("invid");
        int item_id = rs.getInt("itemid");
        //int locationid = rs.getInt("locationid"); deleted from db table
        int totalqty = rs.getInt("totalqty");
        int currqty = rs.getInt("currqty");
        int transcno = rs.getInt("transcno");
        int refno = rs.getInt("refno");
        iteminv.add(new Iteminventory(invid,item_id,totalqty,currqty,transcno,refno));
      }   
      
      rs.close();
      stmt.close();
      
      return iteminv;
   }
   
   /**
    * Get location information by location id
    * @param locationid
    * @return array list
    * @throws SQLException 
    */
   public ArrayList<Location> getLocation(int locationid)throws SQLException{
      ArrayList<Location> location = new ArrayList<Location>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM location WHERE locationid="+locationid);     
      while (rs.next())
      {
        int location_id = rs.getInt("locationid");
        String locname = rs.getString("locname");
        String locdescription = rs.getString("locdescription");
        int locState = rs.getInt("loc_state");
        int locQty = rs.getInt("loc_qty");
        String locType = rs.getString("loc_type");
        location.add(new Location(location_id, locname, locdescription,locState,locQty, locType));
      }   
      
      rs.close();
      stmt.close();
      
      return location;
   }
   
   /**
    * Get location information by location name
    * @param locationname
    * @return array list
    * @throws SQLException 
    */
   public ArrayList<Location> getLocation(String locationname)throws SQLException{
      ArrayList<Location> location = new ArrayList<Location>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM location WHERE locname="+locationname);     
      while (rs.next())
      {
        int location_id = rs.getInt("locationid");
        String locname = rs.getString("locname");
        String locdescription = rs.getString("locdescription");
        int locState = rs.getInt("loc_state");
        int locQty = rs.getInt("loc_qty");
        String locType = rs.getString("loc_type");
        location.add(new Location(location_id, locname, locdescription,locState,locQty, locType));
      }   
      
      rs.close();
      stmt.close();
      
      return location;
   }
   
   /**
    * Get active locations information
    * @param locationid
    * @return array list
    * @throws SQLException 
    */
   public ArrayList<Location> getLocations()throws SQLException{
      ArrayList<Location> location = new ArrayList<Location>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM location WHERE loc_state=1 ORDER BY locname ASC");     
      while (rs.next())
      {
        int location_id = rs.getInt("locationid");
        String locname = rs.getString("locname");
        String locdescription = rs.getString("locdescription");
        int locState = rs.getInt("loc_state");
        int locQty = rs.getInt("loc_qty");
        String locType = rs.getString("loc_type");
        location.add(new Location(location_id, locname, locdescription,locState,locQty, locType));
      }   
      
      rs.close();
      stmt.close();
      
      return location;
   }
   
   /**
    * get categories by classification
    * @return array list of categories
    * @throws SQLException 
    */
   public ArrayList<Category> getCategories(String name)throws SQLException{
      ArrayList<Category> cat = new ArrayList<Category>();
      Statement stmt = con.createStatement();
      String sql = "SELECT * FROM category c"
                 + " LEFT JOIN category_info ci ON c.catid = ci.catid"
                 + " WHERE c.subtype = '"+name+"'";
                 //+ " GROUP BY c.catname";
      rs = stmt.executeQuery(sql);     
      while (rs.next()) 
      {
        int catid = rs.getInt("catid");
        String catname = rs.getString("catname");
        String subtype = rs.getString("subtype");
        cat.add(new Category(catid, catname, subtype));
      }   
      
      rs.close();
      stmt.close();
      
      return cat;
   }
   
   /**
    * get categories
    * @return array list of categories
    * @throws SQLException 
    */
   public ArrayList<Category> getCategories()throws SQLException{
      ArrayList<Category> cat = new ArrayList<Category>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM category");     
      while (rs.next())
      {
        int catid = rs.getInt("catid");
        String catname = rs.getString("catname");
        String subtype = rs.getString("subtype");
        cat.add(new Category(catid, catname, subtype));
      }   
      
      rs.close();
      stmt.close();
      
      return cat;
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
  
   /**
   * Checks if the provided category exists.
   * @param category
   * @param subtype
   * @return true or false if the category doesn't exist
   * @throws SQLException 
   */
   public boolean getCategory(String category, String subtype) throws SQLException {
        
        boolean status = true;
        Statement st = con.createStatement();
        rs = st.executeQuery("SELECT catname FROM category WHERE `catname` ='"+category+"' AND `subtype` = '"+subtype+"'"); 	
	if(!rs.next())
         {
           status = false;
         }

	rs.close();
	st.close();
        
        return status;
  }
  
  public ArrayList<AddressType> getAdressTypeList() throws SQLException{
      ArrayList<AddressType> adresstype = new  ArrayList<AddressType>();
      Statement stmt = con.createStatement();
      rs  = stmt.executeQuery("SELECT *  FROM addresstype");
            
      while( rs.next())
         {   
             int addresstypeid = rs.getInt("addresstypeid");
             String description = rs.getString("addressdescription");
             adresstype.add(new AddressType(addresstypeid, description)); 
         }           
      rs.close();
      stmt.close();
      
      return adresstype;        
   } 
   
  public ArrayList<User> getSalesRepresentativeList() throws SQLException{
      ArrayList<User> salesreplist = new  ArrayList<User>();
      Statement stmt = con.createStatement();
      rs  = stmt.executeQuery("SELECT  `userid`, `firstname`, `lastname`, `email`  FROM user WHERE `roleid` = 3 ");
            
      while( rs.next())
         {   
             int userid = rs.getInt("userid");
             String name = rs.getString("firstname");
             String surname = rs.getString("lastname");
             String email = rs.getString("email");
             salesreplist.add(new User(userid, name, surname, email));
         }           
      rs.close();
      stmt.close();
      
      return salesreplist;        
   }
   
   /**
    * get measurements
    * @return array list of uni measurements
    * @throws SQLException 
    */
   public ArrayList<UnitMeasurement> getUnitMeasurements() throws SQLException{
      ArrayList<UnitMeasurement> unit = new ArrayList<UnitMeasurement>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM unit_measurement");     
      while (rs.next())
      {
        int umid = rs.getInt("umid");
        String umdesc = rs.getString("um_description");
        int um_state = rs.getInt("um_state");
        unit.add(new UnitMeasurement(umid, umdesc, um_state));
      }   
      
      rs.close();
      stmt.close();
      
      return unit; 
    } 
      
     /**
    * get manufactures
    * @return array list of manufactures
    * @throws SQLException 
    */
   public ArrayList<Manufacture> getManufactures() throws SQLException{
      ArrayList<Manufacture> man = new ArrayList<Manufacture>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM manufacture");     
      while (rs.next())
      {
        int manid = rs.getInt("manid");
        String manname = rs.getString("manname");
        int addressid = rs.getInt("addressid");
        String contact = rs.getString("contact");
        int state = rs.getInt("man_state");
        Date enddate = rs.getDate("enddate");
        
        man.add(new Manufacture(manid, manname, addressid, contact, state, enddate));
      }   
      
      rs.close();
      stmt.close();
      
      return man; 
    } 
   
   /**
    * Returns an items unit of measurement
    * @param um_id
    * @return String unit of measurement 
    * @throws SQLException 
    */
   public String getUnitMeasurement(int um_id) throws SQLException{
      String unit = "";
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT um_description FROM unit_measurement WHERE umid ="+um_id);     
      while (rs.next())
      {
        unit = rs.getString("um_description");
      }   
      
      rs.close();
      stmt.close();
      
      return unit; 
    }   
   
   /**
    * returns category name
    * @param catid
    * @return category string
    * @throws SQLException 
    */   
   public String getItemCategory(int catid)throws SQLException{
      String category = "";
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT catname FROM category WHERE catid="+catid);     
      while (rs.next())
      {
        category = rs.getString("catname"); 
      }   
      
      rs.close();
      stmt.close();
      
      return category;
   }
   
   /**
    * returns list of bar code records based on barcodeid
    * @param bcodeid
    * @return array list (bar codes)
    * @throws SQLException 
    */
   public ArrayList<Barcode> getBarcode(int bcodeid)throws SQLException{
      ArrayList<Barcode> barcode = new ArrayList<>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM BARCODE WHERE barcodeid="+bcodeid);     
      while (rs.next())
      {
        int barcodeid = rs.getInt("barodeid");
        String bar_code = rs.getString("barcode");
        int imageid = rs.getInt("imageid");    
        barcode.add(new Barcode( barcodeid, bar_code, imageid));
      }   
      
      rs.close();
      stmt.close();
      
      return barcode;
   }
   
   /**
    * returns list of bar code records based on bar code supplied
     * @param barcode
    * @param bcodeid
    * @return array list (bar codes)
    * @throws SQLException 
    */
   public ArrayList<Barcode> getBarcode(String barcode)throws SQLException{
      ArrayList<Barcode> bcode = new ArrayList<>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM BARCODE WHERE barcode="+barcode);     
      while (rs.next())
      {
        int barcodeid = rs.getInt("barcodeid");
        String bar_code = rs.getString("barcode");
        int imageid = rs.getInt("imageid");    
        bcode.add(new Barcode( barcodeid, bar_code, imageid));
      }   
      
      rs.close();
      stmt.close();
      
      return bcode;
   }
      
   /**
    * Get all information about an item
    * @param itembarcodeid
    * @return vector object
    * @throws SQLException 
    */
   public Vector getItemInfoByBarCodeID(String itembarcodeid)throws SQLException{
      Vector<Vector<String>>tableVector = new Vector<>();
      Statement stmt = con.createStatement();
  
      rs = stmt.executeQuery("SELECT * FROM ITEM INNER JOIN ITEMINVENTORY ON item.itemid = iteminventory.itemid WHERE itembarcodeid="+itembarcodeid);
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
   
   public ArrayList<Itemtype> getItemTypes()throws SQLException{
      ArrayList<Itemtype> item = new ArrayList<Itemtype>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM itemtype");     
      while (rs.next())
      {
        int typeid = rs.getInt("typeid");
        String type = rs.getString("type");
        item.add(new Itemtype(typeid, type));
      }   
      
      rs.close();
      stmt.close();
      
      return item;
   }
      
   public Vector getCustomer(int id) throws SQLException {
     
      Vector<Vector<String>> cust = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();

      /*rs = stmt.executeQuery("SELECT * FROM CUSTOMER_INFO cinf" +
                             " LEFT JOIN CUSTOMER c ON cinf.custid = c.custid" +
                             " LEFT JOIN ADDRESS a ON cinf.addressid = a.addressid" +
                             " LEFT JOIN CONTACT con ON cinf.conid = con.conid" +
                             " LEFT JOIN CATEGORY_INFO ci ON cinf.custid = ci.data" +
                             " WHERE cinf.custid ="+id+""+
                             "   AND ci.fieldid = 1 ");*/
       rs = stmt.executeQuery("SELECT * FROM CUSTOMER_INFO cinf" +
                             " LEFT JOIN CUSTOMER c ON cinf.custid = c.custid" +
                             " LEFT JOIN ADDRESS a ON cinf.addressid = a.addressid" +
                             " LEFT JOIN CONTACT con ON cinf.conid = con.conid" +
                             " WHERE cinf.custid ="+id);
      
      while (rs.next())
      {
       /**----------------------------------------------------------------------
        *!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! NOTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        * ----------------------------------------------------------------------
        * If you make any modifications to vector c then,
        * you must also make necessary changes to the components
        * or classes that uses this method. Examples of such classes are
        * Customers and Quotations.
        */   
        Vector<String> c = new Vector<String>();
        //customer info 
        c.add(rs.getString(1)); // customer info id
        c.add(rs.getString(2)); // customer id
        c.add(rs.getString(3)); // accepts electronic invoice
        c.add(rs.getString(4)); // salesrep id
        c.add(rs.getString(5)); // image id
        c.add(rs.getString(6)); // address id
        c.add(rs.getString(7)); // contact id
        c.add(rs.getString(8)); // category id
        //customer
        c.add(rs.getString(9)); // Customer ID
        c.add(rs.getString(10)); // Customer name
        c.add(rs.getString(11)); // vat
        c.add(rs.getString(12)); // customer state
        c.add(rs.getString(13)); // date created
        c.add(rs.getString(14)); // date modified
        c.add(rs.getString(15)); // end date
        //address
        c.add(rs.getString(16)); // address id
        c.add(rs.getString(17)); // address 1
        c.add(rs.getString(18)); // address 2
        c.add(rs.getString(19)); // city
        c.add(rs.getString(20)); // country
        c.add(rs.getString(21)); // province
        c.add(rs.getString(22)); // zipcode
        //c.add(rs.getString(23)); // phone1
        //c.add(rs.getString(24)); // phone2
        //c.add(rs.getString(25)); // fax
        //c.add(rs.getString(26)); // email
        c.add(rs.getString(23)); // addresstype
        c.add(rs.getString(24)); // createdby
        //contact
        c.add(rs.getString(25)); // Contact id
        c.add(rs.getString(26)); // Contact name
        c.add(rs.getString(27)); // email
        c.add(rs.getString(28)); // mobile
        c.add(rs.getString(29)); // telephone
        c.add(rs.getString(30)); // fax
        c.add(rs.getString(31)); // website
        c.add(rs.getString(32)); // con_state
        c.add(rs.getString(33)); // date created
        c.add(rs.getString(34)); // date modified
        c.add(rs.getString(35)); // end date
        
        cust.add(c);
      }
 
      rs.close();
      stmt.close();
            
      return cust;
   }
   

   
   public Vector getCustomers() throws SQLException {
       
      Vector<Vector<String>> cust = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
      
     /*rs = stmt.executeQuery("SELECT c.custid, c.custname, (SELECT city FROM address WHERE addressid = cinf.addressid) AS 'city', ci.catid, con.name, con.email, con.telephone, con.mobile, c.cust_state" +
                             " FROM CUSTOMER c " +
                             " LEFT JOIN CUSTOMER_INFO cinf ON c.custid = cinf.custid" +
                             " LEFT JOIN CONTACT con ON cinf.conid = con.conid" +
                             " LEFT JOIN CATEGORY_INFO ci ON c.custid = ci.data"+
                             "      AND ci.fieldid = 1" +
                             " GROUP BY c.custid"); */
      
      rs = stmt.executeQuery("SELECT c.custid, c.custname, (SELECT city FROM address WHERE addressid = cinf.addressid) AS 'city', cinf.catid, con.name, con.email, con.telephone, con.mobile, c.cust_state" +
                             " FROM CUSTOMER c " +
                             " LEFT JOIN CUSTOMER_INFO cinf ON c.custid = cinf.custid" +
                             " LEFT JOIN CONTACT con ON cinf.conid = con.conid");
                             //" GROUP BY c.custid"); 

      while (rs.next())
      {
        Vector<String> c = new Vector<String>();
        c.add(rs.getString(1)); // Customer ID
        c.add(rs.getString(2)); // Customer name
        c.add(rs.getString(3)); // City
        c.add(rs.getString(4)); // Categroy ID
        c.add(rs.getString(5)); // Contact name
        c.add(rs.getString(6)); // Email
        c.add(rs.getString(7)); // Telephone
        c.add(rs.getString(8)); // Mobile
        c.add(rs.getString(9)); // Customer state
        cust.add(c);
      }
 
      rs.close();
      stmt.close();
            
      return cust;
   }
 
   public Vector getSupplier(int id) throws SQLException {
       
      Vector<Vector<String>> supp = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
      
      /*rs = stmt.executeQuery("SELECT * " +
                             " FROM SUPPLIER_INFO si" +
                             " LEFT JOIN SUPPLIER s ON s.supp_id = si.supp_id" +
                             " LEFT JOIN CONTACT con ON con.conid = si.conid" +
                             " LEFT JOIN ADDRESS a ON a.addressid = si.addressid" +
                             " LEFT JOIN CATEGORY_INFO ci ON ci.data = si.supp_id" +
                             " WHERE si.supp_id = "+id+""+
                             "   AND ci.fieldid = 2"+
                             " GROUP BY si.supp_id");*/
      
      rs = stmt.executeQuery("SELECT * " +
                             " FROM SUPPLIER_INFO si" +
                             " LEFT JOIN SUPPLIER s ON s.supp_id = si.supp_id" +
                             " LEFT JOIN CONTACT con ON con.conid = si.conid" +
                             " LEFT JOIN ADDRESS a ON a.addressid = si.addressid" +
                             " WHERE si.supp_id = "+id);

      while (rs.next())
      {
       /**----------------------------------------------------------------------
        *!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! NOTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        * ----------------------------------------------------------------------
        * If you make any modifications to vector c then,
        * you must also make necessary changes to the components
        * or classes that uses this method. One example of such class is
        * Customers.
        */   
        Vector<String> s = new Vector<String>();
        //supplier info
        s.add(rs.getString(1)); // supplier info id
        s.add(rs.getString(2)); // supplier id
        s.add(rs.getString(3)); // electronic invoice
        s.add(rs.getString(4)); // image id
        s.add(rs.getString(5)); // address id
        s.add(rs.getString(6)); // contact id
        s.add(rs.getString(7)); // category id
        //supplier
        s.add(rs.getString(8)); // supplier id
        s.add(rs.getString(9)); // supplier name
        s.add(rs.getString(10)); // vat number
        s.add(rs.getString(11)); // supplier state
        s.add(rs.getString(12)); // date created
        s.add(rs.getString(13)); // date modified
        s.add(rs.getString(14)); // end date
        //contact
        s.add(rs.getString(15)); // contact id
        s.add(rs.getString(16)); // contact name
        s.add(rs.getString(17)); // contact email
        s.add(rs.getString(18)); // contact mobile
        s.add(rs.getString(19)); // contact telephone
        s.add(rs.getString(20)); // contact fax
        s.add(rs.getString(21)); // contact website
        s.add(rs.getString(22)); // contact state
        s.add(rs.getString(23)); // date created
        s.add(rs.getString(24)); // date modified
        s.add(rs.getString(25)); // end date
        // address
        s.add(rs.getString(26)); // address id
        s.add(rs.getString(27)); // address 1
        s.add(rs.getString(28)); // address 2 
        s.add(rs.getString(29)); // city
        s.add(rs.getString(30)); // country
        s.add(rs.getString(31)); // province
        s.add(rs.getString(32)); // zipcode
        /*s.add(rs.getString(33)); // phone1
        s.add(rs.getString(34)); // phone2
        s.add(rs.getString(35)); // fax
        s.add(rs.getString(36)); // email*/
        s.add(rs.getString(33)); // address type
        s.add(rs.getString(34)); // created by
        supp.add(s);
      }
 
      rs.close();
      stmt.close();
            
      return supp;
   }
   
    /**
    * get suppliers
    * @return vector list of suppliers
    * @throws SQLException 
    */
   public Vector getSuppliers() throws SQLException{
      Vector<Vector<String>> supp = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
      /*rs = stmt.executeQuery("SELECT s.supp_id, s.suppname,(SELECT city FROM address WHERE addressid = si.addressid) AS 'city',(SELECT catname FROM category WHERE catid = ci.catid) AS 'category',con.name, con.email, con.telephone, con.mobile, s.supp_state" +
                             " FROM SUPPLIER_INFO si" +
                             " LEFT JOIN SUPPLIER s ON s.supp_id = si.supp_id" +
                             " LEFT JOIN CONTACT con ON con.conid = si.conid" +
                             " LEFT JOIN ADDRESS a ON a.addressid = si.addressid" +
                             " LEFT JOIN CATEGORY_INFO ci ON ci.data = si.supp_id" +
                             "       AND ci.fieldid = 2 "+
                             " GROUP BY si.supp_id");*/
      
      rs = stmt.executeQuery("SELECT s.supp_id, s.suppname,(SELECT city FROM address WHERE addressid = si.addressid) AS 'city',(SELECT catname FROM category WHERE catid = si.catid) AS 'category',con.name, con.email, con.telephone, con.mobile, s.supp_state" +
                             " FROM SUPPLIER_INFO si" +
                             " LEFT JOIN SUPPLIER s ON s.supp_id = si.supp_id" +
                             " LEFT JOIN CONTACT con ON con.conid = si.conid" +
                             " LEFT JOIN ADDRESS a ON a.addressid = si.addressid");
                             //" GROUP BY si.supp_id");
      
      while (rs.next())
      {
        Vector<String> s = new Vector<String>();
        s.add(rs.getString(1)); //supplier id
        s.add(rs.getString(2)); //supplier name
        s.add(rs.getString(3)); //contact city
        s.add(rs.getString(4)); //category name 
        s.add(rs.getString(5)); //contact name
        s.add(rs.getString(6)); //contact email
        s.add(rs.getString(7)); //contact telephone
        s.add(rs.getString(8)); //contact mobile
        s.add(rs.getString(9)); //supplier state
        supp.add(s);
      }   
      
      rs.close();
      stmt.close();
      
      return supp; 
    } 
   
   public Vector getItems() throws SQLException {
       
      Vector<Vector<String>> items = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
      
      rs = stmt.executeQuery("SELECT i.itemid, i.itemcode, i.itemdescription, it.typeid, it.catid, it.sales_cost, it.purchase_cost, inv.currqty, i.datecreated, i.item_state" +
                             "  FROM ITEM i" +
                             "  LEFT JOIN ITEM_INFO it ON i.itemid = it.itemid" +
                             "  LEFT JOIN ITEMINVENTORY inv ON i.itemid = inv.itemid");
                             //"  GROUP BY i.itemcode");

      while (rs.next())
      {
        Vector<String> i = new Vector<String>();
        i.add(rs.getString(1)); // Item ID
        i.add(rs.getString(2)); // Item Code
        i.add(rs.getString(3)); // Item Description
        i.add(rs.getString(4)); // Item Type ID
        i.add(rs.getString(5)); // Category ID
        i.add(rs.getString(6)); // Sales Cost
        i.add(rs.getString(7)); // Purchase Cost
        i.add(rs.getString(8)); // Current Qty
        i.add(rs.getString(9)); // Date Created
        i.add(rs.getString(10)); // Item State
        items.add(i);
      }
 
      rs.close();
      stmt.close();
            
      return items;
   }
   
   public ImageIcon getImage(int imageid) throws SQLException {
      byte[] bytes = null;
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT image FROM image WHERE imageid="+imageid);        
      while (rs.next())
      {
        bytes = rs.getBytes("image");
      }   
      
      java.awt.Image img    = Toolkit.getDefaultToolkit().createImage(bytes);
      ImageIcon imageicon = new ImageIcon(img);
      
      rs.close();
      stmt.close();
      
      return imageicon;
   }
   
   
   public Vector getQuotations() throws SQLException {
       
      Vector<Vector<String>> quote = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
            
      rs = stmt.executeQuery("SELECT q.quotenumber,"
                            +" (SELECT custname FROM CUSTOMER WHERE custid = q.custid) AS 'customer', "
                            +" (SELECT status FROM QUOTATIONSTATUS WHERE id = q.quote_status) AS 'status',"
                            +" (SELECT SUM(totalprice) FROM QUOTATIONLINE WHERE quoteid = q.id) AS 'total',"
                            +"  q.datecreated, q.enddate" 
                            +" FROM QUOTATION q"
                            +" WHERE q.quotenumber > 1000000"); 

      while (rs.next())
      {
        Vector<String> q = new Vector<String>();
        q.add(rs.getString(1)); // quote number
        q.add(rs.getString(2)); // Customer name
        q.add(rs.getString(3)); // status
        q.add(rs.getString(4)); // total
        q.add(rs.getString(5)); // created date
        q.add(rs.getString(6)); // expiry date
        quote.add(q);
      }
 
      rs.close();
      stmt.close();
            
      return quote;
   }
   
   public Vector getCustomerQuotations(int custid) throws SQLException {
       
      Vector<Vector<String>> quote = new Vector<Vector<String>>();
      Statement stmt = con.createStatement();
            
      rs = stmt.executeQuery("SELECT q.quotenumber,"
                             + "(SELECT custname FROM CUSTOMER WHERE custid = q.custid) AS 'customer', "
                             + "(SELECT status FROM QUOTATIONSTATUS WHERE id = q.quote_status) AS 'status',"
                             + "(SELECT SUM(totalprice) FROM QUOTATIONLINE WHERE quoteid = q.id) AS 'total',"
                             + " q.datecreated, q.enddate" +
                            " FROM QUOTATION q " +
                            " WHERE custid = "+custid); 

      while (rs.next())
      {
        Vector<String> q = new Vector<String>();
        q.add(rs.getString(1)); // quote number
        q.add(rs.getString(2)); // Customer name
        q.add(rs.getString(3)); // status
        q.add(rs.getString(4)); // total
        q.add(rs.getString(5)); // created date
        q.add(rs.getString(6)); // expiry date
        quote.add(q);
      }
 
      rs.close();
      stmt.close();
            
      return quote;
   }
    
   /**
   * Get user details by user id
   * Note that you wont get a user password :-;
   * @param userid
   * @return user info (array list)
   * @throws SQLException 
   */
  public ArrayList getUser(int userid) throws SQLException
  {  
    ArrayList<User> user = new ArrayList<>();
    Statement st = con.createStatement();
    rs = st.executeQuery("SELECT * FROM user WHERE userid LIKE "+userid);
    while(rs.next())
      {
        int user_id  = rs.getInt("userid");
        String uname = rs.getString("username");
        //String pword = rs.getString("password");
        String pword = "";
        String fname = rs.getString("firstname");
        String lname = rs.getString("lastname");
        String email = rs.getString("email");
        String hometel = rs.getString("hometel");
        String cell = rs.getString("mobile");
        int addressid = rs.getInt("addressid");
        int roleid = rs.getInt("roleid");
        int imageid = rs.getInt("imageid");
        int state = rs.getInt("user_state");
        Date dcreated = rs.getDate("datecreated");
        Date dmodified = rs.getDate("datemodified");
        Date enddate  = rs.getDate("enddate");
        user.add(new User(user_id, uname, pword, fname, lname, email,hometel, cell, addressid, roleid, imageid,state, dcreated,dmodified, enddate));
      } 
    
    rs.close();
    st.close();
   
    return user;
  }
  
  /**
   * Get user details by user name
   * Note that you wont get a user password :-;
   * @param username
   * @return user info (array list)
   * @throws SQLException 
   */
  public ArrayList getUser(String username) throws SQLException
  {  
    ArrayList<User> user = new ArrayList<>();
    Statement st = con.createStatement();
    rs = st.executeQuery("SELECT * FROM user WHERE username= '"+username+"'");
    while(rs.next())
      {
        int user_id  = rs.getInt("userid");
        String uname = rs.getString("username");
        //String pword = rs.getString("password");
        String pword = "";
        String fname = rs.getString("firstname");
        String lname = rs.getString("lastname");
        String email = rs.getString("email");
        String hometel = rs.getString("hometel");
        String cell = rs.getString("mobile");
        int addressid = rs.getInt("addressid");
        int roleid = rs.getInt("roleid");
        int imageid = rs.getInt("imageid");
        int state = rs.getInt("user_state");
        Date dcreated = rs.getDate("datecreated");
        Date dmodified = rs.getDate("datemodified");
        Date enddate  = rs.getDate("enddate");
        user.add(new User(user_id, uname, pword, fname, lname, email,hometel, cell, addressid, roleid, imageid,state, dcreated,dmodified, enddate));
      } 
    
    rs.close();
    st.close();
   
    return user;
  }
  
  /**
   * Verifies user if exists
   * @param user
   * @param passwd
   * @return status: exists, notexisting,passwordtrue or usernametrue 
   * @throws SQLException 
   */
  public String userVerification(String user, String passwd) throws SQLException
  {  
    String status = "";
    String username = user;
    String password = passwd;
    
    Statement st = con.createStatement();
    rs = st.executeQuery("SELECT * FROM user WHERE username='" + username + "'");
    rs.next();

    String unameUpperCase = rs.getString("username");
    String uname = unameUpperCase.toLowerCase();
    String pword = rs.getString("password");  

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
   * Get user temporary password
   * @param userid
   * @return
   * @throws SQLException 
   */
  public String getTemporaryPassword(int userid) throws SQLException{
        String pword="";  
        Statement st = con.createStatement();
        rs = st.executeQuery("SELECT temppassword FROM temppassword WHERE userid ="+userid);
        if(!rs.next())
        { pword = null; }
        else{ pword = rs.getString("temppassword"); }

        rs.close();
        st.close();

        return pword;
  }
  
  public String getQuotationStatus(int quotenumber) throws SQLException{
     String status = "";
     Statement st = con.createStatement();
     rs = st.executeQuery("SELECT qt.status FROM QUOTATIONSTATUS qt"
                        +"  LEFT JOIN QUOTATION q ON qt.id = q.quote_status"
                        +" WHERE q.quotenumber = "+quotenumber+"");
     while(rs.next()){
         status = rs.getString("status");
     }

     if(status.isEmpty()){
         status = "New";
     }
     
     rs.close();
     st.close();

     return status;
  }
  
  public int getQuotationStatus(String status) throws SQLException{
     int id = 0;
     Statement st = con.createStatement();
     rs = st.executeQuery("SELECT id FROM QUOTATIONSTATUS WHERE status = '"+status+"'");
     while(rs.next()){
         id = rs.getInt("id");
     }
     
     rs.close();
     st.close();

     return id;
  }
  
  /**
   * Networks configured on your system
   * @return network(s)
   * @throws SQLException 
   */
  public ArrayList<NetworkConfig> networkInfo() throws SQLException{
      ArrayList<NetworkConfig> network = new ArrayList<>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM network_config");     
      while (rs.next())
      {
        int netid = rs.getInt("netid");
        String net_name = rs.getString("network_interface_name");
        Date setdate = rs.getDate("setdate");    
        int userid  = rs.getInt("userid");
        network.add(new NetworkConfig( netid, net_name, setdate, userid));
      }   
      
      rs.close();
      stmt.close();
      
      return network;
   }
   
  public boolean isSet() throws SQLException  {
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
   * Get active server names set by administrator 
   * @return server name(s)
   * @throws SQLException 
   */
  public ArrayList<Server> getActiveServer() throws SQLException{
      ArrayList<Server> s = new ArrayList<>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM server WHERE server_state=1");     
      while (rs.next())
      {
        int sid = rs.getInt("sid");
        String serverName = rs.getString("server_name");
        int portnumber = rs.getInt("portnumber");
        String database = rs.getString("databasename");
        String username = rs.getString("username");
        String password = rs.getString("password");
        int pwmemory = rs.getInt("pwmemory");
        String connectionString = rs.getString("connection_string");
        int serverState = rs.getInt("server_state");
        String driver = rs.getString("driver");
        Date setdate  = rs.getDate("setdate");
        s.add(new Server(sid, serverName, portnumber, database,username, password, pwmemory, connectionString, serverState, driver, setdate));
        
      }
      
      rs.close();
      stmt.close();
      
      return s;
   }
   
  /**
   * Get all messages in from latest to oldest. This will return messages in 
   * descending order based on message dates.
   * @return messages in array list
   * @throws SQLException 
   */
  public ArrayList<Messages> getMessagesDesc() throws SQLException{
      ArrayList<Messages> message = new ArrayList<>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM message ORDER BY msgdate DESC");     
      while (rs.next())
      {
        int msgid = rs.getInt("msgid");
        String msg = rs.getString("message");
        Date msgdate = rs.getDate("msgdate");    
        int msgtypeid = rs.getInt("msgtypeid");    
        int userid  = rs.getInt("userid");
        message.add(new Messages(msgid, msg, msgtypeid, msgdate, userid));
      }   
      
      rs.close();
      stmt.close();
      
      return message;
   }
  
  /**
   * Get all messages in from oldest to latest. This will return messages in 
   * ascending order based on message dates.
   * @return messages in array list
   * @throws SQLException 
   */
  public ArrayList<Messages> getMessagesAsc() throws SQLException{
      ArrayList<Messages> message = new ArrayList<>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM message ORDER BY msgdate ASC");     
      while (rs.next())
      {
        int msgid = rs.getInt("msgid");
        String msg = rs.getString("message");
        Date msgdate = rs.getDate("msgdate");    
        int msgtypeid = rs.getInt("msgtypeid");    
        int userid  = rs.getInt("userid");
        message.add(new Messages(msgid, msg, msgtypeid, msgdate, userid));
      }   
      
      rs.close();
      stmt.close();
      
      return message;
   }
  
   /**
   * Get all messages of a user
   * @param userid
   * @return messages in array list
   * @throws SQLException 
   */
  public ArrayList<Messages> getMessages(int userid) throws SQLException{
      ArrayList<Messages> message = new ArrayList<>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM message WHERE userid="+userid);     
      while (rs.next())
      {
        int msgid = rs.getInt("msgid");
        String msg = rs.getString("message");
        Date msgdate = rs.getDate("msgdate");    
        int msgtypeid = rs.getInt("msgtypeid");    
        int user_id  = rs.getInt("userid");
        message.add(new Messages(msgid, msg, msgtypeid, msgdate, user_id));
      }   
      
      rs.close();
      stmt.close();
      
      return message;
   }
  
  /**
   * Get all messages of based on message type
   * @param type
   * @return messages in array list
   * @throws SQLException 
   */
  public ArrayList<Messages> getMessageByType(int type) throws SQLException{
      ArrayList<Messages> message = new ArrayList<>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM message WHERE msgtypeid="+type);     
      while (rs.next())
      {
        int msgid = rs.getInt("msgid");
        String msg = rs.getString("message");
        Date msgdate = rs.getDate("msgdate");    
        int msgtypeid = rs.getInt("msgtypeid");    
        int user_id  = rs.getInt("userid");
        message.add(new Messages(msgid, msg, msgtypeid, msgdate, user_id));
      }   
      
      rs.close();
      stmt.close();
      
      return message;
   }
    
   /**
   * Get all messages not older than given number of days
   * @param days
   * @return messages in array list
   * @throws SQLException 
   */
  public ArrayList<Messages> getMessageByDays(int days) throws SQLException{
      ArrayList<Messages> message = new ArrayList<>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM message WHERE msgdate > (NOW() - INTERVAL "+days+" DAY)");     
      while (rs.next())
      {
        int msgid = rs.getInt("msgid");
        String msg = rs.getString("message");
        Date msgdate = rs.getDate("msgdate");    
        int msgtypeid = rs.getInt("msgtypeid");    
        int user_id  = rs.getInt("userid");
        message.add(new Messages(msgid, msg, msgtypeid, msgdate, user_id));
      }   
      
      rs.close();
      stmt.close();
      
      return message;
   }
  
   /**
   * Get all messages based on the given number of days and message type
   * @param type
   * @param days
   * @return messages in array list
   * @throws SQLException 
   */
  public ArrayList<Messages> getMessageByTypeAndDays(int type,int days) throws SQLException{
      ArrayList<Messages> message = new ArrayList<>();
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM message WHERE msgdate > ( NOW() - INTERVAL "+days+" DAY ) AND msgtypeid = "+type);     
      while (rs.next())
      {
        int msgid = rs.getInt("msgid");
        String msg = rs.getString("message");
        Date msgdate = rs.getDate("msgdate");    
        int msgtypeid = rs.getInt("msgtypeid");    
        int user_id  = rs.getInt("userid");
        message.add(new Messages(msgid, msg, msgtypeid, msgdate, user_id));
      }   
      
      rs.close();
      stmt.close();
      
      return message;
   }
  
  /**
   * Close database connection
   * @throws SQLException 
   */
  public void close() throws SQLException {
       this.con.close();
  }


}
