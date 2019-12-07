package model.data_structures.Grafos;

import java.util.Stack;

import model.data_structures.IndexMinPQ;

public class DijkstraSP {

	private Arco[] edgeTo;
	private double[] distTo;
	private IndexMinPQ<Double> pq; 
	private String tipoPeso;



	public DijkstraSP(GrafoNoDirigido G, int s, String pTipoPeso)
	{      
		tipoPeso = pTipoPeso;
		edgeTo = new Arco[G.V()];  
		distTo = new double[G.V()]; 
		pq = new IndexMinPQ<Double>(G.V()); 
		for (int v = 0; v < G.V(); v++)         
			distTo[v] = Double.POSITIVE_INFINITY; 
		distTo[s] = 0.0;     
		pq.insert(s, 0.0); 
		while (!pq.isEmpty())         
			relax(G, pq.delMin());
	}   
	private void relax(GrafoNoDirigido G, int idVerticeOrigen)   
	{      
		for(int idVerticeDestino : G.adj(idVerticeOrigen))
		{         
			Vertice vDestActual = G.getVertex(idVerticeDestino);
			Arco w = G.getVertex(idVerticeOrigen).buscarArcoA(idVerticeDestino);

			if(tipoPeso.equals("tiempo"))
			{
				if (distTo[idVerticeDestino] > distTo[idVerticeOrigen] + w.darTiempo()) 
				{            
					distTo[idVerticeDestino] = distTo[idVerticeOrigen] + w.darTiempo();
					edgeTo[idVerticeDestino] = w;       
					if (pq.contains(idVerticeDestino)) pq.change(idVerticeDestino, distTo[idVerticeDestino]); 
					else                pq.insert(idVerticeDestino, distTo[idVerticeDestino]);     
				} 
			}
			else if(tipoPeso.equals("distancia"))
			{
				if (distTo[idVerticeDestino] > distTo[idVerticeOrigen] + w.darDistancia()) 
				{            
					distTo[idVerticeDestino] = distTo[idVerticeOrigen] + w.darDistancia();
					edgeTo[idVerticeDestino] = w;       
					if (pq.contains(idVerticeDestino)) pq.change(idVerticeDestino, distTo[idVerticeDestino]); 
					else                pq.insert(idVerticeDestino, distTo[idVerticeDestino]);     
				} 
			}
			else if(tipoPeso.equals("velocidad"))
			{
				if (distTo[idVerticeDestino] > distTo[idVerticeOrigen] + w.darVelocidad()) 
				{            
					distTo[idVerticeDestino] = distTo[idVerticeOrigen] + w.darVelocidad();
					edgeTo[idVerticeDestino] = w;       
					if (pq.contains(idVerticeDestino)) pq.change(idVerticeDestino, distTo[idVerticeDestino]); 
					else                pq.insert(idVerticeDestino, distTo[idVerticeDestino]);     
				} 
			}

		}
	}   


	public double distTo(int idVertice) 
	{   
		return distTo[idVertice];   
	} 
	public boolean hasPathTo(int idVertice) 
	{   
		return distTo[idVertice] < Double.POSITIVE_INFINITY;  
	} 

	public Arco buscarArcoA(int pIDV)
	{
		boolean seEncontro = false;
		Arco aRetornar = null;
		for(int i = 0; i < edgeTo.length&&!seEncontro; i++)
		{
			if(edgeTo[i].darIdDestino()==pIDV)
			{
				aRetornar = edgeTo[i];
			}
		}
		return aRetornar;
	}

	public GrafoNoDirigido pathTo(int idVertice, GrafoNoDirigido g)
	{   
		GrafoNoDirigido camino =null;
		if (!hasPathTo(idVertice)) return camino;
		else
		{
			camino = new GrafoNoDirigido<>();
		}
		 
		for (Arco e = buscarArcoA(idVertice); e != null; e = buscarArcoA(e.darIdOrigen())) 
		{
			Vertice x = g.getVertex(e.darIdDestino());
			Vertice y = g.getVertex(e.darIdOrigen());
			camino.addVertex(x.darId(), x);
			camino.addVertex(y.darId(), y);
			camino.addEdge(y.darId(), x.darId(), e.darDistancia(), e.darTiempo());
			
		}
			
		return camino; 
	}

}
