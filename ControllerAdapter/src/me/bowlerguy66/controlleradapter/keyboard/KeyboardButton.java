package me.bowlerguy66.controlleradapter.keyboard;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import me.bowlerguy66.controlleradapter.Values;
import me.bowlerguy66.controlleradapter.utils.KeyCodeParser;
import me.bowlerguy66.controlleradapter.utils.Utils;
@SuppressWarnings("serial")
public class KeyboardButton extends JButton {

	private int keyCode;
	private int altKeyCode;
	
	boolean isHeld;
	
	public KeyboardButton(int keyCode, int altKeyCode, int pixelWidth, int pixelHeight) {
		super();
		this.keyCode = keyCode;
		this.altKeyCode = altKeyCode;

		// Weird hack to make the button click color the accent color
		// Needs to be reworked because this applies to ALL BUTTONS
		UIManager.put("Button.select", Values.COLOR_ACCENT);

		addMouseListener(new CustomMouseListener());
		addChangeListener(new ButtonModelListener());
		setOpaque(false);
		setBackground(Utils.cloneColor(Values.COLOR_MAIN, KeyboardOverlay.OPACITY));
		setForeground(Values.COLOR_FOREGROUND);
		setBorder(KeyboardOverlayLayout.getDefaultBorder());
		setText(getKeyText());
		setFont(new Font(Values.FONT, Font.BOLD, (int)((pixelHeight / 2f) * 0.6f)));
		Dimension size = new Dimension(pixelWidth, pixelHeight);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
	}
		
	public int getKeyCode() {
		return keyCode;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(isHeld) {
			g.setColor(Utils.cloneColor(Values.COLOR_ACCENT, KeyboardOverlay.OPACITY));
			g.fillRect(0, 0, getWidth(), getHeight());
		} else {
			setBackground(Utils.cloneColor(Values.COLOR_MAIN, KeyboardOverlay.OPACITY));
			setOpaque(false);
		}
		super.paintBorder(g);
	}
	
	public String getKeyText() {
		String keyText = KeyCodeParser.getStringFromKeyCode(keyCode);
		if(keyText == null) keyText = (char) keyCode + "";
		String altText = KeyCodeParser.getStringFromKeyCode(altKeyCode);
		if(altText == null) altText = (char) altKeyCode + "";
		String text;
		if(keyCode == altKeyCode) {
			text = keyText;
		} else {
			if(altText == "<") altText = "&lt;";
			text = "<html>" + keyText + "<br> " + altText + "</html>";
		}		
		return text;
	}
	
	public boolean isHeld() {
		return isHeld;
	}

	public void setHeld(boolean held) {
		this.isHeld = held;
		pressKey(held);
	}
	
	public void pressKey(boolean pressed) {
		try {
			Robot rob = new Robot();
			if(pressed) {
				rob.keyPress(getKeyCode());
			} else {
				rob.keyRelease(getKeyCode());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}		
	}
	
}

class ButtonModelListener implements ChangeListener {
	boolean lastState = false;
	@Override
	public void stateChanged(ChangeEvent e) {
		if(!(e.getSource() instanceof KeyboardButton)) return;
		KeyboardButton kb = (KeyboardButton) e.getSource();
		// Attempt to stop "phantom releases"
		if(lastState == kb.getModel().isPressed()) return;
		lastState = kb.getModel().isPressed();
		kb.pressKey(kb.getModel().isPressed());
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