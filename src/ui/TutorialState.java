package ui;

import java.util.ArrayList;

import main.Fleet;
import main.GameObject;
import main.Planet;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.SlickException;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class TutorialState extends GameState {
	private int tutorialStep = 0;
	private String dialogText;
	private int dialogX;
	private int dialogY;
	private boolean displayDialog;
	
	TutorialState(){
		super();
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		super.init(container, arg1);
			
	}
	
	@Override
	public int getID() {
		return 3;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame sbg, int arg2)
			throws SlickException {
		super.update(container, sbg, arg2);
		switch (tutorialStep){
		case 0:
			dialogText = "Welcome to Space IN(DA)avaders. \n" +
					"Your goal is to conquer the galaxy and this tutorial intentds for you to become familiar with the mechanics of the game. \n" +
					"First off: Each player has a color and you are the blueish color that you can see.";
			dialogX = game.getPlayerPlanets(game.getHumanPlayer()).get(0).getX() + 40;
			dialogY = game.getPlayerPlanets(game.getHumanPlayer()).get(0).getX() + 40;
			displayDialog = true;
		}
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame arg1, Graphics g)
			throws SlickException {
		super.render(container, arg1, g);
		if(displayDialog){
			Rectangle dialog = new Rectangle(dialogX, dialogY, 300, 200);
			g.setColor(Color.black);
			g.fill(dialog);
			g.setColor(Color.white);
			g.drawString(dialogText, dialogX, dialogY);
			System.out.println("Yrintaoeu");
		}
	}
	
	@Override
	public void mousePressed(int button, int posX, int posY){
		super.mousePressed(button, posX, posY);
	}
}
