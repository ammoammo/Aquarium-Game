/**
 * HW4 java 
 * Campus: Bear Sheva
 * Student 1: Id:201451622  Name: Ilay Karamani 
 * Student 2: Id:301572046  Name: Amir Moyal 

 */


import java.awt.Graphics;

public abstract class Immobile extends PlantFactory implements SeaCreature {
	String name;
	int id;
	
	public Immobile() {
		super();
		
	}
	
	abstract public String getPlantName();
	abstract public void drawCreature(Graphics g);
	abstract public int getSize();
	abstract public String getColor();
	abstract public Memento saveStateToMemento();
	abstract public void getStateFromMemento(Memento Memento);
}
