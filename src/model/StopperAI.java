package model;

import java.awt.Point;
import java.util.Random;

/**
 * This TTT strategy tries to prevent the opponent from winning by checking for
 * a space where the opponent is about to win. If none found, it randomly picks
 * a place to win, which an sometimes be a win even if not really trying.
 * 
 * @author mercer
 */
public class StopperAI implements TicTacToeStrategy {
	private static Random AI;

	public StopperAI() {
		AI = new Random();
	}

	@Override
	public Point desiredMove(TicTacToeGame theGame) {
		char[][] GameBoard = theGame.getTicTacToeBoard();
		char PlayerChar = theGame.getCurrentPlayerChar();
		char Opponent = 'O';
		if (PlayerChar == 'O') {
			Opponent = 'X';
		}
		// First look to block an opponent win
		Point step = this.NextStep(Opponent, GameBoard);
		if (step != null) {
			return step;
		}
		// If the AI can not block, look for a win
		step = this.NextStep(PlayerChar, GameBoard);
		if (step != null) {
			return step;
		}

		// If no block or win is possible, pick a move from those still
		// available
		return RandomStep(theGame);
	}

	// help method to make the next random step
	private Point RandomStep(TicTacToeGame theGame) {
		// TODO Auto-generated method stub
		boolean game = false;
		while (!game) {
			if (theGame.maxMovesRemaining() == 0)
				throw new IGotNowhereToGoException("Full");
			int row = AI.nextInt(3);
			int colm = AI.nextInt(theGame.size());
			if (theGame.available(row, colm)) {
				game = true;
				return new Point(row, colm);
			}
		}
		return null;
	}

	// help method to make the step to block or win the game
	private Point NextStep(char opponent, char[][] gameBoard) {
		// TODO Auto-generated method stub
		char empty = '_';
		// figure out the steps in the same row
		for (int r = 0; r < gameBoard[0].length; r++) {
			if (gameBoard[r][0] == opponent && gameBoard[r][1] == opponent && gameBoard[r][2] == empty)
				return new Point(r, 2);
			if (gameBoard[r][0] == opponent && gameBoard[r][2] == opponent && gameBoard[r][1] == empty)
				return new Point(r, 1);
			if (gameBoard[r][1] == opponent && gameBoard[r][2] == opponent && gameBoard[r][0] == empty)
				return new Point(r, 0);
		}
		// figure out the steps in the same column
		for (int c = 0; c < gameBoard.length; c++) {
			if (gameBoard[0][c] == opponent && gameBoard[1][c] == opponent && gameBoard[2][c] == empty)
				return new Point(2, c);
			if (gameBoard[1][c] == opponent && gameBoard[2][c] == opponent && gameBoard[0][c] == empty)
				return new Point(0, c);
			if (gameBoard[0][c] == opponent && gameBoard[2][c] == opponent && gameBoard[1][c] == empty)
				return new Point(1, c);
		}
		// figure out the steps from upper right to lower left
		if (gameBoard[0][2] == opponent && gameBoard[2][0] == opponent && gameBoard[1][1] == empty)
			return new Point(1, 1);
		if (gameBoard[2][0] == opponent && gameBoard[1][1] == opponent && gameBoard[0][2] == empty)
			return new Point(0, 2);
		if (gameBoard[1][1] == opponent && gameBoard[0][2] == opponent && gameBoard[2][0] == empty)
			return new Point(2, 0);
		// figure out the steps from upper left to lower right
		if (gameBoard[0][0] == opponent && gameBoard[2][2] == opponent && gameBoard[1][1] == empty)
			return new Point(1, 1);
		if (gameBoard[2][2] == opponent && gameBoard[1][1] == opponent && gameBoard[0][0] == empty)
			return new Point(0, 0);
		if (gameBoard[1][1] == opponent && gameBoard[0][0] == opponent && gameBoard[2][2] == empty)
			return new Point(2, 2);
		return null;
	}

}