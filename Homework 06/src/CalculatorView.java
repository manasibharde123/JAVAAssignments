import java.math.BigDecimal;

public class CalculatorView {

	
	public void menu(){
		System.out.println("Enter Valid Expression:");
	}
	
	public void displayOutput(Double answer){
		System.out.println("\nOutput is: "+new BigDecimal(answer).toPlainString());
		System.out.println("Thank You!");
	}
}
