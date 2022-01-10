/*
    Programmers: Aaron Zhu and James Huynh
    Teacher: Ms. Krasteva
    Date: January 14th, 2022
    Description: One-player battleship game for ICS2U ISP

    Variable Name              Type              Description
    action                     int               This integer stores what key the user presses on the main menu.
*/
import java.awt.*;
import java.io.*;
import hsa.Console;

public class Battleship
{
    static Console c; // console
    static Palette p; // palette
    static Tools t; // tools
    int action = 0; // action (used in main menu)


    public Battleship ()  // This public method is the constructor for the Battleship class, and intializes the console, palette, and tools.
    {
	c = new Console (27, 100, "Battleship");
	p = new Palette ();
	t = new Tools (c);
    }


    public void runSplash () // This public method runs the splashscreen by creating the classes required to run it, and joining them with the main thread.
    {
	Splash_Ship g = new Splash_Ship (c);
	g.start ();
	try
	{
	    g.join ();
	}
	catch (Exception e)
	{
	}
	Splash_Waves t1 = new Splash_Waves (c);
	t1.start ();
	Splash_Bomb a = new Splash_Bomb (c, 50, 0, 5);
	Thread t2 = new Thread (a);
	t2.start ();
	Splash_Bomb b = new Splash_Bomb (c, 700, 1000, 5);
	Thread t3 = new Thread (b);
	t3.start ();
	try
	{
	    t1.join ();
	}
	catch (Exception e)
	{
	}
    }


    public void runMenu () // This public method runs the main menu of the game, where the inputs are recieved to navigate menus.
    {
	// checks if any keys are in the buffer
	// this method was found on http://www.staugustinechs.ca/cadawas/hsa/console.html
	// if buffer contains a key, remove the key from the buffer
	while (c.isCharAvail ())
	{
	    char key = c.getChar (); // grabs the key out of the buffer
	}
	c.clear ();
	c.setColor (p.SKY_BLUE);
	c.fillRect (0, 0, 800, 350);
	c.setColor (p.OCEAN_BLUE);
	c.fillRect (0, 350, 800, 300);
	c.setFont (new Font ("Lucida Sans Typewriter Regular", Font.BOLD, 25));
	c.setColor (Color.white);
	c.drawString ("BATTLESHIP", 320, 50);
	c.drawString ("Press \"1\" to play.", 300, 100);
	c.drawString ("Press \"2\" for instructions.", 245, 150);
	c.drawString ("Press \"3\" for scores.", 280, 200);
	c.drawString ("Press \"4\" to exit.", 300, 250);
	c.setColor (new Color (179, 128, 94));
	c.fillArc (50, 400, 100, 50, 180, 180);
	c.setColor (Color.yellow);
	c.fillRect (60, 415, 20, 10);
	String input = String.valueOf (c.getChar ());
	try
	{
	    action = Integer.parseInt (input);
	    if (action < 1 || action > 4)
		throw new IllegalArgumentException ();
	}
	catch (Exception e)
	{
	    t.errorMessage ("Please press a valid button.", "Error", 2);
	}
    }


    public void startGame () throws IOException // This public method handles the playing of the game, by instancing the PlayGame class.
    {
	c.clear ();
	PlayGame game = new PlayGame (c, p, t);
	game.selectCheat ();
	if (game.finished)
	    return;                    // don't run game if user exits
	boolean userWin = game.play ();
	if (userWin && !game.cheat)
	    game.storeScore ();
	// pause program/return to main menu
	c.setColor (p.CONSOLE_GRAY);
	c.fillRect (10, 200, 800, 140);
	c.setColor (p.BOARD_BACKGROUND);
	c.fillRect (20, 210, 765, 120);
	t.pauseProgram (120, 280, 30, "Press any key to return to main menu...", p.TEXT_GREEN);
    }


    public void instructions () // This public method draws the instructions screen.
    {
	c.clear ();
	c.setColor (p.SKY_BLUE);
	c.fillRect (0, 0, 800, 350);
	c.setColor (p.OCEAN_BLUE);
	c.fillRect (0, 350, 800, 300);
	c.setFont (new Font ("Lucida Sans Typewriter Regular", Font.BOLD, 25));
	c.setColor (Color.white);
	c.drawString ("BATTLESHIP", 320, 50);
	c.setFont (new Font ("Lucida Sans Typewriter Regular", Font.BOLD, 20));
	c.drawString ("When the game starts, you will select a square to fire at, if you hit", 70, 100);
	c.drawString ("an enemy ship, the game will alert you. If you do not, then it will go", 70, 125);
	c.drawString ("to the enemy's turn, where they will fire at your board in the top left.", 70, 150);
	c.drawString ("The first to sink all enemy ships will win. There are 5 boats, each 1 wide", 70, 175);
	c.drawString ("and between 1 and 5 long. When you get a hit, you will be able to fire again.", 70, 200);
	c.drawString ("When you sink a ship, it will reveal all squares surrounding it, as there", 70, 225);
	c.drawString ("will be no ships next to another ship.", 70, 250);
	t.pauseProgram (230, 400, 25, "Press any key to return.", Color.white);
    }


    public void leaderboard () throws IOException // This public method reads the scores.txt file and outputs it to the screen.
    {
	c.clear ();
	c.setColor (p.SKY_BLUE);
	c.fillRect (0, 0, 800, 400);
	c.setColor (p.OCEAN_BLUE);
	c.fillRect (0, 400, 800, 300);
	c.setFont (new Font ("Lucida Sans Typewriter Regular", Font.BOLD, 25));
	c.setColor (Color.white);
	c.drawString ("BATTLESHIP", 320, 50);
	c.drawString ("Player Name", 100, 100);
	c.drawString ("Score", 500, 100);
	c.setFont (new Font ("Lucida Sans Typewriter Regular", Font.BOLD, 20));

	BufferedReader input = new BufferedReader (new FileReader ("scores.txt"));
	
	/*
	This for loop runs 10 times to get the first 10 scores out of the file, and output it.
	Variable Name              Type              Description
	i                          int               Controls the for loop, to have it only run 10 times.
	*/
	for (int i = 0 ; i < 10 ; i++)
	{
	    try
	    {
		String name = input.readLine ();
		if (name != null)
		{
		    int score = Integer.parseInt (input.readLine ());
		    c.drawString (name, 100, 130 + (i * 25));
		    c.drawString ("" + score, 500, 130 + (i * 25));
		}
	    }
	    catch (Exception e)
	    {
		t.errorMessage ("Scores could not be loaded, please check the file!", "Error", 3); // This only happens if the score file is incorrectly formatted
	    }
	}
	input.close ();
	c.drawString ("Press any key to exit.", 280, 425);
	c.drawString ("Press space to clear scores.", 250, 450);
	String button = String.valueOf (c.getChar ());
	/*
	This while loop will run only when the user presses space to clear the file, and it essentially sets all of the lines in the file to ""
	*/
	while (true)
	{
	    if (button.equals (" "))
	    {
		PrintWriter wipe = new PrintWriter (new FileWriter ("scores.txt"));
		wipe.println ("");
		t.errorMessage ("Wiped!", "Wiped.", 1);

		// wipe
		c.setColor (p.OCEAN_BLUE);
		c.fillRect (100, 130, 250, 250);
		break;
	    }
	    else
		break;
	}
    }


    public void goodbye () //This public method handles the goodbye screen.
    {
	c.clear ();
	c.setColor (p.SKY_BLUE);
	c.fillRect (0, 0, 800, 350);
	c.setColor (p.OCEAN_BLUE);
	c.fillRect (0, 350, 800, 300);
	c.setFont (new Font ("Lucida Sans Typewriter Regular", Font.BOLD, 25));
	c.setColor (Color.white);
	c.drawString ("BATTLESHIP", 320, 50);
	c.drawString ("Thank you for playing!", 270, 150);
	c.drawString ("Made by Aaron Z and James H", 220, 180);
	t.pauseProgram (280, 400, 25, "Press any key to exit.", Color.white);
	c.close ();
    }


    public static void main (String[] args) throws IOException // The main method, where everything will be called.
    {
	Battleship g = new Battleship (); //Creates a new Battleship class
	//g.runSplash (); // runs splashscreen
	while (g.action != 4) // While user does not want to exit
	{
	    g.action = 0; // set action to 0 by default
	    g.runMenu (); //run the menu screen
	    if (g.action == 1) //start the game
		g.startGame ();
	    else if (g.action == 2) //instructions
		g.instructions ();
	    else if (g.action == 3) //leaderboard
		g.leaderboard ();
	}
	g.goodbye (); //goodbye screen
    }
}


