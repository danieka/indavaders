package ui;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


public class StartState extends BasicGameState{
	
	Image indavaders;

	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		indavaders = new Image("resources/inda.png");
		
	}

	public void update(GameContainer container, StateBasedGame sbg, int arg2)
			throws SlickException {
		if(container.getInput().isKeyPressed(Input.KEY_1)){
			sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
		}
		if(container.getInput().isKeyPressed(Input.KEY_4)){
			Indavaders.exit();
			}
	}
	
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		indavaders.draw(50, 150);
		g.drawString("[1]New Game", 280, 400);
		g.drawString("[2]Load Game", 280, 415);
		g.drawString("[3]Instructions", 280, 430);
		g.drawString("[4]Exit Game", 280, 445);
	}

	public int getID() {
		return 0;
	}

}
