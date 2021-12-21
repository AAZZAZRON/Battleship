/*
    Programmers: Aaron Zhu and James Huynh
    Teacher: Ms. Krasteva
    Date: January 14th, 2022
    Description: One-player battleship game for ICS2U ISP
*/
import java.awt.*;
import java.io.*;
import hsa.Console;

public class Battleship
{
    static Console c; // console
    static Palette p; // palette
    static Tools t; // tools
    int action = 1; // action (used in main menu)
    
    
    public Battleship() {
	c = new Console(27, 100, "Battleship");
	p = new Palette();
	t = new Tools(c);
    }
    
    public void runSplash() {
	Splash_Ship g = new Splash_Ship (c);
	g.start ();
	try
	{
	    g.join ();
	}
	catch (Exception e)
	{
	}
	Splash_Waves t1 = new Splash_Waves (c);
	t1.start ();
	Splash_Bomb a = new Splash_Bomb (c, 50, 0, 5);
	Thread t2 = new Thread (a);
	t2.start ();
	Splash_Bomb b = new Splash_Bomb (c, 700, 1000, 5);
	Thread t3 = new Thread (b);
	t3.start ();
	try
	{
	    t1.join ();
	}
	catch (Exception e)
	{
	}
    }
    
    public void runMenu() {
	
    }
    
    public void startGame() {
	t.pauseProgram(0, 0, 50, "%", Color.black);
	c.clear();
	PlayGame game = new PlayGame(c, p, t);
	game.selectCheat();
	boolean userWin = game.play();
	if (userWin && !game.cheat) game.storeScore();
	
	// pause program/return to main menu
	c.setColor(p.CONSOLE_GRAY);
	c.fillRect(10, 200, 800, 140);
	c.setColor(p.BOARD_BACKGROUND);
	c.fillRect(20, 210, 765, 120);
	t.pauseProgram(120, 280, 30, "Press any key to return to main menu...", p.TEXT_GREEN);
    }
    
    public void instructions() {
	
    }
    
    public void leaderboard() {
	
    }
    
    public void goodbye() {
	
    }
    
    public static void main (String[] args) throws IOException {
	Battleship g = new Battleship();
	// g.runSplash();
	g.startGame();
	/*
	g.runSplash();
	while (g.action != 4) {
	    g.runMenu();
	    if (g.action == 1) g.startGame();
	    else if (g.action == 2) g.instructions();
	    else if (g.action == 3) g.leaderboard();
	}
	g.goodbye();*/
    }
} 
