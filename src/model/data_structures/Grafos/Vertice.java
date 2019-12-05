package model.data_structures.Grafos;

import java.util.Iterator;
import java.util.LinkedList;

public class Vertice
{
	private LinkedList<Arco> edgeTo;
	private int id;
	double lon;
	double lat;
	int MOVEMENT_ID;
	private boolean marked;
	

	public Vertice(int id, double plon, double plat, int mov_id)
	{
		lon = plon;
		lat = plat;
		MOVEMENT_ID = mov_id;
		marked = false;
		this.id = id;
		edgeTo = new LinkedList<>();
	}
	public void anadirArco(Vertice pV, double pDistancia, double pTiempo)
	{
		Arco arco = new Arco(this, pV, pDistancia, pTiempo);
		edgeTo.add(arco);
	}
	public void eliminarArco(Vertice V)
	{
		edgeTo.remove(buscarArcoA(V));
	}
	public int darId()
	{
		return id;
	}
	public double darLongitud()
	{
		return lon;
	}
	public double darLatitud()
	{
		return lat;
	}
	public int darMOVEMENT_ID()
	{
		return MOVEMENT_ID;
	}
	public double darPesoDistancia(Vertice V)
	{
		return (double) buscarArcoA(V).darDistancia();
	}
	public double darPesoTiempo(Vertice V)
	{
		return (double) buscarArcoA(V).darTiempo();
	}
	public double darPesoVelocidad(Vertice V)
	{
		return (double) buscarArcoA(V).darVelocidad();
	}
	
	public void setInfo(int pId, double plon, double plat, int pMovId)
	{
		id = pId;
		lon = plon;
		lat = plat;
		MOVEMENT_ID = pMovId;
	}
	
	public void setDistanciaArco(Vertice V, double pDistancia)
	{
		buscarArcoA(V).setDistancia(pDistancia);
		buscarArcoA(V).actualizarVelocidad();
	}
	public void setTiempoArco(Vertice V, double pTiempo)
	{
		buscarArcoA(V).setTiempo(pTiempo);
		buscarArcoA(V).actualizarVelocidad();
	}
	
	

	public Arco buscarArcoA(Vertice V)
	{
		boolean seEncontro = false;
		Arco aRetornar = null;
		for(int i = 0; i < edgeTo.size()&&!seEncontro; i++)
		{
			if(edgeTo.get(i).darDestino().equals(V))
			{
				aRetornar = edgeTo.get(i);
			}
		}
		return aRetornar;
	}

	public void desmarcar()
	{
		marked = false;
	}
	public void marcar()
	{
		marked = true;
	}
	public boolean isMarked()
	{
		return marked;
	}
	/**
	 * Retorna la lista de IDs a los que es adyacente el vertice.
	 * @return
	 */
	public int[] adj()
	{
		int[] listaAdyacentes = new int[edgeTo.size()];
		for(int i = 0; i<edgeTo.size(); i++)
		{
			listaAdyacentes[i] = edgeTo.get(i).darDestino().darId();
		}
		return listaAdyacentes;
	}
	
	public LinkedList darArcos()
	{
		return edgeTo;
	}
	
	
	public void desmarcarTodosArcos()
	{
		Iterator it = edgeTo.iterator();
		
		
		while(it.hasNext())
		{
			Arco actual =  (Arco) it.next();
			actual.desmarcar();
		}
	}
	
}
