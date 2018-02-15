/**
 * HW4 java 
 * Campus: Bear Sheva
 * Student 1: Id:201451622  Name: Ilay Karamani 
 * Student 2: Id:301572046  Name: Amir Moyal 

 */

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddPlantDialog extends JDialog{

	
	private static final long serialVersionUID = 1L;
	public JTextField size,x,y,color_lbl;
	public int is_OK;
	JComboBox<Object> typeComboBox;

	// constructor
	public AddPlantDialog()
	{
		super();
		Object[] typePossibilities = {"Laminaria", "Zostera"};
		this.typeComboBox=new JComboBox<Object>(typePossibilities);
		this.typeComboBox.setSelectedItem("Laminaria");
		
		this.size= new JTextField(5);
		this.x= new JTextField(5);
		this.y= new JTextField(5);

	}
	
	
	// add animal create our dialog box
		public void AddPlant()
		{
			JPanel myPanel = new JPanel();
			myPanel.setLayout(new GridLayout(5, 1 )); 

			//is_validate=true;
			//jcombo for type of animal
			myPanel.add(new JLabel("Choose Plant Type:"));
			myPanel.add(typeComboBox);
			myPanel.add(new JLabel("	- Default  is Laminaria. "));
			//input box for animal details
			myPanel.add(new JLabel("Size:"));
			myPanel.add(size);
			myPanel.add(new JLabel("	- Range: 180-320."));
			myPanel.add(new JLabel("X position:"));
			myPanel.add(x);
			myPanel.add(new JLabel("	- Range: 70-1700."));
			myPanel.add(new JLabel("Y position: Bottom of the aquarium"));
			myPanel.add(new JLabel(" "),"wrap");

			//myPanel.add(y);
			//myPanel.add(new JLabel("	- Range: 1-10."));
			//jcombo for color of animal
			//myPanel.add(new JLabel("Choose Color :"));
			//myPanel.add(colorComboBox);
			myPanel.add(new JLabel("	- Default color is green. "));
			//general info for user
			//myPanel.add(new JLabel(" " + " " + "If you dont enter some values it will take default ones!"));
			//get type from user
			is_OK = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter plant details:",
					JOptionPane.OK_CANCEL_OPTION);
			
		}


}
