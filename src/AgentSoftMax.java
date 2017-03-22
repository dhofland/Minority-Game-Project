import java.util.ArrayList;
import java.util.Random;
import java.util.*;
import org.apache.commons.math3.analysis.function.Logit;
import org.ejml.simple.*;
import edu.stanford.nlp.neural.*;

public class AgentSoftMax extends Agent {
	double beta;
	Random random;
	Logit log;
	
	
	public AgentSoftMax(ArrayList<AgentStrategy> strategies, int m, int n, double beta) {
		super(strategies, m, n);
		this.beta = beta;
		random = new Random();
		log = new Logit();
		}
	
	// p * beta
	@Override
	public int action() {
		int actSign = 0;
		// Case: beta = infinity --> players select highest strategy
		if (beta == Double.POSITIVE_INFINITY) {
			return super.bestStrategy(sList).action(memory);
		// Case: beta = 0; return some random strat
		} else if (beta == 0) {
			int randomStrategy = random.nextInt(sList.size());
			actSign = sList.get(randomStrategy).action(memory);
			return (int) actSign;
		} else {
			// fill in data
			double[][] data = new double[sList.size()][1];
			double max = 0; 
			for (int i = 0; i < sList.size(); i ++) {
				double perf = beta * sList.get(i).getPerformance();
				if (perf > max) max = perf;
				data[i][0] = perf;
			}
			for (int i = 0; i < sList.size(); i ++) {
				data[i][0] = data[i][0] - max;
			}
			
			
//			if (beta < 1) {
//				for (int i = 0; i < sList.size(); i ++) {
//					data[i][0] = beta * sList.get(i).getPerformance();
//				}
//			} else {
//				double max = 0; 
//				for (int i = 0; i < sList.size(); i ++) {
//					double perf = beta * sList.get(i).getPerformance();
//					if (perf > max) max = perf;
//					data[i][0] = perf;
//				}
//				for (int i = 0; i < sList.size(); i ++) {
//					data[i][0] = data[i][0] - max;
//				}
//			}
			
			// calc p
			SimpleMatrix sim = new SimpleMatrix(data);
			SimpleMatrix res = NeuralUtils.softmax(sim);
			
			// comp outcome
			double ran = random.nextDouble();
			double sumSoFar = res.get(0, 0); 
			int counter = 0;
			while (sumSoFar < ran) {
				counter ++;
				sumSoFar += res.get(counter, 0);
			}
			AgentStrategy winner = sList.get(counter);
			return winner.action(memory);
		}
	}
	
	// return sign of prob-weighted actions recommended
	
//	@Override
//	public int action(){
//		double actSign = 0;
//
//		if (beta == Double.POSITIVE_INFINITY) {
//			return super.bestStrategy(sList).action(memory);
//		// Case: beta = 0; return some random strat
//		} else if (beta == 0) {
//			int randomStrategy = random.nextInt(sList.size());
//			actSign = sList.get(randomStrategy).action(memory);
//			return (int) actSign;
//		} else {
//			AgentStrategy result = null;
//			double sum = 0;
//			double[] eList = new double[sList.size()];
//			for (int i = 0; i < sList.size(); i ++) {
//				AgentStrategy current = sList.get(i);
//				double perf = current.getPerformance();
//				if (perf == 0) {
//					eList[i] = 1;
//					sum += 1;
//				} else {
////					double exp = Math.pow(((beta * perf)), 1.5);
//					double exp = Math.exp((beta * perf));
//					sum += exp;
//					eList[i] = exp;
//				}	
//			}
//			
//			for (int i = 0; i < sList.size(); i ++) {
//				double relProb = eList[i]/sum;
//				int act = sList.get(i).action(memory);
//				actSign += (relProb*act); 
//			}
//		}
//		int result;
//		if (actSign > 0) {
//			result = 1;
//		} else if (actSign < 0) {
//			result = -1;
//		} else {
//			if (random.nextBoolean()) {
//				result = 1;
//			} else {
//				result = -1;
//			}
//		}
//		if (result == 1) {
//			attendanceY ++;		
//		} else {
//			attendanceN ++;
//		}
//		return result;
//	}
	
	// multiply with t
	@Override
	protected AgentStrategy bestStrategy(ArrayList<AgentStrategy> list) {
		// Case: t = 0 --> every p is equal
		if (beta == 0) {
			int randomStrategy = random.nextInt(list.size());
			return list.get(randomStrategy);
		} else if (beta == Double.POSITIVE_INFINITY) {
			return super.bestStrategy(list);
		} else {
			// 1. calc e^bp + sum
			double sum = 0;
			double [] eList = new double[list.size()];
			for (int i = 0; i < list.size(); i ++) {
				AgentStrategy current = list.get(i);
				double perf = current.getPerformance();
				if (perf == 0) {
					eList[i] = 1;
					sum ++;
				} else {
					double pow = beta * perf;
					double ePow = Math.exp(pow);
					eList[i] = ePow;
					sum += ePow;
				}
			}
			
			// 2. calc rel prob
			double [] prob = new double[list.size()];
			for (int i = 0; i < list.size(); i ++) {
				double e = eList[i];
				double relProb = e / sum;
				prob[i] = relProb;
			}
			
			// 3. compute outcome
			double ran = random.nextDouble();
			double sumSoFar = prob[0]; 
			int counter = 0;
			while (sumSoFar < ran) {
				counter ++;
				sumSoFar += prob[counter];
			}
			AgentStrategy winner = list.get(counter);
			return winner;
			
		}
	}
}
	
	







// rest bla 






//	// original version, doesn't work with high m
//	// Rework: do p/beta, not p*beta
//	@Override
//	protected AgentStrategy bestStrategy(ArrayList<AgentStrategy> list) {
//		// Case: beta = infinity; return highest scoring strat
//		if (beta == 0) {
//			return super.bestStrategy(list);
//		// Case: beta = 0; return some random strat
//		} else if (beta == Double.POSITIVE_INFINITY) {
//			int randomStrategy = random.nextInt(list.size());
//			return list.get(randomStrategy);
//					
//			// Otherwise: calculate relative probability and output strat
//		} else {
//			AgentStrategy result = null;
//			double sum = 0;
//			
//			
//			double[] eList = new double[list.size()];
//			for (int i = 0; i < list.size(); i ++) {
//				AgentStrategy current = list.get(i);
//				double perf = current.getPerformance();
//				if (perf == 0) {
//					eList[i] = 1;
//					sum += 1;
//				} else {
////					double exp = Math.pow(((perf / beta)), 1.5);
//					double exp = Math.exp((perf * beta));
//
//					sum += exp;
//					eList[i] = exp;
//				}	
//			}
//						
//			double[] probs = new double [list.size()];
//			for (int i = 0; i < list.size(); i ++) {
//				double relProb = eList[i]/sum;
//				probs[i] = relProb;
//			}
//			double currentTotalProb = 0;
//			double randomChoice = random.nextDouble();
//			int counter = 0;
//			
//			while (true) {
//				currentTotalProb += probs[counter];
//				if (currentTotalProb > randomChoice) {
//					result = list.get(counter);
//					break;
//				}
//				counter ++;
//			}
//			return result;
//		}
		
		
		

		
		
		
		
		
//	}
	
	
	

