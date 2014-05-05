import static org.junit.Assert.*;

import org.junit.Test;


public class GameObjectTest {

	@Test
	public void testConstruct() {
		GameObject G = GameObject.getInstance();
		assertEquals(G.getPlanets().size(), 6);
		Player human = G.getHumanPlayer();
		G.getPlanets().get(0).setOwner(human);
		assertTrue(G.getPlayerPlanets(human).contains(G.getPlanets().get(0)));
	}

}
