package me.bowlerguy66.controlleradapter.analogsticks.specialized;

import java.awt.Robot;

import me.bowlerguy66.controlleradapter.analogsticks.AnalogStick;
import me.bowlerguy66.controlleradapter.utils.ParseUtils;

/**
 * @author mulle
 *	An AnalogStick class that adaps analog stick controls to mouse wheel movements
 */
public class StickAsScrollWheel extends AnalogStick {

	float scrollSpeed = 3;
	float deadzone = 0.1f;
	
	float move;
	
	/**
	 * Constructor for StickAsScrollWheel
	 * @param args String arguments for StickAsScrollWheel
	 * <ul>
	 * <li>speed: float, the sensitivity of analog stick</>
	 * <li>deadzone: float, the deadzone that the controller stick has to be outside of to count movement</>
	 * </ul>
	 */
	public StickAsScrollWheel(String[] args) {
		super(args);
		for(String arg : args) {
			String argName = arg.substring(0, arg.indexOf('=')).trim();
			String argVal = arg.substring(arg.indexOf('=') + 1, arg.length()).trim();
			switch(argName) {
			case "speed":
				scrollSpeed = ParseUtils.parseFloat(argName, argVal);
				break;
			case "deadzone":
				deadzone = ParseUtils.parseFloat(argName, argVal);
				break;
			default:
				System.out.println("ERROR: Unknown argument \"" + argName + "\" when creating StickAsScrollWheel");
				break;
			}
		}
	}

	@Override
	public void tick(float xVal, float yVal) {
		
		if(Math.abs(yVal) < deadzone) yVal = 0;
		
		try {
			Robot rob = new Robot();
			// Need to invert yVal, makes it more intuitive
			move += scrollSpeed * -yVal;
			if(Math.abs(move) >= 1) {
				rob.mouseWheel((int) move);
				move -= (int) move;
			}
		} catch(Exception e) {
			
		}
	}
	
}
