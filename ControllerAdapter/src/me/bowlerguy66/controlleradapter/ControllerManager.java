package me.bowlerguy66.controlleradapter;

import com.github.strikerx3.jxinput.XInputAxes;
import com.github.strikerx3.jxinput.XInputButtons;
import com.github.strikerx3.jxinput.XInputComponents;
import com.github.strikerx3.jxinput.XInputDevice;

import me.bowlerguy66.controlleradapter.utils.VibrationData;

public class ControllerManager {

	@SuppressWarnings("unused")
	private ControllerAdapter main;
	
	private XInputDevice controller;
	private XInputButtons buttons;
	private XInputAxes axes;

	private int vibTicks;
	
	public ControllerManager(ControllerAdapter main) {
		this.main = main;
		
		try {
			controller = XInputDevice.getDeviceFor(0);
		} catch(Exception e) {
			System.err.println("Failed to load controller");
			System.exit(0);
		}

		controller.setVibration(0, 0);

	}

	public void tick() {
		
		// Poll returns false if device isn't connected
		if(!controller.poll()) {
			return;
		}
		
		XInputComponents components = controller.getComponents();
		buttons = components.getButtons();
		axes = components.getAxes();

		if(vibTicks > 0) {
			vibTicks--;
		} else if(vibTicks <= 0) {
			controller.setVibration(0, 0);
		}
		
	}

	public XInputDevice getController() {
		return controller;
	}

	public XInputButtons getButtons() {
		return buttons;
	}
	
	public XInputAxes getAxes() {
		return axes;
	}

	public void setVibration(VibrationData data) {
		controller.setVibration(data.getLeftStrength(), data.getRightStrength());
		setVibrationTicks(data.getTicks());
	}
	
	public void setVibrationTicks(int ct) {
		this.vibTicks = ct;
	}

}
