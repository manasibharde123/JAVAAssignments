import java.math.BigInteger;


public class LessIsMore {

	/**
	 * @param args
	 */
	
	private static final int MOD = 1000000007;
	private static final BigInteger BI_MOD = BigInteger.valueOf(MOD);
	private static final int MAX_BOARD = 1000000000;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(min_boards(6,3,1));
	}
									//N				M			K
	public static int min_boards(int input1, int input2, int input3)
	{if(input1>MAX_BOARD ||input2>input1||input2>50||input3>input2||input3<1)
		return -1;
	int bad = input1 % input2, good = input2 - bad;
	long answer;
	if(good>=input3)
		answer = count(input1 / input2, good, input3);
	else 
		answer = count(input1 / input2 + 1, bad, input3 - good);
	return (int)answer;	}
	
	private static long count(int segmentCount, int slotsPerSegment, int checkedSlots){
		int a = checkedSlots, b = slotsPerSegment - a, c = segmentCount;
		long result = 1;
		for(int i = 0; i < a; i++){
			result = (result * c(b + i, slotsPerSegment + c - 1)) % MOD;
			result = result * reverse(c(i, slotsPerSegment + c - 1 )) % MOD;
		}
		return result;
	}	

	private static long c(int k, int n) {
		long result = 1;
		for(int i = n - k + 1; i <= n; i++)
			result = result * i % MOD;
		for(int i = 1; i <= k; i++)
			result = result * reverse(i) % MOD;
		return result;
	}

	private static long reverse(long n) {
		return BigInteger.valueOf(n).modInverse(BI_MOD).longValue();
	}
}
