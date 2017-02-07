package view;

/*
 * Name: Songzhe Zhu
 * Date: Feb 5 2017
 * Purpose: This is the second view of the TicTacToe game which is
 * 			a text field version.
 */
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.OurObserver;
import model.ComputerPlayer;
import model.TicTacToeGame;

@SuppressWarnings("serial")
public class TextFieldView extends JPanel implements OurObserver {
	private TicTacToeGame Game;
	private JButton GameButton = new JButton("Make the move");
	private JTextField Row = new JTextField("");
	private JTextField Col = new JTextField("");
	private JLabel row = new JLabel("row");
	private JLabel col = new JLabel("column");
	private JTextArea GridArea = new JTextArea(3, 3);
	private ComputerPlayer AI;
	private char[][] Grid = null;
	private String tmp = "";
	private int height = 0;
	private int width = 0;

	//Create a new game
	public TextFieldView(TicTacToeGame TicTacToeGame, int wide, int high) {
		this.Game = TicTacToeGame;
		this.width = wide;
		this.height = high;
		this.AI = Game.getComputerPlayer();
		initializeButtonPanel();
	}

	//Set the layout
	private void initializeButtonPanel() {
		// TODO Auto-generated method stub
		// Control has two labels, text for row and column, and a button
		JPanel Control = new JPanel();
		// Field has two lables and text for row and column
		JPanel Field = new JPanel();
		int size = Game.size();

		this.setLayout(null);
		Field.setLayout(new GridLayout(size, size, 5, 5));
		Field.setLocation(10, 5);
		Field.setSize(500, 100);
		Field.setBackground(Color.CYAN);
		Field.add(Row);
		Field.add(row);
		Field.add(Col);
		Field.add(col);

		Control.setSize(width - 20, height - 300);
		Control.setLocation(10, 5);
		Control.setBackground(Color.CYAN);

		ButtonListener aListener = new ButtonListener();
		GameButton.setSize(180, 50);
		GameButton.setLocation(200, 20);
		GameButton.setBackground(Color.CYAN);
		GameButton.setFont(new Font("Arial", Font.BOLD, 18));
		GameButton.addActionListener(aListener);

		Control.add(Field);
		Control.add(GameButton);
		this.add(Control);

		Grid = new char[size][size];
		for (int i = 0; i < size; i++) {
			tmp += " ";
			for (int j = 0; j < size; j++) {
				Grid[i][j] = '_';
				tmp += Grid[i][j] + "     ";
			}
			tmp += "\n";
		}

		GridArea.setLayout(new FlowLayout());
		GridArea.setText(tmp);
		GridArea.setSize(width - 100, height - 190);
		GridArea.setLocation(40, 70);
		GridArea.setFont(new Font("Courier", Font.BOLD, 36));
		this.setBackground(Color.CYAN);
		this.add(GridArea);
	}

	// Action when player click the button
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String tmprow = Row.getText();
			String tmpcol = Col.getText();
			int tmpRow = 0;
			int tmpCol = 0;
			try{
				tmpRow = Integer.parseInt(tmprow);
				tmpCol = Integer.parseInt(tmpcol);
			
			if(tmpRow >= 0 && tmpRow < 3 && tmpCol >= 0 && tmpCol < 3 && Grid[tmpRow][tmpCol] == '_'){
				Game.choose(tmpRow, tmpCol);
				updateField();
				if(Game.tied()){
					GameButton.setText("Tied");
					GameButton.setEnabled(false);
					updateField();
				}
				else if(Game.didWin('X')){
					GameButton.setText("X Wins");
					GameButton.setEnabled(false);
					updateField();
				}
				else{
					Point next = AI.desiredMove(Game);
					Game.choose(next.x, next.y);
					updateField();
					if(Game.didWin('O')){
						GameButton.setText("O Wins");
						updateField();
					}
				}
			}
			//else if(Grid[tmpRow][tmpCol]){
				//JOptionPane.showMessageDialog(null, "Move not available");
			//}
			else if(!(tmpRow >= 0) || !(tmpRow < 3) || !(tmpCol >= 0) || !(tmpCol < 3)){
				JOptionPane.showMessageDialog(null, "Invalid move");
			}
			else{
				JOptionPane.showMessageDialog(null, "Move not avaliable");
			}
		}catch (NumberFormatException notInt) {
			JOptionPane.showMessageDialog(null, "Invalid input");
		}
		}
	}

	// This method is called by OurObservable's notifyObservers()
	public void update() {
		// System.out.println("First");
		if (Game.maxMovesRemaining() == Game.size() * Game.size()) {
			// System.out.println("Test");
			resetField(true);
			Row.setText("");
			Col.setText("");
		}
		if (!Game.stillRunning()) {
			resetField(false);
		} else {
			updateField();
			GameButton.setText("Make the move");
		}
	}

	// update Field after one move
	private void updateField() {
		// TODO Auto-generated method stub
		// System.out.println("second");
		Grid = Game.getTicTacToeBoard();
		int size = Game.size();
		tmp = "";
		for (int i = 0; i < size; i++) {
			tmp += " ";
			for (int j = 0; j < size; j++) {
				tmp += Grid[i][j] + "    ";
			}
			tmp += "\n";
		}
		GridArea.setText(tmp);
		GridArea.setEnabled(false);
	}

	// reset Field when a new game start
	private void resetField(boolean bool) {
		// TODO Auto-generated method stub
		if (bool) {
			int size = Game.size();
			Grid = new char[size][size];
			tmp = "";
			for (int i = 0; i < size; i++) {
				tmp += " ";
				for (int j = 0; j < size; j++) {
					tmp += Grid[i][j] + " ";
				}
				tmp += "\n";
			}
			GridArea.setText(tmp);
		} else {
			if (Game.tied()) {
				GameButton.setText("Tied");
				updateField();
			} else if (Game.didWin('X')) {
				GameButton.setText("X Wins");
				updateField();
			} else {
				GameButton.setText("O Wins");
				updateField();
			}
		}
		GameButton.setEnabled(bool);
	}
}
