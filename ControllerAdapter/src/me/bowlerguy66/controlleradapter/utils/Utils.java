package me.bowlerguy66.controlleradapter.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Utils {
	
	public static Color cloneColor(Color c, int newOpacity) {
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), newOpacity);
	}

	public static BufferedImage loadImage(URL url) {		
		try {
			return ImageIO.read(url);
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}		
	}

}
