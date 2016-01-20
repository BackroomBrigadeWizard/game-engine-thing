package com.akumainc.game.entities;

import java.awt.Graphics2D;

import com.akumainc.game.Sprite;
import com.akumainc.game.SpriteSheet;

//abstract entity class so that we can create subclasses such as 
//player, enemy, friendlyNPC etc
public abstract class Entities {
	
	private float x, y, scryX, scryY;
	private String tag;
	private Sprite sprite;
	private SpriteSheet sheet;
	
	//constructor x and y determine the starting position of the entity
	//width and height are the width and height of the overall sprite
	//scryX and scryY tell the sprite class where to pull the sprite from on the spritesheet
	//this is multiplied by the width to get the coordinate
	//tag will be used later for entity-entity collisions
	//spritesheet is the sheet from which the player sprite will be pulled
	public Entities(float x, float y, String tag, float scryX, float scryY, SpriteSheet sheet) {
		this.x = x;
		this.y = y;
		this.tag = tag;
		this.scryX = scryX;
		this.scryY = scryY;
		this.sheet = sheet;
	}
	
	public void update(double delta) {}
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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	

}
