
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RandomSearch rs = new RandomSearch();
		int antennaNumber = 5;
		AntennaArray ant = new AntennaArray(antennaNumber, 90.0);
		Tuple<Double[], Double> bestRandom = rs.searchNTimes(100, ant, antennaNumber);
		System.out.println("Best random search is " + generateDesignString(bestRandom.getItemOne()) + " with a Peak Side Lobe Level of " + bestRandom.getItemTwo());
		System.out.println("GENERATING PARTICLE");
		double[] coefficients = {0.721,1.1193,1.1193};
		ParticleSwarm swarm = new ParticleSwarm(ant, antennaNumber, coefficients, 10);
		swarm.searchSpace(100);
		System.out.println("fin");
	}
	
	static String generateDesignString(Double[] design) {
		String designString = "";
		for(int j = 0; j < design.length; j++) {
			if(j != design.length - 1) {
				designString += design[j] + ", ";
			}
			else {
				designString += design[j];
			}

		}
		return designString;
	}
	
	
	static Double[] convertDoublePrimitiveArrayToObject(double[] primitive) {
		Double[] object = new Double[primitive.length];
		for(int i = 0; i < object.length; i++) {
			object[i] = primitive[i];
		}
		return object;
	}
	
	static double[] convertDoubleObjectArrayToPrimitive(Double[] object) {
		double[] primitive = new double[object.length];
		for(int i = 0; i < object.length; i++) {
			primitive[i] =object[i];
		}
		return primitive;
	}

}

