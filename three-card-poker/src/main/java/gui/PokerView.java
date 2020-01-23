package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import calculations.Card;


/*
 * diese Klasse steuert alle Eigenschaften der grafischen Oberfläche
 * und gibt Anweisungen an die einzelnen Panels weiter
 */

public class PokerView extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private Color bgColor;
	private Color textColor;

	private TopPanel topPanel;
	private CenterPanel centerPanel;
	private BottomPanel bottomPanel;

	// Array zum speichern der Karten die gehalten bzw. neu gemischt werden sollen
	private boolean holdCards[] = {true, true, true};
	
	private Icon cardBackSidePath;

	// speichert die aufgedeckten Kartenvorderseiten
	private Icon tempIcon[] = new Icon[3];
	
	public PokerView() {
		// Hintergrundfarbe und Schriftfarbe festlegen
		bgColor = new Color(70, 120, 190);
		textColor = Color.WHITE;

		createGUI();
		setUndecorated(true);
		
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	}
	
	
	public void createGUI() {
		
		setLayout(new BorderLayout());

		try {
			String cardBackSide = centerPanel.getCardBackside();
			cardBackSidePath = new ImageIcon(getClass().getResource("/symbols/" + cardBackSide + ".png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// erstellt die einzelnen Grafik-Panels
		topPanel = new TopPanel(textColor, bgColor);
		centerPanel = new CenterPanel(textColor, bgColor);
		bottomPanel = new BottomPanel(textColor, bgColor);
		
		add(topPanel,BorderLayout.NORTH);
		add(centerPanel,BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		
		setSpinnerChangeListener();
	}
	
	
	private void setSpinnerChangeListener() {

		// aktualisiert die Anzeige bei Änderungen des Spieleinsatzes
		JSpinner spinner = bottomPanel.getSpinner();
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
			
				int stake = (int)spinner.getValue();
				JLabel stakeLabel = topPanel.getStakeScoreLabel();
				
				stakeLabel.setText(Integer.toString(stake));
			}
		});
	}
	
	/*
	 * deckt bzw. verdeckt die angeklickte Karte und passt entsprechend
	 * das Hold-Label an
	 */
	public void cardClickedAction(MouseEvent ev,JLabel boardLabel[], Card[] boardCards) {
		
		JLabel clickedCardLabel = (JLabel)ev.getSource();
		
		int cardNr = 0;		
		
		 // Prüft welche Karte geklickt wurde (anhand des Boardlabel-Arrays)
		 // die Nummer der geklickten Karte wird der Variable "cardNr" zugewiesen
		for(int i = 0; i < 3; i++) {
			if(clickedCardLabel.equals(boardLabel[i])) {
				cardNr = i;
				// tempIcon speichert die drei Karten-Icons als Array
				//  sofern der Spieler keine Kartenrückseite (cardBackSide) angeklickt hat
				if(clickedCardLabel.getIcon() != cardBackSidePath) {
					tempIcon[i] = clickedCardLabel.getIcon();
				}
				break;
			}
		}
		
		// wird eine Karte angeklickt, die aktuell noch gehalten wird (und somit sichtbar ist),
		//  dann wird diese Karte mit dem Klick verdeckt (d.h. Rückseite aufgedeckt) und für 
		//  das Neumischen "vorgemerkt"
		if(holdCards[cardNr] == true) {
			clickedCardLabel.setIcon(cardBackSidePath);
			setSingleHoldLabelText(boardLabel[cardNr], "Draw");
			holdCards[cardNr] = false;
		}
		else if(holdCards[cardNr] == false) {
			// wenn tempIcon leer ist, wurden die Karten noch nicht gemischt; somit kann die 
			//  angeklickte Karte auch noch nicht aufgedeckt werden
			if(tempIcon != null) {
				clickedCardLabel.setIcon(tempIcon[cardNr]);
				holdCards[cardNr] = true;
			}	
			// Text des Holdlabels wird angepasst an die Sichtbarkeit der Karte/Rückseite
			setSingleHoldLabelText(boardLabel[cardNr], "Hold");
		}
	}
	
	public void addButtonClickedListener(ActionListener buttonClickedListener) {
		bottomPanel.addButtonClickedListener(buttonClickedListener);
	}
	
	public void addCardClickedListener(MouseListener cardClickedListener) {
		centerPanel.addCardClickedListener(cardClickedListener);
	}
	
	public void addExitListener(ActionListener exitListener) {
		topPanel.addExitListener(exitListener);
	}
	
	public int updateStake(int credits) {
		int stake = bottomPanel.getStake();
		
		// wenn der gewählte Einsatz größer ist als das vorhandene Guthaben, wird der Einsatz 
		//  auf die Höhe des verbliebenen Guthabens angepasst und der Labeltext für das 
		//  Guthaben entsprechend aktualisiert
		if(stake > getActualCredits()) {
			stake = getActualCredits();
			topPanel.setStakeLabelText(stake);
		}
		
		topPanel.setStake(stake);
		return stake;
	}
	
	public void resetHoldArray(Card[] boardCards) {
		holdCards[0] = true;
		holdCards[1] = true;
		holdCards[2] = true;
	}
	
	public void resetTempIconArray() {
		tempIcon[0] = null;
		tempIcon[1] = null;
		tempIcon[2] = null;
	}
	
	
	// diverse Getter und Setter für untergeordnete Panels
	
	public JPanel getTopPanel() {
		return topPanel;
	}
	
	public JPanel getCenterPanel() {
		return centerPanel;
	}
	
	public JPanel getBottomPanel() {
		return bottomPanel;
	}
	
	public Color getTextColor() {
		return textColor;
	}

	public void setCardPanels(Card boardStr[]) {
		centerPanel.setCardPanels(boardStr);
	}
	
	public void resetCardPanels(Card boardStr[], String path) {
		centerPanel.resetCardPanels(boardStr, path);
	}
	
	public void updateScoreBoard(int credits, String handStr, int nrOfShuffles) {
		topPanel.updateScoreBoard(credits, handStr, nrOfShuffles);
	}
	
	public int getCredits() {
		return topPanel.getCredits();
	}
	
	// gibt aktuelles Guthaben an "Beenden-Dialog"
	public int getActualCredits() {
		return topPanel.getActualCredits();
	}
	
	public JLabel[] getCardBoard() {
		return centerPanel.getCardBoard();
	}
	
	public void setShuffleButtonText(String text) {
		bottomPanel.setShuffleButtonText(text);
	}
	public void setSingleHoldLabelText(JLabel lbl, String text) {
		centerPanel.setSingleHoldLabelText(lbl,text);
	}
	
	public void resetAllHoldLabelTexts(String text) {
		centerPanel.resetAllHoldLabelTexts(text);
	}
	
	public void disableStakeSpinner() {
		bottomPanel.disableStakeSpinner();
	}
	
	public void enableStakeSpinner() {
		bottomPanel.enableStakeSpinner();
	}
	
	public void setSpinnerValue(int stake) {
		bottomPanel.setSpinnerValue(stake);
	}
	
	public void setSpinnerNumberModel(SpinnerNumberModel spinnerNumberModel) {
		bottomPanel.setSpinnerNumberModel(spinnerNumberModel);
	}
	
	public void disableShuffleButton() {
		bottomPanel.disableShuffleButton();
	}
	
	public boolean[] getHoldCards() {
		return holdCards;
	}
	
	public void setStartScore(int credits, int stake) {
		topPanel.setStartScore(credits, stake);
	}

	public void resetScoreLabelTexts() {
		topPanel.resetScoreLabelTexts();
	}
	
	public String getResultLabelText() {
		return topPanel.getResultLabelText();
	}
	
	public String getCardBackside() {
		return centerPanel.getCardBackside();
	}
}
