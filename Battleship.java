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
    }
    
    public void runSplash() {
	
    }
    
    public void runMenu() {
	
    }
    
    public void startGame() {
	
    }
    
    public void instructions() {
	
    }
    
    public void leaderboard() {
	
    }
    
    public void goodbye() {
	
    }
    
    public static void main (String[] args) throws IOException {
	Battleship g = new Battleship();
	g.runSplash();
	while (g.action != 4) {
	    g.runMenu();
	    if (g.action == 1) g.startGame();
	    else if (g.action == 2) g.instructions();
	    else if (g.action == 3) g.leaderboard();
	}
	g.goodbye();
    } 
} 
