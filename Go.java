import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Go extends JFrame implements ActionListener {
    private Container pane;
    private JPanel row0,row1,row2;
    private JLabel dimensionsL,handicapL,komiL,title;
    private JComboBox<String> dimensions,handicap;
    private JTextField komi;
    private JButton play;
 
    //CONSTRUCTOR SETS EVERYTHING UP
    public Go() {
	this.setTitle("Go Setup");
	this.setSize(300,130);
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
	play.setActionCommand("Boat");
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
    }

    public void actionPerformed(ActionEvent e) {
	String event = e.getActionCommand();
	if (event.equals("Boat")) {
	    String s = "tree fiddy";
	    komiL.setText(s);
	    dispose();
	    GoBoard b = new GoBoard();
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

class GoBoard extends JFrame {
    private Container pane;
}
