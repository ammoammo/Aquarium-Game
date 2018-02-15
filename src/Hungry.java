/**
 * HW4 java 
 * Campus: Bear Sheva
 * Student 1: Id:201451622  Name: Ilay Karamani 
 * Student 2: Id:301572046  Name: Amir Moyal 

 */

public class Hungry implements HungerState{

	@Override
	public void Swim(Swimmable anml,AquaPanel panel) {
		// TODO Auto-generated method stub
		//swim for fish
		if(anml.getAnimalName()=="Fish")
		{
			//x axis - swim to food on x axis
			if (anml.x_front < panel.getWidth()/2) // if fish is at left border of panel
			{
				anml.x_dir=1;
			}
			else
				anml.x_dir=-1;
			if (anml.y_front < panel.getHeight()/2) // if fish is at left border of panel
			{
				anml.verSwim=1;
			}
			else
				anml.verSwim=-1;

			anml.setNewDir(); // direct to food

			anml.x_front += (int)(anml.v_hor_new*anml.x_dir);
			anml.y_front += (int)(anml.v_ver_new*anml.verSwim);

		}
		//swim for Jelly-fish
		else
		{
			//x axis - swim to food on x axis
			if (anml.x_front < panel.getWidth()/2) // if fish is at left border of panel
			{
				anml.x_dir=1;
			}
			else
				anml.x_dir=-1;
			if (anml.y_front < panel.getHeight()/2) // if fish is at left border of panel
			{
				anml.verSwim=1;
			}
			else
				anml.verSwim=-1;

			anml.setNewDir(); // direct to food

			anml.x_front += (int)(anml.v_hor_new*anml.x_dir);
			anml.y_front += (int)(anml.v_ver_new*anml.verSwim);

		}
		
	}

}
