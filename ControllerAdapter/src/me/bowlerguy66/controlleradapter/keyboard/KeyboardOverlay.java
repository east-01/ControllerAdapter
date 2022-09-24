package me.bowlerguy66.controlleradapter.keyboard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import me.bowlerguy66.controlleradapter.ControllerAdapter;
import me.bowlerguy66.controlleradapter.Values;
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
			new int[] {KeyEvent.VK_TAB, KeyEvent.VK_TAB, KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_T, KeyEvent.VK_Y, KeyEvent.VK_U, KeyEvent.VK_I, KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_BRACELEFT, KeyEvent.VK_BRACERIGHT, CustomKeyCode.BAR},
			new int[] {KeyEvent.VK_CAPS_LOCK, KeyEvent.VK_CAPS_LOCK, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_COLON, KeyEvent.VK_QUOTEDBL, KeyEvent.VK_ENTER, KeyEvent.VK_ENTER},		
			new int[] {KeyEvent.VK_SHIFT, KeyEvent.VK_SHIFT, KeyEvent.VK_SHIFT, KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_C, KeyEvent.VK_V, KeyEvent.VK_B, KeyEvent.VK_N, KeyEvent.VK_M, KeyEvent.VK_LESS, KeyEvent.VK_GREATER, CustomKeyCode.QUESTION_MARK, KeyEvent.VK_UP, KeyEvent.VK_SHIFT},		
			new int[] {KeyEvent.VK_CONTROL, KeyEvent.VK_CONTROL, KeyEvent.VK_WINDOWS, KeyEvent.VK_ALT, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_SPACE, KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT}				
	};
	
	public static int OPACITY = 135;

	private KeyboardButton[][] buttons;
	private int width, height;	
	private int keyWidth, keyHeight;
	private int borderWidth, borderHeight;

	public static final int hKeyCnt = 15;
	public static final int vKeyCnt = 6;
	
	// Allows the toggle to only happen once per tick
	private boolean toggleFlag;
	
	public KeyboardOverlay() {
		super(Utils.cloneColor(Values.COLOR_MAIN, OPACITY));
				
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.width = (int) (screenWidth * 0.60);
		this.height = (int) (screenHeight * 0.375);
		this.borderWidth = (int) (screenWidth * 0.015);
		this.borderHeight = (int) borderWidth;
		
		this.keyWidth = (int) ((width - (float)borderWidth / 2f) / (float)hKeyCnt);
		this.keyHeight = (int) ((height - (float)borderHeight / 2f) / (float)vKeyCnt);
		this.buttons = new KeyboardButton[hKeyCnt][vKeyCnt];

		addComponents();
		setLocation((int) (((float)screenWidth / 2) - ((float)width / 2)), (int) (((float)screenHeight / 2) - ((float)height / 2) + ((float)screenHeight * 0.2)));
		setLayout(new BorderLayout());
		
	}

	public void addComponents() {
				
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(width, height));
		panel.setPreferredSize(new Dimension(width, height));
		panel.setMaximumSize(new Dimension(width, height));
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(borderHeight, borderWidth, borderHeight, borderWidth));
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
				KeyboardButton button = new KeyboardButton(keyCode, altKeyboardLayout[y][x], keyWidth * this.keyWidth, this.keyHeight);
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
		
		add(panel, BorderLayout.CENTER);
		
		JPanel textPanel = new JPanel();
		textPanel.setOpaque(false);
		
		BufferedImage buttonsImg = Utils.loadImage(ControllerAdapter.class.getResource(ControllerAdapter.RESOURCES_FOLDER_PATH + "buttons.png"));
		int preferredSize = (int)(width * PREFERRED_SIZE_RATIO);
		
		textPanel.setLayout(new FlowLayout());
		textPanel.add(createButtonIcon(buttonsImg, 0, 0, preferredSize, preferredSize));
		textPanel.add(createTextLabel("Press "));
		textPanel.add(createButtonIcon(buttonsImg, 1, 0, preferredSize, preferredSize));
		textPanel.add(createTextLabel("Backspace "));
		textPanel.add(createButtonIcon(buttonsImg, 2, 0, preferredSize, preferredSize));
		textPanel.add(createTextLabel("Space "));
		textPanel.add(createButtonIcon(buttonsImg, 3, 0, preferredSize, preferredSize));
		textPanel.add(createTextLabel("Enter "));
		textPanel.add(createButtonIcon(buttonsImg, 0, 1, (int) (preferredSize * ((float)SHOULDER_BUTTON_WIDTH / (float)SHOULDER_BUTTON_HEIGHT)), preferredSize));
		textPanel.add(createTextLabel("(Hold) Locks key "));
		textPanel.add(createButtonIcon(buttonsImg, 1, 1, (int) (preferredSize * ((float)SHOULDER_BUTTON_WIDTH / (float)SHOULDER_BUTTON_HEIGHT)), preferredSize));
		textPanel.add(createTextLabel("Unlocks all "));
		textPanel.add(createButtonIcon(buttonsImg, 4, 0, preferredSize, preferredSize));
		textPanel.add(createTextLabel("Open/Close "));
		
		add(textPanel, BorderLayout.SOUTH);
		
		pack();
		
	}

	public void tick() {
		
		if(toggleFlag) {
			setVisible(!isVisible());
			toggleFlag = false;
		}
		
	}

	public void setOpen(boolean open) {
		setVisible(open);
	}
	
	public boolean isOpen() {
		return isVisible();
	}
	
	public void toggleOpen() {
		toggleFlag = true;
	}
	
	public KeyboardButton getButtonAt(int x, int y) {
		try {
			return buttons[x][y];
		} catch(Exception e) {
			return null;
		}
	}
	
	public void clearHeldKeys() {
		for(KeyboardButton[] array : buttons) {
			for(KeyboardButton btn : array) {
				if(btn.isHeld()) {
					btn.setHeld(false);
					btn.repaint();
				}
			}
		}
	}
	
	private static final int CIRCLE_BUTTON_SIZE = 74;
	private static final int SHOULDER_BUTTON_WIDTH = 85;
	private static final int SHOULDER_BUTTON_HEIGHT = 54;
	private static final float PREFERRED_SIZE_RATIO = 0.02f;
	
	private JLabel createButtonIcon(BufferedImage baseImg, int x, int y, int preferredWidth, int preferredHeight) {
		JLabel label = new JLabel();
		int width = y == 0 ? CIRCLE_BUTTON_SIZE : SHOULDER_BUTTON_WIDTH;
		int height = y == 0 ? CIRCLE_BUTTON_SIZE : SHOULDER_BUTTON_HEIGHT;
		label.setIcon(new ImageIcon(baseImg.getSubimage(x * width, y * CIRCLE_BUTTON_SIZE, width, height).getScaledInstance(preferredWidth, preferredHeight, Image.SCALE_SMOOTH)));
		return label;
	}

	private JLabel createTextLabel(String text) {
		JLabel textLabel = new JLabel(text);
		textLabel.setFont(new Font("Baskerville", Font.BOLD, (int)(width * (PREFERRED_SIZE_RATIO - 0.005))));
		textLabel.setForeground(Utils.cloneColor(Values.COLOR_FOREGROUND, OPACITY));
		textLabel.setOpaque(false);
		return textLabel;
	}
	
}