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
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameState extends BasicGameState{
	//private ArrayList<Line> paths;
	//private static int turn;
	private GameObject game;
	Image space;
	Image planetImg;
	Image nextTurn;
	private Fleet selectedFleet;
	private boolean shiftPressed;
	private int divFleetNumber = 0;
	private Fleet divFleet;
	private String toolTip;

	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		game = GameObject.getInstance();
		game.createPlayers(4);
		space = new Image("resources/spaceBG.png");
		planetImg = new Image("resources/planet.png");
		nextTurn = new Image("resources/nextTurn.png");
		ImageCache.getInstance();
		//turn = 0;
			
	}

	public void update(GameContainer container, StateBasedGame sbg, int arg2)
			throws SlickException {
		if(container.getInput().isKeyPressed(Input.KEY_2)){ // || game.win() == true){
			sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
		
		if(container.getInput().isKeyPressed(Input.KEY_SPACE)){
				game.nextTurn();		
		}
		if(container.getInput().isKeyDown(Input.KEY_LSHIFT)){
			shiftPressed = true;
		} else {
			shiftPressed = false;
		}		
	}
	
	public void render(GameContainer container, StateBasedGame arg1, Graphics g)
			throws SlickException {
		space.draw(0, 0);
		planetImg.draw(150, 150);
		nextTurn.draw(900, 650);
		int posX = Mouse.getX();
		int posY = 768 - Mouse.getY();
		String win = "False";
		if(game.win() == true){
			win = "True";
		}
		String lose = "False";
		if(game.lose() == true){
			lose = "True";
		}
		g.drawString("X: " + posX + " Y: "+ posY, 550, 15);
		g.drawString("Win=" + win, 550, 30);
		g.drawString("Lose=" + lose, 550, 45);
		g.setLineWidth(1);
		g.setColor(Color.cyan);
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
			for(Planet p : game.getNeighbourPlanets(selectedFleet.getPlanet())){
				if((posX>p.getX()-7 && posX<p.getX()+7) && (posY>p.getY()-7 && posY<p.getY()+7)){
					g.setColor(Color.blue);
					g.drawLine(selectedFleet.getX()+15, selectedFleet.getY()+15, p.getX(), p.getY());
				}
			}
		}
		if(divFleet != null){
			for(Planet p : game.getNeighbourPlanets(divFleet.getPlanet())){
				if((posX>p.getX()-7 && posX<p.getX()+7) && (posY>p.getY()-7 && posY<p.getY()+7)){
					g.setColor(Color.blue);
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
		if (button == 0 && !shiftPressed){
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
		
		if (button == 1){
			if(divFleet != null){
				Planet p = divFleet.getPlanet();
				ArrayList<Planet> planetList = game.getNeighbourPlanets(p);
				for(Planet plan: planetList){
					int x = plan.getX();
					int y = plan.getY();
					if((posX>x-7 && posX<x+7) && (posY>y-7 && posY<y+7)){						
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
				ArrayList<Planet> planetList = game.getNeighbourPlanets(p);
				for(Planet plan: planetList){
					int x = plan.getX();
					int y = plan.getY();
					if((posX>x-7 && posX<x+7) && (posY>y-7 && posY<y+7)){
						selectedFleet.moveTo(plan);
						selectedFleet = null;
					}
				}
			}
			
		}
	}
	
}
