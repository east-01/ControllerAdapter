package me.bowlerguy66.controlleradapter.analogsticks;

import me.bowlerguy66.controlleradapter.analogsticks.specialized.StickAsMouse;
import me.bowlerguy66.controlleradapter.analogsticks.specialized.StickAsScrollWheel;
import me.bowlerguy66.controlleradapter.analogsticks.specialized.StickAsWASD;

public class AnalogParser {

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
