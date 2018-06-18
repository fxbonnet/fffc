package com.pulickaldanny.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.pulickaldanny.csvconverter.CSVConverter;
/* This class creates user interface for displaying status  
 * of input data file processing*/
public class ProgressGUI extends JFrame implements ActionListener {
	JLabel headerLabel, progressTextLabel, progressTextLabel1;
	JTextField progressTextField;
	JButton progressTestButton;
	JProgressBar progressBar;
	JFrame frame;
	int MAX = 100;

	public ProgressGUI(File metaDataFile, File inputDataFile, File outputDataFile) {
		super("File Converter - Progress");
		
		/* Adding header line into GUI */
		/* Create and add header line to JFrame */		
		headerLabel = new JLabel("Convert Text File Into CSV file");
		headerLabel.setBounds(0, 0, 500, 30);
		headerLabel.setHorizontalAlignment(JTextField.CENTER);
		add(headerLabel);

		/* Adding JLabel to display on-going process */
		progressTextLabel = new JLabel("Status Text:");
		progressTextLabel.setBounds(10, 50, 75, 30);
		progressTextLabel1 = new JLabel("Initializing ....");
		progressTextLabel1.setBounds(85, 50, 390, 30);
		progressTestButton = new JButton("Close");
		progressTestButton.setBounds(200, 170, 100, 30);
		progressTestButton.addActionListener(this);

		// Adding progress bar to display the status of input data file processing
		progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setMaximum(MAX);
		progressBar.setStringPainted(true);
		progressBar.setBounds(10, 100, 470, 30);

		add(progressTextLabel);
		add(progressTextLabel1);
		add(progressBar);
		add(progressTestButton);

		// Thread to handle updating of progressbar
		Thread t = new Thread() {
			public void run() {
				try {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {

							try {
								new CSVConverter().process(metaDataFile, inputDataFile, outputDataFile,
										progressTextLabel1, progressBar);
							} catch (Exception e) {
								//Display error message in a pop up
								new ErrorGUI(e.getMessage());
								System.exit(0);
							}

						}
					});
				} catch (Exception ex) {
					//Display error message in a pop up
					new ErrorGUI(ex.getMessage());
					System.exit(0);
				}
			}
		};
		t.start();

	}

	// Adding action for close button
	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}
}
