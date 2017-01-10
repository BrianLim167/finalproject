import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
	    //int x =                            FIX ME PENN PLS FIX YES
	    //int y =                            FIX ME PENN PLS FIX YES
	    //double komi =                            FIX ME PENN PLS FIX YES
	    //int handicap = Integer.parse                            FIX ME PENN PLS FIX YES
	    dispose();
	    GoBoardFrame b = new GoBoardFrame(5,5,6.5,0);
	    // ^set this to variables later
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
    private char[][] board;
    private int blackPrisoners,whitePrisoners;
    private double komi;
    private int handicap;
    private JButton[][] boardGUI;
    private JLabel currentPlayerL,messageL;               //top of window
    private JLabel blackPrisonersL,whitePrisonersL,komiL; //scoreBoard
    private JButton pass,resign;                          //buttonPanel
    private JPanel boardPanel,scoreBoard,buttonPanel;

    private JButton button;

    public GoBoardFrame(int x,int y, double komi, int handicap) {
	this.setTitle("Go");
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
	pane = this.getContentPane();
	pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
	
	currentPlayer = 'B';
	
	board = new char[x][y];

	blackPrisoners = 0;

	whitePrisoners = 0;

	this.komi = komi;

	this.handicap = handicap;
	
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
		try {
		    Image buttonImage = ImageIO.read(new File("temp.png"));
		    button = new JButton(new ImageIcon(buttonImage));
		    button.setBorder(BorderFactory.createEmptyBorder());
		    button.setContentAreaFilled(false);

		    boardRow.add(button);

		    button.addActionListener(this);
		    button.setActionCommand(x+","+y);

		    boardGUI[row][col] = button;
		}catch (IOException e){
		    System.out.println(e);
		}
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
    public void actionPerformed(ActionEvent e) {
	String event = e.getActionCommand();
	if (event.equals("test")) {
	    this.setTitle("Stop");
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
	//System.out.println(oldAns[0][0]);
	//System.out.println(".");
	//System.out.println(ans[0][0]);
	while (ans != oldAns){
	    oldAns = ans;
	    for (int ind=0 ; ind<marked.size() ; ind++){
	    }
	}
	return ans;
    }
    public static ArrayList<int[]> neighbors(int[] coord,int[] dimensions){
	//returns list of coordinates of a space's neighbors
	ArrayList<int[]> ans = new ArrayList<int[]>();
	int[] neighbor = new int[2]; 
	if (coord[0] > 0){
	    neighbor[0] = coord[0]-1;
	    neighbor[1] = coord[1];
	    ans.add(neighbor);
	}
	if (coord[0] < dimensions[0]){
	    neighbor[0] = coord[0]+1;
	    neighbor[1] = coord[1];
	    ans.add(neighbor);
	}
	if (coord[1] > 0){
	    neighbor[0] = coord[0];
	    neighbor[1] = coord[1]-1;
	    ans.add(neighbor);
	}
	if (coord[1] < dimensions[1]){
	    neighbor[0] = coord[0];
	    neighbor[1] = coord[1]+1;
	    ans.add(neighbor);
	}
	return ans;
    }
	
}
