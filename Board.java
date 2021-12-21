// The "Board" class.
import java.awt.*;
import hsa.Console;

public class Board
{
    private Console c;
    private Palette p;
    private Tools t;
    boolean[][] visited;
    int[][] hasShip;
    int remaining;
    final int SIZE = 10;
    
    public Board(Console cC, Palette cP, Tools cT) {
	c = cC;
	p = cP;
	t = cT;
	visited = new boolean[SIZE][SIZE];
	hasShip = new int[SIZE][SIZE];
	remaining = 15;
    }
    
    public boolean generateShips(int size) {
	if (size == 0) return true;
	boolean done = false;
	while (true) {
	    done = true;
	    int x = (int) (SIZE * Math.random());
	    int y = (int) (SIZE * Math.random());
	    int dir = (int) (2 * Math.random());
	    
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
	return generateShips(size - 1);
    }
    
    public boolean hit(int x, int y) {
	visited[x][y] = true; // visited
	if (hasShip[x][y] == 0) c.setColor(Color.white); // set color
	else c.setColor(Color.red);
	c.fillOval(36 + 23 * x, 36 + 23 * y, 11, 11); // draw "hit"
	if (hasShip[x][y] != 0) {
	    remaining -= 1;
	    sank(x, y); // if hit, check if it sank a ship
	}
	return hasShip[x][y] != 0;
    }
    
    private void sank(int a, int b) {
	int bX, bY;
	int x;
	int y;
	int dir;
	int hitC = hasShip[a][b] - 1; // number of spots on the ship that have been hit
	
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
