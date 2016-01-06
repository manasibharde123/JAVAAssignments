/**
		 * Model class which holds and processes logic of the calculator
 6       *
 7       * @author1    Manasi Sunil Bharde
 8       * @author2    Sri Praneeth Iyyapu
 9       * Revisions:
10       *      $Log$
11       */
import java.util.EmptyStackException;
import java.util.Observable;
import java.util.Observer;

public class CalculatorModel extends Observable implements CalculatorModelInterface {
	private Double answer;
	private String expressionStack[];
	private Stack<Character> operatorStack;
	private Stack<String> postfixExpressionStack;
	private Stack<Double> operandStack;
	private int length;
	
	public CalculatorModel(Observer observer){
		postfixExpressionStack = new Stack<String>();
		operatorStack = new Stack<Character>();
		operandStack = new Stack<Double>();
		this.addObserver(observer);
	}

	@Override
	public Double getAnswer(){
		return answer;
	}
	
	@Override
	public void setExpression(String expression){
		expressionStack = expression.split(" ");
		setChanged();
		notifyObservers();
	}
	
	@Override
	public void infixToPostfix(){
		length = expressionStack.length;
		for(int i=0;i<length;i++){
			String c = (String) expressionStack[i];
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
	 * Empties the operator stack to complete postfix expression
	 */
	private void addOperators() {
		while(!operatorStack.empty())
			postfixExpressionStack.push(""+operatorStack.pop());					
	}
	
	/**
	 * Checks precedence of input parameter with top of stack
	 * If precedence of top of stack operator is higher, updates the  postfix string
	 * else adds input parameter to stack
	 * @param c
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
	 * Gives precedence of input parameter
	 * @param operator :identifies precedence of this
	 * @return : value of precedence
	 */
	private int precedence(char operator){
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
	
	@Override
	public void evaluatePostfix(){
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
		answer = operandStack.pop();
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
    
    private Double pow(Double operand1,Double operand2){
        Double output=1.0;
        while(operand2-->0)
            output = output*operand1;
        return output;
    }


}
