package com.laamella.sfont;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Aa single character in a {@link BitmapFont}.
 */
public interface BitmapFontCharacter {
	void draw(Graphics graphics, Point point);

	Dimension getSize();
}