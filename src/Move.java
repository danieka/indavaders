
public class Move {
	private Fleet fleet;
	private Planet to;
	private Planet from;

	Move(Fleet fleet, Planet to, Planet from){
		this.fleet = fleet;
		this.to = to;
		this.from = from;
	}
}
