package me.bowlerguy66.controlleradapter;

import java.io.File;

import com.github.strikerx3.jxinput.enums.XInputButton;

import me.bowlerguy66.controlleradapter.display.InfoOverlay;
import me.bowlerguy66.controlleradapter.display.MainDisplay;
import me.bowlerguy66.controlleradapter.keyboard.KeyboardOverlay;
import me.bowlerguy66.controlleradapter.keyboard.KeyboardOverlayLayout;
import me.bowlerguy66.controlleradapter.layouts.LayoutManager;

public class ControllerAdapter implements Runnable {

	/* 
	ControllerAdapter by Ethan Mullen, 9/13/2022
	Loading: 
	  - All .txt files in (user home dir)/ControllerToMouse/layouts are PARSED by LayoutLoader and 
	  		added to LayoutManager
	  - For now, the first file that is loaded is set as the current layout
	Runtime:
	  - Instructions are interpreted from layout file and loaded as a Layout
	  - Button presses are handled by the listener in the Layout class
	    - Once read by the layout class, they go to the ActionPerformer class and carry out
	      the sequence of actions that is specified in the Layout file
	  - Analog inputs (sticks/triggers) are handled by a AnalogStick/AnalogTrigger class
	    - Each analog input has its own class that the user can pick in the layout file
	    - Every tick, the analog values are interpreted by each analog class
	*/

	public static void main(String[] args) { new ControllerAdapter(); }
	
	public static String BASE_PATH = System.getProperty("user.home") + "/ControllerToMouse";
	public static String RESOURCES_FOLDER_PATH = "/me/bowlerguy66/controlleradapter/resources/";
	
	public static XInputButton keyboardButton = XInputButton.BACK;
	// The list for the toggle active buttons is in the bottom of the tick function in the layouts.Layout
	public static int TOGGLE_ACTIVE_TICKS = 60 * 3;
	public static boolean IS_ACTIVE = true;
	
	public boolean running;
	public Thread thread;
	private MainDisplay display;	
	final private InfoOverlay infoOverlay;				
	final private KeyboardOverlay keyboardOverlay;
	private KeyboardOverlayLayout keyboardOverlayLayout;
	
	private ControllerManager controllerManager;
	private LayoutManager layoutManager;
			
	public ControllerAdapter() {
				
		// Load layout and config folder if it doesn't exist
		File file = new File(BASE_PATH + "/layouts");
		if(!file.exists()) {
			file.mkdirs();
		}
		
		this.infoOverlay = new InfoOverlay(this);
		this.display = new MainDisplay(this);
		this.keyboardOverlay = new KeyboardOverlay();
		this.keyboardOverlayLayout = new KeyboardOverlayLayout(this);
		
		this.controllerManager = new ControllerManager(this);
		this.layoutManager = new LayoutManager(this);
									
		controllerManager.getController().addListener(keyboardOverlayLayout.getListener());
		
		display.updateLayoutsBox();
		if(layoutManager.getCurrentLayout() != null) infoOverlay.updateText(layoutManager.getCurrentLayout().getTitle(), 180);
		keyboardOverlay.setOpen(false);
		
		running = true;
		thread = new Thread(this);
		thread.start();
		
	}
	
	public void tick() {
		controllerManager.tick();
		keyboardOverlay.tick();
		if(!keyboardOverlay.isOpen()) {
			if(layoutManager.hasCurrentLayout()) layoutManager.getCurrentLayout().tick();
		} else {
			keyboardOverlayLayout.tick();
		}
		infoOverlay.tick();
	}
			
	@Override
	public void run() {
						
		int fps = 60;
		double timepertick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lasttime = System.nanoTime();
		long timer = 0;
		@SuppressWarnings("unused")
		int ticks = 0;
						
		while(running) {
			now = System.nanoTime();
			delta += (now - lasttime) / timepertick;
			timer += now - lasttime;
			lasttime = now;
			
			if(delta >= 1) {				
				tick();
				
				ticks++;
				delta--;
			}
			
			if(timer >= 1000000000) {
				ticks = 0;
				timer = 0;
			}
			
		}
				
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
	}
		
	public MainDisplay getDisplay() {
		return display;
	}
	
	public InfoOverlay getInfoOverlay() {
		return infoOverlay;
	}
	
	public KeyboardOverlay getKeyboardOverlay() {
		return keyboardOverlay;
	}
	
	public KeyboardOverlayLayout getKeyboardOverlayLayout() {
		return keyboardOverlayLayout;
	}
	
	public ControllerManager getControllerManager() {
		return controllerManager;
	}
	
	public LayoutManager getLayoutManager() {
		return layoutManager;
	}
	
}
