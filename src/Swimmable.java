/**
 * HW4 java 
 * Campus: Bear Sheva
 * Student 1: Id:201451622  Name: Ilay Karamani 
 * Student 2: Id:301572046  Name: Amir Moyal 

 */


import java.awt.Color;
import java.awt.Graphics;
//import java.lang.Thread.State;
import java.util.concurrent.CyclicBarrier;
import javax.swing.Timer;


public abstract class Swimmable extends AnimalFactory implements SeaCreature {

	protected int horSpeed;
	protected int verSpeed;
	protected boolean swim;
	public boolean isBarrier=false;
	public Timer timer;
	public int timer_int;
	public int id;
	public HungerState h_state;
	public int y_front,x_front,eat_counter=0;
	public int x_dir;
	public int  verSwim;
	public double v_ver_new=0,v_hor_new=0,v_old =0;

	public Swimmable() {
		super();
		horSpeed = 0;
		verSpeed = 0;
		swim=false;
	}
	public Swimmable(int hor, int ver) {
		super();
		horSpeed = hor;
		verSpeed = ver;
	}
	public int getHorSpeed() { return horSpeed; }
	public int getVerSpeed() { return verSpeed; }
	public void setHorSpeed(int hor) { horSpeed  = hor; }
	public void setVerSpeed(int ver) { verSpeed  = ver; }

	abstract public String getAnimalName();
	abstract public void drawCreature(Graphics g);
	abstract public void setSuspend();
	abstract public void setResume();
	abstract public void setBarrier(CyclicBarrier b);
	abstract public int getSize();
	abstract public void eatInc();
	abstract public int getEatCount();
	abstract public String getColor();
	abstract public Color getCol();
	abstract public Swimmable clone();
	abstract public void setSize(int size);
	abstract public void setColor(String color);
	abstract public void setColor(Color color);
	abstract public void setState(HungerState state);
	abstract public HungerState getHungerState();
	abstract public void setNewDir();
	abstract public Object getAnimalObj();
	abstract public String getColorName(Color c) ;
	abstract public Memento saveStateToMemento();
	abstract public void getStateFromMemento(Memento Memento);
}

