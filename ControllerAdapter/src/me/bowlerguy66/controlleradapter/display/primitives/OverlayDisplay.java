package me.bowlerguy66.controlleradapter.display.primitives;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * @author mulle
 *	A base class for OverlayDisplays, displays that are always on top and can have opacity
 */
public class OverlayDisplay extends JWindow {

	private static final long serialVersionUID = 1L;
		
	/**
	 * Constructor for OverlayDisplay, takes a default color that will go to the background
	 * @param defaultColor Color to set the background to
	 */
	public OverlayDisplay(Color defaultColor) {
		super();
		
		setBackground(defaultColor);
		setFocusable(false);
		setAlwaysOnTop(true);
		
		setContentPane(new ContentPane());
		getContentPane().setBackground(Color.black);
		setLayout(new BorderLayout());
								
	}
	
}

@SuppressWarnings("serial")
/**
 * @author mulle
 *	Workaround to draw non-opaque backgrounds
 */
class ContentPane extends JPanel {

    public ContentPane() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Allow super to paint
        super.paintComponent(g);
        // Apply our own painting effect
        Graphics2D g2d = (Graphics2D) g.create();
        // 50% transparent Alpha
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f));
        g2d.setColor(getBackground());
        g2d.fill(getBounds());
        g2d.dispose();
    }
}