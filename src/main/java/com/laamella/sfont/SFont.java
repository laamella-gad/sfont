package com.laamella.sfont;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * A Java version of <a href="http://www.linux-games.com/sfont/">Karl Bartel's
 * SFont library</a>.
 * <p/>
 * Use SFont to quickly draw text with character images. The character images
 * are stored in an ordinary image file. Several tools exist to create these
 * images.
 */
public class SFont extends BitmapFont {
	private static final String STANDARD_CONTAINED_CHARACTERS = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

	private static class SFontBitmapFontCharacter implements BitmapFontCharacter {
		public final int left;
		public final int right;
		private final BufferedImage fontImage;
		private final Dimension size;

		public SFontBitmapFontCharacter(final BufferedImage fontImage, final int left, final int right) {
			this.fontImage = fontImage;
			this.left = left;
			this.right = right;
			this.size = new Dimension(right - left, fontImage.getHeight());
		}

		public void draw(final Graphics graphics, final Point point) {
			graphics.drawImage(fontImage, point.x, point.y, point.x + size.width, point.y + size.height - 1, left, 1,
					right, size.height, null);
		}

		public Dimension getSize() {
			return size;
		}
	}

	/**
	 * Initialize the font. The characters in the fontImage are expected to be
	 * in the order as specified by the original SFont library.
	 * 
	 * @param fontImage
	 *            the image that contains the font character bitmaps.
	 */
	public SFont(final BufferedImage fontImage) {
		this(fontImage, 1, STANDARD_CONTAINED_CHARACTERS);
	}

	/**
	 * Initialize the font. The characters in the fontImage are expected to be
	 * in the order as specified by the original SFont library.
	 * 
	 * @param fontImage
	 *            the image that contains the font character bitmaps.
	 * @param spacing
	 *            the amount of pixels put between characters.
	 */
	public SFont(final BufferedImage fontImage, final int spacing) {
		this(fontImage, 1, STANDARD_CONTAINED_CHARACTERS);
	}

	/**
	 * Initialize the font.
	 * 
	 * @param fontImage
	 *            the image that contains the font character bitmaps.
	 * @param spacing
	 *            the amount of pixels put between characters.
	 * @param containedCharactersString
	 *            the characters that can be found in fontImage, from left to
	 *            right.
	 */
	public SFont(final BufferedImage fontImage, final int spacing, final String containedCharactersString) {
		super(spacing);
		final char[] containedCharacters = containedCharactersString.toCharArray();
		int start = -1;
		int currentChar = 0;
		int widestChar = 0;
		boolean inCharacter = false;
		for (int x = 0; x < fontImage.getWidth(); x++) {
			final int[] array = new int[4];
			fontImage.getRaster().getPixel(x, 0, array);
			final boolean isPurplePixel = array[0] == 255 && array[1] == 0 && array[2] == 255;
			if (isPurplePixel) {
				if (inCharacter) {
					if (start != -1) {
						final SFontBitmapFontCharacter characterInfo = new SFontBitmapFontCharacter(fontImage, start, x);
						addCharacter(containedCharacters[currentChar], characterInfo);
						currentChar++;

						widestChar = (widestChar >= characterInfo.getSize().width) ? widestChar : characterInfo
								.getSize().width;
						if (currentChar == containedCharactersString.length()) {
							break;
						}
					}
					inCharacter = false;
				}
				start = x + 1;
			} else {
				inCharacter = true;
			}
		}

		if (getCharacter(' ') == null) {
			// Just guess the width of the space character if it wasn't in the font.
			addCharacter(' ', new NonPrintingBitmapFontCharacter(widestChar / 2));
		}
	}

	public SFont(final BufferedImage fontImage, final String containedCharactersString) {
		this(fontImage, 1, containedCharactersString);
	}
}
