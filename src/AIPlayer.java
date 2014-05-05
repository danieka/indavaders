import org.newdawn.slick.Color;

/**
 * 
 * @author danieka
 *
 */
public class AIPlayer extends Player{
	AIPlayer (String name, Color color) {
		super(name, color);
	}
	
	public void makeMove(Fleet fleet){
		GameObject G = GameObject.getInstance();
		Fleet largestFleet = fleet;
		for(Fleet f : G.getPlayerFleets(this)){
			if(f.getSize() > largestFleet.getSize()){
				largestFleet = f;
			}
		}
		if(largestFleet == fleet){
			return;
		}
		else{
			fleet.moveTo(largestFleet.getPlanet());
		}
	}
}
