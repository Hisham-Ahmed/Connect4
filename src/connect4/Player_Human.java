package connect4;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Player_Human extends PlayerSymbol {

	public Player_Human(char symbol) {
		super(symbol);
	}
	public Board makeMove(Board board, ObjectCounter ctr) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));		
		while (true) {
			System.out.print("Enter Next Move (1-6): ");
			try {
				int column = Integer.parseInt(br.readLine());
				column--;
				int validity = board.DropValidity(column);
				if (validity == 0) {
					System.out.println("Invalid Column!");
					continue;
				}else if (validity == 1){
                                    System.out.println("Game Draw!");
                                    break;
                                }
				board.dropDisk(this.symbol, column);
				break;
			} catch(Exception e) {
				e.printStackTrace();
                                System.out.println("Error!");
			}
		}
		return board;
	}
}

abstract class PlayerSymbol {
	protected char symbol;
	protected char symbol_opp;
	public PlayerSymbol(char symbol) {
		this.symbol = symbol;
		if (this.symbol == '1')
			symbol_opp = '2';
		else 
			symbol_opp = '1';
	}
	public abstract Board makeMove(Board board, ObjectCounter ctr);
}