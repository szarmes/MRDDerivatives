
public class Test {

	public static void main(String[] args) {
		/*Single_Variable_Deriver test = new Single_Variable_Deriver("f(x) = (x+x*13)-(27x-x^2)");
		
		System.out.println(test.Parse());
		test =  new Single_Variable_Deriver("f(x) = ((27)/(x-1)*(ln(x^2)))");
		System.out.println(test.Parse());
		System.out.print(test.variable);*/
		
		Term test = new Term("-(7.5/10.0)/(6.3/1.0)x^36/-12", "x");
		System.out.println(test);
	}

}
