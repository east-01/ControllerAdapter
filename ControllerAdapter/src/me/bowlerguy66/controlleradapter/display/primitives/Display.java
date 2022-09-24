package me.bowlerguy66.controlleradapter.display.primitives;

import javax.swing.JFrame;

/**
 * @author mulle
 *	A base class for main JFrame displays
 */
public abstract class Display extends JFrame {
		
	protected static final long serialVersionUID = 1566299383914615455L;
					
	/**
	 * Contructor for Display
	 * @param title The title that will be attached to the JFrame
	 * @param width The width of the JFrame
	 * @param height The height of the JFrame
	 */
	public Display(String title, int width, int height) {

		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		addComponents();
	
	}
				
	/**
	 * Add components to the JFrame, called at the end of the constructor
	 */
	public abstract void addComponents();
				
}
