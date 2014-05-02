
public class Fleet {
	private int size;
	private Planet location;
	private Player owner;
	
	Fleet(){
		
	}
	
	public int getSize(){
		return size;
	}
	
	public void moveTowards(Planet planet){
		GameObject G = GameObject.getInstance();
		int[] path = G.path(location, planet);
		location = G.getPlanet(path[0]);
		return;
	}
	
	public Planet getPlanet(){
		return location;
	}
	
	public Player getOwner(){
		return owner;
	}
}
