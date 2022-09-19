package me.bowlerguy66.controlleradapter.analogsticks.specialized;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import me.bowlerguy66.controlleradapter.analogsticks.AnalogStick;

public class StickAsWASD extends AnalogStick {

	private boolean[][] pressStatus;
	private int[][] keymap = new int[][] {new int[] {KeyEvent.VK_A, KeyEvent.VK_D},
								  		  new int[] {KeyEvent.VK_S, KeyEvent.VK_W}};

	public StickAsWASD(String[] args) {
		super(args);
		pressStatus = new boolean[][] {new boolean[2], new boolean[2]};
	}

	@Override
	public void tick(float xVal, float yVal) {
	
		// This number is 0.8 * (sqrt(2)/2)
		if(Math.abs(xVal) < 0.56568542494) xVal = 0;
		if(Math.abs(yVal) < 0.56568542494) yVal = 0;
						
		try {
			Robot r = new Robot();

			for(int axis = 0; axis < 2; axis++) {
				float targ = axis == 0 ? xVal : yVal;
				for(int direction = 0; direction < 2; direction++) {
					int dir = direction == 0 ? -1 : 1;
					
					boolean status = Math.signum(targ) == dir;
					if(pressStatus[axis][direction] == status) continue;
					pressStatus[axis][direction] = status;
					
					if(status) {
						r.keyPress(keymap[axis][direction]);
					} else {
						r.keyRelease(keymap[axis][direction]);
					}
					
				}
			}

		} catch(Exception e) {
			
		}

	}

}
