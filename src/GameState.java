import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameState extends BasicGameState{
	private ArrayList<Circle> gamePlanets;
	//private ArrayList<Line> paths;
	//private static int turn;
	private GameObject game;

	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		game = GameObject.getInstance();
		game.createPlayers(4);
		//turn = 0;
		gamePlanets = new ArrayList<Circle>();
		//paths = new ArrayList<Line>();
		
		for(Planet p: game.getPlanets()){
			int x = p.getX();
			int y = p.getY();
			gamePlanets.add(new Circle(x, y, 15));
		}
			
	}

	public void update(GameContainer container, StateBasedGame sbg, int arg2)
			throws SlickException {
		if(container.getInput().isKeyPressed(Input.KEY_2)){
			sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
		
	}
	
	public void render(GameContainer container, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.drawString("This is the game", 280, 200);
		g.setLineWidth(3);
		g.setColor(Color.green);
		for(int[] l : game.getAllEdges()){
			int x1 = l[0];
			int y1 = l[1];
			int x2 = l[2];
			int y2 = l[3];
			g.drawLine(x1, y1, x2, y2);
		}
		for(int i = 0; i < game.getPlanets().size(); i++){
			Circle c = gamePlanets.get(i);
			Planet p = game.getPlanets().get(i);
			if(p.getOwner() != null){
				g.setColor(p.getOwner().getColor());
				g.fill(c);
			}else{
				g.setColor(Color.white);
				g.fill(c);
			}
		}
		
		g.setColor(Color.white);
		
		}

	public int getID() {
		return 1;
	}
	
}
