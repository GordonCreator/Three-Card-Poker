package de.poker;

import calculations.CardShuffler;
import control.PokerController;
import gui.PokerView;

public class PokerMain {

	public static void main(String[] args) {
		
		PokerView view = new PokerView();
		CardShuffler cardShuffler = new CardShuffler();
		PokerController controller = new PokerController(view, cardShuffler);
	}

}