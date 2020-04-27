import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class SimFrame extends JFrame
{
	public SimFrame()
	{
		setTitle("Sim Machine");
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout(FlowLayout.CENTER, 150, 25));

		createContents();
		setVisible(true);
	}
	private void createContents()
	{
		JMenuBar menu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem load = new JMenuItem("Load Machine State");
		JMenuItem save = new JMenuItem("Save Machine State");
		JMenuItem exit = new JMenuItem("Exit");
		fileMenu.add(load);
		fileMenu.add(save);
		fileMenu.add(new JSeparator());
		fileMenu.add(exit);

		JMenu editMenu = new JMenu("Edit");
		JMenuItem copy = new JMenuItem("Copy");
		JMenuItem paste = new JMenuItem("Paste");
		JMenuItem reset = new JMenuItem("Reset");
		editMenu.add(copy);
		editMenu.add(paste);
		editMenu.add(reset);

		JMenu runMenu = new JMenu("Run");
		JMenuItem run = new JMenuItem("Run Program");
		JMenuItem halt = new JMenuItem("Halt Program");
		JMenuItem stop = new JMenuItem("Stop Program");
		runMenu.add(run);
		runMenu.add(halt);
		runMenu.add(stop);

		JMenu helpMenu = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		helpMenu.add(about);

		menu.add(fileMenu);
		menu.add(editMenu);
		menu.add(runMenu);
		menu.add(helpMenu);
		setJMenuBar(menu);

		JPanel registerPanel = new JPanel(new GridLayout(18,2));
		JLabel[] registerLabels = new JLabel[16];
		JTextField[] registers = new JTextField[16];
		for(int i = 0; i < registers.length; ++i) {
			registerLabels[i] = new JLabel(toHexString(i) + ":");
			registerPanel.add(registerLabels[i]);
			registers[i] = new JTextField("00", 6);
			registerPanel.add(registers[i]);
		}

		JLabel pcLabel = new JLabel("PC:");
		registerPanel.add(pcLabel);
		JTextField pcField = new JTextField("00", 6);
		registerPanel.add(pcField);

		JLabel irLabel = new JLabel("IR:");
		registerPanel.add(irLabel);
		JTextField irField = new JTextField("00", 6);
		registerPanel.add(irField);

		JPanel memPanel = new JPanel(new GridLayout(17,17));
		JTextField[] memory = new JTextField[256];
		memPanel.add(new JLabel());
		for(int i = 0; i < 16; ++i) {
			memPanel.add(new JLabel(toHexString(i)));
		}
		for(int i = 0; i < memory.length; ++i) {
			if(i % 16 == 0) {
				memPanel.add(new JLabel(toHexString(i/16)));
			}
			memory[i] = new JTextField("00");
			memPanel.add(memory[i]);
		}

		JPanel errorPanel = new JPanel();
		errorPanel.setPreferredSize(new Dimension(500,20));
		errorPanel.add(new JLabel("ERROR: MISSING DINOSAURS"));

		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton loadButton = new JButton("Load");
		JButton runButton = new JButton("Run");
		JButton haltButton = new JButton("Halt");
		JButton saveButton = new JButton("Save");
		JButton stopButton = new JButton("Stop");
		JButton resetButton = new JButton("Reset");
		buttonPanel.add(loadButton);
		buttonPanel.add(runButton);
		buttonPanel.add(haltButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(resetButton);
		
		add(registerPanel);
		add(memPanel);
		add(errorPanel);
		add(buttonPanel);

		/*panel2.add(fresh);
		fresh.addActionListener(flistener);
		JButton quit = new JButton("Quit");
		quit.addActionListener(qlistener);
		panel2.add(quit);
		add(panel2,BorderLayout.EAST);
		d = new TicTacToe();
		d.setVisible(true);
		add(d);
		validate();*/
	}

	private String toHexString(int i) {
		switch(i - 9) {
			default:
				return i + "";
			case 1:
				return "A";
			case 2:
				return "B";
			case 3:
				return "C";
			case 4:
				return "D";
			case 5:
				return "E";
			case 6:
				return "F";
		}
	}
	public static void main(String[] args)
	{
		new SimFrame();
	}
}