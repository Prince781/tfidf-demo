package com.tfidfdemo.gfx;

import java.awt.*;

public class Grapher {
	private static String[] fnames = new String[0];
	private static double[] vals = new double[0];
	private static double max = 1;
	private static String searchTerm = "";
	
	public static class Colors {
		public static final Color TITLE = new Color(234,234,211);
		public static final Color BAR = new Color(200,133,111);
		public static final Color BAR_TEXT = new Color(201,204,202);
		public static final Color BAR_TFIDF_TEXT = new Color(54,83,43);
	}

	public static void setData(String query, String[] filenames,
			double[] values, double m) {
		searchTerm = query;
		fnames = filenames;
		vals = values;
		max = (m == 0 ? max : m);
		System.out.println("Given "+vals.length+" values with max = "+max);
	}
	
	public static void resetData() {
		vals = new double[0];
		fnames = new String[0];
		max = 1;
		searchTerm = "";
	}
	
	/**
	 * Renders output data from analysis.
	 * @param g Graphics context.
	 */
	public static void renderData(Graphics2D g, int w, int h) {
		if (vals.length == 0) return;
		double L = vals.length;
		int offx = 10, offy = 100;
		
		// draw title
		g.setColor(Colors.TITLE);
		String title = "tf-idf results for query \""+searchTerm+"\"";
		double tlen = g.getFontMetrics().getStringBounds(title, g).getWidth();
		g.drawString(title, (int)(w/2 - tlen/2), 20);
		
		for (int i=0; i<vals.length; i++) {
			int x = (int)(offx + (w-offx)*i/L),
				width = (int)((w-offx*2)/L),
				height = (int)((vals[i]/max)*(h-offy));
			
			// draw bar
			g.setColor(Colors.BAR);
			g.fillRect((int)(offx + (w-offx)*i/L), h-height,
					(int)((w-offx*2)/L), height);
			
			// draw text
			g.setColor(Colors.BAR_TEXT);
			double slen = g.getFontMetrics().getStringBounds(fnames[i], g)
					.getWidth();
			g.drawString(fnames[i], x + width/2 - (int)(slen/2), h-height-10);
			
			g.setColor(Colors.BAR_TFIDF_TEXT);
			String label = "tfidf = "+(((float)(int)(vals[i]*100000))/100000);
			slen = g.getFontMetrics().getStringBounds(label, g).getWidth();
			g.drawString(label, x + width/2 - (int)(slen/2), h-height+16);
		}
	}
}