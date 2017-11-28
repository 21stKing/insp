
package inventory_system;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author tebogo
 */
public class Password {

   public Password(){
       
   }
   
   /**
    * Generates an MD5 password
    * @param password
    * @return password
    * @throws NoSuchAlgorithmException 
    */
   protected String getMD5Password(char [] password) throws NoSuchAlgorithmException {
    String md5_password = null;
    char [] pword = password;
    int size = pword.length;
    String user_pword = new String(); 
    for(int x = 0;x<size;x++){
        user_pword = user_pword + pword[x];
    } 
    MessageDigest md = MessageDigest.getInstance("MD5"); 
    md.update(user_pword.getBytes()); 
    byte[] bytes = md.digest();
    StringBuilder sb = new StringBuilder();
    for(int i=0;i<bytes.length;i++){
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100,16).substring(1));
    }
    md5_password = sb.toString();             
    return md5_password;    
   }
   
    /**
    * Generates an MD5 password
    * @param password
    * @return password
    * @throws NoSuchAlgorithmException 
    */
   protected String getMD5Password(String password) throws NoSuchAlgorithmException {
    String md5_password = null;
    MessageDigest md = MessageDigest.getInstance("MD5"); 
    md.update(password.getBytes()); 
    byte[] bytes = md.digest();
    StringBuilder sb = new StringBuilder();
    for(int i=0;i<bytes.length;i++){
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100,16).substring(1));
    }
    md5_password = sb.toString();             
    return md5_password;    
   }
   
   
   /**
    * get string value from an array of characters
    * @param characters
    * @return string
    * @throws NoSuchAlgorithmException 
    */
   protected String charToString(char [] characters) throws NoSuchAlgorithmException{
    String string = null;
    char [] chars = characters;
    int size = chars.length;
    String new_string = new String(); 
    for(int x = 0;x<size;x++){
        new_string = new_string + chars[x];
    } 
    string = new_string;                
    return string;    
   } 
    
}
