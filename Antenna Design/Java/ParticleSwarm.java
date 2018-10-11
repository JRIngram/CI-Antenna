
public class ParticleSwarm {
	
	private Particle[] swarm;
	private Tuple<Double[], Double> globalBest;
	private AntennaArray array;
	
	public ParticleSwarm(AntennaArray array, int antennaNumber, double[] coefficients, int size) {
		swarm = new Particle[size];
		this.array = array;
		//Creates particle swarm
		for(int i = 0; i < size; i++) {
			swarm[i] = new Particle(array, antennaNumber, coefficients);
		}
		globalBest = swarm[0].getPersonalBest();
		
		//Checks which Swarm has the initial best.
		for(int i = 0; i < size; i++){
			if(swarm[i].getPersonalBest().getItemTwo() < globalBest.getItemTwo()) {
				globalBest = swarm[i].getPersonalBest();
				System.out.println("[0] New Global Best " + Main.generateDesignString(globalBest.getItemOne()) + " with a LSS of: " + globalBest.getItemTwo() );
			}
		}
		updateSwarmsGlobalBest();
	}
	
	private void updateSwarmsGlobalBest() {
		for(int i = 0; i < swarm.length; i++){
			swarm[i].setGlobalBest(globalBest);
		}
	}
	
	public Tuple<Double[], Double> searchSpace(int numberOfSearches){
		for(int i = 0; i < numberOfSearches; i++){
			for(int j = 0; j < swarm.length; j++) {
				swarm[j].searchSpace();
			}
			for(int j = 0; j < swarm.length; j++){
				if(swarm[j].getPersonalBest().getItemTwo() < globalBest.getItemTwo()) {
					updateSwarmsGlobalBest();
					globalBest = swarm[j].getPersonalBest();
					System.out.println("[" + j + "] New Global Best " + Main.generateDesignString(globalBest.getItemOne()) + " with a LSS of: " + globalBest.getItemTwo() );
				}
			}
		}
		return globalBest;
	}
}
