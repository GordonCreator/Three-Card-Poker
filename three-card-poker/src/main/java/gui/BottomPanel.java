package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/*
 * diese Klasse erstellt und aktualisiert das untere Panel 
 * hier werden der Spinner mit dem aktuellen Spiel-Einsatz
 * sowie der Button zum Mischen der Karten angezeigt
 */

public class BottomPanel extends JPanel {

	private JButton rulesBtn;
	private JButton highscoreBtn;
	
	private JLabel pickStakeLabel;
	private SpinnerNumberModel spinnerNumberModel;
	private JSpinner spinner;
	
	private JButton shuffleBtn;
	
	private Color fontColor;
	private Color bgColor;
	
	public BottomPanel(Color fontColor, Color bgColor) {
		
		this.fontColor = fontColor;
		this.bgColor = bgColor;

		// statt FlowLayout (im TopPanel) wird hier das GridBagLayout verwendet
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		highscoreBtn = new PButton("Highscores");
		rulesBtn = new PButton("Spielregeln");
		pickStakeLabel = new JLabel("Aktueller Einsatz:");
		pickStakeLabel.setForeground(fontColor);		
		
		// Default-SpinnerNumbermodel
		spinnerNumberModel = new SpinnerNumberModel(50, 20, 200, 10);
		spinner = new JSpinner(spinnerNumberModel);
		
		shuffleBtn = new PButton("Deal");
		
		gc.fill = GridBagConstraints.NONE;
		
		
		gc.gridx = 0;
		gc.insets = new Insets(0,0,10,20);
		gc.anchor = GridBagConstraints.LINE_END;
//		add(rulesBtn,gc);	// coming soon
		
		gc.gridx = 1;
		gc.insets = new Insets(0,0,10,70);
		gc.anchor = GridBagConstraints.LINE_END;
//		add(highscoreBtn,gc);	// coming soon	
		
		gc.gridx = 2;
		gc.insets = new Insets(0,0,10,25);
		add(pickStakeLabel,gc);
		
		gc.gridx = 3;
		gc.insets = new Insets(0,0,10,50);
		add(spinner,gc);
		
		gc.gridx = 4;
		gc.insets = new Insets(0,150,10,20);
		add(shuffleBtn,gc);
		
		shuffleBtn.setPreferredSize(new Dimension(100,30));

		setBackground(bgColor);
	}
	
	void addButtonClickedListener(ActionListener buttonClickedListener) {	
		shuffleBtn.addActionListener(buttonClickedListener);
	}

	public void disableStakeSpinner() {
		spinner.setEnabled(false);
	}
	
	public void enableStakeSpinner() {
		spinner.setEnabled(true);
	}

	public JSpinner getSpinner() {
		return spinner;
	}
	
	public void setSpinnerValue(int stake) {
		spinner.setValue(stake);
	}
	
	public int getStake() {
		return (int) spinner.getValue();
	}
	
	/*
	 * setzt die Werte für Einsatz, Min.einsatz, Max.einsatz analog der 
	 * Werte im Pokercontroller (relevant für den Fall, dass die Defaultwerte
	 * vom Entwickler angepasst werden sollen)
	 */
	public void setSpinnerNumberModel(SpinnerNumberModel spinnerNumberModel) {
		this.spinnerNumberModel = spinnerNumberModel;
		spinner.setModel(this.spinnerNumberModel);
	}
	
	public void disableShuffleButton() {
		shuffleBtn.setEnabled(false);
	}
	
	public JButton getShuffleBtn() {
		return shuffleBtn;
	}
	
	public void setShuffleButtonText(String text) {
		shuffleBtn.setText(text);
	}

	
}
