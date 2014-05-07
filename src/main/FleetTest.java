package main;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FleetTest {

	GameObject G;
	Fleet f;

	@Before
	public void method() {
		G = GameObject.getInstance();
		G.createPlayers(4);
		f = G.getFleets().get(0);
	}
	
	@Test
	public void testMoveTo() {
		Planet prev = f.getPlanet();
		Planet p = G.getPlanets().get(5);
		f.moveTo(p);
		G.executeMoves();
		assertEquals(p, f.getPlanet());
		assertTrue(p.getFleets().contains(f));
		assertFalse(prev.getFleets().contains(f));
	}
	
	@Test
	public void testMoveTowards() {
		Planet prev = f.getPlanet();
		Planet p = G.getPlanets().get(5);
		f.moveTo(G.getPlanets().get(15));
		G.executeMoves();
		assertEquals(p, f.getPlanet());
		assertTrue(p.getFleets().contains(f));
		assertFalse(prev.getFleets().contains(f));
	}
	
	@After
	public void tearDown() {
		G.destroy();
		System.gc();
	}
}
