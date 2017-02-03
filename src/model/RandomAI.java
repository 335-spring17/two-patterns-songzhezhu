package model;

import java.awt.Point;
import java.util.Random;

/**
 * This strategy selects the first available move at random.  It is easy to beat
 * 
 * @throws IGotNowhereToGoException whenever asked for a move that is impossible to deliver
 * 
 * @author mercer
 */

// There is an intentional compile time error.  Implement this interface
public class RandomAI implements TicTacToeStrategy {
	private static Random AI;
	public RandomAI(){
		AI = new Random();
	}
  // Randomly find an open spot while ignoring possible wins and stops.
  // This should be easy to beat as a human. 

  @Override
  public Point desiredMove(TicTacToeGame theGame) {
	  while(true){
		  if(theGame.maxMovesRemaining() == 0)
			  throw new IGotNowhereToGoException("Full");
		  int row = AI.nextInt(3);
		  int colm = AI.nextInt(theGame.size());
		  if(theGame.available(row, colm)){
			  return new Point(row,colm);
		  }
	  }
  }
}