public class Single_Variable_Deriver {

	private String input;
	public char variable;
	private int start;
	
	public Single_Variable_Deriver(String input){
		this.input = input;
		variable = input.charAt(2);
		if(input.charAt(5) == '=')
			start = 7;
		else
			start = 0;
	}
	public String Parse(){
		return Parse(input.substring(start));
	}
	public String Parse(String input){
		if(input.length() <= 2)
			return input;
		int pcounter = 0;
		int temp = 0;
		String result = "";
		if(input.charAt(0) == '('){ //If first character is ( looks through string to find matching ) making sure to handle cases like ((x))
			 pcounter++;
		char test = 0;
		while(pcounter >= 1 && temp < input.length()-1){
			test = input.charAt(++temp);
			if(test == '(')
				pcounter++;
			if(test == ')')
				pcounter--;
		}
		result = "("+Parse(input.substring(1, temp))+ Parse(input.substring(temp));
		}
		else{ //Not a first character (, so look for first operand;
			char test = 0;
			while(temp < input.length()){
				test = input.charAt(temp++);
				if(isOperand(test)){
					int temp1 = temp;					
					if(test=='('){  //special case for paranthesis
						pcounter++;
						while(pcounter >= 1 && temp1 < input.length()){
							test = input.charAt(temp1++);
							if(test == '(')
								pcounter++;
							if(test == ')')
								pcounter--;
						}
						result += Parse(input.substring(0, temp-1), test, input.substring(temp, temp1));
						result += Parse(input.substring(temp1));
						break;						
					}
					else{
						while(temp1<input.length()&&!isOperand(input.charAt(temp1)))
							temp1++;
						result += Parse(input.substring(0, temp-1), test, input.substring(temp, temp1));
						result += Parse(input.substring(temp1));
						break;
					}
				}	
			}
			result = input;
		}
		return result;
	}
	public String Parse(String car, char operand, String cdr){
		int pcounter = 0;
		int temp = 0;
		String result = ""+car+operand+cdr;
		if(cdr.isEmpty())
			return result;
		if(cdr.charAt(0) == '('){ //If first character is ( looks through string to find matching ) making sure to handle cases like ((x))
			 pcounter++;
		char test = 0;
		while(pcounter >= 1 && temp < cdr.length())
			test = cdr.charAt(temp++);
			if(test == '(')
				pcounter++;
			if(test == ')')
				pcounter--;
		result = cdr.substring(1, temp-1);
		}
		else{
			char test = 0;
			while(temp < cdr.length()){
				test = cdr.charAt(temp++);
				if(isOperand(test)){
					result += Parse(cdr.substring(0, temp-1), test, cdr.substring(temp));
					break;
				}
			}
		}
		return result;
		
	}
	private boolean isOperand(char test) {
		return(test == '+' || test == '(' ||test == '-' ||test == '/' ||test == '*' );
	}
}
