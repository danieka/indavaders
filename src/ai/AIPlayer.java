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
 * 
 * @author danieka
 *
 */
public class AIPlayer extends Player{
	private float defendAffinity = 10;
	private float attackAffinity = 5;
	private float colonizeAffinity = 5;
	
	GameObject G;
	ArrayList<Task> taskList;
	
	public AIPlayer (String name, Color color) {
		super(name, color);
		G = GameObject.getInstance();		
	}
	
	public void makeMove(){
		gatherTasks();
		assignTasks();
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
		System.out.println(taskList);
	}
	
	/**
	 * Here we allocate our fleets to 
	 */
	private void assignTasks(){
		return;
	}
}
