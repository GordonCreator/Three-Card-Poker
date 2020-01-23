package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

import calculations.Card;
import calculations.CardShuffler;
import gui.HandFinishedDialog;
import gui.PokerView;


/*
 * diese Klasse steuert die Funktionen der grafischen Oberfläche und
 * der Rechenlogik zum Mischen der Karten und Ermitteln der Handstärke
 * 
 * Listener werden erstellt und hinzugefügt
 * Startguthaben und -einsatz werden festgelegt 
 * 
 * allgemeine Funktionen des ButtonListeners:
 *  1. Runde: alle Karten werden neu gemischt
 *  2. Runde: vom Spieler abgewählte/verdeckte Karten werden neu gemischt
 *  die Stärke der Hand wird berechnet und angezeigt
 *  es wird geprüft, ob ein Game over vorliegt
 *  die Infos zu den gemischten Karten werden zurückgesetzt 
 *  der Spieler startet die nächste Runde mit einem weiteren Klick auf den Deal-Button
 * 
 */

public class PokerController {

	private PokerView view;
	private CardShuffler cardShuffler;
	
	private JLabel boardLabel[];
	// Darstellung der gemischten Karten (z.B. [As,Qd,Kh])
	private Card boardCards[];
	
	// Startwerte für Spielerpunkte
	private final int CREDITS = 1000;
	private final int STAKE = 50;
	
	private int credits = CREDITS;
	private int actualScore;
	private int stake = STAKE;
	
	// Werte für den Spieleinsatz via Spinner
	private SpinnerNumberModel spinnerNumberModel;
	private int minStake = 20;
	private int maxStake = 200;
	private int stakeStepSize = 10;
	
	// Flag-Variable; true sobald zum ersten Mal Karten gemischt wurden
	private boolean shuffleStarted = false;
	
	// gibt an, wie oft Karten in der aktuellen Hand bereits gemischt wurden
	private int nrOfShuffles = 0;
	
	
	public PokerController(PokerView view, CardShuffler cardShuffler) {
		
		this.view = view;
		this.cardShuffler = cardShuffler;
		
		passStartScore();
		
		view.addButtonClickedListener(new ButtonListener());
		view.addCardClickedListener(new CardListener());
		view.addExitListener(new ExitListener());
	}
	
	/*
	 * übergibt Start-Guthaben und Einsatz an GUI und HandCalculator
	 * legt Angaben über Mindest- und Maximal-Einsatz fest
	 */
	private void passStartScore() {
		
		cardShuffler.setStartScore(credits, stake);
		view.setStartScore(credits, stake);
		
		view.resetScoreLabelTexts();
		
		spinnerNumberModel = new SpinnerNumberModel(stake, minStake, maxStake, stakeStepSize);
		view.setSpinnerNumberModel(spinnerNumberModel);
	}
	
	
	/*
	 * gibt an, welche Aktionen beim Klicken des Deal/Draw-Buttons erfolgen
	 */
	class ButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			shuffleStarted = true;
			nrOfShuffles++;
			// 1. Runde
			if(nrOfShuffles == 1) {
				firstShuffle();
			// 2. Runde
			} else if(nrOfShuffles == 2) {
				secondShuffle();
			}

		}
		
		private void firstShuffle() {

			// aktueller Punktestand
			credits = view.getCredits();

			// Einsatz im Label anzeigen
			stake = view.updateStake(credits);

			// Karten mischen und im String-Array speichern
			boardCards = cardShuffler.shuffleAllCards();
			
			shuffleAction();
			
			view.setShuffleButtonText("Draw");
			view.disableStakeSpinner();
		}
		
		private void secondShuffle() {

			// Array mit Info darüber, welche Karten gehalten/neu gemischt werden sollen
			boolean holdCards[] = view.getHoldCards();
			
			// abgewählte Karten neu mischen und Array aktualisieren
			boardCards = cardShuffler.reShuffleCards(holdCards);
			
			shuffleAction();
			
			view.setShuffleButtonText("Deal");
			view.resetAllHoldLabelTexts("Hold");
			view.resetHoldArray(boardCards);
			
			cardShuffler.clearRemainingDeck();
			
			// wenn das Spiel nicht Game over ist, kann der Spieler weitermachen
			//  und den Einsatz für die nächste Hand bei Bedarf anpassen
			if(checkForGameOver() == false) {
				view.enableStakeSpinner();
			}
			
			if(shuffleStarted) {
				String str = view.getResultLabelText();
				new HandFinishedDialog(str);
			}
				
			nrOfShuffles = 0;
			shuffleStarted = false;
		}
		
		private void shuffleAction() {
			
			// gibt JLabel-Array mit den 3 aufgedeckten Board-Karten zurück
			boardLabel = view.getCardBoard();
			
			System.out.println(Arrays.toString(boardCards));
			
			// Karten aufdecken (analog des gespeicherten String-Arrays)
			view.setCardPanels(boardCards);
			
			int score = cardShuffler.calculateHandStrength(boardCards);
			// Punktzahl der aktuellen Hand (Punkte * Einsatz)
			actualScore = score * stake;
			
			// textuelle Bezeichnung der Hand
			String handStr = cardShuffler.getHandStr();
			
			view.updateScoreBoard(actualScore, handStr, nrOfShuffles);
		}
		
		private boolean checkForGameOver() {
			// hat der Spieler kein Guthaben mehr, ist das Spiel vorbei
			if(view.getActualCredits() <= 0) {
				System.out.println("Game over!");
				shuffleStarted = false;
				int selectedOption = JOptionPane.showConfirmDialog(null, "Game Over! "
						+ "Möchtest du ein neues Spiel starten?");
				if((selectedOption) == JOptionPane.YES_OPTION) {
					
					resetDefaults();
					
				} else {
					view.disableShuffleButton();
					view.disableStakeSpinner();
				}
				return true;
			} else {
				return false;
			}
			
			
		}
		
		private void resetDefaults() {
			
			resetBoardLabelArray();
			
			credits = CREDITS;
			stake = STAKE;
			actualScore = 0;
			
			resetViewComponents();
			resetCardShufflerComponents();
		}
		
		/*
		 * temporäre Arrays mit Infos zu den Karten sowie Guthaben
		 * und Einsatz werden zurückgesetzt
		 */
		private void resetViewComponents() {
			view.resetTempIconArray();
			
			view.enableStakeSpinner();
			view.setSpinnerValue(stake);
			
			view.setStartScore(credits, stake);
			view.resetScoreLabelTexts();	
			
			String cardBackSide = view.getCardBackside();
			view.resetCardPanels(boardCards, cardBackSide);
		}
		
		private void resetCardShufflerComponents() {
			
			cardShuffler.resetBoardCards();
			
			cardShuffler.setBoard(boardCards);
			cardShuffler.setStartScore(credits, stake);			
			// Infoanzeige für den Spieler: String-Darstellung der Hand wird geleert
			cardShuffler.setHandStr("");
		}
		
		private void resetBoardLabelArray() {
			boardLabel[0] = null;
			boardLabel[1] = null;
			boardLabel[2] = null;
		}
	}
	
	/*
	 * gibt an was passiert, wenn eine Karte angeklickt wird
	 */
	class CardListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent ev) {
			// Abbruch der Methode wenn die Karten noch nicht gemischt wurden
			if(!shuffleStarted)
				return;
			// Abbruch der Methode wenn die Karten schon min. zweimal gemischt wurden
			if(nrOfShuffles >= 2) {
				return;
			}
			view.cardClickedAction(ev,boardLabel, boardCards);
		}
		
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
	}
	/*
	 * bei Beenden des Spiels erfolgt eine Bestätigungsabfrage mit
	 * der Info zum aktuellem Punktestand
	 */
	class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			int selectedOption = JOptionPane.showConfirmDialog(null, "Du hast " + 
					view.getActualCredits() +  " Punkte.\nSoll das Spiel beendet werden?");
				
			if((selectedOption) == JOptionPane.YES_OPTION)
				System.exit(0);
		}
	}
	
}




