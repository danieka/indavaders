package ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

import main.Fleet;
import main.GameObject;
import main.Move;
import main.Planet;
import main.Player;

import org.newdawn.slick.Color;

/**
 * 
 * @author danieka
 *
 */
public class AIPlayer extends Player{
	private boolean verbose = true;
	private float defendAffinity = 50;
	private float attackAffinity = 40;
	private float colonizeAffinity = 45;

	GameObject G;
	ArrayList<Task> taskList;
	ArrayList<Assignement> assignementList;

	public AIPlayer (String name, Color color) {
		super(name, color);
		G = GameObject.getInstance();

	}

	public void makeMove(){		
		gatherTasks();
		assignTasks();
		executeAssignements();
	}


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
			for( Planet next : G.getNeighbourPlanets(planet)){
				if(!visited.contains(next) && next.getOwner() == this){
					stack.add(next);
					visited.add(next);
				}
				else if(next.getOwner() == null){
					taskList.add(new Task(colonizeAffinity*next.getProductionCapacity(), next, 3));
				}
				else if(next.getOwner() != this){
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
	 * Here we allocate our fleets to 
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

	private void assignTask(Task task, Fleet fleet){
		float score = task.getScore()/Math.max(1, G.path(fleet.getPlanet(), task.getPlanet()).length -2);
		assignementList.add(new Assignement(task, score, fleet)); 
	}

	private void executeAssignements() {
		HashSet<Fleet> assignedFleets = new HashSet<Fleet>();
		for(Assignement ass : assignementList){
			if(assignedFleets.contains(ass.getFleet())) continue;
			
			if(ass.getTask().getShipsNecessary() < ass.getFleet().getSize() && ass.getTask().getShipsNecessary() > 0){
				Fleet newFleet = G.splitFleet(ass.getFleet(), ass.getTask().getShipsNecessary());
				ass.getTask().decreaseShipsNecessary(newFleet.getSize());
				G.addMove(new Move(newFleet, ass.getTask().getPlanet(), newFleet.getPlanet()));
				assignedFleets.add(newFleet);
			}
			else if(ass.getTask().getShipsNecessary() >= ass.getFleet().getSize()){
				G.addMove(new Move(ass.getFleet(), ass.getTask().getPlanet(), ass.getFleet().getPlanet()));
				ass.getTask().decreaseShipsNecessary(ass.getFleet().getSize());
				assignedFleets.add(ass.getFleet());
			}
		}
	}
}
