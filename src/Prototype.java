/**
 * HW4 java 
 * Campus: Bear Sheva
 * Student 1: Id:201451622  Name: Ilay Karamani 
 * Student 2: Id:301572046  Name: Amir Moyal 

 */


import java.util.Hashtable;

public class Prototype {

	private static Hashtable<String, Swimmable> shapeMap = new Hashtable<String, Swimmable>();

	//get prototype
	   public static Swimmable getAnimal(String anml) {
		   Swimmable cachedAnimal = shapeMap.get(anml);
	      return  cachedAnimal.clone();
	   }

	   // for each animal run database query and create animal
	   public static void loadCache(AquaPanel pnl) {
		   Swimmable fish = new Fish(pnl);
	      shapeMap.put(fish.getAnimalName(),fish);

	      Swimmable j_fish = new Jellyfish(pnl);
	      shapeMap.put(j_fish.getAnimalName(),j_fish);
	   }
}
