
public class Expression {
	
	
	//an expression is either just a term, or (an expression, an operand, and an expression). Terms are expressions.
	 
	
	String operand = "";	//stores the operand between 1st term/expression and 2nd term/expression
	String function = "";  //stores the function of the expression (i.e cos,sin,ln, etc.)
	Expression expression1;	//stores the term/expression
	Expression expression2;	//stores the second term/expression
	
	
	//-------------------------------------Constructors
	public Expression(){
		
	}
	public Expression(Term x){  //i.e.  3x
		expression1 = x;
		
	}
	
	public Expression(String func, Term x){   //i.e  cos(x)
		expression1 = x;
		function=func;
	}
	
	public Expression(Expression x){ //i.e. (x+3)
		expression1 = x;
	}
	public Expression(String func, Expression x){ //i.e. cos(x+3)
		expression1 = x;
		function = func;
	}
	
	public Expression(Expression x, char operand, Expression y){ //i.e. 3x + (2x+1)
		this.operand+=operand;
		expression1 = x;
		expression2 = y;
		
	}
	public Expression(Expression x, String operand, Expression y){  //i.e. 3x + (2x+1)
		this.operand=operand;
		expression1 = x;
		expression2 = y;
		
	}
	
	//----------------------------------------Methods
	
	public void combineExpression(char operand, Expression exp){
		
		Expression temp;			//temporary expression
		
		if(function.equals("")){										//check if there is no function attached to expression
			if(this.operand.equals("")&&expression2==null){			//if there is only 1 expression, add the operand and the new expression
				this.operand = ""+ operand;
				this.expression2 = exp;
			}
			else{													//if there are 2 expressions already, make them into 1 and combine it with the new expression/operand
				temp = new Expression(expression1,this.operand,expression2); 
				this.expression1 = temp;							
				this.operand = ""+operand;
				this.expression2 = exp;
				}
			}
		else{													//there IS a function attached
			if(this.operand.equals("")&&expression2==null){
				temp = new Expression(function,expression1);  //if there is only 1 expression, add the operand and the new expression, but keep the function attached only to the first expression
				this.expression1=temp;
				function = "";
				this.operand = ""+ operand;
				this.expression2 = exp;
				

			}
			else{					//if there are 2 expressions already, make them into 1 and combine it with the new expression/operand
				temp = new Expression(expression1,this.operand,expression2);
				temp.function = this.function;
				function = "";
				this.expression1 = temp;
				this.operand = ""+operand;
				this.expression2 = exp;
	
				}
			
			
		}
	}
	
	public String toString(){
		
		if(expression2==null&&operand.equals("")){
			if(function.equals("")){
				return "("+expression1.toString()+")";
			}
			else return "("+function +"("+expression1.toString()+")"+")";
			}
		else{
			if(function.equals("")){
				return "("+expression1.toString()+operand+expression2.toString()+")";
			}
			else{ 
				return "("+function +"(" +expression1.toString()+")"+")"+operand+ "("+expression2.toString()+")";
				}
			}
			
	}
	
	private boolean isOperand(char test) {    
		return(test == '+' || test == '(' ||test == '-' ||test == '/' ||test == '*' );
	}
	
}
