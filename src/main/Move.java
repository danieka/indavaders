package main;

public class Move {
	private Fleet fleet;
	private Planet to;
	private Planet from;

	public Move(Fleet fleet, Planet to, Planet from){
		this.fleet = fleet;
		this.to = to;
		this.from = from;
	}
	
	public Move(Fleet fleet, Planet to){
		this.fleet = fleet;
		this.to = to;
		this.from = fleet.getPlanet();
	}
	
	public void execute(){
		fleet.setLocation(to);
		from.removeFleet(fleet);
		to.addFleet(fleet);
	}
}
