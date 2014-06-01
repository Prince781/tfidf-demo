package com.tfidfdemo;

import java.io.File;
import java.io.IOException;

import javax.swing.JApplet;

import com.libtfidf.doc.PlainTextDocument;
import com.libtfidf.tfidf.Analysis;
import com.tfidfdemo.gfx.GUI;
import com.tfidfdemo.gfx.Grapher;

public class TfIdfDemo extends JApplet {
	private static final long serialVersionUID = 1L;
	private static Analysis an;
	private static File[] files;
	private static String[] fnames;

	/**
	 * Starts the Java applet.
	 */
	@Override
	public void start() { GUI.init(this); }
	
	/**
	 * Perform TFIDF using the library.
	 * @param term Search term (t).
	 * @throws IOException 
	 */
	public static void tfidf(String term) throws IOException {
		Grapher.resetData();
		
		if (files == null || !files.equals(GUI.getFiles())) {
			files = GUI.getFiles();
			
			fnames = new String[files.length];
			PlainTextDocument[] docs = new PlainTextDocument[files.length];
			
			for (int i=0; i<files.length; i++) {
				docs[i] = new PlainTextDocument(files[i]);
				fnames[i] = files[i].getName();
			}
			
			an = new Analysis(docs); // only perform IO with new sets of files
		}
		
		int length = an.getCorpus().length;
		double[] data = new double[length];
		double max = 0;
		
		for (int d=0; d<length; d++) {
			data[d] = an.tfIDF(term, d);
			if (d == 0) max = data[d];
			if (data[d] > max) max = data[d];
			System.out.printf("data[%d] = %f\n", d, data[d]);
		}
		
		Grapher.setData(term, fnames, data, max); // render output
	}
}
