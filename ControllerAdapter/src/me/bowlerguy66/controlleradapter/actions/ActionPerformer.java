package me.bowlerguy66.controlleradapter.actions;

import java.awt.Robot;

import me.bowlerguy66.controlleradapter.ControllerAdapter;
import me.bowlerguy66.controlleradapter.utils.VibrationData;

public class ActionPerformer {

	public static void performActions(ControllerAdapter main, ActionValue[] actionSequence) {
				
		for(ActionValue actionValue : actionSequence) {
			try {

				Robot rob = new Robot();

				InputAction action = actionValue.getAction();
				Object value = actionValue.getValue();
		
				switch(action) {
					case KEY_PRESS:
						rob.keyPress((int) value);
						break;
					case KEY_RELEASE:
						rob.keyRelease((int) value);
						break;
					case MOUSE_PRESS:
						rob.mousePress((int) value);
						break;
					case MOUSE_RELEASE:
						rob.mouseRelease((int) value);
						break;
					case OPEN_KEYBOARD:
						break;
					case CLOSE_KEYBOARD:
						break;
					case SWITCH_LAYOUT:
						String newLayout = (String) value;
						main.getLayoutManager().setCurrentLayout(newLayout);
						break;
					case SWITCH_ANALOG_STICKS:
						main.getLayoutManager().getCurrentLayout().switchAnalogSticks();
						break;
					case SETTINGS:
						break;
					case VIBRATION:
						main.getControllerManager().setVibration((VibrationData) value);
						break;
					default:
						break;				
				}
				
			} catch(Exception e) {
				
			}
		}
		
	}
	
}
