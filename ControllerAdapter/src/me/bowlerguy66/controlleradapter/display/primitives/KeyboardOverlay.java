package me.bowlerguy66.controlleradapter.display.primitives;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class KeyboardOverlay extends OverlayDisplay {

	private int width;
	private int height;
	
	public KeyboardOverlay(Color defaultColor) {
		super(defaultColor);
		
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.width = (int) (screenWidth * 0.60);
		this.height = (int) (screenHeight * 0.375);
		
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
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder((int)(height * 0.05f), (int)(width * 0.05f), (int)(height * 0.05f), (int)(width * 0.05f)));
		JLabel textLabel = new JLabel("hi");
		textLabel.setFont(new Font("Baskerville", Font.BOLD, 30));
		textLabel.setForeground(Color.WHITE);
		panel.add(textLabel, BorderLayout.CENTER);
		add(panel);

		setVisible(true);
		pack();
		
	}

}
