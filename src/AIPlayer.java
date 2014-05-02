/**
 * 
 * @author danieka
 *
 */
public class AIPlayer extends Player{
	AIPlayer (String name) {
		super(name);
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
			fleet.moveTowards(largestFleet.getPlanet());
		}
	}
}
