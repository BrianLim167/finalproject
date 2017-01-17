import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Go extends JFrame implements ActionListener {
    private Container pane;
    private JPanel row0,row1,row2;
    private JLabel dimensionsL,handicapL,komiL,title;
    private JComboBox<String> dimensions,handicap;
    private JTextField komi;
    private JButton play;
 
    //CONSTRUCTOR SETS EVERYTHING UP
    public Go() {
	this.setTitle("Go");
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
	pane = this.getContentPane();
	pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));     

	row0 = new JPanel(new FlowLayout());
	row1 = new JPanel(new FlowLayout());
	row2 = new JPanel(new FlowLayout());

	String[]sizes = {"19x19","13x13","9x9","25x25","21x21","5x5"};
	String[]handi = {"0","1","2","3","4","5","6","7","8","9"};

	dimensionsL = new JLabel("Dimensions ");
	dimensions = new JComboBox<String>(sizes);
	komiL = new JLabel("Komi ");
	komi = new JTextField("6.5");
	handicapL = new JLabel("Handicap Stones ");
	handicap = new JComboBox<String>(handi);
	play = new JButton("PLAY");

	//dimensions.addActionListener(this);
	play.addActionListener(this);
	play.setActionCommand("Run");
	play.setAlignmentX(Component.CENTER_ALIGNMENT);

	row0.add(dimensionsL);
	row0.add(dimensions);
	row1.add(komiL);
	row1.add(komi);
	row2.add(handicapL);
	row2.add(handicap);

	pane.add(row0);
	pane.add(row1);
	pane.add(row2);
	pane.add(play);

	pack();
    }

    public void actionPerformed(ActionEvent e) {
	String event = e.getActionCommand();
	if (event.equals("Run")) {
	    String sizeInput = (String)dimensions.getSelectedItem(); // converts JComboBox input into a String
	    int x = Integer.parseInt(sizeInput.substring(0, sizeInput.indexOf("x")));
	    int y = Integer.parseInt(sizeInput.substring(sizeInput.indexOf("x") + 1));
	    
	    int handicapInput = Integer.parseInt((String)handicap.getSelectedItem());
	    
	    double komiInput = Double.parseDouble(komi.getText());
	    
	    dispose();
	    GoBoardFrame b = new GoBoardFrame(x, y, komiInput, handicapInput);
	    b.setVisible(true);
	    b.setLocationRelativeTo(null);
	}
    }

    public static void main(String[] args) {
	Go g = new Go();
	g.setVisible(true);
	g.setLocationRelativeTo(null);
    }
}

class GoBoardFrame extends JFrame implements ActionListener {
    private Container pane;
    private char currentPlayer;
    private char[][] board,tempBoard,prevBoard; // B = black, W = white, E = empty
    private int blackPrisoners,whitePrisoners;
    private double komi;
    private int handicap;
    private boolean passed;
    private JButton[][] boardGUI;
    private JLabel currentPlayerL,messageL;               //top of window
    private JLabel blackPrisonersL,whitePrisonersL,komiL; //scoreBoard
    private JButton pass,resign;                          //buttonPanel
    private JPanel boardPanel,scoreBoard,buttonPanel;
    
    private JButton button;

    public GoBoardFrame(int x, int y, double komi, int handicap) {
	this.setTitle("Go");
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
	pane = this.getContentPane();
	pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
	
	currentPlayer = 'B';
	
	board = new char[x][y];
	tempBoard = new char[x][y];
	prevBoard = new char[x][y];

	blackPrisoners = 0;

	whitePrisoners = 0;

	this.komi = komi;

	this.handicap = handicap;

	passed = false;
	
	boardGUI = new JButton[x][y];
	
	String currentPlayerS = "Black to play";
	currentPlayerL = new JLabel(currentPlayerS);
	currentPlayerL.setAlignmentX(Component.CENTER_ALIGNMENT);
	
	String messageS = "Handicap Moves Left: "+handicap;
	messageL = new JLabel(messageS);
	messageL.setAlignmentX(Component.CENTER_ALIGNMENT);
	
	String blackPrisonersS = "Black Captures: "+blackPrisoners;
	blackPrisonersL = new JLabel(blackPrisonersS);

	String whitePrisonersS = "White Captures: "+whitePrisoners;
	whitePrisonersL = new JLabel(whitePrisonersS);

	String komiS = "Komi: "+komi;
	komiL = new JLabel(komiS);

	pass = new JButton("Pass");
	pass.addActionListener(this);
	pass.setActionCommand("pass");

	resign = new JButton("Resign");
	resign.addActionListener(this);
	resign.setActionCommand("resign");

	boardPanel = new JPanel();
	boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.Y_AXIS));
	for (int row=0 ; row<x ; row++){
	    JPanel boardRow = new JPanel();
	    boardRow.setLayout(new FlowLayout());
	    ((FlowLayout)boardRow.getLayout()).setHgap(0);
	    ((FlowLayout)boardRow.getLayout()).setVgap(0);
	    for (int col=0 ; col<y ; col++){
		board[row][col] = 'E';
		tempBoard[row][col] = 'E';
		prevBoard[row][col] = 'E';
		Icon buttonImage = new ImageIcon("temp.png");
		button = new JButton(buttonImage);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setContentAreaFilled(false);

		boardRow.add(button);

		button.addActionListener(this);
		button.setActionCommand(row + "," + col);
		
		boardGUI[row][col] = button;
	    }
	    boardPanel.add(boardRow);
	}

	scoreBoard = new JPanel();
	scoreBoard.setLayout(new FlowLayout());
	scoreBoard.add(blackPrisonersL);
	scoreBoard.add(whitePrisonersL);
	scoreBoard.add(komiL);

	buttonPanel = new JPanel();
	buttonPanel.setLayout(new FlowLayout());
	buttonPanel.add(pass);
	buttonPanel.add(resign);
	
	pane.add(currentPlayerL);
	pane.add(messageL);
	pane.add(boardPanel);
	pane.add(scoreBoard);
	pane.add(buttonPanel);
	
	pack();
    }

    public boolean placeStone(int row, int col) {
	if (board[row][col] != 'E') {
	    return false; // unable to place stone in occupied space
	}

	char player = 'E';
	
	Icon i = new ImageIcon("black.png");
	String s = currentPlayerL.getText();
	if (s.equals("Black to play")) {
	    player = 'B';
	    i = new ImageIcon("black.png");
	}
	if (s.equals("White to play")) {
	    player = 'W';
	    i = new ImageIcon("white.png");
	}
	boardGUI[row][col].setIcon(i);
        
	board[row][col] = player;
	return true;
    }

    
    public void actionPerformed(ActionEvent e) {
	String event = e.getActionCommand();
	boolean newTurn = false;
	char[][]oldBoard = new char[board.length][board[0].length];
	for (int row=0 ; row<board.length ; row++){
	    for (int col=0 ; col<board[0].length ; col++){
		oldBoard[row][col] = board[row][col];
	    }
	}
	Icon[][]oldBoardGUI = new Icon[boardGUI.length][boardGUI[0].length];
	for (int row=0 ; row<boardGUI.length ; row++){
	    for (int col=0 ; col<boardGUI[0].length ; col++){
		oldBoardGUI[row][col] = boardGUI[row][col].getIcon();
	    }
	}
	if (event.equals("pass")) {
	    if (passed == true){
		pass.setActionCommand("turkey");
		resign.setActionCommand("turkey");
		for (int row = 0; row < boardGUI.length; row ++) {
		    for (int col = 0; col < boardGUI[row].length; col ++) {
			boardGUI[row][col].setActionCommand("turkey");
		    }
		}
		currentPlayerL.setText("Game has ended.");
	    }
	    messageL.setText(" ");
	    passed = true;
	    String s = currentPlayerL.getText();
	    if (s.equals("Black to play")) {
		currentPlayerL.setText("White to play");
	    }
	    if (s.equals("White to play")) {
		currentPlayerL.setText("Black to play");
	    }
	    newTurn = true;
	}
	else {
	    passed = false;
	}

	if (event.equals("resign")) {
	    messageL.setText(" ");
	    pass.setActionCommand("turkey");
	    resign.setActionCommand("turkey");
	    for (int row = 0; row < boardGUI.length; row ++) {
		for (int col = 0; col < boardGUI[row].length; col ++) {
		    boardGUI[row][col].setActionCommand("turkey");
		}
	    }
	    String s = currentPlayerL.getText();
	    if (s.equals("Black to play")) {
		currentPlayerL.setText("White wins by forfeit!");
	    }
	    if (s.equals("White to play")) {
		currentPlayerL.setText("Black wins by forfeit!");
	    }
	}
	
	if (event.indexOf(",") > -1) { // comma means it's an x,y coordinate
	    int x = Integer.parseInt(event.substring(0, event.indexOf(",")));
	    int y = Integer.parseInt(event.substring(event.indexOf(",") + 1));
	    boolean[][] isDead;
	    String s = currentPlayerL.getText();
	    try {
		if (placeStone(x, y)) {
		    char me;
		    if (s.equals("Black to play")) {
			//int temp = Integer.parseInt(messageL.getText().substring(21));
			if (handicap == 0) {
			    me = 'B';
			    currentPlayerL.setText("White to play");
			    isDead = markDead(board, 'B');
			}
			else {
			    handicap -= 1;
			    messageL.setText("Handicap Moves Left: " + handicap);
			    isDead = markDead(board, 'W');
			}
		    }
		    else {
			me = 'W';
			messageL.setText(" ");
			currentPlayerL.setText("Black to play");
			isDead = markDead(board, 'W');
		    }
		    for (int row = 0; row < isDead.length; row ++) {
			for (int col = 0; col < isDead[row].length; col ++) {
			    if (isDead[row][col] == true) {
				if (board[row][col] == 'B') {
				    whitePrisoners++;
				}
				else {
				    blackPrisoners++;
				}
				boardGUI[row][col].setIcon(new ImageIcon("temp.png"));
				board[row][col] = 'E';
			    }
			}
		    }
		    boolean ko = true;
		    for (int row=0 ; row<board.length ; row++){
			for (int col=0 ; col<board[0].length ; col++){
			    if (board[row][col] != prevBoard[row][col]){
				ko = false;
			    }
			}
		    }
		    if (ko){
			throw new IllegalArgumentException("move that gets the board to repeat is an illegal ko move");
		    }
		    messageL.setText(" ");
		    blackPrisonersL.setText("Black Captures: "+blackPrisoners);
		    whitePrisonersL.setText("White Captures: "+whitePrisoners);
		    newTurn = true;
		}
	    }catch(IllegalArgumentException exc){
		if (exc.getMessage().equals("move that gets own stones captured is suicidal")){
		    messageL.setText("Suicidal Move");
		}else if(exc.getMessage().equals("move that gets the board to repeat is an illegal ko move")){
		    messageL.setText("Illegal Ko Move");
		}
		if (s.equals("Black to play")) {
		    currentPlayerL.setText("Black to play");
		}
		if (s.equals("White to play")) {
		    currentPlayerL.setText("White to play");
		}
		for (int row=0 ; row<board.length ; row++){
		    for (int col=0 ; col<board[0].length ; col++){
			board[row][col] = oldBoard[row][col];
		    }
		}
		for (int row=0 ; row<boardGUI.length ; row++){
		    for (int col=0 ; col<boardGUI[0].length ; col++){
			boardGUI[row][col].setIcon(oldBoardGUI[row][col]);
		    }
		}
		newTurn = false;
	    }
	}
	if (newTurn){
	    for (int row=0 ; row<board.length ; row++){
		for (int col=0 ; col<board[0].length ; col++){
		    prevBoard[row][col] = tempBoard[row][col];
		}
	    }
	    for (int row=0 ; row<board.length ; row++){
		for (int col=0 ; col<board[0].length ; col++){
		    tempBoard[row][col] = board[row][col];
		}
	    }
	}
    }
    public static boolean[][] markDead(char[][] board,char me) {
	//me is the player whose move it is
	boolean[][] ans = new boolean[board.length][board[0].length];
	ArrayList<int[]> marked = new ArrayList<int[]>(); //list of marked stones
	
	    char you; //you is the other player
	    if (me=='B'){
		you = 'W';
	    }else{
		you = 'B';
	    }

	    //begins by marking all stones as dead
	    for (int row=0 ; row<board.length ; row++){
		for (int col=0 ; col<board.length ; col++){
		    if (board[row][col] != 'E'){
			ans[row][col] = true;
			int[] coord = {row,col};
			marked.add(coord);
		    }
		}
	    }
        
	    boolean[][]oldAns = new boolean[board.length][board[0].length];
	    while (ans != oldAns){
		for (int n=0 ; n<360 ; n++){
		    oldAns = ans;
		    for (int stone=marked.size()-1 ; stone>=0 ; stone--){
			int[] coord = new int[2];   //coordinates of this stone
			coord[0] = marked.get(stone)[0];
			coord[1] = marked.get(stone)[1];
			int[] dimensions = new int[2];
			dimensions[0] = board.length;
			dimensions[1] = board[0].length;
			for (int neighbor=0 ;
			     neighbor<neighbors(coord,dimensions).size() ;
			     neighbor++)
			    {
				int[] adj = neighbors(coord,dimensions).get(neighbor);
				// ^coordinates of adjacent space
				if (board[adj[0]][adj[1]] == 'E' ||
				    (board[adj[0]][adj[1]] == board[coord[0]][coord[1]] &&
				     ans[adj[0]][adj[1]] == false))
				    {
					ans[coord[0]][coord[1]] = false;
					//marked.remove(stone);
				    }
			    }
		    }
		}
	    }
	    //determine what color of stones are marked
	    boolean selfCapture = false;
	    boolean otherCapture = false;
	    for (int row=0 ; row<ans.length ; row++){
	    for (int col=0 ; col<ans.length ; col++){
		if (ans[row][col] && board[row][col] == me){
		    selfCapture = true;
		}else if (ans[row][col] && board[row][col] == you){
		    otherCapture = true;
		}
	    }
	}
	if (selfCapture && otherCapture){ //pseudosuicidal move case
	    for (int row=0 ; row<ans.length ; row++){
		for (int col=0 ; col<ans[0].length ; col++){
		    if (ans[row][col] == true && board[row][col] == me){
			ans[row][col] = false;
		    }
		}
	    }
	}else if (selfCapture){ //suicidal move case
	    throw new IllegalArgumentException("move that gets own stones captured is suicidal");	    
	}
	return ans;
    }

    public static ArrayList<int[]> neighbors(int[] coord,int[] dimensions){
	//returns list of coordinates of a space's neighbors
	ArrayList<int[]> ans = new ArrayList<int[]>(); 
	if (coord[0] > 0){
	    int[] neighbor0 = new int[2];
	    neighbor0[0] = coord[0]-1;
	    neighbor0[1] = coord[1];
	    ans.add(neighbor0);
	}
	if (coord[0] < dimensions[0]-1){
	    int[] neighbor1 = new int[2];
	    neighbor1[0] = coord[0]+1;
	    neighbor1[1] = coord[1];
	    ans.add(neighbor1);
	}
	if (coord[1] > 0){
	    int[] neighbor2 = new int[2];
	    neighbor2[0] = coord[0];
	    neighbor2[1] = coord[1]-1;
	    ans.add(neighbor2);
	}
	if (coord[1] < dimensions[1]-1){
	    int[] neighbor3 = new int[2];
	    neighbor3[0] = coord[0];
	    neighbor3[1] = coord[1]+1;
	    ans.add(neighbor3);
	}
	return ans;
    }
        
}

