import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

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
	    dispose();
	    GoBoardFrame b = new GoBoardFrame(9,9);
	    // ^set this to variable dimensions later
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
    private JLabel currentPlayerL,message;             //top of window
    private JLabel blackPrisoners,whitePrisoners,komi; //scoreBoard
    private JButton[][] boardGUI;                      //boardPanel
    private JButton pass,resign;                       //buttonPanel
    private JPanel boardPanel,scoreBoard,buttonPanel;

    private JButton button;

    public GoBoardFrame(int x,int y) {
	this.setTitle("Go");
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
	pane = this.getContentPane();
	pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
	
	currentPlayerL = new JLabel("Black to play");

	boardPanel = new JPanel();
	boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.Y_AXIS));
	for (int row=0 ; row<x ; row++){
	    JPanel boardRow = new JPanel();
	    boardRow.setLayout(new FlowLayout());
	    for (int col=0 ; col<y ; col++){
		try {
		    Image buttonImage = ImageIO.read(new File("temp.png"));
		    button = new JButton(new ImageIcon(buttonImage));
		    button.setBorder(BorderFactory.createEmptyBorder());
		    button.setContentAreaFilled(false);

		    boardRow.add(button);

		    button.addActionListener(this);
		    button.setActionCommand("test");
		}catch (IOException e){
		    System.out.println(e);
		}
	    }
	    boardPanel.add(boardRow);
	}
		
	/*
	  try {
	  Image middle = ImageIO.read(new File("temp.png"));
	  button = new JButton(new ImageIcon(middle));
	  button.setBorder(BorderFactory.createEmptyBorder());
	  button.setContentAreaFilled(false);

	  pane.add(button);

	  button.addActionListener(this);
	  button.setActionCommand("test");
	  }catch (IOException e){
	  System.out.println(e);
	  }*/

	pane.add(currentPlayerL);
	pane.add(boardPanel);
	
	pack();
    }
    public void actionPerformed(ActionEvent e) {
	String event = e.getActionCommand();
	if (event.equals("test")) {
	    this.setTitle("Stop");
	}
    }

}
