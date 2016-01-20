package com.akumainc.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

//class that gets, renders, and updates individual sprites
//from the larger spritesheet
public class Sprite {
	
	@SuppressWarnings("unused")//its actually used
	private SpriteSheet sheet;
	@SuppressWarnings("unused")//they are actually used
	private float scryX, scryY, width, height, x, y, velX, velY;
	
	BufferedImage sprite;
	
	//constructor requires the spritesheet that contains the desired sprite
	//x and y position to render it at, the width and height of the sprite
	//and the scryX and scryY
	//scryX and scryY tell the sprite class where to pull the sprite from on the spritesheet
	//this is multiplied by the width to get the coordinate
	public Sprite(SpriteSheet sheet, float x, float y, float scryX, float scryY, float width, float height) {
		this.sheet = sheet;
		this.x = x;
		this.y = y;
		this.scryX = scryX;
		this.scryY = scryY;
		this.width = width;
		this.height = height;
		
		//gets the sprite from the spritesheet
		sprite = sheet.getSheet().getSubimage((int)scryX, (int)scryY, (int)width, (int)height);
		//sprite = (BufferedImage) sprite.getScaledInstance(2, 2, 1);
		
		
	}
	
	//updates the position of the sprite
	public void update(double delta) {
		x += velX * delta;
		y += velY * delta;
	}
	
	//draws the sprite on the screen
	public void render(Graphics2D g) {
		g.drawImage(sprite, (int)x, (int)y, null);
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}
	
	public BufferedImage getSprite() {
		return sprite;
	}
	
	public float getWidth() {return width;}
	public float getHeight() {return height;}

}
