package ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Stack;

import main.Fleet;
import main.GameObject;
import main.Planet;
import main.Player;

import org.newdawn.slick.Color;

/**
 * This class handles all AI logic. The AI is actually really stupid since it's only reactive but if
 * the player is active he seldom notices it. The basic algorithm is rather simple:
 * 1. Figure out all tasks we need to do during the next turn. This can be to defend a planet, colonize or attack a planet
 * if the opportunity presents itself.
 * 2. Combine these tasks with the all available fleets to figure out which fleet is more suitable for each tasks.
 * 3. Move the fleets according to the most suitable assignments.
 * @author danieka
 *
 */
public class AIPlayer extends Player{
	public static enum color {
        RED,
        GREEN,
        PINK,
    }
	
	private float defendAffinity = 50;
	private float attackAffinity = 40;
	private float colonizeAffinity = 50;
	//private Planet goal;

	GameObject G;
	ArrayList<Task> taskList;
	ArrayList<Assignement> assignementList;

	public AIPlayer (String name, Color color) {
		super(name, color);
		G = GameObject.getInstance();

	}
	
	/**
	 * This function is the external function that GameObjects calls. This should in turn call all internal functions as needed.
	 */
	public void makeMove(){	
		//setGoal();
		gatherTasks();
		assignTasks();
		executeAssignements();
	}

	/**
	 * Set a overarching goal for the AI-player to strive against. This is actually not acted on right now.
	 */
	//private void setGoal() {
		//goal = G.getPlayerPlanets(G.getHumanPlayer()).get(0);
	//}

	/**
	 * This dfs-search goes through all planets and generates appropriate tasks that ought to be performed in the next round.
	 */
	private void gatherTasks(){
		taskList = new ArrayList<Task>();
		Planet start = G.getPlayerPlanets((Player)this).get(0);
		// DFS uses Stack data structure
		HashSet<Planet> visited = new HashSet<Planet>();
		Stack<Planet> stack = new Stack<Planet>();
		stack.add(start);
		visited.add(start);
		while(!stack.isEmpty()) {
			Planet planet = stack.pop();
			for( Planet next : G.getNeighborPlanets(planet)){
				if(!visited.contains(next) && next.getOwner() == this){
					stack.add(next);
					visited.add(next);
				}
				else if(next.getOwner() == null){
					//This means we should colonize the planet.
					taskList.add(new Task(colonizeAffinity*next.getProductionCapacity(), next, 3));
				}
				else if(next.getOwner() != this){
					//This mean the planet next is owned by the enemy.
					//goal = next;
					int enemyShips = 1; //Suspect we could get strange behavior if requiredShips == 0
					for(Fleet fleet : next.getFleets()){
						enemyShips += fleet.getSize();
					}
					int ourShips = 0;
					for(Fleet fleet : planet.getFleets()){
						ourShips += fleet.getSize();
					}
					if(enemyShips < ourShips)
						taskList.add(new Task(attackAffinity*next.getProductionCapacity(), next, (int) Math.ceil(enemyShips*1.3)));
					if(enemyShips >= ourShips)
						taskList.add(new Task(defendAffinity*planet.getProductionCapacity(), planet, (int) Math.ceil(enemyShips*0.8)));
				}
			}
		}
		Collections.sort(taskList);
	}

	/**
	 * Here we generate all possible assignements for all fleets and all tasks.
	 */
	private void assignTasks(){
		assignementList = new ArrayList<Assignement>();
		for(Task task : taskList){
			for(Fleet fleet : G.getPlayerFleets(this)){
				assignTask(task, fleet);
			}
		}
		Collections.sort(assignementList);
	}

	/**
	 * Calculates the suitability of a task for fleet.
	 * @param task
	 * @param fleet
	 */
	private void assignTask(Task task, Fleet fleet){
		float score = task.getScore()*(1/Math.max(1, G.path(fleet.getPlanet(), task.getPlanet()).length - 1));
		assignementList.add(new Assignement(task, score, fleet)); 
	}

	/**
	 * Walks through all tasks and assigns the most suitable fleet.
	 */
	private void executeAssignements() {
		HashSet<Fleet> assignedFleets = new HashSet<Fleet>();
		for(Assignement ass : assignementList){
			if(assignedFleets.contains(ass.getFleet()) || ass.getTask().getShipsNecessary() == 0) continue;
			if(ass.getTask().getShipsNecessary() < ass.getFleet().getSize() && ass.getTask().getShipsNecessary() > 0){
				Fleet newFleet = G.splitFleet(ass.getFleet(), ass.getTask().getShipsNecessary());
				ass.getTask().decreaseShipsNecessary(newFleet.getSize());
				newFleet.moveTo(ass.getTask().getPlanet());
				assignedFleets.add(newFleet);
			}
			else if(ass.getTask().getShipsNecessary() >= ass.getFleet().getSize()){
				ass.getFleet().moveTo(ass.getTask().getPlanet());
				ass.getTask().decreaseShipsNecessary(ass.getFleet().getSize());
				assignedFleets.add(ass.getFleet());
			}
		}
		for(Fleet f : G.getPlayerFleets(this)){
			if(!assignedFleets.contains(f)){
				//f.moveTo(goal); Not implemented at this time.
			}
		}
	}
}
