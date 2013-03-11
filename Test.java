
public class Test {

	public static void main(String[] args) {
		/*Single_Variable_Deriver test = new Single_Variable_Deriver("f(x) = (x+x*13)-(27x-x^2)");
		
		System.out.println(test.Parse());
		test =  new Single_Variable_Deriver("f(x) = ((27)/(x-1)*(ln(x^2)))");
		System.out.println(test.Parse());
		System.out.print(test.variable);*/
		
		Term term1 = new Term("2x^3", "x");
		Term term2 = new Term("3x^2", "x");
		Term term3 = new Term("4x^3", "x");
		Term term4 = new Term("4x^2", "x");
		//Expression exp1 = new Expression(term1, "+", term2);
		Expression exp = new Expression(term1, "*", term2);
		Expression exp1 = new Expression(term3, "*", term4);
		Expression test = new Expression(exp, "*", exp1);
		Expression test1;
		//Expression test2;
		test = test.simplify();
		System.out.println(test);
		test1 = test.derive();
		System.out.println(test1 + "\n");
		//test2 = test1.derive();
		//System.out.println(test2 + "\n");
		exp = new Expression(term2, "-", term3);
		test = new Expression(exp, "+", term1);
		test = test.simplify();
		System.out.println(test);
		test1 = test.derive();
		System.out.println(test1 + "\n");
		//test2 = test1.derive();
		//System.out.println(test2 + "\n");
		exp = new Expression(term2, "/", term3);
		test = new Expression(exp, "/", term1);
		test = test.simplify();
		System.out.println(test);
		test1 = test.derive();
		System.out.println(test1);
		//test2 = test1.derive();
		//System.out.println(test2);
		
	}

}
