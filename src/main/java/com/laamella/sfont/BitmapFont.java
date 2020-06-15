package com.laamella.sfont;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public abstract class BitmapFont {
	private final Map<Character, BitmapFontCharacter> characterSet = new HashMap<>();
	private final int spacing;

	protected BitmapFont(final int spacing) {
		this.spacing = spacing;
	}

	/**
	 * Draws text.
	 * 
	 * @param graphics
	 *            the graphics object to draw on.
	 * @param startPoint
	 *            the upper left corner of the text.
	 * @param text
	 *            the text to draw. Characters that are not in the font are
	 *            ignored.
	 */
	public void drawText(final Graphics graphics, final Point2D startPoint, final String text) {
		drawText(graphics, (int) startPoint.getX(), (int) startPoint.getY(), text);
	}

	/**
	 * Draws text.
	 * 
	 * @param graphics
	 *            the graphics object to draw on.
	 * @param x
	 *            the left point of the text.
	 * @param y
	 *            the top point of the text.
	 * @param text
	 *            the text to draw. Characters that are not in the font are
	 *            ignored.
	 */
	public void drawText(final Graphics graphics, final int x, final int y, final String text) {
		final Point point = new Point(x, y);
		for (final char c : text.toCharArray()) {
			final BitmapFontCharacter character = characterSet.get(c);
			if (character != null) {
				character.draw(graphics, point);
				point.x += character.getSize().width + spacing;
			}
		}
	}

	/**
	 * Draws text horizontally centered.
	 * 
	 * @param graphics
	 *            the graphics object to draw on.
	 * @param leftX
	 *            the left point of the area in which to draw.
	 * @param rightX
	 *            the right point of the area in which to draw.
	 * @param y
	 *            the top point of the text.
	 * @param text
	 *            the text to draw. Characters that are not in the font are
	 *            ignored.
	 */
	public void drawTextCentered(final Graphics graphics, final int leftX, final int rightX, final int y,
			final String text) {
		drawTextCentered(graphics, new Point((leftX + rightX) / 2, y), text);
	}

	/**
	 * Draws text horizontally centered.
	 * 
	 * @param graphics
	 *            the graphics object to draw on.
	 * @param centerPoint
	 *            the upper middle point of the text.
	 * @param text
	 *            the text to draw. Characters that are not in the font are
	 *            ignored.
	 */
	public void drawTextCentered(final Graphics graphics, final Point centerPoint, final String text) {
		drawText(graphics, centerPoint.x - (getTextWidth(text) / 2), centerPoint.y, text);
	}

	/**
	 * @param text
	 *            text to measure the height of.
	 * @return the height of text in pixels. This is always the same as the
	 *         height of the font image.
	 */
	public int getTextHeight(final String text) {
		int height = 0;
		for (final char c : text.toCharArray()) {
			final BitmapFontCharacter character = characterSet.get(c);
			if (character != null) {
				if (character.getSize().height > height) {
					height = character.getSize().height;
				}
			}
		}
		return height;
	}

	/**
	 * @param text
	 *            text to measure the size of.
	 * @return the size of the text in pixels.
	 */
	public Dimension getTextSize(final String text) {
		return new Dimension(getTextWidth(text), getTextHeight(text));
	}

	/**
	 * @param text
	 *            text to measure the width of.
	 * @return the width of text in pixels.
	 */
	public int getTextWidth(final String text) {
		int width = 0;
		for (final char c : text.toCharArray()) {
			final BitmapFontCharacter character = characterSet.get(c);
			if (character != null) {
				width += character.getSize().width + spacing;
			}
		}
		return width - spacing;
	}

	/**
	 * Add a character to the font.
	 * 
	 * @param c
	 *            the character.
	 * @param character
	 *            information about the character.
	 */
	public void addCharacter(final char c, final BitmapFontCharacter character) {
		characterSet.put(c, character);
	}

	/**
	 * @param c
	 *            the character to get information about
	 * @return the character information, or null if the character does not
	 *         exist in this font.
	 */
	public BitmapFontCharacter getCharacter(final char c) {
		return characterSet.get(c);
	}
}
