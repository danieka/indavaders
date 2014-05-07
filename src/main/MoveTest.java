package main;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MoveTest {
	GameObject G;

	@Before
	public void method() {
		G = GameObject.getInstance();
		G.createPlayers(4);
	}
	
	@Test
	public void testMove() {
		Move m = new Move(G.getFleets().get(0), G.getPlanets().get(5), G.getFleets().get(0).getPlanet());
		m.execute();
		assertEquals(G.getPlanets().get(5), G.getFleets().get(0).getPlanet());
		assertEquals(1, G.getPlanets().get(5).getFleets().size());
	}
	
	@Test
	public void testNewMove() {
		Fleet f = G.getFleets().get(0);
		Planet p = G.getPlanets().get(5);
		Move m = new Move(f, p);
		m.execute();
		assertEquals(p, f.getPlanet());
		assertTrue(p.getFleets().contains(f));
	}
	
	@After
	public void tearDown() {
		G.destroy();
		System.gc();
	}

}
