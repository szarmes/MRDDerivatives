public class Term extends Expression{ 

	private float exponent = 1.0f;
	private float coefficient = 1.0f;
	private String variable = "";
	
	//Takes a string as input and a string as a variable and finds the coefficient and exponent involved with the instance of the variable
	public Term(String input, String variable){
		this.variable = variable;
		int i = 0;
		while(i < input.length()&&input.charAt(i) != variable.charAt(0))//Takes the first part of the input. the coefficient and makes a substring
			i++;
		coefficient = makenumber(input.substring(0, i));//Takes the found substring and converts it to a float value, handles fractions and decimals
		while(i < input.length() && input.charAt(i) != '^')//Makes another substring of everything after a '^' and makes this the exponent
			i++;
		if(i < input.length())
		exponent = makenumber(input.substring(i+1));//Takes the substring and sets it to a float value which is the exponent.  Also handles decimals and fractions
	}

	//Takes a a string and sets it to a number.
	private float makenumber(String substring) {
		if(substring.charAt(0) == '-')
			return -makenumber(substring.substring(1));
		int division = substring.indexOf("/"); 	//Checks if the string represents a division of integers or floats
		if(division != -1){ 					//If there is a division sign then start this sequence of calls, otherwise move on
			if(substring.indexOf("/", division+1) != -1){ //Checks if there is another division sign after the one we know about
			int i = 0;
			int pcounter = 0;
			while(substring.charAt(i) != '/' || pcounter != 0){ //Moves through the string until it finds the '/' in question, done by counting 
				if(substring.charAt(i) == '(')					//parenthesis
					pcounter++;
				if(substring.charAt(i) == ')')
					pcounter--;
				i++;
			}
			return(makenumber(substring.substring(0,i))/makenumber(substring.substring(i+1))); 	//Calls make number on the 'numerator' and 
			}																				  	//'denominator' of the string
			if(substring.charAt(0) == '(')  //If there are brackets around the expression, removes them
				return makenumber(substring.substring(1,substring.length()-1));
			int i = 0;
			while(substring.charAt(i) != '/') 
				i++;
			return(makenumber(substring.substring(0,i))/makenumber(substring.substring(i+1)));	//Calls make number on the 'numerator' and 
		}																				  		//'denominator' of the string
		if(substring.charAt(0) == '(')  //If there are brackets around the expression, removes them
			return makenumber(substring.substring(1,substring.length()-1));
		int power = -1; //Sets the initial power to -1 as for every entry in the string we increment power by 1. i.e. string = "7", power = 0;
		double place_value = 0;
		float  result = 0; //This array stores the 'numerator' and 'denominator' of the string
		int i = 0;
		while(i < substring.length() && substring.charAt(i) != '.'){ 	//finds the part of the string that represents numbers > 0 and sets
			i++;														//power to the appropriate number of digits to the right of the decimal							
			power++;													//point.
		}
		i=0;
		while(i < substring.length()){
			place_value = Math.pow(10, power)*(substring.charAt(i)-48); //Finds the value in the appropriate place corresponding 
			if(place_value >= 0)													//character in the string.
				result += place_value;
			else
				if(substring.charAt(i) == '.') 	//If the character is the decimal we increment the power so that the next step will
					power++;					//decrement it to -1, which is the first spot after the decimal
			i++;
			power--;
		}
		return result;
	}
	
	//If you can't figure out what this does, sucks to be you.
	public String toString(){
		return ""+coefficient+variable+"^"+exponent;
	}
	
}
