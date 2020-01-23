package gui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/*
 * diese Klasse zeigt nach jeder beendeten Hand einen (nicht-modalen) Info-Dialog 
 * mit der aktuellen Handstärke und der erzielten Punktzahl an
 * Dies soll dem Spieler helfen, leichter festzustellen, dass die Hand beendet ist
 * und nicht nochmal neu gemischt werden kann
 * 
 * Das Layout des Dialogs ist noch in Überarbeitung
 */

public class HandFinishedDialog extends JDialog {

	JDialog dialog = new JDialog();
	String str;
	
	public HandFinishedDialog(String str) {
		
		this.str = str;
		
		JOptionPane optPane = new JOptionPane(str, 
				JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, 
				null, new Object[]{}, null);

		dialog.setUndecorated(true);
//		dialog.setLayout(new BorderLayout());
		dialog.add(optPane);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

	}
	
	
}
