import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class GameObjectTest {
	private GameObject G;
	
	@Before
	public void method() {
		GameObject G = GameObject.getInstance();
	}
	
	@Test
	public void testConstruct() {	
		assertEquals(G.getPlanets().size(), 6);
		Player human = G.getHumanPlayer();
		G.getPlanets().get(0).setOwner(human);
		assertTrue(G.getPlayerPlanets(human).contains(G.getPlanets().get(0)));
	}
	
	@Test
	public void testPath() {
		assertArrayEquals(new int[]{0,1,5},G.path(0, 5));
	}

}
