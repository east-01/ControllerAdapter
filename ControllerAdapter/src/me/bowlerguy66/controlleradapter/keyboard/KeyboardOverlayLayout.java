package me.bowlerguy66.controlleradapter.keyboard;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import com.github.strikerx3.jxinput.XInputAxes;
import com.github.strikerx3.jxinput.enums.XInputButton;
import com.github.strikerx3.jxinput.listener.SimpleXInputDeviceListener;

import me.bowlerguy66.controlleradapter.ControllerAdapter;
import me.bowlerguy66.controlleradapter.Values;

public class KeyboardOverlayLayout {

	private ControllerAdapter main;
	
	public static int DIRECTION_HOLD_TIME = (int) (60 * 0.5f); // half a second
	public static int DIRECTION_HOLD_RATE = 10; // ticks in between repeats
	
	private float deadzone = 0.7f;
	
	private int x, y;
	// The last state of the left axes
	private int lastX, lastY;
	// The amount of time the current state has been held
	@SuppressWarnings("unused")
	private int xTime, yTime;
	private KeyboardButton lastFocusedButton;
	private KeyboardButton focusedButton;
	
	private SimpleXInputDeviceListener listener;
	
	public KeyboardOverlayLayout(ControllerAdapter main) {
		this.main = main;
		this.x = 6;
		this.y = 3;
		this.listener = createListener();
		this.focusedButton = main.getKeyboardOverlay().getButtonAt(x, y);
		applyFocus(main.getKeyboardOverlay().getButtonAt(x, y));
	}
	
	public void tick() {
				
		moveKeyboardFocus();
		
	}
	
	public SimpleXInputDeviceListener createListener() {
		return new SimpleXInputDeviceListener() {
			@Override
			public void buttonChanged(final XInputButton button, final boolean pressed) {
				// Don't want to act on events if the keyboard is not open
				if(!main.getKeyboardOverlay().isOpen()) return;
				if(pressed) {
					if(ControllerAdapter.keyboardButton != null && ControllerAdapter.keyboardButton == button) main.getKeyboardOverlay().toggleOpen();
					switch(button) {
					case A:
						focusedButton.doClick();
						break;
					default:
						break;
					}
				} else {
				}
			}
		};
	} 
	
	public void moveKeyboardFocus() {
		XInputAxes axes = main.getControllerManager().getAxes();
		float lx = axes.lx;
		float ly = -axes.ly;
		if(Math.abs(lx) < deadzone) lx = 0;
		if(Math.abs(ly) < deadzone) ly = 0;
		lx = Math.signum(lx);
		ly = Math.signum(ly);
		
		boolean xMove = (lx != lastX && lx != 0);
		boolean yMove = (ly != lastY && ly != 0);
		
		if(xMove || yMove) {
			moveFocus((int)lx, (int)ly);
			if(xMove) xTime = 0;
			if(yMove) yTime = 0;
		} else {
			if(!xMove) xTime++;
			if(!yMove) yTime++;
		}
		
		// Will always reach int form because of Math.signum()
		lastX = (int) lx;
		lastY = (int) ly;		
	}
	
	public void moveFocus(int xDir, int yDir) {
		// Perform movement in the direction of the stick until it goes out of bounds or reaches a new button
		lastFocusedButton = focusedButton;
		// [0, hKeyCnt] is the maximum amount of loops, since that means the keyboard has looped horizontally once
		for(int i = 0; i < KeyboardOverlay.hKeyCnt; i++) {
			x += xDir;
			y += yDir;
			// Set the x loc to wrap around
			//   If the direction is positive, new point is 0; if it is negative the new point is on the far right side
			if(x < 0 || x > KeyboardOverlay.hKeyCnt - 1) x = xDir == 1 ? 0 : KeyboardOverlay.hKeyCnt - 1;		
			if(y < 0 || y > KeyboardOverlay.vKeyCnt - 1) y = yDir == 1 ? 0 : KeyboardOverlay.vKeyCnt - 1;
			KeyboardButton foundButton = main.getKeyboardOverlay().getButtonAt(x, y);
			if(foundButton != null && foundButton != focusedButton) {
				applyFocus(foundButton);
				focusedButton = foundButton;
				break;
			}
		}
				
	}
	
	public void applyFocus(KeyboardButton button) {
		button.setBorder(getHighlightedBorder());
		if(lastFocusedButton != null) lastFocusedButton.setBorder(getDefaultBorder());
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public SimpleXInputDeviceListener getListener() {
		return listener;
	}
	
	public static Border getHighlightedBorder() {
		return BorderFactory.createLineBorder(Values.COLOR_MAIN_BRIGHT, 6);
	}
	
	public static Border getDefaultBorder() {
		return BorderFactory.createLineBorder(Values.COLOR_MAIN_HIGHLIGHT, 2);
	}
	
}
