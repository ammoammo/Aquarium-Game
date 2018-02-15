/**
 * HW4 java 
 * Campus: Bear Sheva
 * Student 1: Id:201451622  Name: Ilay Karamani 
 * Student 2: Id:301572046  Name: Amir Moyal 

 */


import java.awt.Color;

import javax.swing.JColorChooser;

public class MarineAnimalDecorator implements MarineAnimal{

	MarineAnimal m_a ;
	Swimmable animal;
	
	
	public MarineAnimalDecorator(Swimmable anml) {
		// TODO Auto-generated constructor stub
		animal =anml;
		this.PaintFish();
	}
	
	@Override
	public void PaintFish() {
		// TODO Auto-generated method stub
		Color anml_color = JColorChooser.showDialog(null, "Change Button Background",null);
        if(anml_color!=null)
        {
        	animal.setColor(anml_color);
        	animal.setColor(animal.getColorName(animal.getCol()));
        }
	}

	
}
