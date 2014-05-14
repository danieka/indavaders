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
		if(!GameObject.getInstance().getNeighborPlanets(fleet.getPlanet()).contains(to)){
			throw new IllegalArgumentException("Planet is no neigbour");
		}
		this.fleet = fleet;
		this.to = to;
		this.from = fleet.getPlanet();
	}
	
	public void execute(){
		fleet.setLocation(to);
		from.removeFleet(fleet);
		to.addFleet(fleet);
	}
	
    @Override
    public boolean equals(Object v) {
        boolean retVal = false;

        if (v instanceof Move){
            Move ptr = (Move) v;
            retVal = ptr.fleet == this.fleet;
        }

     return retVal;
    }

    @Override
    public int hashCode() {
    	int hash;
        hash = fleet.hashCode();
        return hash;
    }
    
    @Override
    public String toString(){
    	return "Move fleet " + fleet.toString() + " from " + from.toString() + " to " + to.toString();
    }
}
