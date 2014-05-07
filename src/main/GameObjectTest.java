package main;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class GameObjectTest {
	private GameObject G;
	
	@Before
	public void method() {
		G = GameObject.getInstance();
		G.createPlayers(4);
	}
	
	@Test
	public void testConstruct() {
		assertEquals(G.getPlanets().size(), 6);
		assertEquals(G.getAIPlayers().size(), 3);
	}
	
	@Test
	public void testChangeOwner() {
		Player human = G.getHumanPlayer();
		G.getPlanets().get(0).setOwner(human);
		assertTrue(G.getPlayerPlanets(human).contains(G.getPlanets().get(0)));
		Player AIPlayer = G.getAIPlayers().get(1);
		G.getPlanets().get(0).setOwner(AIPlayer);
		assertEquals(AIPlayer, G.getPlanets().get(0).getOwner());
		assertEquals(2, G.getPlayerPlanets(AIPlayer).size());
		assertEquals(0, G.getPlayerPlanets(human).size());
	}
	
	@Test
	public void testPath() {
		assertArrayEquals(new int[]{0,1,5},G.path(G.getPlanets().get(0), G.getPlanets().get(5)));
		assertArrayEquals(new int[]{1,5},G.path(G.getPlanets().get(1), G.getPlanets().get(5)));
		assertArrayEquals(new int[]{2,3,4},G.path(G.getPlanets().get(2), G.getPlanets().get(4)));
	}
	
	@Test
	public void testNeighbourPlanet() {
		ArrayList<Planet> n = G.getNeighbourPlanets(G.getPlanets().get(0));
		assertTrue(n.contains(G.getPlanets().get(3)));
		assertTrue(n.contains(G.getPlanets().get(1)));
		
		n = G.getNeighbourPlanets(G.getPlanets().get(5));
		assertTrue(n.contains(G.getPlanets().get(4)));
		assertTrue(n.contains(G.getPlanets().get(1)));
	}
	
	@Test
	public void testEmptyTurn() {
		G.nextTurn();
	}
	
	@Test
	public void testSplitFleet(){
		Fleet oldFleet = G.getFleets().get(0);
		Fleet newFleet = G.splitFleet(oldFleet, 5);
		assertEquals(15, oldFleet.getSize());
		assertEquals(5, newFleet.getSize());
		assertEquals(newFleet.getPlanet(), oldFleet.getPlanet());
		assertEquals(5, G.getFleets().size());
	}
	
	@After
	public void tearDown() {
		G.destroy();
		System.gc();
	}

}