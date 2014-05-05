import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Game object
 * @author danieka
 *
 */
public class GameObject {
	
	private ArrayList<Player> players;
	private ArrayList<Planet> planets;
	private ArrayList<Fleet> fleets;
	private Graph G;
	private static GameObject uniqInstance;
	private Queue<Move> moveQueue;
	
	
	private GameObject(){	
		moveQueue = new LinkedList<Move>();
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
            		System.out.println("On line " + line);
            	}
            	if(G != null && elements.length == 3){
            		G.add(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
            		
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
            		System.out.println("On line " + line);
            	}
            	if(elements.length == 4){
            		//G.add(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
            		
            			System.out.println("a");
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
		System.out.println(planets);
		return planets;
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
		players.add(new Player("name"));
		for(int i = 0; i < amountOfPlayers-1; i++){
			players.add(new AIPlayer("name"));
		}
		
	}
	
	public Player getHumanPlayer(){		
		return players.get(0);
	}
	
	
	public ArrayList<AIPlayer> getAIPlayers(){
		ArrayList<AIPlayer> list = new ArrayList<AIPlayer>();
		for(Player player: players){
			if(player.getClass() == AIPlayer.class){
				list.add((AIPlayer) player); 
			}			
		}		
		System.out.println(players);
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
		moveQueue.add(move);
	}
	
	public void nextTurn(){
		for(AIPlayer p : getAIPlayers()){
			for(Fleet f : getPlayerFleets(p)){
				p.makeMove(f);
			}
		}
		
		Move m;
		while(!moveQueue.isEmpty()){
			m = moveQueue.poll();
			m.execute();
		}
		
		//Gå igenom alla planeter å kolla om det finns flottor från olika spelare,
		//om det finns det ska dom slåss.
		for(Planet planet: planets){
			Player i = null;
			for(Fleet fleet: planet.getFleets()){				
				if(i == null){
					i = fleet.getOwner();
				}
				if(i != null){
					//TODO FIGHT
				}
			}			
		}
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
}
