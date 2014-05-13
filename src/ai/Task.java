package ai;

import main.Fleet;
import main.Planet;

/**
 * This class represents a task that the AI need to perform during the next turn.
 * @author danieka
 *
 */
public class Task implements Comparable<Task> {
	private float score;
	private Planet destination;
	private int shipsNecessary;
	
	Task(float score, Planet destination, int shipsNecessary){
		this.score = score;
		this.destination = destination;
		this.shipsNecessary = shipsNecessary;
	}
	
	public String toString(){
		return "Score: " + score + ", Dest: " + destination.getName() + ", Ships: " + shipsNecessary;
	}

	@Override
	public int compareTo(Task arg0) {
		return (int)Math.ceil(this.score+(this.score / this.shipsNecessary) - arg0.score+(arg0.score / arg0.shipsNecessary));
	}

	/**
	 * Get the planet associated with the task.
	 * @return
	 */
	public Planet getPlanet() {
		return destination;
	}

	/**
	 * Get the score, that measures necessity of the task.
	 * @return
	 */
	public float getScore() {
		return score+(score / shipsNecessary);
	}
	
	/**
	 * Number of ships needed to perform this task.
	 * @return
	 */
	public int getShipsNecessary() {
		return shipsNecessary;
	}

	/**
	 * Decrease the number of ships necessary.
	 * This happens when a fleet is assigned to the task.
	 * @param size
	 */
	public void decreaseShipsNecessary(int size) {
		shipsNecessary -= size;
	}
}
