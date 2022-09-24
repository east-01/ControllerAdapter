package me.bowlerguy66.controlleradapter.analogsticks;

import me.bowlerguy66.controlleradapter.analogsticks.specialized.StickAsMouse;
import me.bowlerguy66.controlleradapter.analogsticks.specialized.StickAsScrollWheel;
import me.bowlerguy66.controlleradapter.analogsticks.specialized.StickAsWASD;

/**
 * @author mulle
 *	A class that parses AnalogStick/AnalogTrigger strings and returns the correct class
 *	according to the strings' name
 */
public class AnalogParser {

	/**
	 * Parse a string to an AnalogStick class, also passes in a String[] that contains each argument
	 * for the AnalogStick, each String will be parsed in its own specific type.
	 * @param s The string that holds a name of an AnalogStick type class, also arguments in between 
	 * 			parenthesis and separated by commas. {@code StickAsMouse(arg1=x,arg2=y,arg3=z)}
	 * @return An AnalogStick type class based off of the string given, null if the string isn't recognoized.
	 */
	public static AnalogStick parseAsAnalogStick(String s) {
		boolean hasArgs = s.contains("(");
		String cleanedString = s;
		String[] args = new String[0];
		if(hasArgs) {
			cleanedString = s.substring(0, s.indexOf('('));
			args = s.substring(s.indexOf("(") + 1, s.indexOf(")")).split(",");
		}
		
		if(cleanedString.equalsIgnoreCase("StickAsMouse")) {
			return new StickAsMouse(args);
		} else if(cleanedString.equalsIgnoreCase("StickAsWASD")) {
			return new StickAsWASD(args);
		} else if(cleanedString.equalsIgnoreCase("StickAsScrollWheel")) {
			return new StickAsScrollWheel(args);
		} else {
			return null;
		}
	}
	
}
