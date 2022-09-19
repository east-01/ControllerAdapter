# ControllerAdapter
A Java program that lets you use an Xbox controller as a keyboard and mouse, use different layouts to configure what each input on the Xbox controller does.
Currently in alpha, documentation will be provided once the program is in a better state.

A general summary:

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
