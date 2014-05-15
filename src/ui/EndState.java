package ui;
import main.GameObject;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The final state of the game; you have either won or lost. 
 * @author Adam Pielbusch
 *
 */
public class EndState extends BasicGameState{
	
	private GameObject game;

	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		game = GameObject.getInstance();
	}

	public void update(GameContainer container, StateBasedGame sbg, int arg2)
			throws SlickException {
		if(container.getInput().isKeyPressed(Input.KEY_Q)){
			GameObject.destroy();
			GameObject.getInstance();
			sbg.enterState(0);
		}
		
	}
	
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		if(game.win() == true){ //Win text
			g.drawString("You have won!", 280, 300);
			g.drawString("Such best, very victory, many horse", 280, 315);
			g.drawString("Press any key to continue", 280, 330);
		}else{ //Lose text
			g.drawString("Game Over", 280, 300);
			g.drawString("Such defeat, very lose, many sad", 280, 315);
			g.drawString("Press any key to continue", 280, 330);
		}
	}

	public int getID() {
		return 2;
	}

}
