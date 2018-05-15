//interface with textboxes and buttons to go through, add to and delete from an array of locomotives
//cap user entry to 10
//button finds if there are more than 4 wheel locomotives than 12 wheels locomotives
//button finds how many are blue
//button finds the loco id with the most wheels in the array.  first if tie.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIWindow extends JFrame{

	private Locomotive[] loco = new Locomotive[10];
	private Locomotive locoTemp;

	private int recCount = -1;  //add one for displaying purposes.
	private int currIndex = -1;
	private String message;

	private JLabel idLabel = new JLabel("ID: ");
	private JLabel wheelsLabel = new JLabel("Wheels: ");
	private JLabel colorLabel = new JLabel("Color: ");

	private JLabel recLabel = new JLabel("Num of Records: ");
	private JLabel indexLabel = new JLabel("Current Index: ");

	private JLabel blankLabel = new JLabel("");

	private JLabel blankLabel1 = new JLabel("");
	private JLabel blankLabel2 = new JLabel("");
	private JLabel blankLabel3 = new JLabel("");
	private JLabel blankLabel4 = new JLabel("");	

	private JTextField idField = new JTextField("");
	private JTextField wheelsField = new JTextField("4");
	private JTextField colorField= new JTextField("BLACK");

	private JTextField recField = new JTextField("");
	private JTextField indexField = new JTextField("");

	private JTextField commentField = new JTextField("Choo choo.  Let's get started.");

	private JButton addButton = new JButton("ADD");
	private JButton deleteButton= new JButton("DELETE");

	private JButton firstButton = new JButton("<<"); 
	private JButton previousButton = new JButton("<");
	private JButton nextButton = new JButton(">");
	private JButton lastButton = new JButton(">>");

	private JButton greater4Button = new JButton("4 > 12");
	private JButton blueButton = new JButton("# of Blue");
	private JButton	mostWheelsButton = new JButton("Most Wheels ID");
	
	public GUIWindow (){

		//for validating and other uses. cool.
		locoTemp = new Locomotive(0, 0, "");

		//prevent changes to some fields
		recField.setEditable(false);
		indexField.setEditable(false);
		commentField.setEditable(false);
		recField.setBackground(Color.lightGray);
		indexField.setBackground(Color.lightGray);
		commentField.setBackground(Color.lightGray);


		JPanel dataPanel = new JPanel(new GridLayout(4, 3, 12, 6));
		dataPanel.add(idLabel);
		dataPanel.add(wheelsLabel);
		dataPanel.add(colorLabel);

		dataPanel.add(idField);		
		dataPanel.add(wheelsField);		
		dataPanel.add(colorField);

		dataPanel.add(addButton);
		dataPanel.add(blankLabel);
		dataPanel.add(deleteButton);

		dataPanel.add(greater4Button);
		dataPanel.add(blueButton);
		dataPanel.add(mostWheelsButton);

		JPanel otherPanel = new JPanel(new GridLayout(2, 4, 12, 6));
		otherPanel.add(recLabel);
		otherPanel.add(recField);
		otherPanel.add(indexLabel);
		otherPanel.add(indexField);

		otherPanel.add(firstButton);
		otherPanel.add(previousButton);
		otherPanel.add(nextButton);
		otherPanel.add(lastButton);

		JPanel commentPanel = new JPanel(new GridLayout(1, 1, 12, 6));
		commentPanel.add(commentField);

		JPanel holdPanel = new JPanel(new GridLayout(2, 1, 12, 6));
		holdPanel.add(otherPanel);
		holdPanel.add(commentPanel);

		JPanel holdingPanel = new JPanel(new GridLayout(2, 1, 12, 6));
		holdingPanel.add(dataPanel);
		holdingPanel.add(holdPanel);
		
		Container container = getContentPane();
		container.add(holdingPanel);

		//listeners
		addButton.addActionListener(new AddListener());
		deleteButton.addActionListener(new DeleteListener());
		firstButton.addActionListener(new FirstListener());
		previousButton.addActionListener(new PreviousListener());
		nextButton.addActionListener(new NextListener());
		lastButton.addActionListener(new LastListener());
		
		greater4Button.addActionListener(new Greater4Listener());
		blueButton.addActionListener(new BlueListener());
		mostWheelsButton.addActionListener(new MostWheelsListener());

		//System.out.println(currRec);
	}
	
	//add
	private class AddListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(recCount >= 9){
				commentField.setText("No more data allowed.");
				return;
			}
			String input = idField.getText();
			String input2 = wheelsField.getText();
			String cirC = colorField.getText().trim().toUpperCase();
			
			String message = locoTemp.validate(input, input2, cirC);
			//System.out.println(message);
			if(message.equals("")){
				//System.out.println(locoTemp.getIdNum() + "<-----ID of locoTest.");
				//add by copying temp to array rec
				recCount++;
				currIndex = recCount;
				loco[currIndex] = new Locomotive(locoTemp);
				//System.out.println(loco[currIndex].getBodyColor() + "<------new record.");
				updateDisplay("Record added.");			
			}
			else
				commentField.setText("Not added -- " + message);
		}
			
	}

	//delete
	private class DeleteListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(recCount == -1){
				commentField.setText("Add a record first.");
				return;
			}
			//if the current record's index == recCount then recCount--
			if(currIndex == recCount){
				recCount--;
				currIndex = recCount;
				if(currIndex == -1){
					idField.setText("");
					wheelsField.setText("4");
					colorField.setText("BLACK");

					recField.setText("");
					indexField.setText("");
					
					commentField.setText("There are no more records.");		
				}
				else
					updateDisplay("Record was removed.");
			}
			if(currIndex < recCount){
				//move records down
				int track = currIndex;
				while((track + 1) <= recCount){
					loco[track] = new Locomotive(loco[track + 1]);
					track++;
				}
				recCount--;
				updateDisplay("Record was removed.");
			}

		}
	}

	//more 4 wheels than 12 wheels?
	private class Greater4Listener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(recCount == -1){
				commentField.setText("Add a record first.");
				return;
			}
			int c4 = 0, c12 = 0, numWh;
			for(int c = 0; c <= recCount; c++){
				numWh = loco[c].getNumWheels();
				if(numWh == 4) c4++;
				if(numWh == 12) c12++;
			}
			if(c4 > c12)
				commentField.setText("True.  There are more 4-wheels (" + c4 + ") than there are 12-wheels (" + c12 + ").");
			else
				commentField.setText("False.  There are as many or more 12-wheels than there are 4-wheels.");
		}
	}

	//count blue locos
	private class BlueListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(recCount == -1){
				commentField.setText("Add a record first.");
				return;
			}
			int cnt = 0;
			for(int c = 0; c <= recCount; c++){
				if(loco[c].getBodyColor().equals("BLUE"))
					cnt++;
			}
			commentField.setText("There are " + cnt + " blue locomotives.");
		}
	}

	//id with the most wheels
	private class MostWheelsListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(recCount == -1){
				commentField.setText("Add a record first.");
				return;
			}
			int idMostWheels = -1;
			int highCount = -1;
			for(int c = 0; c <= recCount; c++){
				if(loco[c].getNumWheels() > highCount){
					highCount = loco[c].getNumWheels();
					idMostWheels = loco[c].getIdNum();
					currIndex = c;
				}
			}
			updateDisplay("The first loco with the greatest number of wheels is ID " + idMostWheels + " as shown.");
		}
	}

	//navigation
	private class FirstListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(recCount == -1){
				commentField.setText("Add a record first.");
				return;
			}
			currIndex = 0;
			updateDisplay("This is the first record.");				
		}
	}

	private class PreviousListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(recCount == -1){
				commentField.setText("Add a record first.");
				return;
			}
			if(currIndex == 0){
				commentField.setText("You're already at the first record.");
				return;
			}
			currIndex--;
			updateDisplay("This is the previous record.");
		}	
	}

	private class NextListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(recCount == -1){
				commentField.setText("Add a record first.");
				return;
			}
			if(currIndex >= recCount){
				commentField.setText("You're already at the last record.");
				return;
			} 
			currIndex++;
			updateDisplay("This is the next record.");
		}	
	}

	private class LastListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(recCount == -1){
				commentField.setText("Add a record first.");
				return;
			}
			currIndex = recCount;
			updateDisplay("This is the last record.");
		}
	}

	
	//display
	public void updateDisplay(String msg){
		idField.setText(String.valueOf(loco[currIndex].getIdNum()));
		wheelsField.setText(String.valueOf(loco[currIndex].getNumWheels()));
		colorField.setText(loco[currIndex].getBodyColor());

		commentField.setText(msg);
		recField.setText(String.valueOf(recCount + 1));
		indexField.setText(String.valueOf(currIndex));
		
	}

	
	//main
	public static void main(String[] args){
		GUIWindow theGUI = new GUIWindow();
		//STD
		//theGUI.setSize =
		theGUI.setTitle("Locomotive Tracker");
		theGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theGUI.pack();
		theGUI.setVisible(true);
	}	
}