/*
* James Huynh and Aaron Zhu
* Ms. Krasteva
* ISP ICS3UP
* This class creates a bomb and draws it on the specified console, with specified x value, delay, and speed.
* Dec 19, 2021

Variable Name         Type         Description
c                   Console        Same console as the one in Battleship.java, as it is passed through the constructor
x                     int          Controls the starting x value of the bomb
delay                 int          Controls the delay before the bomb is drawn.
speed                 int          Controls the speed of the animation of the bomb.
p                    Palette       Holds the colours
t                    Tools         Holds commonly used tools
*/

import java.awt.*;
import hsa.Console;

public class Splash_Bomb implements Runnable
{
    private Console c;           // The output console
    private Tools t = new Tools (c);
    private Palette p = new Palette ();
    private int x = 0;
    private int delay = 0;
    private int speed = 0;
    /*
    This is the public constructor for this runnable, it accepts 3 parameters and sets the values to the parameters given.
    It also draws the background when called.
    Variable Name         Type         Description
    c                     Console      This is the inputted console.
    x                     int          This is the inputted x value.
    delay                 int          This is the inputted delay.
    speed                 int          This is the inputted speed.
    */
    public Splash_Bomb (Console c, int x, int delay, int speed)
    {
	this.c = c;
	this.x = x;
	this.delay = delay;
	this.speed = speed;
	c.setColor (p.OCEAN_BLUE);
	c.fillRect (0, 350, 800, 300);
    }


    /*
    This public method is run when start() is called on the thread.
    For Loop 1: Animates the bombs
    Variable Name         Type         Description
    i                     int          Controls the animation of each bomb.
    */
    public void run ()
    {
	t.sleep (delay);
	for (int i = 0 ; i < 470 ; i++)
	{
	    c.setColor (p.SKY_BLUE);
	    c.fillRect (x, -111 + i, 70, 85);
	    c.setColor (Color.LIGHT_GRAY);
	    c.fillRect (x + 30, -110 + i, 10, 20);
	    c.setColor (Color.black);
	    c.fillOval (x, -100 + i, 70, 70);
	    t.sleep (speed);
	}
	c.setColor (p.OCEAN_BLUE);
	c.fillRect (x - 50, 350, 800, 300);
    }
}
