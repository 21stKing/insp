package inventory_system;

import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.beans.*; //property change stuff
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/* 1.4 example used by DialogDemo.java. */
class ChangePasswordGUI extends JDialog implements ActionListener,PropertyChangeListener 
{
    private JLabel photoLabel = new JLabel();
    private JOptionPane optionPane;
    private String btnString1 = "Change";
    private String btnString2 = "Cancel";
    public Object value = "";
    public ImageIcon icon;
    private static String [] lDetails;
    private static String compid;
    private static String name;
    private static String surname;
    private static String fullname;
    protected JPasswordField new_pword_field;
    protected JPasswordField cnew_pword_field; 
    
    /** Creates the reusable dialog. */
    public ChangePasswordGUI(Frame aFrame,ImageIcon iconIn,String [] details)
    {   
        super(aFrame, true);
        this.setSize(345,400);
        photoLabel.setBackground(new java.awt.Color(255, 255, 255));
        photoLabel.setPreferredSize(new Dimension(166,165)); 
        photoLabel.setIcon(iconIn); 
        name       = details[1];
        surname    = details[2];
        compid     = details[0];
        fullname   = name+" "+surname;
        icon = iconIn;
        lDetails = details;
      
        new_pword_field = new JPasswordField();
        cnew_pword_field =  new JPasswordField();
        
        setTitle("Password Change");

        //Create an array of the text and components to be displayed.
        String msgString1 = "<html>Welcome <b>"+fullname+"</b><br><br>Please type in a new password below:</html>";
        //String msgString2 = "<html>Group: <b>"+group+"</b><br>Level: <b>"+level+"</b></html>";
        String pword = "New password:";
        String cpword = "Confirm password:";
        Object[] array = {photoLabel,msgString1,pword,new_pword_field,cpword,cnew_pword_field};

        //Create an array specifying the number of dialog buttons
        //and their text.
        Object[] options = {btnString1,btnString2 };

        //Create the JOptionPane. 
       // optionPane.setBackground(Color.white);
        optionPane = new JOptionPane(array,JOptionPane.PLAIN_MESSAGE,JOptionPane.YES_NO_CANCEL_OPTION,null,options,options[0]);
  
        //Make this dialog display it.
        setContentPane(optionPane);

        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                    optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
            }
        });

        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);
    }

    /** This method handles events for the text field. */
    public void actionPerformed(ActionEvent e) {
        optionPane.setValue(btnString1);
    }

    /** This method reacts to state changes in the option pane. */

    public void propertyChange(PropertyChangeEvent e)
    {
        String prop = e.getPropertyName();    
        
        if (isVisible() && (e.getSource() == optionPane) && (JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) 
        {
          value = optionPane.getValue();
         
          if (value == JOptionPane.UNINITIALIZED_VALUE)
            {
                //ignore reset
                return;
            }
            //Reset the JOptionPane's value.
            //If you don't do this, then if the user
            //presses the same button next time, no
            //property change event will be fired.
           // optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
          
        }
        
        clearAndHide();
            
        
    }
    
       /** This method clears the dialog and hides it. */
    public void clearAndHide()
    {
        setVisible(false);
    }
    
     
    
    
}