package com.tfidfdemo.gfx;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

import com.libtfidf.tfidf.Analysis;

public class GUI {
	// GUI
	private static JPanel canvas, bottom;
	private static JButton browse, analyze;
	private static JLabel label;
	
	// IO
	private static File[] files;
	public static void init(JApplet applet) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		
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
				g.setColor(new Color(20,20,20));
				g.fillRect(0, 0, width, height);
				
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
				
			}
		});
		
		analyze = new JButton("Analyze");
		analyze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { GUI.tfidf(); }
		});
		
		// text label
		label = new JLabel("libtfidf Demo", SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(350, 50));
		
		applet.setLayout(new BoxLayout(applet.getContentPane(),
				BoxLayout.Y_AXIS));
		applet.add(canvas);
			bottom.add(browse);
			bottom.add(label);
			bottom.add(analyze);
		applet.add(bottom);
	}
	
	/**
	 * Perform TFIDF using the library.
	 */
	public static void tfidf() {
		Analysis an = new Analysis();
		
	}
}
