package me.bowlerguy66.controlleradapter.layouts;

import java.util.Map;

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
		
	}
	
	public void tick() {
		ControllerManager cm = main.getControllerManager();
		if(cm == null || cm.getAxes() == null) return;
		if(leftStick != null) leftStick.tick(cm.getAxes().lx, cm.getAxes().ly);
		if(rightStick != null) rightStick.tick(cm.getAxes().rx, cm.getAxes().ry);
		if(leftTrigger != null) leftTrigger.tick(cm.getAxes().lt);
		if(rightTrigger != null) rightTrigger.tick(cm.getAxes().rt);
	}
	
	public SimpleXInputDeviceListener createListener() {
		return new SimpleXInputDeviceListener() {
			@Override
			public void buttonChanged(final XInputButton button, final boolean pressed) {
				if(pressed) {
					if(ControllerAdapter.cycleButton != null && ControllerAdapter.cycleButton == button) main.getLayoutManager().cycleLayout();
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
