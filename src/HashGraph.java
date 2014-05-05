import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A graph with a fixed number of vertices implemented using adjacency maps.
 * Space complexity is &Theta;(n + m) where n is the number of vertices and m
 * the number of edges.
 * 
 * @author Daniel Karlsson
 * @version 2014-04-15
 */
public class HashGraph implements Graph {
	/**
	 * The map edges[v] contains the key-value pair (w, c) if there is an edge
	 * from v to w; c is the cost assigned to this edge. The maps may be null
	 * and are allocated only when needed.
	 */
	private final Map<Integer, Integer>[] edges;
	private final static int INITIAL_MAP_SIZE = 4;

	/** Number of edges in the graph. */
	private int numEdges;

	/**
	 * Constructs a HashGraph with n vertices and no edges. Time complexity:
	 * O(n)
	 * 
	 * @throws IllegalArgumentException
	 *             if n < 0
	 */
	public HashGraph(int n) {
		if (n < 0)
			throw new IllegalArgumentException("n = " + n);

		// The array will contain only Map<Integer, Integer> instances created
		// in addEdge(). This is sufficient to ensure type safety.
		@SuppressWarnings("unchecked")
		Map<Integer, Integer>[] a = new HashMap[n];
		edges = a;
	}

	/**
	 * Add an edge without checking parameters.
	 */
	private void addEdge(int from, int to, int cost) {
		if (edges[from] == null)
			edges[from] = new HashMap<Integer, Integer>(INITIAL_MAP_SIZE);
		if (edges[from].put(to, cost) == null)
			numEdges++;
	}

	private void removeEdge(int from, int to) {
		if(edges[from] == null)
			return;
		if (edges[from].containsKey(to)){
			edges[from].remove(to);
			numEdges--;
		}
		return;
	}
	
	/**
	 * {@inheritDoc Graph} Time complexity: O(1).
	 */
	@Override
	public int numVertices() {
		return edges.length;
	}

	/**
	 * {@inheritDoc Graph} Time complexity: O(1).
	 */
	@Override
	public int numEdges() {
		return numEdges;
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public int degree(int v) throws IllegalArgumentException {
		checkVertexParameter(v);
		if (edges[v] != null)
			return edges[v].size();
		return 0;
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public VertexIterator neighbors(int v) {
		checkVertexParameter(v);
		return new NeighborIterator(v);
		
	}
	
	private class NeighborIterator implements VertexIterator {
		Iterator<Map.Entry<Integer, Integer>> iter = null;

		NeighborIterator(int v) {
			if (edges[v] != null){
				iter = edges[v].entrySet().iterator();
			}				
		}


		@Override
		public boolean hasNext() {
			if (iter == null){
				return false;
			}
			return iter.hasNext();
		}

		@Override
		public int next() {
			return iter.next().getKey();
		}
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public boolean hasEdge(int v, int w) {
		checkVertexParameters(v,w);
		if (edges[v] == null)
			return false;
		if (edges[v].containsKey(w))
			return true;
		return false;
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public int cost(int v, int w) throws IllegalArgumentException {
		checkVertexParameters(v,w);
		if (edges[v] == null)
			return NO_COST;
		if (edges[v].containsKey(w))
			return edges[v].get(w);
		return NO_COST;
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void add(int from, int to) {
		checkVertexParameters(from, to);
		addEdge(from, to, -1);
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void add(int from, int to, int c) {
		checkVertexParameters(from, to);
		addEdge(from, to, c);
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void addBi(int v, int w) {
		checkVertexParameters(v,w);
		addEdge(v, w, -1);
		addEdge(w, v, -1);
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void addBi(int v, int w, int c) {
		checkVertexParameters(v,w);
		addEdge(v, w, c);
		addEdge(w, v, c);
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void remove(int from, int to) {
		checkVertexParameters(from, to);
		removeEdge(from, to);
	}

	/**
	 * {@inheritDoc Graph}
	 */
	@Override
	public void removeBi(int v, int w) {
		checkVertexParameters(v, w);
		removeEdge(v, w);
		removeEdge(w, v);
	}

	/**
	 * Returns a string representation of this graph.
	 * 
	 * @return a String representation of this graph
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (int i = 0; i < edges.length; i++) {
			if (edges[i] == null)
				continue;

			for (Map.Entry<Integer, Integer> entry : edges[i].entrySet()){

				if (entry.getValue() == -1)
					sb.append("(" + i + "," + entry.getKey() + "), ");
				else
					sb.append("(" + i + "," + entry.getKey() + "," + entry.getValue() + "), ");
			}
		}
		if (numEdges > 0)
			sb.setLength(sb.length() - 2); // Remove trailing ", "
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * Checks a single vertex parameter v.
	 * 
	 * Blatantly stolen from Stefan Nilsson
	 * 
	 * @throws IllegalArgumentException
	 *             if v is out of range
	 */
	private void checkVertexParameter(int v) {
		if (v < 0 || v >= edges.length)
			throw new IllegalArgumentException("Out of range: v = " + v + ".");
	}

	/**
	 * Checks two vertex parameters v and w.
	 * 
	 * Blatantly stolen from Stefan Nilsson
	 * 
	 * @throws IllegalArgumentException
	 *             if v or w is out of range
	 */
	private void checkVertexParameters(int v, int w) {
		if (v < 0 || v >= edges.length || w < 0 || w >= edges.length)
			throw new IllegalArgumentException("Out of range: v = " + v
					+ ", w = " + w + ".");
	}
}