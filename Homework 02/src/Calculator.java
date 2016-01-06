import java.math.BigDecimal;
import java.util.EmptyStackException;

/**
 2       * A simple calculator that can evaluate 
 3       * the basic arithmetic operations involving single digit operands and '_','+','%','*','/' operators.
 4       * Pass expression as command line arguments while executing program
 5       * @version   1.1
 6       *
 7       * @author    msb4977 : Manasi Sunil Bharde
 8       * @author    Sri Praneeth Iyyapu
 9       * Revisions:
10       *      $Log$
11       */

public class Calculator {
	
	String expression = "";
	String[] expressionArray;
	Stack<Character> operatorStack;
	Stack<String> expressionStack,postfixExpressionStack;
	Stack<Double> operandStack;
	Double answer;
	int length;
	
	
	public Calculator(String expression){
		this.expression = expression;
		expressionStack = new Stack<String>();
		postfixExpressionStack = new Stack<String>();
		operatorStack = new Stack<Character>();
		operandStack = new Stack<Double>();
	}

	/**
	   * The main program.	   *
	   * @param    args    expression passed as command line argument
	   */
	public static void main(String[] args) {
		String expression = "25 / ( 15 + 6 / 2 ^ 7 - 6 )";
		/*if(args.length>0)
		expression = args[0];
		else{
			System.out.println("Please pass valid expression as command line argument");
			System.exit(1);
		}		*/
		Calculator c = new Calculator(expression);
		c.createExp();
		c.infixToPostfix();
		c.answer  = c.evaluatePostfix();
		System.out.println("\nOutput is: "+new BigDecimal(c.answer).toPlainString());
		System.out.println("Thank You!");		
	}
	
	/**
	 * Create an Expression stack to identify the multi digit operands
	 */
	private void createExp(){
		expressionStack.array = expression.split(" ");
		expressionStack.index = expressionStack.array.length; 
	}

	/**
	 * Converts infix expression entered by user to postfix expression
	 */
	public void infixToPostfix(){
		length = expressionStack.index;
		for(int i=0;i<length;i++){
			String c = (String) expressionStack.array[i];
			if(c.length()==0)
				continue;
			if(c.matches("[0-9]+"))
				postfixExpressionStack.push(c);
			else 
				chkPrecedenceAndPush(c.charAt(0));
		}		
		addOperators();
		System.out.println("\nPostfix expression is:");
		for(int i = 0; i<postfixExpressionStack.index; i++)
		System.out.print((String)postfixExpressionStack.array[i]);
	}
	
	/**
	 * Empty operator stack to complete postfix expression.
	 */
	private void addOperators() {
		while(!operatorStack.empty())
			postfixExpressionStack.push(""+operatorStack.pop());					
	}
	
	/**
	 * Fills operator stack as per precedence
	 */
	private void chkPrecedenceAndPush(char c) {
		if(operatorStack.empty())
			operatorStack.push(c);
		else{
            if(c=='(')
                operatorStack.push(c);
            else if(c==')'){
                while(operatorStack.peek()!='(')
                	postfixExpressionStack.push(""+operatorStack.pop());
                operatorStack.pop();
            }
            else{              
                while((!operatorStack.empty())&&(precedence(operatorStack.peek())>precedence(c)))
                	postfixExpressionStack.push(""+operatorStack.pop());              
            	operatorStack.push(c);
            }
		}
	}
	
	/**
	 * precedence(char operator) decides precedence of operator
	 * @param operator operator
	 * @return returns precedence
	 */
	public int precedence(char operator){
		int p=0;
		switch(operator){
        case ')':{
            p= -1;
            break;
        }
		case '+':{
			p=1;
			break;
		}
		case '-':{
			p=2;
			break;			
		}
		case '%':{
			p=3;
			break;
		}
		case '*':{
			p=4;
			break;
		}
		case '/':{
			p=5;
			break;
		}
        case '^':{
            p=6;
            break;
            }
        case '(':{
            p=0;
            break;
        }
		}
        return p;
	}
	/**Evaluates postfix expression
	 */
	public Double evaluatePostfix(){
		Double operand1 = 0.0;
		Double operand2;
        length = postfixExpressionStack.index;
		for(int i=0;i<length;i++){
			String term = (String) postfixExpressionStack.array[i];		
			if(term.matches("[0-9]+"))			
				operandStack.push(Double.parseDouble(term));			
			else			
			try{
				operand2 = operandStack.pop();			
				operand1 = operandStack.pop();
				operandStack.push(evaluate(term.charAt(0),operand1, operand2));
			}
			catch(EmptyStackException ese)
			{	System.out.println("Bad Operands");
				System.exit(1);
			}
		}
		return operandStack.pop();
	}
	/**
	 * Gives output of expression
	 *
	 * @param       c    operator
	 * @param       operand1    operand 1
	 * @param       operand1    operand 2
	 * @return  	returns float output of expression passed
	 * 
	 */
	private Double evaluate(char c, Double operand1, Double operand2) {
	try{
	switch(c){
	case '+': return operand1+operand2;
	case '-': return operand1-operand2;
	case '%': return operand1%operand2;
	case '*': return operand1*operand2;
	case '/': {
		if(operand2==0)
			throw new ArithmeticException("Division by zero!");
		else
			return operand1/operand2;
			  }
    case '^': return pow(operand1,operand2);
	default:{
		System.out.println("Wrong operators entered");
		System.exit(1);
	}
	}
	}catch(ArithmeticException e){
		System.out.println(e.getMessage());
		System.exit(1);		
	}	
	return 0.0;
	}
    
    public Double pow(Double operand1,Double operand2){
        Double output=1.0;
        while(operand2-->0)
            output = output*operand1;
        return output;
    }
}

class Stack<T> {
	Object[] array;
	int index;
	
	Stack(){
		array = new Object[100]; 
		index = 0;
	}

	public T peek(){
		return (T) array[index-1];
	}
	
	public T pop(){
		return (T) array[--index];
	}
	
	public void push(T item ){
		array[index] = item;
		index++;
	}
	
	public boolean empty(){
		if(index==0)
			return true;
		else 
			return false;
	}
}
