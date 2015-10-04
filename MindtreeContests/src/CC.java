
public class CC {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] ip = {15,3,15,-3};
		System.out.println(getPosition(ip));
	}
	 public static String getPosition(int[] input1)
	    {		 		 
		 	 
		 String output = "0";
		 int position;		
		 for(int i=1; i<(input1.length); i++)
		 {				 
			 position = 0;
			 for(int j=0; j<i; j++)
			 {
				 if(input1[i]<0 || input1[i]>20)
				 {
					 output = "invalid";
					 break;
				 }
				 if(input1[i]==input1[j])
					 position = i-1;
			 }			
			 if(output.equals("invalid"))
				 break;
			 else
				 output = output.concat(","+position);
		 }	 		 
			return output;
	    }

}
