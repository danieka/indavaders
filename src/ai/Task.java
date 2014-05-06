package ai;

import main.Planet;

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

	public Planet getPlanet() {
		return destination;
	}

	public float getScore() {
		// TODO Auto-generated method stub
		return score+(score / shipsNecessary);
	}
}
