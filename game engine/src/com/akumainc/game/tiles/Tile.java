package com.akumainc.game.tiles;

import java.awt.Graphics2D;
import java.awt.Rectangle;

//abstract tile class to later create various tile types
//to be used within the game
public abstract class Tile {
	
	private float x, y, width, height;
	private boolean collidable;
	
	private Rectangle bounds;
	
	public Tile(float x, float y, float width, float height, boolean collidable) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.collidable = collidable;
		
		bounds = new Rectangle((int)x, (int)y, (int)width, (int)height);
	}
	
	public void update(double delta) {
		
	}
	
	public void render(Graphics2D g) {}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public boolean isCollidable() {
		return collidable;
	}

	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	
	
}
