import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Game object
 * @author danieka
 *
 */
public class GameObject {
	//Players
	//Planets
	//Fleets
	
	GameObject(){
		Graph G = null;
		
		// This "try-with-resource" statement automatically calls file.close()
        // just before leaving the try block.
        try (BufferedReader file = new BufferedReader(new FileReader("Distances.txt"))) {
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
	}
	
	public ArrayList<Planet> getPlanets(){
		return null;
	}
	
	public ArrayList<Fleet> getFleets(){
		return null;
	}
	
	/**
	 * Returns an arraylist of all planets beloning to player.
	 * @param player
	 */
	public ArrayList <Player> getPlayerPlanets(Player player){
		return null;
	}
	
	public ArrayList<Fleet> getPlayerFleets(Player player){
		return null;
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
}
