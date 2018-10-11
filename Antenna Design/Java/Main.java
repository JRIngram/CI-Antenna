
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RandomSearch rs = new RandomSearch();
		AntennaArray ant = new AntennaArray(3, 90.0);
		Tuple<Double[], Double> bestRandom = rs.searchNTimes(10000, ant, 3);
		System.out.println("Best random search is " + generateDesignString(bestRandom.getItemOne()) + " with a Peak Side Lobe Level of " + bestRandom.getItemTwo());
	}
	
	public static String generateDesignString(Double[] design) {
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
	
	
	private static Double[] convertDoubleArrayPrimitiveToObject(double[] primitive) {
		Double[] object = new Double[primitive.length];
		for(int i = 0; i < object.length; i++) {
			object[i] = primitive[i];
		}
		return object;
	}
	
	private static double[] convertDoubleObjectArrayToPrimitive(Double[] object) {
		double[] primitive = new double[object.length];
		for(int i = 0; i < object.length; i++) {
			primitive[i] =object[i];
		}
		return primitive;
	}

}

