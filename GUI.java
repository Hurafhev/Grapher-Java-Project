
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class GUI extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private static JTextField txtTerm = new JTextField();
	private static JTextField txtAusgabe = new JTextField();
	private static JButton btnTest = new JButton("Test");
	
	
	public GUI(String frameTitle, int frameWidth, int frameHeight) {
		
		super();
		
		setTitle(frameTitle);
		setVisible(true);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(frameWidth, frameHeight);
		setLocation((screenSize.width - frameWidth)/2, (screenSize.height - frameHeight)/2);		
		setResizable(false);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Container contents = getContentPane();
		contents.setLayout(null);
		
		txtTerm.setBounds(10, 10, 100, 40);
		contents.add(txtTerm);
		txtAusgabe.setBounds(10, 100, 100, 40);
		contents.add(txtAusgabe);
		
		btnTest.setBounds(10, 60, 100, 30);
		contents.add(btnTest);
		
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				test();
			}
		});
		
	}
	
	public void test() {
		Function f = new Function(txtTerm.getText());
		txtAusgabe.setText(Double.toString(Function.parse(f.getTerm(), 0, 3)));
		//txtAusgabe.setText(Double.toString(Function.parseExpression(f.term(), 3)));
		Graphics g = getGraphics();
		f.draw(g, 300, 300);
	}
	
	
	public static void main(String[] args) {

		new GUI("test", 800, 500);

	}

}
