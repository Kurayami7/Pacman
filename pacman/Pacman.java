/* The game is a classic arcade-style game called "Pac-Man". In the game, the player controls a yellow character called Pac-Man
 who navigates a maze-like environment, 4 eating dots and avoiding enemy ghosts. The game consists of multiple levels, each with its own unique maze 
 layout. The player controls Pac-Man using the arrow keys on their keyboard, moving him around the maze to eat all the dots without getting caught by 
 the ghosts. The ghosts move randomly around the maze, but they will chase Pac-Man if he gets too close */



package pacman;

import javax.swing.JFrame;

public class Pacman extends JFrame{

	public Pacman() {
		add(new BluePrint());
	}
		
	public static void main(String[] args) {
		Pacman pac = new Pacman();
		pac.setVisible(true);
		pac.setTitle("Pacman");
		pac.setSize(380, 420);
		pac.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pac.setLocationRelativeTo(null);
	}

}
