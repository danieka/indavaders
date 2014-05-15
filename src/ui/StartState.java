package ui;

import main.GameObject;

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
/**
 * StartState is the first state of the game. From here you'll get to the game, tutorial and so on.
 * 
 * @author Adam Pielbusch
 *
 */

public class StartState extends BasicGameState{
	
	Image indavaders;
	private boolean start;
	private boolean tutorial;
	private boolean quit;
	private boolean secondMenu;
	private boolean thirdMenu;
	private static int x = 250;
	private static int y = 360;
	protected GameObject game;

	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		indavaders = new Image("resources/inda.png");
		start = false;
		tutorial = false;
		quit = false;
		secondMenu = false;
		thirdMenu = false;
		game = GameObject.getInstance();
		
	}

	public void update(GameContainer container, StateBasedGame sbg, int arg2)
			throws SlickException {
		//Start the game
		if(container.getInput().isKeyPressed(Input.KEY_1) || start == true){
			start = false;
			secondMenu = false;
			thirdMenu = false;
			sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
		}
		//Exit the game
		if(container.getInput().isKeyPressed(Input.KEY_5) || quit == true){
			Indavaders.exit();
			}
		//Enter the tutorial
		if(container.getInput().isKeyPressed(Input.KEY_4) || tutorial == true){
			tutorial = false;
			sbg.enterState(3, new FadeOutTransition(), new FadeInTransition());
			}
	}
	
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		indavaders.draw(50, 150); //the logo
		
		//Draws the buttons
		g.draw(new Rectangle(x, y, 150, 40));
		g.draw(new Rectangle(x, y+60, 150, 40));
		g.draw(new Rectangle(x, y+120, 150, 40));
		g.draw(new Rectangle(x, y+180, 150, 40));
		g.draw(new Rectangle(x, y+240, 150, 40));
		g.drawString("[1]New Game", x+5, y+12);
		g.drawString("[2]Load Game", x+5, y+72);
		g.drawString("[3]Instructions", x+5, y+132);
		g.drawString("[4]Tutorial", x+5, y+192);
		g.drawString("[5]Exit Game", x+5, y+252);
		
		if(secondMenu == true){
			g.draw(new Rectangle(x+180, y, 150, 40));
			g.draw(new Rectangle(x+180, y+60, 150, 40));
			g.drawString("Fog of War", x+185, y+12);
			g.drawString("Normal", x+185, y+72);
		}
		if(thirdMenu == true){
			g.draw(new Rectangle(x+360, y, 150, 40));
			g.draw(new Rectangle(x+360, y+60, 150, 40));
			g.draw(new Rectangle(x+360, y+120, 150, 40));
			g.drawString("Easy", x+365, y+12);
			g.drawString("Normal", x+365, y+72);
			g.drawString("Hard", x+365, y+132);
		}
	}

	public int getID() {
		return 0;
	}
	
	@Override
	public void mousePressed(int button, int posX, int posY){
		if (button == 0){
			if((posX>x && posX<x+150) && (posY>y && posY<400)){
				//start = true;
				secondMenu = true;
			}
			if((posX>x && posX<x+150) && (posY>y+60 && posY<460)){
				
			}
			if((posX>x && posX<x+150) && (posY>y+120 && posY<520)){
				
			}
			if((posX>x && posX<x+150) && (posY>y+180 && posY<580)){
				game.randomPlayers(2);
				tutorial = true;
			}
			if((posX>x && posX<x+150) && (posY>y+240 && posY<640)){
				quit = true;
			}
			//Fog of war
			if((posX>x+180 && posX<x+330) && (posY>y && posY<400) && secondMenu == true){
				game.setFogOfWar(true);
				thirdMenu = true;
			}
			//Normal game
			if((posX>x+180 && posX<x+330) && (posY>y+60 && posY<460) && secondMenu == true){
				game.setFogOfWar(false);
				thirdMenu = true;
			}
			//Easy
			if((posX>x+360 && posX<x+510) && (posY>y && posY<400) && thirdMenu == true){
				game.randomPlayers(2);
				start = true;
			}
			//Normal
			if((posX>x+360 && posX<x+510) && (posY>y+60 && posY<400+60) && thirdMenu == true){
				game.randomPlayers(3);
				start = true;
			}
			//Hard
			if((posX>x+360 && posX<x+510) && (posY>y+120 && posY<400+120) && thirdMenu == true){
				game.randomPlayers(4);
				start = true;
			}
		}
	}

}
