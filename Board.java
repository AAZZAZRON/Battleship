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
    
    public void generateShips() {
	
    }
    
    public boolean hit(int x, int y) {
	return true;
    }
    
    public void sank() {
	
    }
    
    

}
