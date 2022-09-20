package me.bowlerguy66.controlleradapter.display.primitives;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import me.bowlerguy66.controlleradapter.display.KeyboardOverlayLayout;
import me.bowlerguy66.controlleradapter.utils.KeyCodeParser;
@SuppressWarnings("serial")
public class KeyboardButton extends JButton {

	private int keyCode;
	
	public KeyboardButton(int keyCode, int altKeyCode, int x, int y) {
		super();
		addMouseListener(new CustomMouseListener());
		setBorder(KeyboardOverlayLayout.getDefaultBorder());
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

class CustomMouseListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(!(e.getSource() instanceof KeyboardButton)) return;
		KeyboardButton button = ((KeyboardButton) e.getSource());
		button.setBorder(KeyboardOverlayLayout.getHighlightedBorder());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(!(e.getSource() instanceof KeyboardButton)) return;
		((KeyboardButton) e.getSource()).setBorder(KeyboardOverlayLayout.getDefaultBorder());		
	}
	
}
