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
import org.newdawn.slick.geom.Rectangle;


public class StartState extends BasicGameState{
	
	Image indavaders;
	Boolean start;
	Boolean load;
	Boolean instructions;
	Boolean tutorial;
	Boolean quit;

	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		indavaders = new Image("resources/inda.png");
		start = false;
		load = false;
		instructions = false;
		tutorial = false;
		quit = false;
		
	}

	public void update(GameContainer container, StateBasedGame sbg, int arg2)
			throws SlickException {
		if(container.getInput().isKeyPressed(Input.KEY_1) || start == true){
			start = false;
			sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
		}
		if(container.getInput().isKeyPressed(Input.KEY_5) || quit == true){
			Indavaders.exit();
			}
		if(container.getInput().isKeyPressed(Input.KEY_4) || tutorial == true){
			tutorial = false;
			sbg.enterState(3, new FadeOutTransition(), new FadeInTransition());
			}
	}
	
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		indavaders.draw(50, 150);
		
		g.draw(new Rectangle(400, 360, 150, 40));
		g.draw(new Rectangle(400, 420, 150, 40));
		g.draw(new Rectangle(400, 480, 150, 40));
		g.draw(new Rectangle(400, 540, 150, 40));
		g.draw(new Rectangle(400, 600, 150, 40));
		g.drawString("[1]New Game", 405, 372);
		g.drawString("[2]Load Game", 405, 432);
		g.drawString("[3]Instructions", 405, 492);
		g.drawString("[4]Tutorial", 405, 552);
		g.drawString("[5]Exit Game", 405, 612);
	}

	public int getID() {
		return 0;
	}
	
	@Override
	public void mousePressed(int button, int posX, int posY){
		if (button == 0){
			if((posX>400 && posX<550) && (posY>360 && posY<400)){
				start = true;
			}
			if((posX>400 && posX<550) && (posY>420 && posY<460)){
				load = true;
			}
			if((posX>400 && posX<550) && (posY>480 && posY<520)){
				instructions = true;
			}
			if((posX>400 && posX<550) && (posY>540 && posY<580)){
				tutorial = true;
			}
			if((posX>400 && posX<550) && (posY>600 && posY<640)){
				quit = true;
			}
		}
	}

}
