package com.akumainc.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//creates an object that contains the spritesheet
//used by other classes to get their individual sprites
public class SpriteSheet {
	
	@SuppressWarnings("unused")
	private String path;
	private float sWidth, sHeight;
	private BufferedImage sheet;
	
	//constructor requires the path of the image file and the
	//width and height of the overall SpriteSheet image
	public SpriteSheet(String path, float sWidth, float sHeight) {
		this.path = path;
		this.sWidth = sWidth;
		this.sHeight = sHeight;
		
		//trys to create a buffered image sheet from the path
		//provided in the constructor
		try {
			sheet = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//renders the entire sheet if necessary
	public void render(Graphics2D g) {
		g.drawImage(sheet, 50, 50, null);
	}

	public float getsWidth() {
		return sWidth;
	}

	public void setsWidth(float sWidth) {
		this.sWidth = sWidth;
	}

	public float getsHeight() {
		return sHeight;
	}

	public void setsHeight(float sHeight) {
		this.sHeight = sHeight;
	}

	public BufferedImage getSheet() {
		return sheet;
	}

	public void setSheet(BufferedImage sheet) {
		this.sheet = sheet;
	}
	
	
	
}
