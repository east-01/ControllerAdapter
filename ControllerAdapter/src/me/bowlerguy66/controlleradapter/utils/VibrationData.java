package me.bowlerguy66.controlleradapter.utils;

public class VibrationData {

	private int leftStrength;
	private int rightStrength;
	private int ticks;
	
	public VibrationData(int leftStrength, int rightStrength, int ticks) {
		this.leftStrength = leftStrength;
		this.rightStrength = rightStrength;
		this.ticks = ticks;
	}

	public int getLeftStrength() {
		return leftStrength;
	}

	public void setLeftStrength(int leftStrength) {
		this.leftStrength = leftStrength;
	}

	public int getRightStrength() {
		return rightStrength;
	}

	public void setRightStrength(int rightStrength) {
		this.rightStrength = rightStrength;
	}

	public int getTicks() {
		return ticks;
	}

	public void setTicks(int ticks) {
		this.ticks = ticks;
	}
	
}
