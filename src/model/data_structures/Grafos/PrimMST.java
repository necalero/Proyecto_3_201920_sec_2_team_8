package model.data_structures.Grafos;



import model.data_structures.IndexMinPQ;

public class PrimMST 
{
	private static final double FLOATING_POINT_EPSILON = 1E-12;

	private Arco[] edgeTo;        // edgeTo[v] = shortest edge from tree vertex to non-tree vertex
	private double[] distTo;      // distTo[v] = weight of shortest such edge
	private boolean[] marked;     // marked[v] = true if v on tree, false otherwise
	private IndexMinPQ<Double> pq;
	private String criterio;

	/**
	 * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
	 * @param G the edge-weighted graph
	 */
	public PrimMST(GrafoNoDirigido G, String pCriterio) {
		edgeTo = new Arco[G.V()];
		distTo = new double[G.V()];
		marked = new boolean[G.V()];
		criterio = pCriterio;
		pq = new IndexMinPQ<Double>(G.V());
		for (int v = 0; v < G.V(); v++)
		{
			System.out.println(v/G.V());
			distTo[v] = Double.POSITIVE_INFINITY;
		}
			

		for (int v = 0; v < G.V(); v++)      // run from each vertex to find
			if (!marked[v]) prim(G, v);      // minimum spanning forest

		
	}

	// run Prim's algorithm in graph G, starting from vertex s
	private void prim(GrafoNoDirigido G, int s) {
		distTo[s] = 0.0;
		pq.insert(s, distTo[s]);
		while (!pq.isEmpty()) {
			int v = pq.delMin();
			scan(G, v);
		}
	}

	// scan vertex v
	private void scan(GrafoNoDirigido G, int v) {
		marked[v] = true;
		for (int idVertices : G.adj(v)) 
		{
			Arco e = G.getVertex(v).buscarArcoA(idVertices);
			int w = e.other(v);
			if (marked[w]) continue;         // v-w is obsolete edge
			if(criterio.equals("distancia"))
			{
				if (e.darDistancia() < distTo[w]) {
					distTo[w] = e.darDistancia();
					edgeTo[w] = e;
					if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
					else                pq.insert(w, distTo[w]);
				}
			}
			else if (criterio.equals("tiempo"))
			{
				if (e.darTiempo() < distTo[w]) {
					distTo[w] = e.darTiempo();
					edgeTo[w] = e;
					if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
					else                pq.insert(w, distTo[w]);
				}
			}
			else if (criterio.equals("velocidad"))
			{
				if (e.darVelocidad() < distTo[w]) {
					distTo[w] = e.darVelocidad();
					edgeTo[w] = e;
					if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
					else                pq.insert(w, distTo[w]);
				}
			}
			
		}
	}

	/**
	 * Returns the edges in a minimum spanning tree (or forest).
	 * @return the edges in a minimum spanning tree (or forest) as
	 *    an iterable of edges
	 */
	public Iterable<Arco> edges() {
		model.data_structures.Grafos.Queue<Arco> mst = new model.data_structures.Grafos.Queue<>();
		for (int v = 0; v < edgeTo.length; v++) {
			Arco e = edgeTo[v];
			if (e != null) {
				mst.enqueue(e);
			}
		}
		return mst;
	}
	
	public GrafoNoDirigido arbolMST(GrafoNoDirigido total) {
		GrafoNoDirigido g = new GrafoNoDirigido<>();
		for (int v = 0; v < edgeTo.length; v++) 
		{
			System.out.println(edgeTo.length);
			Arco e = edgeTo[v];
			if (e != null)
			{
				g.addVertex(e.darIdOrigen(), total.getVertex(e.darIdOrigen()));
				g.addVertex(e.darIdDestino(), total.getVertex(e.darIdDestino()));
				g.addEdge(e.darIdOrigen(),e.darIdDestino(),e.darDistancia(), e.darTiempo());
			}
		}
		return g;
	}

	/**
	 * Returns the sum of the edge weights in a minimum spanning tree (or forest).
	 * @return the sum of the edge weights in a minimum spanning tree (or forest)
	 */
	public double weight() {
		double weight = 0.0;
		if(criterio.equals("distancia"))
		{
			for (Arco e : edges())
			{
				weight += e.darDistancia();
			}
				
		}
		else if(criterio.equals("tiempo"))
		{
			for (Arco e : edges())
			{
				weight += e.darTiempo();
			}
		}
		else if(criterio.equals("velocidad"))
		{
			for (Arco e : edges())
			{
				weight += e.darVelocidad();
			}
		}
		
		return weight;
	}


	// check optimality conditions (takes time proportional to E V lg* V)
	private boolean check(GrafoNoDirigido G) 
	{

		// check weight
		double totalWeight = 0.0;
		for (Arco e : edges()) {
			
			if(criterio.equals("distancia"))
			{
				totalWeight += e.darDistancia();
					
			}
			else if(criterio.equals("tiempo"))
			{
				totalWeight += e.darTiempo();
			}
			else if(criterio.equals("velocidad"))
			{
				totalWeight += e.darVelocidad();
			}
		}
		if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
			System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
			return false;
		}

		// check that it is acyclic
		UF uf = new UF(G.V());
		for (Arco e : edges()) {
			int v = e.darIdDestino(), w = e.other(v);
			if (uf.find(v) == uf.find(w)) {
				System.err.println("Not a forest");
				return false;
			}
			uf.union(v, w);
		}

		// check that it is a spanning forest
		for(Vertice v : G.darVertices())
		{
			if(v!=null)
			{
				for (Arco e : v.darArcosD()) {
					int b = e.darIdDestino(), w = e.other(b);
					if (uf.find(b) != uf.find(w)) {
						System.err.println("Not a spanning forest");
						return false;
					}
				}
			}
			
		}
		

		// check that it is a minimal spanning forest (cut optimality conditions)
		for (Arco e : edges()) 
		{

			// all edges in MST except e
			uf = new UF(G.V());
			for (Arco f : edges()) {
				int x = f.darIdDestino(), y = f.other(x);
				if (f != e) uf.union(x, y);
			}

			// check that e is min weight edge in crossing cut
			for(Vertice h : G.darVertices())
			{
				if(h!=null)
				{
					for (Arco f : h.darArcosD()) 
					{
						int x = f.darIdDestino(), y = f.other(x);
						if (uf.find(x) != uf.find(y)) 
						{
							if(criterio.equals("distancia"))
							{
								if (f.darDistancia() < e.darDistancia()) {
									System.err.println("Edge " + f + " violates cut optimality conditions");
									return false;
								}
							}
							else if(criterio.equals("tiempo"))
							{
								if (f.darTiempo() < e.darTiempo()) {
									System.err.println("Edge " + f + " violates cut optimality conditions");
									return false;
								}
							}
							else if(criterio.equals("velocidad"))
							{
								if (f.darVelocidad() < e.darVelocidad()) {
									System.err.println("Edge " + f + " violates cut optimality conditions");
									return false;
								}
							}
							
							
							
						}
					}
				}
			}
			

		}

		return true;
	}

	
}
