package com.mayuri.engine;

public abstract class AbstractGame {
	
	public abstract void update(gameContainer gc, float dt); //delta time
	public abstract void render(gameContainer gc, Renderer r); //give it the renderer

}
