
public class CountAndSayClass {
	
	
	
	public static void main(String[] args){
		System.out.println(countAndSay(5));
	}

	static String countAndSay(int n){
		String op[] = new String[n+1];
		op[0] = "";
		op[1] = "1";
		for(int i=2; i<=n; i++){
			op[i] = parseAndUpdate(op[i-1]);
		}
		return op[n];		
	}
	
	static String parseAndUpdate(String op){
		char c = op.charAt(0);
		int count = 1;
		String output="";
		for(int i = 1; i < op.length(); i++ ){
			if(op.charAt(i)==c)
				count++;
			else{
				output = output + count + c;
				count = 1;
				c = op.charAt(i);
			}			
		}
		output = output + count + c;
		return output;
	}	
}
