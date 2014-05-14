package main;

public class Move {
	private Fleet fleet;
	private Planet to;
	private Planet from;

	/**
	 * Constructor for objects of class Move
     * Moves a fleet with parameters fleet, to and from.   
	 * @param fleet
	 * @param to
	 * @param from
	 */
	public Move(Fleet fleet, Planet to, Planet from){
		this.fleet = fleet;
		this.to = to;
		this.from = from;
	}
	
	/**
	 * Constructor for objects of class Move.
	 * Moves a fleet with parameters fleet and to.
	 * @param fleet
	 * @param to
	 */
	public Move(Fleet fleet, Planet to){
		if(!GameObject.getInstance().getNeighbourPlanets(fleet.getPlanet()).contains(to)){
			throw new IllegalArgumentException("Planet is no neigbour");
		}
		this.fleet = fleet;
		this.to = to;
		this.from = fleet.getPlanet();
	}
	
	/**
	 * Moves a fleet from one planet to another.
	 */
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
    	GameObject G = GameObject.getInstance(); 
    	return "Move fleet " + fleet.toString() + " from " + from.toString() + " to " + to.toString();
    }
}
