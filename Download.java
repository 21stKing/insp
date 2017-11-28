package inventory_system;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JOptionPane;
//import static store_inventory_system.MainWindow.download_lb;


public class Download {
    private String libURL;
    protected File libFile;
    private float percentage;
    protected boolean state;
    
    public Download(String file_url, File filename)
    {
       this.libURL = file_url;
       this.libFile = filename;
    }
    
    public void downloadFile()
    { 
        try
        {   
            URL url = new URL(libURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
            int filesize = connection.getContentLength();
            float totalDataRead = 0;
            BufferedInputStream in = new java.io.BufferedInputStream(connection.getInputStream());
            FileOutputStream fos = new java.io.FileOutputStream(libFile);
            BufferedOutputStream bout = new BufferedOutputStream(fos,1024);
            byte[] data = new byte[1024];
            int i = 0;
            MainWindow.download_lb.setText("Downloading..."+libURL); 
            MainWindow.download_lb.setToolTipText(""+libURL); 
            while((i = in.read(data,0,1024))>=0)
                {
                    totalDataRead = totalDataRead + i;
                    bout.write(data,0,i);
                    float Percent = (totalDataRead*100) / filesize;
                    //setPercentage(Percent);
                    MainWindow.progressBar.setValue((int) Percent); 
                }  
            bout.close(); 
            in.close();
            
            //download complete
            MainWindow.download_lb.setHorizontalAlignment(MainWindow.download_lb.TRAILING); 
            MainWindow.download_lb.setText("Download complete");
            state = true;
          }
          catch(Exception e)
          {
             JOptionPane.showConfirmDialog(null,e.getMessage(), "Error - FileNotFound",JOptionPane.DEFAULT_OPTION);
             state = false;
          }
      }
    
   /* private void setPercentage(float percent){
        this.percentage = percent;
    }
    
    protected float getPercentage(){
        return this.percentage;
    }*/
}