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
    boolean[] shipSunk;
    final int SIZE = 10;
    
    public Board(Console cC, Palette cP, Tools cT) {
	c = cC;
	p = cP;
	t = cT;
	visited = new boolean[SIZE][SIZE];
	hasShip = new int[SIZE][SIZE];
	remaining = 15;
	shipSunk = new boolean[5];
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
		    for (int i = x; i < x + size; i += 1) hasShip[i][y] = size; // label the ship
		    break; // break out of loop
		}
	    }
	}
	return generateShips(size - 1);
    }
    
    public boolean hit(int x, int y) {
	return true;
    }
    
    public void sank() {
	
    }
    
    

}
