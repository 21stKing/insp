/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory_system;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class JIconTextField extends JTextField{
 
    private Icon icon;
    private Insets dummyInsets;
 
    public JIconTextField(){
        super();
        this.icon = null;
 
        Border border = UIManager.getBorder("TextField.border");
        JTextField dummy = new JTextField();
        this.dummyInsets = border.getBorderInsets(dummy);
    }
 
    public void setIcon(Icon icon){
        this.icon = icon;
    }
 
    public Icon getIcon(){
        return this.icon;
    }
 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
 
        int textX = 2;
 
        if(this.icon!=null){
            int iconWidth = icon.getIconWidth();
            int iconHeight = icon.getIconHeight();
            int x = dummyInsets.left + 5;//this is our icon's x
            textX = x+iconWidth+2; //this is the x where text should start
            int y = (this.getHeight() - iconHeight)/2;
            icon.paintIcon(this, g, x, y);
        }
 
        setMargin(new Insets(2, textX, 2, 2));
 
    }
    
    public static void main(String[] args) throws Exception
    {
       JIconTextField  jiconr = new JIconTextField ();
       //smtpMailSender 
    }
 
}