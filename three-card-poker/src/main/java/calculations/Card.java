package calculations;

public class Card {

	private int cardColor;
	private int cardValue;
	private int cardRank;
	
	private String cardString;
	
	// Variablen sind statisch, damit diese nicht bei jedem Aufruf neu erstellt werden
	public static final String CARDVALUES[] = {"2","3","4","5","6",
			"7","8","9","10","Jack","Queen","King","Ace"};
	public static final String CARDCOLORS[] = {"Clubs", "Diamonds","Hearts","Spades"};

	
	public Card(int cardValue, int cardColor) {

		this.cardValue = cardValue;
		this.cardColor = cardColor;
		
		cardRank = calculateCardRank();
		cardString = buildCardString(); 
	}
	
	public String toString() {
		String value = CARDVALUES[this.cardValue];
		String color = CARDCOLORS[this.cardColor];
		
		String text = cardString;
		
		return text;
	}
	
	/*
	 * verkürzte String-Darstellung der Karte (z.B. Ad für Ace of Diamonds)
	 */
	public String buildCardString() {
		
		String value = getValueAsString();
		String color = getCardColorAsString();
		
		String cardStr = value + color;
		
		return cardStr;
	}

	/*
	 * gibt den jeweiligen Rang der Karte als String zurück
	 */
	public String getValueAsString() {
		
		String cardValueStr = null;
		
		switch(cardValue) {
			case 0:		cardValueStr = "2";		break;
			case 1:		cardValueStr = "3";		break;
			case 2:		cardValueStr = "4";		break;
			case 3:		cardValueStr = "5";		break;
			case 4:		cardValueStr = "6";		break;
			case 5:		cardValueStr = "7";		break;
			case 6:		cardValueStr = "8";		break;
			case 7:		cardValueStr = "9";		break;
			case 8:		cardValueStr = "T";		break;
			case 9:		cardValueStr = "J";		break;
			case 10:	cardValueStr = "Q";		break;
			case 11:	cardValueStr = "K";		break;
			case 12:	cardValueStr = "A";		break;
			default:	cardValueStr = null;	break;
		}
		
		return cardValueStr;
	}
	
	/*
	 * gibt die jeweilige Farbe der Karte als String zurück
	 */
	public String getCardColorAsString() {
		
		String cardColorStr = null;
		
		switch(cardColor) {
			case 0:		cardColorStr = "c";		break;
			case 1:		cardColorStr = "d";		break;
			case 2:		cardColorStr = "h";		break;
			case 3:		cardColorStr = "s";		break;
			default:	cardColorStr = null;	break;
		}
		
		return cardColorStr;
	}
		
	
	private int calculateCardRank() {
		
		String value = CARDVALUES[cardValue];
		
		switch(value) {
			case "2":		cardRank = 2;		break;
			case "3":		cardRank = 3;		break;
			case "4":		cardRank = 4;		break;
			case "5":		cardRank = 5;		break;
			case "6":		cardRank = 6;		break;
			case "7":		cardRank = 7;		break;
			case "8":		cardRank = 8;		break;
			case "9":		cardRank = 9;		break;
			case "10":		cardRank = 10;		break;
			case "Jack":	cardRank = 11;		break;
			case "Queen":	cardRank = 12;		break;
			case "King":	cardRank = 13;		break;
			case "Ace":		cardRank = 14;		break;
			// Ass kann im Sonderfall auch cardRank = 1 haben (siehe HandCalculator -> checkHandCombinations)
			default:		cardRank = 0;		break;
		}
		
		return cardRank;
	}
	
	public int getCardRank() {
		return cardRank;
	}
	
	
	/*
	 * String-Repräsentation der jeweiligen Karte 
	 * (bestehend aus Wert und Farbe)
	 */
	public String getCardAsString() {
		return cardString;
	}


	public int getCardValue() {
		return cardValue;
	}
	
	public int getCardColor() {
		return cardColor;
	}
	
}
