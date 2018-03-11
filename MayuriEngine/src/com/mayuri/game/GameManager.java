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
	private soundClip clip;

	public GameManager() {
		
		image = new imageTile("/imageTileTest.png", 16, 16);
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
		temp += dt * 20;
		
		if(temp > 3) {
			temp = 0;
		}
	}
	float temp = 0;

	@Override
	public void render(gameContainer gc, Renderer r) {
		
		r.drawImageTile(image, gc.getInput().getMouseX() - 8, gc.getInput().getMouseY() - 16, (int)temp , 0);
		
	}
	
	public static void main(String args[]) {
		
		gameContainer gc = new gameContainer(new GameManager());
		gc.start();
	}

}
