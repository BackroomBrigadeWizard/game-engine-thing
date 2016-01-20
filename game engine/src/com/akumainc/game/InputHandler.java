package com.akumainc.game;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//handles input used for navigating menus and controlling the player
public class InputHandler implements KeyListener {
	
	private boolean[] keys = new boolean[256];

	//assigns the newly created InputHandler to a Component
	public InputHandler(Component c) {
		c.addKeyListener(this);
	}
	
	//checks for a specific key being pressed
	//returns if that key is pressed using the keyCode param
	public boolean isKeyDown(int keyCode) {
		if(keyCode > 0 && keyCode < 256) {
			return keys[keyCode];
		}
		return false;
	}
	
	//not used
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	//called when the key is pressed
	//@param e KeyEvent sent by the component
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() > 0 && e.getKeyCode() < 256) {
			keys[e.getKeyCode()] = true;
		}
		
	}

	//called when the key is released
	@Override
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() > 0 && e.getKeyCode() < 256) {
			keys[e.getKeyCode()] = false;
		}
		
	}

	
	
}
