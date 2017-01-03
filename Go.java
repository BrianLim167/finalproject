import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Go extends JFrame implements ActionListener {
    private Container pane;
    private JLabel dimensionsL,handicapL,komiL,title;
    private JComboBox dimensions,handicap;
    private JTextField komi;
    private JButton play;
 
    //CONSTRUCTOR SETS EVERYTHING UP
    public Go() {
	this.setTitle("Go Setup");
	this.setSize(600,400);
	this.setLocation(500,400);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
	pane = this.getContentPane();
	pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
     
	String[]sizes = {"19x19","13x13","9x9","25x25","21x21","5x5"};

	komiL = new JLabel("Komi");
	komi = new JTextField("6.5");
	JButton play = new JButton("PLAY");
	play.addActionListener(this);
	play.setActionCommand("Boat");
	
	pane.add(komiL);
	pane.add(komi);
	pane.add(play);
    }

    public void actionPerformed(ActionEvent e) {
	String event = e.getActionCommand();
	if (event.equals("Boat")) {
	    String s = "tree fiddy";
	    komiL.setText(s);
	}
    }

    public static void main(String[] args) {
	Go g = new Go();
	g.setVisible(true);
    }
}
