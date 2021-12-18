// The "EnemyBoard" class.
import java.awt.*;
import hsa.Console;

public class EnemyBoard
{
    private Console c;
    private Palette p;
    private Tools t;
    boolean[][] visited;
    int[][] hasShip;
    boolean cheatsOn;
    boolean[] shipSunk;
    int cursorX;
    int cursorY;
    final int SIZE = 10;
    
    public EnemyBoard(Console cC, Palette cP, Tools cT) {
	c = cC;
	p = cP;
	t = cT;
	visited = new boolean[SIZE][SIZE];
	hasShip = new int[SIZE][SIZE];
	shipSunk = new boolean[6];
	cursorX = 0;
	cursorY = 0;
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
    
    public boolean hit() {
	return true;
    }
    
    public void moveCursor(char dir) {
	
    }
    
    public void toggleCheat() {
	
    }
    
    public void sank() {
	
    }
    
    public void remaining(int nTurn, int nHit) {
	String message; // message for hit or alive
    
	c.setColor(p.BOARD_BACKGROUND); // reset board console
	c.fillRect(20, 280, 250, 160);

	c.setColor(p.TEXT_GREEN);
	c.setFont (new Font ("Lucida Sans Typewriter Regular", 1, 25));
	c.drawString("Turns: " + nTurn, 30, 305);
	c.drawString("Hits: " + nHit, 30, 330);
	c.setFont (new Font ("Lucida Sans Typewriter Regular", 1, 20));
	
	for (int i = 1; i <= 5; i += 1) {
	    if (shipSunk[i]) message = "SUNK";
	    else message = "OPERATING";
	    c.drawString(i + "x1 " + message, 30, 335 + i * 20);
	}
    }
    
}
