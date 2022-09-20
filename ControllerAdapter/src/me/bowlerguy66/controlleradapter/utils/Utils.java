package me.bowlerguy66.controlleradapter.utils;

import java.awt.Color;

public class Utils {
	
	public static Color cloneColor(Color c, int newOpacity) {
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), newOpacity);
	}

}
