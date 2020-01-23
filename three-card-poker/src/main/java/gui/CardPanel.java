package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/*
 * diese Klasse beschreibt den Aufbau der Kartenpanels innerhalb des CenterPanels
 * Jedes Kartenpanel besteht aus dem Icon der Karte sowie dem darüberstehenden Text mit 
 * der Information, ob die Karte gehalten ("Hold") oder neu gemischt ("Draw") werden soll
 */

public class CardPanel extends JPanel {

	private JLabel holdLabel = new JLabel("Hold");
	private JLabel cardLabel = new JLabel();
	
	// Dateiname f�r das Symbol der Kartenr�ckseiten 
	private final static String CARDBACKSIDE = "back_red";
	
	// Pfad f�r das Symbol der Kartenrückseite
	private String pathCardBack = "/symbols/" + CARDBACKSIDE + ".png";
	
	public CardPanel() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		holdLabel.setAlignmentX(CENTER_ALIGNMENT);
		cardLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		Border invisibleBorder = BorderFactory.createEmptyBorder(2,10,2,10);
		Border visibleBorder = BorderFactory.createLineBorder(Color.WHITE, 2, true);
		
		holdLabel.setBorder(BorderFactory.createCompoundBorder(visibleBorder, invisibleBorder));
		
		holdLabel.setFont(new Font("Default",Font.PLAIN, 20));
		holdLabel.setForeground(Color.WHITE);
		holdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		holdLabel.setPreferredSize(new Dimension(130, 30));
		holdLabel.setMaximumSize(new Dimension(130, 30));
		
		cardLabel.setIcon(new ImageIcon(getClass().getResource(pathCardBack)));

		add(holdLabel);
		add(Box.createRigidArea(new Dimension(0,5)));
		add(cardLabel);
		
		setOpaque(false);
	}
	
	public JLabel getCardLabel() {
		return cardLabel;
	}

	public JLabel getHoldLabel() {
		return holdLabel;
	}

	public void setHoldLabel(JLabel holdLabel) {
		this.holdLabel = holdLabel;
	}
	
	public void setHoldLabelText(String text) {
		holdLabel.setText(text);
	}
	
	public static String getCardBackSide() {
		return CARDBACKSIDE;
	}
	
}
