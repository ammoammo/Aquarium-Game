/**
 * HW4 java 
 * Campus: Bear Sheva
 * Student 1: Id:201451622  Name: Ilay Karamani 
 * Student 2: Id:301572046  Name: Amir Moyal 

 */

import java.util.HashSet;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AnimalFactory extends Thread implements AbstractSeaFactory {

	public int counter;
	public HashSet<SeaCreature> t_set;
	boolean is_validate;
	AquaPanel pnl;
	AddAnimalDialog anmDlg=new AddAnimalDialog();

	//default constructor
	public AnimalFactory()
	{
		counter=0;
	}
	
	
	public AnimalFactory(HashSet<SeaCreature> t_set_other,int[] animal_counter,AquaPanel panel){
		counter=animal_counter[0];
		t_set=t_set_other;
		pnl=panel;

		//create dialog box and get details
		anmDlg.AddAnimal();

		if (anmDlg.is_OK==JOptionPane.OK_OPTION) 
		{

			is_validate=true;
			//check if user dident input nothing
			if (!anmDlg.size.getText().isEmpty()) 
			{
				// check if input is correct
				if (Integer.parseInt(anmDlg.size.getText()) < 20 || Integer.parseInt(anmDlg.size.getText()) > 320)
					is_validate=false;
			}
			//check if user dident input nothing
			if( !anmDlg.verSpeed.getText().isEmpty() )
			{
				if (Integer.parseInt(anmDlg.verSpeed.getText()) > 10 || Integer.parseInt(anmDlg.verSpeed.getText()) < 1)
					is_validate=false;
			}
			//check if user dident input nothing
			if(!anmDlg.horSpeed.getText().isEmpty())
			{
				if (Integer.parseInt(anmDlg.horSpeed.getText()) > 10 || Integer.parseInt(anmDlg.horSpeed.getText()) < 1)
					is_validate=false;	
			}
			if(!anmDlg.timer_lbl.getText().isEmpty())
			{
				if (Integer.parseInt(anmDlg.timer_lbl.getText()) > 300 || Integer.parseInt(anmDlg.timer_lbl.getText()) < 30)
					is_validate=false;	
			}


			// if user input valid data - CREATE RELEVANT ANIMEL WITH DETAILS
			if(is_validate) 
			{
				counter++;
				//if user choose to create a fish -- or default choose
				if (anmDlg.typeComboBox.getSelectedItem() == "Fish") {

					this.produceSeaCreature("Fish");
				}
				else
				{
					this.produceSeaCreature("Jellyfish");
				}
			}
			else
				JOptionPane.showMessageDialog(null, "At-List One of the details is INVALID!!");
		}
		
		
		animal_counter[0]=counter;
		//	Add();



	}


	public void produceSeaCreature(String type){		
		SeaCreature s_c;


		if("Fish".equals(type))
		{
			Swimmable fish=new Fish(anmDlg,pnl);
			
			fish.start();
			s_c=fish;
			
			t_set.add(s_c);
			

		}
		else // create JellyFish
		{
			
			Swimmable j_fish=new Jellyfish(anmDlg,pnl);
			j_fish.start();
			s_c=j_fish;
			t_set.add(s_c);
			
		}

	}

}