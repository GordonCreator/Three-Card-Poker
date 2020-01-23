package calculations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
 * diese Klasse ermittelt anhand der übergebenen drei Karten die Stärke der Hand
 * Dafür werden die Farbe, der Wert sowie die Rangfolge der übergebenen Karten
 * untersucht und anschließend die Punktzahl für die Hand zurückgegeben
 */

public class HandCalculator {

	private Card board[] = new Card[3];
	
	private String handStr;
	
	private String[] cardColors = new String[3];		// z.B. d(iamond) oder h(earts)
	private int[] cardRanks = new int[3];				// Rangfolge der Karten (f�r die Straight bzw. Stra�e)
	
	public int calculateHandStrength(Card[] board) {
		
		// Kartenfarbe entspricht der zweiten Stelle im board-String
		cardColors[0] = board[0].getCardColorAsString();
		cardColors[1] = board[1].getCardColorAsString();
		cardColors[2] = board[2].getCardColorAsString();		
		
		cardRanks[0] = board[0].getCardRank();
		cardRanks[1] = board[1].getCardRank();
		cardRanks[2] = board[2].getCardRank();
		
		int actualScore = checkHandCombinations();
		
		return actualScore;
	}
	
	public int checkHandCombinations() {
		
		// Flag-Variablen für Straße und Flush (relevant für die Prüfung auf Straight Flush)
		boolean straight = false;
		boolean flush = false;
		
		// Karten nach Rangfolge aufsteigend sortieren (zum Prüfen der Straße)
		ArrayList<Integer> cards = new ArrayList<Integer>();
		cards.add(cardRanks[0]);
		cards.add(cardRanks[1]);
		cards.add(cardRanks[2]);
		
		Collections.sort(cards);
		
		// Standardausgabe wenn keine zählbare Hand vorliegt
		handStr = "No Hand";
		
		// Flush prüfen (alle drei Karten haben die gleiche Farbe)
		if(cardColors[0] == cardColors[1] && cardColors[0] == cardColors[2]) 
			flush = true;
		
		// Straight (Straße) prüfen (drei aufeinanderfolgende Karten)
		if((cards.get(0) + 2) == (cards.get(1) + 1) && (cards.get(0) + 2) == (cards.get(2)))
			straight = true;
		
		/*
		 * das Ass kann einen cardRank von sowohl 14 als auch 1 haben
		 * wenn cardRank2 = 14 ist (d.h. die höchste Karte ist ein Ass), dann wird geprüft,  
		 * ob [Ass, Zwei, Drei] vorliegt, da dies ebenfalls eine Straße ist
		 */
		if(cards.get(2) == 14)
			if(cards.get(0) == 2 && cards.get(1) == 3)
				straight = true;
		
		// Straight Flush prüfen (liegt vor, wenn Straight und Flush gleichzeitig vorliegen)
		if(straight && flush) {
			// wenn die höchste Karte in der ArrayList ein Ass ist [cards.get(2)] und die
			//  zweithöchste ein König [cards.get(1)], dann liegt ein Royal Flush vor
			if(cards.get(2) == 14 && cards.get(1) == 13) {
				handStr = "Royal Flush!!!";
				return 750;
			}
			// ansonsten nur ein normaler Straight Flush
			else {
				handStr = "Straight Flush";
				return 60;
			}
		}
		
		// bei Flush oder Straight (kein Straight Flush) entsprechende Punkte zurückgeben
		if(straight && !flush) {
			handStr = "Straight";
			return 5;
		}
		
		if(flush && !straight) {
			handStr = "Flush";
			return 4;
		}
		
		// Drilling prüfen (drei gleiche Karten)
		if(cardRanks[0] == cardRanks[1] && cardRanks[0] == cardRanks[2]) {
			handStr = "3 of a Kind";
			return 50;
		}
		
		// Paar prüfen (zwei gleiche Karten)
		if(cardRanks[0] == cardRanks[1] || cardRanks[0] == cardRanks[2] || cardRanks[1] == cardRanks[2]) {
			handStr = "Pair";
			return 1;
		}
		
		// prüfen ob drei verschiedene Face Cards (von Bube bis Ass) vorliegen
		//  (gilt nicht für Straßen und Flushes, da diese bereits zuvor ausgewertet wurden)
		if((cardRanks[0] > 10) && (cardRanks[1] > 10) && (cardRanks[2] > 10)) {
			handStr = "3 Face Cards";
			return 4;
		}
	
		return 0;
	}
	
	public Card[] getCardBoard() {
		return board;
	}
	
	public void setBoard(Card[] board) {
		this.board = board;
	}

	public String getHandStr() {
		return handStr;
	}
	
	public void setHandStr(String handStr) {
		this.handStr = handStr;
	}

	public void resetCardColors() {
		cardColors[0] = null;
		cardColors[1] = null;
		cardColors[2] = null;
	}

	public void resetCardRanks() {
		cardRanks[0] = 0;
		cardRanks[1] = 0;
		cardRanks[2] = 0;
	}
}
