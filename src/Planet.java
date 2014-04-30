/**
 * Class representing planets in the game.
 * @author danieka
 *
 */
public class Planet {
	private int size;
	private String name;
	private int productionCapacity;
	
	Planet (int size, String name){
		this.size = size;
		this.name = name;
		this.productionCapacity = size;
	}
	
	public int getProductionCapacity() {
		return 0;
	}
	
	public void setOwner(Player player){
		return;
	}
}
