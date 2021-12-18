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
	visited[cursorX][cursorY] = true; // visited
	if (hasShip[cursorX][cursorY] == 0) c.setColor(Color.white); // set color
	else c.setColor(Color.red);
	c.fillOval(308 + 47 * cursorX, 48 + 47 * cursorY, 31, 31); // draw "hit"
	if (hasShip[cursorX][cursorY] != 0) sank(); // if hit, check if it sank a ship
	return hasShip[cursorX][cursorY] != 0;
    }
    
    public void moveCursor(char dir) {
	c.setColor(p.BOARD_BACKGROUND); // remove cursor
	c.fillRect(300 + 47 * cursorX, 40 + 47 * cursorY, 47, 47);
	c.setColor(p.TEXT_GREEN);
	c.drawRect(300 + 47 * cursorX, 40 + 47 * cursorY, 47, 47);
	if (visited[cursorX][cursorY]) {
	    if (hasShip[cursorX][cursorY] != 0) c.setColor(Color.red);
	    else c.setColor(Color.white);
	    c.fillOval(308 + 47 * cursorX, 48 + 47 * cursorY, 31, 31);
	}
	
	// move cursor
	if ((dir == 'w' || dir == 'W') && cursorY != 0) cursorY -= 1;
	if ((dir == 'a' || dir == 'A') && cursorX != 0) cursorX -= 1;
	if ((dir == 's' || dir == 'S') && cursorY != SIZE - 1) cursorY += 1;
	if ((dir == 'd' || dir == 'D') && cursorX != SIZE - 1) cursorX += 1;
	
	// redraw cursor
	c.setColor(Color.red); 
	c.drawRect(302 + 47 * cursorX, 42 + 47 * cursorY, 43, 43);
    }
    
    public void toggleCheat() {
	
    }
    
    public void sank() {
	
    }
    
    public void remaining(int nTurn, int nHit) {
	String message; // message for hit or alive
    
	c.setColor(p.BOARD_BACKGROUND); // reset board console
	c.fillRect(20, 280, 250, 170);

	c.setColor(p.TEXT_GREEN);
	c.setFont (new Font ("Lucida Sans Typewriter Regular", 1, 25));
	c.drawString("Turns: " + nTurn, 30, 315);
	c.drawString("Hits: " + nHit, 30, 340);
	c.setFont (new Font ("Lucida Sans Typewriter Regular", 1, 20));
	
	for (int i = 1; i <= 5; i += 1) {
	    if (shipSunk[i]) message = "SUNK";
	    else message = "OPERATING";
	    c.drawString(i + "x1 " + message, 30, 345 + i * 20);
	}
    }
}
