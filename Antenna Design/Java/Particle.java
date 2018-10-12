import java.util.Arrays;
import java.util.Random;

/*
 * 	BEGIN
		INITIALISE population;
		REPEAT UNTIL ( termination condition IS satisfied ) DO
			UPDATE global best;
			FOR EACH ( particle in population ) DO
				1. UPDATE velocity and position;
				2. EVALUATE new position;
				3. UPDATE personal best;
			OD
		OD
	END
*/

public class Particle {
	
	private double[] velocity;
	private double[] position;
	
	private double[] personalBestDesign;
	private double personalBestResult;
	
	private double intertiaCoefficient;
	private double cognitiveCoefficient;
	private double socialCoefficient;
	
	private double[] globalBestDesign;
	private double globalBestResult;
	
	private AntennaArray array;
	
	public Particle(AntennaArray array, int antennaNumber, double[] coefficients){
		RandomSearch rs = new RandomSearch();
		double[] initialPosition = rs.randomGeneration(array, antennaNumber);
		while(!array.is_valid(initialPosition)) {
			initialPosition = rs.randomGeneration(array, antennaNumber);
		}
		position = initialPosition;
		personalBestDesign = Arrays.copyOf(position, position.length);
		personalBestResult = array.evaluate(personalBestDesign);
		intertiaCoefficient = coefficients[0];
		cognitiveCoefficient = coefficients[1];
		socialCoefficient = coefficients[2];
		velocity = new double[position.length];
		this.array = array;
		
		double[] secondPosition = rs.randomGeneration(array, antennaNumber);
		
		velocity = new double[position.length];
		for(int i = 0; i < velocity.length; i++) {
			velocity[i] = (position[i] + secondPosition[i]) / 2;
			if(i == velocity.length - 1) {
				velocity[i] = 0.0;
			}
		}
	
	}
	
	public void searchSpace(){
		for(int i = 0; i < velocity.length; i++) {
			position[i] = position[i] + velocity[i]; //Causes personal best to also be updated?
		}
		if(array.is_valid(position)) {
			double newDesignValue = array.evaluate(position);
			if(newDesignValue < personalBestResult) {
				System.out.println("New Personal Best: " + newDesignValue);
				personalBestDesign = position;
				personalBestResult = newDesignValue;
			}
		}
	}
	
	public double[] calculateNewVelocity() {
		double[] newInertiaVector = calculateInertia();
		double[] cognitiveAttractionVector = calculateCognitiveAttraction();
		double[] socialAttractionVector = calculateSocialAttraction();
		double[] newVelocity = new double[velocity.length];
		for(int i = 0; i < velocity.length; i++) {
			newVelocity[i] = newInertiaVector[i] + cognitiveAttractionVector[i] + socialAttractionVector[i];
			if(i + 1 == velocity.length) {
				newVelocity[i] = 0.0;
			}
		}
		return newVelocity;
	}
	
	private double[] calculateInertia() {
		double[] newInertia = new double[velocity.length];	
		for(int i = 0; i < velocity.length - 1; i++) {
			newInertia[i] = velocity[i] * intertiaCoefficient;
		}
		return newInertia;
	}
	
	private double[] calculateCognitiveAttraction() {
		double[] cognitiveAttraction = new double[personalBestDesign.length];
		Random rng = new Random();
		double randomness = rng.nextDouble();
		for(int i = 0; i < personalBestDesign.length; i++) {
			double singleCognitiveAttraction = cognitiveCoefficient * randomness * (personalBestDesign[i] - position[i]);
			cognitiveAttraction[i] = singleCognitiveAttraction;
		}
		return cognitiveAttraction;
	}
	
	private double[] calculateSocialAttraction() {
		double[] socialAttraction = new double[globalBestDesign.length];
		double[] globalBestPosition = globalBestDesign;
		Random rng = new Random();
		double randomness = rng.nextDouble();
		for(int i = 0; i < globalBestDesign.length; i++) {
			socialAttraction[i] = socialCoefficient * randomness * (globalBestPosition[i] - position[i]);
		}
		return socialAttraction;
	}
	
	public double[] getPersonalBestDesign(){
		return personalBestDesign;
	}
	
	public double getPersonalBestResult() {
		return personalBestResult;
	}
	
	public void setGlobalBest(double[] design, double result) {
		this.globalBestDesign = design;
		this.globalBestResult = result;
	}
}


