package main;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
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

public class GameState extends BasicGameState{
	private ArrayList<Circle> gamePlanets;
	private GameObject game;
	Animation idleShip, selectedShip, movingShip;
	int[] duration = {500, 500, 500, 500};
	Image starShip;
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
		game.randomPlayers(4);
		starShip = new Image("resources/starship.gif");
		space = new Image("resources/spaceBG.png");
		planetImg = new Image("resources/planet.png");
		nextTurn = new Image("resources/nextTurn.png");
		Image[] shipIdle = {new Image("resources/starship1.png"), new Image("resources/starship1.png"),
		new Image("resources/starship1.png"), new Image("resources/starship1.png")};
		Image[] selectingShip = {new Image("resources/starship1.png"), new Image("resources/starship2.png"),
		new Image("resources/starship3.png"), new Image("resources/starship4.png")};
		Image[] movedShip = {new Image("resources/starshipMove1.png"), new Image("resources/starshipMove2.png"),
		new Image("resources/starship3.png"), new Image("resources/starship4.png")};
		selectedShip = new Animation(selectingShip, duration, false);
		idleShip = new Animation(shipIdle, duration, false);
		movingShip = new Animation(movedShip, duration, false);
		gamePlanets = new ArrayList<Circle>();
		
		for(Planet p: game.getPlanets()){
			int x = p.getX();
			int y = p.getY();
			gamePlanets.add(new Circle(x, y, 15));
		}
			
	}

	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		if(container.getInput().isKeyPressed(Input.KEY_2)){
			sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
		//int posX = Mouse.getX();
		//int posY = 768 - Mouse.getY();
		
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
		g.drawString("X: " + posX + " Y: "+ posY, 600, 50);
		g.setLineWidth(1);
		g.setColor(Color.cyan);
		for(int[] l : game.getAllEdges()){
			int x1 = l[0];
			int y1 = l[1];
			int x2 = l[2];
			int y2 = l[3];
			g.drawLine(x1, y1, x2, y2);
		}
		for(int i = 0; i < game.getPlanets().size(); i++){
			Circle c = gamePlanets.get(i);
			Planet p = game.getPlanets().get(i);
			if(p.getOwner() != null){
				g.setColor(p.getOwner().getColor());
				g.fill(c);
			}else{
				g.setColor(Color.white);
				g.fill(c);
			}
		}
		for(Planet p: game.getPlanets()){
			if(!p.getFleets().isEmpty()){
				int x = p.getX();
				int y = p.getY();
				//starShip.draw(x, y);
				selectedShip.draw(x, y);
				int z = 0;
				for(int n=0; n<p.getFleets().size(); n++){
					z += p.getFleets().get(n).getSize();
				}
				g.setColor(Color.white);
				g.drawString("[" +z+ "]", x+30, y+10);
			}
		}
		
		g.setColor(Color.white);
		if(toolTip != null){
			g.drawString(toolTip, posX + 15, posY);
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
			for(Planet p: game.getPlanets()){
				if(!p.getFleets().isEmpty()){
					int x = p.getX();
					int y = p.getY();
					if((posX>x && posX<x+32) && (posY>y && posY<y+32)){
						Fleet fleet = p.getFleets().get(0);
						Mouse.getEventButtonState();
						if(Mouse.isButtonDown(0)){
							selectedFleet = fleet;
							//idleShip = selectedShip;
						}
					}
				}
			}
		}
		
		if (button == 0 && shiftPressed){
			for(Planet p: game.getPlanets()){
				if(!p.getFleets().isEmpty()){
					int x = p.getX();
					int y = p.getY();
					if((posX>x && posX<x+32) && (posY>y && posY<y+32)){
						Fleet fleet = p.getFleets().get(0);
						if(divFleet == fleet || divFleet == null){
							divFleet = fleet;
							if(0 < fleet.getSize()){
								fleet.setSize(fleet.getSize() - 1);
								divFleetNumber += 1;
								toolTip = divFleetNumber + "";
							}
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
						selectedShip = movingShip;
					}
				}
			}
			
		}
	}
	
}
