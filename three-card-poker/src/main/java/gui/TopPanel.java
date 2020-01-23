package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * diese Klasse erstellt und aktualisiert das obere Panel mit
 * den Infos zum Spieler-Guthaben, aktuellem Einsatz, der Stärke
 * der aktuellen Hand sowie dem Beenden-Button
 */

public class TopPanel extends JPanel {

	private JLabel iconLbl;
	
	private JLabel creditTextLabel;
	private JLabel creditScoreLabel;
	private JLabel stakeTextLabel;
	private JLabel stakeScoreLabel;
	
	private JLabel resultLabel;
	
	private JButton newGameBtn;
	private JButton exitBtn;
	
	private Color fontColor;
	private Color bgColor;
	
	private int credits;
	private int newCreditScore;
	private int stake;
	
	public TopPanel(Color fontColor, Color bgColor) {
		
		this.fontColor = fontColor;
		this.bgColor = bgColor;
		
		setLayout(new FlowLayout(20));
		
		setBackground(bgColor);
		
		// Logo in linker oberer Ecke
		iconLbl = new JLabel();
		iconLbl.setIcon(new ImageIcon(getClass().getResource("/symbols/StraightFlush60.png")));
		
		creditTextLabel = createPlayerInfo("Credit: ");
		creditScoreLabel = createPlayerInfo(Integer.toString(credits));
		stakeTextLabel = createPlayerInfo("Stake: ");
		stakeScoreLabel = createPlayerInfo(Integer.toString(stake));
		
		resultLabel = new JLabel("");
		resultLabel.setForeground(new Color(222, 222, 140));
		resultLabel.setFont(new Font("Default", Font.BOLD, 22));
		
		int labelHeight = (int)stakeScoreLabel.getPreferredSize().getHeight();
		resultLabel.setPreferredSize(new Dimension(250,labelHeight));
		

		newGameBtn = new PButton("Neues Spiel");
		exitBtn = new PButton("Beenden");
		
		// Flowlayout mit festen Abständen zwischen den Komponenten
		add(Box.createRigidArea(new Dimension(10, 0)));
		add(iconLbl);
		add(Box.createRigidArea(new Dimension(30, 0)));
		add(creditTextLabel);
		add(creditScoreLabel);
		add(Box.createRigidArea(new Dimension(15, 0)));
		add(stakeTextLabel);
		add(stakeScoreLabel);
		add(resultLabel);
		add(Box.createRigidArea(new Dimension(5, 0)));
//		add(newGameBtn);		// coming soon
		add(Box.createRigidArea(new Dimension(10, 0)));
		add(exitBtn);
		
	}
	
	/*
	 * setzt Eigenschaften für die erstellten Labels
	 *  (Größe, Schriftart, Schriftfarbe)
	 */
	private JLabel createPlayerInfo(String str) {
		
		JLabel tempLbl = new JLabel(str);
		tempLbl.setForeground(fontColor);
		tempLbl.setFont(new Font("Default", Font.BOLD, 22));
		tempLbl.setPreferredSize(new Dimension(75,25));
		return tempLbl;
	}
	
	void addExitListener(ActionListener exitListener) {
		exitBtn.addActionListener(exitListener);
	}

	/*
	 * aktualisiert die Anzeige des Guthabens und der Stärke der aktuellen 
	 *  Hand (z.B. Straight, Flush, etc.)
	 * 
	 * Nach dem ersten Mischen wird das Guthaben um den aktuellen Einsatz verringert;
	 *  nach dem zweiten Mischen ist die Hand beendet und die Punkte für die aktuelle
	 *  Hand werden zu dem Guthaben addiert
	 */
	public void updateScoreBoard(int actualScore, String handStr, int nrOfShuffles) {
		
		updateCreditScoreLabel(actualScore, nrOfShuffles);
		updateResultLabel(actualScore, handStr, nrOfShuffles);
	}
	
	private void updateCreditScoreLabel(int actualScore, int nrOfShuffles) {

		int oldScore = Integer.parseInt(creditScoreLabel.getText());
		// neuer Punktestand = alter Punktestand abzügl. Einsatz plus Punkte für aktuelle Hand
		if(nrOfShuffles == 1)
			// 1. Runde: Einsatz wird von Guthaben abgezogen
			newCreditScore = oldScore - stake;		
		else if(nrOfShuffles == 2)
			// 2. Runde: aktuelle Punktzahl wird Guthaben zugeschrieben
			newCreditScore = oldScore + actualScore;
		
		creditScoreLabel.setText(Integer.toString(newCreditScore));
	}
	
	private void updateResultLabel(int actualScore, String handStr, int nrOfShuffles) {
		String output = "";
		// aktuelle Hand wird im Label angezeigt (inkl. Punktzahl wenn Punktzahl größer 0)
		if(nrOfShuffles == 2 && actualScore > 0 )
			output = handStr + ":   + " + Integer.toString(actualScore);
		else
			output = handStr;
		
		resultLabel.setText(output);
	}
	
	// diverse Getter und Setter
	
	public void setStake(int stake) {
		this.stake = stake;
	}
	
	public void setStakeLabelText(int stake) {
		stakeScoreLabel.setText(Integer.toString(stake));
	}
	
	public void resetScoreLabelTexts() {
		creditScoreLabel.setText(Integer.toString(credits));
		stakeScoreLabel.setText(Integer.toString(stake));
		resultLabel.setText("");
	}
	
	public JLabel getCreditScoreLabel() {
		return creditScoreLabel;
	}

	public void setCreditScoreLabel(JLabel creditScoreLabel) {
		this.creditScoreLabel = creditScoreLabel;
	}

	public JLabel getStakeScoreLabel() {
		return stakeScoreLabel;
	}

	public void setStakeScoreLabel(JLabel stakeScoreLabel) {
		this.stakeScoreLabel = stakeScoreLabel;
	}
	
	public int getCredits() {
		return credits;
	}
	
	public int getActualCredits() {
		return newCreditScore;
	}
	
	public void setStartScore(int credits, int stake) {
		this.credits = credits;
		this.stake = stake;
		newCreditScore = this.credits;
	}
	
	public String getResultLabelText() {
		return resultLabel.getText();
	}
	
}
