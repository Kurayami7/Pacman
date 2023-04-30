/*
 * Author: Areaf
 * Description: The game is a classic arcade-style game called "Pac-Man". In the game, the player controls a yellow character called Pac-Man who navigates a maze-like environment, 4
 eating dots and avoiding enemy ghosts. The game consists of multiple levels, each with its own unique maze layout. The player controls Pac-Man using the arrow keys on their keyboard, 
 moving him around the maze to eat all the dots without getting caught by the ghosts. The ghosts move randomly around the maze, but they will chase Pac-Man if he gets too close
 * Date: 17 April, 2023
 */


// Import statements 
package pacman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;



/* The Model class is a Java class that extends the JPanel class and implements the ActionListener interface. It is used to create the game's model, which includes the game logic and state
 * A JPanel is the building block for creating a GUI. 
 *  */
public class BluePrint extends JPanel implements ActionListener {
	private Dimension d; // The height and width of the entire pacman field
	private final Font smallFont = new Font("Arial", Font.BOLD, 14); // The font type and size of text inside the game
	private boolean inGame = false; // A boolean to control and check if the game is actually running
	private boolean dying = false; // Checks if the pacman is alive using a boolean 
	
	private final int blockSize = 24; // Setting up the size of each block in the game
	private final int noBlocks = 15; // Refers to the total number of blocks
	private final int screenSize = blockSize * noBlocks; // The screen's size, found by multiplying the block size,
	//by the number of blocks
	private final int maxGhosts = 12; // The highest number of ghosts which threaten pacman at a given time
	private final int pacmanPace = 6; // Setting up the speed at which pacman moves
	
	private int noGhosts = 6; // The initial number of ghosts
	private int lives, score; // Initializing two variables to keep track of the number of lives and the score
	private int [] dx, dy; /* dx = delta X, dy = delta Y. This is important for controlling the position of the player. 
	These variables are used to update the position of the player based on the direction of movement determined by the user input. 
	dx and dy determine the actual direction of movement for Pac-Man, while req_dx and req_dy determine the desired direction of movement */
	private int [] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed; // dx, dy correspond to delta x and delta y. Determines 
	//the number and positions of the ghosts
	
	private Image heart, ghost;
	private Image up, down, left, right;
	
	private int pacman_x, pacman_y, pacmand_x, pacmand_y; // x and y keep track of pacman's coordinates. Delta x and y
	//keep track of the changes in those coordinates, since that is what delta means
	private int req_dx, req_dy; // req means the requested variable. It is used to store what the user inputs into the game
	
	private final int legitSpeeds[] = {1, 2, 3, 4, 6, 8}; // An array to track the valid speeds
	private final int maxSpeed = 6; // The highest game speed achievable 
	private int currentSpeed = 3;  // The current game speed
	private short [] screenData; // An array to represent the data for the current state of the game. It will be updated
	//and redrawn frequently to control the game' graphics
	private Timer timer; // The timer object is used to update the game state at regular intervals
	
	private final short levelData[] = { // An array that controls the map structure. Each number represents the border & item
			// 0 is blue, 1 is the left border, 2 = top border, 4 = right border, 8 = bottom border, 16 = white dot (food)
			19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
	        17, 16, 16, 16, 16, 24, 16, 16, 16, 16, 16, 16, 16, 16, 20,
	        25, 24, 24, 24, 28, 0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
	        0,  0,  0,  0,  0,  0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
	        19, 18, 18, 18, 18, 18, 16, 16, 16, 16, 24, 24, 24, 24, 20,
	        17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
	        17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
	        17, 16, 16, 16, 24, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
	        17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 18, 18, 18, 18, 20,
	        17, 24, 24, 28, 0, 25, 24, 24, 16, 16, 16, 16, 16, 16, 20,
	        21, 0,  0,  0,  0,  0,  0,   0, 17, 16, 16, 16, 16, 16, 20,
	        17, 18, 18, 22, 0, 19, 18, 18, 16, 16, 16, 16, 16, 16, 20,
	        17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 20,
	        17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 20,
	        25, 24, 24, 24, 26, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28	
	};
	
	
	// Constructor
	public BluePrint() {
		loadImages(); // Ensuring images are loaded
		initVariables(); // Ensuring variables are initialized
		addKeyListener(new TAdapter()); // The function that controls the input keys from the user (sort of like a joystick)
		setFocusable(true); // A method that ensures that the game window has focus so that the game receives user input, 
		//without it, the game might not receive accurate user input
		initGame(); // A method that initializes the game state/starts the game
	}
	

	// Setting up a method to load all the relevant images from my local directory
	private void loadImages( ) {
		down = new ImageIcon("C:\\Users\\Arief\\Desktop\\Georgian@ILAC\\Programming Fundamentals\\Java Pacman\\images/down.gif").getImage();
		up = new ImageIcon("C:\\Users\\Arief\\Desktop\\Georgian@ILAC\\Programming Fundamentals\\Java Pacman\\images/up.gif").getImage();
		left = new ImageIcon("C:\\Users\\Arief\\Desktop\\Georgian@ILAC\\Programming Fundamentals\\Java Pacman\\images/left.gif").getImage();
		right = new ImageIcon("C:\\Users\\Arief\\Desktop\\Georgian@ILAC\\Programming Fundamentals\\Java Pacman\\images/right.gif").getImage();
		ghost = new ImageIcon("C:\\Users\\Arief\\Desktop\\Georgian@ILAC\\Programming Fundamentals\\Java Pacman\\images/ghost.gif").getImage();
		heart = new ImageIcon("C:\\Users\\Arief\\Desktop\\Georgian@ILAC\\Programming Fundamentals\\Java Pacman\\images/heart.png").getImage();
	}
	
	// A method to display the intro screen
	private void showIntroScreen(Graphics2D g2d) {
		 
    	String start = "Press SPACE to start";
        g2d.setColor(Color.yellow);
        g2d.drawString(start, (screenSize)/4, 150);
    }

	// A method to paint the score display
	private void drawScore(Graphics2D g) {
        g.setFont(smallFont);
        g.setColor(new Color(5, 181, 79));
        String s = "Score: " + score;
        g.drawString(s, screenSize / 2 + 96, screenSize + 16);

        for (int i = 0; i < lives; i++) {
            g.drawImage(heart, i * 28 + 8, screenSize + 1, this);
        }
    }
	
	
	 // A method to initialize the variables with values
	private void initVariables() {
		screenData = new short [noBlocks * noBlocks]; // The screenData array declared earlier which controls the current state of the game (used for redrawing maps)
		d = new Dimension(400, 400); // The height and width of the entire pacman field
		// The positions of the ghosts
		ghost_x = new int [maxGhosts]; 
		ghost_y = new int [maxGhosts];
		ghost_dx = new int [maxGhosts];
		ghost_dy = new int [maxGhosts];
		ghostSpeed = new int [maxGhosts];
		dx = new int [4];
		dy = new int [4];
		
		timer = new Timer (50, this); // The timer variable determines how often the game is redrawn. In this case, I set it to every 50 milliseconds
		timer.start(); // Starts the Timer, causing it to start sending action events to its listeners
	}
	
	
	// A method to initialize the game
	private void initGame() {
		lives = 3; 
		score = 0;
		initLevel(); // There will be multiple levels in the game
		noGhosts = 6;
		currentSpeed = 3;
	}
	
	
	// A method to initialize the current level of the game
	private void initLevel () {
		int i; // Declaring i in advance, it's not essential
		for (i = 0; i < noBlocks * noBlocks; i++) { // A for-loop that copies the entire data from the levelData into a different variable (screenData) 
			screenData[i] = levelData[i];
		}
	}
	
	
	// A method to display graphics 
	private void playGame (Graphics2D g2d) {
		if (dying) {
			death(); // Rendering the pacman dead
		}
		
		else {
			movePacman();
			drawPacman(g2d);
			moveGhosts(g2d);
			checkMaze();
		}
	}
	
	
	// A method to control the movement of the pacman
	private void movePacman() {
        int pos;
        short ch;

        if (pacman_x % blockSize == 0 && pacman_y % blockSize == 0) { // Checks if the Pacman character is at an intersection of four blocks by checking if its x and y 
        	//coordinates are both divisible by the block size
            pos = pacman_x / blockSize + noBlocks * (int) (pacman_y / blockSize);
            ch = screenData[pos];

            if ((ch & 16) != 0) { // Read the comments at the end. 16 is the white dot/pellet/food
                screenData[pos] = (short) (ch & 15);
                score++;
            }

            /* If there is a directional change requested (i.e. the player pressed a key to change direction), it checks if the requested direction is valid by checking 
            the type of block in the direction of the requested move. If the direction is valid, it updates the pacmand_x and pacmand_y variables to reflect the requested move
             */
            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    pacmand_x = req_dx;
                    pacmand_y = req_dy;
                }
            }

            
            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
                pacmand_x = 0;
                pacmand_y = 0;
            }
        } 
        pacman_x = pacman_x + pacmanPace * pacmand_x;
        pacman_y = pacman_y + pacmanPace * pacmand_y;
    }
	
	
	/* This method is used to draw the Pacman character on the game screen. The image used to draw Pacman depends on the direction in which the player wants Pacman to move. 
	 If the player has requested Pacman to move left, the "left" image is used */
	
	private void drawPacman(Graphics2D g2d) {

        if (req_dx == -1) {
        	g2d.drawImage(left, pacman_x + 1, pacman_y + 1, this);
        } else if (req_dx == 1) {
        	g2d.drawImage(right, pacman_x + 1, pacman_y + 1, this);
        } else if (req_dy == -1) {
        	g2d.drawImage(up, pacman_x + 1, pacman_y + 1, this);
        } else {
        	g2d.drawImage(down, pacman_x + 1, pacman_y + 1, this);
        }
    }
	
	
	// A method to check the condition of the maze, and whether the pacman has eaten all the pellets
	private void checkMaze() {
	    int i = 0;
	    boolean finished = true;

	    while (i < noBlocks * noBlocks && finished) {
	        if ((screenData[i] & 48) != 0) {  // This operation extracts only the bits that represent the maze blocks that Pacman can pass through horizontally and vertically,
	        	//which are represented by the binary value 00110000 (48 in decimal)
	            finished = false;
	        }
	        i++;
	    }

	    if (finished) {
	        score += 50;
	        if (noGhosts < maxGhosts) {
	            noGhosts++;
	        }
	        if (currentSpeed < maxSpeed) {
	            currentSpeed++;
	        }
	        initLevel();
	    }
	}

	// A method that controls what happens when the player loses all lives or when the player dies to a ghost
    private void death() {

    	lives--;

        if (lives == 0) {
            inGame = false;
        }

        continueLevel();
    }
	
	
	// This method is responsible for updating the position of the ghosts in the game
	private void moveGhosts(Graphics2D g2d) {
		// Variables
		int pos;
		int count;

		// Loop through all ghosts.
		for (int i = 0; i < noGhosts; i++) {

		    // Check if the ghost has reached a new block.
		    if (ghost_x[i] % blockSize == 0 && ghost_y[i] % blockSize == 0) {

		        // Calculate the position of the ghost in the maze.
		        pos = ghost_x[i] / blockSize + noBlocks * (int) (ghost_y[i] / blockSize);

		        // Initialize count variable to zero.
		        count = 0;

		        // Check if the ghost can move left and add to possible moves if so.
		        if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) {
		            dx[count] = -1;
		            dy[count] = 0;
		            count++;
		        }

		        // Check if the ghost can move up and add to possible moves if so.
		        if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) {
		            dx[count] = 0;
		            dy[count] = -1;
		            count++;
		        }

		        // Check if the ghost can move right and add to possible moves if so.
		        if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) {
		            dx[count] = 1;
		            dy[count] = 0;
		            count++;
		        }

		        // Check if the ghost can move down and add to possible moves if so.
		        if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) {
		            dx[count] = 0;
		            dy[count] = 1;
		            count++;
		        }

		        // Check if there are any possible moves.
		        if (count == 0) {

		            // If there are no possible moves, the ghost is stuck.
		            // Check if the ghost is surrounded by blocks and has no way to move.
		            // If so, it stops moving.
		            if ((screenData[pos] & 15) == 15) {
		                ghost_dx[i] = 0;
		                ghost_dy[i] = 0;
		            } 
		            else {
		                // If the ghost is not surrounded by blocks, it moves in the opposite direction.
		                ghost_dx[i] = -ghost_dx[i];
		                ghost_dy[i] = -ghost_dy[i];
		            }

		        } 
		        else {

		            // If there are possible moves, randomly select one.
		            count = (int) (Math.random() * count);

		            // Limit the maximum number of possible moves to 3.
		            if (count > 3) {
		                count = 3;
		            }

		            // Move the ghost in the selected direction.
		            ghost_dx[i] = dx[count];
		            ghost_dy[i] = dy[count];
		        }

		    }
            ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
            ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
            drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);

            if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12)
                    && pacman_y > (ghost_y[i] - 12) && pacman_y < (ghost_y[i] + 12)
                    && inGame) {

                dying = true;
            }
        }
    }
	
	
	
	// A method to draw/paint the ghosts
	private void drawGhost(Graphics2D g2d, int x, int y) {
    	g2d.drawImage(ghost, x, y, this);
        }
	
	
	// A method to define the position of the ghosts and to continue to a higher level
	private void continueLevel() {
		int dx = 1;
		int random;
		
		for (int i = 0; i < noGhosts; i++) {
			ghost_y[i] = 4 * blockSize; 
            ghost_x[i] = 4 * blockSize;
            ghost_dy[i] = 0;
            ghost_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1)); // It does not include the last index by default, so + 1

            if (random > currentSpeed) {
                random = currentSpeed;
            }

            ghostSpeed[i] = legitSpeeds[random]; // Setting up the ghost speeds to be randomized according to all the valid speeds I declared above
		}
		
		pacman_x = 7 * blockSize;  // Starting position for the pacman
        pacman_y = 11 * blockSize;
        pacmand_x = 0;	// Reset directional movement
        pacmand_y = 0;
        req_dx = 0;		// Reset direction controls
        req_dy = 0;
        dying = false;
	}
	
	
	
	
	
	// This is a method that draws the maze for the game. It takes in a Graphics2D object as a parameter and uses it to draw the maze on the screen.
	private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < screenSize; y += blockSize) {
            for (x = 0; x < screenSize; x += blockSize) {

                g2d.setColor(new Color(0,255,250));
                g2d.setStroke(new BasicStroke(5));
                
                if ((levelData[i] == 0)) { 
                	g2d.fillRect(x, y, blockSize, blockSize);
                 }

                if ((screenData[i] & 1) != 0) { 
                    g2d.drawLine(x, y, x, y + blockSize - 1);
                }

                if ((screenData[i] & 2) != 0) { 
                    g2d.drawLine(x, y, x + blockSize - 1, y);
                }

                if ((screenData[i] & 4) != 0) { 
                    g2d.drawLine(x + blockSize - 1, y, x + blockSize - 1,
                            y + blockSize - 1);
                }

                if ((screenData[i] & 8) != 0) { 
                    g2d.drawLine(x, y + blockSize - 1, x + blockSize - 1,
                            y + blockSize - 1);
                }

                if ((screenData[i] & 16) != 0) { 
                    g2d.setColor(new Color(255,255,255));
                    g2d.fillOval(x + 10, y + 10, 6, 6);
               }

                i++;
            }
        }
    }
	
	
	// A method to paint the game. The method takes a Graphics object as an argument, which is used to draw the game elements on the screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Calling the super.paintComponent method to clear the screen and set the background color to black

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);

        
        /* If the game is in progress, it calls the playGame method to update and draw the game elements, otherwise,
        it calls the showIntroScreen method to display the intro screen */
        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);  
        }

        Toolkit.getDefaultToolkit().sync(); /* A method call in Java that ensures that the display is up-to-date. It synchronizes the graphics 
        state of the display with the internal state of the program. It is typically called after painting graphics to the screen to ensure that 
        the changes are immediately visible. It's especially important in multi-threaded programs where changes to the display may be made by multiple threads simultaneously */
        g2d.dispose(); // A good practice to free up resources after use
	}
	
	
	// A method to set up the cursor controller
	class TAdapter extends KeyAdapter { // The KeyAdapter is a built-in java class that provides an implementation of the KeyListener interface, which allows components 
		//to respond to keyboard events. Please read the documentation for more information
		public void keyPressed (KeyEvent e) {
			int key = e.getKeyCode();
			
			if (inGame) { // An if statement that checks if the user is in-game. It defaults to check whether the boolean is true
				if (key == KeyEvent.VK_LEFT) { // Controls horizontal leftward movement
                    req_dx = -1;
                    req_dy = 0;
                } 
				
				else if (key == KeyEvent.VK_RIGHT) { // Controls horizontal rightward movement
                    req_dx = 1;
                    req_dy = 0;
                } 
				
				else if (key == KeyEvent.VK_UP) { // Controls vertical upward movement
                    req_dx = 0;
                    req_dy = -1;
                } 
				
				else if (key == KeyEvent.VK_DOWN) { // Controls vertical downward movement
                    req_dx = 0;
                    req_dy = 1;
                } 
				
				else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) { // If the timer is running and the esc key is pressed, the game ends
                    inGame = false;
                } 
            } 
			
			else {
                if (key == KeyEvent.VK_SPACE) { // Whenever the space key is pressed, the game is supposed to start and the initGame() method is called
                    inGame = true;
                    initGame();
                }
            }
        }
}

	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

}





/* The moveGhosts() 
 * 
It loops through each ghost in the game.
It checks if the ghost is at an intersection point (where it can change its direction) by checking if its x and y coordinates are divisible by the size of a block.
If the ghost is at an intersection point, it determines which directions it can move in by checking the bits of the screenData array at the ghost's current position. If a bit is set to 0, that means the ghost can move in that direction. The possible directions are stored in the dx and dy arrays.
If there are no possible directions for the ghost to move in, it checks if the ghost is trapped by checking if all 4 bits of the screenData array at the ghost's current position are set to 1. If so, the ghost stops moving. If not, the ghost reverses its direction.
If there are possible directions for the ghost to move in, it randomly chooses one of them (up to a maximum of 3 directions) and updates its ghost_dx and ghost_dy variables.
The method then updates the ghost's position based on its current direction and speed, and draws the ghost on the screen.
Finally, the method checks if the ghost has collided with Pacman. If so, it sets the dying variable to true, which will trigger the game over sequence
 
*/


/* ch & 16 is a bitwise AND operation between the value of ch and the integer 16.

In this case, ch is a short value representing the contents of the block that the Pacman is currently occupying, and 16 is a binary number equivalent to 00010000 in binary representation.

By performing a bitwise AND operation with 16, we are checking if the 5th bit (counting from the rightmost bit) of ch is set to 1. If it is, then ch & 16 will evaluate to a non-zero value (in this case, 16), indicating that there is a pellet in the block that the Pacman is currently occupying.

The expression (ch & 15) in the next line of code sets the 5th bit of ch to 0, effectively removing the pellet from the block
*/