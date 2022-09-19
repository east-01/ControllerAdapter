package me.bowlerguy66.controlleradapter.analogsticks;

public abstract class AnalogStick {
	protected String[] args;
	public AnalogStick(String[] args) {
		this.args = args;
	}
	public abstract void tick(float xVal, float yVal);
}
