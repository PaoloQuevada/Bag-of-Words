package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Main {
	public static void initList(FileDialog dialog, String filename, ArrayList<WordInstance> list) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(dialog.getDirectory() + filename));
		
		list.clear();
		
		while(sc.hasNext()) {
			String temp = sc.next();
			temp = temp.replaceAll("[^a-zA-Z0-9]", "");
			temp = temp.toLowerCase();
			if(temp.length() == 0) {
				break;
			}
			if(list.size() != 0) {
				for(int i = 0; i < list.size(); ++ i) {
					if(list.get(i).getWord().equals(temp)){
						System.out.println("pumasok sa meron");
						list.get(i).increment();
						break;
					}else {
						System.out.println("pumasok sa wala");
						WordInstance instance = new WordInstance(temp);
						list.add(instance);
						break;
					}
				}
			} else {
				System.out.println("gumawa ng sinauna");
				WordInstance instance = new WordInstance(temp);
				list.add(instance);
			}
		}
		sc.close();	
		for(WordInstance w: list) {
			System.out.println("Word: " + w.getWord() + ", Count: " + w.getCount());
		}
	}
	
	public static void updateData(ArrayList<WordInstance> list, JLabel size, JLabel total, JTable table) {
		int isize = list.size();
		int itotal = 0;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		Object[] row = new Object[2];
		
		for(WordInstance w: list) {
			itotal += w.getCount();
			row[0] = w.getWord();
			row[1] = w.getCount();
			model.addRow(row);
		}
		
		size.setText("Dictionary Size: " + isize);
		total.setText("Total Words: " + itotal);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		GridBagConstraints c = new GridBagConstraints();
		
		JFrame frame = new JFrame();

        frame.setLayout(new GridBagLayout());
        JLabel openFile = new JLabel("Select File: ");
        c.gridx = 0;
        c.gridy = 0;
        frame.getContentPane().add(openFile, c);
        
        JLabel dSize = new JLabel("Dictionary Size: 0");
        c.gridy = 1;
        frame.getContentPane().add(dSize, c);
        JLabel dTotal = new JLabel("Total Words: 0");
        c.gridy = 2;
        frame.getContentPane().add(dTotal, c);
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Word");
        model.addColumn("Count");
        JTable table = new JTable();
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        c.gridy = 4;
        frame.getContentPane().add(scrollPane, c);
        
        ArrayList<WordInstance> list = new ArrayList<WordInstance>();
        JButton fileButton = new JButton("Browse...");
        fileButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        	    dialog.setMode(FileDialog.LOAD);
        	    dialog.setVisible(true);
        	    String fileString = dialog.getFile();
        	    System.out.println(fileString + " chosen.");
        	    try {
					initList(dialog, fileString, list);
					updateData(list, dSize, dTotal, table);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
        	}
        });
        c.gridx = 1;
        c.gridy = 0;
        frame.add(fileButton, c);
        
        frame.pack();
		frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
}
