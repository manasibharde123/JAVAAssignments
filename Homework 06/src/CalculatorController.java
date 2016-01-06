import java.util.Scanner;

public class CalculatorController {
	
	String expression ="";
	CalculatorModel calcModel = new CalculatorModel();
	CalculatorView calcView = new CalculatorView();
	
	
	public void displayMenu(){
		calcView.menu();
	}
	
	public void getExpression(){
		Scanner s = new Scanner(System.in);
		expression = s.nextLine();
		s.close();
	}
	
	public void setExpression(){
		calcModel.setExpression(expression);
	}
	
	public Double getAnswer(){
		calcModel.infixToPostfix();
		calcModel.evaluatePostfix();
		return calcModel.getAnswer();
	}
	
	public void displayAnswer(){
		calcView.displayOutput(getAnswer());
	}
	
	public static void main(String args[]){
		CalculatorController newCalc = new CalculatorController();
		newCalc.displayMenu();
		newCalc.getExpression();
		newCalc.setExpression();
		newCalc.displayAnswer();		
	}	
}
