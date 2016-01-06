
public interface CalculatorModelInterface {

	public Double getAnswer();
	
	public void setExpression(String expression);
	
	public void infixToPostfix();
	
	public void evaluatePostfix();
}
