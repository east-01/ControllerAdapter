package me.bowlerguy66.controlleradapter.analogsticks.specialized;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;

import me.bowlerguy66.controlleradapter.analogsticks.AnalogStick;
import me.bowlerguy66.controlleradapter.utils.ParseUtils;

/**
 * @author mulle
 *	An AnalogStick class that adaps analog stick controls to mouse movements
 */
public class StickAsMouse extends AnalogStick {

	float sensitivity = 15;
	float deadzone = 0.1f;
	boolean acceleration = false;
	
	private float moveX;
	private float moveY;

	private float accX;
	private float accY;
	
	/**
	 * Constructor for StickAsMouse
	 * @param args String arguments for StickAsMouse
	 * <ul>
	 * <li>sens: float, the sensitivity of analog stick</>
	 * <li>deadzone: float, the deadzone that the controller stick has to be outside of to count movement</>
	 * <li>acceleration: boolean, whether the cursor has acceleration or not</>
	 * </ul>
	 */
	public StickAsMouse(String[] args) {
		super(args);
		for(String arg : args) {
			String argName = arg.substring(0, arg.indexOf('=')).trim();
			String argVal = arg.substring(arg.indexOf('=') + 1, arg.length()).trim();
			switch(argName) {
			case "sens":
				sensitivity = ParseUtils.parseFloat(argName, argVal);
				break;				
			case "deadzone":
				deadzone = ParseUtils.parseFloat(argName, argVal);				
				break;
			case "acceleration":
				acceleration = ParseUtils.parseBoolean(argName, argVal);
				break;
			default:
				System.out.println("ERROR: Unknown argument \"" + argName + "\" when creating StickAsMouse");
				break;
			}
		}
	}
	
	@Override
	public void tick(float xVal, float yVal) {
		if(Math.abs(xVal) < deadzone) xVal = 0;
		if(Math.abs(yVal) < deadzone) yVal = 0;
		if(xVal == 0 && yVal == 0) return;
						
		try {
			int[] offset = acceleration ? moveAcceleration(xVal, yVal) : moveNormal(xVal, yVal);
			Point mouse = MouseInfo.getPointerInfo().getLocation();
			moveMouse(new Point((int) (mouse.getX() + offset[0]), (int) (mouse.getY() + offset[1])));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Move mouse around with analog inputs, no acceleration
	 * @param xVal The x axis value of the controller analog stick
	 * @param yVal The y axis value of the controller analog stick
	 * @return A two value integer array
	 * <ul>
	 * <li>[0] = xOffset</li>
	 * <li>[1] = yOffset</li>
	 * </ul>
	 */
	public int[] moveNormal(float xVal, float yVal) {
		
		// The strength of the movement, depends on how far the analog stick is pushed
		float strength = (float) Math.sqrt(xVal*xVal + yVal*yVal);
		if(strength > 1) strength = 1;
		
		// Y is inverted
		float xComp = xVal * sensitivity * strength;
		float yComp = -yVal * sensitivity * strength;
		
		int x = 0;
		int y = 0;
			
		// This way allows us to store partial pixels of movement inbetween ticks because Robot().mouseMove() only takes int inputs
		moveX += xComp;
		if(Math.abs(moveX) >= 1) {
			x = (int) moveX;
			moveX -= (int)moveX;
		}
		moveY += yComp;
		if(Math.abs(moveY) >= 1) {
			y = (int) moveY;
			moveY -= (int)moveY;
		}
		return new int[] {x, y};
	}
	
	/**
	 * Move mouse around with analog inputs, acceleration included
	 * @param xVal The x axis value of the controller analog stick
	 * @param yVal The y axis value of the controller analog stick
	 * @return A two value integer array
	 * <ul>
	 * <li>[0] = xOffset</li>
	 * <li>[1] = yOffset</li>
	 * </ul>
	 */
	public int[] moveAcceleration(float xVal, float yVal) {
		// Y is inverted
		if(Math.abs(xVal) > 0) {
			accX += xVal;
			if(accX > 1) accX = 1;
			if(accX < -1) accX = -1;
		} else {
			accX = 0;
		}
		if(Math.abs(yVal) > 0) {
			accY += yVal;
			if(accY > 1) accY = 1;
			if(accY < -1) accY = -1;
		} else {
			accY = 0;
		}
		
		float xComp = accX * sensitivity;
		float yComp = -accY * sensitivity;
		
		int x = 0;
		int y = 0;
			
		// This way allows us to store partial pixels of movement inbetween ticks because Robot().mouseMove() only takes int inputs
		moveX += xComp;
		if(Math.abs(moveX) >= 1) {
			x = (int) moveX;
			moveX -= (int)moveX;
		}
		moveY += yComp;
		if(Math.abs(moveY) >= 1) {
			y = (int) moveY;
			moveY -= (int)moveY;
		}
		return new int[] {x, y};
	}
	
	// Swiped from stackoverflow:
	// https://stackoverflow.com/questions/2941324/how-do-i-set-the-position-of-the-mouse-in-java
	/**
	 * Move the mouse to a specific point in screen space
	 * @param p The point that the mouse will be moved to
	 */
	public static void moveMouse(Point p) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();

		// Search the devices for the one that draws the specified point.
		for (GraphicsDevice device : gs) {
			GraphicsConfiguration[] configurations = device.getConfigurations();
			for (GraphicsConfiguration config : configurations) {
				Rectangle bounds = config.getBounds();
				if (bounds.contains(p)) {
					// Set point to screen coordinates.
					Point b = bounds.getLocation();
					Point s = new Point(p.x - b.x, p.y - b.y);

					try {
						Robot r = new Robot(device);
						r.mouseMove(s.x, s.y);
					} catch (AWTException e) {
						e.printStackTrace();
					}

					return;
				}
			}
		}
		// Couldn't move to the point, it may be off screen.
		return;
	}

}
