import java.util.Arrays;
import java.util.Random;

public class RandomSearch {
		
	public RandomSearch() {
	}
	
	public double[] randomGeneration(AntennaArray array, int antennaNumber){
		Random rng = new Random();
		double[] randomDesign = new double[antennaNumber];
		double[][] apertureSize = array.bounds();
		while(!array.is_valid(randomDesign)) {
			for(int i = 0; i < randomDesign.length; i++) {
				if(i == randomDesign.length - 1) {
					randomDesign[i] = apertureSize[i][1];
				}
				else {
					double randomPosition = rng.nextDouble() * (apertureSize[i][1] - apertureSize[i][0]) + apertureSize[i][0];
					randomDesign[i] =  randomPosition;
				}
			}
		}
		Arrays.sort(randomDesign);
		return randomDesign;
	}
	
	public Tuple<Double[], Double> searchNTimes(int numberOfSearches, AntennaArray array, int antennaNumber) {
		Double bestResult = Double.MAX_VALUE;
		Double[] bestDesign = new Double[antennaNumber];
		for(int i = 0; i < numberOfSearches; i++) {
			double[] design = randomGeneration(array, antennaNumber);
			double result = array.evaluate(design);
			if(result < bestResult) {
				bestResult = result;
				for(int j = 0; j < bestDesign.length; j++) {
					bestDesign[j] = design[j];
				}
				System.out.print("[" + i + "] New best design found: ");
				for(int j = 0; j < bestDesign.length; j++) {
					if(j != bestDesign.length - 1) {
						System.out.print(bestDesign[j] + ", ");
					}
					else {
						System.out.print(bestDesign[j]);
					}
				}
				System.out.println(" with a result of " + bestResult);
			}
		}
		Tuple<Double[], Double> resultTuple = new Tuple<Double[], Double>(bestDesign, bestResult);
		return resultTuple;
	}

}
