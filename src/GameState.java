import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameState extends BasicGameState{
	private ArrayList<Circle> gamePlanets;
	private ArrayList<Planet> planetList;
	//private static int turn;
	private GameObject game;

	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		game = GameObject.getInstance();
		//turn = 0;
		planetList = game.getPlanets();
	}

	public void update(GameContainer container, StateBasedGame sbg, int arg2)
			throws SlickException {
		if(container.getInput().isKeyPressed(Input.KEY_2)){
			sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
		for(Planet p: planetList){
			int x = p.getX();
			int y = p.getY();
			gamePlanets.add(new Circle(x, y, 15));
		}
	}
	
	public void render(GameContainer container, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.drawString("This is the game", 280, 200);
		for(Circle p: gamePlanets){
			g.fill(p);
		}
		
	}

	public int getID() {
		return 1;
	}
	
}
