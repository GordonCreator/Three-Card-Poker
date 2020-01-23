package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import calculations.Card;

/*
 * diese Klasse erstellt und aktualisiert das mittlere Panel 
 * hier werden die Karten-Icons sowie die Auszahlungsstruktur
 * angezeigt
 */

public class CenterPanel extends JPanel {
	
	private CardPanel[] cardPanels = new CardPanel[3];
	
	private JPanel payoutsPanel;
	
	private Color fontColor;
	private Color bgColor;
	
	
	public CenterPanel(Color fontColor, Color bgColor) {
		
		this.fontColor = fontColor;
		this.bgColor = bgColor;
		
		setLayout(new FlowLayout(0,35,25));
		
		cardPanels[0] = new CardPanel();
		cardPanels[1] = new CardPanel();
		cardPanels[2] = new CardPanel();
		
		add(cardPanels[0]);
		add(cardPanels[1]);
		add(cardPanels[2]);
		
		add(createPayoutsTable());
		
		setBackground(bgColor);
	}
	
	/*
	 * temporäre Lösung zur Anzeige der Auszahlungsstruktur
	 */
	private JPanel createPayoutsTable(){
		
		payoutsPanel = new JPanel(new GridLayout(0,1,0,0));
		
		JLabel header = new JLabel("Payouts");
		JLabel zeile1 = new JLabel("Royal Flush............750");
		JLabel zeile2 = new JLabel("Straight Flush..........60");
		JLabel zeile3 = new JLabel("Three of a Kind.......50");
		JLabel zeile4 = new JLabel("Straight.......................5");
		JLabel zeile5 = new JLabel("Flush............................4");
		JLabel zeile6 = new JLabel("Three Face Cards.....4");
		JLabel zeile7 = new JLabel("Pair...............................1");
		
		JLabel labels[] = {header,zeile1,zeile2,zeile3,
			zeile4,zeile5,zeile6,zeile7};
		
		for(JLabel lbl:labels) {
			payoutsPanel.add(lbl);
			lbl.setForeground(fontColor);
		}
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setFont(new Font("Default", Font.BOLD, 15));

		payoutsPanel.setBackground(bgColor);
		payoutsPanel.setPreferredSize(new Dimension(130,220));
		
		return payoutsPanel;
	}
	
	void addCardClickedListener(MouseListener cardClickedListener) {
		cardPanels[0].getCardLabel().addMouseListener(cardClickedListener);
		cardPanels[1].getCardLabel().addMouseListener(cardClickedListener);
		cardPanels[2].getCardLabel().addMouseListener(cardClickedListener);
	}
	
	/*
	 * Karten-Icons werden aktualisiert anhand des übergebenen String-Arrays
	 */
	public void setCardPanels(Card board[]) {
		
		// String-Repräsentation der jeweiligen Karte
		// Damit wird auf den entsprechenden Pfad des Kartensymbols zugegriffen
		String card0 = board[0].getCardAsString();
		String card1 = board[1].getCardAsString();
		String card2 = board[2].getCardAsString();

		String boardStr[] = {card0, card1, card2};
		
		changeCardPanels(boardStr);
	}
	
	/*
	 * Karten-Icons werden nach dem GameOver aktualisiert anhand des übergebenen Strings
	 *  Der String repräsentiert die Rückseite der Karte
	 */
	public void resetCardPanels(Card board[], String path) {
		
		String boardStr[] = {path, path, path};
		
		changeCardPanels(boardStr);
	}
	
	private void changeCardPanels(String[] boardStr) {
		
		try {
			cardPanels[0].getCardLabel().setIcon(new ImageIcon(getClass().
								getResource("/symbols/" + boardStr[0] +".png")));
			cardPanels[1].getCardLabel().setIcon(new ImageIcon(getClass().
								getResource("/symbols/" + boardStr[1] +".png")));
			cardPanels[2].getCardLabel().setIcon(new ImageIcon(getClass().
								getResource("/symbols/" + boardStr[2] +".png")));
		} catch (Exception ex) {
			System.out.println("Fehler beim Erstellen der Karten");
		}
		
	}
	
	
	public JLabel[] getCardBoard() {
		
		JLabel board[] = new JLabel[3];
		board[0] = cardPanels[0].getCardLabel();
		board[1] = cardPanels[1].getCardLabel();
		board[2] = cardPanels[2].getCardLabel();
		
		return board;
	}
	
	/*
	 * setzt für genau eine Karte den Text des darüberliegenden Labels
	 *  (je nachdem, ob die Karte gehalten oder neu gemischt werden soll)
	 */
	public void setSingleHoldLabelText(JLabel lbl, String text) {
		if(lbl.equals(cardPanels[0].getCardLabel()))
			cardPanels[0].setHoldLabelText(text);
		else if(lbl.equals(cardPanels[1].getCardLabel()))
			cardPanels[1].setHoldLabelText(text);
		else if(lbl.equals(cardPanels[2].getCardLabel()))
			cardPanels[2].setHoldLabelText(text);	
	}
	
	/*
	 * setzt für alle drei Karten den Text der darüberliegenden Labels
	 */
	public void resetAllHoldLabelTexts(String text) {
		cardPanels[0].setHoldLabelText(text);
		cardPanels[1].setHoldLabelText(text);
		cardPanels[2].setHoldLabelText(text);
	}
	
	// Dateiname der Kartenrückseite
	public static String getCardBackside() {
		return CardPanel.getCardBackSide();
	}
	
}
