package me.bowlerguy66.controlleradapter.display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import me.bowlerguy66.controlleradapter.ControllerAdapter;
import me.bowlerguy66.controlleradapter.Values;
import me.bowlerguy66.controlleradapter.display.primitives.OverlayDisplay;
import me.bowlerguy66.controlleradapter.utils.Utils;

@SuppressWarnings("serial")
public class InfoOverlay extends OverlayDisplay{

	public static int MIN_BG_OPACITY = 0;
	public static int MAX_BG_OPACITY = 120;
	public static int MIN_FG_OPACITY = 0;
	public static int MAX_FG_OPACITY = 255;
	
	public static int FADE_TIME = 80;
	int showTicks;
	
	private JLabel textLabel;

	public InfoOverlay(ControllerAdapter main) {
		super(Utils.cloneColor(Values.COLOR_MAIN, MAX_BG_OPACITY));
		setLocation(20, 20);
		showTicks = 180;
		addComponents();
	}
	
	public void addComponents() {
		
		int width = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.1);
		int height = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.045);
		
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(width, height));
		panel.setPreferredSize(new Dimension(width, height));
		panel.setMaximumSize(new Dimension(width, height));
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder((int)(height * 0.05f), (int)(width * 0.05f), (int)(height * 0.05f), (int)(width * 0.05f)));
		textLabel = new JLabel("");
		textLabel.setFont(new Font("Baskerville", Font.BOLD, 30));
		textLabel.setForeground(Utils.cloneColor(Values.COLOR_FOREGROUND, MAX_FG_OPACITY));
		panel.add(textLabel, BorderLayout.CENTER);
		add(panel);

		setVisible(true);
		pack();
		
	}

	public void tick() {
		if(showTicks >= 0 ) {
			if(showTicks < FADE_TIME) {
				float ratio = ((float)showTicks / (float)FADE_TIME);
				
				setBackground(Utils.cloneColor(getBackground(), (int)(MIN_BG_OPACITY + ratio * (MAX_BG_OPACITY - MIN_BG_OPACITY))));
				textLabel.setForeground(Utils.cloneColor(textLabel.getForeground(), (int)(MIN_FG_OPACITY + ratio * (MAX_FG_OPACITY - MIN_FG_OPACITY))));
				showTicks--;
			}
			showTicks--;
		}
		
	}
	
	public void updateText(String newText, int time) {
		textLabel.setText(newText);
		showTicks = time;
		setMaxOpacity();
	}
	
	public void setMaxOpacity() {
		setBackground(Utils.cloneColor(getBackground(), MAX_BG_OPACITY));
		textLabel.setForeground(Utils.cloneColor(textLabel.getForeground(), MAX_FG_OPACITY));		
	}
	
}
