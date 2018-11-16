import java.util.Arrays;

public class ParticleSwarm {
	
	private Particle[] swarm;
	private double[] globalBestDesign;
	private double globalBestResult;
	private AntennaArray array;
	
	/**
	 * Creates a particle swarm of a user-defined size
	 * @param array The array to solve the problem for
	 * @param antennaNumber The number of antennae
	 * @param coefficients The coefficients for calculating the particle's new positions.
	 * @param size The size of the particle swarms
	 */
	public ParticleSwarm(AntennaArray array, int antennaNumber, double[] coefficients, int size) {
		swarm = new Particle[size];
		this.array = array;
		//Creates particle swarm
		for(int i = 0; i < size; i++) {
			swarm[i] = new Particle(array, antennaNumber, coefficients);
		}
		
		globalBestDesign = swarm[0].getPersonalBestDesign();
		globalBestResult = swarm[0].getPersonalBestResult();
		System.out.println("[0] New Global Best " + globalBestDesign.toString() + " with a LSS of: " + globalBestResult );
		
		//Checks which Swarm has the initial best.
		for(int i = 0; i < size; i++){
			if(swarm[i].getPersonalBestResult() < globalBestResult) {
				globalBestDesign = swarm[i].getPersonalBestDesign();
				globalBestResult = swarm[i].getPersonalBestResult();
				System.out.println("[0] New Global Best " + globalBestDesign.toString() + " with a LSS of: " + globalBestResult );
			}
		}
		updateSwarmsGlobalBest();
	}
	
	/**
	 * Updates the gloabl best of the swarm
	 */
	private void updateSwarmsGlobalBest() {
		for(int i = 0; i < swarm.length; i++){
			swarm[i].setGlobalBest(globalBestDesign, globalBestResult);
		}
	}
	
	/**
	 * Causes the particles to search the state space to find the best position
	 * @param numberOfSearches
	 * @return
	 */
	public double[] searchSpace(int numberOfSearches){
		for(int i = 0; i < numberOfSearches; i++) {
			//Each particle searches the state space
			for(int j = 0; j < swarm.length; j++) {
				swarm[j].searchSpace();
			}
			//Checks all particles and updates the global best
			for(int j = 0; j < swarm.length; j++){
				if(swarm[j].getPersonalBestResult()< globalBestResult && array.is_valid(swarm[j].getPersonalBestDesign())) {
					globalBestDesign = Arrays.copyOf(swarm[j].getPersonalBestDesign(), swarm[j].getPersonalBestDesign().length);
					globalBestResult = swarm[j].getPersonalBestResult();
					System.out.println("[" + i + "] New Global Best " + globalBestDesign.toString() + " with a LSS of: " + globalBestResult );
					updateSwarmsGlobalBest();
				}
			}
			//Calculates new velocity for all particles based on global best.
			for(int j = 0; j < swarm.length; j++) {
				swarm[j].calculateNewVelocity();
			}
		}
		System.out.println("Finished with a global best of: " + globalBestResult);
		return globalBestDesign;
	}
}
