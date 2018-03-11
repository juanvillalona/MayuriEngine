package com.mayuri.engine.gfx;

public class Font {

	public static final Font STANDARD = new Font("/Fonts/fonts.png");
	private Image fontImage;
	private int[] offsets;
	private int[] widths; //fonts aren't all the same width, so we're going to have individual widths for our letters
	
	//pass in path, so we can know where to download our Font
	public Font(String path) {
		fontImage = new Image(path);
		
		offsets = new int[59]; //reason why we have it at 58 is because of UNICODE
		widths = new int[59];
		
		int unicode = 0;
		
		for(int i = 0; i < fontImage.getWidth(); i++) {
			if(fontImage.getPixel()[i] == 0xff0000ff) {
				offsets[unicode] = i;
			}
			if(fontImage.getPixel()[i] == 0xffffff00) {
				widths[unicode] = i - offsets[unicode];
				unicode++;
			}
		}
	}

	public Image getFontImage() {
		return fontImage;
	}

	public void setFontImage(Image fontImage) {
		this.fontImage = fontImage;
	}

	public int[] getOffsets() {
		return offsets;
	}

	public void setOffsets(int[] offsets) {
		this.offsets = offsets;
	}

	public int[] getWidths() {
		return widths;
	}

	public void setWidths(int[] widths) {
		this.widths = widths;
	}
}
