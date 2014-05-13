package ai;

import main.Fleet;

/**
 * This class maps on task to one fleet, and the suitability of that combination.
 * @author danieka
 *
 */
public class Assignement implements Comparable<Assignement> {
	private Task task;
	private Fleet fleet;
	private float score;
	
	Assignement(Task task, float score, Fleet fleet){
		this.task = task;
		this.score = score;
		this.fleet = fleet;
	}
	
	/**
	 * Returns the task associated with the assignment.
	 * @return
	 */
	public Task getTask(){
		return task;
	}
	
	@Override
	public int compareTo(Assignement arg0) {
		return (int)Math.ceil(arg0.score - this.score);
	}
	
	public String toString(){
		return "Task: " + task.toString() + ", Ass. Scr: " + score + ", With fleet: " + fleet;
	}

	/**
	 * Returns the fleet associated with the assignment.
	 * @return
	 */
	public Fleet getFleet() {
		return fleet;
	}
	
}
