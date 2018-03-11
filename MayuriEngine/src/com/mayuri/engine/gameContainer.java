  package com.mayuri.engine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class gameContainer implements Runnable{
	
	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;
	private AbstractGame game;
	
	private boolean running = false;
	//Our refresh rate/ Once every 60 seconds
	private final double UPDATE_CAP = 1.0/60.0;
	private int width = 320, height = 240; //320x240
	private float scale = 4f; //4
	private String title = "MayuriEngine v1.0";
	
	public gameContainer(AbstractGame game) {
		this.game = game;
	}
	
	public void start() {
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);
		
		thread = new Thread(this);
		//.run() sets this as our main thread
		thread.run();
	}
	
	public void stop() {
		
	}
	

	public void run() {
		
		running = true;
		
		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0; //current nanoTime of our system, we diivide it by a billion because it's too accurate. Turns to milliseconds
		double passedTime = 0;
		double unprocessedTime = 0;
		
		double frameTime = 0;
		int frames = 0; //how many frames have passed
		int fps = 0; //how many frames per second
		
		while(running) {
			
			render = false;
			
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime; // how long has it been since lastTime updated to firstTime;
			lastTime = firstTime;
			
			unprocessedTime += passedTime;
			frameTime += passedTime;
			
			while(unprocessedTime >= UPDATE_CAP) { //if our thread freezes and our frame rate drops, we still want to update the game on things we missed
				unprocessedTime -= UPDATE_CAP;
				render = true; //only render when we update the screen;
				
				game.update(this, (float)UPDATE_CAP);
				input.update();
			}
			if(render) {
				renderer.clear();
				game.render(this, renderer);
				renderer.drawText("FPS:" + fps, 0, 0, 0xff00ffff);
				window.update();
				frames++;
			}
			if (frameTime >= 1.0) { //if frameTime is greater than a second then do this.
				frameTime = 0;
				fps = frames;
				frames = 0;
			}
			else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
		}
		dispose();
	}
	
	private void dispose(){
		//TODO: dispose code
	}
	

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}

	
}
