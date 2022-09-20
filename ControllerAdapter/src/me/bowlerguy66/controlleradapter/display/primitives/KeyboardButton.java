package me.bowlerguy66.controlleradapter.display.primitives;

import javax.swing.JButton;

import me.bowlerguy66.controlleradapter.utils.KeyCodeParser;
@SuppressWarnings("serial")
public class KeyboardButton extends JButton {

	private int keyCode;
	
	public KeyboardButton(int keyCode, int altKeyCode, int x, int y) {
		super();
		this.keyCode = keyCode;
		String keyText = KeyCodeParser.getStringFromKeyCode(keyCode);
		if(keyText == null) keyText = (char) keyCode + "";
		String altText = KeyCodeParser.getStringFromKeyCode(altKeyCode);
		if(altText == null) altText = (char) altKeyCode + "";
		if(keyCode == altKeyCode) {
			setText(keyText);
		} else {
			if(altText == "<") altText = "&lt;";
			setText("<html>" + keyText + "<br> " + altText + "</html>");			
		}
	}
	
	public int getKeyCode() {
		return keyCode;
	}
	
}
