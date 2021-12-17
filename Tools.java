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
public class Tools
{
    private Console c;
    
    /*
    The constructor of the Tools class
    Variable Name         Type         Description
    cC                  Console        cC stands for constructor console, it is the console passed through from Battleship.java
    */
    public Tools(Console cC){
        c = cC;
    }
    /*
    This method pauses the program and will continue when 
    Variable Name         Type         Description
    */
    public void pauseProgram(int x,int y, int size, String message){
        c.setColor(Color.white);
        c.setFont(new Font("Lucida Sans Typewriter Regular",1,size));
        c.drawString(message,x,y);
    }
} // Tools class
