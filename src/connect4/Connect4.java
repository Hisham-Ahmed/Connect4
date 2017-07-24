package connect4;

public class Connect4 {

	private Board board;
	private PlayerSymbol player1, player2;
	public Connect4() {
		board = new Board(4);
		player1 = new Player_Human('1');
		player2 = new Player_AlphaBeta('2');
	}
	public void play() {
		ObjectCounter ctr1 = new ObjectCounter();
		ObjectCounter ctr2 = new ObjectCounter();
		board.printBoard();
		int prev_ctr = 0;
                int p1_moves = 0;
		int p2_moves = 0;
		long p1_time = 0;
		long p2_time = 0;
		while(true) {			
			if (board.isWon('2')) {
				System.out.println("AI(Computer) wins!");
				break;
			}
			prev_ctr = ctr1.get();
			long startTime = System.currentTimeMillis();
			board = player1.makeMove(board, ctr1);			
			long endTime = System.currentTimeMillis();
			p1_moves++;
			p1_time += (endTime - startTime);
			board.printBoard();
		//	System.out.println("Player 1 created " + (ctr1.get()-prev_ctr) + " nodes");
		//	System.out.println("Player 1 took " + (endTime-startTime) + " milliseconds to move");
			if (board.isWon('1')) {
				System.out.println("Player 1 wins!");
				break;
			}
			prev_ctr = ctr2.get();
			startTime = System.currentTimeMillis();
			board = player2.makeMove(board,ctr2);			
			endTime = System.currentTimeMillis();
			p2_moves++;
			p2_time += (endTime - startTime);
			board.printBoard();
		//	System.out.println("AI(Computer) created " + (ctr2.get()-prev_ctr) + " nodes");
		//	System.out.println("AI(Computer) took " + (endTime-startTime) + " milliseconds to move");
		}
		System.out.println("Player 1: Average nodes generated/move: " + (double)ctr1.get()/p1_moves);
                System.out.println("Player 1: Average time/move: " + (double)p1_time/p1_moves);
		System.out.println("AI(Computer): Average nodes generated/move: " + (double)ctr2.get()/p2_moves); 
                System.out.println("AI(Computer): Average time/move: " + (double)p2_time/p2_moves);
	}
	public static void main(String[] args) {
		Connect4 c4 = new Connect4();
		c4.play();
	}
}