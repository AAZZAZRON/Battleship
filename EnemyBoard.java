/*
    Programmers: Aaron Zhu and James Huynh
    Teacher: Ms. Krasteva
    Date: January 14th, 2022
    Description: EnemyBoard class (creates a board for the enemy)
*/

import java.awt.*;
import hsa.Console;

public class EnemyBoard
{
    /*
	Variable Name        Type           Description
	c                    Console        stores the console instance that was initialized in Battleship.java
	p                    Palette        stores the palette instance that was initialized in Battleship.java
	t                    Tools          stores the tools instance that was initialized in Battleship.java
	visited              boolean[][]    stores if coordinate [x, y] has been hit
	hasShip              int[][]        stores if coordinate [x, y] contains a ship
					    stores the size of the ship at [x, y]
	cheatsOn             boolean        stores if the board currently has cheats on
	shipSunk             boolean[]      stores if ship size i has been sunk
	cursorX              int            stores the user cursor's x coordinate
	cursorY              int            stores the user cursor's y coordinate
	SIZE                 final int      how big the board is
    */
    private Console c;
    private Palette p;
    private Tools t;
    boolean[] [] visited;
    int[] [] hasShip;
    boolean cheatsOn;
    boolean[] shipSunk;
    int cursorX;
    int cursorY;
    final int SIZE = 10;
    
    
    /*
	Constructor for the EnemyBoard class
	
	Variable Name      Type       Description
	cC                 Console    console passed from Battleship.java
	cP                 Palette    palette passed from Battleship.java
	cT                 Tools      tools passed from Battleship.java
    */
    public EnemyBoard (Console cC, Palette cP, Tools cT)
    {
	c = cC;
	p = cP;
	t = cT;
	visited = new boolean [SIZE] [SIZE];
	hasShip = new int [SIZE] [SIZE];
	cheatsOn = false;
	shipSunk = new boolean [6];
	shipSunk [0] = true;
	cursorX = 0;
	cursorY = 0;
    }

    /*
	Public method that generates a ship of size `size` onto the user's board
	
	Variable Name       Type        Description
	size                int         parameter indicating the size of the ship we want to place
	done                boolean     determines if the ship has been placed
	x                   int         stores the top left x coordinate we try to place the ship at
	y                   int         stores the top left y coordinate we try to place the ship at
	dir                 int         stores the direction we are trying to place the ship (horizontal or vertical)
	i                   int         for loop iterator
	
	While Loop 1:
	    keeps trying to generate a ship until the generation is successful
	For Loop 1:
	    if dir is 0 (horizontal), check that no ships have been placed directly left and right of the place we want to place this ship
	For Loop 2:
	    if dir is 0 (horizontal), check that no ships have been placed directly above, on, and below of the place we want to place
	For Loop 3:
	    if dir is 0 (horizontal), and no ships are directly adjacent to it, place the ship onto the board (hasShip)
	For Loop 4:
	    if dir is 1 (vertical), check that no ships have been placed directly above and below of the place we want to place this ship
	For Loop 5:
	    if dir is 1 (vertical), check that no ships have been placed directly left, on, and right of the place we want to place
	For Loop 6:
	    if dir is 1 (vertical), and no ships are directly adjacent to it, place the ship onto the board (hasShip)
    */
    public void generateShips (int size)
    {
	boolean done = false;
	while (true)
	{
	    done = true;
	    int x = (int) (SIZE * Math.random ());
	    int y = (int) (SIZE * Math.random ());
	    int dir = (int) (2 * Math.random ());

	    if (dir == 0 && y + size < SIZE)
	    { // if horizontal and the ship fits on the board
		// check that the ship squares are not occupied
		for (int i = Math.max (0, x - 1) ; i <= Math.min (9, x + 1) ; i += 1)
		{
		    if (y != 0 && hasShip [i] [y - 1] > 0)
			done = false;                                      // check that no ships are adjacent on the left
		    if (hasShip [i] [y + size] > 0)
			done = false;                               // check that no ships are adjacent on the right
		}
		for (int i = y ; i < y + size ; i += 1)
		{
		    if (hasShip [x] [i] > 0)
			done = false;                        // check where the ship is
		    if (x != 0 && hasShip [x - 1] [i] > 0)
			done = false;                                      // check that no ships are adjacent above
		    if (x != 9 && hasShip [x + 1] [i] > 0)
			done = false;                                      // check that no ships are adjacent below
		}
		if (done)
		{
		    for (int i = y ; i < y + size ; i += 1)
			hasShip [x] [i] = size;                                     // label the ship
		    break; // break out of loop
		}
	    }
	    else if (x + size < SIZE)
	    { // if vertical and the ship fits on the board
		// check that the ship squares are not occupied
		for (int i = Math.max (0, y - 1) ; i <= Math.min (9, y + 1) ; i += 1)
		{
		    if (x != 0 && hasShip [x - 1] [i] > 0)
			done = false;                                      // check that no ships are adjacent on the top
		    if (hasShip [x + size] [i] > 0)
			done = false;                               // check that no ships are adjacent on the bottom
		}
		for (int i = x ; i < x + size ; i += 1)
		{
		    if (hasShip [i] [y] > 0)
			done = false;                        // check where the ship is
		    if (y != 0 && hasShip [i] [y - 1] > 0)
			done = false;                                      // check that no ships are adjacent left
		    if (y != 9 && hasShip [i] [y + 1] > 0)
			done = false;                                      // check that no ships are adjacent right
		}
		if (done)
		{
		    for (int i = x ; i < x + size ; i += 1)
			hasShip [i] [y] = size;                                     // label the ship
		    break; // break out of loop
		}
	    }
	}
    }


    /*
	Method to hit the user's board at coordinate [cursorX, cursorY]
	Although public, method is called in sank() to generate "white tokens" around the sunk ship
	
	Draws a "token" at x, y (either red or white depending on hit or miss)
	Returns if the coordinate we hit contained a ship
    */
    public boolean hit ()
    {
	visited [cursorX] [cursorY] = true; // visited
	if (hasShip [cursorX] [cursorY] == 0)
	    c.setColor (Color.white);                                    // set color
	else
	    c.setColor (p.MARKER_RED);
	c.fillOval (311 + 47 * cursorX, 51 + 47 * cursorY, 25, 25); // draw "hit"
	if (hasShip [cursorX] [cursorY] != 0)
	    sank ();                                    // if hit, check if it sank a ship
	return hasShip [cursorX] [cursorY] != 0;
    }

    /*
	Overloaded method to hit the user's board at coordinate [x, y]
	used to simulate "hits" to the squares directly adjacent to a sunk ship
	
	Variable Name       Type        Description
	x                   int         stores the x coordinate we want to hit
	y                   int         stores the y coordinate we want to hit
    */
    public void hit (int x, int y)
    { // overloaded method to hit adjacent squares when ship sinks
	visited [x] [y] = true; // visited
	c.setColor (Color.white); // set color
	c.fillOval (311 + 47 * x, 51 + 47 * y, 25, 25); // draw "hit"
    }

    /*
	Public method to move the user's cursor in the direction `dir`
	
	Variable Name       Type        Description
	dir                 char        stores the direction the user wants to move
					q -> stay still
					w or W -> move up
					a or A -> move left
					s or S -> move down
					d or D -> move right
					when the cursor goes offscreen, it wraps to the other side
    */
    public void moveCursor (char dir)
    {
	if (dir != 'q')
	{ // q stands for "redraw the cursor"
	    c.setColor (p.BOARD_BACKGROUND); // remove cursor
	    c.fillRect (300 + 47 * cursorX, 40 + 47 * cursorY, 47, 47);
	    c.setColor (p.TEXT_GREEN);
	    c.drawRect (300 + 47 * cursorX, 40 + 47 * cursorY, 47, 47);
	    if (visited [cursorX] [cursorY])
	    {
		if (hasShip [cursorX] [cursorY] != 0)
		    c.setColor (p.MARKER_RED);
		else
		    c.setColor (Color.white);
		c.fillOval (311 + 47 * cursorX, 51 + 47 * cursorY, 25, 25); // draw "hit"
	    }

	    // move cursor
	    if (dir == 'w' || dir == 'W')
		cursorY -= 1;
	    if (dir == 'a' || dir == 'A')
		cursorX -= 1;
	    if (dir == 's' || dir == 'S')
		cursorY += 1;
	    if (dir == 'd' || dir == 'D')
		cursorX += 1;

	    // loop to the other side
	    cursorX = (cursorX + SIZE) % SIZE;
	    cursorY = (cursorY + SIZE) % SIZE;
	}
	// redraw cursor
	c.setColor (Color.red);
	c.setColor (Color.red);
	// c.drawRect (302 + 47, 42 + 47, 43, 43);

	c.fillRect (302 + 47 * cursorX, 42 + 47 * cursorY, 17, 5);
	c.fillRect (328 + 47 * cursorX, 42 + 47 * cursorY, 17, 5);

	c.fillRect (302 + 47 * cursorX, 42 + 47 * cursorY, 5, 17);
	c.fillRect (341 + 47 * cursorX, 42 + 47 * cursorY, 5, 17);

	c.fillRect (328 + 47 * cursorX, 80 + 47 * cursorY, 17, 5);
	c.fillRect (302 + 47 * cursorX, 80 + 47 * cursorY, 17, 5);

	c.fillRect (302 + 47 * cursorX, 68 + 47 * cursorY, 5, 17);
	c.fillRect (341 + 47 * cursorX, 68 + 47 * cursorY, 5, 17);
    }


    /*
	Public method to toggle cheatsOn
	when cheats are on, the user can see where the enemy's ships are
	cheats automatically toggle off when the user moves their cursor
	
	Variable Name          Type           Description
	checked                boolean[]      stores if ship size i has already been drawn
	BX                     final int      stores the topleft x coordinate of the enemy's board
	XY                     final int      stores the topleft y coordinate of the enemy's board
	GRID_SIZE              final int      stores the size of each grid square in pixels
	val                    int            stores hasShip[i][j], during grid traversal
	i                      int            for loop iterator
	j                      int            for loop iterator
	
	For Loop 1:
	    traverse the grid's x coordinates to toggle cheats on
	For Loop 2:
	    traverse the grid's y coordinates
	    for each iteration, check if coordinate [i, j] has a ship, and if so, if the ship has already been shown
	    if the ship has not been shown, show the ship on the board
	For Loop 3:
	    traverse the grid's x coordiantes to toggle cheats off
	For Loop 4:
	    traverse the grid's y coordinates
	    for each iteration, clear the coordinate if there was a ship displayed
    */
    public void toggleCheat ()
    {
	boolean[] checked = new boolean [6]; // if ship size i has been drawn yet
	final int BX = 300;
	final int BY = 40;
	final int GRID_SIZE = 47;
	int val;
	if (!cheatsOn)
	{
	    for (int i = 0 ; i < SIZE ; i += 1)
	    { // traverse grid
		for (int j = 0 ; j < SIZE ; j += 1)
		{
		    val = hasShip [i] [j];
		    if (!checked [val])
		    { // if it is a ship that has not been drawn yet
			c.setColor (p.SHIP_GRAY);
			checked [val] = true; // mark that it has been drawn
			if (i + 1 != SIZE && hasShip [i + 1] [j] == val)
			    c.fillOval (BX + GRID_SIZE * i, BY + GRID_SIZE * j, GRID_SIZE * val, GRID_SIZE);
			else
			    c.fillOval (BX + GRID_SIZE * i, BY + GRID_SIZE * j, GRID_SIZE, GRID_SIZE * val);
		    }
		    // draw hits on top of ship
		    if (hasShip [i] [j] != 0 && visited [i] [j])
		    {
			c.setColor (p.MARKER_RED);
			c.fillOval (311 + 47 * i, 51 + 47 * j, 25, 25); // draw "hit"
		    }
		}
	    }
	    cheatsOn = true;
	}
	else
	{
	    for (int i = 0 ; i < SIZE ; i += 1)
	    { // traverse grid
		for (int j = 0 ; j < SIZE ; j += 1)
		{
		    val = hasShip [i] [j];
		    if (val != 0)
		    {
			c.setColor (p.BOARD_BACKGROUND); // remove ship
			c.fillRect (300 + 47 * i, 40 + 47 * j, 47, 47);
			c.setColor (p.TEXT_GREEN);
			c.drawRect (300 + 47 * i, 40 + 47 * j, 47, 47);
			if (visited [i] [j])
			{
			    c.setColor (p.MARKER_RED);
			    c.fillOval (311 + 47 * i, 51 + 47 * j, 25, 25); // draw "hit"
			}
		    }
		}
	    }
	    cheatsOn = false;
	}
	moveCursor ('q');
    }

    /*
	Private method to check if the boat containing cell [cursorX, cursorY] has been sunk (i.e. all spots have been hit)
	Called in hit()
	
	Variable Name       Type        Description
	bX                  int         stores the largest x coordinate that the ship is in
	bY                  int         stores the largest y coordinate that the ship is in
	x                   int         variable used to search for the full length of the ship
	y                   int         variable used to search for the full length of the ship
	hitC                int         stores the number of spots on the ship that have not been hit        
	i                   int         for loop iterator
	
	While Loop 1:
	    search downwards/rightwards for the bottommost coordinate of the ship
	While Loop 2:
	    search upwards/leftwards for the uppermost coordinate of the ship
	For Loop 3:
	    if the ship was sunk and is positioned vertically,
	    mark adjacent left and right coordinates as visited
	For Loop 4:
	    if the ship was sunk and is positioned vertically,
	    mark adjacent upper and lower coordinates as visited
	For Loop 5:
	    if the ship was sunk and is positioned horizontally,
	    mark the adjacent upper and lower coordinates as visited
	For Loop 6:
	    if the ship was sunk and is positioned horizontally,
	    mark the adjacent left and right coordinates as visited
    */
    private void sank ()
    {
	int bX, bY;
	int x;
	int y;
	int hitC = hasShip[cursorX][cursorY] - 1; // number of spots on the ship that have been hit
	
	// search downwards
	x = cursorX;
	y = cursorY;
	while (true)
	{
	    if (x != SIZE - 1 && hasShip [x + 1] [y] != 0)
		x += 1;
	    else if (y != SIZE - 1 && hasShip [x] [y + 1] != 0)
		y += 1;
	    else
		break;
	    if (visited [x] [y])
		hitC -= 1;
	}
	bX = x; // store the right/bottommost square of the ship
	bY = y;

	// search upwards
	x = cursorX;
	y = cursorY;
	while (true)
	{
	    if (x != 0 && hasShip [x - 1] [y] != 0)
		x -= 1;
	    else if (y != 0 && hasShip [x] [y - 1] != 0)
		y -= 1;
	    else
		break;
	    if (visited [x] [y])
		hitC -= 1;
	}

	if (hitC == 0)
	{ // if the ship was sunk
	    // alert user that ship has been sunk
	    t.errorMessage ("SHIP " + hasShip [cursorX] [cursorY] + "x1 HAS BEEN SUNK", "SUNK", 2);

	    // mark ship as sunk
	    shipSunk [hasShip [cursorX] [cursorY]] = true;
	    // mark all surrounding coordinates as "visited"
	    if (x == bX)
	    { // if ship is vertical
		// check that the ship squares are not occupied
		for (int i = Math.max (0, x - 1) ; i <= Math.min (9, x + 1) ; i += 1)
		{
		    if (y != 0)
			hit (i, y - 1);            // hit left
		    if (y + hasShip [cursorX] [cursorY] < SIZE)
			hit (i, y + hasShip [cursorX] [cursorY]);                                        // hit right
		}
		for (int i = y ; i < y + hasShip [cursorX] [cursorY] ; i += 1)
		{
		    if (x != 0)
			hit (x - 1, i);            // hit above
		    if (x != 9)
			hit (x + 1, i);            // hit below
		}
	    }
	    else
	    {
		// check that the ship squares are not occupied
		for (int i = Math.max (0, y - 1) ; i <= Math.min (9, y + 1) ; i += 1)
		{
		    if (x != 0)
			hit (x - 1, i);            // hit above
		    if (x + hasShip [cursorX] [cursorY] < SIZE)
			hit (x + hasShip [cursorX] [cursorY], i);                                        // hit below
		}
		for (int i = x ; i < x + hasShip [cursorX] [cursorY] ; i += 1)
		{
		    if (y != 0)
			hit (i, y - 1);            // hit left
		    if (y != 9)
			hit (i, y + 1);            // hit right
		}
	    }
	}
    }


    /*
	Public method to draw the user's stats onto the console
	
	Variable Name        Type       Description
	nTurn                int        parameter that stores how many turns have gone by
	nHit                 int        parameter that stores how many hits the user has made
	nScore               int        parameter that stores the user's current score
	i                    int        for loop iterator
	
	For Loop 1:
	    display the status of each ship from size 1 to size 5
    */
    public void remaining (int nTurn, int nHit, int nScore)
    {
	c.setColor (p.BOARD_BACKGROUND); // reset board console
	c.fillRect (20, 280, 250, 170);

	c.setColor (p.TEXT_GREEN);
	c.setFont (new Font ("Lucida Sans Typewriter Regular", 1, 25));
	c.drawString ("Turns: " + nTurn, 30, 310);
	c.drawString ("Hits: " + nHit, 30, 335);
	c.drawString("Score: ", 170, 310);
	c.drawString("" + nScore, 170, 335);
	
	c.setFont (new Font ("Lucida Sans Typewriter Regular", 1, 20));

	for (int i = 1 ; i <= 5 ; i += 1)
	{
	    c.setColor (p.TEXT_GREEN);
	    c.drawString (i + "x1 ", 30, 345 + i * 20);

	    if (!shipSunk [i])
		c.drawString ("OPERATING", 100, 345 + i * 20);
	    else
	    {
		c.setColor (Color.red); // change the colour for SUNK
		c.drawString ("SUNK", 100, 345 + i * 20);
	    }
	}
    }
}
