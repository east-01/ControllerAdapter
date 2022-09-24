package me.bowlerguy66.controlleradapter.actions;

/**
 * @author mulle
 *	A way to pair InputActions with a sepcific value.
 *	Value types aren't checked, and the LayoutLoader is
 *	expected to provide the correct values.
 */
public class ActionValue {

	private InputAction action;
	private Object value;
	
	/**
	 * ActionValue main constructor
	 * @param action The InputAction instruction
	 * @param value The associated value for the instruction, not type checked and it's assumed that the correct type is being passed in
	 */
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
