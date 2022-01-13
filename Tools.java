/*
    Programmers: Aaron Zhu and James Huynh
    Teacher: Ms. Krasteva
    Date: January 14th, 2022
    Description: Tools.java

    Stores common functions that will be needed in other classes
*/

import hsa.*;
import java.awt.*;
import javax.swing.*;
public class Tools
{
    /*
	Variable Name       Type           Description
	c                   Console        Same console as the one in Battleship.java, as it is passed through the constructor
	p                   Palette        Sama palette as the one in Battleship.java, as it is passed through the constructor
    */
    private Console c;
    private Palette p;

    /*
    The constructor of the Tools class

    Variable Name         Type         Description
    cC                  Console        cC stands for constructor console, it is the console passed through from Battleship.java
    */
    public Tools (Console cC)
    {
	c = cC;
	p = new Palette ();
    }


    /*
    This method pauses the program and will continue when the user presses any button.

    Variable Name         Type         Description
    x                     int          The x value to draw the string at.
    y                     int          The y value to draw the string at.
    size                  int          The size of the font to draw
    message               String       The message drawn.
    */
    public void pauseProgram (int x, int y, int size, String message, Color col)
    {
	c.setColor (col);
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
	try
	{
	    Thread.sleep (ms);
	}
	catch (Exception e)
	{
	}
    }


    /*
    This method will pause the program for the specified amount of milliseconds

    Variable Name         Type         Description
    text                  String       The text drawn on the popup
    output                String       The text the user inputs, is returned
    */
    public String inputMessage (String text)
    {

	String output = "";
	c.setColor (p.CONSOLE_GRAY);
	c.fillRect (10, 200, 800, 140);
	c.setColor (p.BOARD_BACKGROUND);
	c.fillRect (20, 210, 765, 120);
	c.setCursor (14, 5);
	c.setTextBackgroundColor (p.BOARD_BACKGROUND);
	c.setTextColor (Color.green);
	c.print (text);
	output = c.readLine ().trim ();
	
	c.setTextBackgroundColor (Color.white);
	c.setTextColor (Color.black);
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
    public void errorMessage (String message, String title, int label)
    {
	JOptionPane.showMessageDialog (new JFrame (), message, title, label);
    }
}
