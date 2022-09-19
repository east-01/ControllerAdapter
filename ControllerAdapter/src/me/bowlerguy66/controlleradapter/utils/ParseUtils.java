package me.bowlerguy66.controlleradapter.utils;

import java.awt.event.MouseEvent;

public class ParseUtils {

	public static int stringToMouseCode(String s) throws IllegalArgumentException {
		switch(s) {
		case "LEFT":
			return MouseEvent.BUTTON1_DOWN_MASK;
		case "RIGHT":
			return MouseEvent.BUTTON3_DOWN_MASK;
		case "MIDDLE":
			return MouseEvent.BUTTON2_DOWN_MASK;
		default:
			throw new IllegalArgumentException("The only acceptable mouse strings are LEFT, RIGHT, MIDDLE");
		}
	}

	public static float parseFloat(String argName, String argVal) {
		try {
			return Float.parseFloat(argVal);
		} catch(Exception e) {
			System.out.println("ERROR: Failed to parse the argument \"" + argName + "\" as float. \"" + argVal + "\" not accepted.");
			return 0;
		}
	}

	public static boolean parseBoolean(String argName, String argVal) {
		try {
			return Boolean.parseBoolean(argVal);
		} catch(Exception e) {
			System.out.println("ERROR: Failed to parse the argument \"" + argName + "\" as boolean. \"" + argVal + "\" not accepted.");
			return false;
		}
	}

}
