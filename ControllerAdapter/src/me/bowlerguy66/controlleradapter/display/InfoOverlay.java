package me.bowlerguy66.controlleradapter.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
/**
 * @author mulle
 *	The upper left display showing crucial info about the current status of the program.
 *	Shows the currentLayout for now
 */
public class InfoOverlay extends OverlayDisplay{

	public static int MIN_BG_OPACITY = 0;
	public static int MAX_BG_OPACITY = 120;
	public static int MIN_FG_OPACITY = 0;
	public static int MAX_FG_OPACITY = 255;
	
	public static int FADE_TIME = 80;
	
	private int showTicks;
	
	// Used to refresh the background/foreground on a specific rate since updating it every tick seems to crash the program
	private final int refreshRate = 5;
	private int refreshTimer;
	private Color nextBackground;
	private Color nextForeground;
	
	private JLabel textLabel;

	/**
	 * Constructor for InfoOverlay, takes a main instance parameter
	 * @param main An instance of the main class for use in other functions
	 */
	public InfoOverlay(ControllerAdapter main) {
		super(Utils.cloneColor(Values.COLOR_MAIN, MAX_BG_OPACITY));		
		setLocation(20, 20);
		showTicks = 180;
		addComponents();
		setLayout(new FlowLayout());
	}

	/**
	 * Add components to the panel
	 */
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
		textLabel.setOpaque(false);
		panel.add(textLabel, BorderLayout.CENTER);
		add(panel);

		setVisible(true);
		pack();
		
	}
		
	/**
	 * Tick the display, is used for dynamically fading the opacity right now
	 */
	public void tick() {
		if(showTicks >= 0) {
			// We can only update the background every so often. If you try to update it every tick the program freezes
			if(showTicks < FADE_TIME && refreshTimer == 0) {
				float ratio = ((float)showTicks / (float)FADE_TIME);
				this.nextBackground = Utils.cloneColor(Values.COLOR_MAIN, (int)(MIN_BG_OPACITY + ratio * (MAX_BG_OPACITY - MIN_BG_OPACITY)));
				this.nextForeground = Utils.cloneColor(textLabel.getForeground(), (int)(MIN_FG_OPACITY + ratio * (MAX_FG_OPACITY - MIN_FG_OPACITY)));
			}
			showTicks--;
			// Last tick, setMinOpacity here
			if(showTicks == 0) {
				setMinOpacity();
				showTicks = -1;
			}
		}
		// More code to refresh foreground/background on a specific interval
		if(refreshTimer >= 0) {
			refreshTimer--;
		} else {
			setColors(nextBackground, nextForeground);
			this.nextBackground = null;
			this.nextForeground = null;
			refreshTimer = refreshRate;
		}
	}
	
	/**
	 * Update the text to a new string
	 * @param newText The new string to be shown
	 * @param time The amount of time (in ticks) until the display reaches minimum opacity
	 */
	public void updateText(String newText, int time) {
		setMaxOpacity();
		textLabel.setText(newText);
		showTicks = time;
	}
	
	/**
	 * Set the display to its maximum opacity
	 */
	public void setMaxOpacity() {
		this.nextBackground = Utils.cloneColor(getBackground(), MAX_BG_OPACITY);
		this.nextForeground = Utils.cloneColor(textLabel.getForeground(), MAX_FG_OPACITY);		
	}

	/**
	 * Set the display to its minimum opacity
	 */
	public void setMinOpacity() {
		this.nextBackground = Utils.cloneColor(getBackground(), MIN_BG_OPACITY);
		this.nextForeground = Utils.cloneColor(textLabel.getForeground(), MIN_FG_OPACITY);		
	}

	/**
	 * Helper method to change the colors of the background and the foreground, changes visibility if all opacities are 0
	 * @param newBackground The new background color
	 * @param newForeground The new foreground color
	 */
	private void setColors(Color newBackground, Color newForeground) {
		if(newBackground == null && newForeground == null) return;
		boolean visible = false;
		if(newBackground != null) {
			if(newBackground.getAlpha() > 0) visible = true;
			setBackground(newBackground);
		}
		if(newForeground != null) {
			if(newForeground.getAlpha() > 0) visible = true;
			for(Component c : getContentPane().getComponents()) {
				c.setForeground(newForeground);
			}
		}
		// Check for invisibility and the apply visibility accordingly
		setVisible(visible);
	}
	
}
