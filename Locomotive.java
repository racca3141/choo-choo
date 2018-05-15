//locomotive class
//loco id number, loco color, number of wheels
//methods -- set/retrieve each of above to not allow user can't mess with them accidentally
//color must be black, yellow, green or blue
//id must be a number
//number of wheels must be between 4 and 16
//interface with textboxes and buttons to go through, add to and delete from an array of locomotives
//cap user entry to 10
//button finds if there are more than 4 wheel locomotives than 12 wheen locomotives
//button finds how many are blue
//button finds the loco id with the most wheels in the array.  first if tie.
//

public class Locomotive{
	private int idNum;
	private int numWheels;
	private String bodyColor;

	public Locomotive(int id, int wheels, String clr){
		idNum = id;
		numWheels = wheels;
		bodyColor = clr;
	}

	public Locomotive(Locomotive loco){
		idNum = loco.getIdNum();
		numWheels = loco.getNumWheels();
		bodyColor = loco.getBodyColor();
	}

	public void setIdNum(int id){
		idNum = id;
	}

	public int getIdNum(){
		return idNum;
	}
	
	public void setNumWheels(int wheels){
		numWheels = wheels;
	}

	public int getNumWheels(){
		return numWheels;
	}

	public void setBodyColor(String clr){
		bodyColor = clr;
	}

	public String getBodyColor(){
		return bodyColor;
	}


	//object validates the data
	public String validate(String idStr, String wheelsStr, String colorStr){
		String message = "";
		int id;
		int wheels;

		try{
			id = Integer.parseInt(idStr);
			if(id < 0){
				message = message + "Id < 0; ";
			}
		}
		catch (Exception a){
			id = -1;
			message = message + "Id is not num; ";
		}

		try{
			wheels = Integer.parseInt(wheelsStr);
			if(wheels < 4 || wheels > 16){
				message = message + "wheels (4-16); ";
			}
		}
		catch (Exception b){
			wheels = -1;
			message = message + "wheels is not num; ";
		}

		colorStr = colorStr.trim().toUpperCase();
		if(!(colorStr.equals("BLACK") || colorStr.equals("YELLOW") || colorStr.equals("GREEN") || colorStr.equals("BLUE"))){
			message = message + "color not valid; ";
		}
		
		if(message.equals("")){
			idNum = id;
			numWheels = wheels;
			bodyColor = colorStr;

		}		

		return message;
	}


}