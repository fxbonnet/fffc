package com.pulickaldanny.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/* This class is for displaying pop up window for error message */
public class ErrorGUI {
	JFrame jframe;

	public ErrorGUI(String message) {
		jframe = new JFrame();
		JOptionPane.showMessageDialog(jframe, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
