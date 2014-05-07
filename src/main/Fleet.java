package main;

public class Fleet {
	private int size;
	private Planet location;
	private Player owner;
	
	Fleet(int size, Player owner, Planet location){
		this.size = size;
		this.owner = owner;
		this.location = location;
	}
	
	public int getSize(){
		return size;
	}
	
	public void moveTo(Planet planet){
		GameObject G = GameObject.getInstance();
		int[] path = G.path(location, planet);
		G.addMove(new Move(this, G.getPlanets().get(path[1])));
		return;
	}
	
	public Planet getPlanet(){
		return location;
	}
	
	public Player getOwner(){
		return owner;
	}

	public void setLocation(Planet to) {
		location = to;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
}
