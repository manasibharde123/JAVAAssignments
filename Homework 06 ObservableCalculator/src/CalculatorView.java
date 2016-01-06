/**
		 * View class which displays output on screen
 6       *
 7       * @author1    Manasi Sunil Bharde
 8       * @author2    Sri Praneeth Iyyapu
 9       * Revisions:
10       *      $Log$
11       */
import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

public class CalculatorView implements CalculatorViewInterface {

	public void menu(){
		System.out.println("Enter Valid Expression:");
	}
	
	public void displayOutput(Double answer){
		System.out.println("\nOutput is: "+new BigDecimal(answer).toPlainString());
		System.out.println("Thank You!");
	}


}
