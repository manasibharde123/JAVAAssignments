import java.util.EmptyStackException;

public class CalculatorModel {
	
	private Double answer;
	private String expressionStack[];
	private Stack<Character> operatorStack;
	private Stack<String> postfixExpressionStack;
	private Stack<Double> operandStack;
	private int length;
	
	public CalculatorModel(){
		postfixExpressionStack = new Stack<String>();
		operatorStack = new Stack<Character>();
		operandStack = new Stack<Double>();
	}


	public Double getAnswer(){
		return answer;
	}
	
	public void setExpression(String expression){
		expressionStack = expression.split(" ");
	}
	
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
	
	private void addOperators() {
		while(!operatorStack.empty())
			postfixExpressionStack.push(""+operatorStack.pop());					
	}
	
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
