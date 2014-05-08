package ui;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The final state of the game; game over. 
 * @author Adam Pielbusch
 *
 */
public class EndState extends BasicGameState{

	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
	}

	public void update(GameContainer container, StateBasedGame sbg, int arg2)
			throws SlickException {
		if(container.getInput().isKeyPressed(Input.KEY_Q)){
			sbg.enterState(0);
		}
		
	}
	
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.drawString("Game Over", 280, 200);
		g.drawString("Press any key to continue", 280, 215);
		
	}

	public int getID() {
		return 2;
	}

}
