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
    }
    
    public void generateShips() {
	
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
    
    public void remaining() {
	
    }
    
}
