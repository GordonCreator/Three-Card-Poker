package gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;


/*
 * diese Klasse beschreibt das Layout der im Spiel verwendeten Buttons
 */

public class PButton extends JButton {

	public PButton(String text) {
		
		setText(text);
		setBackground(new Color(70, 150, 250));
		setFont(new Font("Default", Font.BOLD, 20));
		setForeground(Color.WHITE);
		setBorderPainted(false);
		setFocusable(false);
	}

}


