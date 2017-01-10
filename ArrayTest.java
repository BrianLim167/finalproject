public class ArrayTest {
    private char[][] board; // b = black, w = white, e = empty
    private int blackScore, whiteScore;

    public ArrayTest(int size) { // size is determined by the setup window
	// set initial scores to 0
	blackScore = 0;
	whiteScore = 0;
	// setup the board
	board = new char[size][size];
	for (int row = 0; row < size; row ++) {
	    for (int col = 0; col < size; col ++) {
		board[row][col] = 'e'; // board is initialized to be empty
	    }
	}
    }

    public boolean placeStone(int row, int col, char player) {
	if (board[row][col] != 'e') {
	    return false; // unable to place stone in occupied space
	}
	
	board[row][col] = player;
	return true;
    }

    public char getStatus(int row, int col) {
	return board[row][col];
    }

    public int getSize() {
	return board.length;
    }

    public void isDead() {
	boolean[][] killMe = new boolean[board.length][board.length];
	for (int row = 0; row < killMe.length; row ++) {
	    for (int col = 0; col < killMe.length; col++) {
		killMe[row][col] = board[row][col] != 'e';
	    }
	}
    }

    public static void main(String[] args) {
	ArrayTest tester = new ArrayTest(10);
        System.out.println(tester.placeStone(6, 3, 'b'));
	System.out.println(tester.placeStone(6, 3, 'b'));
	System.out.println(tester.getStatus(6, 3));
    }
}
