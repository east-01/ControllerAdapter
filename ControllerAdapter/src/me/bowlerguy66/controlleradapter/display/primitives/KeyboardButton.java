package me.bowlerguy66.controlleradapter.display.primitives;

import java.awt.Color;

import javax.swing.JButton;

import me.bowlerguy66.controlleradapter.utils.KeyCodeParser;

@SuppressWarnings("serial")
public class KeyboardButton extends JButton {

	private int keyCode;
	
	public KeyboardButton(int keyCode, int x, int y) {
		super();
		this.keyCode = keyCode;
		String keyText = KeyCodeParser.getStringFromKeyCode(keyCode);
		if(keyText == null) keyText = (char) keyCode + "";
		String altText = "a";
		setText("<html>" + keyText + "<br>" + altText + "</html>");
		setForeground(Color.WHITE);
	}
	
	public int getKeyCode() {
		return keyCode;
	}
	
}
