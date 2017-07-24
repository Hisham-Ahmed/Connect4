package connect4;

public class Board implements Cloneable {
	
	char [][] cells;
	int height;
	int width;
	boolean [] col;

	public Board() {
		height = 4;
		width = 6;
                cells = new char[height][width];
                col = new boolean[width];
                for(int i = 0; i < width; i++){
                    col[i] = false;
                }
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < height; j++) {
				cells[i][j] = '.';
			}
		}
	}
	public Board(int height) {
		cells = new char[height][6];
		this.height = height;
		this.width = 6;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				cells[i][j] = '.';
			}
		}
	}
	public Board(char [][] cells, int height, int width) {
		this.cells = cells;
		this.height = height;
		this.width = width;
	}
	public int DropValidity(int column) {
		if (column > 5 || column < 0) {
			return 0;			
		}
		if (cells[height-1][column] != '.') {
			return 0;
		}
                if (cells[height-1][0] != '.' && cells[height-1][1] != '.' 
                        && cells[height-1][2] != '.' && cells[height-1][3] != '.' 
                        && cells[height-1][4] != '.' && cells[height-1][5] != '.'){
                    return 1;       
                }
		return 2;
	}
	public boolean dropDisk(char symbol, int column) {
		if (DropValidity(column) != 2) {
			return false;
		}
		int i = 0;
		while (cells[i][column] != '.') {
			i++;			
		}
		cells[i][column] = symbol;
		return true;
	}
	public void printBoard() {
		for (int i = height-1;i >= 0; i--) {
			for(int j = 0; j < width; j++) {
				System.out.print("|" + cells[i][j]);
			}
			System.out.println("|");
		}
		for(int j = 0; j < (2*width)+1; j++) {
			System.out.print("=");
		}
		System.out.println();
		for(int j = 0; j < width; j++) {
			System.out.print("|" + (j+1));
		}
                System.out.println("|");
                for(int j = 0; j < (2*width)+1; j++) {
			System.out.print("=");
		}
		System.out.println();
	}

	public boolean isWon(char symbol) {
		// Row
		for (int i=0; i < height; i++) {
			for (int j=0; j < width - 3; j++) {
				if (cells[i][j] == symbol) {
					if (cells[i][j+1] == symbol && cells[i][j+2] == symbol &&
						cells[i][j+3] == symbol) {
						return true;
					}						
				}
			}
		}		
		// Column
		for (int j=0; j < width; j++) {
			for (int i=0; i < height - 3; i++) {
				if (cells[i][j] == symbol) {
					if (cells[i+1][j] == symbol && cells[i+2][j] == symbol &&
						cells[i+3][j] == symbol) {
						return true;
					}						
				}
			}
		}
		// Forward Diagonal
		for (int i=0; i < height-3; i++) {
			for (int j=0; j < width - 3; j++) {
				if (cells[i][j] == symbol) {
					if (cells[i+1][j+1] == symbol && cells[i+2][j+2] == symbol &&
						cells[i+3][j+3] == symbol) {
						return true;
					}						
				}
			}
		}
		
		// Backward Diagonal
		for (int i=height-1; i >= 3; i--) {
			for (int j=0; j < width - 3; j++) {
				if (cells[i][j] == symbol) {
					if (cells[i-1][j+1] == symbol && cells[i-2][j+2] == symbol &&
						cells[i-3][j+3] == symbol) {
						return true;
					}						
				}
			}
		}
		return false;
	}
	public int SymInARow(char symbol, char opponent_symbol, int n) {
		int count = 0;	
		// Row-wise
		boolean stop = false;
		for (int i=0; i < height; i++) {
			for (int j=0; j < width - 3; j++) {				
				if (cells[i][j] == symbol) {
					int s_count = 1;
					for(int k=1;k<4;k++) {
						if (cells[i][j+k] == opponent_symbol) {
							s_count = 0;
							break;
						}
						if (cells[i][j+k] == symbol) {
							s_count++;
						}
					}
					if (s_count == n) {
						count++;
					}
				}				
			}
		}
		stop = false;
		// Row-wise back
		for (int i=0; i < height; i++) {
			for (int j=3; j < width; j++) {				
				if (cells[i][j] == symbol) {
					int s_count = 1;
					for(int k=1;k<4;k++) {
						if (cells[i][j-k] == opponent_symbol) {
							s_count = 0;
							break;
						}
						if (cells[i][j-k] == symbol) {
							s_count++;
						}
					}
					if (s_count == n) {
						count++;
					}
				}				
			}			
		}
		stop = false;
		// Column wise
		for (int j=0; j < width; j++) {
			for (int i=0; i < height - 3; i++) {
				if (cells[i][j] == symbol) {
					int s_count = 1;
					for(int k=1;k<4;k++) {
						if (cells[i+k][j] == opponent_symbol) {
							s_count = 0;
							break;
						}
						if (cells[i+k][j] == symbol) {
							s_count++;
						}
					}
					if (s_count == n) {
						count++;
					}
				}
			}
		}
		stop = false;
		// Forward diagonal
		for (int i=0; i < height-3; i++) {
			for (int j=0; j < width - 3; j++) {
				if (cells[i][j] == symbol) {
					int s_count = 1;
					for(int k=1;k<4;k++) {
						if (cells[i+k][j+k] == opponent_symbol) {
							s_count = 0;
							break;
						}
						if (cells[i+k][j+k] == symbol) {
							s_count++;
						}
					}
					if (s_count == n) {
						count++;
					}
				}
			}
		}
		stop = false;
		// Backward diagonal
		for (int i=height-1; i >= 3; i--) {
			for (int j=0; j < width - 3; j++) {
				if (cells[i][j] == symbol) {
					int s_count = 1;					
					for(int k=1;k<4;k++) {
						if (cells[i-k][j+k] == opponent_symbol) {
							s_count = 0;
							break;
						}
						if (cells[i-k][j+k] == symbol) {
							s_count++;
						}
					}
					if (s_count == n) {
						count++;
					}
				}
			}
		}
		return count;
	}
	public void setValue(int i, int j, char c) {
		cells[i][j] = c;	
	}
	@Override
	public Board clone() {
	    try {
                    return (Board)super.clone();
		} catch (CloneNotSupportedException e) {
                    e.printStackTrace();
		}
		return null;
	}
	public Board copy() {
		char [][] newcells = new char[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++ ) {
				newcells[i][j] = cells[i][j];
			}				
		}
		Board b = new Board(newcells,height,width);
		return b;
	}
	public static void main(String[] args) {
		Board b = new Board(6);
		b.printBoard();
	}
}

class ObjectCounter {
    int n;
    public ObjectCounter() {
        n = 0;
    }
    public void increment() {
        n++;
    }
    public int get() {
        return n;
    }
}