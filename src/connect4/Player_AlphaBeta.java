package connect4;
import java.util.ArrayList;

public class Player_AlphaBeta extends PlayerSymbol {
	public Player_AlphaBeta(char symbol) {
		super(symbol);
	}
	public Board makeMove(Board board, ObjectCounter ctr) {
		B_Node bn = new B_Node(board, 3, symbol, symbol, true, ctr);
		ArrayList<B_Node> nodes = bn.getChild();
		int maximum_eval = Integer.MIN_VALUE;
		int max_index = 0;
		for (int i = 0; i < nodes.size(); i++) {
			int evaluation = nodes.get(i).evaluate(Integer.MIN_VALUE);
			if (evaluation > maximum_eval) {
				maximum_eval = evaluation;
				max_index = i;
			}
		}
		board = nodes.get(max_index).getBoard();
		return board;
	}
}