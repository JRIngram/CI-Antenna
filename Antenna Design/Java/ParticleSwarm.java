import java.util.Arrays;

public class ParticleSwarm {
	
	private Particle[] swarm;
	private double[] globalBestDesign;
	private double globalBestResult;
	private AntennaArray array;
	
	public ParticleSwarm(AntennaArray array, int antennaNumber, double[] coefficients, int size) {
		swarm = new Particle[size];
		this.array = array;
		//Creates particle swarm
		for(int i = 0; i < size; i++) {
			swarm[i] = new Particle(array, antennaNumber, coefficients);
		}
		
		globalBestDesign = swarm[0].getPersonalBestDesign();
		globalBestResult = swarm[0].getPersonalBestResult();
		System.out.println("[0] New Global Best " + globalBestDesign + " with a LSS of: " + globalBestResult );
		
		//Checks which Swarm has the initial best.
		for(int i = 0; i < size; i++){
			if(swarm[i].getPersonalBestResult() < globalBestResult) {
				globalBestDesign = swarm[i].getPersonalBestDesign();
				System.out.println("[0] New Global Best " + globalBestDesign + " with a LSS of: " + globalBestResult );
			}
		}
		updateSwarmsGlobalBest();
	}
	
	private void updateSwarmsGlobalBest() {
		for(int i = 0; i < swarm.length; i++){
			swarm[i].setGlobalBest(globalBestDesign, globalBestResult);
		}
	}
	
	public double[] searchSpace(int numberOfSearches){
		for(int i = 0; i < numberOfSearches; i++) {
			for(int j = 0; j < swarm.length; j++) {
				swarm[j].searchSpace();
			}
			for(int j = 0; j < swarm.length; j++){
				if(swarm[j].getPersonalBestResult()< globalBestResult && array.is_valid(swarm[j].getPersonalBestDesign())) {
					globalBestDesign = Arrays.copyOf(swarm[j].getPersonalBestDesign(), swarm[j].getPersonalBestDesign().length);
					globalBestResult = swarm[j].getPersonalBestResult();
					updateSwarmsGlobalBest();
				}
			}
			for(int j = 0; j < swarm.length; j++) {
				swarm[j].calculateNewVelocity();
			}
		}
		System.out.println("Finished with a global best of: " + globalBestResult);
		return globalBestDesign;
	}
}
