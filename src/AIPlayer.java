/**
 * 
 * @author danieka
 *
 */
public class AIPlayer extends Player{
	AIPlayer (String name) {
		super(name);
	}
	
	public void makeMove(Map map){
		for (Planet planet : map.getPlanets()){
			if (planet.getOwner() != this){
				System.out.println(planet);
			}
		}
	}
}
