package com.minor.proj;
/*** @author Ankush */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.text.*;


/** create a class Textedit, this is the base class for the notepad clone*/

public class TextEdit extends JFrame{
	//base variables are here XD
	private JTextArea area=new JTextArea(20,120);
	private String currentFile="Untitled";
	private boolean changed=false;
	private JFileChooser dialog=new JFileChooser(System.getProperty("user.dir"));
	
	//changed refers to any area change man!
	
	/***The Constructor has several parts making the basic UI of the app*/
	public TextEdit(){
		//This is the first part of the constructor, We create a menubar, a scroll view and add menu optionns.
		area.setFont(new Font("Arial", Font.PLAIN, 12));
		JScrollPane scroll =new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll,BorderLayout.CENTER);
		JMenuBar JMB = new JMenuBar();
		setJMenuBar(JMB);
		JMenu file=new JMenu("File");
		JMenu edit=new JMenu("Edit");
		JMB.add(file);JMB.add(edit);
		
		//This part here adds functionality to menu, the internal fuctions like open,cut etc will be defined  later
		file.add(NEW);file.add(Open);file.add(Save);file.add(Quit);file.add(SaveAs);file.addSeparator();
		for(int i=0;i<4;i++)
			file.getItem(i).setIcon(null);
		
		edit.add(Cut);edit.add(Copy);edit.add(Paste);
		edit.getItem(0).setText("Cut off!");
		edit.getItem(1).setText("Copy!");
		edit.getItem(2).setText("Paste!");
		
		//simply adding icons, save and saveas false on default, we need to specify images
		JToolBar tool=new JToolBar();
					  add(tool,BorderLayout.NORTH);
					  tool.add(NEW);tool.add(Open);tool.add(Save);tool.add(Save);
					  tool.addSeparator();		
					  
					  JButton cut=tool.add(Cut),cop=tool.add(Copy),pas=tool.add(Paste);
					  cut.setText(null);cut.setIcon(new ImageIcon("cut.png"));
					  cop.setText(null);cop.setIcon(new ImageIcon("copy.png"));
					  pas.setText(null);pas.setIcon(new ImageIcon("paste.png"));
					  
					  Save.setEnabled(false);
					  SaveAs.setEnabled(false);
					  
					  setDefaultCloseOperation(EXIT_ON_CLOSE);
					  pack();
					  area.addKeyListener(k1);
					  setTitle(currentFile);
					  setVisible(true);
		
	}
	
	/***This adds a listener to when a key is pressed so basically letting us know about key press*/
	private KeyListener k1=new KeyAdapter() {
		public void keyPressed(KeyEvent e){
			changed= true;
			Save.setEnabled(true);
			SaveAs.setEnabled(true);
		}
	};
	
	//Open abstract action
	Action Open =new AbstractAction("Open",new ImageIcon("open.png")) {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			saveOld();
			if(dialog.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
				readInFile(dialog.getSelectedFile().getAbsolutePath());
			}
			SaveAs.setEnabled(true);
		}
	};
		
	//We save our file here
	Action Save = new AbstractAction("Save", new ImageIcon("save.png")) {
		public void actionPerformed(ActionEvent e) {
			if(!currentFile.equals("Untitled"))
				saveFile(currentFile);
			else
				saveFileAs();
		}
	};
	
	//save file as action
	Action SaveAs = new AbstractAction("Save as!") {
		public void actionPerformed(ActionEvent e) {
			saveFileAs();
		}
	};
	
	Action NEW = new AbstractAction("New file") {
		public void actionPerformed(ActionEvent e) {
			new TextEdit();
		}
	};
	
	//just exit action
	Action Quit = new AbstractAction("Quit") {
		public void actionPerformed(ActionEvent e) {
			saveOld();
			System.exit(0);
		}
	};
	
	//Sort krdiya cut copy paste action yhan
	ActionMap m = area.getActionMap();
	Action Cut = m.get(DefaultEditorKit.cutAction);
	Action Copy = m.get(DefaultEditorKit.copyAction);
	Action Paste = m.get(DefaultEditorKit.pasteAction);
	
	//Method to save the current File
	private void saveFileAs() {
		if(dialog.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
			saveFile(dialog.getSelectedFile().getAbsolutePath());
	}
	
	//it saves the changed state
	private void saveOld() {
		if(changed) {
			if(JOptionPane.showConfirmDialog(this, "Yo! Wanna save this file! "+ currentFile +" ?","Save",JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION)
				saveFile(currentFile);
		}
	}
	//This is a file reader
	private void readInFile(String fileName) {
		try {
			FileReader r = new FileReader(fileName);
			area.read(r,null);
			r.close();
			currentFile = fileName;
			setTitle(currentFile);
			changed = false;
		}
		catch(IOException e) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this,"Editor can't find the file called "+fileName);
		}
	}
	
	//We are saving the file here!
	private void saveFile(String fileName) {
		try {
			FileWriter w = new FileWriter(fileName);
			area.write(w);
			w.close();
			currentFile = fileName;
			setTitle(currentFile);
			changed = false;
			Save.setEnabled(false);
		}
		catch(IOException e) {
		}
	}
	public  static void main(String[] arg) {
		new TextEdit();
	}
}
	

