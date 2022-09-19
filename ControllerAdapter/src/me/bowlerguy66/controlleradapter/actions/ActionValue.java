package me.bowlerguy66.controlleradapter.actions;

public class ActionValue {

	private InputAction action;
	private Object value;
	
	public ActionValue(InputAction action, Object value) {
		this.action = action;
		this.value = value;
	}

	public InputAction getAction() {
		return action;
	}

	public void setAction(InputAction action) {
		this.action = action;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
}
