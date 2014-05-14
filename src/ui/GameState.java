package ui;
import java.util.ArrayList;

import main.Fleet;
import main.GameObject;
import main.Planet;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * The gamestate is the game. From here you'll be able to play the game. 
 * When entered a new GameObject is created.
 * @author Adam Pielbusch
 *
 */
public class GameState extends BasicGameState{
	//private ArrayList<Line> paths;
	protected GameObject game;
	Image space; //Background
	Image planetImg;
	Image nextTurn;
	protected Fleet selectedFleet = null;
	private boolean shiftPressed;
	private boolean ctrlPressed;
	private int divFleetNumber = 0;
	protected Fleet divFleet;
	private String toolTip;
	protected int turns;

	public void init(GameContainer container,StateBasedGame arg1)
			throws SlickException {
		game = GameObject.getInstance();
		//game.randomPlayers(amountOfPlayers);
		space = new Image("resources/spaceBG.png");
		planetImg = new Image("resources/planet.png");
		nextTurn = new Image("resources/nextTurn.png");
		ImageCache.getInstance();
			
	}

	public void update(GameContainer container, StateBasedGame sbg, int arg2)
			throws SlickException {
		//Take you to the endstate
		if(container.getInput().isKeyPressed(Input.KEY_2) || game.win() == true || game.lose() == true){ 
			sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
		//Simulates a turn when you push the button
		if(container.getInput().isKeyPressed(Input.KEY_SPACE)){
				turns++;
				game.nextTurn();		
		}
		
		if(container.getInput().isKeyDown(Input.KEY_LSHIFT)){
			shiftPressed = true;
		} else {
			shiftPressed = false;
		}
		
		if(container.getInput().isKeyDown(Input.KEY_LCONTROL)){
			ctrlPressed = true;
		} else {
			ctrlPressed = false;
		}
	}
	
	public void render(GameContainer container, StateBasedGame arg1, Graphics g)
			throws SlickException {
		space.draw(0, 0);
		planetImg.draw(150, 150);
		nextTurn.draw(900, 650);
		int posX = Mouse.getX();
		int posY = 768 - Mouse.getY();
		g.setColor(Color.white);
		//g.drawString("X: " + posX + " Y: "+ posY, 550, 15);
		g.setLineWidth(1);
		//g.setColor(Color.cyan);
		for(int[] l : game.getAllEdges()){
			int x1 = l[0];
			int y1 = l[1];
			int x2 = l[2];
			int y2 = l[3];
			g.drawLine(x1, y1, x2, y2);
		}

		for(Drawable d : game.getDrawable()){
			d.draw(g);
		}
		
		g.setColor(Color.white);
		if(toolTip != null){
			g.drawString(toolTip, posX + 15, posY);
		}
		
		if(selectedFleet != null){
			g.setLineWidth(2);
			g.setColor(game.getHumanPlayer().getColor());
			g.draw(new Circle(selectedFleet.getX()+14, selectedFleet.getY()+18, 20));
			for(Planet p : game.getNeighborPlanets(selectedFleet.getPlanet())){
				if((posX>p.getX()-14 && posX<p.getX()+14) && (posY>p.getY()-14 && posY<p.getY()+14)){
					
					g.drawLine(selectedFleet.getX()+15, selectedFleet.getY()+15, p.getX(), p.getY());
				}
			}
		}
		if(divFleet != null){
			for(Planet p : game.getNeighborPlanets(divFleet.getPlanet())){
				if((posX>p.getX()-14 && posX<p.getX()+14) && (posY>p.getY()-14 && posY<p.getY()+14)){
					g.setColor(game.getHumanPlayer().getColor());
					g.drawLine(divFleet.getX()+15, divFleet.getY()+15, p.getX(), p.getY());
				}
			}
		}
		
	}

	public int getID() {
		return 1;
	}
	@Override
	public void mousePressed(int button, int posX, int posY){
		if (button == 0 && !shiftPressed && !ctrlPressed){
			if(divFleet != null){
				divFleet.setSize(divFleet.getSize() + divFleetNumber);
				divFleetNumber = 0;
				divFleet = null;
				toolTip = null;
			}


			if((posX>900 && posX<995) && (posY<746 && posY>654)){
				if(Mouse.isButtonDown(0)){
					game.nextTurn();

				}
			}
			for(Fleet f: game.getPlayerFleets(game.getHumanPlayer())){
				int x = f.getX();
				int y = f.getY();
				if((posX>x && posX<x+32) && (posY>y && posY<y+32)){
					selectedFleet = f;
				}
			}
		}

		if (button == 0 && shiftPressed){
			selectedFleet = null;
			for(Fleet f: game.getPlayerFleets(game.getHumanPlayer())){
				int x = f.getX();
				int y = f.getY();
				if((posX>x && posX<x+32) && (posY>y && posY<y+32)){
					if(divFleet == f || divFleet == null){
						divFleet = f;
						if(1 < f.getSize()){
							f.setSize(f.getSize() - 1);
							divFleetNumber += 1;
							toolTip = divFleetNumber + "";
						}
					}
				}	
			}
		}
		
		if (button == 0 && ctrlPressed){
			selectedFleet = null;
			for(Fleet f: game.getPlayerFleets(game.getHumanPlayer())){
				int x = f.getX();
				int y = f.getY();
				if((posX>x && posX<x+32) && (posY>y && posY<y+32)){
					if(divFleet == f || divFleet == null){
						divFleet = f;
						if(1 < f.getSize() - 10){
							f.setSize(f.getSize() - 10);
							divFleetNumber += 10;
							toolTip = divFleetNumber + "";
						}
					}
				}	
			}
		}
		
		if (button == 1){
			if(divFleet != null){
				Planet p = divFleet.getPlanet();
				ArrayList<Planet> planetList = game.getNeighborPlanets(p);
				for(Planet plan: planetList){
					int x = plan.getX();
					int y = plan.getY();
					if((posX>x-14 && posX<x+14) && (posY>y-14 && posY<y+14)){						
						Fleet f = game.createFleet(game.getHumanPlayer(), divFleet.getPlanet(), divFleetNumber);
						divFleet = null;
						divFleetNumber = 0;
						toolTip = null;
						f.moveTo(plan);					
					}
				}	
			}
			
			if(selectedFleet != null){
				Planet p = selectedFleet.getPlanet();
				ArrayList<Planet> planetList = game.getNeighborPlanets(p);
				for(Planet plan: planetList){
					int x = plan.getX();
					int y = plan.getY();
					if((posX>x-14 && posX<x+14) && (posY>y-14 && posY<y+14)){
						selectedFleet.moveTo(plan);
						selectedFleet = null;
					}
				}
			}
			
		}
	}
	
}
