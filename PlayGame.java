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
	user.generateShips(); // generate user ships
	enemy.generateShips(); // generate enemy ships
	
	while (!finished) { // while the game isn't finished
	    key = c.getChar();
	    // only exit while loop if the user wants to hit and that square has not been hit yet
	    while (key != '\n' && !enemy.visited[enemy.cursorX][enemy.cursorY]) {
		c.println("!" + key + "!");
		if ("wasd".indexOf(key) != -1) enemy.moveCursor(key); // if key is directional, move cursor
		else if (key == 'c' && cheat) enemy.showBoard(); // if key is cheat and cheats are on, turn on/off cheats
		key = c.getChar(); // get the next action
	    }
	}
    }
    
    private void enemyTurn() {
	
    }
    
    public void storeScore() {
	
    }
    
    
}
