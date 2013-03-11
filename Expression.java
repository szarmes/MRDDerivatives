
public class Expression {
	
	
	//an expression is either just a term, or (an expression, an operand, and an expression). Terms are expressions.
	 
	
	String operand = "";	//stores the operand between 1st term/expression and 2nd term/expression
	String function = "";  //stores the function of the expression (i.e cos,sin,ln, etc.)
	Expression expression1;	//stores the term/expression
	Expression expression2;	//stores the second term/expression
	float exponent;
	float coefficient;
	String variable;
	
	
	//-------------------------------------Constructors
	public Expression(){
		
	}
	public Expression(Term x){  //i.e.  3x
		expression1 = x;
		this.exponent = x.getExponent();
		this.coefficient = x.getCoefficient();
		this.variable = x.getVariable();		
	}
	
	public Expression(String func, Term x){   //i.e  cos(x)
		expression1 = x;
		function=func;
		this.exponent = x.getExponent();
		this.coefficient = x.getCoefficient();
		this.variable = x.getVariable();
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
	
	//Derives the expression and applies rules based on the expression, makes choices about what rules to apply
	public Expression derive(){
		Expression result = null;
		if(expression2 == null)
			result = expression1.derive();
		if(expression2 != null)
			result = applyRule();
		return result;
	}

	private Expression applyRule() {
		Expression result = null;
		if(operand == "+" || operand == "-")
			result = sumRule();
		if(operand == "*")
			result  = productRule();
		if(operand == "/" || operand == "\\") 
			result = quotientRule();
		return result;
		
	}
	private Expression quotientRule() {
		Expression temp1;
		Expression temp2;
		temp1 = new Expression(expression1.derive(), "*", expression2);
		temp2 = new Expression(expression2.derive(), "*", expression1);
		Expression numerator = new Expression(temp1, "-", temp2);
		Expression denominator = new Expression(expression2, "*",expression2);
		Expression result = new Expression(numerator, "/", denominator);
		return result;
		
	}
	
	//Applies the product rule and returns a new expression which is the derivative of the old expression i.e. d/dx(f(x)*g(x)) = f'(x)*g(x) + f(x)*g'(x)
	private Expression productRule() {
		Expression temp1;
		Expression temp2;
		temp1 = new Expression(expression1.derive(), "*", expression2);
		temp2 = new Expression(expression2.derive(), "*", expression1);
		Expression result = new Expression(temp1, "+", temp2);
		return result;
	}
	
	//Applies the sum rule and returns a new expression which is the derivative of the old expression i.e. d/dx(f(x)+g(x)) = f'(x)+g'(x)
	private Expression sumRule() {
		Expression temp1 = new Expression();
		Expression temp2 = new Expression();
		temp1 = expression1.derive();
		temp2 = expression2.derive();
		Expression result = new Expression(temp1, this.operand, temp2);		
		return result;
	}
	
	//Applies some basic simplification rules to the expression in the hope of making it more readable.  Only just started this method, I am certain that there is more to be done here.
	//Possible that this will become an endless problem that we will just have to say is "good enough"
	public Expression simplify(){
		Expression result = null;
		if(expression2 == null)
			result = expression1.simplify();
		if(operand.equals("+"))
			result = add(expression1.simplify(), expression2.simplify());
		if(operand.equals("-"))
			result = subtract(expression1.simplify(), expression2.simplify());
		if(operand.equals("*"))
			result = multiply(expression1.simplify(), expression2.simplify());
		if(operand.equals("/"))
			result = divide(expression1.simplify(), expression2.simplify());
		return result;
	}
	//Divides two expressions that have the same variable by subtracting their exponents and dividing their coefficients.
	private Expression divide(Expression first, Expression second) {
		Expression result = null;
		if(first.variable.equals(second.variable)){
			Term temp = new Term(first.coefficient/second.coefficient, first.variable, first.exponent-second.exponent);
			result = new Expression(temp);
		}
		else{
			result = new Expression(first, "/", second);
		}			
		return result;
	}
	//Multiplies two expressions that have the same variable by adding their exponents and multiplying their coefficients.
	private Expression multiply(Expression first, Expression second) {
		Expression result = null;
		if(first.variable.equals(second.variable)){
			Term temp = new Term(first.coefficient*second.coefficient, first.variable, first.exponent+second.exponent);
			result = new Expression(temp);
		}
		else{
			result = new Expression(first, "*", second);
		}			
		return result;
	}
	//This needs more work, still not quite there same with addition, handles 2 terms fine, has problem with nested expressions... Maybe needs to be reworked from the ground up as it is giving me way
	//too much trouble, think about this more later, I am tired now
	private Expression subtract(Expression first, Expression second) {
		Expression result = null;
		if(first.expression2 == null && second.expression2 == null){ //These lines are for the 2 term case, everything else is for nested expressions and that is where it falls apart.
		if(first.variable.equals(second.variable) && first.exponent == second.exponent){
			Term temp = new Term(first.coefficient-second.coefficient, first.variable, first.exponent);
			result = new Expression(temp);
		}
		else{
			result = new Expression(first, "-", second);
		}
		}
		else if(first.expression2 == null){
			if(second.operand == "-")
				second.expression2.coefficient = -second.expression2.coefficient;
			if(first.exponent == second.expression1.exponent){
				if(second.operand == "-")
					second.expression1.coefficient = -second.expression1.coefficient;
				result = new Expression(subtract(first, second.expression1), second.operand, second.expression2);
			}else if(first.exponent == second.expression2.exponent){
				if(second.operand == "-")
					second.expression2.coefficient = -second.expression2.coefficient;
				result = new Expression(subtract(first, second.expression2), second.operand, second.expression1);
			}else
				result = new Expression(first, "-", second);
		}
		else if(second.expression2 == null){
			if(first.operand == "-")
				first.expression2.coefficient = -first.expression2.coefficient;
			if(second.exponent == first.expression1.exponent){
				if(first.operand == "-")
					first.expression1.coefficient = -first.expression1.coefficient;
				result = new Expression(subtract(first.expression1, second), first.operand, first.expression2);
			}else if(second.exponent == first.expression2.exponent){
				if(first.operand == "-")
					first.expression2.coefficient = -first.expression2.coefficient;
				result = new Expression(subtract(first.expression2, second), first.operand, first.expression1);
			}else
				result = new Expression(first, "-", second);
		}
		else{
			Expression temp1 = first;
			Expression temp2 = second;
			if(first.expression1.exponent == second.expression1.exponent && first.expression2.exponent == second.expression2.exponent){
				temp1 = subtract(first.expression1, second.expression1);
				temp2 = subtract(first.expression2, second.expression2);
			}else{}
			result = new Expression(temp1, "-", temp2);
		}
		return result;
	}
	
	//Same problems as the subtract method, again I think that there is an easier way but I am just not seeing it.
	private Expression add(Expression first, Expression second) {
		Expression result = null;
		if(first.expression2 == null && second.expression2 == null){
		if(first.variable.equals(second.variable) && first.exponent == second.exponent){
			Term temp = new Term(first.coefficient+second.coefficient, first.variable, first.exponent);
			result = new Expression(temp);
		}
		else{
			result = new Expression(first, "+", second);
		}
		}
		else if(first.expression2 == null){
			if(first.exponent == second.expression1.exponent)
				result = new Expression(add(first, second.expression1), second.operand, second.expression2);
			if(first.exponent == second.expression2.exponent)
				result = new Expression(add(first, second.expression2), second.operand, second.expression1);
			else
				result = new Expression(first, "+", second);
		}
		else if(second.expression2 == null){
			if(second.exponent == first.expression1.exponent)
				result = new Expression(add(second, first.expression1), first.operand, first.expression2);
			if(second.exponent == first.expression2.exponent)
				result = new Expression(add(second, first.expression2), first.operand, first.expression1);
			else
				result = new Expression(first, "+", second);
		}
		else{
			result = new Expression(add(first.simplify(), second.simplify()));
		}
		return result;
	}
		
}
