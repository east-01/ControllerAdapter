package me.bowlerguy66.controlleradapter.display.primitives;

import javax.swing.JFrame;

public abstract class Display extends JFrame {
		
	protected static final long serialVersionUID = 1566299383914615455L;
						
	public Display(String title, int width, int height) {

		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		addComponents();
	
	}
				
	public abstract void addComponents();
				
}
