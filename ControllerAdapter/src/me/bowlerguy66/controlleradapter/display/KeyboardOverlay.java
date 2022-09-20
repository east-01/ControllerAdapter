package me.bowlerguy66.controlleradapter.display;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import me.bowlerguy66.controlleradapter.Values;
import me.bowlerguy66.controlleradapter.display.primitives.KeyboardButton;
import me.bowlerguy66.controlleradapter.display.primitives.OverlayDisplay;
import me.bowlerguy66.controlleradapter.utils.CustomKeyCode;
import me.bowlerguy66.controlleradapter.utils.Utils;

@SuppressWarnings("serial")
public class KeyboardOverlay extends OverlayDisplay {
	
	private int[][] defKeyboardLayout = {
		new int[] {KeyEvent.VK_ESCAPE, KeyEvent.VK_F1, KeyEvent.VK_F2, KeyEvent.VK_F3, KeyEvent.VK_F4, KeyEvent.VK_F5, KeyEvent.VK_F6, KeyEvent.VK_F7, KeyEvent.VK_F8, KeyEvent.VK_F9, KeyEvent.VK_F10, KeyEvent.VK_F11, KeyEvent.VK_F12, KeyEvent.VK_INSERT, KeyEvent.VK_DELETE},
		new int[] {KeyEvent.VK_DEAD_GRAVE, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9, KeyEvent.VK_0, KeyEvent.VK_MINUS, KeyEvent.VK_EQUALS, KeyEvent.VK_BACK_SPACE, KeyEvent.VK_BACK_SPACE},
		new int[] {KeyEvent.VK_TAB, KeyEvent.VK_TAB, KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_T, KeyEvent.VK_Y, KeyEvent.VK_U, KeyEvent.VK_I, KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_OPEN_BRACKET, KeyEvent.VK_CLOSE_BRACKET, KeyEvent.VK_BACK_SLASH},
		new int[] {KeyEvent.VK_CAPS_LOCK, KeyEvent.VK_CAPS_LOCK, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_SEMICOLON, KeyEvent.VK_QUOTE, KeyEvent.VK_ENTER, KeyEvent.VK_ENTER},		
		new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_SHIFT, KeyEvent.VK_SHIFT, KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_C, KeyEvent.VK_V, KeyEvent.VK_B, KeyEvent.VK_N, KeyEvent.VK_M, KeyEvent.VK_COMMA, KeyEvent.VK_PERIOD, KeyEvent.VK_SLASH, KeyEvent.VK_UP, KeyEvent.VK_SHIFT},		
		new int[] {KeyEvent.VK_CONTROL, KeyEvent.VK_CONTROL, KeyEvent.VK_WINDOWS, KeyEvent.VK_ALT, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT}				
	};

	private int[][] altKeyboardLayout = {
			new int[] {KeyEvent.VK_ESCAPE, KeyEvent.VK_F1, KeyEvent.VK_F2, KeyEvent.VK_F3, KeyEvent.VK_F4, KeyEvent.VK_F5, KeyEvent.VK_F6, KeyEvent.VK_F7, KeyEvent.VK_F8, KeyEvent.VK_F9, KeyEvent.VK_F10, KeyEvent.VK_F11, KeyEvent.VK_F12, KeyEvent.VK_INSERT, KeyEvent.VK_DELETE},
			new int[] {KeyEvent.VK_DEAD_TILDE, KeyEvent.VK_EXCLAMATION_MARK, KeyEvent.VK_AT, KeyEvent.VK_NUMBER_SIGN, KeyEvent.VK_DOLLAR, CustomKeyCode.PERCENT, CustomKeyCode.POWER, KeyEvent.VK_AMPERSAND, KeyEvent.VK_MULTIPLY, KeyEvent.VK_LEFT_PARENTHESIS, KeyEvent.VK_RIGHT_PARENTHESIS, KeyEvent.VK_UNDERSCORE, KeyEvent.VK_PLUS, KeyEvent.VK_BACK_SPACE, KeyEvent.VK_BACK_SPACE},
			new int[] {KeyEvent.VK_TAB, KeyEvent.VK_TAB, KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_T, KeyEvent.VK_Y, KeyEvent.VK_U, KeyEvent.VK_I, KeyEvent.VK_0, KeyEvent.VK_P, KeyEvent.VK_BRACELEFT, KeyEvent.VK_BRACERIGHT, CustomKeyCode.BAR},
			new int[] {KeyEvent.VK_CAPS_LOCK, KeyEvent.VK_CAPS_LOCK, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_COLON, KeyEvent.VK_QUOTEDBL, KeyEvent.VK_ENTER, KeyEvent.VK_ENTER},		
			new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_SHIFT, KeyEvent.VK_SHIFT, KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_C, KeyEvent.VK_V, KeyEvent.VK_B, KeyEvent.VK_N, KeyEvent.VK_M, KeyEvent.VK_LESS, KeyEvent.VK_GREATER, CustomKeyCode.QUESTION_MARK, KeyEvent.VK_UP, KeyEvent.VK_SHIFT},		
			new int[] {KeyEvent.VK_CONTROL, KeyEvent.VK_CONTROL, KeyEvent.VK_WINDOWS, KeyEvent.VK_ALT, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT}				
	};
	
	public static int OPACITY = 165;

	private KeyboardButton[][] buttons;
	private int width, height;	
	private int keyWidth, keyHeight;
	private int borderSize;

	public static final int hKeyCnt = 15;
	public static final int vKeyCnt = 6;
	
	public KeyboardOverlay() {
		super(Utils.cloneColor(Values.COLOR_MAIN, OPACITY));
				
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.width = (int) (screenWidth * 0.60);
		this.height = (int) (screenHeight * 0.375);
		this.borderSize = (int) (height * 0.05);
		
		this.keyWidth = (int) ((width - (float)borderSize / 2f) / (float)hKeyCnt);
		this.keyHeight = (int) ((height - (float)borderSize / 2f) / (float)vKeyCnt);
		
		this.buttons = new KeyboardButton[hKeyCnt][vKeyCnt];

		addComponents();
		setLocation((int) (((float)screenWidth / 2) - ((float)width / 2)), (int) (((float)screenHeight / 2) - ((float)height / 2) + ((float)screenHeight * 0.175)));
		
	}

	public void addComponents() {
				
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(width, height));
		panel.setPreferredSize(new Dimension(width, height));
		panel.setMaximumSize(new Dimension(width, height));
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize));
		panel.setLayout(new GridBagLayout());
		
		for(int y = 0; y < vKeyCnt; y++) {
			for(int x = 0; x < hKeyCnt; x++) {
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = x;
				gbc.gridy = y;
				int keyCode = defKeyboardLayout[y][x];
				int keyWidth = 1;
				// Check to see if the next keys are the same key
				while(x + keyWidth < hKeyCnt && defKeyboardLayout[y][x + keyWidth] == keyCode) {
					keyWidth++;
				}
				gbc.gridwidth = keyWidth;
				gbc.gridheight = 1;
				KeyboardButton button = createKey(keyCode, altKeyboardLayout[y][x], x, y, keyWidth, 1);
				panel.add(button, gbc);
				buttons[x][y] = button;
				// Fill out the array with the multi wide button
				if(keyWidth > 1) {
					for(int i = 0; i < keyWidth; i++) {
						buttons[x + i][y] = button;
					}
				}
				x += keyWidth - 1;
			}
		}
		
		add(panel);
		pack();
		setVisible(true);
		
	}

	public KeyboardButton createKey(int keyCode, int altKeyCode, int x, int y, int width, int height) {
		
		KeyboardButton button = new KeyboardButton(keyCode, altKeyCode, x, y);
		Dimension size = new Dimension(keyWidth * width, keyHeight * height);
		button.setMinimumSize(size);
		button.setPreferredSize(size);
		button.setMaximumSize(size);
		button.addChangeListener(new ButtonModelListener());
		button.setOpaque(false);
		button.setBackground(Utils.cloneColor(Values.COLOR_MAIN, OPACITY));
		button.setForeground(Values.COLOR_FOREGROUND);
		return button;
		
	}

	public void setOpen(boolean open) {
		setVisible(open);
	}
	
	public boolean isOpen() {
		return isVisible();
	}
	
	public void toggleOpen() {
		setVisible(!isVisible());
	}
	
	public KeyboardButton getButtonAt(int x, int y) {
		try {
			return buttons[x][y];
		} catch(Exception e) {
			return null;
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
		try {
			Robot rob = new Robot();
			if(kb.getModel().isPressed()) {
				rob.keyPress(kb.getKeyCode());
			} else {
				rob.keyRelease(kb.getKeyCode());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}