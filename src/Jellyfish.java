/**
 * HW4 java 
 * Campus: Bear Sheva
 * Student 1: Id:201451622  Name: Ilay Karamani 
 * Student 2: Id:301572046  Name: Amir Moyal 

 */


import java.awt.Color;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import javax.swing.JColorChooser;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Jellyfish extends Swimmable implements MarineAnimal{

	protected int size=120;
	private Color col=Color.red;
	public String type,color_str; // color_str is the string name of color
	AquaPanel pnl;
	public CyclicBarrier barrier;
	public String[] state = new String[8];

	//default constructor
	public Jellyfish(AquaPanel panel)
	{
		super();
		this.swim=true;
		this.type="Jellyfish";
		pnl=panel;
		this.timer_int=0;

		this.size=120; //size default value
		this.horSpeed=1; //horSpeed default value
		this.verSpeed=1; //verSpeed default value
		this.color_str="Red";
		this.col=Color.red;

		this.y_front=panel.getHeight()/2;
		this.x_front=panel.getWidth()/2;
		this.x_dir=0;
		eat_counter=0;

		this.timer = new Timer(60000, new ActionListener() {
			@Override
		      public void actionPerformed(ActionEvent e) {
				panel.foodNotify(id);
		        
		      }
		    });
		this.setState(new Satiated());
		
	}



	//constructor
	public Jellyfish(AddAnimalDialog amlData,AquaPanel panel)
	{
		super();
		this.swim=true;
		this.pnl=panel;
		this.type=amlData.typeComboBox.getSelectedItem().toString();

		if(amlData.size.getText().isEmpty())
			this.size=120; //size default value
		else
			this.size=Integer.parseInt(amlData.size.getText());
		if(amlData.horSpeed.getText().isEmpty())
			this.horSpeed=1; //horSpeed default value
		else
			this.horSpeed=Integer.parseInt(amlData.horSpeed.getText());
		if(amlData.verSpeed.getText().isEmpty())
			this.verSpeed=1; //verSpeed default value
		else
			this.verSpeed=Integer.parseInt(amlData.verSpeed.getText());
		if(amlData.timer_lbl.getText().isEmpty())
			this.timer_int=60;
		else
			this.timer_int=Integer.parseInt(amlData.timer_lbl.getText());
		
		this.color_str=amlData.colorComboBox.getSelectedItem().toString();
		if(amlData.colorComboBox.getSelectedItem()=="Green")
			this.col=Color.green;
		else if(amlData.colorComboBox.getSelectedItem()=="Blue")
			this.col=Color.blue;
		else
			this.col=Color.red; //Color default value

		
		
		this.id= panel.animal_counter[0]+1;

		this.timer = new Timer(this.timer_int*1000, new ActionListener() {
			@Override
		      public void actionPerformed(ActionEvent e) {
				if(pnl.isFood)
				{
					setState(new Hungry());
				}
				panel.foodNotify(id);
		        
		      }
		    });
		
		this.y_front=panel.getHeight()/2;
		this.x_front=panel.getWidth()/2;
		this.x_dir=0;

		this.setState(new Satiated());
	}

	@Override
	public void setState(HungerState state) {
		// TODO Auto-generated method stub
		this.h_state = state;
	}
	
	
	// run function for jellyfish thread
	public void run() {
		this.timer.start();
		while (true) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}
			if(!this.swim) // if fish is suspended
			{
				synchronized(this)
				{
					this.timer.stop();
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else // if fish is not suspended -- check for food or swim regularly
			{
				if (pnl.isFood && this.isBarrier) 
				{
					try {
						this.barrier.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					this.h_state.Swim(this, this.pnl);
					
					// animal reached food
					synchronized (this) {
						if((Math.abs(x_front-pnl.getWidth()/2) <= 5) && (Math.abs(y_front-pnl.getHeight()/2) <= 5))
						{

							this.isBarrier=false;
							pnl.setFood(false);
							this.setState(new Satiated());

							pnl.callback(this);

							pnl.revalidate();
							pnl.repaint();
						}
					} 
				}

				//else -- if there is no food yet , keep moving regularly
				else
				{
					this.h_state.Swim(this, this.pnl);
					this.setState(new Satiated());

				}
			}

			//
			pnl.revalidate();
			pnl.repaint();
		}
	}



	//draw jellyfish
	public void drawCreature(Graphics g)
	{
		int numLegs;
		if(size<40)
			numLegs = 5;
		else if(size<80)
			numLegs = 9;
		else
			numLegs = 12;
		g.setColor(col);
		g.fillArc(x_front - size/2, y_front - size/4, size, size/2, 0, 180);
		for(int i=0; i<numLegs; i++)
			g.drawLine(x_front - size/2 + size/numLegs + size*i/(numLegs+1),
					y_front, x_front - size/2 + size/numLegs + size*i/(numLegs+1),
					y_front+size/3);
	}

	@Override
	public String getAnimalName() {
		// TODO Auto-generated method stub
		return this.type.toString();
	}

	@Override
	public void setSuspend() {
		// TODO Auto-generated method stub
		this.swim=false;
	}

	@Override
	public synchronized void setResume() {
		// TODO Auto-generated method stub
		this.swim=true;
		this.notify();
	}

	@Override
	public void setBarrier(CyclicBarrier b) {
		// TODO Auto-generated method stub
		this.barrier=b;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return this.size;
	}

	@Override
	public void eatInc() {
		// TODO Auto-generated method stub
		this.eat_counter++;
	}

	@Override
	public int getEatCount() {
		// TODO Auto-generated method stub
		return this.eat_counter;
	}

	@Override
	public String getColor() {
		// TODO Auto-generated method stub
		return this.color_str;
	}

	//calc jellyfish direction to worm (food)
	public synchronized void setNewDir()
	{
		v_old = Math.sqrt(this.verSpeed*this.verSpeed + this.horSpeed*this.horSpeed);
		double k =Math.abs( (float)(y_front - pnl.getHeight()/2) / (float)(x_front - pnl.getWidth()/2)); 

		this.v_hor_new =v_old / Math.sqrt(k*k+1);
		this.v_ver_new = v_hor_new*k;

	}



	@Override
	public Swimmable clone() {
		// TODO Auto-generated method stub
		Swimmable animal= new Jellyfish(pnl);
		return animal;
	}

	@Override
	public void setSize(int s) {
		// TODO Auto-generated method stub
		this.size=s;
	}



	@Override
	public void setColor(String color) {
		// TODO Auto-generated method stub
		if(color.equals("(0,255,0)")|| color=="Green")
		{
			this.col=Color.green;
			this.color_str="Green";
		}
		else if(color.equals("(0,0,255)") || color=="Blue")
		{
			this.col=Color.blue;
			this.color_str="Blue";
		}
		else if(color.equals("(255,0,0)") || color=="Red")
		{
			this.col=Color.red; //Color default value
			this.color_str="Red";
		}
		else
			this.color_str=color;
		
	}


	@Override
	public HungerState getHungerState() {
		// TODO Auto-generated method stub
		return this.h_state;
	}
	


	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub
		this.col=color;
		this.setColor(this.getColorName(this.col));		
	}
	


	@Override
	public void PaintFish() {
		// TODO Auto-generated method stub
		//last dialog -- keep copy or change properties of copied animal
        Color anml_color = JColorChooser.showDialog(null, "Change Button Background",null);
        if(anml_color!=null)
        {
        	this.setColor(anml_color);
            this.setColor(this.getColorName(this.col));
        }
	}

	@Override
	public Jellyfish getAnimalObj() {
		// TODO Auto-generated method stub
		return this;
	}



	@Override
	public Color getCol() {
		// TODO Auto-generated method stub
		return this.col;
	}



	@Override
	public String getColorName(Color c) {
		// TODO Auto-generated method stub
		   return "("+c.getRed()+","+c.getGreen()+","+c.getBlue()+")";
	}



	@Override
	public Memento saveStateToMemento() {
		state[0] = this.getColorName(this.col); // color
		state[1] = Integer.toString(this.size); // size
		state[2] = Integer.toString(this.horSpeed); // horSpeed
		state[3] = Integer.toString(this.verSpeed); // verSpeed
		state[4] = Integer.toString(this.x_front); // x_front
		state[5] = Integer.toString(this.y_front); // y_front
		state[6] = Integer.toString(this.id); // id
		state[7] = this.getAnimalName(); // type
		return new Memento(state);	
	}



	@Override
	public void getStateFromMemento(Memento Memento) {
		// TODO Auto-generated method stub
		this.state = Memento.getState();
		this.setColor(state[0]);
		
		String str = state[0];
		str = str.replaceAll("[^0-9]+", " ");
		String[] RGB=str.trim().split(" ");
		
		this.col= new Color(Integer.parseInt(RGB[0]),Integer.parseInt(RGB[1]),Integer.parseInt(RGB[2]));		
		
				
		this.size=Integer.parseInt(state[1]);
		this.horSpeed=Integer.parseInt(state[2]);
		this.verSpeed=Integer.parseInt(state[3]);
		this.x_front=Integer.parseInt(state[4]);
		this.y_front=Integer.parseInt(state[5]);

	}


}
