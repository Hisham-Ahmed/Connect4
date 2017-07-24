package connect4;
import java.util.ArrayList;

public class B_Node {
	private Board board;
	private int depth;
	private char symbol_to_play;
	private char symbol_opponent;
	private char head_symbol;
	private char head_opponent;	
	private boolean pruning_enabled;
	private ObjectCounter ctr;

	public B_Node(Board board, int depth, char symbol, char head_symbol, boolean pruning_enabled, ObjectCounter ctr) {
		this.board = board;
		this.depth = depth;
		this.head_symbol = head_symbol;
		if (head_symbol == '1')
			head_opponent = '2';
		else
			head_opponent = '1';
		this.symbol_to_play = symbol;
		if(symbol_to_play == '1') {
			symbol_opponent = '2';
		} else {
			symbol_opponent = '1';
		}
		this.pruning_enabled = pruning_enabled;
		this.ctr = ctr;
		ctr.increment();
	}
	public int evaluate(int current_parent_evaluation) {
		if (board.isWon(head_symbol)) {
			return Integer.MAX_VALUE;
		}
		if (board.isWon(head_opponent)) {
			return Integer.MIN_VALUE;
		}
		int threshold = 5;
		if (depth == threshold) {
			return heuristicEvaluation();
		}
		ArrayList<B_Node> children = getChild();		
		
		int evaluation = 0;
		// "Max"
		if (depth % 2 == 0) {
			evaluation = Integer.MIN_VALUE;
			for (B_Node child : children) {
				int value = child.evaluate(evaluation);
				if (value > evaluation) {
					evaluation = value;
				} 
				if (pruning_enabled && evaluation > current_parent_evaluation)
					break;
			}
		} else { // "Min"
			evaluation = Integer.MAX_VALUE;
			for (B_Node child : children) {
				int value = child.evaluate(evaluation);
				if (value < evaluation) {
					evaluation = value;
				}
				if (pruning_enabled && evaluation < current_parent_evaluation)
					break;
			}
		}		
		return evaluation;
	}
	//  Heuristic: "three-in-a-row" and "two-in-a-row"!
	private int heuristicEvaluation() {
		if (board.isWon(head_symbol)) {
			return Integer.MAX_VALUE;
		}
		if (board.isWon(head_opponent)) {
			return Integer.MIN_VALUE;
		}
		int threepos = 100*board.SymInARow(head_symbol, head_opponent,3);
		int threeneg = -100*board.SymInARow(head_opponent, head_symbol,3);
		int twopos = 10*board.SymInARow(head_symbol, head_opponent,2);
		int twoneg =  -10*board.SymInARow(head_opponent, head_symbol,2);
		
		return threepos+threeneg+twopos+twoneg;
	}
	public ArrayList<B_Node> getChild() {
		ArrayList<B_Node> children = new ArrayList<B_Node>();
		for (int i = 0; i < 7; i++) {
			if (board.DropValidity(i) == 2) {
				Board child = board.copy();
				child.dropDisk(symbol_to_play, i);			
				B_Node childNode = new B_Node(child, depth+1,symbol_opponent, head_symbol, pruning_enabled, ctr);
				children.add(childNode);
			}
		}
		return children;
	}
	public Board getBoard() {		
		return board;
	}
}