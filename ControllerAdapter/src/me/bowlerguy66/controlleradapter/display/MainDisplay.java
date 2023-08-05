package me.bowlerguy66.controlleradapter.display;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import me.bowlerguy66.controlleradapter.ControllerAdapter;
import me.bowlerguy66.controlleradapter.Values;
import me.bowlerguy66.controlleradapter.display.primitives.Display;
import me.bowlerguy66.controlleradapter.display.primitives.TextAreaOutputStream;
import me.bowlerguy66.controlleradapter.layouts.LayoutManager;
import me.bowlerguy66.controlleradapter.utils.Utils;

@SuppressWarnings("serial")
/**
 * @author mulle
 *	The main display for the program, currently shows layout picker and system log
 */
public class MainDisplay extends Display {

	private ControllerAdapter main;
	
	private JComboBox<String> layoutsBox;
	private JTextArea console;
	
	/**
	 * Constructor for MainDisplay, takes main instance
	 * @param main Instance of main class
	 */
	public MainDisplay(ControllerAdapter main) {
		super("Controller Adapter", 
			  (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.2), 
			  (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.1));
		this.main = main;
		setIconImage(Utils.loadImage(ControllerAdapter.class.getResource(ControllerAdapter.RESOURCES_FOLDER_PATH + "icon.png")));
	}
	
	/**
	 * Add components to the display such as title text, layouts box, and display log
	 */
	public void addComponents() {
		
		setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JLabel text = new JLabel("Adapter is running");
		Font font = new Font(Values.FONT, Font.BOLD, (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.02));
		text.setFont(font);
		topPanel.add(text, BorderLayout.LINE_START);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		
		JButton reloadButton = new JButton("Reload");
		reloadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reloadPressed();
			}
		});
		controlPanel.add(reloadButton, BorderLayout.LINE_START);
		
		layoutsBox = new JComboBox<String>(new String[0]);
		layoutsBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() != ItemEvent.SELECTED) return;
				main.getLayoutManager().setCurrentLayout((String) e.getItem(), true);
			}
		});
		controlPanel.add(layoutsBox, BorderLayout.LINE_END);
		
		topPanel.add(controlPanel, BorderLayout.LINE_END);
		add(topPanel, BorderLayout.NORTH);
		
		console = new JTextArea(10, 80);
		console.setEditable(false);
		System.setOut(new PrintStream(new TextAreaOutputStream(console)));
		add(new JScrollPane(console), BorderLayout.SOUTH);

		pack(); // Resizes frame to adjust to canvas.
		setVisible(true);
		setLocationRelativeTo(null);

	}

	/**
	 * Update the layouts box to have all of the loaded layouts in it
	 */
	public void updateLayoutsBox() {
		if (main.getLayoutManager() == null) return;
		LayoutManager lm = main.getLayoutManager();
		DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) layoutsBox.getModel();
		model.removeAllElements();
		for(String name : lm.getLayoutNames()) {
			model.addElement(name);
		}
		if(lm.getCurrentLayout() != null) model.setSelectedItem(lm.getCurrentLayout().getTitle());
		layoutsBox.setModel(model);
	}

	/**
	 * Reload button pressed
	 */
	public void reloadPressed() {
		try {
            String javaCommand = System.getProperty("java.home") + "/bin/java";
            String javaClassPath = System.getProperty("java.class.path");
            String mainClass = ControllerAdapter.class.getCanonicalName();
            
            ProcessBuilder processBuilder = new ProcessBuilder(javaCommand, "-cp", javaClassPath, mainClass);
            processBuilder.start();
            
            System.exit(0); // Terminate the current instance
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
}
