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
	private Double[] position; //Design
	private Tuple<Double[], Double> personalBest;
	private Tuple<Double[], Double> globalBest;
	private double intertiaCoefficient;
	private double cognitiveCoefficient;
	private double socialCoefficient;
	private AntennaArray array;
	
	public Particle(AntennaArray array, int antennaNumber, double[] coefficients){
		RandomSearch rs = new RandomSearch();
		position = Main.convertDoublePrimitiveArrayToObject(rs.randomGeneration(array, antennaNumber));
		personalBest = new Tuple<Double[], Double>(position, array.evaluate(Main.convertDoubleObjectArrayToPrimitive(position)));
		Double[] globalBestVector = {0.0, 0.0, 0.0};
		globalBest = new Tuple<Double[], Double>(globalBestVector, 0.0);
		intertiaCoefficient = coefficients[0];
		cognitiveCoefficient = coefficients[1];
		socialCoefficient = coefficients[2];
		velocity = new double[position.length];
		this.array = array;
		
		Double[] secondPosition = Main.convertDoublePrimitiveArrayToObject(rs.randomGeneration(array, antennaNumber));
		
		velocity = new double[position.length];
		for(int i = 0; i < velocity.length; i++) {
			velocity[i] = (position[i] + secondPosition[i]) / 2;
			if(i == velocity.length - 1) {
				velocity[i] = 0.0;
			}
		}
		for(int i = 0; i < 100; i++) {
			searchSpace();
			velocity = calculateNewVelocity();
			if(array.is_valid(Main.convertDoubleObjectArrayToPrimitive(position))) {
				System.out.println("[" + i + "] " + Main.generateDesignString(position));
			}
			if(i % 10000000 == 0) {
				System.out.println("[" + i + "] " + Main.generateDesignString(position));
			}
		}
		System.out.println("fin");
	
	}
	
	private void searchSpace(){
		for(int i = 0; i < velocity.length; i++) {
			position[i] = position[i] + velocity[i];
		}
		if(array.is_valid(Main.convertDoubleObjectArrayToPrimitive(position))) {
			double newDesignValue = array.evaluate(Main.convertDoubleObjectArrayToPrimitive(position));
			if(newDesignValue < personalBest.getItemTwo()) {
				System.out.println("New Personal Best: " + newDesignValue);
				personalBest.setItemOne(position);
				personalBest.setItemTwo(newDesignValue);
			}
		}
	}
	
	private double[] calculateNewVelocity() {
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
		double[] cognitiveAttraction = new double[personalBest.getItemOne().length];
		double[] personalBestPosition = Main.convertDoubleObjectArrayToPrimitive(personalBest.getItemOne());
		Random rng = new Random();
		double randomness = rng.nextDouble();
		for(int i = 0; i < personalBest.getItemOne().length; i++) {
			cognitiveAttraction[i] = cognitiveCoefficient * randomness * (personalBestPosition[i] - position[i]);
		}
		return cognitiveAttraction;
	}
	
	private double[] calculateSocialAttraction() {
		double[] socialAttraction = new double[globalBest.getItemOne().length];
		double[] globalBestPosition = Main.convertDoubleObjectArrayToPrimitive(globalBest.getItemOne());
		Random rng = new Random();
		double randomness = rng.nextDouble();
		for(int i = 0; i < globalBest.getItemOne().length; i++) {
			socialAttraction[i] = cognitiveCoefficient * randomness * (globalBestPosition[i] - position[i]);
		}
		return socialAttraction;
	}

	
}


