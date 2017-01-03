import javax.swing.*;
import java.awt.*;
public class Go extends JFrame {
    private Container pane;
    private JLabel dimensionsL,handicapL,komiL,title;
    private JComboBox dimensions,handicap;
    private JTextField komi;
 
    //CONSTRUCTOR SETS EVERYTHING UP
    public Go() {
	this.setTitle("Go Setup");
	this.setSize(600,400);
	this.setLocation(500,400);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
	pane = this.getContentPane();
	pane.setLayout(new FlowLayout());
     
	String[]sizes = {"19x19","13x13","9x9","25x25","21x21","5x5"};

	komi = new JTextField("6.5");
	pane.add(komi);
    }

    public static void main(String[] args) {
	Go g = new Go();
	g.setVisible(true);
    }
}

