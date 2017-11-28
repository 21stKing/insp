/*
 * Copyright (c) 2014, 2014 kinotech and/or its affiliates.
 * All Rights Reserved. The use of InSP is 
 * subject to all terms and conditions.
 * 
 * Written by kinotech (Pty) Ltd, December 2014.
 * http://www.kinotech.co.za
 *
 */ 

package inventory_system;

import java.util.Arrays;

/**
 * @author kinotech (Pty) Ltd. 
 * 
 * GlobalField Class is a generic class with variable values that are accessible through out the entire application.
 * 
 * Example: 
 * 
 * GlobalField gf = new GlobalField(); - class initialization
 * gf.setSize(2);                      - number of variables/fields to be used
 * gf.setField("STRING_FIELD");
 */
public class GlobalField {
    
    private static String [] fields;
    
    public void GlobalField(){  }
    
    /**
     * Sets the number of fields/variables to be used
     * @param size 
     */
    public static void setSize(int size){
        fields = new String[size];
    } 
    
    /**
     * Returns the number/size of fields
     * @return 
     */
    public static int getSize(){
        try{
            return fields.length;
        }catch(NullPointerException e){
           
            return 0;
        }
        
    }
        
    /**
     * Sets the field
     * @param field 
     */
    public static void setField(String field){
        int x = 0;       
        do {
            fields[x] = field;
        } while(fields[x].isEmpty() || fields[x].equals(""));  
        
    }
    
    /**
     * Returns the specified field by position
     * @param position
     * @return 
     */
    public static String getField(int position){        
        return fields[position];
    }
    
    /**
     * Clears the fields
     */
    public static void clear(){
        Arrays.fill(fields, "");
    }
    
    public static boolean isEmpty(){
       if(fields.length == 0){
           return true;
       }else{
           return false;
       }
    }
    
}
