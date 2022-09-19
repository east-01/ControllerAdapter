package me.bowlerguy66.controlleradapter.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import me.bowlerguy66.controlleradapter.display.primitives.KeyboardButton;
import me.bowlerguy66.controlleradapter.display.primitives.OverlayDisplay;

@SuppressWarnings("serial")
public class KeyboardOverlay extends OverlayDisplay {
	
	private int[][] defKeyboardLayout = {
		new int[] {KeyEvent.VK_ESCAPE, KeyEvent.VK_F1, KeyEvent.VK_F2, KeyEvent.VK_F3, KeyEvent.VK_F4, KeyEvent.VK_F5, KeyEvent.VK_F6, KeyEvent.VK_F7, KeyEvent.VK_F8, KeyEvent.VK_F9, KeyEvent.VK_F10, KeyEvent.VK_F11, KeyEvent.VK_F12, KeyEvent.VK_INSERT, KeyEvent.VK_DELETE},
		new int[] {KeyEvent.VK_DEAD_TILDE, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9, KeyEvent.VK_0, KeyEvent.VK_MINUS, KeyEvent.VK_EQUALS, KeyEvent.VK_BACK_SPACE, KeyEvent.VK_BACK_SPACE},
		new int[] {KeyEvent.VK_TAB, KeyEvent.VK_TAB, KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_T, KeyEvent.VK_Y, KeyEvent.VK_U, KeyEvent.VK_I, KeyEvent.VK_0, KeyEvent.VK_P, KeyEvent.VK_OPEN_BRACKET, KeyEvent.VK_CLOSE_BRACKET, KeyEvent.VK_BACK_SLASH},
		new int[] {KeyEvent.VK_CAPS_LOCK, KeyEvent.VK_CAPS_LOCK, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_SEMICOLON, KeyEvent.VK_QUOTE, KeyEvent.VK_ENTER, KeyEvent.VK_ENTER},		
		new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_SHIFT, KeyEvent.VK_SHIFT, KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_C, KeyEvent.VK_V, KeyEvent.VK_B, KeyEvent.VK_N, KeyEvent.VK_M, KeyEvent.VK_COMMA, KeyEvent.VK_PERIOD, KeyEvent.VK_SLASH, KeyEvent.VK_UP, KeyEvent.VK_SHIFT},		
		new int[] {KeyEvent.VK_CONTROL, KeyEvent.VK_CONTROL, KeyEvent.VK_WINDOWS, KeyEvent.VK_ALT, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT}				
	};
	
	private int width, height;	
	private int keyWidth, keyHeight;
	private int borderSize;

	private final int hKeyCnt = 15;
	private final int vKeyCnt = 6;

	public KeyboardOverlay(Color defaultColor) {
		super(defaultColor);
		
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.width = (int) (screenWidth * 0.60);
		this.height = (int) (screenHeight * 0.375);
		this.borderSize = (int) (height * 0.05);
				
		this.keyWidth = (int) ((width - (float)borderSize / 2f) / (float)hKeyCnt);
		this.keyHeight = (int) ((height - (float)borderSize / 2f) / (float)vKeyCnt);
		
		setFocusable(true);
		addComponents();
		setLocation((int) (((float)screenWidth / 2) - ((float)width / 2)), (int) (((float)screenHeight / 2) - ((float)height / 2) + ((float)screenHeight * 0.175)));
		
	}

	public void addComponents() {
				
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(width, height));
		panel.setPreferredSize(new Dimension(width, height));
		panel.setMaximumSize(new Dimension(width, height));
		panel.setOpaque(false);
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize));
		
		for(int y = 0; y < vKeyCnt; y++) {
			for(int x = 0; x < hKeyCnt; x++) {
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = x;
				gbc.gridy = y;
				int keyCode = KeyEvent.VK_W;
				int keyWidth = 1;
				keyCode = defKeyboardLayout[y][x];
				// Check to see if the next keys are the same key
				while(keyWidth + x < hKeyCnt && defKeyboardLayout[y][x + keyWidth] == keyCode) {
					keyWidth++;
				}
				gbc.gridwidth = keyWidth;
				gbc.gridheight = 1;
				JButton button = createKey(keyCode, x, y, keyWidth, 1);
				panel.add(button, gbc);
				x += keyWidth - 1;
			}
		}
		
		add(panel);
		setVisible(true);
		pack();
		
	}

	public JButton createKey(int keyCode, int x, int y, int width, int height) {
		
		KeyboardButton button = new KeyboardButton(keyCode, x, y);
		Dimension size = new Dimension(keyWidth * width, keyHeight * height);
		button.addChangeListener(new ButtonModelListener());
		button.setMinimumSize(size);
		button.setPreferredSize(size);
		button.setMaximumSize(size);
		button.setOpaque(false);
		button.setBackground(new Color(100, 100, 200, 50));
		return button;
		
	}
			
}

class ButtonModelListener implements ChangeListener {

	@Override
	public void stateChanged(ChangeEvent e) {
		if(!(e.getSource() instanceof KeyboardButton)) return;
		KeyboardButton kb = (KeyboardButton) e.getSource();
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