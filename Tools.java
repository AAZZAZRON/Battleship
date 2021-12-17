/*
* James Huynh and Aaron Zhu
* Ms. Krasteva
* ISP ICS3UP
* This class contains all of the tools we will be using in the other classes.
* Dec 17, 2021

Variable Name         Type         Description
c                   Console        Same console as the one in Battleship.java, as it is passed through the constructor
*/

import hsa.*;
import java.awt.*;
import javax.swing.*;
public class Tools
{
    private Console c;

    /*
    The constructor of the Tools class
    
    Variable Name         Type         Description
    cC                  Console        cC stands for constructor console, it is the console passed through from Battleship.java
    */
    public Tools (Console cC)
    {
	c = cC;
    }


    /*
    This method pauses the program and will continue when the user presses any button.
    
    Variable Name         Type         Description
    x                     int          The x value to draw the string at.
    y                     int          The y value to draw the string at.
    size                  int          The size of the font to draw
    message               String       The message drawn.
    */
    public void pauseProgram (int x, int y, int size, String message)
    {
	c.setColor (Color.black);
	c.setFont (new Font ("Lucida Sans Typewriter Regular", 1, size));
	c.drawString (message, x, y);
	c.getChar ();
    }


    /*
    This method will pause the program for the specified amount of milliseconds
    
    Variable Name         Type         Description
    ms                    int          The milliseconds to sleep for.
    */
    public void sleep (int ms) 
    {
	try {
	    Thread.sleep (ms);
	} catch (Exception e) {}
    }

    /*
    This method will pause the program for the specified amount of milliseconds
    
    Variable Name         Type         Description
    text                  String       The text drawn on the popup
    output                String       The text the user inputs, is returned
    */

    public String inputMessage (String text)
    {
	String output = JOptionPane.showInputDialog (text);
	return output;
    }


    /*
    This method will create an error popup.
    
    Variable Name         Type         Description
    title                 String       The title of the message
    message               String       The text on the popup
    label                 int          The label of the popup
				       0 - error
				       1 - info
				       2 - warning
				       3 - help/question
    */
    public void errorMessage(String message, String title, int label){
	JOptionPane.showMessageDialog(new JFrame(), message, title, label);
    }
}
