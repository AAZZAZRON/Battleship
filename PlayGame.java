import java.awt.*;
import java.io.*;
import hsa.Console;

public class PlayGame
{
    private Console c;
    private Palette p;
    private Tools t;
    Board user;
    EnemyBoard enemy;
    boolean finished;
    boolean cheat;
    int turns;
    int hits;
    
    public PlayGame(Console cC, Palette cP, Tools cT) {
	c = cC;
	p = cP;
	t = cT;
	user = new Board(c, p, t);
	enemy = new EnemyBoard(c, p, t);
	finished = false;
	turns = 0;
	hits = 0;
    }
    
    public void selectCheat() {
	cheat = true;
    }
    
    public void play() {
	char key; // local variable to score keypressed

	user.generateShips(5); // generate user ships
	enemy.generateShips(5); // generate enemy ships
	
	drawBackground(); // draw board
	enemy.moveCursor('q'); // draw the cursor at [0, 0]
	
	enemy.remaining(turns, hits); // display which boats still need to be sunk
	
	while (!finished) { // while the game isn't finished
	    key = c.getChar();
	    // only exit while loop if the user wants to hit and that square has not been hit yet
	    while (!(key == ' ' && !enemy.visited[enemy.cursorX][enemy.cursorY])) {
		if ("wasdWASD".indexOf(key) != -1) {
		    if (enemy.cheatsOn) enemy.toggleCheat(); // turn off cheats
		    enemy.moveCursor(key); // if key is directional, move cursor
		}
		else if (key == 'c' && cheat) enemy.toggleCheat(); // if key is cheat and cheats are on, turn on/off cheats
		key = c.getChar(); // get the next action
	    }
	    if (!enemy.hit()) {
		enemyTurn();
	    }
	}
    }
    
    private void drawBackground() {
	// graphics for game
	c.setColor(p.CONSOLE_GRAY);
	c.fillRect(10, 10, 800, 540);
	
	c.setColor(p.BOARD_BACKGROUND);
	c.fillRect(20, 20, 250, 250);
	c.fillRect(20, 280, 250, 250);
	c.fillRect(285, 25, 500, 500);
	
	c.setColor(p.TEXT_GREEN);
	
	// user's board
	c.drawRect(30, 30, 230, 230);
	for (int i = 30 + 23; i < 260; i += 23) {
	    c.drawLine(i, 30, i, 260);
	    c.drawLine(30, i, 260, i);
	}
	
	// enemy's board
	c.drawRect(300, 40, 470, 470);
	for (int i = 0; i < 470; i += 47) {
	    c.drawLine(300 + i, 40, 300 + i, 510);
	    c.drawLine(300, 40 + i, 770, 40 + i);
	}
	
	// instructional text in case user doesn't read instructions
	c.setFont (new Font ("Lucida Sans Typewriter Regular", 1, 17));
	c.drawString("use 'wasd' to move cursor", 30, 475);
	c.drawString("use space to hit coordinate", 30, 495);
	if (cheat) c.drawString("use c to toggle cheats", 30, 515);
	
	c.setColor(p.SHIP_GRAY); // draw user's ships
	boolean[] checked = new boolean[6]; // if ship size i has been drawn yet
	int val;
	checked[0] = true;
	int buff = 30; // buffer
	int gridSize = 23;
	
	for (int i = 0; i < user.SIZE; i += 1) { // traverse grid
	    for (int j = 0; j < user.SIZE; j += 1) {
		val = user.hasShip[i][j];
		if (!checked[val]) { // if it is a ship that has not been drawn yet
		    checked[val] = true; // mark that it has been drawn
		    if (i + 1 != user.SIZE && user.hasShip[i + 1][j] == val) c.fillOval(buff + gridSize * i, buff + gridSize * j, gridSize * val, gridSize);
		    else c.fillOval(buff + gridSize * i, buff + gridSize * j, gridSize, gridSize * val);
		}
	    }
	}
    }
    
    private void enemyTurn() {
	
    }
    
    public void storeScore() {
	
    }
    
    
}
