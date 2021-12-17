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
    
    public Board(Console cC, Palette cP, Tools cT) {
	c = cC;
	p = cP;
	t = cT;
    }
    
    public void generateShips() {
	
    }
    
    public boolean hit(int x, int y) {
	return true;
    }
    
    public void sank() {
	
    }
    
    

}
