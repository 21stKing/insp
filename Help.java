
package inventory_system;

import java.awt.Image;
import javax.help.*;
import java.net.URL;
import java.text.Normalizer.Form;
import javax.swing.*;


public class Help
{  
   JHelp helpViewer = null;   
   JFrame frame;
   
   public Help()
   {
       try
        {
	  ClassLoader cl = Help.class.getClassLoader();
          URL url = HelpSet.findHelpSet(cl, "config_help/jhelpset.hs");
          helpViewer = new JHelp(new HelpSet(cl, url));
          helpViewer.setCurrentID("top");
	} catch (Exception e) 
        {
	  System.err.println("API Help Set not found\n\n"+e);
     	}
       
       frame = new JFrame();
       frame.setSize(800,700);
       frame.getContentPane().add(helpViewer);
       frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       frame.setLocationByPlatform(true); 
       frame.setVisible(true);
       
   }
   
   public void setIconImage(String imagePath)
   {
       frame.setIconImage(new ImageIcon(getClass().getResource(imagePath)).getImage());
   }
    
   public static void main(String args[])
   {
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run()
            {
                new Help();
            }
        });
   }
    
}
