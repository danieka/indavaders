package main;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import org.newdawn.slick.Color;

import ui.Drawable;
import ai.AIPlayer;
import ai.Task;

/**
 * Game object
 * @author danieka
 *
 */
public class GameObject {
	private enum color {
		RED(Color.red), GREEN(Color.green), MAGENTA(Color.magenta), ORANGE(Color.orange);

		private Color color;

		private color(Color color) {
			this.color = color;
		}
	}

	private ArrayList<Player> players;
	private ArrayList<Planet> planets;
	private ArrayList<Fleet> fleets;
	private Graph G;
	private static GameObject uniqInstance;
	private Queue<Move> moveQueue;
	private Random rand;
	private Player humanPlayer;

	private GameObject(){	
		moveQueue = new LinkedList<Move>();
		rand = new Random();

		BufferedReader file = null;
		// This "try-with-resource" statement automatically calls file.close()
		// just before leaving the try block.
		try {
			file = new BufferedReader(new InputStreamReader(new FileInputStream("src/Planets.txt"), "UTF-8"));
			String line = file.readLine();
			while(line != null){
				if(line.startsWith("//") || line.trim().length() == 0){
					//If the line starts with a comment or is empty we can safely ignore it.
					line = file.readLine();
					continue;
				}

				String[] elements = line.split(" +");
				elements = removeComments(elements);
				if(G == null && elements.length == 1){
					G = new HashGraph(Integer.parseInt(elements[0]));
					line = file.readLine();
					continue;
				}
				if(G == null && elements.length != 1){
					System.err.println("Illegal size argument");
				}
				if(G != null && elements.length != 3){
					System.err.println("Illegal number of arguments on the line, should be 3 not " + elements.length);
					System.err.println("On line " + line);
				}
				if(G != null && elements.length == 3){
					G.addBi(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));

				}

				line = file.readLine();            	
			}

		} catch (IOException e) {
			System.err.printf("%s%n",  e);
			System.exit(1);
		} catch (NumberFormatException e){
			System.err.printf("Illegal number %s%n",  e);
			System.exit(1);
		} catch (IllegalArgumentException e){
			System.err.println("The vertex is larger than the size of the graph.");
			System.err.printf("%s%n",  e);
			System.exit(1);
		}
		players = new ArrayList<Player>();
		planets = new ArrayList<Planet>();
		fleets = new ArrayList<Fleet>();

		file = null;
		// This "try-with-resource" statement automatically calls file.close()
		// just before leaving the try block.
		try {
			file = new BufferedReader(new InputStreamReader(new FileInputStream("src/Planetpositions.txt"), "UTF-8"));
			String line = file.readLine();
			while(line != null){
				if(line.startsWith("//") || line.trim().length() == 0){
					//If the line starts with a comment or is empty we can safely ignore it.
					line = file.readLine();
					continue;
				}

				String[] elements = line.split(" +");
				elements = removeComments(elements);            	
				if(elements.length != 4){
					System.err.println("Illegal number of arguments on the line, should be 4 not " + elements.length);
					System.err.println("On line " + line);
				}
				if(elements.length == 4){
					planets.add(new Planet(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]), Integer.parseInt(elements[2]), elements[3], null));

				}

				line = file.readLine();            	
			}

		} catch (IOException e) {
			System.err.printf("%s%n",  e);
			System.exit(1);
		} catch (NumberFormatException e){
			System.err.printf("Illegal number %s%n",  e);
			System.exit(1);
		} catch (IllegalArgumentException e){
			System.err.println("The vertex is larger than the size of the graph.");
			System.err.printf("%s%n",  e);
			System.exit(1);
		}
	}

	public ArrayList<Planet> getPlanets(){
		return planets;
	}

	public Planet getPlanet(int i){
		return planets.get(i);
	}

	public ArrayList<Fleet> getFleets(){
		return fleets;
	}


	/**
	 * Returns an arraylist of all planets beloning to player.
	 * @param player
	 */
	public ArrayList<Planet> getPlayerPlanets(Player player){
		ArrayList<Planet> list = new ArrayList<Planet>();
		for(Planet plan: planets){
			if(plan.getOwner() == player){
				list.add(plan); 
			}			
		}		
		return list;
	}

	public Fleet splitFleet(Fleet fleet, int newSize){
		if(newSize > fleet.getSize()) throw new IllegalArgumentException("Illegal size");
		Fleet newFleet = new Fleet(newSize, fleet.getOwner(), fleet.getPlanet());
		fleets.add(newFleet);
		fleet.getPlanet().addFleet(newFleet);
		fleet.setSize(fleet.getSize() - newSize);
		return newFleet;
	}

	public ArrayList<Fleet> getPlayerFleets(Player player){
		ArrayList<Fleet> list = new ArrayList<Fleet>();
		for(Fleet fleet: fleets){
			if(fleet.getOwner() == player){
				list.add(fleet); 
			}			
		}		
		return list;
	}

	public void createPlayers(int amountOfPlayers){
		humanPlayer = new Player("name", Color.blue);
		players.add(humanPlayer);
		planets.get(0).setOwner(players.get(0));
		fleets.add(new Fleet(20, players.get(0), planets.get(0)));			
		planets.get(0).addFleet(fleets.get(0));
		for(int i = 1; i < amountOfPlayers; i++){			
			players.add(new AIPlayer("name", color.values()[i].color));	
			planets.get(i).setOwner(players.get(i));			
			fleets.add(new Fleet(20, players.get(i), planets.get(i)));	
			planets.get(i).addFleet(fleets.get(i));
		}
	}

	public void randomPlayers(int amountOfPlayers){
		if(humanPlayer == null){
			Color playerColor = new Color(0x002b7cff);
			humanPlayer = new Player("name", playerColor);
			players.add(humanPlayer);
			Planet p = randomUnownedPlanet();
			p.setOwner(players.get(0));
			fleets.add(new Fleet(20, players.get(0), p));			
			p.addFleet(fleets.get(0));
			for(int i = 1; i < amountOfPlayers; i++){
				p = randomUnownedPlanet();
				players.add(new AIPlayer("name", color.values()[i].color));	
				p.setOwner(players.get(i));			
				fleets.add(new Fleet(20, players.get(i), p));	
				p.addFleet(fleets.get(i));
			}
		}
	}

	public Planet randomUnownedPlanet(){
		Planet p = getPlanet(rand.nextInt(19));
		while(p.getOwner() != null){
			p = getPlanet(rand.nextInt(19));
		}
		return p;
	}

	public Player getHumanPlayer(){		
		return humanPlayer;
	}


	public ArrayList<AIPlayer> getAIPlayers(){
		ArrayList<AIPlayer> list = new ArrayList<AIPlayer>();
		for(Player player: players){
			if(player.getClass() == AIPlayer.class){
				list.add((AIPlayer) player); 
			}			
		}		
		return list;
	}

	/**
	 * This returns an array devoid of comments.
	 * 
	 * Accepts an array of strings, such as the one you get from String.split()
	 * The functions removes any string that are behind a comment ("//") including the comment.
	 * @param strarr
	 * @return
	 */
	private static String[] removeComments(String[] strarr){
		int i = 0;
		for(String s : strarr){
			if (s.startsWith("//")){
				break;
			}
			i++;
		}
		return Arrays.copyOfRange(strarr, 0, i);
	}	

	public static synchronized GameObject getInstance() {
		if (uniqInstance == null) {
			uniqInstance = new GameObject();
		}
		return uniqInstance;
	}

	public void addMove(Move move){
		if(moveQueue.contains(move)){
			moveQueue.remove(move);
		}
		if(moveQueue.contains(move)){
			throw new IllegalArgumentException();
		}
		moveQueue.add(move);
	}

	public ArrayList<int[]> getAllEdges(){
		ArrayList<int[]> ret = new ArrayList<int[]>();
		for(int i = 0; i < G.numVertices(); i++){
			for(VertexIterator iter = G.neighbors(i); iter.hasNext();){
				int n = iter.next();
				int[] edge = new int[]{planets.get(i).getX(), planets.get(i).getY(), planets.get(n).getX(), planets.get(n).getY()};
				if (!ret.contains(edge)){
					ret.add(edge);
				}
			}
		}
		return ret;
	}

	public ArrayList<Planet> getNeighbourPlanets(Planet planet){
		ArrayList<Planet> ret = new ArrayList<Planet>();
		for (VertexIterator iter = G.neighbors(planets.indexOf(planet)); iter.hasNext();){
			int v = iter.next();
			ret.add(planets.get(v));
		}
		return ret;
	}

	public void nextTurn(){
		System.out.println("<<<<<<<<<<<<<<  New turn  >>>>>>>>>>>>>>");
		for(AIPlayer p : getAIPlayers()){
			p.makeMove();
		}

		executeMoves();


		merge();


		fight();

		changeOwnership();
		eliminatePlayer();
		spawnNewFleets();	
		merge();
		fight();
	}


	public void fight() {

		for(Planet planet: planets){
			Fleet fleetOne = null;
			for(Fleet fleet: planet.getFleets()){				
				if(fleetOne == null){
					fleetOne = fleet;	
					continue;
				}
				if(fleetOne.getOwner() != fleet.getOwner()){
					if(fleet.getSize() == 0){
						continue;
					}
					if(fleetOne.getSize() > fleet.getSize()){
						fleetOne.setSize(fleetOne.getSize() - fleet.getSize());
						fleet.setSize(0);
					} else if(fleetOne.getSize() < fleet.getSize()){
						fleet.setSize(fleet.getSize() - fleetOne.getSize() );
						fleetOne.setSize(0);
						fleetOne = fleet;
					} else if(fleetOne.getSize() == fleet.getSize()){
						fleetOne.setSize(1);
						fleet.setSize(0);
					}
				}
			}
			Iterator<Fleet> i = planet.getFleets().iterator();
			while (i.hasNext()) {
				Fleet f = i.next(); // must be called before you can call i.remove()
				if(f.getSize() == 0){
					i.remove();
					fleets.remove(f);
				}			   
			}
		}
	}

	public void executeMoves(){
		Move m;
		while(!moveQueue.isEmpty()){
			m = moveQueue.poll();
			m.execute();
		}
	}

	public void changeOwnership(){		
		for(Planet planet: planets){
			planet.updateOwnership();
		}
	}

	public void eliminatePlayer(){
		Iterator<Player> i = players.iterator();
		while (i.hasNext()) {
			Player p = i.next(); // must be called before you can call i.remove()
			if(getPlayerPlanets(p).isEmpty()){
				i.remove();
			}			   
		}
	}

	public void spawnNewFleets(){		
		for(Planet planet: planets){
			if(planet.getOwner() != null){
				int r = 5;
				if(planet.getOwner() == players.get(0)){
					r = 6;
				}
				Fleet f = new Fleet(r*planet.getProductionCapacity(), planet.getOwner(), planet);
				planet.addFleet(f);
				fleets.add(f);
			}
		}
	}	

	public void merge(){
		for(Planet planet: planets){			
			for(Fleet f : planet.getFleets()){
				if (f.getSize() == 0) continue;
				for(Fleet fleet: planet.getFleets()){					
					if(fleet == f || fleet.getSize() == 0){					
						continue;
					}
					if(f.getOwner() == fleet.getOwner()){
						if(fleet.getSize() == 0){
							continue;
						}					
						int size = f.getSize();
						f.setSize(size + fleet.getSize());
						fleet.setSize(0);					
					}			
				}
			}
			Iterator<Fleet> i = planet.getFleets().iterator();
			while (i.hasNext()) {
				Fleet f = i.next(); // must be called before you can call i.remove()
				if(f.getSize() == 0){
					i.remove();
					fleets.remove(f);
				}			   
			}
		}		
	}

	public boolean win(){
		int count = 0;
		for(Planet planet: planets){
			if(planet.getOwner() == getHumanPlayer()){
				count++;                
			}else{        
				break;
			}
		}
		if(count == planets.size()){
			return true;            
		}
		return false;
	}

	public boolean lose(){
		if(players.contains(humanPlayer)){
			return false;
		}
		return true;
	}	

	public int[] path(Planet fromPlanet, Planet destPlanet){
		int from = planets.indexOf(fromPlanet);
		int next = planets.indexOf(destPlanet);
		int[][] i = dijkstras(from);		
		LinkedList<Integer> directions = new LinkedList<Integer>();
		while(next != from){
			directions.addFirst(next);
			next = i[1][next];
		}
		directions.addFirst(from);
		int[] ret = new int[directions.size()];
		for(int j = 0;j < ret.length;j++)
			ret[j] = directions.get(j);
		return ret;
	}

	/**
	 * Djikstras algorithm.
	 * 
	 * Returns two arrays of integers inside an array of arrays containing integers = int[][].
	 * The first array contains the cost from that node to the source. If MAX_INT there is no path.
	 * The second array contains the previous node in the optimal path to source for each node. If -1 there is no path.
	 * 
	 * @param source
	 * @return
	 */
	private int[][] dijkstras(int source){
		//TODO: Stop once we reach the right planet
		int numVertices = G.numVertices();
		int[] dist = new int[numVertices];
		int[] prev = new int[numVertices];
		HashSet<Integer> Q = new HashSet<Integer>(numVertices);
		for(int v = 0; v < numVertices; v++){
			dist[v] = Integer.MAX_VALUE;
			prev[v] = -1;
			Q.add(v);
		}
		dist[source] = 0;

		while (!Q.isEmpty()){
			int u = max(dist);
			for(int v : Q){
				if(dist[v] <= dist[u]){
					u = v;
				}
			}
			Q.remove(u);
			if(dist[u] == Integer.MAX_VALUE){
				continue;
			}
			for (VertexIterator iter = G.neighbors(u); iter.hasNext();){
				int v = iter.next();
				int alt = dist[u] + G.cost(u, v);
				if(alt < dist[v]){
					dist[v] = alt;
					prev[v] = u;
				}
			}
		}

		int[][] r = {dist,prev};
		return r;
	}

	/**
	 * Returns the index of the largest value in the values array.
	 * @param values
	 * @return
	 */
	private static int max(int[] values) {
		int max = 0;
		for(int i = 0; i < values.length; i++) {
			if(values[i] > values[max])
				max = i;
		}
		return max;
	}

	public void destroy() {
		uniqInstance = new GameObject();
	}

	public Fleet createFleet(Player player1, Planet p, int i) {
		Fleet f = new Fleet(i, player1, p);
		fleets.add(f);
		p.addFleet(f);
		return f;
	}

	public ArrayList<Drawable> getDrawable() {
		ArrayList<Drawable> ret = new ArrayList<Drawable>(getPlayerPlanets(getHumanPlayer()));
		if(getPlayerPlanets(getHumanPlayer()).size() == 0) return ret;
		Planet start = getPlayerPlanets(getHumanPlayer()).get(0);
		// DFS uses Stack data structure
		HashSet<Planet> visited = new HashSet<Planet>();
		Stack<Planet> stack = new Stack<Planet>();
		stack.add(start);
		visited.add(start);
		while(!stack.isEmpty()) {
			Planet planet = stack.pop();
			for( Planet next : getNeighbourPlanets(planet)){
				if(!visited.contains(next) && (planet.getOwner() == getHumanPlayer() || planet.siegedBy(getHumanPlayer()))){
					stack.add(next);
					visited.add(next);
				}
			}
			ret.add(planet);
			ret.addAll(planet.getFleets());
		}
		
		return ret;
	}
}
