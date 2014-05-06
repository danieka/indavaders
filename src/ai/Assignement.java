package ai;

import main.Fleet;

public class Assignement implements Comparable<Assignement> {
	private Task task;
	private Fleet fleet;
	private float score;
	
	Assignement(Task task, float score, Fleet fleet){
		this.task = task;
		this.score = score;
		this.fleet = fleet;
	}
	
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

	public Fleet getFleet() {
		return fleet;
	}
	
}
