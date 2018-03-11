package com.mayuri.engine.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * @author Mayuri
 *
 */
public class Image {
	
	private int width, height;
	private int[] pixel;
	
	/**
	 * Method used to read and retrieve an image
	 * @param path: variable given to the method in order to get the image
	 */
	public Image(String path) {
		BufferedImage image = null;
		
		try {
			//we need to read an image, but at the same time we have to go into the Image class and get it.
			image = ImageIO.read(Image.class.getResourceAsStream(path));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		width = image.getWidth();
		height = image.getHeight();
		pixel = image.getRGB(0, 0, width, height, null, 0, width);
		
		image.flush();
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

	public int[] getPixel() {
		return pixel;
	}

	public void setPixel(int[] pixel) {
		this.pixel = pixel;
	}

}
