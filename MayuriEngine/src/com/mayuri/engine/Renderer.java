package com.mayuri.engine;

import java.awt.image.DataBufferInt;

import com.mayuri.engine.gfx.Font;
import com.mayuri.engine.gfx.Image;
import com.mayuri.engine.gfx.imageTile;

public class Renderer {
	
	private int pW, pH; //pixel width / pixel height
	private int[] pixels;
	
	private Font font = Font.STANDARD;
	
	public Renderer(gameContainer gc) {
		pW = gc.getWidth();
		pH = gc.getHeight();  
		//gives our array direct access to the pixel data
		pixels = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		
	}
	
	public void clear() {
		
		for(int i = 0; i < pixels.length; i++) {
			
			pixels[i] = 0;
		}
	}
	
	//set our pixel data, keep our image from going out of bounds
	public void setPixel(int x , int y, int value) {
		
		//if we are out of bounds or our value is set to the default value of the shade
		if( x < 0 || x >= pW || y < 0 || y >= pH || ((value >> 24) & 0xff) == 0) {
			return;
		}
		//convert our 2dimensional number to a one dimensional number
		pixels[x + y *pW] = value; //now that we know it's in bounds, set pixel data
	}
	
	public void drawText(String text, int offX, int offY, int color) {
		Image fontImage = font.getFontImage();
		
		text = text.toUpperCase();
		int offset = 0;
		
		for(int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i) - 32; // in unicode 0 is not Space, we need space to be 0 and space is 32 so we subtract
			
			for(int y = 0; y < fontImage.getHeight(); y++) {
				for(int x = 0; x < font.getWidths()[unicode]; x++) {
					if(font.getFontImage().getPixel()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getWidth()] == 0xffffffff) {
						setPixel(x + offX + offset, y + offY, color);
					}
				}
			}
			offset += font.getWidths()[unicode];
		}
	}
	
	/**
	 * draw our image
	 * @param image
	 * @param offX
	 * @param offY
	 */
	public void drawImage(Image image, int offX, int offY) {
		//dont render code
		if(offX < -image.getWidth()) return;
		if(offY < -image.getHeight()) return;
		if(offX >= pW) return;
		if(offY >= pH) return;
		
		//declare the variables down here because theres no reason to declare variables if the code won't render anyway
		int newX = 0;
		int newY = 0;
		int newWidth = image.getWidth();
		int newHeight = image.getHeight();
		

		
		//clipping code
		if(offX < 0) newX -= offX;
		if(offY < 0) newY -= offY;
		if(newWidth + offX >= pW) newWidth -= newWidth + offX - pW;
		if(newHeight + offY > pH) newHeight -= newHeight + offY - pH;
		
		for(int y = newY; y < newHeight; y++) {
			for(int x = newX; x < newWidth; x++) {
				setPixel(x + offX, y+ offY, image.getPixel()[x+y * image.getWidth()]);
			}
		}
	}
	
	public void drawImageTile(imageTile image, int offX, int offY, int tileX, int tileY) {
		//dont render code
				if(offX < -image.getTileWidth()) return;
				if(offY < -image.getTileHeight()) return;
				if(offX >= pW) return;
				if(offY >= pH) return;
				
				//declare the variables down here because theres no reason to declare variables if the code won't render anyway
				int newX = 0;
				int newY = 0;
				int newWidth = image.getTileWidth();
				int newHeight = image.getTileHeight();
				
				//clipping code
				if(offX < 0) newX -= offX;
				if(offY < 0) newY -= offY;
				if(newWidth + offX >= pW) newWidth -= newWidth + offX - pW;
				if(newHeight + offY > pH) newHeight -= newHeight + offY - pH;
				
				for(int y = newY; y < newHeight; y++) {
					for(int x = newX; x < newWidth; x++) {
						setPixel(x + offX, y+ offY, image.getPixel()[(x + tileX * image.getTileWidth()) + (y + tileY * image.getTileHeight()) * image.getWidth()]);
			}
		}
	}
	
	public void drawRect(int offX , int offY, int width, int height, int color) {
		
		for(int y = 0; y <= height; y++) {
			setPixel(offX, y + offY, color); //left line of rectangle
			setPixel(offX + width, y + offY, color); //right line of rectangle
		}
		
		for(int x = 0; x <= width; x++) {
			setPixel(x + offX, offY , color);//top line
			setPixel(x + offX, offY + height, color); //bottom line
		}
	}
	
	public void drawFillRect(int offX, int offY, int width, int height, int color) {
		//dont render code
		if(offX < -width) return;
		if(offY < -height) return;
		if(offX >= pW) return;
		if(offY >= pH) return;
		
		//declare the variables down here because theres no reason to declare variables if the code won't render anyway
		int newX = 0;
		int newY = 0;
		int newWidth = width;
		int newHeight = height;
		
		//clipping code
		if(offX < 0) newX -= offX;
		if(offY < 0) newY -= offY;
		if(newWidth + offX >= pW) newWidth -= newWidth + offX - pW;
		if(newHeight + offY > pH) newHeight -= newHeight + offY - pH;
		
		for (int y = newY; y <= newHeight; y++) {
			for (int x = newX; x <= newWidth; x++) {
				setPixel(x + offX, y + offY, color);
			}
		}
	}

}
