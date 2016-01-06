/**
		 * Controller class which acts between view and model class to execute logic and display 
		 * output of calculator
 6       *
 7       * @author1    Manasi Sunil Bharde
 8       * @author2    Sri Praneeth Iyyapu
 9       * Revisions:
10       *      $Log$
11       */
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class CalculatorController implements Observer, CalculatorControllerInterface {

	/**
	 * Method declared in interface Observer
	 * Method is executed whenever upon data change at observable class,
	 * setChanged is true and notifyObservers is called. 
	 */
	@Override
	public void update(Observable calculatorModel, Object arg) {
		calcView.displayOutput(getAnswer());
	}

	String expression ="";
	CalculatorView calcView = new CalculatorView();
	CalculatorModel calcModel = new CalculatorModel(this);
	
	@Override
	public void displayMenu(){
		calcView.menu();
	}
	
	@Override
	public void getExpression(){
		Scanner s = new Scanner(System.in);
		expression = s.nextLine();
		s.close();
	}
	
	@Override
	public void setExpression(){
		calcModel.setExpression(expression);
	}
	
	@Override
	public Double getAnswer(){
		calcModel.infixToPostfix();
		calcModel.evaluatePostfix();
		return calcModel.getAnswer();
	}
	

	public static void main(String args[]){
		CalculatorController newCalc = new CalculatorController();
		newCalc.displayMenu();
		newCalc.getExpression();
		newCalc.setExpression();
	}
	
}
