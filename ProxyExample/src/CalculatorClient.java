public class CalculatorClient {
	private final Calculator calculator;
	
	public CalculatorClient(Calculator calculator) {
		this.calculator = calculator;
	}

	public void runTests() {
		System.out.println("1 + 2 = " + calculator.add(1, 2));
		System.out.println("4 - 5 = " + calculator.subtract(4, 5));
		System.out.println("10 / 2 = " + calculator.divide(10, 2));
		System.out.println("3 * 6 = " + calculator.multiply(3, 6));
	}
	
	public static void main(String[] args) {
		Calculator calculator;
		if(args.length >= 2) {
			String host = args[0];
			int port = Integer.parseInt(args[1]);
			calculator = new ProxyCalculator(host, port);
		}
		else {
			calculator = new RealCalculator();
		}
		
		CalculatorClient client = new CalculatorClient(calculator);
		client.runTests();
	}
}