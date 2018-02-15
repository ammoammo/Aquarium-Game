/**
 * HW4 java 
 * Campus: Bear Sheva
 * Student 1: Id:201451622  Name: Ilay Karamani 
 * Student 2: Id:301572046  Name: Amir Moyal 

 */


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.util.Arrays;

public class Zostera extends Immobile{

	private int size=120,y,x;
	private Color col=Color.green;
	public String type,color_str; // color_str is the string name of color
	AquaPanel pnl;
	public String[] state=new String[6];


	public Zostera(AddPlantDialog plntData,AquaPanel panel)
	{
		super();
		this.col=Color.green;
		this.color_str="Green";
		this.pnl=panel;
		this.type=plntData.typeComboBox.getSelectedItem().toString();
		
		this.id=panel.plant_counter[0]+1;

		if(plntData.size.getText().isEmpty())
			this.size=120; //size default value
		else
			this.size=Integer.parseInt(plntData.size.getText());
		if(plntData.x.getText().isEmpty())
			this.x=70; //x position default value
		else
			this.x=Integer.parseInt(plntData.x.getText());

		this.y=panel.getHeight()-60;
		pnl.revalidate();
		pnl.repaint();

	}


	@Override
	public String getPlantName() {
		// TODO Auto-generated method stub
		return this.type.toString();	

	}

	@Override
	public void drawCreature(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2=(Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g2.setColor(col);
		g.drawLine(x-2, y, x-10, y-size*9/10);
		g.drawLine(x+2, y, x+10, y-size*9/10);
		g.drawLine(x-4, y, x-20, y-size*4/5);
		g.drawLine(x+4, y, x+20, y-size*4/5);
		g.drawLine(x-6, y, x-30, y-size*7/10);
		g.drawLine(x+6, y, x+30, y-size*7/10);
		g.drawLine(x-8, y, x-40, y-size*4/7);
		g.drawLine(x+8, y, x+40, y-size*4/7);
		g2.setStroke(new BasicStroke(1));


	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return this.size;
	}

	@Override
	public String getColor() {
		// TODO Auto-generated method stub
		return this.color_str;
	}

	@Override
	public Memento saveStateToMemento() {
		state[0] = this.getColor(); // color
		state[1] = Integer.toString(this.size); // size
		state[2] = Integer.toString(this.x); // x_front
		state[3] = Integer.toString(this.y); // y_front
		state[4] = Integer.toString(this.id); // id
		state[5] = this.getPlantName(); // type
		return new Memento(state);	
	}



	@Override
	public void getStateFromMemento(Memento Memento) {
		// TODO Auto-generated method stub
		this.state = Memento.getState();
		
		this.size=Integer.parseInt(state[1]);
		
		this.x=Integer.parseInt(state[2]);
		this.y=Integer.parseInt(state[3]);

	}

}
