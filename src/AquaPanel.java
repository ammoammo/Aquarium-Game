/**
 * HW4 java 
 * Campus: Bear Sheva
 * Student 1: Id:201451622  Name: Ilay Karamani 
 * Student 2: Id:301572046  Name: Amir Moyal 

 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.concurrent.CyclicBarrier;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

//import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

//import sun.awt.image.PNGImageDecoder.PNGException;
//import sun.security.jca.GetInstance;


public class AquaPanel extends JPanel implements ActionListener{

	/**
	 * Panel to manage all activities of animals to be created in our aquarium
	 */
	private static final long serialVersionUID = 1L;
	final static String IMAGES_PATH = "image/";
	final static String IMAGES_SUFIX = ".jpg";
	private static final Object Hungry = new Hungry();

	private List<Memento> mementoList = new ArrayList<Memento>();

	//private AquaFrame aquaf;
	public CyclicBarrier barrier;

	private BufferedImage img=null;
	JFileChooser chooser = new JFileChooser();
	File file ;

	//lower panel
	JPanel southPanel= new JPanel();
	private String[] lowBarNames = {"Add Sea Creature","Sleep","Wake-Up","Reset","Food","Info","Duplicate Animal","Change Color","Exit"};
	private JButton[] lowBar;
	BorderLayout myBorderLayout = new BorderLayout();

	public boolean is_validate=true,isFood=false,eatenAtlistOnce=false; // check user input**check eating state** 

	int[] animal_counter={0};
	int[] plant_counter={0};



	Worm worm;

	// HashSet declaration
	HashSet<SeaCreature> t_set = new HashSet<SeaCreature>();
	HashSet<SeaCreature> t_set_p = new HashSet<SeaCreature>();

	JPanel optionPanel = new JPanel();

	JButton anml_button=new JButton("Add Animal");
	JButton plnts_button=new JButton("Add Plant");

	//info table
	int click_num=0;
	String[] columnNames = {"Sea Creature","Color","Size","Hor.speed","Ver.speed","Eat counter"};
	String tblData[][]={
			{null,null,null,null,null,null},
			{null,null,null,null,null,null},
			{null,null,null,null,null,null},
			{null,null,null,null,null,null},
			{null,null,null,null,null,null},
			{null,null,null,null,null,null},
			{null,null,null,null,null,null},
			{null,null,null,null,null,null},
			{null,null,null,null,null,null},
			{null,null,null,null,null,null},
			{"Total",null,null,null,null,null}
	};
	JTable tbl;
	JScrollPane tableScroll;



	//constructor
	public AquaPanel(AquaFrame aquaFrame) {
		super();

		Prototype.loadCache(this); // load animals prototype

		this.setSize(aquaFrame.getSize());
		this.setBackground(Color.white);

		this.setLayout(myBorderLayout);		
		//amount of buttons in lower panel
		lowBar = new JButton[lowBarNames.length];

		//add Button to the south panel
		for(int i=0;i<lowBarNames.length;i++)
		{
			lowBar[i]=new JButton(lowBarNames[i]);
			lowBar[i].setPreferredSize(new Dimension(140,50));
			lowBar[i].addActionListener(this);
			southPanel.add(lowBar[i]);

		}

		//add Panel to the South
		this.add(southPanel,BorderLayout.SOUTH);


		//panel to choose animal/plant
		anml_button.setPreferredSize(new Dimension(5,50));
		plnts_button.setPreferredSize(new Dimension(5,50));
		anml_button.addActionListener(this);
		plnts_button.addActionListener(this);
		optionPanel.setLayout(new GridLayout(1, 1 ));
		optionPanel.add(anml_button);
		optionPanel.add(plnts_button);			
		optionPanel.setVisible(true);



		//info table
		tbl = new JTable(tblData,columnNames);


	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// if image is not null then user want to draw it as background
		if(img!=null ) 
			g.drawImage(img, 0, 0,getWidth(),getHeight(), this);

		// if the worm (the food) was set in panel
		if(isFood)
		{
			worm.drawWorm(g);

		}

		//create a copy of our Thread set
		SeaCreature[] anml=t_set.toArray(new SeaCreature[t_set.size()]);
		SeaCreature[] plnt=t_set_p.toArray(new SeaCreature[t_set_p.size()]);


		//draw all creatures
		for(int i=0;i<anml.length;i++)
		{
			anml[i].drawCreature(g);
		}
		for(int i=0;i<plnt.length;i++)
		{
			plnt[i].drawCreature(g);
		}	
	}

	// set background to jpanel
	public void setBackgr(int back)
	{
		// change background to None
		if(back==0)
		{
			img=null;
			//remove all components in panel.
			this.removeAll(); 
			// refresh the panel.
			this.updateUI();

			//add Panel to the NORTH
			this.add(southPanel,BorderLayout.SOUTH);

			this.setBackground(Color.white);
		}
		// change background to Blue
		if(back==1)
		{
			img=null;
			//remove all components in panel.
			this.removeAll(); 
			// refresh the panel.
			this.updateUI();
			//add Panel to the South
			this.add(southPanel,BorderLayout.SOUTH);

			this.setBackground(Color.blue);
		}
		// change background to image
		if(back==2)
		{
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setMultiSelectionEnabled(false);
			chooser.setFileFilter(filter);
			int choice = chooser.showOpenDialog(this);
			if(choice == JFileChooser.APPROVE_OPTION) {
				file = chooser.getSelectedFile();
				try {
					img = ImageIO.read(file);
				} catch(IOException e) {
					System.out.println("read error: " + e.getMessage());
				}

				int x = img.getWidth();
				int y = img.getHeight();
				setPreferredSize(new Dimension(x , y));
				revalidate();
				repaint();
			}

		}
	}

	@Override
	public  void actionPerformed(ActionEvent e) {

		if(e.getSource() == lowBar[0]) // if user clicked add sea creature button
		{

			this.remove(southPanel);
			this.add(optionPanel,BorderLayout.SOUTH);
			this.validate();

			this.repaint();
		}
		else if(e.getSource() == lowBar[1]) //  if user clicked  sleep button 
		{

			//copy the threads set to array
			Swimmable[] animals = t_set.toArray(new Swimmable[t_set.size()]);

			//suspend Threads
			for(int i=0;i<animals.length;i++)
			{
				animals[i].setSuspend();
			}
		}
		else if(e.getSource() == lowBar[2]) //wake up
		{
			synchronized(this)
			{
				Swimmable[] animals = t_set.toArray(new Swimmable[t_set.size()]);

				// notify thread
				for(int i=0;i<animals.length;i++)
				{
					animals[i].setResume();
					animals[i].timer.restart();
				}
			}
		}
		else if(e.getSource() == lowBar[3]) // if user clicked button reset button
		{
			Swimmable[] animals = t_set.toArray(new Swimmable[t_set.size()]);
			for(int i=0;i<animals.length;i++)
			{
				animals[i].timer.stop();
			}
			t_set.removeAll(t_set);
			t_set_p.removeAll(t_set_p);
			animal_counter[0]=0;
			plant_counter[0]=0;

			mementoList.clear();

			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.setFood(false);

			// update info table with relevant data -- clear all data after reset
			for(int i=0;i<11;i++)
			{
				for(int j=0;j<6;j++)
				{
					tbl.setValueAt(null, i, j);

					// if table cell is the total cell
					if(i==10 && j==0)
						tbl.setValueAt("Total", i, j);

				}
			}

		}
		else if(e.getSource() == lowBar[4]) // if user clicked  food button
		{   

			// if at list one animal created
			if(animal_counter[0]>0 )
			{
				//set barriers for animals(threads)
				synchronized (this) {


					//create worm -- as singleton	
					worm=Worm.getInstance(this); 					

					barrier = new CyclicBarrier(t_set.size());
					this.setFood(true);
					Swimmable[] animals = t_set.toArray(new Swimmable[t_set.size()]);

					// update info table with relevant data
					for(int i=0;i<animals.length;i++)
					{

						{
							animals[i].setBarrier(barrier);
							animals[i].isBarrier=true;
						}

					}

				}
			}
			else
				JOptionPane.showMessageDialog(this, "Cant add food before creating at list one animal!");

		}
		else if(e.getSource() == lowBar[5]) // if user clicked info button
		{

			infoTable();

		}
		else if(e.getSource() == lowBar[6]) // if user clicked duplicate button
		{
			// if at list one animal created
			if(animal_counter[0]>0  && animal_counter[0]<5)
			{
				JPanel copyPanel = new JPanel();
				copyPanel.setLayout(new GridLayout(6, 1 )); 

				Swimmable[] anmls_to_copy = t_set.toArray(new Swimmable[t_set.size()]);
				ButtonGroup group = new ButtonGroup();

				JRadioButton[] r_button = new JRadioButton[anmls_to_copy.length];

				String[] anml_data =  new String[anmls_to_copy.length];

				// sava data of all animals as string array
				for(int i=0;i<anmls_to_copy.length;i++)
				{

					anml_data[i]= "- " + anmls_to_copy[i].getAnimalName() + ", " +
							"Size: " + anmls_to_copy[i].getSize() + ", " +
							"horSpeed: " + anmls_to_copy[i].getHorSpeed() + ", " +
							"verSpeed: " + anmls_to_copy[i].getVerSpeed() + ", " +
							"Color: " + anmls_to_copy[i].getColor() + " ." ;
				}

				// add radio Buttons to panel
				for(int i=0;i<anmls_to_copy.length;i++)
				{

					r_button[i]=new JRadioButton(anml_data[i]);

					group.add(r_button[i]);
					r_button[i].setSelected(true);
					copyPanel.add(r_button[i]);
				}
				//int is_OK;
				int is_OK=JOptionPane.showConfirmDialog(null, copyPanel, "Please Choose Animal To Copy:",
						JOptionPane.OK_CANCEL_OPTION);
				if(is_OK==JOptionPane.OK_OPTION)
				{
					Swimmable copy_fish=new Fish(this);
					JTextField size_box,verSpeed_box,horSpeed_box;
					JComboBox<Object> colorComboBox;
					Object[] colorPossibilities = {"Default","Red", "Green", "Blue"};
					colorComboBox=new JComboBox<Object>(colorPossibilities);


					for(int i=0;i<anmls_to_copy.length;i++)
					{
						if(r_button[i].isSelected())
						{
							String type= anmls_to_copy[i].getAnimalName();
							if(type=="Fish")
							{

								copy_fish =Prototype.getAnimal("Fish");

								copy_fish.timer_int=anmls_to_copy[i].timer_int;

								copy_fish.id=anmls_to_copy[i].id;

								copy_fish.verSpeed=anmls_to_copy[i].getVerSpeed();
								copy_fish.horSpeed=anmls_to_copy[i].getHorSpeed();

								copy_fish.setSize(anmls_to_copy[i].getSize());

								copy_fish.setColor(anmls_to_copy[i].getCol());

								copy_fish.setColor(anmls_to_copy[i].getColorName(copy_fish.getCol()));

								colorComboBox.setSelectedItem("Default");

							}
							else
							{
								copy_fish =Prototype.getAnimal("Jellyfish");

								copy_fish.timer_int=anmls_to_copy[i].timer_int;
								copy_fish.id=anmls_to_copy[i].id;

								copy_fish.verSpeed=anmls_to_copy[i].getVerSpeed();
								copy_fish.horSpeed=anmls_to_copy[i].getHorSpeed();

								copy_fish.setSize(anmls_to_copy[i].getSize());

								copy_fish.setColor(anmls_to_copy[i].getCol());

								copy_fish.setColor(anmls_to_copy[i].getColorName(copy_fish.getCol()));

								colorComboBox.setSelectedItem("Default");

							}
						}
					}
					//last dialog -- keep copy or change properties of copied animal
					JPanel myPanel = new JPanel();
					myPanel.setLayout(new GridLayout(0, 1)); 
					size_box = new JTextField(Integer.toString(copy_fish.getSize()));
					verSpeed_box = new JTextField(Integer.toString(copy_fish.getVerSpeed()));
					horSpeed_box = new JTextField(Integer.toString(copy_fish.getHorSpeed()));

					//jcombo for type of animal
					myPanel.add(new JLabel("Animel Type:"));
					//myPanel.add(typeComboBox);
					if(copy_fish.getAnimalName()=="Fish")
						myPanel.add(new JLabel(" Fish. "));
					else
						myPanel.add(new JLabel(" Jellyfish. "));
					//input box for animal details
					myPanel.add(new JLabel(" "));
					myPanel.add(new JLabel("Size:"));
					myPanel.add(size_box);
					myPanel.add(new JLabel("	- Range: 20-320."));
					myPanel.add(new JLabel("Vertical Speed:"));
					myPanel.add(verSpeed_box);
					myPanel.add(new JLabel("	- Range: 1-10."));
					myPanel.add(new JLabel("Horizontal Speed:"));
					myPanel.add(horSpeed_box);
					myPanel.add(new JLabel("	- Range: 1-10."));
					//jcombo for color of animal
					myPanel.add(new JLabel("Choose Color :"));
					myPanel.add(colorComboBox);

					//general info for user

					//get type from user
					is_OK = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter Animal details:",
							JOptionPane.OK_CANCEL_OPTION);
					if(is_OK==JOptionPane.OK_OPTION)
					{
						is_validate=true;
						//check if user dident input nothing
						if (!size_box.getText().isEmpty()) 
						{
							// check if input is correct
							if (Integer.parseInt(size_box.getText()) < 20 || Integer.parseInt(size_box.getText()) > 320)
								is_validate=false;
						}
						else
							is_validate=false;
						//check if user dident input nothing
						if( !verSpeed_box.getText().isEmpty() )
						{
							if (Integer.parseInt(verSpeed_box.getText()) > 10 || Integer.parseInt(verSpeed_box.getText()) < 1)
								is_validate=false;
						}
						else
							is_validate=false;
						//check if user dident input nothing
						if(!horSpeed_box.getText().isEmpty())
						{
							if (Integer.parseInt(horSpeed_box.getText()) > 10 || Integer.parseInt(horSpeed_box.getText()) < 1)
								is_validate=false;	
						}
						else
							is_validate=false;
						// if user input valid data - CREATE RELEVANT ANIMEL WITH DETAILS
						if(is_validate) 
						{

							copy_fish.verSpeed=Integer.parseInt(verSpeed_box.getText());
							copy_fish.horSpeed=Integer.parseInt(horSpeed_box.getText());

							copy_fish.setSize(Integer.parseInt(size_box.getText()));

							copy_fish.id=copy_fish.id+1;

							if(colorComboBox.getSelectedItem().toString()!="Default")
							{
								copy_fish.setColor(colorComboBox.getSelectedItem().toString());

							}

							final int temp_id=copy_fish.id;
							copy_fish.timer = new Timer(copy_fish.timer_int*1000, new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {

									foodNotify(temp_id);

								}

							});


							animal_counter[0]=animal_counter[0]+1;	

							copy_fish.start();

							copy_fish.timer.restart();

							t_set.add(copy_fish);

						}
						else
							JOptionPane.showMessageDialog(null, "At-List One of the details is INVALID!!");


					}
				}

			}
			else
			{
				if(animal_counter[0]==0)
				{
					JOptionPane.showMessageDialog(this, "Cant Duplicate Animals before creating at list one animal!");
				}
				else
					JOptionPane.showMessageDialog(this, "Max NO. of Animals is 5, CANT DUPLICATE any more!");

			}

		}
		else if(e.getSource() == lowBar[7]) // if user clicked change color button
		{
			// if at list one animal created
			if(animal_counter[0]>0 )
			{
				JPanel JPanelDecorator = new JPanel();
				JPanelDecorator.setLayout(new GridLayout(6, 1 )); 				

				Swimmable[] anmls_array = t_set.toArray(new Swimmable[t_set.size()]);
				ButtonGroup group = new ButtonGroup();
				//new JButton[lowBarNames.length];
				JRadioButton[] r_button = new JRadioButton[anmls_array.length];

				String[] anml_data =  new String[anmls_array.length];

				// sava data of all animals as string array
				for(int i=0;i<anmls_array.length;i++)
				{

					anml_data[i]= "- " + anmls_array[i].getAnimalName() + ", " +
							"Size: " + anmls_array[i].getSize() + ", " +
							"horSpeed: " + anmls_array[i].getHorSpeed() + ", " +
							"verSpeed: " + anmls_array[i].getVerSpeed() + ", " +
							"Color: " + anmls_array[i].getColor() + " ." ;
				}

				// add radio Buttons to panel
				for(int i=0;i<anmls_array.length;i++)
				{

					r_button[i]=new JRadioButton(anml_data[i]);

					group.add(r_button[i]);
					r_button[i].setSelected(true);
					JPanelDecorator.add(r_button[i]);
				}



				//JOptionPane
				int is_OK=JOptionPane.showOptionDialog(null, 
						JPanelDecorator, 
						"Please Choose Animal To Change Color:",
						JOptionPane.OK_CANCEL_OPTION, 
						JOptionPane.INFORMATION_MESSAGE, 
						null, 
						new String[]{"Change Color", "Cancel"}, // this is the array
						"default");

				//if user clicked change color
				if(is_OK == JOptionPane.OK_OPTION )
				{ 

					//check which animal was selected
					for(int i=0;i<anmls_array.length;i++)
					{
						if(r_button[i].isSelected())
						{
							String type = anmls_array[i].getAnimalName();
							if(type=="Fish")
							{
								Fish fish =  (Fish)anmls_array[i].getAnimalObj();								
								fish.PaintFish();

								/** DO_NOT DELETE
									//new MarineAnimalDecorator(fish);
								 **/

								SeaCreature anml_s_c = (SeaCreature)anmls_array[i].getAnimalObj();

								t_set.remove(anml_s_c);
								t_set.add(fish);

							}
							else	
							{
								Jellyfish j_fish =  (Jellyfish)anmls_array[i].getAnimalObj();

								j_fish.PaintFish();

								/** DO_NOT DELETE
								//new MarineAnimalDecorator(j_fish);
								 **/

								SeaCreature anml_s_c = (SeaCreature)anmls_array[i].getAnimalObj();

								t_set.remove(anml_s_c);
								t_set.add(j_fish);

							}

						}
					}

				}



			}
			else
				JOptionPane.showMessageDialog(this, "Cant Change color to Animals before creating at list one animal!");

		}
		else if(e.getSource() == lowBar[8]) // if user clicked exit button
		{
			System.exit(0);
		}
		if(e.getSource()==anml_button) // if user clicked add animal (after clicking add sea creature)
		{
			this.remove(optionPanel);
			this.add(southPanel,BorderLayout.SOUTH);
			this.revalidate();
			this.repaint();

			if(animal_counter[0]<5) // check if user can add new animals -- 5 limit
			{
				AbstractSeaFactory a= new AnimalFactory(t_set,animal_counter,this) ;
			}
			else // if user already created 5 animals
			{
				JOptionPane.showMessageDialog(this, "Cant Add More Then 5 Animals!");
			}
		}
		if(e.getSource()==plnts_button)  // if user clicked add plant (after clicking add sea creature)
		{
			this.remove(optionPanel);
			this.add(southPanel,BorderLayout.SOUTH);
			this.revalidate();
			this.repaint();

			if(plant_counter[0]<5) // check if user can add new plants -- 5 limit
			{
				AbstractSeaFactory a= new PlantFactory(t_set_p,plant_counter,this) ;
			}
			else // if user already created 5 animals
			{
				JOptionPane.showMessageDialog(this, "Cant Add More Then 5 Plants!");
			}
		}



	}

	public void infoTable()
	{
		// get animals from hashset
		Swimmable[] animals = t_set.toArray(new Swimmable[t_set.size()]);
		Immobile[] plants = t_set_p.toArray(new Immobile[t_set_p.size()]);

		int index=0;

		int i;
		// update info table with animal relevant data
		for( i=0;i<animals.length;i++)
		{
			for(int j=0;j<6;j++)
			{
				if(j==0) // animal type
					tbl.setValueAt(animals[i].getAnimalName(), i, j);
				if(j==1) // animal color
					tbl.setValueAt(animals[i].getColor(), i, j);
				if(j==2) // animal size			
					tbl.setValueAt(Integer.toString(animals[i].getSize()), i, j);
				if(j==3) // animal horSpeed
					tbl.setValueAt(Integer.toString(animals[i].getHorSpeed()), i, j);
				if(j==4) // animal verSpeed
					tbl.setValueAt(Integer.toString(animals[i].getVerSpeed()), i, j);
				if(j==5) // animal eat counter
					tbl.setValueAt(Integer.toString(animals[i].getEatCount()), i, j);

			}

		}
		index=i;
		// update info table with plants relevant data
		for(i=0;i<plants.length;i++)
		{
			for(int j=0;j<6;j++)
			{
				if(j==0) // plant type
					tbl.setValueAt(plants[i].getPlantName(), i+index, j);
				if(j==1) // plant color
					tbl.setValueAt(plants[i].getColor(), i+index, j);
				if(j==2) // plant size			
					tbl.setValueAt(Integer.toString(plants[i].getSize()), i+index, j);
				if(j==3) // animal horSpeed
					tbl.setValueAt(Integer.toString(0), i+index, j);
				if(j==4) // animal verSpeed
					tbl.setValueAt(Integer.toString(0), i+index, j);
				if(j==5) // animal eat counter
					tbl.setValueAt(Integer.toString(0), i+index, j);
			}
		}

		int sum=0;
		tbl.setValueAt(Integer.toString(sum), 10, 5);
		//calc total and add to table total
		if(eatenAtlistOnce)
		{
			for(i=0;i<t_set.size();i++)
			{

				sum=sum+(animals[i].getEatCount());
			}
			tbl.setValueAt(Integer.toString(sum), 10, 5);
		}


		//// check if table was shown already
		if (click_num>0) {
			this.remove(tableScroll);

		}
		click_num++;
		if(click_num%2!=0)
		{

			tableScroll = new JScrollPane(tbl);
			tableScroll.setPreferredSize(new Dimension(200,200));
			tableScroll.setBounds(this.getBounds());

			this.add(tableScroll,BorderLayout.NORTH);

		}

		revalidate();
		repaint();

	}

	// synchronized function to set relevant values
	public synchronized void setFood(boolean food)
	{
		if(food)
		{
			this.isFood=true;
			eatenAtlistOnce=true;
		}
		else
		{
			this.isFood=false;
			if(eatenAtlistOnce)
			{
				this.worm.setInstance();
			}
		}
	}

	public void callback(Swimmable anml) {
		// TODO Auto-generated method stub
		anml.eatInc();

	}

	public void foodNotify(int id)
	{

		Swimmable[] anml=t_set.toArray(new Swimmable[t_set.size()]);
		Swimmable current_fish=new Fish(this);

		for(int i=0;i<anml.length;i++)
		{
			if(id==anml[i].id)
			{
				current_fish=anml[i];
			}
		}

		if(!isFood)
		{

			String anml_data = "- " + current_fish.getAnimalName() + ", " +
					"Size: " + current_fish.getSize() + ", " +
					"horSpeed: " + current_fish.getHorSpeed() + ", " +
					"verSpeed: " + current_fish.getVerSpeed() + ", " +
					"Color: " + current_fish.getColor() + ", " +
					"Hunger Every: "+current_fish.timer_int+" sec." ;


			JPanel feed_panel = new JPanel();
			feed_panel.setLayout(new GridLayout(2, 1 )); 

			JLabel lbl1 = new JLabel(anml_data +" is hungry! ");
			JLabel lbl2 = new JLabel("Click OK to drop food in tank");
			lbl1.setFont(lbl1.getFont().deriveFont(20.0f));
			lbl2.setFont(lbl2.getFont().deriveFont(18.0f));

			feed_panel.add(lbl1);
			feed_panel.add(lbl2);

			int is_OK = JOptionPane.showConfirmDialog(null, feed_panel,"Listener: Feed Animals:",
					JOptionPane.OK_CANCEL_OPTION);

			if (is_OK==JOptionPane.OK_OPTION) 
			{
				current_fish.setState(new Hungry());


				//set barriers for animals(threads)
				synchronized (this) {

					//create worm -- as singleton	
					worm=Worm.getInstance(this); 					

					barrier = new CyclicBarrier(t_set.size());
					this.setFood(true);
					//Swimmable[] animals = t_set.toArray(new Swimmable[t_set.size()]);

					// update info table with relevant data
					for(int i=0;i<anml.length;i++)
					{
						anml[i].setBarrier(barrier);
						anml[i].isBarrier=true;						
					}

				}
			}
			else
				anml[id-1].timer.restart();
		}
		/*else
		{
			//current_fish.setState(new Hungry());

		}*/

	}

	// save object state / data to momentoList
	public void setObjState()
	{
		if(animal_counter[0]>0 ||  plant_counter[0]>0)
		{
			JPanel saveStatePanel = new JPanel();
			saveStatePanel.setLayout(new GridLayout(6, 1 )); 

			Swimmable[] anmls_to_copy = t_set.toArray(new Swimmable[t_set.size()]);
			Immobile[] plants_to_copy = t_set_p.toArray(new Immobile[t_set_p.size()]);

			Object[] objects_arr = new Object[anmls_to_copy.length + plants_to_copy.length];

			for(int i=0;i<objects_arr.length;i++)
			{
				if(i<anmls_to_copy.length)
				{
					objects_arr[i]=(Swimmable)anmls_to_copy[i];
				}
				else
					objects_arr[i]=(Immobile)plants_to_copy[i-anmls_to_copy.length];
			}



			ButtonGroup group = new ButtonGroup();

			JRadioButton[] r_button = new JRadioButton[anmls_to_copy.length + plants_to_copy.length];

			String[] anml_data =  new String[anmls_to_copy.length ];
			String[] plants_data =  new String[plants_to_copy.length];



			// sava data of all animals as string array
			for(int i=0;i<anmls_to_copy.length;i++)
			{

				anml_data[i]= "- " + anmls_to_copy[i].getAnimalName() + ", " +
						"Size: " + anmls_to_copy[i].getSize() + ", " +
						"horSpeed: " + anmls_to_copy[i].getHorSpeed() + ", " +
						"verSpeed: " + anmls_to_copy[i].getVerSpeed() + ", " +
						"Color: " + anmls_to_copy[i].getColor() + " ." ;
			}

			// sava data of all plants as string array
			for(int i=0;i<plants_to_copy.length;i++)
			{
				plants_data[i]= "- " + plants_to_copy[i].getPlantName() + ", " +
						"Size: " + plants_to_copy[i].getSize() + ", " +
						"Color: " + plants_to_copy[i].getColor() + " ." ;
			}

			// add radio Buttons to panel
			for(int i=0;i<r_button.length;i++)
			{
				if(i<anmls_to_copy.length)
				{
					r_button[i]=new JRadioButton(anml_data[i]);

					group.add(r_button[i]);
					r_button[i].setSelected(true);
					saveStatePanel.add(r_button[i]);
				}
				else
				{
					r_button[i]=new JRadioButton(plants_data[i-anml_data.length]);

					group.add(r_button[i]);
					r_button[i].setSelected(true);
					saveStatePanel.add(r_button[i]);
				}
			}
			//int is_OK;
			int is_OK=JOptionPane.showConfirmDialog(null, saveStatePanel, "Please Choose Animal To Save:",
					JOptionPane.OK_CANCEL_OPTION);

			if(is_OK==JOptionPane.OK_OPTION)
			{
				for(int i=0;i<r_button.length;i++)
				{
					if(r_button[i].isSelected())
					{
						if(objects_arr[i] instanceof Swimmable)
						{
							Swimmable fish = (Swimmable)objects_arr[i];

							//loop to check if memento already exist
							for(int j=0;j<mementoList.size();j++)
							{
								Memento mem=mementoList.get(j);
								String[] dta = mem.getState();
								if(dta.length>6)
								{
									if(fish.id == Integer.parseInt(dta[6]))
									{
										mementoList.remove(j);
									}
								}
								
							}

							mementoList.add(fish.saveStateToMemento());

						}
						else
						{
							Immobile plant = (Immobile)objects_arr[i];

							//loop to check if memento already exist
							for(int j=0;j<mementoList.size();j++)
							{
								Memento mem=mementoList.get(j);
								String[] dta = mem.getState();
								if(dta.length<=6)
								{
									if(plant.id == Integer.parseInt(dta[4]))
									{
										mementoList.remove(j);
									}
								}
								
							}

							mementoList.add(plant.saveStateToMemento());
						}
					}

				}

			}

		}
		else
			JOptionPane.showMessageDialog(this, "Cant Save Objects before creating at list one Object!");
	}




	// load object state / data from momentoList
	public void getObjState()
	{
		if(!mementoList.isEmpty())
		{
			JPanel loadStatePanel = new JPanel();
			loadStatePanel.setLayout(new GridLayout(6, 1 )); 

			ButtonGroup group = new ButtonGroup();

			JRadioButton[] r_button = new JRadioButton[mementoList.size()];

			String[] mem_data= new String[mementoList.size()]; 

			// sava data of memento objects to string data
			for(int i=0;i<mementoList.size();i++)
			{
				String[] temp = mementoList.get(i).getState();		
				//if object is animal (NOT plant!)
				if(temp.length>6)
				{
					mem_data[i] = "- " + temp[7] + ", " +
							"Size: " + temp[1] + ", " +
							"horSpeed: " + temp[2] + ", " +
							"verSpeed: " + temp[3] + ", " +
							"x_front: " + temp[4] + ", " +
							"y_front: " + temp[5] + ", " +
							"Color: " + temp[0] + " ." ;
				}
				else
				{
					mem_data[i] = "- " + temp[5] + ", " +
							"Size: " + temp[1] + ", " +
							"x_front: " + temp[2] + ", " +
							"y_front: " + temp[3] + ", " +
							"Color: " + temp[0] + " ." ;
				}



			}

			// add radio Buttons to panel
			for(int i=0;i<r_button.length;i++)
			{

				r_button[i]=new JRadioButton(mem_data[i]);

				group.add(r_button[i]);
				r_button[i].setSelected(true);
				loadStatePanel.add(r_button[i]);
			}

			int is_OK=JOptionPane.showConfirmDialog(null, loadStatePanel, "Please Choose Animal To Load:",
					JOptionPane.OK_CANCEL_OPTION);

			if(is_OK==JOptionPane.OK_OPTION)
			{
				Swimmable[] anmls_to_copy = t_set.toArray(new Swimmable[t_set.size()]);
				Immobile[] plants_to_copy = t_set_p.toArray(new Immobile[t_set_p.size()]);

				for(int i=0;i<r_button.length;i++)
				{
					if(r_button[i].isSelected())
					{

						String[] temp = mementoList.get(i).getState();
						//if swimble
						if(temp.length>6)
						{
							for(int j=0; j<anmls_to_copy.length;j++)
							{
								//if this is the animel i want "load" state for
								if(anmls_to_copy[j].id==Integer.parseInt(temp[6]))
								{
									anmls_to_copy[j].getStateFromMemento(mementoList.get(i));
								}
							}

						}
						//if Immobile
						else
						{
							for(int j=0; j<plants_to_copy.length;j++)
							{
								//if this is the plant i want "load" state for
								if(plants_to_copy[j].id==Integer.parseInt(temp[4]))
								{
									plants_to_copy[j].getStateFromMemento(mementoList.get(i));
								}
							}
						}

					}					

				}
			}

		}
		else
			JOptionPane.showMessageDialog(this, "Cant Load Objects before creating at list one Object!");
	}
}



