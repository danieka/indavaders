package main;
import static org.junit.Assert.*;

import java.util.ArrayList;

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
		G.getPlanet(0).setOwner(human);
		assertTrue(G.getPlayerPlanets(human).contains(G.getPlanet(0)));
		Player AIPlayer = G.getAIPlayers().get(1);
		G.getPlanet(0).setOwner(AIPlayer);
		assertEquals(AIPlayer, G.getPlanet(0).getOwner());
		assertEquals(2, G.getPlayerPlanets(AIPlayer).size());
		assertEquals(0, G.getPlayerPlanets(human).size());
	}
	
	@Test
	public void testPath() {
		assertArrayEquals(new int[]{0,1,2,3,4},G.path(G.getPlanet(0), G.getPlanet(4)));
		assertArrayEquals(new int[]{1,5},G.path(G.getPlanet(1), G.getPlanet(5)));
		assertArrayEquals(new int[]{10,6,7},G.path(G.getPlanet(10), G.getPlanet(7)));
	}
	
	@Test
	public void testNeighbourPlanet() {
		ArrayList<Planet> n = G.getNeighbourPlanets(G.getPlanet(0));
		assertTrue(n.contains(G.getPlanet(5)));
		assertTrue(n.contains(G.getPlanet(1)));
		
		n = G.getNeighbourPlanets(G.getPlanet(5));
		assertTrue(n.contains(G.getPlanet(6)));
		assertTrue(n.contains(G.getPlanet(1)));
		
		n = G.getNeighbourPlanets(G.getPlanet(13));
		assertTrue(n.contains(G.getPlanet(12)));
		assertTrue(n.contains(G.getPlanet(14)));
		assertTrue(n.contains(G.getPlanet(8)));
		assertTrue(n.contains(G.getPlanet(9)));
		assertTrue(n.contains(G.getPlanet(19)));
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
		assertEquals(10, p.getFleets().get(1).getSize());
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
	
	@Test
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
		G.addMove(new Move(f, G.getPlanet(1)));
		G.executeMoves();
		G.fight();
		assertEquals(1, G.getPlanet(1).getFleets().size());
		assertTrue(G.getFleets().contains(f));
		assertFalse(G.getFleets().contains(e));
	}
	
	@Test
	public void testEqualFight(){
		Fleet f = G.getPlayerFleets(G.getHumanPlayer()).get(0);
		Fleet e = G.getPlayerFleets(G.getAIPlayers().get(0)).get(0);
		G.addMove(new Move(f, G.getPlanet(1)));
		G.executeMoves();
		G.fight();
		assertEquals(1, G.getPlanet(1).getFleets().size());
		assertTrue(G.getFleets().contains(e));
		assertFalse(G.getFleets().contains(f));
	}
	
	@Test
	public void testThreewayFight(){
		Fleet f = G.getPlayerFleets(G.getHumanPlayer()).get(0);
		f.setSize(60);
		Fleet e = G.getPlayerFleets(G.getAIPlayers().get(0)).get(0);
		Fleet e2 = G.getPlayerFleets(G.getAIPlayers().get(1)).get(0);
		G.addMove(new Move(f, G.getPlanet(1)));
		G.addMove(new Move(e2, G.getPlanet(1)));
		G.executeMoves();
		assertEquals(3, G.getPlanet(1).getFleets().size());
		G.fight();
		assertEquals(1, G.getPlanet(1).getFleets().size());
		assertTrue(G.getFleets().contains(f));
		assertFalse(G.getFleets().contains(e));
		assertFalse(G.getFleets().contains(e2));
	}
	
	@Test
	public void testMerge2(){		
		Player player1 = G.getAIPlayers().get(1);		
		Player player2 = G.getAIPlayers().get(2);
		Planet p = G.getPlanet(15);
		Fleet f1 = G.createFleet(player1, p, 20);
		Fleet f2 = G.createFleet(player2, p, 20);
		Fleet f3 = G.createFleet(player1, p, 20);
		Fleet f4 = G.createFleet(player2, p, 20);
		G.merge();
		assertEquals(2, p.getFleets().size());
		assertTrue(p.getFleets().contains(f1));
		assertTrue(p.getFleets().contains(f2));
	}
	
	@Test
	public void testMerge(){		
		G.spawnNewFleets();
		G.merge();
		G.getFleets();
		assertEquals(4, G.getFleets().size());		
		assertEquals(25, G.getFleets().get(1).getSize());
	}
	

@Test
    public void testWin(){
        Player human = G.getHumanPlayer();
        G.getPlanet(0).setOwner(human);
        assertTrue(G.getPlayerPlanets(human).contains(G.getPlanet(0)));
        Player AIPlayer = G.getAIPlayers().get(0);
        G.getPlanet(1).setOwner(AIPlayer);
        assertEquals(AIPlayer, G.getPlanet(1).getOwner());
        G.win();
        assertEquals(false, G.win());
        G.getPlanet(1).setOwner(human);;  
        assertEquals(0, G.getPlayerPlanets(AIPlayer).size());
        assertEquals(2, G.getPlayerPlanets(human).size());
        G.win();
        assertEquals(true, G.win()); 
        
    }
    
    @Test
    public void testLose(){
        Player human = G.getHumanPlayer();
        G.getPlanet(0).setOwner(human);
        assertTrue(G.getPlayerPlanets(human).contains(G.getPlanet(0)));
        Player AIPlayer = G.getAIPlayers().get(0);
        G.getPlanet(1).setOwner(AIPlayer);
        assertEquals(AIPlayer, G.getPlanet(1).getOwner());
        G.lose();
        assertEquals(false, G.lose());
        G.getPlanet(0).setOwner(AIPlayer);    
        assertEquals(2, G.getPlayerPlanets(AIPlayer).size());
        assertEquals(0, G.getPlayerPlanets(human).size());
        G.lose();
        assertEquals(true, G.lose());       
    }

	
	@Test(expected=IllegalArgumentException.class)
	public void testAddDuplicateMove(){
		G.addMove(new Move(G.getFleets().get(0), G.getPlanet(0), G.getPlanet(1)));
		G.addMove(new Move(G.getFleets().get(0), G.getPlanet(0), G.getPlanet(5)));
	}
	
	
	@After
	public void tearDown() {
		G.destroy();
		System.gc();
	}

}
