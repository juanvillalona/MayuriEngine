package com.mayuri.game;

import java.awt.event.KeyEvent;

import javax.sound.sampled.LineUnavailableException;

import com.mayuri.engine.AbstractGame;
import com.mayuri.engine.Renderer;
import com.mayuri.engine.gameContainer;
import com.mayuri.engine.audio.soundClip;
import com.mayuri.engine.gfx.Image;
import com.mayuri.engine.gfx.imageTile;

public class GameManager extends AbstractGame{
	
	private imageTile image;
	//private image image;
	private soundClip clip;
	

	public GameManager() {
		
		image = new imageTile("/gokuleft_0.png", 16, 16);
		try {
			clip = new soundClip("/Audio/test.wav");
			clip.setVolume(-20); //decibals
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(gameContainer gc, float dt) {
		if(gc.getInput().isKeyDown(KeyEvent.VK_A)) {
			clip.play();
		}
		temp += dt * 10;
		
		if(temp > 9) {
			temp = 0;
		}
	}
	float temp = 0;

	@Override
	public void render(gameContainer gc, Renderer r) {
		
		r.drawImage(image, gc.getInput().getMouseX()-10, gc.getInput().getMouseY()-10);
		//r.drawImageTile(image, gc.getInput().getMouseX() - 8, gc.getInput().getMouseY() - 16, (int)temp , 0);
		//r.drawFillRect(gc.getInput().getMouseX() - 200, gc.getInput().getMouseY() - 200, 400, 400, 0xffffccff);
		
		
		
	}
	
	public static void main(String args[]) {
		
		gameContainer gc = new gameContainer(new GameManager());
		gc.setWidth(320);
		gc.setHeight(240);
		gc.setScale(3f);
		gc.start();
	}

}
