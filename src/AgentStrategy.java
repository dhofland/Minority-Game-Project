import java.util.ArrayList;

public class AgentStrategy {
	private double performance = 0;
	private int m;
	private int exp; 
	private String bin;

	ArrayList<Boolean> response;
	boolean[] responseVector;
	
	// numeral [0, 2^2^m - 1]
	// public Strategy(int m, int numeral) {
	public AgentStrategy(int m, boolean[] vector) {
	
		this.m = m;
		exp = (int) Math.pow(2, m);
		responseVector = vector;

	}
		
	public double getPerformance() {
		return performance;
	}

	public void changePerformance(double change) {
		performance += change;
	}
	
	public int action(boolean[] memory) {
		int num = booleanToNumeral(memory);
		boolean result = responseVector[num];
		if (result) {
			return 1;
		} else {
			return -1;
		}
	}
	
	private int booleanToNumeral(boolean[] input) {
		String binary = "";
		for (int i = 0; i < input.length; i ++) {
			if (input[i]) {
				binary += '1';
			} else {
				binary += '0';
			}
		}
		return Integer.parseInt(binary, 2);
			
	}
	
	private boolean[] numeralToVector(int numeral) {
		String binaryString = Integer.toBinaryString(numeral);
		boolean[] result = new boolean[exp];
		while (binaryString.length() < exp) {
			binaryString = '0' + binaryString;
		}
		bin = binaryString;
		for (int i = 0; i < result.length; i ++) {
			char x = binaryString.charAt(i);
			if (x == '0') {
				result[i] = false;
			} else {
				result[i] = true;
			}
		}
		return result;
	}

	
}
