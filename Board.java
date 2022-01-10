/*
    Programmers: Aaron Zhu and James Huynh
    Teacher: Ms. Krasteva
    Date: January 14th, 2022
    Description: Board class (creates a board for the user)
*/

import java.awt.*;
import hsa.Console;

public class Board
{
    /*
	Variable Name        Type           Description
	c                    Console        stores the console instance that was initialized in Battleship.java
	p                    Palette        stores the palette instance that was initialized in Battleship.java
	t                    Tools          stores the tools instance that was initialized in Battleship.java
	visited              boolean[][]    stores if coordinate [x, y] has been hit
	hasShip              int[][]        stores if coordinate [x, y] contains a ship
					    stores the size of the ship at [x, y]
	remaining            int            stores how many hits the enemy (computer) needs to make before they win
	SIZE                 final int      how big the board is
    */
    private Console c;
    private Palette p;
    private Tools t;
    boolean[][] visited;
    int[][] hasShip;
    int remaining;
    final int SIZE = 10;

    /*
	Constructor for the Board class
	
	Variable Name      Type       Description
	cC                 Console    console passed from Battleship.java
	cP                 Palette    palette passed from Battleship.java
	cT                 Tools      tools passed from Battleship.java
    */
    public Board(Console cC, Palette cP, Tools cT) {
	c = cC;
	p = cP;
	t = cT;
	visited = new boolean[SIZE][SIZE];
	hasShip = new int[SIZE][SIZE];
	remaining = 15;
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
    public void generateShips(int size) {
	boolean done = false;
	int x, y, dir;
	while (true) {
	    done = true;
	    x = (int) (SIZE * Math.random());
	    y = (int) (SIZE * Math.random());
	    dir = (int) (2 * Math.random());
	    
	    if (dir == 0 && y + size < SIZE) { // if horizontal and the ship fits on the board
		// check that the ship squares are not occupied
		for (int i = Math.max(0, x - 1); i <= Math.min(9, x + 1); i += 1) {
		    if (y != 0 && hasShip[i][y - 1] > 0) done = false; // check that no ships are adjacent on the left
		    if (hasShip[i][y + size] > 0) done = false; // check that no ships are adjacent on the right
		}
		for (int i = y; i < y + size; i += 1) {
		    if (hasShip[x][i] > 0) done = false; // check where the ship is
		    if (x != 0 && hasShip[x - 1][i] > 0) done = false; // check that no ships are adjacent above
		    if (x != 9 && hasShip[x + 1][i] > 0) done = false; // check that no ships are adjacent below
		}
		if (done) {
		    for (int i = y; i < y + size; i += 1) hasShip[x][i] = size; // label the ship
		    break; // break out of loop
		}
	    } else if (x + size < SIZE) { // if vertical and the ship fits on the board
		// check that the ship squares are not occupied
		for (int i = Math.max(0, y - 1); i <= Math.min(9, y + 1); i += 1) {
		    if (x != 0 && hasShip[x - 1][i] > 0) done = false; // check that no ships are adjacent on the top
		    if (hasShip[x + size][i] > 0) done = false; // check that no ships are adjacent on the bottom
		}
		for (int i = x; i < x + size; i += 1) {
		    if (hasShip[i][y] > 0) done = false; // check where the ship is
		    if (y != 0 && hasShip[i][y - 1] > 0) done = false; // check that no ships are adjacent left
		    if (y != 9 && hasShip[i][y + 1] > 0) done = false; // check that no ships are adjacent right
		}
		if (done) {
		    for (int i = x; i < x + size; i += 1) hasShip[i][y] = size; // fill the ship with its size
		    break; // break out of loop
		}
	    }
	}
    }
    
    /*
	Method to hit the user's board at coordinate [x, y]
	Although public, method is called in sank() to generate "white tokens" around the sunk ship
	
	Variable Name       Type        Description
	x                   int         stores the x coordinate we want to hit
	y                   int         stores the y coordinate we want to hit
	
	Draws a "token" at x, y (either red or white depending on hit or miss)
	Returns if the coordinate we hit contained a ship
    */
    public boolean hit(int x, int y) {
	visited[x][y] = true; // visited
	if (hasShip[x][y] == 0) c.setColor(Color.white); // set color
	else c.setColor(p.MARKER_RED);
	c.fillOval(36 + 23 * x, 36 + 23 * y, 11, 11); // draw "hit"
	if (hasShip[x][y] != 0) {
	    remaining -= 1;
	    sank(x, y); // if hit, check if it sank a ship
	}
	return hasShip[x][y] != 0;
    }
    
    /*
	Private method to check if the boat containing cell [a, b] has been sunk (i.e. all spots have been hit)
	Called in hit()
	
	Variable Name       Type        Description
	a                   int         parameter storing the x coordinate that was hit
	b                   int         parameter storing the y coordinate that was hit
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
    private void sank(int a, int b) {
	int bX, bY;
	int x;
	int y;
	int hitC = hasShip[a][b] - 1; // number of spots on the ship that have not been hit
	
	// search downwards
	x = a;
	y = b;
	while (true) {
	    if (x != SIZE - 1 && hasShip[x + 1][y] != 0) x += 1;
	    else if (y != SIZE - 1 && hasShip[x][y + 1] != 0) y += 1;
	    else break;
	    if (visited[x][y]) hitC -= 1;
	}
	bX = x;
	bY = y;
	
	// search upwards
	x = a;
	y = b;
	while (true) {
	    if (x != 0 && hasShip[x - 1][y] != 0) x -= 1;
	    else if (y != 0 && hasShip[x][y - 1] != 0) y -= 1;
	    else break;
	    if (visited[x][y]) hitC -= 1;
	}
	
	if (hitC == 0) { // if the ship was sunk
	    // alert user that ship has been sunk
	    t.errorMessage("YOUR " + hasShip[a][b] + "x1 HAS BEEN SUNK", "SUNK", 2);
	    
	    // mark all surrounding coordinates as "visited"
	    if (x == bX) { // if ship is vertical
		// check that the ship squares are not occupied
		for (int i = Math.max(0, x - 1); i <= Math.min(9, x + 1); i += 1) {
		    if (y != 0) hit(i, y - 1); // hit left
		    if (y + hasShip[a][b] < SIZE) hit(i, y + hasShip[a][b]); // hit right
		}
		for (int i = y; i < y + hasShip[a][b]; i += 1) {
		    if (x != 0) hit(x - 1, i); // hit above
		    if (x != 9) hit(x + 1, i); // hit below
		}
	    } else {
		// check that the ship squares are not occupied
		for (int i = Math.max(0, y - 1); i <= Math.min(9, y + 1); i += 1) {
		    if (x != 0) hit(x - 1, i); // hit above
		    if (x + hasShip[a][b] < SIZE) hit(x + hasShip[a][b], i); // hit below
		}
		for (int i = x; i < x + hasShip[a][b]; i += 1) {
		    if (y != 0) hit(i, y - 1); // hit left
		    if (y != 9) hit(i, y + 1); // hit right
		}
	    }
	}
    }
}
