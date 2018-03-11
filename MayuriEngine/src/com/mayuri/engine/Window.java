package com.mayuri.engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Window {
	
	private JFrame frame;
	private BufferedImage image; //where the actual pixel data will be stored
	private Canvas canvas; //our image will be rendered to
	private BufferStrategy bs;
	private Graphics g;
	
	public Window(gameContainer gc) {
		 //buffer means something we store in RAM
		image = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_RGB); //the type of image we want is an RGB image
		canvas = new Canvas();
		
		//in order for us to set the dimensions of our canvas we're going to make an instance variable of type Dimension that will help us set the dimensions easily
		Dimension s = new Dimension( (int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale())); // if we didnt Scale our height and width then the window would be too tiny to see
		canvas.setPreferredSize(s);
		canvas.setMaximumSize(s);
		canvas.setMinimumSize(s);
		
		frame = new JFrame(gc.getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //program ends when the frame is Xd out
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER); //add the canvas to the frame and center it
		frame.pack(); //sets frame to the size of the canvas
		frame.setLocationRelativeTo(null); //frame will open in the middle of the screen
		frame.setResizable(false); //no resize
		frame.setVisible(true); //be able to see the frame
		
		canvas.createBufferStrategy(2); //2 buffers to render to
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
	}
	
	public void update(){
		g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null); //drawing our image to our canvas
		bs.show(); // this is drawing it to the bufferstragey so we have to use the show method
	}

	public BufferedImage getImage() {
		return image;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public JFrame getFrame() {
		return frame;
	}

}
