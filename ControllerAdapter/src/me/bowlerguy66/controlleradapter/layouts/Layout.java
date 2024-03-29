package me.bowlerguy66.controlleradapter.layouts;

import java.util.Map;

import com.github.strikerx3.jxinput.XInputButtons;
import com.github.strikerx3.jxinput.enums.XInputButton;
import com.github.strikerx3.jxinput.listener.SimpleXInputDeviceListener;

import me.bowlerguy66.controlleradapter.ControllerManager;
import me.bowlerguy66.controlleradapter.ControllerAdapter;
import me.bowlerguy66.controlleradapter.actions.ActionPerformer;
import me.bowlerguy66.controlleradapter.actions.ActionValue;
import me.bowlerguy66.controlleradapter.analogsticks.AnalogStick;
import me.bowlerguy66.controlleradapter.analogsticks.AnalogTrigger;

public class Layout {

	private ControllerAdapter main;
	
	private String title;
	
	private Map<XInputButton, ActionValue[]> buttonPressActions;
	private Map<XInputButton, ActionValue[]> buttonReleaseActions;
	
	private AnalogStick leftStick;
	private AnalogStick rightStick;
	
	private AnalogTrigger leftTrigger;
	private AnalogTrigger rightTrigger;
	
	private SimpleXInputDeviceListener listener;
	
	/**
	 * Count the amount of ticks that the toggle enable/disable buttons have been pressed
	 */
	private int toggleActiveTicks;
	
	public Layout(ControllerAdapter main,
				  String title,
				  Map<XInputButton, ActionValue[]> buttonPressActions, 
				  Map<XInputButton, ActionValue[]> buttonReleaseActions,
				  AnalogStick leftStick,
				  AnalogStick rightStick,
				  AnalogTrigger leftTrigger,
				  AnalogTrigger rightTrigger) {
		
		this.main = main;
		this.listener = createListener();
		
		this.title = title;
		
		this.buttonPressActions = buttonPressActions;
		this.buttonReleaseActions = buttonReleaseActions;
		this.leftStick = leftStick;
		this.rightStick = rightStick;
		this.leftTrigger = leftTrigger;
		this.rightTrigger = rightTrigger;
		
		this.toggleActiveTicks = 0;
		
	}
	
	public void tick() {

		ControllerManager cm = main.getControllerManager();
		if(cm == null || cm.getAxes() == null) return;

		XInputButtons buttons = cm.getButtons();
		
		// Check if we're trying to enable/disable the adapter
		boolean toggleActiveButtonsHeld = buttons.lThumb && buttons.rThumb && buttons.lShoulder && buttons.rShoulder;
		if(toggleActiveButtonsHeld) {
			toggleActiveTicks++;
			if(toggleActiveTicks >= ControllerAdapter.TOGGLE_ACTIVE_TICKS) {
				ControllerAdapter.IS_ACTIVE = !ControllerAdapter.IS_ACTIVE;
				toggleActiveTicks = 0;
				main.getInfoOverlay().updateText(ControllerAdapter.IS_ACTIVE ? "Adapter active" : "Adapter disabled", 60*2);
			}
		} else {
			toggleActiveTicks = 0;
		}
		
		// Don't want to act on events if the adapter isn't active
		if(!ControllerAdapter.IS_ACTIVE) return;
		
		if(leftStick != null) leftStick.tick(cm.getAxes().lx, cm.getAxes().ly);
		if(rightStick != null) rightStick.tick(cm.getAxes().rx, cm.getAxes().ry);
		if(leftTrigger != null) leftTrigger.tick(cm.getAxes().lt);
		if(rightTrigger != null) rightTrigger.tick(cm.getAxes().rt);
		
	}
	
	public SimpleXInputDeviceListener createListener() {
		return new SimpleXInputDeviceListener() {
			@Override
			public void buttonChanged(final XInputButton button, final boolean pressed) {
				// Don't want to act on events if the adapter isn't active
				if(!ControllerAdapter.IS_ACTIVE) return;
				// Don't want to act on events if the keyboard is open
				if(main.getKeyboardOverlay().isOpen()) return;
				if(pressed) {
					if(ControllerAdapter.keyboardButton != null && ControllerAdapter.keyboardButton == button) main.getKeyboardOverlay().toggleOpen();
					if(!buttonPressActions.containsKey(button)) return;
					ActionPerformer.performActions(main, buttonPressActions.get(button));
				} else {
					if(!buttonReleaseActions.containsKey(button)) return;
					ActionPerformer.performActions(main, buttonReleaseActions.get(button));
				}
			}
		};
	}
	
	public void switchAnalogSticks() {
		AnalogStick temp = this.rightStick;
		this.rightStick = this.leftStick;
		this.leftStick = temp;
	}
	
	public SimpleXInputDeviceListener getListener() {
		return listener;
	}
	
	public String getTitle() {
		return title;
	}
	
}
