/**
 * HW4 java 
 * Campus: Bear Sheva
 * Student 1: Id:201451622  Name: Ilay Karamani 
 * Student 2: Id:301572046  Name: Amir Moyal 

 */


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.GridLayout;

//import javax.swing.JDialog;
import javax.swing.JLabel;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Worm {
	private static Worm instance = null;
	public static JPanel aqua_p;
	static JPanel myPanel = new JPanel();
	static JLabel label=new JLabel();
	
	/* A private Constructor prevents any other
	 * class from instantiating.*/
	//the private constructor
	private Worm(){ 
		
	}
	
	public static Worm getInstance(JPanel pnl) {
		aqua_p=pnl;
		if(instance == null) // dosent exist yet
		{
			instance = new Worm();
			myPanel.remove(label);
			label=new JLabel("NEW worm created!");
			label.setFont(label.getFont().deriveFont(35.0f));
			label.setForeground(Color.green);
			myPanel.add(label);
			myPanel.setVisible(true);
	
			aqua_p.add(myPanel,BorderLayout.NORTH);
			aqua_p.validate();
			
			
		}
		else // warm exist already
		{
			myPanel.remove(label);
			label=new JLabel("singletone -- worm exist!");
			label.setFont(label.getFont().deriveFont(35.0f));
			label.setForeground(Color.red);
			myPanel.add(label);
			myPanel.setVisible(true);
			aqua_p.add(myPanel,BorderLayout.NORTH);
			aqua_p.validate();
		}
		return instance;
	}
	
	
	public void drawWorm(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.red);
		g2.drawArc(aqua_p.getWidth()/2, aqua_p.getHeight()/2-5, 10, 10, 30, 210);	   
		g2.drawArc(aqua_p.getWidth()/2, aqua_p.getHeight()/2+5, 10, 10, 180, 270);	
		g2.setStroke(new BasicStroke(1));
	}

	 public void setInstance()
	 {
		 instance=null;
		 aqua_p.remove(myPanel);
		 		 
	 }

}
