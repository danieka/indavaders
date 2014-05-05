
public class Fleet {
	private int size;
	private Planet location;
	private Player owner;
	
	Fleet(){
		
	}
	
	public int getSize(){
		return size;
	}
	
	public void moveTo(Planet planet){
		GameObject G = GameObject.getInstance();
		int[] path = G.path(location, planet);
		location = G.getPlanets().get(path[0]);
		G.addMove(new Move(this, location, planet));
		return;
	}
	
	public Planet getPlanet(){
		return location;
	}
	
	public Player getOwner(){
		return owner;
	}
}
