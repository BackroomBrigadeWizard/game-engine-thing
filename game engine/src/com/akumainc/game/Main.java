package com.akumainc.game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;

import com.akumainc.game.entities.Player;
import com.akumainc.game.tiles.Wall;

//main class contains the gameloop and draws the frame
public class Main extends JFrame{
	
	/**
	 * @author Taylor
	 */
	private static final long serialVersionUID = 1L;
	//width and height of the window
	private int width; 
	private int height;
	
	//spritesheet object. Used to determine sprites when creating player/enemy objects
	//may later be moved to level loading class b/c they will be created while decoding the level
	//and added to a LinkedList entities when that happens
	SpriteSheet sheet;
	
	//temp sprite for testing reasons
	Sprite sprite;
	
	//player object
	Player player;
	
	//object for the inputHandler class
	InputHandler input;
	
	//inset object for later getting the insets of the window
	Insets insets;
	
	//rng object
	Random rand;
	
	//keeps the game runing
	private boolean isRunning = true;
	
	//velocity vars for object x and y pos
	private static float velX;
	private static float velY;
	
	//temp array of tiles for testing
	private LinkedList<Wall> walls;
	
	//camera object for keeping the player centered on the screen
	//makes map appear to move instead of the player
	private Camera cam;
	
	//backbuffered image object
	BufferedImage backBuffer;
	
	//x and y vars for an entity
	private float x;
	private float y;
	
	//initializes the objects used, similar to constructors
	void initialize() {
		
		rand = new Random();
		
		input = new InputHandler(this);
		addKeyListener(input);
		setFocusable(true);
		
		width = 1280;
		height = 720;
		
		velX = 0;
		velY = 0;
		
		x = rand.nextInt(width);
		y = rand.nextInt(height);
		
		cam = new Camera(0, 0);
		
		walls = new LinkedList<Wall>();
		
		//places a wall tile every 64 pixels around the edges of the screen
		//used for testing the collision methods
		for(int i = 0; i < width/64; i++) {
			for(int j = 0; j < (int)height / 64; j++) {
				if(i == 0 && j == 0) 
					walls.add(new Wall(i* 64, j* 64, 64, 64, true));
				
				else if (j == 0 || j == (int)height / 64 - 1 && i > 0) {
					walls.add(new Wall(i*64, j*64, 64, 64, true));
				}
				
				else if (i == 0 || i == (int)width / 64 - 1 && j > 0) {
					walls.add(new Wall(i*64, j*64, 64, 64, true));
				}
				
				//else
					//walls[i][j] = null;
			}
		}
		
		sheet = new SpriteSheet("res/BadSprites.png", 64, 64);
		
		sprite = new Sprite(sheet, x, y, 0, 0, 64, 64);
		
		player = new Player(width/2, height/2, 64, 64, "player", 0, 0, sheet);
		
		backBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	
		setTitle("Game");
		setSize(width, height);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		insets = getInsets();
		
		setSize(insets.left + width + insets.right, insets.top + height + insets.bottom);
	}
	
	public static float getVelX() {
		return velX;
	}

	public static void setVelX(float velX1) {
		velX = velX1;
	}

	public static float getVelY() {
		return velY;
	}

	public static void setVelY(float velY1) {
		velY = velY1;
	}


	//the run method, runs and game and includes the game loop
	public void run() {
		
		initialize();
		
		//fps counters needed to determine the current fps of the game
		long lastFpsTime = 0;
		int fps = 0;
		
		//used later to determine the wait time
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
		
		while(isRunning) {
			
			//work out how long its been since the last update,
			//will be used to calculate how far the entites should 
			//move this loop
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ((double)OPTIMAL_TIME);
			
			//update the frame counter 
			lastFpsTime += updateLength;
			fps++;
			
			//update our FPS counter if a second has passed since 
			//we last recorded it
			if(lastFpsTime >= 1000000000) {
				//System.out.println("(FPS: " + fps + ")");
				setTitle("Game || fps: " + fps);
				lastFpsTime = 0;
				fps = 0;
			}
			
			//update game logic
			update(delta);
			
			//update rendering
			render();
			
			//we want each frame t take 10 milliseconds, to do this
			//we've recoded when we started the frame we add 10 ms
			//to this and then factor in the current time to give 
			//us our final value to wait for
			//remember that this in is ms, whereas our lastLoopTime, ect. is in ns
			try{Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME)/1000000);}
			catch(Exception e) {}
			
		}
		setVisible(false);
	}


	//remember to multiply time related values by delta(ie: x's and y's)
	void update(double delta) {
		x += velX * delta;
		y += velY * delta;
		
		player.update(delta, input);
		cam.update(delta, player);
		
		for(Wall w : walls) {
			w.update(delta);
		}
		
		player.testCollision(walls);
		
		
	} 
	
	//render method for the game
	//must pass bbg to other render methods to avoid flickering images
	void render() {
		Graphics2D g = (Graphics2D) getGraphics();
		
		Graphics2D bbg = (Graphics2D) backBuffer.getGraphics();
		
		bbg.setColor(Color.BLACK);
		bbg.fillRect(0, 0, width, height);
		
		//everything below will translate relative to the
		//cameras x and y
		//this will make them appear to be moving and the player remains in the
		//center of the screen
		bbg.translate(cam.getX(), cam.getY());
		
		
		for(Wall w : walls) {
			w.render(bbg);
		}
		
		player.render(bbg);
		
		//translates what is drawn below relative to the negative value of the
		//cameras x and y
		//causes them to remain stationary on the screen
		//will be used for things such as debug text
		bbg.translate(-cam.getX(), -cam.getY());
		
		g.drawImage(backBuffer, insets.left, insets.top, this);
		
	}
	
	public static void main(String[] args) {
		Main game = new Main();
		game.run();
		System.exit(0);
	}

}
