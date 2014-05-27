package com.tfidfdemo;

import javax.swing.JApplet;

import com.tfidfdemo.gfx.GUI;

public class TfIdfDemo extends JApplet {
	private static final long serialVersionUID = 1L;

	@Override
	public void start() { GUI.init(this); }
	
	
}
