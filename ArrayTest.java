class board {
    private int isOccupied; // 0 = empty, 1 = black, 2 = white
    private boolean isAlive; // empty spaces are technically alive

    public board() {
	isOccupied = 0;
	isAlive = true;
    }

    public int getIsOccupied() {
	return isOccupied;
    }

    public void setIsOccupied(int i) {
	isOccupied = i;
    }
}

public class ArrayTest {
    public board[][] goBoard;
    private int blackScore;
    private int whiteScore;

    public ArrayTest(int size) {
	goBoard = new board[size][size];
	for (int row = 0; row < size; row ++) {
	    for (int col = 0; col < size; col ++) {
		goBoard[row][col] = new board();
	    }
	}
	blackScore = 0;
	whiteScore = 0;
    }

    public void click(int row, int col, int turn) {
	goBoard[row][col].setIsOccupied(turn);
    }

    public static void main(String[] args) {
	ArrayTest test = new ArrayTest(10);
	test.click(3, 3, 2);
	System.out.println(test.goBoard[3][3].getIsOccupied());
    }
}
	
