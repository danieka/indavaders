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
		assertEquals(G.getPlanets().size(), 20);
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
		assertArrayEquals(new int[]{0,1,2,3,4},G.path(G.getPlanets().get(0), G.getPlanets().get(4)));
		assertArrayEquals(new int[]{1,5},G.path(G.getPlanets().get(1), G.getPlanets().get(5)));
		assertArrayEquals(new int[]{10,6,7},G.path(G.getPlanets().get(10), G.getPlanets().get(7)));
	}
	
	@Test
	public void testNeighbourPlanet() {
		ArrayList<Planet> n = G.getNeighbourPlanets(G.getPlanets().get(0));
		assertTrue(n.contains(G.getPlanets().get(5)));
		assertTrue(n.contains(G.getPlanets().get(1)));
		
		n = G.getNeighbourPlanets(G.getPlanets().get(5));
		assertTrue(n.contains(G.getPlanets().get(6)));
		assertTrue(n.contains(G.getPlanets().get(1)));
		
		n = G.getNeighbourPlanets(G.getPlanets().get(13));
		assertTrue(n.contains(G.getPlanets().get(12)));
		assertTrue(n.contains(G.getPlanets().get(14)));
		assertTrue(n.contains(G.getPlanets().get(8)));
		assertTrue(n.contains(G.getPlanets().get(9)));
		assertTrue(n.contains(G.getPlanets().get(19)));
	}
	
	@Test
	public void testEmptyTurn() {
		G.nextTurn();
	}
	
	@Test
	public void testEliminatePlayers() {
		Player p = G.getAIPlayers().get(1);
		G.getPlayerPlanets(p).get(0).setOwner(G.getHumanPlayer());
		G.eliminatePlayer();
		assertFalse(G.getAIPlayers().contains(p));
	}
	
	@Test
	public void testSpawnFleets() {
		Planet p = G.getPlayerPlanets(G.getHumanPlayer()).get(0);
		G.spawnNewFleets();
		assertEquals(2, p.getFleets().size());
		assertEquals(5, p.getFleets().get(1).getSize());
		assertEquals(2, G.getPlayerFleets(G.getHumanPlayer()).size());
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
	
	//@Test
	public void testGame(){
		while(G.getAIPlayers().size() != 1){
			G.nextTurn();
		}
	}
	
	@Test
	public void testFight(){
		Fleet f = G.getPlayerFleets(G.getHumanPlayer()).get(0);
		Fleet e = G.getPlayerFleets(G.getAIPlayers().get(0)).get(0);
		f.setSize(40);
		G.addMove(new Move(f, G.getPlanets().get(1)));
		G.executeMoves();
		G.fight();
		assertEquals(1, G.getPlanets().get(1).getFleets().size());
		assertTrue(G.getFleets().contains(f));
		assertFalse(G.getFleets().contains(e));
	}
	
	@Test
	public void testEqualFight(){
		Fleet f = G.getPlayerFleets(G.getHumanPlayer()).get(0);
		Fleet e = G.getPlayerFleets(G.getAIPlayers().get(0)).get(0);
		G.addMove(new Move(f, G.getPlanets().get(1)));
		G.executeMoves();
		G.fight();
		assertEquals(1, G.getPlanets().get(1).getFleets().size());
		assertTrue(G.getFleets().contains(e));
		assertFalse(G.getFleets().contains(f));
	}
	
	@Test
	public void testThreewayFight(){
		Fleet f = G.getPlayerFleets(G.getHumanPlayer()).get(0);
		f.setSize(60);
		Fleet e = G.getPlayerFleets(G.getAIPlayers().get(0)).get(0);
		Fleet e2 = G.getPlayerFleets(G.getAIPlayers().get(1)).get(0);
		G.addMove(new Move(f, G.getPlanets().get(1)));
		G.addMove(new Move(e2, G.getPlanets().get(1)));
		G.executeMoves();
		assertEquals(3, G.getPlanets().get(1).getFleets().size());
		G.fight();
		assertEquals(1, G.getPlanets().get(1).getFleets().size());
		assertTrue(G.getFleets().contains(f));
		assertFalse(G.getFleets().contains(e));
		assertFalse(G.getFleets().contains(e2));
	}
	
	@Test
	public void testMerge2(){		
		Player player1 = G.getAIPlayers().get(1);		
		Player player2 = G.getAIPlayers().get(2);	
		Fleet f = G.getPlayerFleets(G.getAIPlayers().get(0));
		Fleet e = G.getPlayerFleets(G.getAIPlayers().get(0));
		
		
		
//		G.addMove(new Move(f, G.getPlanets().get(1)));
//		G.executeMoves();
		
		G.merge();
		assertEquals(1, G.getPlanets().get(1).getFleets().size());
		assertTrue(G.getFleets().contains(f));
		assertFalse(G.getFleets().contains(e));
	}
	
	@Test
	public void testMerge(){		
		G.spawnNewFleets();
		G.merge();
		G.getFleets();
		assertEquals(4, G.getFleets().size());		
		assertEquals(25, G.getFleets().get(0).getSize());
	}
	
	
	
	
	@After
	public void tearDown() {
		G.destroy();
		System.gc();
	}

}
