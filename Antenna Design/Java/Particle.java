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
	
	/**
	 * Creates a particle in a random position / random design.
	 * @param array The array to solve the problem for
	 * @param antennaNumber The number of antennae on the array
	 * @param coefficients The coefficients for calculating the new positions.
	 */
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
	
	/**
	 * Updates the particle's position and checks if it's better than the previous position.
	 */
	public void searchSpace(){
		for(int i = 0; i < velocity.length; i++) {
			position[i] = position[i] + velocity[i];
		}
		if(array.is_valid(position)) {
			double newDesignValue = array.evaluate(position);
			if(newDesignValue < personalBestResult) {
				personalBestDesign = position;
				personalBestResult = newDesignValue;
			}
		}
	}
	
	/**
	 * Calculates the new velocity by summing inertia, social attraction and cognitive attraction
	 * @see calculateInertia
	 * @see calculateCognitiveAttraction
	 * @see calculateSocialAttraction
	 */
	public double[] calculateNewVelocity() {
		double[] newInertiaVector = calculateInertia();
		double[] cognitiveAttractionVector = calculateCognitiveAttraction();
		double[] socialAttractionVector = calculateSocialAttraction();
		double[] newVelocity = new double[velocity.length];
		for(int i = 0; i < velocity.length; i++) {
			velocity[i] = newInertiaVector[i] + cognitiveAttractionVector[i] + socialAttractionVector[i];
			if(i + 1 == velocity.length) {
				newVelocity[i] = 0.0;
			}
		}
		return newVelocity;
	}
	
	/**
	 * Calculates the affect of the current velocity on how it moves to a new position.
	 **/
	private double[] calculateInertia() {
		double[] newInertia = new double[velocity.length];	
		for(int i = 0; i < velocity.length - 1; i++) {
			newInertia[i] = velocity[i] * intertiaCoefficient;
		}
		return newInertia;
	}
	
	/**
	 *	Calculates the attraction to current personal best with some degree of randomness.
	 */
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
	
	/**
	 * Calculates the attraction to the global best with some degree of randomness.
	 */
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
	
	/**
	 * Returns personal best design
	 */
	public double[] getPersonalBestDesign(){
		return personalBestDesign;
	}
	
	/**
	 * Returns personal best result
	 */
	public double getPersonalBestResult() {
		return personalBestResult;
	}
	
	/**
	 * Sets global best solution.
	 * @param design The current design of the global best
	 * @param result The current result of the global best
	 */
	public void setGlobalBest(double[] design, double result) {
		this.globalBestDesign = design;
		this.globalBestResult = result;
	}
}


