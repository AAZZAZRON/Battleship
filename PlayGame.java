/*
    Programmers: Aaron Zhu and James Huynh
    Teacher: Ms. Krasteva
    Date: January 14th, 2022
    Description: PlayGame.java

    Controls the game flow/logic for Battleship
*/

import java.awt.*;
import java.io.*;
import hsa.Console;

public class PlayGame
{
    /*
	Variable Name        Type           Description
	c                    Console        stores the console instance that was initialized in Battleship.java
	p                    Palette        stores the palette instance that was initialized in Battleship.java
	t                    Tools          stores the tools instance that was initialized in Battleship.java
	user                 Board          stores the user's board for this round
	enemy                EnemyBoard     stores the enemy's board for this round
	finished             boolean        stores if the game is finished
	cheat                boolean        stores if the user is playing in cheat mode (initialized in selectCheat())
	turns                int            stores the number of turns the game has been going for
	hits                 int            stores the number of hits that the user has made
	score                int            stores the score of the user
    */
    private Console c;
    private Palette p;
    private Tools t;
    Board user;
    EnemyBoard enemy;
    boolean finished;
    boolean cheat;
    int turns;
    int hits;
    int score;

    /*
	Constructor for the PlayGame class

	Variable Name      Type       Description
	cC                 Console    console passed from Battleship.java
	cP                 Palette    palette passed from Battleship.java
	cT                 Tools      tools passed from Battleship.java
    */
    public PlayGame (Console cC, Palette cP, Tools cT)
    {
	c = cC;
	p = cP;
	t = cT;
	user = new Board (c, p, t);
	enemy = new EnemyBoard (c, p, t);
	finished = false;
	turns = 1;
	hits = 0;
	score = 1000;
    }


    public void selectCheat ()
    {
	cheat = true;
    }


    /*
	Public method to run the "game logic"

	Variable Name         Type         Description
	key                   char         stores the key that the user pressed
					   used to move user's cursor, hit ships, etc.

	While Loop 1:
	    constantly runs the user's move, then the computer's move, until one of the two wins
	While Loop 2:
	    constantly get user input until they choose to hit a square, [cursorX, cursorY], and
	    that square has not been hit already
    */
    public boolean play ()
    {
	char key = '\u0000'; // local variable to score keypressed

	user.generateShips (5); // generate user ships
	enemy.generateShips (5); // generate enemy ships

	drawBackground (); // draw board
	enemy.moveCursor ('q'); // draw the cursor at [0, 0]
	enemy.remaining (turns, hits, score); // draw default stats

	t.errorMessage ("Your Turn!", "Your Turn", 1);
	while (!finished)
	{ // while the game isn't finished
	    enemy.remaining (turns, hits, score);
	    // only exit while loop if the user wants to hit and that square has not been hit yet
	    while (!(key == ' ' && !enemy.visited [enemy.cursorX] [enemy.cursorY]))
	    {
		// checks if any keys are in the buffer
		// this method was found on http://www.staugustinechs.ca/cadawas/hsa/console.html
		// if buffer contains a key, remove the key from the buffer
		if (c.isCharAvail ())
		{
		    key = c.getChar (); // grabs the key out of the buffer
		    key = '\u0000'; // resets the key
		}
		else
		    key = c.getChar ();      // when the buffer is cleared, ask the user for an input

		if ("wasdWASD".indexOf (key) != -1)
		{
		    if (enemy.cheatsOn)
			enemy.toggleCheat ();                     // turn off cheats
		    enemy.moveCursor (key); // if key is directional, move cursor
		}
		else if (key == 'c' && cheat)
		    enemy.toggleCheat ();                               // if key is cheat and cheats are on, turn on/off cheats
	    }
	    if (!enemy.hit ())
	    {
		score -= 10;
		enemy.remaining(turns, hits, score);
		t.errorMessage ("Enemy's Turn!", "Enemy's Turn", 1);
		enemyTurn ();
		turns += 1;
		if (user.remaining != 0)
		    t.errorMessage ("Your Turn!", "Your Turn", 1);
	    }
	    else
	    {
		hits += 1;
		score += 25 + 2 * (int) (21 * Math.random() + 1); // 150 for every hit + RNG
	    }
	    if (hits == 15 || user.remaining == 0)
		finished = true;                                        // exit game is over
	}
	if (hits == 15)
	{
	    t.errorMessage ("Nice! You found all the ships in " + turns + " moves.", "SUCCESS", 1);
	    return true; // user won
	}
	else
	{
	    t.errorMessage ("Aww... The enemy has sunk all your ships.", "FAIL", 1);
	    return false;
	}
    }


    /*
	Private method to draw the "canvas" that the game will be hosted on
	Called in playGame()

	Variable Name         Type              Description
	checked               boolean[]         stores if ship[i] has been drawn to teh console
	val                   int               temporary storage for the ship located at the current coordinate
	BUFF                  final int         constant the startX and startY of the user's board on the console
	GRID_SIZE             final int         constant representing the size of a coordinate on the console

	For Loop 1:
	    draws the 10x10 grid, representing the user's battleship board
	For Loop 2:
	    draws the 10x10 grid, representing the enemy's battleship board
	For Loop 3:
	    loop through all the rows of the user's board, to draw the user's ships
	For Loop 4:
	    loop through all the columns of the user's board, to draw the user's ships
    */
    private void drawBackground ()
    {
	// graphics for game
	c.setColor (p.CONSOLE_GRAY);
	c.fillRect (10, 10, 800, 540);

	c.setColor (p.BOARD_BACKGROUND);
	c.fillRect (20, 20, 250, 250);
	c.fillRect (20, 280, 250, 250);
	c.fillRect (285, 25, 500, 500);

	c.setColor (p.TEXT_GREEN);

	// user's board
	c.drawRect (30, 30, 230, 230);
	for (int i = 30 + 23 ; i < 260 ; i += 23)
	{
	    c.drawLine (i, 30, i, 260);
	    c.drawLine (30, i, 260, i);
	}

	// enemy's board
	c.drawRect (300, 40, 470, 470);
	for (int i = 0 ; i < 470 ; i += 47)
	{
	    c.drawLine (300 + i, 40, 300 + i, 510);
	    c.drawLine (300, 40 + i, 770, 40 + i);
	}

	// instructional text in case user doesn't read instructions
	c.setFont (new Font ("Lucida Sans Typewriter Regular", 1, 17));
	c.drawString ("use 'wasd' to move cursor", 30, 475);
	c.drawString ("use space to hit coordinate", 30, 495);
	if (cheat)
	    c.drawString ("use c to toggle cheats", 30, 515);

	c.setColor (p.SHIP_GRAY); // draw user's ships
	boolean[] checked = new boolean [6]; // if ship size i has been drawn yet
	int val;
	checked [0] = true;
	final int BUFF = 30; // buffer
	final int GRID_SIZE = 23;

	for (int i = 0 ; i < user.SIZE ; i += 1)
	{ // traverse grid
	    for (int j = 0 ; j < user.SIZE ; j += 1)
	    {
		val = user.hasShip [i] [j];
		if (!checked [val])
		{ // if it is a ship that has not been drawn yet
		    checked [val] = true; // mark that it has been drawn
		    if (i + 1 != user.SIZE && user.hasShip [i + 1] [j] == val)
			c.fillOval (BUFF + GRID_SIZE * i, BUFF + GRID_SIZE * j, GRID_SIZE * val, GRID_SIZE);
		    else
			c.fillOval (BUFF + GRID_SIZE * i, BUFF + GRID_SIZE * j, GRID_SIZE, GRID_SIZE * val);
		}
	    }
	}
    }


    private void enemyTurn ()
    {
	int x = (int) (user.SIZE * Math.random ());
	int y = (int) (user.SIZE * Math.random ());
	boolean keepGoing = true;
	while (keepGoing && user.remaining != 0)
	{
	    while (user.visited [x] [y])
	    { // while the position has been visited, pick another position
		x = (int) (user.SIZE * Math.random ());
		y = (int) (user.SIZE * Math.random ());
	    }
	    if (!user.hit (x, y))
		keepGoing = false;
	    t.sleep (2000); // delay so it's more realistic
	}
    }


    public void storeScore () throws IOException
    {
	String name = t.inputMessage ("give me name :D");
	int fileL = 0;
	String[] names = new String [12];
	int[] scores = new int [12];
	int buffer = 0;
	String lbName;
	int lbScore;

	// get length of file
	BufferedReader inp = new BufferedReader (new FileReader ("scores.txt"));
	while (true)
	{
	    String line = inp.readLine (); // name
	    if (line != null)
	    {
		line = inp.readLine (); // score
		fileL++;
	    }
	    else
	    {
		break;
	    }
	}

	inp.close ();
	// insert score into what we have
	BufferedReader input = new BufferedReader (new FileReader ("scores.txt"));
	for (int i = 0 ; i < fileL ; i += 1)
	{
	    lbName = input.readLine ();
	    lbScore = Integer.parseInt (input.readLine ());
	    if (buffer != 1 && score > lbScore)
	    { // insert user's score
		scores [i] = score;
		names [i] = name;
		buffer += 1;
	    }
	    scores [i + buffer] = lbScore;
	    names [i + buffer] = lbName;

	}
	if (buffer == 0)
	{
	    scores [fileL] = score;
	    names [fileL] = name;
	    if (fileL != 10)
		buffer += 1;
	}
	if (buffer == 1)
	    t.errorMessage ("Your score made it onto the leaderboard!", "CONGRAGULATIONS", 1);
	input.close ();
	PrintWriter output = new PrintWriter (new FileWriter ("scores.txt"));
	for (int i = 0 ; i < Math.min (10, fileL + 1) ; i += 1)
	{
	    output.println (names [i]);
	    output.println (scores [i]);
	}
	output.close ();
    }
}
