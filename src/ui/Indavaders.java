package ui;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The main class. This will create the different states from which you can play the game.
 * @author Adam Pielbusch
 *
 */

public class Indavaders extends StateBasedGame{
	
	public static int siffra;
	public static AppGameContainer appgc;
	
	public Indavaders(String gamename)
	{
		super(gamename);
	}


	public static void main(String[] args)
	{
		try
		{
			appgc = new AppGameContainer(new Indavaders("Indavaders"));
			appgc.setDisplayMode(1024, 768, false);
			appgc.setTargetFrameRate(60);
			appgc.setShowFPS(false);
			appgc.start();
			
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Indavaders.class.getName()).log(Level.SEVERE, null, ex);
		}
	}


	public void initStatesList(GameContainer arg0) throws SlickException {
		//The different states
		this.addState(new StartState());
		this.addState(new GameState());		
		this.addState(new EndState());
		this.addState(new TutorialState()); 
		
		
		
	}
	
	public static void exit(){
		appgc.exit();
	}
}
