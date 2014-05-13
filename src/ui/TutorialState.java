package ui;

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
					"Your goal is to conquer the galaxy and this tutorial intends for you \n" +
					"to become familiar with the mechanics of the game. \n" +
					"First off: Each player has a color and you are the player with the blueish color\n" +
					"To select your fleet, left click on it.";
			dialogX = 50;			
			dialogY =  game.getPlayerPlanets(game.getHumanPlayer()).get(0).getY() + 300;
			if(dialogY > 500) dialogY = 50;
			
			
			if(selectedFleet != null){
				tutorialStep = 1;
			}
			displayDialog = true;
			break;
		case 1:
			dialogText = "Well done! \n" +
					"Now order it to a neighbouring planet by right-clicking on that planet.";
			if(selectedFleet == null){
				tutorialStep = 2;
			}
			break;
		case 2:
			dialogText = "Nice! \n" +
					"You've now commanded the fleet to move, and you can see it in transit. \n" +
					"But before the fleet moves we have to advance the game to the next turn. \n" +
					"To do this press the spacebar or the next turn button in the lower right corner. \n ";
			if(turns != 0){
				tutorialStep = 3;
			}
			break;
		case 3:
			dialogText = "Okay! \n" +
					"As you can see the fleet has now moved to the new planet. \n" +
					"Also the planet changed to your color, you have conquered it! \n" +
					"You're galactic empire has expanded but you might now have met your first enemies. \n" +
					"Ignore them for now, we will crush them when the time is right! \n" +
					"Now the next thing you need to know is how to divide fleets. \n" +
					"To divide a fleet: Hold down shift while left-clicking on it. \n" +
					"To select ten ships at a time: Hold down left control. \n";
			if(divFleet != null){
				tutorialStep = 4;
			}
			break;
		case 4:
			dialogText = "Spectacular! \n" +
					"Now rigth-click as usual to command the fleet.";
			if(divFleet == null){
				tutorialStep = 5;
			}
			break;
		case 5:
			dialogText = "Stupendous! \n" +
					"As you can see there are many planets that you need to conquer. \n" +
					"That's why you need to split your fleet to conquer all of them as rapidly as possible \n" +
					"Send fleets to all white, unoccupied planets and when you have done that go to the next turn. \n" +
					"For now it is wise to avoid the enemy.";
			if(turns != 1){
				tutorialStep = 6;
			}
			break;
		case 6:
			dialogText = "Great! \n" +
					"Now, let's focus on combat! \n" +
					"In order to conquer the enemy planets you need to defeat their fleets and lay seige to their planets. \n" +
					"To do that you order fleets as normal, but you need to make sure that you have a superior force. \n" +
					"When you lay siege to a planet it takes one turn for it to change to your planet.\n" +
					"Well, that's about all that I have to say. Good luck! \n" +
					"You can continue this game and this dialog will disappear on the next turn. \n" +
					"Or you can exit to the menu and start a new game.";
					if(turns != 2){
						tutorialStep = 7;
					}
					break;
		case 7:
			displayDialog = false;
		}
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame arg1, Graphics g)
			throws SlickException {
		super.render(container, arg1, g);
		if(displayDialog){
			Rectangle dialog = new Rectangle(dialogX, dialogY, 900, 200);
			g.setColor(Color.white);
			g.fill(dialog);
			g.setColor(Color.black);
			g.drawString(dialogText, dialogX, dialogY);
		}
	}
	
	@Override
	public void mousePressed(int button, int posX, int posY){
		super.mousePressed(button, posX, posY);
	}
}
