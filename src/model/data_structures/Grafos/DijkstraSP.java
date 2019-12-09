package model.data_structures.Grafos;

import java.util.Stack;

import model.data_structures.IndexMinPQ;
	
public class DijkstraSP
{

	private Arco[] edgeTo;
	private double[] distTo;
	private IndexMinPQ<Double> pq; 
	private String tipoPeso;



	public DijkstraSP(GrafoNoDirigido G, int s, String pTipoPeso)
	{      
		tipoPeso = pTipoPeso;
		edgeTo = new Arco[G.V()+1];  
		distTo = new double[G.V()+1]; 
		pq = new IndexMinPQ<Double>(G.V()); 
		
		
		for (int v = 0; v < G.V()+1; v++)         
		{
			distTo[v] = Double.POSITIVE_INFINITY; 
			edgeTo[v] = null;
		}
		
		distTo[s] = 0.0;  
		
		pq.insert(s, distTo[s]); 
		while (!pq.isEmpty())   
		{
			int v = pq.delMin();	
			for(Arco e : G.getVertex(v).darArcosD())
			{
				relax(e, G);
			}
		}
			
	}  	
	
	
	private void relax(Arco e, GrafoNoDirigido g) {
		
        int v = e.darIdOrigen(), w = e.darIdDestino();
        if(tipoPeso.equals("distancia"))
        {
        	
        	if (distTo[w] > distTo[v] + e.darDistancia()) 
        	{
                distTo[w] = distTo[v] + e.darDistancia();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }
        else if(tipoPeso.equals("tiempo"))
        {
        	if (distTo[w] > distTo[v] + e.darTiempo()) {
                distTo[w] = distTo[v] + e.darTiempo();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }
        else if(tipoPeso.equals("velocidad"))
        {
        	if (distTo[w] > distTo[v] + e.darVelocidad()) {
                distTo[w] = distTo[v] + e.darVelocidad();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }
        else if(tipoPeso.equals("tiempoEspecial"))
        {
        	v = g.getVertex(e.darIdOrigen()).darMOVEMENT_ID();
        	w = g.getVertex(e.darIdDestino()).darMOVEMENT_ID();
        	if (distTo[w] > distTo[v] + e.darTiempo()) {
                distTo[w] = distTo[v] + e.darTiempo();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
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
			if(edgeTo[i]!=null)
			{
				if(edgeTo[i].darIdDestino()==pIDV)
				{
					aRetornar = edgeTo[i];
				}
			}
			
		}
		return aRetornar;
	}

	public GrafoNoDirigido grafoDistanciaMinima(int idVertice, GrafoNoDirigido g)
	{   
		GrafoNoDirigido camino =null;
		if (!hasPathTo(idVertice)) return camino;
		else
		{
			camino = new GrafoNoDirigido<>();
		}
		 
		for(Arco e = edgeTo[idVertice]; e != null; e = edgeTo[e.darIdOrigen()]) 
		{
			Vertice x = g.getVertex(e.darIdDestino());
			Vertice y = g.getVertex(e.darIdOrigen());
			camino.addVertex(x.darId(), x);
			camino.addVertex(y.darId(), y);
			camino.addEdge(y.darId(), x.darId(), e.darDistancia(), e.darTiempo());
			
		}
			
		return camino; 
	}
	
	@SuppressWarnings("unchecked")
	public GrafoNoDirigido grafoTiempoEspecialMinimo(int idVertice, GrafoNoDirigido g, GrafoNoDirigido todo)
	{   
		GrafoNoDirigido camino =null;
		if (!hasPathTo(idVertice)) return camino;
		else
		{
			camino = new GrafoNoDirigido<>();
		}
		Stack stack = new Stack();
		for(Arco e = edgeTo[idVertice]; e != null; e = edgeTo[e.darIdOrigen()]) 
		{
			
			if(!(stack.contains(todo.getVertex(e.darIdDestino()).darMOVEMENT_ID())||stack.contains(todo.getVertex(e.darIdOrigen()).darMOVEMENT_ID())))
			{
				Integer c = todo.getVertex(e.darIdDestino()).darMOVEMENT_ID();
				stack.push(c);
				Integer d =todo.getVertex(e.darIdOrigen()).darMOVEMENT_ID();
				stack.push(d);
				Vertice x = g.getVertex(todo.getVertex(e.darIdDestino()).darMOVEMENT_ID());
				Vertice y = g.getVertex(todo.getVertex(e.darIdOrigen()).darMOVEMENT_ID());
				
				camino.addVertex(c, x);
				camino.addVertex(d, y);
				camino.addEdge(y.darId(), x.darId(), e.darDistancia(), e.darTiempo());
				
			}
			
			
		}
			
		return camino; 
	}
	public Iterable<Arco> pathTo(int idVertice, GrafoNoDirigido g)
	{   
		Stack camino = null;
		if (!hasPathTo(idVertice))
		{
			return camino;
		}
		else
		{
			camino = new Stack<>();
		}
		 
		for (Arco e = edgeTo[idVertice]; e != null; e = edgeTo[e.darIdOrigen()]) 
		{
			camino.push(e);
		}
			
		return camino; 
	}

}
