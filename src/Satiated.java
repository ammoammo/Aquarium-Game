/**
 * HW4 java 
 * Campus: Bear Sheva
 * Student 1: Id:201451622  Name: Ilay Karamani 
 * Student 2: Id:301572046  Name: Amir Moyal 

 */


public class Satiated implements HungerState{

	@Override
	public void Swim(Swimmable anml,AquaPanel panel) {
		// TODO Auto-generated method stub
		//swim for fish
		if(anml.getAnimalName()=="Fish")
		{

			if (anml.x_front < 260) // if fish leave left border of panel
			{
				anml.x_front += anml.horSpeed;
				anml.x_dir = 1;
			}
			else if (anml.x_front > (panel.getWidth() - 260)) // if fish leave right border of panel
			{
				anml.x_front -= anml.horSpeed;
				anml.x_dir = 0;
			} 
			else if (anml.x_dir == 1) {
				anml.x_front += anml.horSpeed;
			} 
			else if (anml.x_dir != 1) {
				anml.x_front -= anml.horSpeed;
			}

			// y axis
			if (anml.y_front < 60) // upper border
			{
				anml.verSwim = 0;
			} else if (anml.y_front > (panel.getHeight() - 90)) // lower border
			{
				anml.verSwim = 1;
			}
			if (anml.verSwim == 0) {
				anml.y_front += anml.verSpeed;
			} else
				anml.y_front -= anml.verSpeed;

		}
		//swim for Jelly-fish
		else
		{
			if (anml.x_front < 260) // if fish leave left border of panel
			{

				anml.x_front += anml.horSpeed;
				anml.x_dir = 1;
			}

			else if (anml.x_front > (panel.getWidth() - 260)) // if fish leave right border of panel
			{
				anml.x_front -= anml.horSpeed;
				anml.x_dir = 0;
			} else if (anml.x_dir == 1) {
				anml.x_front += anml.horSpeed;
			} else if (anml.x_dir != 1) {
				anml.x_front -= anml.horSpeed;
			}
			// y axis
			if (anml.y_front < 60) // upper border
			{
				anml.verSwim = 0;
			} else if (anml.y_front > (panel.getHeight() - 90)) // lower border
			{
				anml.verSwim = 1;
			}
			if (anml.verSwim == 0) {
				anml.y_front += anml.verSpeed;
			} else
				anml.y_front -= anml.verSpeed;
		}
	}

}
