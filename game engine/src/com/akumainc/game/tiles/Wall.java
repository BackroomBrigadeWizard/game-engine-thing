package com.akumainc.game.tiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Wall extends Tile	{

	private float x, y, width, height;
	private boolean collidable;
	
	private Rectangle bounds;
	
	//basic wall class for testing the Tile class
	//currently does not support sprites
	public Wall(float x, float y, float width, float height, boolean collidable) {
		super(x, y, width, height, collidable);
		
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.collidable = collidable;
		
		if(collidable) {
			bounds = new Rectangle((int)x, (int)y, (int)width, (int)height);
		}
		else
			bounds = null;
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.CYAN);
		g.fillRect((int)x, (int)y, (int)width, (int)height);
		g.setColor(Color.RED);
		g.draw(getBounds());
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, (int)width,(int)height);
	}

}
