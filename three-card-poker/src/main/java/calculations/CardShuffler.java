package calculations;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * diese Klasse steuert die Rechenlogik zum Mischen der Karten
 * und ruft die Methoden der HandCalculator-Klasse auf
 */

public class CardShuffler {

	private HandCalculator handCalculator;
	
	// Anzahl der Karten (13 Ranks * 4 Farben = 52 Karten)
	private final int NR_OF_VALUES = Card.CARDVALUES.length;
	private final int NR_OF_COLORS = Card.CARDCOLORS.length;
	
	private final int NR_OF_CARDS = NR_OF_VALUES * NR_OF_COLORS;
	
	private Card DECK[] = new Card[NR_OF_CARDS];

	private ArrayList<Card> remainingDeck;
	
	private int credits;
	private int stake;

	// speichert die drei ermittelten Karten als Strings
	private Card board[] = new Card[3];
	
	
	public CardShuffler() {
		
		handCalculator = new HandCalculator();
		remainingDeck = new ArrayList<Card>();
		
		fillCardDeck();
	}
	
	
	
	// Auffüllen des Kartendecks mit den 52 Karten
	private void fillCardDeck() {
		
		int index = 0;
		
		for(int value = 0; value < NR_OF_VALUES; value++) {
			for(int color = 0; color < NR_OF_COLORS; color++) {
				DECK[index] = new Card(value, color);
				index++;
			}
		}
		
	}
	
	/*
	 * in der ersten Runde des Spiels werden drei Karten neu gemischt
	 */
	public Card[] shuffleAllCards() {

		// ArrayList mit allen Karten befüllen
		for(int card = 0; card < NR_OF_CARDS; card++) {
			remainingDeck.add(DECK[card]);
		}
		// boardCardNr steht jeweils für eine der drei Karten auf dem Board
		for(int boardCardNr = 0; boardCardNr < 3; boardCardNr++) {	
			getRandomCard(boardCardNr);
		}

		return board;
	}
	
	/*
	 * in der zweiten Runde des Spiels werden nur die Karten neu gemischt,
	 * die entsprechend markiert sind (wenn das Hold-Label auf "Draw" steht)
	 */
	public Card[] reShuffleCards(boolean hold[]) {

		for(int i = 0; i < 3; i++) {	
			// wenn die Karte neu gemischt(nicht gehalten) werden soll 
			if(hold[i] == false) {
				getRandomCard(i);
			}
			
		}
		
		return board;
	}
	
	
	/*
	 * eine zufällige Zahl wird ermittelt (anfangs eine Zahl zwischen 0 und 51)
	 * analog dieser Zahl wird dem Board-Array die entsprechende Karte zugeteilt
	 * Damit keine Karte mehrfach gezogen werden kann, wird anschließend diese 
	 * Karte aus dem remainingDeck entfernt )
	 */
	public void getRandomCard(int boardCardNr) {
		
		int nr = 0;			// temporäre Variable für die Zufallszahl
		int size = 0;		// Größe der ArrayList
		
		try {
			size = remainingDeck.size();			
			nr = (int) (Math.random() * size);
			
			board[boardCardNr] = remainingDeck.get(nr);
			
			// gezogene Karte aus der ArrayList löschen
			remainingDeck.remove(nr);
		
		// falls die Zufallszahl außerhalb des Arrays liegt	
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		
		
	}

	public int calculateHandStrength(Card[] board) {
		
		return handCalculator.calculateHandStrength(board);
	}
	
	public Card[] getCardBoard() {
		return handCalculator.getCardBoard();
	}
	
	public void setBoard(Card[] board) {
		handCalculator.setBoard(board);
	}

	public String getHandStr() {
		return handCalculator.getHandStr();
	}
	
	public void setHandStr(String handStr) {
		handCalculator.setHandStr(handStr);
	}

	public void clearRemainingDeck() {
		remainingDeck.clear();
	}
	
	public void resetBoardCards() {
		handCalculator.resetCardColors();
		handCalculator.resetCardRanks();
	}

	public void setStartScore(int credits, int stake) {
		this.stake = stake;
		this.credits = credits;
	}
	
}
