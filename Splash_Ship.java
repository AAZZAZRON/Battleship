/*
* James Huynh and Aaron Zhu
* Ms. Krasteva
* ISP ICS3UP
* This class draws and animates the boat on the splashscreen.
* Dec 19, 2021

Variable Name         Type         Description
c                   Console        Same console as the one in Battleship.java, as it is passed through the constructor
*/
import java.awt.*;
import hsa.Console;

public class Splash_Ship extends Thread
{
    static Console c;           // The output console
    private static Tools t = new Tools (c);
    private Palette p = new Palette ();
    public Splash_Ship(Console c){
	this.c = c;
    }
    public void run ()
    {
	c.setColor (p.SKY_BLUE);
	c.fillRect (0, 0, 800, 350);
	c.setColor (p.SHIP_GRAY);
	int[] x = {200, 600, 550, 250};
	int[] y = {270, 270, 350, 350};
	c.fillPolygon (x, y, 4);
	c.fillRect (250, 250, 200, 40);
	c.fillRect (300, 220, 150, 40);
	c.fillRect (360, 150, 50, 100);
	c.setColor (new Color (207, 226, 243));
	c.fillOval (410, 235, 30, 30);
	c.fillOval (360, 235, 30, 30);
	c.fillOval (310, 235, 30, 30);
	c.setColor (Color.yellow);
	c.fillRect (500, 260, 20, 10);
	c.setColor (Color.white);
	c.setFont (new Font ("Lucida Sans Typewriter Regular", Font.BOLD, 25));
	c.drawString ("BATTLESHIP", 320, 300);
	c.drawString ("Aaron Z and James H", 280, 330);

    }
} // Splash_Ship class
