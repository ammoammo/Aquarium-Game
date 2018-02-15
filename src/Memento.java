/**
 * HW4 java 
 * Campus: Bear Sheva
 * Student 1: Id:201451622  Name: Ilay Karamani 
 * Student 2: Id:301572046  Name: Amir Moyal 

 */


//import java.awt.Color;

//import com.sun.corba.se.spi.orbutil.fsm.State;

public class Memento {

	/** 
	 * mem_col[0],
	 * mem_size[1],
	 * mem_horSpeed[2],
	 * mem_verSpeed[3],
	 * mem_x_front[4] ,
	 * mem_y_front[5];
	 * **/
	
	private String[] data;

	public Memento(String[] state){
		
		if(state.length>6) // save state for SeaCreature -- fish/jellyfish
		{
			data = new String[8];
			this.data[0] = state[0]; //mem_col
			this.data[1] = state[1]; //mem_size
			this.data[2] = state[2]; //mem_horSpeed
			this.data[3] = state[3]; //mem_verSpeed
			this.data[4] = state[4]; //mem_x_front
			this.data[5] = state[5]; //mem_y_front
			this.data[6] = state[6]; //id
			this.data[7] = state[7]; //type
			
		}
		else  // save state for SeaPlants
		{
			data = new String[6];
			this.data[0] = state[0]; //mem_col
			this.data[1] = state[1]; //mem_size
			this.data[2] = state[2]; //mem_x_front
			this.data[3] = state[3]; //mem_y_front
			this.data[4] = state[4]; //id
			this.data[5] = state[5]; //type


			
		}
	}

	public String[] getState(){
		
		return data;
	}	
}
