/*
* James Huynh and Aaron Zhu
* Ms. Krasteva
* ISP ICS3UP
* This class contains the method to draw the waves.
* Dec 17, 2021

Variable Name         Type         Description
c                   Console        Same console as the one in Battleship.java, as it is passed through the constructor
p                    Palette       Holds the colours
t                    Tools         Holds commonly used tools
*/

import java.awt.*;
import hsa.Console;

public class Splash_Waves extends Thread
{
    private Console c;
    private Tools t = new Tools (c);
    private Palette p = new Palette ();
    /*
    Variable Name         Type         Description
    cC                    Console      This is the inputted console.
    */
    public Splash_Waves (Console cC)
    {
	c = cC;
    }


    /*
    This public method is run when start() is called on the thread.
    For Loop 1: Animates the waves
    For Loop 2: Draws 12 arcs that make up the wave
    Variable Name         Type         Description
    i                     int          Controls the for loop and increments for each frame of animation.
    j                     int          Controls the for loop and increments for each arc.
    shift                 int          Controls the shifting from left to right of the waves.
    */
    public void run ()
    {
	int shift = -30;
	for (int i = 0 ; i < 300 ; i++)
	{
	    if (i % 50 >= 25)
	    {
		shift--;
	    }
	    else
	    {
		shift++;
	    }

	    for (int j = 0 ; j < 12 ; j++)
	    {
		c.setColor (p.OCEAN_BLUE);
		c.fillArc (-1 + (j * 70) + shift, 409, 102, 100, 29, 122);
		c.fillArc (9 + (j * 70) + shift, 419, 82, 90, -1, 182);
		c.setColor (p.SKY_BLUE);
		c.fillArc ((j * 70) + shift, 410, 100, 100, 30, 120);
		c.setColor (p.OCEAN_BLUE);
		c.fillArc (10 + (j * 70) + shift, 420, 80, 90, 0, 180);

		c.setColor (p.OCEAN_BLUE);
		c.fillArc (-11 + (j * 70) + shift, 479, 102, 100, 29, 122);
		c.fillArc (-1 + (j * 70) + shift, 489, 82, 90, -1, 182);
		c.setColor (p.SKY_BLUE);
		c.fillArc (-10 + (j * 70) + shift, 480, 100, 100, 30, 120);
		c.setColor (p.OCEAN_BLUE);
		c.fillArc (0 + (j * 70) + shift, 490, 80, 90, 0, 180);
	    }
	    t.sleep (20);
	}

    }
}
