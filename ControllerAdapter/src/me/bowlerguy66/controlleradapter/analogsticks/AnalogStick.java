package me.bowlerguy66.controlleradapter.analogsticks;

/**
 * @author mulle
 *	Abstract class that acts as a base for all AnalogStick supported classes
 */
public abstract class AnalogStick {
	protected String[] args;
	/**
	 * Constructor for an AnalogStick, takes in a String[] for arguments
	 * @param args Arguments for the AnalogStick class
	 */
	public AnalogStick(String[] args) {
		this.args = args;
	}
	/**
	 * Tick the analog stick with new given axes values.
	 * @param xVal The x axis value of the controller analog stick
	 * @param yVal The y axis value of the controller analog stick
	 */
	public abstract void tick(float xVal, float yVal);
}
