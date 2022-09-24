package me.bowlerguy66.controlleradapter.analogsticks;

/**
 * @author mulle
 *	Abstract class that acts as a base for all AnalogTrigger supported classes
 */
public abstract class AnalogTrigger {
	/**
	 * Tick the analog trigger with a new given axis value.
	 * @param val The axis value of the controller trigger.
	 */
	public abstract void tick(float val);
}
