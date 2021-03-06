package de.slag.finance.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import de.slag.common.base.BaseException;

public class FinCtrlApp {

	public static void main(String[] args) {
		ActionListener al;
		JFrame f = new JFrame();// creating instance of JFrame

		JButton bStage = new JButton("STAGE");
		JButton bImport = new JButton("IMPORT");
		JButton bCalc = new JButton("CALC");
		JButton bStop = new JButton("STOP");
		JButton bReport = new JButton("REPORT");
		JTextField textField = new JTextField();

		final int buttonHeight = 40;
		final int buttonWidth = 100;

		final int abstand = 15;

		bStage.setBounds(abstand, abstand, buttonWidth, buttonHeight);
		bImport.setBounds(abstand * 2 + buttonWidth, abstand, buttonWidth, buttonHeight);
		bCalc.setBounds(abstand * 3 + buttonWidth * 2, abstand, buttonWidth, buttonHeight);
		bReport.setBounds(abstand * 4 + buttonWidth * 3, abstand, buttonWidth, buttonHeight);
		bStop.setBounds(abstand * 5 + buttonWidth * 4, abstand, buttonWidth, buttonHeight);
		textField.setBounds(abstand, abstand * 2 + buttonHeight, buttonWidth * 5, 25);

		f.add(bStage);
		f.add(bImport);
		f.add(bCalc);
		f.add(bReport);
		f.add(bStop);
		f.add(textField);

		final int width = abstand * 7 + buttonWidth * 5;
		final int height = abstand * 4 + buttonHeight * 2;
		f.setSize(width, height);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = textField.getText();
				if (StringUtils.isBlank(text)) {
					JOptionPane.showMessageDialog(null, "path not setted");
					return;
				}
				String s = text + "/";
				final Object source = e.getSource();
				if (source == bStage) {
					s += "STAGE";
				} else if (source == bImport) {
					s += "IMPORT";
				} else if (source == bCalc) {
					s += "CALC";
				} else if (source == bReport) {
					s += "REPORT";
				} else if (source == bStop) {
					s += "STOP";
				} else {
					throw new BaseException("");
				}
				try {
					new File(s).createNewFile();
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, "exception, message: " + ex.getMessage());
					throw new BaseException(ex);
				}

			}
		};
		bStage.addActionListener(al);
		bImport.addActionListener(al);
		bCalc.addActionListener(al);
		bStop.addActionListener(al);
	}

}
