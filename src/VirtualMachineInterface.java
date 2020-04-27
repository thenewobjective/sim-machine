import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.text.*;// For JTextFieldLimit

/**
 * The main user interface for the Virtual Machine Project
 * @author Graham Mueller
 * @since December 14th, 2010
 * @version 1.0
 * @see Machine This class acts as a wrapper for the Machine class
 * 
 * Known issues
 * 	Major
 * 		1. Machine is referenced, but doesn't exist in my code
 * 		2. I didn't receive code for "Save" or "Load" listeners, so they're currently empty
 * 
 * 	Minor
 * 		1. None of the text fields restrain input to two characters
 *  	2. Exceptions are not handled properly by button listeners
 *  	3. Deprecated method on message box in "Reset"
 */
public class VirtualMachineInterface extends JFrame {
	//Avoid unnecessary warnings
	private static final long serialVersionUID = 1L;
	//GLOBAL VARIABLES
	//Stored for access throughout the GUI
	
	//The window
	private Container window;
	
	// Main Panels
	private JPanel cpuPanel, memoryPanel, buttonPanel;
	
	// Buttons
	private JButton load = new JButton("Load"), 
					save = new JButton("Save"),
					run = new JButton("Run"), 
					step = new JButton("Step"),
					halt = new JButton("Halt"), 
					reset = new JButton("Reset");
	// Error label
	private JLabel error = new JLabel("Error");
	
	// IR and PC text fields
	//Note: IR contains two 00 00 values, and has a space between each
	private JTextField pcTextField = new JTextField(new JTextFieldLimit(2), "00", 0),
					   irTextField = new JTextField(new JTextFieldLimit(4), "0000", 0);
	
	// Registers' text fields
	private JTextField[] registers = new JTextField[0xF + 1];
	
	// Main Memory cells' text fields
	private JTextField[] memcells = new JTextField[0xFF + 1];


	//0 to F strings to use as labels
	//ISSUE: Tried to store these as JLabels, but couldn't
	//		 seem to add them to panels that way. Not sure why.
	private static final String[] ZEROTOF = new String[] { "0", "1", "2", "3", "4",
			"5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	public VirtualMachineInterface() {
		window = this.getContentPane();
		setTitle("Virtual Machine");
		setSize(800, 600);
		setResizable(false); // Don't want resizing, might break formatting
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		createContents();
		setVisible(true);
	}

	private void createContents() {
		//We have a border layout, we will use Center and South
		setLayout(new BorderLayout());
		
		//Create the three main areas
		createCPU();
		createMemory();
		createButtons();
		
		//The two top pieces split the center region 50/50
		JPanel cpuMemPanel = new JPanel(new GridLayout(1, 2));
		cpuMemPanel.add(cpuPanel);
		cpuMemPanel.add(memoryPanel);

		//The newly formed "top panel" and button panel are added
		add(cpuMemPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private void createButtons() {

		JPanel buttonCenter = new JPanel(new BorderLayout());

		// Add Error Label -- Can't use floater because the error message may change
		// However, it still needs to be on its own panel to preserve centered text
		JPanel errorFloater = new JPanel(new FlowLayout());
		errorFloater.add(error);
		error.setForeground(Color.red); // red text
		buttonCenter.add(errorFloater, BorderLayout.CENTER);

		// Add button listeners
		load.addActionListener(new Listener());
		run.addActionListener(new Listener());
		halt.addActionListener(new Listener());
		save.addActionListener(new Listener());
		step.addActionListener(new Listener());
		reset.addActionListener(new Listener());

		// Create actual panel containing buttons
		JPanel buttons = new JPanel(new GridLayout(2, 3));
		buttons.add(load);
		buttons.add(step);
		buttons.add(halt);
		buttons.add(save);
		buttons.add(run);
		buttons.add(reset);

		// Add actual buttons to the centered panel
		buttonCenter.add(buttons, BorderLayout.SOUTH);

		// Instantiate the button panel, surround the buttons with dummies
		buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(new JPanel());
		buttonPanel.add(buttonCenter);
		buttonPanel.add(new JPanel());
	}

	private void createMemory() {

		// Create Label
		JPanel memFloater = createFloater("Main Memory");

		// Create actual memory grid
		// Note: One extra row and column to add labels
		JPanel memory = new JPanel(new GridLayout(17, 17));
		// Dummy first space
		memory.add(new JPanel());
		// Top labels
		for (int i = 0; i < 16; i++) {
			memory.add(new JLabel(ZEROTOF[i]));
		}
		// Contents of memory
		for (int i = 0; i < 16; i++) {
			memory.add(new JLabel(ZEROTOF[i]));
			for (int j = 0; j < 16; j++) {
				memory.add(memcells[i*16 + j] = new JTextField(new JTextFieldLimit(2),"00", 0));// Don't know know what 0 does
			}
		}
		memoryPanel = new JPanel(new BorderLayout());
		memoryPanel.add(memFloater, BorderLayout.NORTH);
		memoryPanel.add(memory);
	}

	private void createCPU() {

		JPanel cpuFloater = createFloater("C.P.U.");

		// Main panel
		JPanel cpu = new JPanel(new GridLayout(1, 2));
		JPanel pcir = new JPanel(new GridLayout(2, 1));
		JPanel register = new JPanel(new GridLayout(17, 1));
		// Add subpanels
		cpu.add(pcir);
		cpu.add(register);
		// Fill in subpanels pc and ir
		JPanel pc = new JPanel(new FlowLayout());
		JPanel ir = new JPanel(new FlowLayout());
		// add pc and ir to pcir
		pc.add(new JLabel("PC"));
		pc.add(pcTextField);
		ir.add(new JLabel("IR"));
		ir.add(irTextField);
		pcir.add(pc);
		pcir.add(ir);
		// Fill in subpanel register
		JPanel regFloater = createFloater("Registers");
		register.add(regFloater);

		JPanel[] regPanel = new JPanel[16];
		for (int i = 0; i < 16; i++) {
			registers[i] = new JTextField(new JTextFieldLimit(2), "00", 0);
			regPanel[i] = new JPanel(new FlowLayout());
			regPanel[i].add(new JLabel(ZEROTOF[i]));
			regPanel[i].add(registers[i]);
			register.add(regPanel[i]);
		}

		// Create the actual panel
		cpuPanel = new JPanel(new BorderLayout());
		cpuPanel.add(cpuFloater, BorderLayout.NORTH);
		cpuPanel.add(cpu);
	}

	public JPanel createFloater(String text) {
		JPanel floater = new JPanel(new FlowLayout());
		floater.add(new JLabel(text));
		return floater;
	}

	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// SAVE
			if(e.getSource() == save){
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(fc);
				File selFile = fc.getSelectedFile();
				if(selFile.canWrite() || !selFile.exists())
					try {
						Machine.serialize(selFile);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				else
					JOptionPane.showMessageDialog(null,"No Write Permision", "Saving Error", JOptionPane.ERROR_MESSAGE);
			}
			// LOAD
			else if(e.getSource() == load){
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(fc);
				File selFile = fc.getSelectedFile();
				
				if(selFile.canRead())
					Machine.deserialize(selFile);
				else if(selFile.exists())
					JOptionPane.showMessageDialog(null,"No Read Permision", "Loading Error", JOptionPane.ERROR_MESSAGE);
				else
					JOptionPane.showMessageDialog(null,"File Doesn't Exist", "Loading Error", JOptionPane.ERROR_MESSAGE);
			}
			// STEP
			else if(e.getSource() == step){
				// Try/Catch for bad values while stepping
				try{
					Machine.step();
				}catch(Exception stepException){
					error = new JLabel("Error: Problem encountered while stepping");
				}
				update();
			}
			// RUN
			else if(e.getSource() == run){
		    	  Machine.run();
			}
			// RESET
			else if(e.getSource() == reset){
				//Create the message box
				JOptionPane pane = new JOptionPane("Are you sure you want to reset all the memory and registers?");
				
				//Create it's options
				Object[] options = new String[] { "Yes, reset everything", "No, don't reset everything" };
				
				//Add the options to the message box
				pane.setOptions(options);
				
				//Create the pop-up window with the title "Reset?"
				JDialog dialog = pane.createDialog(window, "Reset?");
				
				//Show it... might not need to do this
				dialog.show();
				
				//Get the response
				Object obj = pane.getValue();
				
				//Figure out what the response is
				int result = 0;
				for (int i = 0; i < options.length; i++)
					if (options[i].equals(obj))
						result = i;
				
				//If user selects yes
				if (result == 0)
				{
					//Reset registers
					for (int i =0x0; i < 0xF; i++)
					{
						Machine.setRegister(00, i);

					}
					//Reset memory
					for (int i = 0x00; i < 0xFF; i++)
					{
						Machine.setMemoryCell(00, i);
					}
					//Reset IR and PC
					Machine.setIR(0000);
					Machine.setPC(00);
					//Update GUI
					update();
				}
			}
			// HALT
			else if(e.getSource() == halt){
				//If it is running, stop it
				if(Machine.isrunning()){
					Machine.halt();
				}
				update();
			}
		}
		
		private void update(){
			// Update general purpose registers
			for(int i = 0; i < 16; i++){
				registers[i].setText("" + borkbork(Machine.getRegister(i)));
			}
			// Update main memory cells
			for(int i = 0; i < 256; i++){
				memcells[i].setText("" + borkbork(Machine.getMemoryCell(i)));
			}
			// Update IR
			irTextField.setText(borkbork(Machine.getIR()>>8 & 0xFF) + borkbork(Machine.getIR() & 0xFF));//.substring(0, 1) + " " + (Integer.toHexString(Machine.getIR())).substring(2,3));
			// Update PC
			pcTextField.setText("" + borkbork(Machine.getPC()));
		}
	}
	
	private static String borkbork(int b) {
		if(b <= 0xF)
			return "0" + Integer.toHexString(b);
		return Integer.toHexString(b);
	}
	
	public static class JTextFieldLimit extends PlainDocument {
		private static final long serialVersionUID = 1L;
		private int limit;
		  // optional uppercase conversion
		  private boolean toUppercase = false;
		  
		  JTextFieldLimit(int limit) {
		   super();
		   this.limit = limit;
		   }
		   
		  JTextFieldLimit(int limit, boolean upper) {
		   super();
		   this.limit = limit;
		   toUppercase = upper;
		   }
		 
		  public void insertString
		    (int offset, String  str, AttributeSet attr)
		      throws BadLocationException {
		   if (str == null) return;

		   if ((getLength() + str.length()) <= limit) {
		     if (toUppercase) str = str.toUpperCase();
		     super.insertString(offset, str, attr);
		     }
		   }
		}


	//Simple main... create an instance of the interface
	public static void main(String[] args) { new VirtualMachineInterface(); }
}
