
public class BugD {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[] input = {"1#2","1#5","2#5","2#3","3#4","4#5","4#6"};
		System.out.println(minRoads(input));
	}	
	public static int destroy2 = 0;
	
	public static void recursiveCalculate(String e1, String compE1,String[] compStrArr){
		
		String s[] = e1.split("#");
				
		if(s[0].equals(compE1))
				{
			destroy2++;
			System.out.println(destroy2);
				}
		else
		{	for(String str: compStrArr){
				String arr[] = str.split("#");
				if(arr[1].equals(s[0]))
				{	System.out.println("arr "+ arr[1]);
					recursiveCalculate(str, compE1, compStrArr);}}			
		}
	}
	
	 public static int getMax(String[] compStrArr)
	 {
		 int max = -1;
		 for(String str:compStrArr){
			 String s[] = str.split("#");
			 int lhs = Integer.parseInt(s[0]);
			 int rhs = Integer.parseInt(s[1]);
			 if(lhs>max)
				 max = lhs;
			 if(rhs>max)
				 max=rhs;				 }
		return max;		 
	 }
	 
	 public static int getMin(String[] compStrArr)
	 {
		 int min = 9999;
		 for(String str:compStrArr){
			 String s[] = str.split("#");
			 int lhs = Integer.parseInt(s[0]);
			 int rhs = Integer.parseInt(s[1]);
			 if(lhs<min)
				 min = lhs;
			 if(rhs<min)
				 min=rhs;			
		 }
		return min;		 
	 }
	 
	public static int minRoads(String[] input1){
		int max = getMax(input1), min = getMin(input1),i=0;
	
		for(String str:input1){
			String arr[] = str.split("#");		
			if(arr[1].equals(max+""))
				recursiveCalculate(str, min+"",input1);
			}					
		return destroy2;		
	}
	
	 public static int minRoads1(String[] input1)
	    {
		//Write code here
		 int max = getMax(input1), min = getMin(input1),i=0;
		 String stops[];
		 String[] from, to = new String[input1.length];
		 String[] maxRoutes;
		 		 
		 for(String route:input1)
		 {
			 stops = route.split("#");			 
		 }		 
		 return 0;	        
	    }

}
