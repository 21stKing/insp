/*
 * Retrives or Sets logged in user details
 * @author tebogo
 */
package inventory_system;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Tebogo kekana - kinotech (Pty) Ltd.
 */
public class UserProfile 
{
   private static int userid;
   private static String username;
   private static String password;
   private static String firstname;
   private static String lastname;
   private static String email;
   private static String hometel;
   private static String cell;
   private static int addressid;
   private static int roleid;
   private static int imageid;
   private static int userstate;
   private static Date datecreated;
   private static Date datemodified;
   private static Date enddate;
   
   
   /**
    * Creates a user profile of the logged in user
    * 
    * @param userid 
    */
   public UserProfile(int userid)
   {
      this.userid = userid;
      setProfile();
   }
   
    /**
    * User's ID
    * 
    * @return   user id of the logged in user
    */
   public static int getUserID(){return userid;}
   //public void setUserID(int userid){ this.userid = userid;}
   
   /**
    * Username
    * 
    * @return   username of the logged in user
    */
   public static String getUsername(){return username;}
  // public void setUsername(String username){ this.username = username;}
   
   /**
    * Password
    * @return encryped password
    */
   public static String getPassword(){return password; }
   //public void setPassword(String password){ this.password = password;}
   
   /**
    * User's First Name
    * 
    * @return   first name of the logged in user
    */
   public static String getFirstName(){return firstname;}
   //public void setFirstName(String firstname){ this.firstname = firstname;}
   
   /**
    *User's Last Name 
    * @return last name of the logged in user
    */
   public static String getLastName(){return lastname;}
   //public void setlastName(String lastname){ this.lastname = lastname;}
   
   /**
    * User's e-mail address
    * 
    * @return   user e-mail address of the logged in user
    */
   public static String getEmail(){return email;}
   //public void setEmail(String email){ this.email = email;}
   
   /**
    * User's home land line
    * 
    * @return   user land line of the logged in user
    */
   public static String getTel(){return hometel;}
   //public void setTel(String hometel){ this.hometel = hometel;}
   
   /**
    * User's cell phone number
    * 
    * @return   user cell phone number of the logged in user
    */
   public static String getCell(){return cell;}
   //public void setCell(String cell){ this.cell = cell;}
   
   /**
    * User's address
    * 
    * @return   user address id of the logged in user
    */
   public static int getAddressID(){return addressid;}
   //public void setAddressID(int addressid){ this.addressid = addressid;}
   
    /**
    * User's role image
    * 
    * @return   user role id of the logged in user
    */
   public static int getRole(){return roleid;}
   //public void setRole(int roleid){ this.roleid = roleid;}
   
     /**
    * User's image
    * 
    * @return   user image id of the logged in user
    */
   public static int getImageID(){return imageid;}
   //public void setImageID(int imageid){ this.imageid = imageid;}
   
     /**
    * User's active state
    * 
    * @return   user state of the logged in user
    */
   public static int getUserState(){return userstate;}
   //public void setUserState(int userstate){ this.userstate = userstate;}
   
   /**
    * Date user created
    * 
    * @return   date of the logged in user
    */
   public static Date getCreationDate(){return datecreated;}
   //public void setCreationDate(Date datecreated){ this.datecreated = datecreated;}
   
    /**
    * Date user modified
    * 
    * @return   date of the logged in user
    */
   public static Date getModifiedDate(){return datemodified;}
   //public void setModifiedDate(Date datemodified){ this.datemodified = datemodified;}
   
      /**
    * Date user terminated
    * 
    * @return   date of the logged in user
    */
   public static Date getTerminationDate(){return enddate;}
   //public void setTerminationDate(Date enddate){ this.enddate = enddate;}
   
   /**
    * Sets a user profile for the logged in user using userid 
    */
   private void setProfile()
   {
      try{
          DB db = new DB(RunProgram.CONNECTION_MODE);
          ArrayList<User> usr = new ArrayList<>();
          usr = db.getUser(userid);
          //String user_details [] = database.getUserProfile(userid);
          //UserProfile.user_id = Integer.parseInt(user_details[0]);
          UserProfile.username = usr.get(0).getUsername(); 
          UserProfile.firstname = usr.get(0).getFirstname();
          UserProfile.lastname = usr.get(0).getLastname();
          UserProfile.email = usr.get(0).getEmail();
          UserProfile.hometel = usr.get(0).getHometel();
          UserProfile.cell = usr.get(0).getMobile();
          UserProfile.addressid = usr.get(0).getAddressid();
          UserProfile.roleid = usr.get(0).getRoleid(); 
          UserProfile.imageid = usr.get(0).getImageid();
          UserProfile.userstate = usr.get(0).getUserState();
          UserProfile.datecreated = usr.get(0).getDatecreated();
          UserProfile.datemodified = usr.get(0).getDatemodified();
          UserProfile.enddate = usr.get(0).getEnddate();
       }
      catch(Exception e){e.printStackTrace(); JOptionPane.showMessageDialog(null, e,"", 2);}
       
   } 
   
   
}
