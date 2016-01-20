package com.akumainc.game;

import com.akumainc.game.entities.Player;

//camera class keeps the player in the center of the screen
//is currently does not stop when the player collides with a wall and 
//continues holding the move button in the direction of the wall
public class Camera {
	
	private int x, y;
	
	//constructor for the starting position of the camera
	//currently set to 0, 0 on launch but would ideally be set to 
	//the players x and y + width or height divided by 2
	//then subtractin half the width and height of the window
	//ie: (player.x + (player.width / 2) - (WINDOW_WIDTH / 2)
	public Camera(int x, int y) {
		this.x = x; this.y = y;
	}
	
	public void update(double delta, Player p) {
		//moves the camera at the same rate as the players velocity
		//we make it negative to make it appear to move the right way
		//ie player is moving left so everything is translated to the right
		//since the player is moving at the same rate as the camera
		//it remains in the center of the screen while everything else moves appropriately
		x += -p.getVelX() * delta;
		y += -p.getVelY() * delta;
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
	
}