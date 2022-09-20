package me.bowlerguy66.controlleradapter.layouts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.KeyStroke;

import com.github.strikerx3.jxinput.enums.XInputButton;

import me.bowlerguy66.controlleradapter.ControllerAdapter;
import me.bowlerguy66.controlleradapter.actions.ActionValue;
import me.bowlerguy66.controlleradapter.actions.InputAction;
import me.bowlerguy66.controlleradapter.analogsticks.AnalogParser;
import me.bowlerguy66.controlleradapter.analogsticks.AnalogStick;
import me.bowlerguy66.controlleradapter.analogsticks.AnalogTrigger;
import me.bowlerguy66.controlleradapter.utils.ParseUtils;
import me.bowlerguy66.controlleradapter.utils.VibrationData;

public class LayoutLoader {

	public static Layout loadLayout(ControllerAdapter main, File file) { 

		String errorPrefix = "Error parsing layout file \"" + file.getName() + "\" line %LN%: ";
				
		String title = "Default Title";
		Map<XInputButton, ActionValue[]> buttonPressActions = new HashMap<XInputButton, ActionValue[]>();
		Map<XInputButton, ActionValue[]> buttonReleaseActions = new HashMap<XInputButton, ActionValue[]>();
		AnalogStick leftStick = null;
		AnalogStick rightStick = null;
		AnalogTrigger leftTrigger = null;
		AnalogTrigger rightTrigger = null;

		int lineNumber = 0;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
					
			XInputButton currentButton = null;
			boolean pressMode = true;
			
			String line = reader.readLine();
			while(line != null) {
				lineNumber++;
				
				// Allows commenting
				if(line.startsWith("#")) {
					line = reader.readLine();
					continue;
				}
				
				if(!line.contains(":")) {
					System.err.println(errorPrefix.replaceAll("%LN%", lineNumber+"") + "Attempted to read an instruction line that is missing a \':\' all instruction lines need one.");
					reader.close();
					return null;
				}
				String lineValue = line.substring(0, line.indexOf(':')).trim();
				String lineData = line.substring(line.indexOf(':') + 1, line.length()).trim();

				// Set title
				if(lineValue.equalsIgnoreCase("title")) {
					title = lineData;
					line = reader.readLine();
					continue;
				}
								
				if(lineValue.equalsIgnoreCase("leftStick") || lineValue.equalsIgnoreCase("rightStick")) {
					AnalogStick parsed = AnalogParser.parseAsAnalogStick(lineData);
					if(parsed != null) {
						if(lineValue.equalsIgnoreCase("leftStick")) {
							leftStick = parsed;
						} else {
							rightStick = parsed;
						}
					} else {
						System.err.println(errorPrefix.replaceAll("%LN%", lineNumber+"") + "Failed to parse " + lineValue.toLowerCase() + ", read value as \"" + lineData + "\"");
						reader.close();
						return null;
					}
				}
				
				// Read instructions
				int clearedTabs = 0;
				while(line.startsWith("\t")) {
					line = line.replaceFirst("\t", "");
					clearedTabs++;
				}
								
				if(clearedTabs == 0) { // Finding the current button
					try {
						currentButton = XInputButton.valueOf(lineValue);
					} catch(Exception e) {
						currentButton = null;
					}
				} else if(clearedTabs == 1) { // Reading press/release mode
					if(currentButton == null) {
						System.err.println(errorPrefix.replaceAll("%LN%", lineNumber+"") + "Found indented line without a button set.");
						reader.close();
						return null;
					}
					
					if(lineValue.equalsIgnoreCase("press")) {
						pressMode = true;
					} else if(lineValue.equalsIgnoreCase("release")) {
						pressMode = false;
					} else {
						System.err.println(errorPrefix.replaceAll("%LN%", lineNumber+"") + "Failed to read press/release mode, found single indented line with unexpected value \"" + lineValue + "\"");
						reader.close();
						return null;
					}
					
				} else if(clearedTabs == 2) { // Reading instructions
					
					InputAction action;
					try {
						action = InputAction.valueOf(lineValue);
					} catch(Exception e) {
						System.err.println(errorPrefix.replaceAll("%LN%", lineNumber+"") + "Failed to parse InputAction, read \"" + lineValue + "\"");
						reader.close();
						return null;
					}
					
					ActionValue toAdd = null;
					
					if(action == InputAction.MOUSE_PRESS || action == InputAction.MOUSE_RELEASE) {
						int keyCode = 0;
						try {
							keyCode = ParseUtils.stringToMouseCode(lineData);
						} catch(Exception e) {
							System.err.println(errorPrefix.replaceAll("%LN%", lineNumber+"") + "Failed to parse mouse keycode string, read \"" + lineData + "\"");		
							reader.close();
							return null;
						}
						toAdd = new ActionValue(action, keyCode);
					} else if(action == InputAction.KEY_PRESS || action == InputAction.KEY_RELEASE) {
						int keyCode = 0;
						try {
							KeyStroke ks = KeyStroke.getKeyStroke(lineData);
							keyCode = ks.getKeyCode();
						} catch(Exception e) {
							System.err.println(errorPrefix.replaceAll("%LN%", lineNumber+"") + "Failed to parse keyboard keycode string, read \"" + lineData + "\"");		
							reader.close();
							return null;
						}
						toAdd = new ActionValue(action, keyCode);
					} else if(action == InputAction.VIBRATION) {
						String[] data = lineData.split(",");
						if(data.length != 3) {
							System.err.println(errorPrefix.replaceAll("%LN%", lineNumber+"") + "Failed to parse vibration data, more than 3 values provided");		
							reader.close();
							return null;							
						}
						int[] parsedData = new int[3];
						for(int i = 0; i < data.length; i++) {
							try {
								parsedData[i] = Integer.parseInt(data[i]);
							} catch(Exception e) {
								System.err.println(errorPrefix.replaceAll("%LN%", lineNumber+"") + "Failed to parse vibration value #" + i + ", \"" + data[i] + "\" is not accepted as an integer.");		
								reader.close();
								return null;
							}
						}
						toAdd = new ActionValue(action, new VibrationData(parsedData[0], parsedData[1], parsedData[2]));
					} else if(action == InputAction.SWITCH_LAYOUT) {
						toAdd = new ActionValue(action, lineValue); // No way to check if they want to add a valid layout, just have to trust
					} else {
						toAdd = new ActionValue(action, null);
					}
					
					// Add instruction to existing arrays, depending on press mode or not
					// Since we're using arrays we have to specially append each value. May be good to switch to lists in a future update.
					if(pressMode) {
						ActionValue[] array = new ActionValue[] {toAdd};
						if(buttonPressActions.containsKey(currentButton)) array = appendValue(buttonPressActions.get(currentButton), toAdd);
						buttonPressActions.put(currentButton, array);
					} else {
						ActionValue[] array = new ActionValue[] {toAdd};
						if(buttonReleaseActions.containsKey(currentButton)) array = appendValue(buttonReleaseActions.get(currentButton), toAdd);
						buttonReleaseActions.put(currentButton, array);						
					}
					
				} else {
					System.err.println(errorPrefix.replaceAll("%LN%", lineNumber+"") + "Line has an incorrect amount of tabs. (" + clearedTabs + ")");
					reader.close();
					return null;
				}
				
				line = reader.readLine();
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		if(lineNumber > 0) System.out.print("Loaded layout \"" + title + "\"");
		return new Layout(main, title, buttonPressActions, buttonReleaseActions, leftStick, rightStick, leftTrigger, rightTrigger);
	
	}
			
	public static List<Layout> loadLayoutsInFolder(ControllerAdapter main, File folder) {
		System.out.println("Loading all layouts in \"" + folder.getPath() + "\"");
		List<Layout> layouts = new ArrayList<Layout>();
		File[] files = folder.listFiles();
		for(File file : files) {
			// Make sure it's a txt file
			System.out.print("  Loading from \"" + file.getPath() + "\" ... ");
			if (file.getName().lastIndexOf('.') > 0 && file.getName().substring(file.getName().lastIndexOf('.') + 1).equals("TXT")) {
				Layout layout = loadLayout(main, file);
				if(layout == null) {
					System.err.println("Failed to load layout \"" + file.getName() + "\"");
					continue;
				}
				System.out.println(" ... Success");
				layouts.add(layout);
			} else {
				System.out.println("  Warning: " + file.getName() + " isn't a .txt file, layouts need to be .txt");
			}
		}
		System.out.println("Loaded " + layouts.size() + " layout(s).");
		return layouts;
	}
	
	public static ActionValue[] appendValue(ActionValue[] original, ActionValue newData) {
		ActionValue[] newArray = new ActionValue[original.length + 1];
		for(int i = 0; i < original.length; i++) {
			newArray[i] = original[i];
		}
		newArray[newArray.length - 1] = newData;
		return newArray;
	}
	
}
