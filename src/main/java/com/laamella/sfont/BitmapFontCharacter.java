package com.laamella.sfont;

import java.awt.*;

/**
 * Aa single character in a {@link BitmapFont}.
 */
public interface BitmapFontCharacter {
	void draw(Graphics graphics, Point point);

	Dimension getSize();
}