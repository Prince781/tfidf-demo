package com.tfidfdemo.gfx;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import com.tfidfdemo.TfIdfDemo;

public class GUI {
	// GUI
	private static JPanel canvas, bottom;
	private static JButton browse, analyze;
	private static JLabel label;
	private static JTextField termField;
	
	// IO
	private static File[] files = new File[0];
	public static void init(final JApplet applet) {
		setDefaultUI();
		
		// canvas
		canvas = new JPanel(true) {
			private int width, height;
			public void screenUpdate() { Toolkit.getDefaultToolkit().sync(); }
			public void paint(Graphics g) {
				// TODO: update variables according to dimensions
				width = getWidth();
				height = getHeight();
				super.paint(g);
			}
			public void paintComponent(Graphics gfx) {
				Graphics2D g = (Graphics2D) gfx;
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				
				g.clearRect(0, 0, width, height);
				
				// render background
				g.setColor(new Color(40,40,40));
				g.fillRect(0, 0, width, height);
				// render analytical results
				Grapher.renderData(g, width, height);
				
				screenUpdate();
				repaint();
			}
		};
			canvas.setPreferredSize(new Dimension(800, 440));
		bottom = new JPanel(true);
			bottom.setPreferredSize(new Dimension(800, 60));
		
		// buttons
		browse = new JButton("Select Corpus");
		browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(true);
				if (fc.showOpenDialog(applet) == JFileChooser.APPROVE_OPTION)
					files = fc.getSelectedFiles();
			}
		});
		
		analyze = new JButton("Analyze");
		analyze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (files.length == 0)
					showNotice(applet,
								"You must select plain-text files to analyze.");
				else try {
					if (termField.getText().isEmpty())
						showNotice(applet, "Please enter a search term.");
					else TfIdfDemo.tfidf(termField.getText().split(" ")[0]);
				} catch (IOException exp) {
					showNotice(applet, "There was an error while reading.");
				}
			}
		});
		
		// text label
		label = new JLabel("libtfidf Demo", SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(350, 50));
		
		// term field
		termField = new JTextField();
		termField.setPreferredSize(new Dimension(100, 30));
		termField.setText("term");
		termField.setToolTipText("Enter a single word (term) to search for.");
		
		applet.setLayout(new BoxLayout(applet.getContentPane(),
				BoxLayout.Y_AXIS));
		applet.add(canvas);
			bottom.add(browse);
			bottom.add(label);
			bottom.add(termField);
			bottom.add(analyze);
		applet.add(bottom);
	}
	
	public static void showNotice(final JApplet applet, String text) {
		JOptionPane.showMessageDialog(applet, text);
	}
	
	/**
	 * Gets selected files.
	 */
	public static File[] getFiles() { return files; }
	
	/**
	 * Resets file count.
	 */
	public static void resetFiles() { files = new File[0]; }
	
	/**
	 * Attempts to set the appearance of the file chooser to the default 
	 * requested by the system's theme.
	 */
	public static void setDefaultUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}
