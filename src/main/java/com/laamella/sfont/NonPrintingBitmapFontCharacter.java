package com.laamella.sfont;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

/**
 * A character that is not visible, it only occupies some space.
 */
public class NonPrintingBitmapFontCharacter implements BitmapFontCharacter {
	private final Dimension size;

	public NonPrintingBitmapFontCharacter(final int width) {
		this.size = new Dimension(width, 1);
	}

	public void draw(final Graphics graphics, final Point point) {
		// Does nothing. It's non-printing.
	}

	public Dimension getSize() {
		return size;
	}

}
