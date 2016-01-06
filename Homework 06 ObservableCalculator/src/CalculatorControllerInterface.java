
public interface CalculatorControllerInterface {
	
	/**
	 * Prompts user to enter valid expression
	 */
	public void displayMenu();
	
	/**
	 * Get user input expression 
	 */
	public void getExpression();
	
	/**
	 * Create expression stack from user input
	 */
	public void setExpression();
	
	/**
	 * Run algorithm, evaluate expression and return answer
	 * @return
	 */
	public Double getAnswer();

}
