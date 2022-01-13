/*
    Programmers: Aaron Zhu and James Huynh
    Teacher: Ms. Krasteva
    Date: January 14th, 2022
    Description: One-player battleship game for ICS3U ISP
    
    Be the first to sink all of the enemy's ships! Each time the user plays the game,
    a randomly generated board will be curated for the user and the enemy.
    For this version of battleship, the enemy is a computer AI. 
    Strategically hit coordinates of your board, and sink the ships. Ship sizes range from
    1x1 to 5x1, adding an aspect of luck to the game.
    
    Our specific implementation of Battleship also includes a "cheat mode". This enables the user to see
    where the enemy's ships are. However, when in cheat mode, user's scores will not be added to the leaderboard.
    
    Have fun!
*/
import java.awt.*;
import java.io.*;
import hsa.Console;

public class Battleship
{
    /*
	Variable Name              Type              Description
	c                          Console           instance of the Console class that will be used throughout the program
	p                          Palette           instance of the Palette class that will be used throughout the program
	t                          Tools             instance of the Tools class that will be used throughout the program
	action                     int               This integer stores what key the user presses on the main menu
    */
    static Console c; // console
    static Palette p; // palette
    static Tools t; // tools
    int action = 0; // action (used in main menu)

    /*
	The constructor for the Battleship class
	Intializes the console, palette, and tools.
    */
    public Battleship ()
    {
	c = new Console (27, 100, "Battleship");
	p = new Palette ();
	t = new Tools (c);
    }

    /*
	This public method runs the splash screen by initializing the classes 
	required to run it, and joining them with the main thread
	
	Variable Name       Type            Description
	g                   Splash_Ship     instance of the class to draw a ship on the console
	t1                  Splash_Waves    instance of the class to draw the waves on the console
	a                   Splash_Bomb     insteance of the class to draw a bomb on the console
	b                   Splash_Bomb     second instance of the class to draw a bomb on the console
	t2                  Thread          creates a new thread for the first bomb
	t3                  Thread          creates a new thread for the second bomb
    */

    public void runSplash ()
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
    
    
    /*
	This public method runs the main menu of the game, 
	where the inputs are recieved to navigate menus
	
	Variable Name           Type          Description
	key                     char          used to clear buffer before main menu is run
	input                   String        stores the user's input
	
	While Loop 1:
	    clears the buffer using c.isCharAvail()
    */

    public void runMenu ()
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

    
    /*
    This public method handles the playing of the game, by instancing the PlayGame class.
    Variable Name              Type              Description
    game                       PlayGame          This stores the instanced class that runs the game.
    userWin                    boolean           This variable stores whether the user won or not.
    */
    public void startGame () throws IOException
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

    // This public method draws the instructions screen.
    public void instructions () 
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
	c.drawString ("When the game starts, you will select a square to fire at. If you hit", 50, 100);
	c.drawString ("an enemy ship, the game will alert you. If you do not, then it will go", 50, 125);
	c.drawString ("to the enemy's turn, where they will fire at your board (in the top left).", 50, 150);
	c.drawString ("The first to sink all opponent ships will win. There are 5 boats, each 1 wide", 50, 175);
	c.drawString ("and between 1 and 5 long. If you hit a ship, you will be able to fire again.", 50, 200);
	c.drawString ("Sinking a ship will reveal all squares surrounding it, as there will", 50, 225);
	c.drawString ("be no ships next to another ship. A round's score is calculated by starting", 50, 250);
	c.drawString ("at 2500, and adding/subtracting points based on if the user hits.", 50, 275);
	c.drawString ("To play the game, use wasd to move your cursor, space to hit, and", 50, 300);
	c.drawString ("and c to enable cheat mode, if any.", 50, 325);

	t.pauseProgram (230, 400, 25, "Press any key to return.", Color.white);
    }

    /*
	This public method reads the scores.txt file 
	and outputs it to the screen
	
	Variable Name       Type                Description
	input               BufferedReader      used to read the leaderboard file
	name                String              stores the user's name for each leaderboard entry
	score               int                 stores the user's score for each leaderboard entry
	button              String              gets the user input, to either clear or exit the leaderboard page
	wipe                PrintWriter         used to clear the leaderboard file
	i                   int                 for loop iterator
	
	For Loop 1:
	    display the leaderboard            
    */
    public void leaderboard () throws IOException
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
	if (button.equals (" "))
	{
	    PrintWriter wipe = new PrintWriter (new FileWriter ("scores.txt"));
	    wipe.println ("");
	    t.errorMessage ("Wiped!", "Wiped.", 1);
	    // wipe
	    c.setColor (p.OCEAN_BLUE);
	    c.fillRect (100, 130, 250, 250);
	}
    }

    // public method that that displays when the user wants to exit the game
    public void goodbye ()
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
	c.drawString ("This program was made for ICS3UP ISP.", 150, 210);
	t.pauseProgram (280, 400, 25, "Press any key to exit.", Color.white);
	c.close ();
    }

    /*
	Main method --> controls program flow
	
	Variable Name       Type            Description
	g                   Battleship      instance of the Battleship class, to run the program
	
	While Loop:
	    while the user doesn't want to leave the program, 
	    prompt for what the user wants to do
	    1 = start the game
	    2 = view instructions
	    3 = view leaderboard
	    4 = exit
	    anything else will be considered as invalid input
    */
    public static void main (String[] args) throws IOException
    {
	Battleship g = new Battleship (); //Creates a new Battleship class
	g.runSplash (); // runs splashscreen
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


