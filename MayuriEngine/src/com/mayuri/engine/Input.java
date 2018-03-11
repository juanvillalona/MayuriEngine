package com.mayuri.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	
	private gameContainer gc;

	private final int NUM_KEYS = 256; //ammount of keys on a keyboard
	private boolean[] keys = new boolean[NUM_KEYS];//make a boolean array for all the keys
	private boolean[] keysLast = new boolean[NUM_KEYS]; //what were the keys on the last frame
	
	private final int NUM_BUTTONS = 5; 
	private boolean[] buttons = new boolean[NUM_BUTTONS];//make a boolean array for all the buttons
	private boolean[] buttonsLast = new boolean[NUM_BUTTONS]; //what were the buttons on the last frame

	private int mouseX, mouseY; //location of our mouse
	private int scroll; //1:scroll up, 0: no scroll, -1:scroll down

	public Input(gameContainer gc) {
		this.gc = gc;
		mouseX = 0;
		mouseY = 0;
		scroll = 0;
		
		//adds listeners to our canvas
		gc.getWindow().getCanvas().addKeyListener(this);
		gc.getWindow().getCanvas().addMouseMotionListener(this);
		gc.getWindow().getCanvas().addMouseListener(this);
		gc.getWindow().getCanvas().addMouseWheelListener(this);
	}
	
	public void update() {
		//takes current input and stores it on our last input
		scroll = 0;
		
		for(int i = 0; i < NUM_KEYS; i++) {
			keysLast[i] = keys[i];
		}
		
		for(int i = 0; i < NUM_BUTTONS; i++) {
			buttonsLast[i] = buttons[i];
		}
	}
	
	public boolean isKey(int keyCode) {
		return keys[keyCode];
	}
	 //did we just release it?
	//was the key down in the last frame?
	public boolean isKeyUp(int keyCode) {
		return !keys[keyCode] && keysLast[keyCode]; //it's not down in the current frame but it was down in the last frame
	}
	
	public boolean isKeyDown(int keyCode) {
		return keys[keyCode] && !keysLast[keyCode]; //it's down on the current frame but it was up on the last frame
	}
	
	public boolean isButton(int button) {
		return buttons[button];
	}
	
	public boolean isButtonUp(int button) {
		return !buttons[button] && buttonsLast[button]; //it's not down in the current frame but it was down in the last frame
	}
	
	public boolean isButtonDown(int button) {
		return buttons[button] && !buttonsLast[button]; //it's down on the current frame but it was up on the last frame
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scroll = e.getWheelRotation();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = (int)(e.getX() / gc.getScale()); //we need to scale this so that we don't go out of bounds
		mouseY = (int)(e.getY() / gc.getScale());
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = (int)(e.getX() / gc.getScale()); //we need to scale this so that we don't go out of bounds
		mouseY = (int)(e.getY() / gc.getScale());
		
		//PROBLEMS THAT MAY OCCUR
		//Needs to take into consideration the width and height and boundaries of our window
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
	
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		buttons[e.getButton()] = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
	keys[e.getKeyCode()] = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public int getScroll() {
		return scroll;
	}
	
	

}
