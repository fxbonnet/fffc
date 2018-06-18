package com.pulickaldanny.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/* This class creates user interface for passing meta data file and input data file
 * to the program. Also the program will display the output file path*/
public class UserInterface extends JFrame implements ActionListener {
	JMenuBar mb;
	JMenu file;
	JMenuItem open;
	JTextArea ta;
	JLabel headerLabel, metaDataLabel, inputDataLabel, outputDataLabel;
	JTextField metaDataTextField, inputDataTextField, outputDataTextField;
	JButton metaDataButton, inputDataButton, clearButton, proceedButton;
	String metaDataFilePath, inputDataFilePath, outputDataFilePath;

	UserInterface() {
		super("File Converter");
		
		/* Adding header line into GUI */
		/* Create and add header line to JFrame */
		headerLabel = new JLabel("Convert Text File Into CSV file");
		headerLabel.setBounds(0, 0, 500, 30);
		headerLabel.setHorizontalAlignment(JTextField.CENTER);
		add(headerLabel);
		/* MetaData file */
		/* Create and add MetaData file items to JFrame */
		metaDataLabel = new JLabel("Choose Metadata file:");
		metaDataLabel.setBounds(10, 35, 150, 30);
		metaDataTextField = new JTextField();
		metaDataTextField.setEditable(false);
		metaDataTextField.setBounds(150, 35, 200, 30);
		metaDataButton = new JButton("Browse");
		metaDataButton.setBounds(360, 35, 95, 30);
		metaDataButton.addActionListener(this);
		add(metaDataLabel);
		add(metaDataTextField);
		add(metaDataButton);

		/* Input file */
		/* Create and add InoutData file items to JFrame */
		inputDataLabel = new JLabel("Choose Input file:");
		inputDataLabel.setBounds(10, 70, 150, 30);
		inputDataTextField = new JTextField();
		inputDataTextField.setEditable(false);
		inputDataTextField.setBounds(150, 70, 200, 30);
		inputDataButton = new JButton("Browse");
		inputDataButton.setBounds(360, 70, 95, 30);
		inputDataButton.addActionListener(this);
		add(inputDataLabel);
		add(inputDataTextField);
		add(inputDataButton);

		/* Output file */
		/* Create and add OutputData file items to JFrame */
		outputDataLabel = new JLabel("Onput file location:");
		outputDataLabel.setBounds(10, 105, 150, 30);
		outputDataTextField = new JTextField();
		outputDataTextField.setEditable(false);
		outputDataTextField.setBounds(150, 105, 305, 30);
		add(outputDataLabel);
		add(outputDataTextField);

		/* Create clear button to clear current selection */
		clearButton = new JButton("Clear All");
		clearButton.setBounds(100, 170, 100, 30);
		/* Adding action to clear button to clear meta data & input data path from text fields*/
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metaDataFilePath = null;
				inputDataFilePath = null;
				outputDataFilePath = null;
				metaDataTextField.setText(metaDataFilePath);
				inputDataTextField.setText(inputDataFilePath);
				outputDataTextField.setText(outputDataFilePath);
			}
		});

		/* Create a Proceed button to process the current selection */
		proceedButton = new JButton("Proceed");
		proceedButton.setBounds(300, 170, 100, 30);
		proceedButton.addActionListener(this);
		add(clearButton);
		add(proceedButton);
		showProgressGUI();
	}
	
	/* Adding action for Browze button of meta data & input data */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == metaDataButton) {
			JFileChooser fc = new JFileChooser();
			int i = fc.showOpenDialog(this);
			if (i == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				metaDataFilePath = f.getPath();
				metaDataTextField.setText(metaDataFilePath);
			}
		} else if (e.getSource() == inputDataButton) {
			JFileChooser fc = new JFileChooser();
			int i = fc.showOpenDialog(this);
			if (i == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				inputDataFilePath = f.getPath();
				String path = f.getParent();
				// System.out.println("Absolute Path of the file is "+path);
				inputDataTextField.setText(inputDataFilePath);
				if (!path.isEmpty()) {
					outputDataFilePath = path + "\\output.csv";
				} else {
					outputDataFilePath = null;
				}
				outputDataTextField.setText(outputDataFilePath);
			}
		}
	}
	
	/* Adding action for proceed button*/
	public void showProgressGUI() {
		proceedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("MetaDataFilePath:" + metaDataFilePath);
				System.out.println("InputDataFilePath:" + inputDataFilePath);
				System.out.println("OutputDataFilePath:" + outputDataFilePath);
				/* Confirming that the meta data and input data file are not forgotten to select */
				if (metaDataFilePath != null && !metaDataFilePath.isEmpty() && inputDataFilePath != null
						&& !inputDataFilePath.isEmpty()) {
					File metaDataFile = new File(metaDataFilePath);
					File inputDataFile = new File(inputDataFilePath);
					File outputDataFile = new File(outputDataFilePath);
					ProgressGUI pg = new ProgressGUI(metaDataFile, inputDataFile, outputDataFile);
					pg.setSize(500, 250);
					pg.setLayout(null);
					pg.setVisible(true);
					pg.setDefaultCloseOperation(EXIT_ON_CLOSE);
					dispose();
				} else {
					/* Display error, if the file is not selected*/
					String errorMessage = "";
					if (metaDataFilePath == null) {
						errorMessage = "Meta Data file";
					}
					if (inputDataFilePath == null) {
						if (!errorMessage.isEmpty())
							errorMessage += " and";
						errorMessage += " Input Data file";
					}
					errorMessage += " is not selected. Please try again";
					ErrorGUI egui = new ErrorGUI(errorMessage);
				}
			}
		});
	}

	public static void main(String[] args) {
		UserInterface ui = new UserInterface();
		ui.setSize(500, 250);
		ui.setLayout(null);
		ui.setVisible(true);
		ui.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
}
