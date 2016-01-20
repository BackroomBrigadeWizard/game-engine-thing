package com.akumainc.game.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import com.akumainc.game.InputHandler;
import com.akumainc.game.Sprite;
import com.akumainc.game.SpriteSheet;
import com.akumainc.game.tiles.Wall;

public class Player extends Entities{

	private float x, y, velX, velY, scryX, scryY;
	private float width, height;
	private Sprite sprite;

	//char array for offset finder
	//used to store the location of transparent and
	//non transparent pixels within the sprite
	char[][] pixels;

	//add x and yOffsets to the x and y
	//subtract w and hOffset from the width and height
	private float xOffset, yOffset, wOffset, hOffset;
	private int farLeftX, farUpY, farRightX, farDownY;

	//InputHandler input;

	//private Rectangle bounds;

	//constructor x and y determine the starting position of the player
	//width and height are the width and height of the overall sprite
	//scryX and scryY tell the sprite class where to pull the sprite from on the spritesheet
	//this is multiplied by the width to get the coordinate
	//tag will be used later for entity-entity collisions
	//spritesheet is the sheet from which the player sprite will be pulled
	public Player(float x, float y, float width, float height, String tag, float scryX, float scryY, SpriteSheet sheet) {
		super(x, y, tag, scryX, scryY, sheet);
		this.x = x;	this.y = y;
		this.scryX = scryX;	this.scryY = scryY;
		this.width = width; this.height = height;

		sprite = new Sprite(sheet, x, y, scryX, scryY, 64, 64);

		//sets the pixel char array to the size of the sprite
		pixels = new char[sprite.getSprite().getWidth()][sprite.getSprite().getHeight()];

		farLeftX = 64;
		farUpY = 64;
		farRightX = 0;
		farDownY = 0;

		velX = 0;
		velY = 0;

		//iterates through the pixels of the sprite and sets
		//each location in the array to -, =, x, or y
		//- represents pixels that have an alpha of 0 and are transparent
		//= are non-transparent pixels
		//x, and y show what pixel hold the farthest left, right, up and down positions
		//used for finding the offsets of sprites so that in the future a person would
		//be able to use custom sprites with as little limitation as possible
		//and not have to go back and hardcode in what the offsets should be
		for(int yy = 0; yy < sprite.getSprite().getHeight(); yy++) {
			for(int xx = 0; xx < sprite.getSprite().getWidth(); xx++) {
				Color currColor = new Color(sprite.getSprite().getRGB(xx, yy), true);
				if(currColor.getAlpha() == 0) {
					pixels[xx][yy] = '-';
					//System.out.print("-");
				}
				else{
					pixels[xx][yy] = '=';
					//System.out.print("=");
				}

				//sets the location of the nontransparent pixels that
				//are closest to the edge of the sprite
				//the array is used for debugging and ensuring that the 
				//offsets are found correctly
				if(currColor.getAlpha() != 0) {
					if(xx < farLeftX) {
						farLeftX = xx;
						pixels[xx][yy] = 'x';
					}
					if(xx > farRightX) {
						farRightX = xx;
						pixels[xx][yy] = 'x';
					}
					if(yy < farUpY) {
						pixels[xx][yy] = 'y';
						farUpY = yy;
					}
					if(yy > farDownY) {
						pixels[xx][yy] = 'y';
						farDownY = yy;
					}
				}

			}
			System.out.println();
		}


		for(int yy = 0; yy < sprite.getSprite().getHeight(); yy++) {
			for(int xx = 0; xx < sprite.getSprite().getWidth(); xx++) {
				System.out.print(pixels[xx][yy]);
			}
			System.out.println();
		}

		System.out.println(farLeftX + " " + farUpY);
		System.out.println(farRightX + " " + farDownY);
		System.out.println(sprite.getSprite().getWidth());

		//puts the offsets for the right and bottom to zero if
		//the farthest non-transparent pixel on those sides is
		//equal to the width or height of the sprite
		if(farRightX == sprite.getSprite().getWidth() - 1) {
			farRightX = 0;
		}
		if(farDownY == sprite.getSprite().getHeight() - 1) {
			farDownY = 0;
		}

		xOffset = farLeftX;
		yOffset = farUpY;
		wOffset = farRightX;
		hOffset = farDownY;
		//System.out.println(yOffset);

	}

	//handles updating the states of the player object
	//such as the x, y, and velocity
	//delta is used to keep the updates consistent and 
	//separate from the frames
	public void update(double delta, InputHandler input) {
		x += velX * delta;
		y += velY * delta;

		move(input);

		//System.out.println("x: " + x + " || y: " + y);

	}

	//handles rendering aspects of the player class
	public void render(Graphics2D g) {
		g.drawImage(sprite.getSprite(), (int)x, (int)y, null);
		g.setColor(Color.RED);
		g.draw(getBounds());
		g.draw(topBounds());
		g.draw(leftBounds());
		g.draw(botBounds());
		g.draw(rightBounds());
	}

	//moves the player depending on the input
	//Requires an InputHandler object
	//upon keypress it sets the players velocity = to 5 pixels per
	//update in the desired direction
	public void move(InputHandler input) {
		if(input.isKeyDown(KeyEvent.VK_W)) {
			setVelY(-5);
		}
		else if(input.isKeyDown(KeyEvent.VK_S)) {
			setVelY(5);
		}
		else {
			setVelY(0);
		}

		if(input.isKeyDown(KeyEvent.VK_A)) {
			setVelX(-5);
		}
		else if (input.isKeyDown(KeyEvent.VK_D)) {
			setVelX(5);
		}
		else {
			setVelX(0);
		}
	}

	public void setVelX(float velX) {this.velX = velX;}
	public float getVelX() {return velX;}
	public float getVelY() {return velY;}
	public void setVelY(float velY) {this.velY = velY;}

	//bounding boxes for the player
	//there is one for each side and one for the sprite as a whole
	public Rectangle getBounds() {
		return new Rectangle((int)(x + xOffset), (int)(y + yOffset), 
				(int)(width - wOffset), (int)(height - hOffset));
	}

	public Rectangle topBounds() {
		return new Rectangle((int) (x + xOffset + 4), (int) (y + yOffset),
				(int)(width - wOffset - 8), (int)(height - hOffset)/2);
	}

	public Rectangle leftBounds() {
		return new Rectangle((int) (x + xOffset), (int) (y + yOffset) + 4, 4, 
				(int)(height - hOffset) - 8);
	}

	public Rectangle botBounds() {
		return new Rectangle((int)(x + xOffset) + 4, (int)(y + (height / 2)),
				(int)(width - wOffset) - 8, (int)(height - hOffset) / 2);
	}

	public Rectangle rightBounds() {
		return new Rectangle((int)((x + xOffset) + (width - wOffset)) - 4, (int) (y + yOffset)+ 4,
				4, (int)(height - hOffset) - 8);
	}


	//Tests collisions with walls using the .intersects method
	public void testCollision(LinkedList<Wall> walls) {

		for(Wall w : walls) {
			if(leftBounds().intersects(w.getBounds())) {
				x = (float) (w.getBounds().getX() + w.getBounds().getWidth()) - xOffset;
			}

			if(topBounds().intersects(w.getBounds())) {
				y = (float)(w.getBounds().getY() + w.getBounds().getHeight());
			}

			if(botBounds().intersects(w.getBounds())) {
				y = (float)(w.getBounds().getY() - height) + hOffset;
			}

			if(rightBounds().intersects(w.getBounds())) {
				x = (float)(w.getBounds().getX() - width) + wOffset;
			}
		}
	}

	//	public void stopMovement(Wall w) {
	//		//for(Wall w : walls) {
	//			if(leftBounds().getX() <= w.getBounds().getX() + w.getBounds().getWidth()) {
	//				x = (float) (w.getBounds().getX() + w.getBounds().getWidth());
	//			}
	//			if(topBounds().getY() <= w.getBounds().getY() + w.getBounds().getHeight()) {
	//				y = (float)(w.getBounds().getY() + w.getBounds().getHeight());
	//			}
	//		//}
	//	}
}
