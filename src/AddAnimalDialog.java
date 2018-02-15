/**
 * HW4 java 
 * Campus: Bear Sheva
 * Student 1: Id:201451622  Name: Ilay Karamani 
 * Student 2: Id:301572046  Name: Amir Moyal 

 */
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;
public class AddAnimalDialog extends JDialog {

	
	private static final long serialVersionUID = 1L;
	public Color col;
	public JTextField size,verSpeed,horSpeed,timer_lbl;
	public int is_OK;
	JComboBox<Object> typeComboBox;
	JComboBox<Object> colorComboBox;

	// constructor
	public AddAnimalDialog()
	{
		super();
		Object[] typePossibilities = {"Fish", "Jellyfish"};
		Object[] colorPossibilities = {"Red", "Green", "Blue"};
		this.typeComboBox=new JComboBox<Object>(typePossibilities);
		this.typeComboBox.setSelectedItem("Fish");
		colorComboBox=new JComboBox<Object>(colorPossibilities);
		this.colorComboBox.setSelectedItem("Red");
		this.size= new JTextField(5);
		this.verSpeed= new JTextField(5);
		this.horSpeed= new JTextField(5);
		this.timer_lbl= new JTextField(5);
		this.col=Color.red;

	}

	// add animal create our dialog box
	public synchronized void AddAnimal()
	{
		JPanel myPanel = new JPanel();
		myPanel.setLayout(new GridLayout(7, 1 )); 

		
		//jcombo for type of animal
		myPanel.add(new JLabel("Choose Animel Type:"));
		myPanel.add(typeComboBox);
		myPanel.add(new JLabel("	- Default  is Fish. "));
		//input box for animal details
		myPanel.add(new JLabel("Size:"));
		myPanel.add(size);
		myPanel.add(new JLabel("	- Range: 20-320. Default size is 120. "));
		myPanel.add(new JLabel("Vertical Speed:"));
		myPanel.add(verSpeed);
		myPanel.add(new JLabel("	- Range: 1-10. Default speed is 1. "));
		myPanel.add(new JLabel("Horizontal Speed:"));
		myPanel.add(horSpeed);
		myPanel.add(new JLabel("	- Range: 1-10. Default speed is 1. "));
		//jcombo for color of animal
		myPanel.add(new JLabel("Choose Color :"));
		myPanel.add(colorComboBox);
		myPanel.add(new JLabel("	- Default  is red. "));
		// choose eat Frequency  in seconds label
		myPanel.add(new JLabel("Choose eat Frequency (in sec) :"));
		myPanel.add(timer_lbl);
		myPanel.add(new JLabel("	- Range: 30-300 sec. - Default  is every 60 sec. "));
		
		//general info for user
		myPanel.add(new JLabel(" " + " " + "If you dont enter some values it will take default ones!"));
		//get type from user
		is_OK = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter Animal details:",
				JOptionPane.OK_CANCEL_OPTION);
		
	}
}
