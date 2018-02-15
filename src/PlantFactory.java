/**
 * HW4 java 
 * Campus: Bear Sheva
 * Student 1: Id:201451622  Name: Ilay Karamani 
 * Student 2: Id:301572046  Name: Amir Moyal 

 */


import java.util.HashSet;

import javax.swing.JOptionPane;

public class PlantFactory extends Thread implements AbstractSeaFactory {

	public int counter;
	public HashSet<SeaCreature> t_set_p;
	boolean is_validate;
	AquaPanel pnl;
	AddPlantDialog plntDlg=new AddPlantDialog();

	//default constructor
	public PlantFactory()
	{
		counter=0;
	}

	public PlantFactory(HashSet<SeaCreature> t_set_other,int[] plant_counter,AquaPanel panel){
		counter=plant_counter[0];
		t_set_p=t_set_other;
		pnl=panel;

		//create dialog box and get details
		plntDlg.AddPlant();

		if (plntDlg.is_OK==JOptionPane.OK_OPTION) 
		{

			is_validate=true;
			
			//check if user dident input nothing
			if (!plntDlg.size.getText().isEmpty()) 
			{
				// check if input is correct
				if (Integer.parseInt(plntDlg.size.getText()) < 180 || Integer.parseInt(plntDlg.size.getText()) > 320)
					is_validate=false;
			}
			else
				is_validate=false;
			//check if user dident input nothing
			if( !plntDlg.x.getText().isEmpty() )
			{
				if (Integer.parseInt(plntDlg.x.getText()) > 1700 || Integer.parseInt(plntDlg.x.getText()) < 70)
					is_validate=false;
			}
			else
				is_validate=false;

			// if user input valid data - CREATE RELEVANT ANIMEL WITH DETAILS
			if(is_validate) 
			{
				counter++;
				//if user choose to create a fish -- or default choose
				if (plntDlg.typeComboBox.getSelectedItem() == "Laminaria") {

					this.produceSeaCreature("Laminaria");
				}
				else
				{
					this.produceSeaCreature("Zostera");
				}
			}
			else
				JOptionPane.showMessageDialog(null, "At-List One of the details is INVALID!!");
		}
		plant_counter[0]=counter;


	}
	
	public void produceSeaCreature(String type){		
		SeaCreature s_c;

		if("Laminaria".equals(type))
		{
			Immobile plant=new Laminaria(plntDlg,pnl);
			s_c=plant;
			t_set_p.add(s_c);
			
		}
		else // create JellyFish
		{
			
			Immobile plant=new Zostera(plntDlg,pnl);
	
			s_c=plant;
			t_set_p.add(s_c);
			
		}

	}

	
}
