/*
* James Huynh and Aaron Zhu
* Ms. Krasteva
* ISP ICS3UP
* This class contains
* Dec 17, 2021

Variable Name         Type         Description
c                   Console        Same console as the one in Battleship.java, as it is passed through the constructor
*/

import java.awt.*;
import hsa.Console;

public class Splash_Waves extends Thread
{
    private static Console c;
    Tools t = new Tools (c);
    Palette p = new Palette ();

    public void run ()
    {
	int shift = -30;
	for (int i = 0 ; i < 200 ; i++)
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
